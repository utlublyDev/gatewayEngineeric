import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './store-adv-banner.reducer';
import { IStoreAdvBanner } from 'app/shared/model/store-adv-banner.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StoreAdvBanner = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const storeAdvBannerList = useAppSelector(state => state.storeAdvBanner.entities);
  const loading = useAppSelector(state => state.storeAdvBanner.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="store-adv-banner-heading" data-cy="StoreAdvBannerHeading">
        <Translate contentKey="gatewayEngineericApp.storeAdvBanner.home.title">Store Adv Banners</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="gatewayEngineericApp.storeAdvBanner.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="gatewayEngineericApp.storeAdvBanner.home.createLabel">Create new Store Adv Banner</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {storeAdvBannerList && storeAdvBannerList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeAdvBanner.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeAdvBanner.storeOwnerId">Store Owner Id</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeAdvBanner.webKey">Web Key</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayEngineericApp.storeAdvBanner.imageUrl">Image Url</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {storeAdvBannerList.map((storeAdvBanner, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${storeAdvBanner.id}`} color="link" size="sm">
                      {storeAdvBanner.id}
                    </Button>
                  </td>
                  <td>{storeAdvBanner.storeOwnerId}</td>
                  <td>{storeAdvBanner.webKey}</td>
                  <td>{storeAdvBanner.imageUrl}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${storeAdvBanner.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${storeAdvBanner.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${storeAdvBanner.id}/delete`}
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
              <Translate contentKey="gatewayEngineericApp.storeAdvBanner.home.notFound">No Store Adv Banners found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default StoreAdvBanner;
