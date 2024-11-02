import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  private apiUrl = 'http://localhost:8089/api/orders';

  constructor(private http: HttpClient) {}

  getOrdersByUserId(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/user/${userId}`);
  }

  getOrderById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  getAllOrders(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  cancelOrder(id: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/cancel/${id}`, null);
  }

  placeOrder(order: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/place`, order);
  }
}
