import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { CartService } from 'src/app/cart/cart.service';
import { Product } from 'src/app/shared/models/product.model';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css'],
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  firstFiveProducts: Product[] = [];
  remainingProducts: Product[] = [];
  loading: boolean = true;

  constructor(
    private productService: ProductService,
    private router: Router,
    private cartService: CartService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.getAllProducts();
  }

  getAllProducts(): void {
    this.productService.getProducts().subscribe((data: Product[]) => {
      this.products = data;
      this.firstFiveProducts = this.products.slice(0, 5);
      this.remainingProducts = this.products.slice(5);
      this.loading = false;
    });
  }

  goToProductDetail(productId: number) {
    this.router.navigate([`/products/${productId}`]);
  }

  addToCart(product: Product) {
    console.log('inside addToCart ', product);

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
              console.log('Product added to cart for user', userId, response);
            },
            (error) => {
              console.error('Error adding product to cart', error);
            }
          );
      } else {
        console.log('User ID not found. Please log in.');
      }
    } else {
      console.log(
        'Cannot add product to cart. Ensure you are logged in and are a customer.'
      );
    }
  }
}
