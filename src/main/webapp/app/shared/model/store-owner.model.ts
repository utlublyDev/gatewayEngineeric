import dayjs from 'dayjs';

export interface IStoreOwner {
  id?: string;
  userStoreOwnerId?: string;
  storeName?: string;
  storeNameInArabic?: string;
  address?: string;
  addressInArabic?: string;
  longitude?: number;
  latitude?: number;
  isBusy?: boolean;
  city?: string;
  cityInArabic?: string;
  description?: string | null;
  descriptionInArabic?: string | null;
  storeContactNumber?: string | null;
  createdDate?: string | null;
  storeLogoUrl?: string | null;
  isActive?: boolean | null;
  hasDelivery?: boolean;
  hasFreeDelivery?: boolean | null;
  availableDateTime?: string;
  shopOpeiningTime?: string | null;
  shopClosingTime?: string | null;
  currency?: string | null;
  deliveryCost?: number | null;
}

export const defaultValue: Readonly<IStoreOwner> = {
  isBusy: false,
  isActive: false,
  hasDelivery: false,
  hasFreeDelivery: false,
};
