import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { CartComponent } from './cart.component';
import {MaterialModule} from "../shared/ng-comon-material/material.module";

@NgModule({
  declarations: [CartComponent],
  imports: [CommonModule,MaterialModule]
})
export class CartModule {}
