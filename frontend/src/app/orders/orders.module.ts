import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { OrderDetailComponent } from './order-detail/order-detail.component';
import { OrderListComponent } from './order-list/order-list.component';
import {MaterialModule} from "../shared/ng-comon-material/material.module";
import {RouterModule} from "@angular/router";

@NgModule({
  declarations: [OrderListComponent, OrderDetailComponent],
  imports: [CommonModule,MaterialModule, RouterModule],
})
export class OrdersModule {}
