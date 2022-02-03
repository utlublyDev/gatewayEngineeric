import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './product-category.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ProductCategoryDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const productCategoryEntity = useAppSelector(state => state.productCategory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productCategoryDetailsHeading">
          <Translate contentKey="gatewayEngineericApp.productProductCategory.detail.title">ProductCategory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{productCategoryEntity.id}</dd>
          <dt>
            <span id="userStoreOwnerId">
              <Translate contentKey="gatewayEngineericApp.productProductCategory.userStoreOwnerId">User Store Owner Id</Translate>
            </span>
          </dt>
          <dd>{productCategoryEntity.userStoreOwnerId}</dd>
          <dt>
            <span id="productCategoryName">
              <Translate contentKey="gatewayEngineericApp.productProductCategory.productCategoryName">Product Category Name</Translate>
            </span>
          </dt>
          <dd>{productCategoryEntity.productCategoryName}</dd>
          <dt>
            <span id="productCategoryNameInArabic">
              <Translate contentKey="gatewayEngineericApp.productProductCategory.productCategoryNameInArabic">
                Product Category Name In Arabic
              </Translate>
            </span>
          </dt>
          <dd>{productCategoryEntity.productCategoryNameInArabic}</dd>
          <dt>
            <span id="productCategoryDescription">
              <Translate contentKey="gatewayEngineericApp.productProductCategory.productCategoryDescription">
                Product Category Description
              </Translate>
            </span>
          </dt>
          <dd>{productCategoryEntity.productCategoryDescription}</dd>
          <dt>
            <span id="productCategoryDescriptionInArabic">
              <Translate contentKey="gatewayEngineericApp.productProductCategory.productCategoryDescriptionInArabic">
                Product Category Description In Arabic
              </Translate>
            </span>
          </dt>
          <dd>{productCategoryEntity.productCategoryDescriptionInArabic}</dd>
          <dt>
            <span id="sortOrder">
              <Translate contentKey="gatewayEngineericApp.productProductCategory.sortOrder">Sort Order</Translate>
            </span>
          </dt>
          <dd>{productCategoryEntity.sortOrder}</dd>
          <dt>
            <span id="dateAdded">
              <Translate contentKey="gatewayEngineericApp.productProductCategory.dateAdded">Date Added</Translate>
            </span>
          </dt>
          <dd>
            {productCategoryEntity.dateAdded ? (
              <TextFormat value={productCategoryEntity.dateAdded} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dateModified">
              <Translate contentKey="gatewayEngineericApp.productProductCategory.dateModified">Date Modified</Translate>
            </span>
          </dt>
          <dd>
            {productCategoryEntity.dateModified ? (
              <TextFormat value={productCategoryEntity.dateModified} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="status">
              <Translate contentKey="gatewayEngineericApp.productProductCategory.status">Status</Translate>
            </span>
          </dt>
          <dd>{productCategoryEntity.status}</dd>
          <dt>
            <Translate contentKey="gatewayEngineericApp.productProductCategory.product">Product</Translate>
          </dt>
          <dd>
            {productCategoryEntity.products
              ? productCategoryEntity.products.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.productName}</a>
                    {productCategoryEntity.products && i === productCategoryEntity.products.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/product-category" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/product-category/${productCategoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductCategoryDetail;
