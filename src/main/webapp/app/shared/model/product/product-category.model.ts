import dayjs from 'dayjs';
import { IProduct } from 'app/shared/model/product/product.model';
import { CategoryStatus } from 'app/shared/model/enumerations/category-status.model';

export interface IProductCategory {
  id?: string;
  userStoreOwnerId?: string;
  productCategoryName?: string;
  productCategoryNameInArabic?: string;
  productCategoryDescription?: string | null;
  productCategoryDescriptionInArabic?: string | null;
  sortOrder?: number | null;
  dateAdded?: string | null;
  dateModified?: string | null;
  status?: CategoryStatus | null;
  webKey?: string | null;
  imageUrl?: string | null;
  products?: IProduct[] | null;
}

export const defaultValue: Readonly<IProductCategory> = {};
