import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Product } from 'src/app/shared/models/product.model';

@Component({
  selector: 'app-admin-add-product',
  templateUrl: './admin-add-product.component.html',
  styleUrls: ['./admin-add-product.component.css'],
})
export class AdminAddProductComponent {
  productForm: FormGroup;
  selectedFile: File | null = null;

  private apiUrl = 'http://localhost:8089/api';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private snackBar: MatSnackBar,
    private router: Router
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

  onFileSelected(event: Event): void {
    const target = event.target as HTMLInputElement;
    if (target.files && target.files.length > 0) {
      this.selectedFile = target.files[0];
    }
  }
  navigateToProductsList() {
    this.router.navigate(['admin/products']);
  }

  addProduct(): void {
    if (this.productForm.valid && this.selectedFile) {
      const formData = new FormData();
      formData.append(
        'product',
        new Blob([JSON.stringify(this.productForm.value)], {
          type: 'application/json',
        })
      );
      formData.append('image', this.selectedFile);

      this.http
        .post<Product>(`${this.apiUrl}/admin/products`, formData)
        .subscribe(
          (createdProduct) => {
            console.log('createdProduct', createdProduct);

            this.snackBar.open('Product added successfully', 'Close', {
              duration: 3000,
            });
            this.navigateToProductsList();
          },
          (error) => {
            console.error('Error adding product:', error);
            this.snackBar.open('Error adding product', 'Close', {
              duration: 3000,
            });
          }
        );
    }
  }
}
