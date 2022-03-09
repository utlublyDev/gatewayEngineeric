export interface IStoreAdvBanner {
  id?: string;
  storeOwnerId?: string | null;
  webKey?: string | null;
  imageUrl?: string | null;
}

export const defaultValue: Readonly<IStoreAdvBanner> = {};
