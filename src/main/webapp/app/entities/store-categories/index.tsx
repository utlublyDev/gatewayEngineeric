import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import StoreCategories from './store-categories';
import StoreCategoriesDetail from './store-categories-detail';
import StoreCategoriesUpdate from './store-categories-update';
import StoreCategoriesDeleteDialog from './store-categories-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StoreCategoriesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StoreCategoriesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StoreCategoriesDetail} />
      <ErrorBoundaryRoute path={match.url} component={StoreCategories} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={StoreCategoriesDeleteDialog} />
  </>
);

export default Routes;
