import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './product.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ProductDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const productEntity = useAppSelector(state => state.product.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productDetailsHeading">
          <Translate contentKey="gatewayEngineericApp.productProduct.detail.title">Product</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{productEntity.id}</dd>
          <dt>
            <span id="userStoreOwnerId">
              <Translate contentKey="gatewayEngineericApp.productProduct.userStoreOwnerId">User Store Owner Id</Translate>
            </span>
          </dt>
          <dd>{productEntity.userStoreOwnerId}</dd>
          <dt>
            <span id="productName">
              <Translate contentKey="gatewayEngineericApp.productProduct.productName">Product Name</Translate>
            </span>
          </dt>
          <dd>{productEntity.productName}</dd>
          <dt>
            <span id="productNameInArabic">
              <Translate contentKey="gatewayEngineericApp.productProduct.productNameInArabic">Product Name In Arabic</Translate>
            </span>
          </dt>
          <dd>{productEntity.productNameInArabic}</dd>
          <dt>
            <span id="productDescription">
              <Translate contentKey="gatewayEngineericApp.productProduct.productDescription">Product Description</Translate>
            </span>
          </dt>
          <dd>{productEntity.productDescription}</dd>
          <dt>
            <span id="productDescriptionInArabic">
              <Translate contentKey="gatewayEngineericApp.productProduct.productDescriptionInArabic">
                Product Description In Arabic
              </Translate>
            </span>
          </dt>
          <dd>{productEntity.productDescriptionInArabic}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="gatewayEngineericApp.productProduct.price">Price</Translate>
            </span>
          </dt>
          <dd>{productEntity.price}</dd>
          <dt>
            <span id="image">
              <Translate contentKey="gatewayEngineericApp.productProduct.image">Image</Translate>
            </span>
          </dt>
          <dd>
            {productEntity.image ? (
              <div>
                {productEntity.imageContentType ? (
                  <a onClick={openFile(productEntity.imageContentType, productEntity.image)}>
                    <img src={`data:${productEntity.imageContentType};base64,${productEntity.image}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {productEntity.imageContentType}, {byteSize(productEntity.image)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="imageUrl">
              <Translate contentKey="gatewayEngineericApp.productProduct.imageUrl">Image Url</Translate>
            </span>
          </dt>
          <dd>{productEntity.imageUrl}</dd>
          <dt>
            <span id="keywords">
              <Translate contentKey="gatewayEngineericApp.productProduct.keywords">Keywords</Translate>
            </span>
          </dt>
          <dd>{productEntity.keywords}</dd>
          <dt>
            <span id="dateAdded">
              <Translate contentKey="gatewayEngineericApp.productProduct.dateAdded">Date Added</Translate>
            </span>
          </dt>
          <dd>
            {productEntity.dateAdded ? <TextFormat value={productEntity.dateAdded} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="dateModified">
              <Translate contentKey="gatewayEngineericApp.productProduct.dateModified">Date Modified</Translate>
            </span>
          </dt>
          <dd>
            {productEntity.dateModified ? (
              <TextFormat value={productEntity.dateModified} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/product" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/product/${productEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductDetail;
