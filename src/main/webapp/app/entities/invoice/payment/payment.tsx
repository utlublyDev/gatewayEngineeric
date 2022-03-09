import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './payment.reducer';
import { IPayment } from 'app/shared/model/invoice/payment.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Payment = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const paymentList = useAppSelector(state => state.payment.entities);
  const loading = useAppSelector(state => state.payment.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="payment-heading" data-cy="PaymentHeading">
        <Translate contentKey="gatewayEngineericApp.invoicePayment.home.title">Payments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="gatewayEngineericApp.invoicePayment.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="gatewayEngineericApp.invoicePayment.home.createLabel">Create new Payment</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {paymentList && paymentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="gatewayEngineericApp.invoicePayment.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.invoicePayment.customerId">Customer Id</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.invoicePayment.orderId">Order Id</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.invoicePayment.amount">Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.invoicePayment.paymentStatus">Payment Status</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.invoicePayment.storeOwnerId">Store Owner Id</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.invoicePayment.webKey">Web Key</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.invoicePayment.createdAt">Created At</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {paymentList.map((payment, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${payment.id}`} color="link" size="sm">
                      {payment.id}
                    </Button>
                  </td>
                  <td>{payment.customerId}</td>
                  <td>{payment.orderId}</td>
                  <td>{payment.amount}</td>
                  <td>
                    <Translate contentKey={`gatewayEngineericApp.paymentStatus.${payment.paymentStatus}`} />
                  </td>
                  <td>{payment.storeOwnerId}</td>
                  <td>{payment.webKey}</td>
                  <td>{payment.createdAt ? <TextFormat type="date" value={payment.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${payment.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${payment.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${payment.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="gatewayEngineericApp.invoicePayment.home.notFound">No Payments found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Payment;
