export interface IStoreCategories {
  id?: string;
  userStoreOwnerId?: string;
  categoryName?: string;
  description?: string;
  categoryNameInArabic?: string;
  descriptionInArabic?: string | null;
  userStoreOwnerIdImageUrl?: string | null;
}

export const defaultValue: Readonly<IStoreCategories> = {};
