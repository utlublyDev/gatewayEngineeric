import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import StoreOwner from './store-owner';
import ProductCategory from './product/product-category';
import Reviews from './product/reviews';
import Notification from './notification/notification';
import ProductOrder from './product/product-order';
import Invoice from './invoice/invoice';
import Customer from './customer';
import Product from './product/product';
import StoreCategories from './store-categories';
import Shipment from './invoice/shipment';
import OrderItem from './product/order-item';
import TopBannerInformation from './top-banner-information';
import SliderImageSlider from './slider-image-slider';
import StoreAdvBanner from './store-adv-banner';
import Payment from './invoice/payment';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}store-owner`} component={StoreOwner} />
      <ErrorBoundaryRoute path={`${match.url}product-category`} component={ProductCategory} />
      <ErrorBoundaryRoute path={`${match.url}reviews`} component={Reviews} />
      <ErrorBoundaryRoute path={`${match.url}notification`} component={Notification} />
      <ErrorBoundaryRoute path={`${match.url}product-order`} component={ProductOrder} />
      <ErrorBoundaryRoute path={`${match.url}invoice`} component={Invoice} />
      <ErrorBoundaryRoute path={`${match.url}customer`} component={Customer} />
      <ErrorBoundaryRoute path={`${match.url}product`} component={Product} />
      <ErrorBoundaryRoute path={`${match.url}store-categories`} component={StoreCategories} />
      <ErrorBoundaryRoute path={`${match.url}shipment`} component={Shipment} />
      <ErrorBoundaryRoute path={`${match.url}order-item`} component={OrderItem} />
      <ErrorBoundaryRoute path={`${match.url}top-banner-information`} component={TopBannerInformation} />
      <ErrorBoundaryRoute path={`${match.url}slider-image-slider`} component={SliderImageSlider} />
      <ErrorBoundaryRoute path={`${match.url}store-adv-banner`} component={StoreAdvBanner} />
      <ErrorBoundaryRoute path={`${match.url}payment`} component={Payment} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
