import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './store-owner.reducer';
import { IStoreOwner } from 'app/shared/model/store-owner.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StoreOwnerUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const storeOwnerEntity = useAppSelector(state => state.storeOwner.entity);
  const loading = useAppSelector(state => state.storeOwner.loading);
  const updating = useAppSelector(state => state.storeOwner.updating);
  const updateSuccess = useAppSelector(state => state.storeOwner.updateSuccess);
  const handleClose = () => {
    props.history.push('/store-owner');
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
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.availableDateTime = convertDateTimeToServer(values.availableDateTime);
    values.shopOpeiningTime = convertDateTimeToServer(values.shopOpeiningTime);
    values.shopClosingTime = convertDateTimeToServer(values.shopClosingTime);

    const entity = {
      ...storeOwnerEntity,
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
          createdDate: displayDefaultDateTime(),
          availableDateTime: displayDefaultDateTime(),
          shopOpeiningTime: displayDefaultDateTime(),
          shopClosingTime: displayDefaultDateTime(),
        }
      : {
          ...storeOwnerEntity,
          createdDate: convertDateTimeFromServer(storeOwnerEntity.createdDate),
          availableDateTime: convertDateTimeFromServer(storeOwnerEntity.availableDateTime),
          shopOpeiningTime: convertDateTimeFromServer(storeOwnerEntity.shopOpeiningTime),
          shopClosingTime: convertDateTimeFromServer(storeOwnerEntity.shopClosingTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gatewayEngineericApp.storeOwner.home.createOrEditLabel" data-cy="StoreOwnerCreateUpdateHeading">
            <Translate contentKey="gatewayEngineericApp.storeOwner.home.createOrEditLabel">Create or edit a StoreOwner</Translate>
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
                  id="store-owner-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('gatewayEngineericApp.storeOwner.userStoreOwnerId')}
                id="store-owner-userStoreOwnerId"
                name="userStoreOwnerId"
                data-cy="userStoreOwnerId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeOwner.storeName')}
                id="store-owner-storeName"
                name="storeName"
                data-cy="storeName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeOwner.storeNameInArabic')}
                id="store-owner-storeNameInArabic"
                name="storeNameInArabic"
                data-cy="storeNameInArabic"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeOwner.address')}
                id="store-owner-address"
                name="address"
                data-cy="address"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeOwner.addressInArabic')}
                id="store-owner-addressInArabic"
                name="addressInArabic"
                data-cy="addressInArabic"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeOwner.longitude')}
                id="store-owner-longitude"
                name="longitude"
                data-cy="longitude"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeOwner.latitude')}
                id="store-owner-latitude"
                name="latitude"
                data-cy="latitude"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeOwner.isBusy')}
                id="store-owner-isBusy"
                name="isBusy"
                data-cy="isBusy"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeOwner.city')}
                id="store-owner-city"
                name="city"
                data-cy="city"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeOwner.cityInArabic')}
                id="store-owner-cityInArabic"
                name="cityInArabic"
                data-cy="cityInArabic"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeOwner.description')}
                id="store-owner-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeOwner.descriptionInArabic')}
                id="store-owner-descriptionInArabic"
                name="descriptionInArabic"
                data-cy="descriptionInArabic"
                type="text"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeOwner.storeContactNumber')}
                id="store-owner-storeContactNumber"
                name="storeContactNumber"
                data-cy="storeContactNumber"
                type="text"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeOwner.createdDate')}
                id="store-owner-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeOwner.storeLogoUrl')}
                id="store-owner-storeLogoUrl"
                name="storeLogoUrl"
                data-cy="storeLogoUrl"
                type="text"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeOwner.isActive')}
                id="store-owner-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeOwner.hasDelivery')}
                id="store-owner-hasDelivery"
                name="hasDelivery"
                data-cy="hasDelivery"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeOwner.hasFreeDelivery')}
                id="store-owner-hasFreeDelivery"
                name="hasFreeDelivery"
                data-cy="hasFreeDelivery"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeOwner.availableDateTime')}
                id="store-owner-availableDateTime"
                name="availableDateTime"
                data-cy="availableDateTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeOwner.shopOpeiningTime')}
                id="store-owner-shopOpeiningTime"
                name="shopOpeiningTime"
                data-cy="shopOpeiningTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeOwner.shopClosingTime')}
                id="store-owner-shopClosingTime"
                name="shopClosingTime"
                data-cy="shopClosingTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeOwner.currency')}
                id="store-owner-currency"
                name="currency"
                data-cy="currency"
                type="text"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeOwner.deliveryCost')}
                id="store-owner-deliveryCost"
                name="deliveryCost"
                data-cy="deliveryCost"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/store-owner" replace color="info">
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

export default StoreOwnerUpdate;
