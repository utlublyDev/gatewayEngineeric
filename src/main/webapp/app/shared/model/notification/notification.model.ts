import dayjs from 'dayjs';
import { NotificationType } from 'app/shared/model/enumerations/notification-type.model';

export interface INotification {
  id?: string;
  userStoreOwnerId?: string;
  date?: string;
  details?: string | null;
  sentDate?: string;
  format?: NotificationType;
  userId?: string;
  productId?: string;
}

export const defaultValue: Readonly<INotification> = {};
