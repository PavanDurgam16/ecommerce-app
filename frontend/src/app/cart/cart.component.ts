import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { OrderService } from '../orders/order.service';
import { Cart } from '../shared/models/cart.model'; // Import the Cart model
import { CartService } from './cart.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent implements OnInit {
  cart: Cart | null = null;
  totalPrice: number = 0;
  loading: boolean = false;

  constructor(
    private cartService: CartService,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar,
    private orderService: OrderService
  ) {}

  ngOnInit(): void {
    this.loadCart();
  }

  loadCart(): void {
    const userId = localStorage.getItem('userId');
    if (userId) {
      this.loading = true;
      this.cartService.getCartByUser(Number(userId)).subscribe(
        (cart) => {
          this.cart = cart;
          if (cart) {
            this.calculateTotalPrice();
          }
          this.loading = false;
        },
        (error) => {
          this.snackBar.open(
            'Error loading cart, please try again later!',
            'Close',
            {
              duration: 3000,
            }
          );
          this.loading = false;
        }
      );
    } else {
      this.loading = false;
    }
  }

  removeFromCart(productId: number): void {
    const userId = localStorage.getItem('userId');
    if (userId) {
      this.cartService
        .removeCartItem(Number(userId), productId)
        .subscribe(() => {
          this.snackBar.open('cart Item are removed successfully! ', 'Close', {
            duration: 3000,
          });
          this.loadCart();
        });
    }
  }

  clearTotoalCart(): void {
    const userId = this.authService.getUserId();
    if (userId) {
      this.cartService.clearCart(Number(userId)).subscribe(() => {
        this.loadCart();
        this.snackBar.open('All the cart Items are cleared', 'Close', {
          duration: 3000,
        });
      });
    }
  }

  calculateTotalPrice(): void {
    if (this.cart) {
      this.totalPrice = this.cart.cartItems.reduce(
        (sum, item) => sum + item.product.price * item.quantity,
        0
      );
    }
  }

  placeOrder(): void {
    if (this.cart && this.cart.cartItems.length > 0) {
      const order = {
        orderItems: this.cart.cartItems.map((item) => ({
          productId: item.product.id,
          quantity: item.quantity,
        })),
        totalAmount: this.totalPrice,
        userId: Number(this.authService.getUserId()),
      };

      this.orderService.placeOrder(order).subscribe(
        (response: any) => {
          console.log('response -order ', response);
          this.snackBar.open('Order placed successfully!', 'Close', {
            duration: 3000,
          });
          this.clearCartItems();
          this.router.navigate(['/orders']);
        },
        (error: any) => {
          console.error('Error placing order:', error);
          this.snackBar.open(
            'Error placing order. Please try again.',
            'Close',
            {
              duration: 3000,
            }
          );
        }
      );
    } else {
      this.snackBar.open('Your cart is empty!', 'Close', {
        duration: 3000,
      });
    }

    if (this.cart && this.cart.cartItems.length > 0) {
      const order = {
        orderItems: this.cart.cartItems.map((item) => ({
          productId: item.product.id,
          quantity: item.quantity,
        })),
        totalAmount: this.totalPrice,
        userId: Number(this.authService.getUserId()),
      };

      // Only one call to place the order
      this.orderService.placeOrder(order).subscribe(
        (response: any) => {
          console.log('response -order ', response);
          this.snackBar.open('Order placed successfully!', 'Close', {
            duration: 3000,
          });
          this.clearCartItems();
          this.router.navigate(['/orders']);
        },
        (error: any) => {
          console.error('Error placing order:', error);
          this.snackBar.open(
            'Error placing order. Please try again.',
            'Close',
            {
              duration: 3000,
            }
          );
        }
      );
    } else {
      this.snackBar.open('Your cart is empty!', 'Close', {
        duration: 3000,
      });
    }
  }

  clearCartItems(): void {
    this.cart = null;
    this.totalPrice = 0;
  }
}
