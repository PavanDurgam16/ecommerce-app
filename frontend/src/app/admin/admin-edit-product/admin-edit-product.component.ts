import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/shared/models/product.model';

@Component({
  selector: 'app-admin-edit-product',
  templateUrl: './admin-edit-product.component.html',
  styleUrls: ['./admin-edit-product.component.css'],
})
export class AdminEditProductComponent implements OnInit {
  productForm: FormGroup;
  selectedFile: File | null = null;
  productId!: number;

  private apiUrl = 'http://localhost:8089/api';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private snackBar: MatSnackBar,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.productForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      price: [0, [Validators.required, Validators.min(0)]],
      itemSize: ['', Validators.required],
      stock: [0, [Validators.required, Validators.min(0)]],
      categoryName: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    console.log('-------------------------', this.route.snapshot.paramMap);

    this.productId = Number(this.route.snapshot.paramMap.get('id'));
    this.fetchProduct();
  }

  fetchProduct(): void {
    this.http
      .get<Product>(`${this.apiUrl}/products/${this.productId}`)
      .subscribe(
        (product) => {
          this.productForm.patchValue(product);
        },
        (error) => {
          console.error('Error fetching product:', error);
          this.snackBar.open('Error fetching product', 'Close', {
            duration: 3000,
          });
        }
      );
  }

  onFileSelected(event: Event): void {
    const target = event.target as HTMLInputElement;
    if (target.files && target.files.length > 0) {
      this.selectedFile = target.files[0];
    }
  }

  navigateToProductsList() {
    this.router.navigate(['admin/products']);
  }

  updateProduct(): void {
    if (this.productForm.valid) {
      const formData = new FormData();
      formData.append(
        'product',
        new Blob([JSON.stringify(this.productForm.value)], {
          type: 'application/json',
        })
      );

      if (this.selectedFile) {
        formData.append('image', this.selectedFile);
      }

      this.http
        .put<Product>(
          `${this.apiUrl}/admin/products/${this.productId}`,
          formData
        )
        .subscribe(
          (updatedProduct) => {
            console.log('updatedProduct', updatedProduct);
            this.snackBar.open('Product updated successfully', 'Close', {
              duration: 3000,
            });
            this.navigateToProductsList();
          },
          (error) => {
            console.error('Error updating product:', error);
            this.snackBar.open('Error updating product', 'Close', {
              duration: 3000,
            });
          }
        );
    }
  }
}
