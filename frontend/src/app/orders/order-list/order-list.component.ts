import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { Order } from 'src/app/shared/models/order.model';
import { OrderService } from '../order.service';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css'],
})
export class OrderListComponent implements OnInit {
  orders: Order[] = [];
  loading: boolean = true;

  constructor(
    private orderService: OrderService,
    private authService: AuthService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit(): void {
    const userId = Number(this.authService.getUserId());
    this.orderService.getOrdersByUserId(userId).subscribe(
      (orders) => {
        this.orders = orders;
        this.orders = orders.sort((a, b) => b.id - a.id);
        this.loading = false;
      },
      (error) => {
        console.error('Error fetching orders: ', error);
        this.snackBar.open(
          'Failed to fetch the order. Please try again.',
          'Close',
          {
            duration: 3000,
          }
        );
        this.loading = false;
      }
    );
  }

  viewOrderDetails(id: number) {
    this.router.navigate(['/orders', id]);
  }

  cancelOrder(id: number): void {
    if (id && this.authService.isAuthenticated()) {
      this.orderService.cancelOrder(id).subscribe(
        () => {
          this.snackBar.open('Order cancelled successfully!', 'Close', {
            duration: 3000,
          });
        },
        (error) => {
          console.error('Error cancelling order:', error);
          this.snackBar.open(
            'Failed to cancel the order. Please try again.',
            'Close',
            {
              duration: 3000,
            }
          );
        }
      );
    }
  }
}
