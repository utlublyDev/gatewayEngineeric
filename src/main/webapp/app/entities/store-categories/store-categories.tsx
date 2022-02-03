import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './store-categories.reducer';
import { IStoreCategories } from 'app/shared/model/store-categories.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StoreCategories = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const storeCategoriesList = useAppSelector(state => state.storeCategories.entities);
  const loading = useAppSelector(state => state.storeCategories.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="store-categories-heading" data-cy="StoreCategoriesHeading">
        <Translate contentKey="gatewayEngineericApp.storeCategories.home.title">Store Categories</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="gatewayEngineericApp.storeCategories.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="gatewayEngineericApp.storeCategories.home.createLabel">Create new Store Categories</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {storeCategoriesList && storeCategoriesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeCategories.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeCategories.userStoreOwnerId">User Store Owner Id</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeCategories.categoryName">Category Name</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeCategories.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeCategories.categoryNameInArabic">Category Name In Arabic</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeCategories.descriptionInArabic">Description In Arabic</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeCategories.userStoreOwnerIdImageUrl">
                    User Store Owner Id Image Url
                  </Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {storeCategoriesList.map((storeCategories, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${storeCategories.id}`} color="link" size="sm">
                      {storeCategories.id}
                    </Button>
                  </td>
                  <td>{storeCategories.userStoreOwnerId}</td>
                  <td>{storeCategories.categoryName}</td>
                  <td>{storeCategories.description}</td>
                  <td>{storeCategories.categoryNameInArabic}</td>
                  <td>{storeCategories.descriptionInArabic}</td>
                  <td>{storeCategories.userStoreOwnerIdImageUrl}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${storeCategories.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${storeCategories.id}/edit`}
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
                        to={`${match.url}/${storeCategories.id}/delete`}
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
              <Translate contentKey="gatewayEngineericApp.storeCategories.home.notFound">No Store Categories found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default StoreCategories;
