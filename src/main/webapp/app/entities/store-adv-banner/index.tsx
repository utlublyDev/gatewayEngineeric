import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import StoreAdvBanner from './store-adv-banner';
import StoreAdvBannerDetail from './store-adv-banner-detail';
import StoreAdvBannerUpdate from './store-adv-banner-update';
import StoreAdvBannerDeleteDialog from './store-adv-banner-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StoreAdvBannerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StoreAdvBannerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StoreAdvBannerDetail} />
      <ErrorBoundaryRoute path={match.url} component={StoreAdvBanner} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={StoreAdvBannerDeleteDialog} />
  </>
);

export default Routes;
