import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Reviews from './reviews';
import ReviewsDetail from './reviews-detail';
import ReviewsUpdate from './reviews-update';
import ReviewsDeleteDialog from './reviews-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ReviewsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ReviewsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ReviewsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Reviews} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ReviewsDeleteDialog} />
  </>
);

export default Routes;
