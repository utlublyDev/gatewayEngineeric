import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './store-owner.reducer';
import { IStoreOwner } from 'app/shared/model/store-owner.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StoreOwner = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const storeOwnerList = useAppSelector(state => state.storeOwner.entities);
  const loading = useAppSelector(state => state.storeOwner.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="store-owner-heading" data-cy="StoreOwnerHeading">
        <Translate contentKey="gatewayEngineericApp.storeOwner.home.title">Store Owners</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="gatewayEngineericApp.storeOwner.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="gatewayEngineericApp.storeOwner.home.createLabel">Create new Store Owner</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {storeOwnerList && storeOwnerList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeOwner.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeOwner.userStoreOwnerId">User Store Owner Id</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeOwner.storeName">Store Name</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeOwner.storeNameInArabic">Store Name In Arabic</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeOwner.address">Address</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeOwner.addressInArabic">Address In Arabic</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeOwner.longitude">Longitude</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeOwner.latitude">Latitude</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeOwner.isBusy">Is Busy</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeOwner.city">City</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeOwner.cityInArabic">City In Arabic</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeOwner.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeOwner.descriptionInArabic">Description In Arabic</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeOwner.storeContactNumber">Store Contact Number</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeOwner.createdDate">Created Date</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeOwner.storeLogoUrl">Store Logo Url</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeOwner.isActive">Is Active</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeOwner.hasDelivery">Has Delivery</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeOwner.hasFreeDelivery">Has Free Delivery</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeOwner.availableDateTime">Available Date Time</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeOwner.shopOpeiningTime">Shop Opeining Time</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeOwner.shopClosingTime">Shop Closing Time</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeOwner.currency">Currency</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeOwner.deliveryCost">Delivery Cost</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {storeOwnerList.map((storeOwner, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${storeOwner.id}`} color="link" size="sm">
                      {storeOwner.id}
                    </Button>
                  </td>
                  <td>{storeOwner.userStoreOwnerId}</td>
                  <td>{storeOwner.storeName}</td>
                  <td>{storeOwner.storeNameInArabic}</td>
                  <td>{storeOwner.address}</td>
                  <td>{storeOwner.addressInArabic}</td>
                  <td>{storeOwner.longitude}</td>
                  <td>{storeOwner.latitude}</td>
                  <td>{storeOwner.isBusy ? 'true' : 'false'}</td>
                  <td>{storeOwner.city}</td>
                  <td>{storeOwner.cityInArabic}</td>
                  <td>{storeOwner.description}</td>
                  <td>{storeOwner.descriptionInArabic}</td>
                  <td>{storeOwner.storeContactNumber}</td>
                  <td>
                    {storeOwner.createdDate ? <TextFormat type="date" value={storeOwner.createdDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{storeOwner.storeLogoUrl}</td>
                  <td>{storeOwner.isActive ? 'true' : 'false'}</td>
                  <td>{storeOwner.hasDelivery ? 'true' : 'false'}</td>
                  <td>{storeOwner.hasFreeDelivery ? 'true' : 'false'}</td>
                  <td>
                    {storeOwner.availableDateTime ? (
                      <TextFormat type="date" value={storeOwner.availableDateTime} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {storeOwner.shopOpeiningTime ? (
                      <TextFormat type="date" value={storeOwner.shopOpeiningTime} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {storeOwner.shopClosingTime ? (
                      <TextFormat type="date" value={storeOwner.shopClosingTime} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{storeOwner.currency}</td>
                  <td>{storeOwner.deliveryCost}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${storeOwner.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${storeOwner.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${storeOwner.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="gatewayEngineericApp.storeOwner.home.notFound">No Store Owners found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default StoreOwner;
