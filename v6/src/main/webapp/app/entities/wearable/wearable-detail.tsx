import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './wearable.reducer';

export const WearableDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const wearableEntity = useAppSelector(state => state.wearable.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="wearableDetailsHeading">
          <Translate contentKey="v4App.wearable.detail.title">Wearable</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{wearableEntity.id}</dd>
          <dt>
            <span id="nameWearable">
              <Translate contentKey="v4App.wearable.nameWearable">Name Wearable</Translate>
            </span>
          </dt>
          <dd>{wearableEntity.nameWearable}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="v4App.wearable.type">Type</Translate>
            </span>
          </dt>
          <dd>{wearableEntity.type}</dd>
        </dl>
        <Button tag={Link} to="/wearable" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/wearable/${wearableEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default WearableDetail;
