import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './top-banner-information.reducer';
import { ITopBannerInformation } from 'app/shared/model/top-banner-information.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TopBannerInformationUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const topBannerInformationEntity = useAppSelector(state => state.topBannerInformation.entity);
  const loading = useAppSelector(state => state.topBannerInformation.loading);
  const updating = useAppSelector(state => state.topBannerInformation.updating);
  const updateSuccess = useAppSelector(state => state.topBannerInformation.updateSuccess);
  const handleClose = () => {
    props.history.push('/top-banner-information');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.startBanner = convertDateTimeToServer(values.startBanner);
    values.endBanner = convertDateTimeToServer(values.endBanner);

    const entity = {
      ...topBannerInformationEntity,
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
          startBanner: displayDefaultDateTime(),
          endBanner: displayDefaultDateTime(),
        }
      : {
          ...topBannerInformationEntity,
          startBanner: convertDateTimeFromServer(topBannerInformationEntity.startBanner),
          endBanner: convertDateTimeFromServer(topBannerInformationEntity.endBanner),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gatewayEngineericApp.topBannerInformation.home.createOrEditLabel" data-cy="TopBannerInformationCreateUpdateHeading">
            <Translate contentKey="gatewayEngineericApp.topBannerInformation.home.createOrEditLabel">
              Create or edit a TopBannerInformation
            </Translate>
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
                  id="top-banner-information-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('gatewayEngineericApp.topBannerInformation.enableBanner')}
                id="top-banner-information-enableBanner"
                name="enableBanner"
                data-cy="enableBanner"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.topBannerInformation.bannerText')}
                id="top-banner-information-bannerText"
                name="bannerText"
                data-cy="bannerText"
                type="text"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.topBannerInformation.startBanner')}
                id="top-banner-information-startBanner"
                name="startBanner"
                data-cy="startBanner"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.topBannerInformation.endBanner')}
                id="top-banner-information-endBanner"
                name="endBanner"
                data-cy="endBanner"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.topBannerInformation.webKey')}
                id="top-banner-information-webKey"
                name="webKey"
                data-cy="webKey"
                type="text"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.topBannerInformation.storeOwnerId')}
                id="top-banner-information-storeOwnerId"
                name="storeOwnerId"
                data-cy="storeOwnerId"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/top-banner-information" replace color="info">
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

export default TopBannerInformationUpdate;
