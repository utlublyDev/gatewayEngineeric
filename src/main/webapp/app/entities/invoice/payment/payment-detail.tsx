import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './payment.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PaymentDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const paymentEntity = useAppSelector(state => state.payment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="paymentDetailsHeading">
          <Translate contentKey="gatewayEngineericApp.invoicePayment.detail.title">Payment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.id}</dd>
          <dt>
            <span id="customerId">
              <Translate contentKey="gatewayEngineericApp.invoicePayment.customerId">Customer Id</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.customerId}</dd>
          <dt>
            <span id="orderId">
              <Translate contentKey="gatewayEngineericApp.invoicePayment.orderId">Order Id</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.orderId}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="gatewayEngineericApp.invoicePayment.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.amount}</dd>
          <dt>
            <span id="paymentStatus">
              <Translate contentKey="gatewayEngineericApp.invoicePayment.paymentStatus">Payment Status</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.paymentStatus}</dd>
          <dt>
            <span id="storeOwnerId">
              <Translate contentKey="gatewayEngineericApp.invoicePayment.storeOwnerId">Store Owner Id</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.storeOwnerId}</dd>
          <dt>
            <span id="webKey">
              <Translate contentKey="gatewayEngineericApp.invoicePayment.webKey">Web Key</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.webKey}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="gatewayEngineericApp.invoicePayment.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.createdAt ? <TextFormat value={paymentEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/payment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/payment/${paymentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PaymentDetail;
