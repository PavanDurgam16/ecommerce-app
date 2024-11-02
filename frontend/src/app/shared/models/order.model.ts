import { OrderItem } from './order-item.model';

export interface Order {
  id: number;
  userId: number;
  orderItems: OrderItem[];
  totalAmount: number;
  status: string;
}
