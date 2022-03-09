import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TopBannerInformation from './top-banner-information';
import TopBannerInformationDetail from './top-banner-information-detail';
import TopBannerInformationUpdate from './top-banner-information-update';
import TopBannerInformationDeleteDialog from './top-banner-information-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TopBannerInformationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TopBannerInformationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TopBannerInformationDetail} />
      <ErrorBoundaryRoute path={match.url} component={TopBannerInformation} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TopBannerInformationDeleteDialog} />
  </>
);

export default Routes;
