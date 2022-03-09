export interface ISliderImageSlider {
  id?: string;
  storeOwnerId?: string | null;
  webKey?: string | null;
  imageUrl?: string | null;
  alt?: string | null;
}

export const defaultValue: Readonly<ISliderImageSlider> = {};
