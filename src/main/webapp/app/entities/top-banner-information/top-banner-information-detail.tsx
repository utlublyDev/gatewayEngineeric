import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './top-banner-information.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TopBannerInformationDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const topBannerInformationEntity = useAppSelector(state => state.topBannerInformation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="topBannerInformationDetailsHeading">
          <Translate contentKey="gatewayEngineericApp.topBannerInformation.detail.title">TopBannerInformation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{topBannerInformationEntity.id}</dd>
          <dt>
            <span id="enableBanner">
              <Translate contentKey="gatewayEngineericApp.topBannerInformation.enableBanner">Enable Banner</Translate>
            </span>
          </dt>
          <dd>{topBannerInformationEntity.enableBanner ? 'true' : 'false'}</dd>
          <dt>
            <span id="bannerText">
              <Translate contentKey="gatewayEngineericApp.topBannerInformation.bannerText">Banner Text</Translate>
            </span>
          </dt>
          <dd>{topBannerInformationEntity.bannerText}</dd>
          <dt>
            <span id="startBanner">
              <Translate contentKey="gatewayEngineericApp.topBannerInformation.startBanner">Start Banner</Translate>
            </span>
          </dt>
          <dd>
            {topBannerInformationEntity.startBanner ? (
              <TextFormat value={topBannerInformationEntity.startBanner} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endBanner">
              <Translate contentKey="gatewayEngineericApp.topBannerInformation.endBanner">End Banner</Translate>
            </span>
          </dt>
          <dd>
            {topBannerInformationEntity.endBanner ? (
              <TextFormat value={topBannerInformationEntity.endBanner} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="webKey">
              <Translate contentKey="gatewayEngineericApp.topBannerInformation.webKey">Web Key</Translate>
            </span>
          </dt>
          <dd>{topBannerInformationEntity.webKey}</dd>
          <dt>
            <span id="storeOwnerId">
              <Translate contentKey="gatewayEngineericApp.topBannerInformation.storeOwnerId">Store Owner Id</Translate>
            </span>
          </dt>
          <dd>{topBannerInformationEntity.storeOwnerId}</dd>
        </dl>
        <Button tag={Link} to="/top-banner-information" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/top-banner-information/${topBannerInformationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TopBannerInformationDetail;
