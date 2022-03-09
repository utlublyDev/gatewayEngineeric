import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IProduct } from 'app/shared/model/product/product.model';
import { getEntities as getProducts } from 'app/entities/product/product/product.reducer';
import { getEntity, updateEntity, createEntity, reset } from './product-category.reducer';
import { IProductCategory } from 'app/shared/model/product/product-category.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { CategoryStatus } from 'app/shared/model/enumerations/category-status.model';

export const ProductCategoryUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const products = useAppSelector(state => state.product.entities);
  const productCategoryEntity = useAppSelector(state => state.productCategory.entity);
  const loading = useAppSelector(state => state.productCategory.loading);
  const updating = useAppSelector(state => state.productCategory.updating);
  const updateSuccess = useAppSelector(state => state.productCategory.updateSuccess);
  const categoryStatusValues = Object.keys(CategoryStatus);
  const handleClose = () => {
    props.history.push('/product-category');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getProducts({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...productCategoryEntity,
      ...values,
      products: mapIdList(values.products),
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
          status: 'AVAILABLE',
          ...productCategoryEntity,
          products: productCategoryEntity?.products?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gatewayEngineericApp.productProductCategory.home.createOrEditLabel" data-cy="ProductCategoryCreateUpdateHeading">
            <Translate contentKey="gatewayEngineericApp.productProductCategory.home.createOrEditLabel">
              Create or edit a ProductCategory
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
                  id="product-category-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('gatewayEngineericApp.productProductCategory.userStoreOwnerId')}
                id="product-category-userStoreOwnerId"
                name="userStoreOwnerId"
                data-cy="userStoreOwnerId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.productProductCategory.productCategoryName')}
                id="product-category-productCategoryName"
                name="productCategoryName"
                data-cy="productCategoryName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.productProductCategory.productCategoryNameInArabic')}
                id="product-category-productCategoryNameInArabic"
                name="productCategoryNameInArabic"
                data-cy="productCategoryNameInArabic"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.productProductCategory.productCategoryDescription')}
                id="product-category-productCategoryDescription"
                name="productCategoryDescription"
                data-cy="productCategoryDescription"
                type="text"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.productProductCategory.productCategoryDescriptionInArabic')}
                id="product-category-productCategoryDescriptionInArabic"
                name="productCategoryDescriptionInArabic"
                data-cy="productCategoryDescriptionInArabic"
                type="text"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.productProductCategory.sortOrder')}
                id="product-category-sortOrder"
                name="sortOrder"
                data-cy="sortOrder"
                type="text"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.productProductCategory.dateAdded')}
                id="product-category-dateAdded"
                name="dateAdded"
                data-cy="dateAdded"
                type="date"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.productProductCategory.dateModified')}
                id="product-category-dateModified"
                name="dateModified"
                data-cy="dateModified"
                type="date"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.productProductCategory.status')}
                id="product-category-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {categoryStatusValues.map(categoryStatus => (
                  <option value={categoryStatus} key={categoryStatus}>
                    {translate('gatewayEngineericApp.CategoryStatus.' + categoryStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('gatewayEngineericApp.productProductCategory.webKey')}
                id="product-category-webKey"
                name="webKey"
                data-cy="webKey"
                type="text"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.productProductCategory.imageUrl')}
                id="product-category-imageUrl"
                name="imageUrl"
                data-cy="imageUrl"
                type="text"
              />
              <ValidatedField
                label={translate('gatewayEngineericApp.productProductCategory.product')}
                id="product-category-product"
                data-cy="product"
                type="select"
                multiple
                name="products"
              >
                <option value="" key="0" />
                {products
                  ? products.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.productName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/product-category" replace color="info">
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

export default ProductCategoryUpdate;
