import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './store-adv-banner.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StoreAdvBannerDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const storeAdvBannerEntity = useAppSelector(state => state.storeAdvBanner.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="storeAdvBannerDetailsHeading">
          <Translate contentKey="gatewayEngineericApp.storeAdvBanner.detail.title">StoreAdvBanner</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{storeAdvBannerEntity.id}</dd>
          <dt>
            <span id="storeOwnerId">
              <Translate contentKey="gatewayEngineericApp.storeAdvBanner.storeOwnerId">Store Owner Id</Translate>
            </span>
          </dt>
          <dd>{storeAdvBannerEntity.storeOwnerId}</dd>
          <dt>
            <span id="webKey">
              <Translate contentKey="gatewayEngineericApp.storeAdvBanner.webKey">Web Key</Translate>
            </span>
          </dt>
          <dd>{storeAdvBannerEntity.webKey}</dd>
          <dt>
            <span id="imageUrl">
              <Translate contentKey="gatewayEngineericApp.storeAdvBanner.imageUrl">Image Url</Translate>
            </span>
          </dt>
          <dd>{storeAdvBannerEntity.imageUrl}</dd>
        </dl>
        <Button tag={Link} to="/store-adv-banner" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/store-adv-banner/${storeAdvBannerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StoreAdvBannerDetail;
