import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './product-category.reducer';
import { IProductCategory } from 'app/shared/model/product/product-category.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ProductCategory = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const productCategoryList = useAppSelector(state => state.productCategory.entities);
  const loading = useAppSelector(state => state.productCategory.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="product-category-heading" data-cy="ProductCategoryHeading">
        <Translate contentKey="gatewayEngineericApp.productProductCategory.home.title">Product Categories</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="gatewayEngineericApp.productProductCategory.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="gatewayEngineericApp.productProductCategory.home.createLabel">Create new Product Category</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {productCategoryList && productCategoryList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="gatewayEngineericApp.productProductCategory.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.productProductCategory.userStoreOwnerId">User Store Owner Id</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.productProductCategory.productCategoryName">Product Category Name</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.productProductCategory.productCategoryNameInArabic">
                    Product Category Name In Arabic
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.productProductCategory.productCategoryDescription">
                    Product Category Description
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.productProductCategory.productCategoryDescriptionInArabic">
                    Product Category Description In Arabic
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.productProductCategory.sortOrder">Sort Order</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.productProductCategory.dateAdded">Date Added</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.productProductCategory.dateModified">Date Modified</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.productProductCategory.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.productProductCategory.webKey">Web Key</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.productProductCategory.imageUrl">Image Url</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.productProductCategory.product">Product</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {productCategoryList.map((productCategory, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${productCategory.id}`} color="link" size="sm">
                      {productCategory.id}
                    </Button>
                  </td>
                  <td>{productCategory.userStoreOwnerId}</td>
                  <td>{productCategory.productCategoryName}</td>
                  <td>{productCategory.productCategoryNameInArabic}</td>
                  <td>{productCategory.productCategoryDescription}</td>
                  <td>{productCategory.productCategoryDescriptionInArabic}</td>
                  <td>{productCategory.sortOrder}</td>
                  <td>
                    {productCategory.dateAdded ? (
                      <TextFormat type="date" value={productCategory.dateAdded} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {productCategory.dateModified ? (
                      <TextFormat type="date" value={productCategory.dateModified} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    <Translate contentKey={`gatewayEngineericApp.CategoryStatus.${productCategory.status}`} />
                  </td>
                  <td>{productCategory.webKey}</td>
                  <td>{productCategory.imageUrl}</td>
                  <td>
                    {productCategory.products
                      ? productCategory.products.map((val, j) => (
                          <span key={j}>
                            <Link to={`product/${val.id}`}>{val.productName}</Link>
                            {j === productCategory.products.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${productCategory.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${productCategory.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${productCategory.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="gatewayEngineericApp.productProductCategory.home.notFound">No Product Categories found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ProductCategory;
