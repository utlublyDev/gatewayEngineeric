import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './customer.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CustomerDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const customerEntity = useAppSelector(state => state.customer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="customerDetailsHeading">
          <Translate contentKey="gatewayEngineericApp.customer.detail.title">Customer</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{customerEntity.id}</dd>
          <dt>
            <span id="userId">
              <Translate contentKey="gatewayEngineericApp.customer.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{customerEntity.userId}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="gatewayEngineericApp.customer.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{customerEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="gatewayEngineericApp.customer.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{customerEntity.lastName}</dd>
          <dt>
            <span id="gender">
              <Translate contentKey="gatewayEngineericApp.customer.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{customerEntity.gender}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="gatewayEngineericApp.customer.email">Email</Translate>
            </span>
          </dt>
          <dd>{customerEntity.email}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="gatewayEngineericApp.customer.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{customerEntity.phone}</dd>
          <dt>
            <span id="addressLine1">
              <Translate contentKey="gatewayEngineericApp.customer.addressLine1">Address Line 1</Translate>
            </span>
          </dt>
          <dd>{customerEntity.addressLine1}</dd>
          <dt>
            <span id="addressLine2">
              <Translate contentKey="gatewayEngineericApp.customer.addressLine2">Address Line 2</Translate>
            </span>
          </dt>
          <dd>{customerEntity.addressLine2}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="gatewayEngineericApp.customer.city">City</Translate>
            </span>
          </dt>
          <dd>{customerEntity.city}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="gatewayEngineericApp.customer.country">Country</Translate>
            </span>
          </dt>
          <dd>{customerEntity.country}</dd>
          <dt>
            <Translate contentKey="gatewayEngineericApp.customer.user">User</Translate>
          </dt>
          <dd>{customerEntity.user ? customerEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/customer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/customer/${customerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CustomerDetail;
