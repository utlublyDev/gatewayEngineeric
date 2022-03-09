import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './payment.reducer';
import { IPayment } from 'app/shared/model/invoice/payment.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { paymentStatus } from 'app/shared/model/enumerations/payment-status.model';

export const PaymentUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const paymentEntity = useAppSelector(state => state.payment.entity);
  const loading = useAppSelector(state => state.payment.loading);
  const updating = useAppSelector(state => state.payment.updating);
  const updateSuccess = useAppSelector(state => state.payment.updateSuccess);
  const paymentStatusValues = Object.keys(paymentStatus);
  const handleClose = () => {
    props.history.push('/payment');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdAt = convertDateTimeToServer(values.createdAt);

    const entity = {
      ...paymentEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          createdAt: displayDefaultDateTime(),
        }
      : {
          paymentStatus: 'DONE',
          ...paymentEntity,
          createdAt: convertDateTimeFromServer(paymentEntity.createdAt),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gatewayEngineericApp.invoicePayment.home.createOrEditLabel" data-cy="PaymentCreateUpdateHeading">
            <Translate contentKey="gatewayEngineericApp.invoicePayment.home.createOrEditLabel">Create or edit a Payment</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="payment-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('gatewayEngineericApp.invoicePayment.customerId')}
                id="payment-customerId"
                name="customerId"
                data-cy="customerId"
                type="text"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.invoicePayment.orderId')}
                id="payment-orderId"
                name="orderId"
                data-cy="orderId"
                type="text"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.invoicePayment.amount')}
                id="payment-amount"
                name="amount"
                data-cy="amount"
                type="text"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.invoicePayment.paymentStatus')}
                id="payment-paymentStatus"
                name="paymentStatus"
                data-cy="paymentStatus"
                type="select"
              >
                {paymentStatusValues.map(paymentStatus => (
                  <option value={paymentStatus} key={paymentStatus}>
                    {translate('gatewayEngineericApp.paymentStatus.' + paymentStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('gatewayEngineericApp.invoicePayment.storeOwnerId')}
                id="payment-storeOwnerId"
                name="storeOwnerId"
                data-cy="storeOwnerId"
                type="text"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.invoicePayment.webKey')}
                id="payment-webKey"
                name="webKey"
                data-cy="webKey"
                type="text"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.invoicePayment.createdAt')}
                id="payment-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/payment" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PaymentUpdate;
