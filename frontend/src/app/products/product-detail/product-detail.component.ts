import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { CartService } from 'src/app/cart/cart.service';
import { Product } from 'src/app/shared/models/product.model';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css'],
})
export class ProductDetailComponent implements OnInit {
  product: Product | null = null;
  loading: boolean = true;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private productService: ProductService,
    private cartService: CartService,
    private authService: AuthService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    const productId = Number(this.route.snapshot.paramMap.get('id'));
    this.getProductDetail(productId);
  }

  getProductDetail(id: number) {
    this.productService.getProductById(id).subscribe((data: Product) => {
      this.product = data;
      this.loading = false;
    });
  }

  goBack() {
    if (this.authService.isAdmin()) {
      this.router.navigate(['/admin/products']);
    } else {
      this.router.navigate(['/products']);
    }
  }

  addToCart(product: Product) {
    console.log('Add to Cart clicked for product:', product);
    if (
      product &&
      this.authService.isAuthenticated() &&
      this.authService.isCustomer()
    ) {
      const userId: string | null = localStorage.getItem('userId');

      if (userId) {
        let quantity = 1;
        this.cartService
          .addItemToCart(Number(userId), product.id, quantity)
          .subscribe(
            (response) => {
              this.snackBar.open('Product added to cart!', 'Close', {
                duration: 3000,
              });
            },
            (error) => {
              this.snackBar.open('Error adding product to cart.', 'Close', {
                duration: 3000,
              });
            }
          );
      } else {
        this.snackBar.open('User ID not found. Please log in.', 'Close', {
          duration: 3000,
        });
      }
    } else {
      this.snackBar.open(
        'Please log in to add products to the cart.',
        'Close',
        {
          duration: 3000,
        }
      );
      this.router.navigate(['/auth/login']);
    }
  }
}
