import dayjs from 'dayjs';

export interface IReviews {
  id?: string;
  customerId?: string | null;
  orderId?: string | null;
  review?: string | null;
  rating?: number | null;
  createdAt?: string | null;
  prodcutsId?: string | null;
}

export const defaultValue: Readonly<IReviews> = {};
