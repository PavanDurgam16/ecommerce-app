import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { Order } from 'src/app/shared/models/order.model';
import { OrderService } from '../order.service';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.css'],
})
export class OrderDetailComponent implements OnInit {
  order!: Order | null;
  loading: boolean = true;

  constructor(
    private route: ActivatedRoute,
    private orderService: OrderService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    const orderId = Number(this.route.snapshot.paramMap.get('id'));
    this.orderService.getOrderById(orderId).subscribe(
      (order: Order) => {
        this.order = order;
        this.snackBar.open('Order fetched successfully!', 'Close', {
          duration: 3000,
        });
        this.loading = false;
      },
      (error: any) => {
        this.snackBar.open('Error fetching order details.', 'Close', {
          duration: 3000,
        });
        this.loading = false;
        console.error('Error fetching order details:', error);
      }
    );
  }

  cancelOrder(): void {
    if (this.order) {
      this.orderService.cancelOrder(this.order.id).subscribe(() => {
        this.snackBar.open('Order cancelled successfully!', 'Close', {
          duration: 3000,
        });
      });
    }
  }
}
