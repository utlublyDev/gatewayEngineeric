import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import StoreOwner from './store-owner';
import StoreOwnerDetail from './store-owner-detail';
import StoreOwnerUpdate from './store-owner-update';
import StoreOwnerDeleteDialog from './store-owner-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StoreOwnerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StoreOwnerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StoreOwnerDetail} />
      <ErrorBoundaryRoute path={match.url} component={StoreOwner} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={StoreOwnerDeleteDialog} />
  </>
);

export default Routes;
