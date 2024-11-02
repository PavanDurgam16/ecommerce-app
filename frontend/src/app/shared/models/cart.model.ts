import { CartItem } from './cart-item.model';

export interface Cart {
  id: number;
  user: {
    id: number;
    username: string;
  };
  cartItems: CartItem[];
}
