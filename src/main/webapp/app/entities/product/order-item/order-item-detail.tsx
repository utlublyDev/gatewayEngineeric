import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './order-item.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const OrderItemDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const orderItemEntity = useAppSelector(state => state.orderItem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="orderItemDetailsHeading">
          <Translate contentKey="gatewayEngineericApp.productOrderItem.detail.title">OrderItem</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.id}</dd>
          <dt>
            <span id="userStoreOwnerId">
              <Translate contentKey="gatewayEngineericApp.productOrderItem.userStoreOwnerId">User Store Owner Id</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.userStoreOwnerId}</dd>
          <dt>
            <span id="userId">
              <Translate contentKey="gatewayEngineericApp.productOrderItem.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.userId}</dd>
          <dt>
            <span id="quantity">
              <Translate contentKey="gatewayEngineericApp.productOrderItem.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.quantity}</dd>
          <dt>
            <span id="totalPrice">
              <Translate contentKey="gatewayEngineericApp.productOrderItem.totalPrice">Total Price</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.totalPrice}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="gatewayEngineericApp.productOrderItem.status">Status</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.status}</dd>
          <dt>
            <span id="paymentId">
              <Translate contentKey="gatewayEngineericApp.productOrderItem.paymentId">Payment Id</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.paymentId}</dd>
          <dt>
            <span id="orderNumber">
              <Translate contentKey="gatewayEngineericApp.productOrderItem.orderNumber">Order Number</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.orderNumber}</dd>
          <dt>
            <Translate contentKey="gatewayEngineericApp.productOrderItem.product">Product</Translate>
          </dt>
          <dd>{orderItemEntity.product ? orderItemEntity.product.productName : ''}</dd>
          <dt>
            <Translate contentKey="gatewayEngineericApp.productOrderItem.order">Order</Translate>
          </dt>
          <dd>{orderItemEntity.order ? orderItemEntity.order.code : ''}</dd>
        </dl>
        <Button tag={Link} to="/order-item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/order-item/${orderItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrderItemDetail;
