import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './store-owner.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StoreOwnerDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const storeOwnerEntity = useAppSelector(state => state.storeOwner.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="storeOwnerDetailsHeading">
          <Translate contentKey="gatewayEngineericApp.storeOwner.detail.title">StoreOwner</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{storeOwnerEntity.id}</dd>
          <dt>
            <span id="userStoreOwnerId">
              <Translate contentKey="gatewayEngineericApp.storeOwner.userStoreOwnerId">User Store Owner Id</Translate>
            </span>
          </dt>
          <dd>{storeOwnerEntity.userStoreOwnerId}</dd>
          <dt>
            <span id="storeName">
              <Translate contentKey="gatewayEngineericApp.storeOwner.storeName">Store Name</Translate>
            </span>
          </dt>
          <dd>{storeOwnerEntity.storeName}</dd>
          <dt>
            <span id="storeNameInArabic">
              <Translate contentKey="gatewayEngineericApp.storeOwner.storeNameInArabic">Store Name In Arabic</Translate>
            </span>
          </dt>
          <dd>{storeOwnerEntity.storeNameInArabic}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="gatewayEngineericApp.storeOwner.address">Address</Translate>
            </span>
          </dt>
          <dd>{storeOwnerEntity.address}</dd>
          <dt>
            <span id="addressInArabic">
              <Translate contentKey="gatewayEngineericApp.storeOwner.addressInArabic">Address In Arabic</Translate>
            </span>
          </dt>
          <dd>{storeOwnerEntity.addressInArabic}</dd>
          <dt>
            <span id="longitude">
              <Translate contentKey="gatewayEngineericApp.storeOwner.longitude">Longitude</Translate>
            </span>
          </dt>
          <dd>{storeOwnerEntity.longitude}</dd>
          <dt>
            <span id="latitude">
              <Translate contentKey="gatewayEngineericApp.storeOwner.latitude">Latitude</Translate>
            </span>
          </dt>
          <dd>{storeOwnerEntity.latitude}</dd>
          <dt>
            <span id="isBusy">
              <Translate contentKey="gatewayEngineericApp.storeOwner.isBusy">Is Busy</Translate>
            </span>
          </dt>
          <dd>{storeOwnerEntity.isBusy ? 'true' : 'false'}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="gatewayEngineericApp.storeOwner.city">City</Translate>
            </span>
          </dt>
          <dd>{storeOwnerEntity.city}</dd>
          <dt>
            <span id="cityInArabic">
              <Translate contentKey="gatewayEngineericApp.storeOwner.cityInArabic">City In Arabic</Translate>
            </span>
          </dt>
          <dd>{storeOwnerEntity.cityInArabic}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="gatewayEngineericApp.storeOwner.description">Description</Translate>
            </span>
          </dt>
          <dd>{storeOwnerEntity.description}</dd>
          <dt>
            <span id="descriptionInArabic">
              <Translate contentKey="gatewayEngineericApp.storeOwner.descriptionInArabic">Description In Arabic</Translate>
            </span>
          </dt>
          <dd>{storeOwnerEntity.descriptionInArabic}</dd>
          <dt>
            <span id="storeContactNumber">
              <Translate contentKey="gatewayEngineericApp.storeOwner.storeContactNumber">Store Contact Number</Translate>
            </span>
          </dt>
          <dd>{storeOwnerEntity.storeContactNumber}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="gatewayEngineericApp.storeOwner.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {storeOwnerEntity.createdDate ? <TextFormat value={storeOwnerEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="storeLogoUrl">
              <Translate contentKey="gatewayEngineericApp.storeOwner.storeLogoUrl">Store Logo Url</Translate>
            </span>
          </dt>
          <dd>{storeOwnerEntity.storeLogoUrl}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="gatewayEngineericApp.storeOwner.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{storeOwnerEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="hasDelivery">
              <Translate contentKey="gatewayEngineericApp.storeOwner.hasDelivery">Has Delivery</Translate>
            </span>
          </dt>
          <dd>{storeOwnerEntity.hasDelivery ? 'true' : 'false'}</dd>
          <dt>
            <span id="hasFreeDelivery">
              <Translate contentKey="gatewayEngineericApp.storeOwner.hasFreeDelivery">Has Free Delivery</Translate>
            </span>
          </dt>
          <dd>{storeOwnerEntity.hasFreeDelivery ? 'true' : 'false'}</dd>
          <dt>
            <span id="availableDateTime">
              <Translate contentKey="gatewayEngineericApp.storeOwner.availableDateTime">Available Date Time</Translate>
            </span>
          </dt>
          <dd>
            {storeOwnerEntity.availableDateTime ? (
              <TextFormat value={storeOwnerEntity.availableDateTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="shopOpeiningTime">
              <Translate contentKey="gatewayEngineericApp.storeOwner.shopOpeiningTime">Shop Opeining Time</Translate>
            </span>
          </dt>
          <dd>
            {storeOwnerEntity.shopOpeiningTime ? (
              <TextFormat value={storeOwnerEntity.shopOpeiningTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="shopClosingTime">
              <Translate contentKey="gatewayEngineericApp.storeOwner.shopClosingTime">Shop Closing Time</Translate>
            </span>
          </dt>
          <dd>
            {storeOwnerEntity.shopClosingTime ? (
              <TextFormat value={storeOwnerEntity.shopClosingTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="currency">
              <Translate contentKey="gatewayEngineericApp.storeOwner.currency">Currency</Translate>
            </span>
          </dt>
          <dd>{storeOwnerEntity.currency}</dd>
          <dt>
            <span id="deliveryCost">
              <Translate contentKey="gatewayEngineericApp.storeOwner.deliveryCost">Delivery Cost</Translate>
            </span>
          </dt>
          <dd>{storeOwnerEntity.deliveryCost}</dd>
        </dl>
        <Button tag={Link} to="/store-owner" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/store-owner/${storeOwnerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StoreOwnerDetail;
