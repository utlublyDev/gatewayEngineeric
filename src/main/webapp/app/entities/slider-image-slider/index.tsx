import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SliderImageSlider from './slider-image-slider';
import SliderImageSliderDetail from './slider-image-slider-detail';
import SliderImageSliderUpdate from './slider-image-slider-update';
import SliderImageSliderDeleteDialog from './slider-image-slider-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SliderImageSliderUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SliderImageSliderUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SliderImageSliderDetail} />
      <ErrorBoundaryRoute path={match.url} component={SliderImageSlider} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SliderImageSliderDeleteDialog} />
  </>
);

export default Routes;
