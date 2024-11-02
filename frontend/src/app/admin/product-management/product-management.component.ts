import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Product } from 'src/app/shared/models/product.model';

@Component({
  selector: 'app-product-management',
  templateUrl: './product-management.component.html',
  styleUrls: ['./product-management.component.css'],
})
export class ProductManagementComponent implements OnInit {
  products: Product[] = [];
  productsMatTable = new MatTableDataSource<Product>(this.products);
  displayedColumns: string[] = [
    'id',
    'name',
    'price',
    'itemSize',
    'stock',
    'categoryName',
    'actions',
  ];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  private apiUrl = 'http://localhost:8089/api';

  constructor(
    private http: HttpClient,
    private snackBar: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.fetchProducts();
  }

  fetchProducts(): void {
    this.http.get<Product[]>(`${this.apiUrl}/products`).subscribe(
      (data: Product[]) => {
        this.products = data;
        this.productsMatTable.data = this.products;
        this.productsMatTable.paginator = this.paginator;
        this.productsMatTable.sort = this.sort;
      },
      (error) => {
        console.error('Error fetching products:', error);
        this.snackBar.open('Error fetching products', 'Close', {
          duration: 3000,
        });
      }
    );
  }

  deleteProduct(id: number): void {
    this.http.delete(`${this.apiUrl}/admin/products/${id}`).subscribe(
      () => {
        this.snackBar.open('Product deleted successfully', 'Close', {
          duration: 3000,
        });
        this.fetchProducts();
      },
      (error) => {
        console.error('Error deleting product:', error);
        this.snackBar.open('Error deleting product', 'Close', {
          duration: 3000,
        });
      }
    );
  }

  editProduct(id: number): void {
    console.log(id);
    this.router.navigate(['admin/products/edit', id]);
  }

  viewProduct(product: Product): void {
    this.router.navigate(['products', product.id]);
  }
}
