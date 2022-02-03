import { IProduct } from 'app/shared/model/product/product.model';
import { IProductOrder } from 'app/shared/model/product/product-order.model';
import { OrderItemStatus } from 'app/shared/model/enumerations/order-item-status.model';

export interface IOrderItem {
  id?: string;
  userStoreOwnerId?: string;
  userId?: string;
  quantity?: number;
  totalPrice?: number;
  status?: OrderItemStatus;
  paymentId?: string;
  orderNumber?: number;
  product?: IProduct;
  order?: IProductOrder;
}

export const defaultValue: Readonly<IOrderItem> = {};
