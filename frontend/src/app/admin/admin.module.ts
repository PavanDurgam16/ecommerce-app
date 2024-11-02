import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from '../app-routing.module';
import { MaterialModule } from '../shared/ng-comon-material/material.module';
import { AdminAddProductComponent } from './admin-add-product/admin-add-product.component';
import { AdminEditProductComponent } from './admin-edit-product/admin-edit-product.component';
import { ProductManagementComponent } from './product-management/product-management.component';

@NgModule({
  declarations: [
    ProductManagementComponent,
    AdminAddProductComponent,
    AdminEditProductComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    AppRoutingModule,
  ],
})
export class AdminModule {}
