import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './shipment.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ShipmentDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const shipmentEntity = useAppSelector(state => state.shipment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="shipmentDetailsHeading">
          <Translate contentKey="gatewayEngineericApp.invoiceShipment.detail.title">Shipment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{shipmentEntity.id}</dd>
          <dt>
            <span id="userStoreOwnerId">
              <Translate contentKey="gatewayEngineericApp.invoiceShipment.userStoreOwnerId">User Store Owner Id</Translate>
            </span>
          </dt>
          <dd>{shipmentEntity.userStoreOwnerId}</dd>
          <dt>
            <span id="userId">
              <Translate contentKey="gatewayEngineericApp.invoiceShipment.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{shipmentEntity.userId}</dd>
          <dt>
            <span id="trackingCode">
              <Translate contentKey="gatewayEngineericApp.invoiceShipment.trackingCode">Tracking Code</Translate>
            </span>
          </dt>
          <dd>{shipmentEntity.trackingCode}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="gatewayEngineericApp.invoiceShipment.date">Date</Translate>
            </span>
          </dt>
          <dd>{shipmentEntity.date ? <TextFormat value={shipmentEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="details">
              <Translate contentKey="gatewayEngineericApp.invoiceShipment.details">Details</Translate>
            </span>
          </dt>
          <dd>{shipmentEntity.details}</dd>
          <dt>
            <Translate contentKey="gatewayEngineericApp.invoiceShipment.invoice">Invoice</Translate>
          </dt>
          <dd>{shipmentEntity.invoice ? shipmentEntity.invoice.code : ''}</dd>
        </dl>
        <Button tag={Link} to="/shipment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/shipment/${shipmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ShipmentDetail;
