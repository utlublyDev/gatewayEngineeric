import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './store-adv-banner.reducer';
import { IStoreAdvBanner } from 'app/shared/model/store-adv-banner.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StoreAdvBannerUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const storeAdvBannerEntity = useAppSelector(state => state.storeAdvBanner.entity);
  const loading = useAppSelector(state => state.storeAdvBanner.loading);
  const updating = useAppSelector(state => state.storeAdvBanner.updating);
  const updateSuccess = useAppSelector(state => state.storeAdvBanner.updateSuccess);
  const handleClose = () => {
    props.history.push('/store-adv-banner');
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
      ...storeAdvBannerEntity,
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
          ...storeAdvBannerEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gatewayEngineericApp.storeAdvBanner.home.createOrEditLabel" data-cy="StoreAdvBannerCreateUpdateHeading">
            <Translate contentKey="gatewayEngineericApp.storeAdvBanner.home.createOrEditLabel">Create or edit a StoreAdvBanner</Translate>
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
                  id="store-adv-banner-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('gatewayEngineericApp.storeAdvBanner.storeOwnerId')}
                id="store-adv-banner-storeOwnerId"
                name="storeOwnerId"
                data-cy="storeOwnerId"
                type="text"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeAdvBanner.webKey')}
                id="store-adv-banner-webKey"
                name="webKey"
                data-cy="webKey"
                type="text"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.storeAdvBanner.imageUrl')}
                id="store-adv-banner-imageUrl"
                name="imageUrl"
                data-cy="imageUrl"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/store-adv-banner" replace color="info">
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

export default StoreAdvBannerUpdate;
