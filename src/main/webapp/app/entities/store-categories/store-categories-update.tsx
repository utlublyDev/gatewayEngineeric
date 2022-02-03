import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './store-categories.reducer';
import { IStoreCategories } from 'app/shared/model/store-categories.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StoreCategoriesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const storeCategoriesEntity = useAppSelector(state => state.storeCategories.entity);
  const loading = useAppSelector(state => state.storeCategories.loading);
  const updating = useAppSelector(state => state.storeCategories.updating);
  const updateSuccess = useAppSelector(state => state.storeCategories.updateSuccess);
  const handleClose = () => {
    props.history.push('/store-categories');
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
    const entity = {
      ...storeCategoriesEntity,
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
      ? {}
      : {
          ...storeCategoriesEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gatewayEngineericApp.storeCategories.home.createOrEditLabel" data-cy="StoreCategoriesCreateUpdateHeading">
            <Translate contentKey="gatewayEngineericApp.storeCategories.home.createOrEditLabel">Create or edit a StoreCategories</Translate>
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
                  id="store-categories-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('gatewayEngineericApp.storeCategories.userStoreOwnerId')}
                id="store-categories-userStoreOwnerId"
                name="userStoreOwnerId"
                data-cy="userStoreOwnerId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeCategories.categoryName')}
                id="store-categories-categoryName"
                name="categoryName"
                data-cy="categoryName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeCategories.description')}
                id="store-categories-description"
                name="description"
                data-cy="description"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeCategories.categoryNameInArabic')}
                id="store-categories-categoryNameInArabic"
                name="categoryNameInArabic"
                data-cy="categoryNameInArabic"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeCategories.descriptionInArabic')}
                id="store-categories-descriptionInArabic"
                name="descriptionInArabic"
                data-cy="descriptionInArabic"
                type="text"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeCategories.userStoreOwnerIdImageUrl')}
                id="store-categories-userStoreOwnerIdImageUrl"
                name="userStoreOwnerIdImageUrl"
                data-cy="userStoreOwnerIdImageUrl"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/store-categories" replace color="info">
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

export default StoreCategoriesUpdate;
