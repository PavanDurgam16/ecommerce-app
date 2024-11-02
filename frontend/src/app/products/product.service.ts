import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../shared/models/product.model';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private apiUrl = 'http://localhost:8089/api';

  constructor(private http: HttpClient) {}

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/products`);
  }

  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.apiUrl}/products/${id}`);
  }

  addProduct(product: Product, image: File): Observable<Product> {
    const formData: FormData = new FormData();
    formData.append(
      'product',
      new Blob([JSON.stringify(product)], { type: 'application/json' })
    );
    formData.append('image', image);

    return this.http.post<Product>(`${this.apiUrl}/admin/products`, formData);
  }

  updateProduct(
    id: number,
    product: Product,
    image?: File
  ): Observable<Product> {
    const formData: FormData = new FormData();
    formData.append(
      'product',
      new Blob([JSON.stringify(product)], { type: 'application/json' })
    );
    if (image) {
      formData.append('image', image);
    }

    return this.http.put<Product>(
      `${this.apiUrl}/admin/products/${id}`,
      formData
    );
  }

  removeProduct(productId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/admi/products/${productId}`);
  }
}
