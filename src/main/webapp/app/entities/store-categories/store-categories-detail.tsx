import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './store-categories.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StoreCategoriesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const storeCategoriesEntity = useAppSelector(state => state.storeCategories.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="storeCategoriesDetailsHeading">
          <Translate contentKey="gatewayEngineericApp.storeCategories.detail.title">StoreCategories</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{storeCategoriesEntity.id}</dd>
          <dt>
            <span id="userStoreOwnerId">
              <Translate contentKey="gatewayEngineericApp.storeCategories.userStoreOwnerId">User Store Owner Id</Translate>
            </span>
          </dt>
          <dd>{storeCategoriesEntity.userStoreOwnerId}</dd>
          <dt>
            <span id="categoryName">
              <Translate contentKey="gatewayEngineericApp.storeCategories.categoryName">Category Name</Translate>
            </span>
          </dt>
          <dd>{storeCategoriesEntity.categoryName}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="gatewayEngineericApp.storeCategories.description">Description</Translate>
            </span>
          </dt>
          <dd>{storeCategoriesEntity.description}</dd>
          <dt>
            <span id="categoryNameInArabic">
              <Translate contentKey="gatewayEngineericApp.storeCategories.categoryNameInArabic">Category Name In Arabic</Translate>
            </span>
          </dt>
          <dd>{storeCategoriesEntity.categoryNameInArabic}</dd>
          <dt>
            <span id="descriptionInArabic">
              <Translate contentKey="gatewayEngineericApp.storeCategories.descriptionInArabic">Description In Arabic</Translate>
            </span>
          </dt>
          <dd>{storeCategoriesEntity.descriptionInArabic}</dd>
          <dt>
            <span id="userStoreOwnerIdImageUrl">
              <Translate contentKey="gatewayEngineericApp.storeCategories.userStoreOwnerIdImageUrl">
                User Store Owner Id Image Url
              </Translate>
            </span>
          </dt>
          <dd>{storeCategoriesEntity.userStoreOwnerIdImageUrl}</dd>
        </dl>
        <Button tag={Link} to="/store-categories" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/store-categories/${storeCategoriesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StoreCategoriesDetail;
