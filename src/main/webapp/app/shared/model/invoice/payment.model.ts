import dayjs from 'dayjs';
import { paymentStatus } from 'app/shared/model/enumerations/payment-status.model';

export interface IPayment {
  id?: string;
  customerId?: string | null;
  orderId?: string | null;
  amount?: number | null;
  paymentStatus?: paymentStatus | null;
  storeOwnerId?: string | null;
  webKey?: string | null;
  createdAt?: string | null;
}

export const defaultValue: Readonly<IPayment> = {};
