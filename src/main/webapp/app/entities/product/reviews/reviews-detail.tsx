import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './reviews.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ReviewsDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const reviewsEntity = useAppSelector(state => state.reviews.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="reviewsDetailsHeading">
          <Translate contentKey="gatewayEngineericApp.productReviews.detail.title">Reviews</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{reviewsEntity.id}</dd>
          <dt>
            <span id="customerId">
              <Translate contentKey="gatewayEngineericApp.productReviews.customerId">Customer Id</Translate>
            </span>
          </dt>
          <dd>{reviewsEntity.customerId}</dd>
          <dt>
            <span id="orderId">
              <Translate contentKey="gatewayEngineericApp.productReviews.orderId">Order Id</Translate>
            </span>
          </dt>
          <dd>{reviewsEntity.orderId}</dd>
          <dt>
            <span id="review">
              <Translate contentKey="gatewayEngineericApp.productReviews.review">Review</Translate>
            </span>
          </dt>
          <dd>{reviewsEntity.review}</dd>
          <dt>
            <span id="rating">
              <Translate contentKey="gatewayEngineericApp.productReviews.rating">Rating</Translate>
            </span>
          </dt>
          <dd>{reviewsEntity.rating}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="gatewayEngineericApp.productReviews.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{reviewsEntity.createdAt ? <TextFormat value={reviewsEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="prodcutsId">
              <Translate contentKey="gatewayEngineericApp.productReviews.prodcutsId">Prodcuts Id</Translate>
            </span>
          </dt>
          <dd>{reviewsEntity.prodcutsId}</dd>
        </dl>
        <Button tag={Link} to="/reviews" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/reviews/${reviewsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ReviewsDetail;
