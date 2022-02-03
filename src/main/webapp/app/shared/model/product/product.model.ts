import dayjs from 'dayjs';
import { IProductCategory } from 'app/shared/model/product/product-category.model';

export interface IProduct {
  id?: string;
  userStoreOwnerId?: string;
  productName?: string;
  productNameInArabic?: string;
  productDescription?: string | null;
  productDescriptionInArabic?: string | null;
  price?: number;
  imageContentType?: string | null;
  image?: string | null;
  imageUrl?: string | null;
  keywords?: string | null;
  dateAdded?: string | null;
  dateModified?: string | null;
  categories?: IProductCategory[] | null;
}

export const defaultValue: Readonly<IProduct> = {};
