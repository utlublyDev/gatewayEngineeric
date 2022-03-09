import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities, reset } from './top-banner-information.reducer';
import { ITopBannerInformation } from 'app/shared/model/top-banner-information.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TopBannerInformation = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const topBannerInformationList = useAppSelector(state => state.topBannerInformation.entities);
  const loading = useAppSelector(state => state.topBannerInformation.loading);
  const totalItems = useAppSelector(state => state.topBannerInformation.totalItems);
  const links = useAppSelector(state => state.topBannerInformation.links);
  const entity = useAppSelector(state => state.topBannerInformation.entity);
  const updateSuccess = useAppSelector(state => state.topBannerInformation.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="top-banner-information-heading" data-cy="TopBannerInformationHeading">
        <Translate contentKey="gatewayEngineericApp.topBannerInformation.home.title">Top Banner Informations</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="gatewayEngineericApp.topBannerInformation.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="gatewayEngineericApp.topBannerInformation.home.createLabel">Create new Top Banner Information</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={topBannerInformationList ? topBannerInformationList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {topBannerInformationList && topBannerInformationList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="gatewayEngineericApp.topBannerInformation.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('enableBanner')}>
                    <Translate contentKey="gatewayEngineericApp.topBannerInformation.enableBanner">Enable Banner</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('bannerText')}>
                    <Translate contentKey="gatewayEngineericApp.topBannerInformation.bannerText">Banner Text</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('startBanner')}>
                    <Translate contentKey="gatewayEngineericApp.topBannerInformation.startBanner">Start Banner</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('endBanner')}>
                    <Translate contentKey="gatewayEngineericApp.topBannerInformation.endBanner">End Banner</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('webKey')}>
                    <Translate contentKey="gatewayEngineericApp.topBannerInformation.webKey">Web Key</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('storeOwnerId')}>
                    <Translate contentKey="gatewayEngineericApp.topBannerInformation.storeOwnerId">Store Owner Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {topBannerInformationList.map((topBannerInformation, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`${match.url}/${topBannerInformation.id}`} color="link" size="sm">
                        {topBannerInformation.id}
                      </Button>
                    </td>
                    <td>{topBannerInformation.enableBanner ? 'true' : 'false'}</td>
                    <td>{topBannerInformation.bannerText}</td>
                    <td>
                      {topBannerInformation.startBanner ? (
                        <TextFormat type="date" value={topBannerInformation.startBanner} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {topBannerInformation.endBanner ? (
                        <TextFormat type="date" value={topBannerInformation.endBanner} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{topBannerInformation.webKey}</td>
                    <td>{topBannerInformation.storeOwnerId}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`${match.url}/${topBannerInformation.id}`}
                          color="info"
                          size="sm"
                          data-cy="entityDetailsButton"
                        >
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`${match.url}/${topBannerInformation.id}/edit`}
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
                          to={`${match.url}/${topBannerInformation.id}/delete`}
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
                <Translate contentKey="gatewayEngineericApp.topBannerInformation.home.notFound">No Top Banner Informations found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default TopBannerInformation;
