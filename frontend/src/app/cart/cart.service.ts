import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Cart } from '../shared/models/cart.model';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private apiUrl = 'http://localhost:8089/api/cart';

  constructor(private http: HttpClient) {}

  addItemToCart(userId: number, productId: number, quantity: number) {
    const params = { userId, productId, quantity };
    console.log(
      `Adding product ${productId} with quantity ${quantity} for user ${userId}`
    );
    return this.http.post<Cart>(`${this.apiUrl}/add`, null, { params });
  }

  getCartByUser(userId: number) {
    return this.http.get<Cart>(`${this.apiUrl}/user/${userId}`);
  }

  removeCartItem(userId: number, productId: number) {
    const params = { userId, productId };
    return this.http.delete<void>(`${this.apiUrl}/remove`, { params });
  }

  clearCart(userId: number) {
    return this.http.delete<void>(`${this.apiUrl}/clear/${userId}`);
  }
}
