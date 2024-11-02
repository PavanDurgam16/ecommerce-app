import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminAddProductComponent } from './admin/admin-add-product/admin-add-product.component';
import { AdminEditProductComponent } from './admin/admin-edit-product/admin-edit-product.component';
import { ProductManagementComponent } from './admin/product-management/product-management.component';
import { AuthGuard } from './auth/auth.guard';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { CartComponent } from './cart/cart.component';
import { OrderDetailComponent } from './orders/order-detail/order-detail.component';
import { OrderListComponent } from './orders/order-list/order-list.component';
import { ProductDetailComponent } from './products/product-detail/product-detail.component';
import { ProductListComponent } from './products/product-list/product-list.component';

const routes: Routes = [
  { path: '', redirectTo: '/products', pathMatch: 'full' },

  // Auth Routes
  { path: 'auth/login', component: LoginComponent },
  { path: 'auth/register', component: RegisterComponent },

  // Product Routes
  { path: 'products', component: ProductListComponent },
  { path: 'products/:id', component: ProductDetailComponent },

  // Cart Routes
  { path: 'cart', component: CartComponent, canActivate: [AuthGuard] },
  // Order Routes
  {
    path: 'orders',
    component: OrderListComponent,
    canActivate: [AuthGuard],
    children: [
      { path: '', redirectTo: 'user', pathMatch: 'full' },
      { path: 'user', component: OrderListComponent },
      { path: ':id', component: OrderDetailComponent },
      { path: 'cancel/:id', component: OrderDetailComponent },
    ],
  },
  // Admin Routes
  {
    path: 'admin/products',
    component: ProductManagementComponent,
    canActivate: [AuthGuard],
    children: [
      { path: '', redirectTo: 'list', pathMatch: 'full' },
      { path: 'products/:id', component: ProductDetailComponent },
      { path: 'edit/:id', component: AdminEditProductComponent },
      { path: 'add', component: AdminAddProductComponent },
    ],
  },
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
