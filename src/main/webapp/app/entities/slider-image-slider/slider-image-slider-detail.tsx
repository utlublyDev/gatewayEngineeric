import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './slider-image-slider.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SliderImageSliderDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const sliderImageSliderEntity = useAppSelector(state => state.sliderImageSlider.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sliderImageSliderDetailsHeading">
          <Translate contentKey="gatewayEngineericApp.sliderImageSlider.detail.title">SliderImageSlider</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{sliderImageSliderEntity.id}</dd>
          <dt>
            <span id="storeOwnerId">
              <Translate contentKey="gatewayEngineericApp.sliderImageSlider.storeOwnerId">Store Owner Id</Translate>
            </span>
          </dt>
          <dd>{sliderImageSliderEntity.storeOwnerId}</dd>
          <dt>
            <span id="webKey">
              <Translate contentKey="gatewayEngineericApp.sliderImageSlider.webKey">Web Key</Translate>
            </span>
          </dt>
          <dd>{sliderImageSliderEntity.webKey}</dd>
          <dt>
            <span id="imageUrl">
              <Translate contentKey="gatewayEngineericApp.sliderImageSlider.imageUrl">Image Url</Translate>
            </span>
          </dt>
          <dd>{sliderImageSliderEntity.imageUrl}</dd>
          <dt>
            <span id="alt">
              <Translate contentKey="gatewayEngineericApp.sliderImageSlider.alt">Alt</Translate>
            </span>
          </dt>
          <dd>{sliderImageSliderEntity.alt}</dd>
        </dl>
        <Button tag={Link} to="/slider-image-slider" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/slider-image-slider/${sliderImageSliderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SliderImageSliderDetail;
