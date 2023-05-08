import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-collect.reducer';

export const UserCollectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userCollectEntity = useAppSelector(state => state.userCollect.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userCollectDetailsHeading">
          <Translate contentKey="v4App.userCollect.detail.title">UserCollect</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{userCollectEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="v4App.userCollect.name">Name</Translate>
            </span>
          </dt>
          <dd>{userCollectEntity.name}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="v4App.userCollect.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{userCollectEntity.lastName}</dd>
          <dt>
            <span id="username">
              <Translate contentKey="v4App.userCollect.username">Username</Translate>
            </span>
          </dt>
          <dd>{userCollectEntity.username}</dd>
          <dt>
            <span id="vitalKey">
              <Translate contentKey="v4App.userCollect.vitalKey">Vital Key</Translate>
            </span>
          </dt>
          <dd>{userCollectEntity.vitalKey}</dd>
          <dt>
            <Translate contentKey="v4App.userCollect.wearable">Wearable</Translate>
          </dt>
          <dd>{userCollectEntity.wearable ? userCollectEntity.wearable.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-collect" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-collect/${userCollectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserCollectDetail;
