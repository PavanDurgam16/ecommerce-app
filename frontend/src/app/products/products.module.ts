import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MaterialModule } from '../shared/ng-comon-material/material.module';
import { TruncatePipe } from '../shared/pipes/truncate.pipe';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { ProductListComponent } from './product-list/product-list.component';

@NgModule({
  declarations: [ProductListComponent, ProductDetailComponent, TruncatePipe],
  imports: [CommonModule, MaterialModule],
})
export class ProductsModule {}
