import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './slider-image-slider.reducer';
import { ISliderImageSlider } from 'app/shared/model/slider-image-slider.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SliderImageSliderUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const sliderImageSliderEntity = useAppSelector(state => state.sliderImageSlider.entity);
  const loading = useAppSelector(state => state.sliderImageSlider.loading);
  const updating = useAppSelector(state => state.sliderImageSlider.updating);
  const updateSuccess = useAppSelector(state => state.sliderImageSlider.updateSuccess);
  const handleClose = () => {
    props.history.push('/slider-image-slider' + props.location.search);
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
      ...sliderImageSliderEntity,
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
          ...sliderImageSliderEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gatewayEngineericApp.sliderImageSlider.home.createOrEditLabel" data-cy="SliderImageSliderCreateUpdateHeading">
            <Translate contentKey="gatewayEngineericApp.sliderImageSlider.home.createOrEditLabel">
              Create or edit a SliderImageSlider
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
                  id="slider-image-slider-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('gatewayEngineericApp.sliderImageSlider.storeOwnerId')}
                id="slider-image-slider-storeOwnerId"
                name="storeOwnerId"
                data-cy="storeOwnerId"
                type="text"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.sliderImageSlider.webKey')}
                id="slider-image-slider-webKey"
                name="webKey"
                data-cy="webKey"
                type="text"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.sliderImageSlider.imageUrl')}
                id="slider-image-slider-imageUrl"
                name="imageUrl"
                data-cy="imageUrl"
                type="text"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.sliderImageSlider.alt')}
                id="slider-image-slider-alt"
                name="alt"
                data-cy="alt"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/slider-image-slider" replace color="info">
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

export default SliderImageSliderUpdate;
