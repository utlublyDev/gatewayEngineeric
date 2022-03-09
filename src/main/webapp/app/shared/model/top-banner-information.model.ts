import dayjs from 'dayjs';

export interface ITopBannerInformation {
  id?: string;
  enableBanner?: boolean | null;
  bannerText?: string | null;
  startBanner?: string | null;
  endBanner?: string | null;
  webKey?: string | null;
  storeOwnerId?: string | null;
}

export const defaultValue: Readonly<ITopBannerInformation> = {
  enableBanner: false,
};
