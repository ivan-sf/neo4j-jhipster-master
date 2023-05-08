import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './data-vital.reducer';

export const DataVitalDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const dataVitalEntity = useAppSelector(state => state.dataVital.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dataVitalDetailsHeading">
          <Translate contentKey="v4App.dataVital.detail.title">DataVital</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{dataVitalEntity.id}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="v4App.dataVital.date">Date</Translate>
            </span>
          </dt>
          <dd>{dataVitalEntity.date ? <TextFormat value={dataVitalEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="data">
              <Translate contentKey="v4App.dataVital.data">Data</Translate>
            </span>
          </dt>
          <dd>{dataVitalEntity.data}</dd>
          <dt>
            <span id="eventType">
              <Translate contentKey="v4App.dataVital.eventType">Event Type</Translate>
            </span>
          </dt>
          <dd>{dataVitalEntity.eventType}</dd>
          <dt>
            <span id="vitalKey">
              <Translate contentKey="v4App.dataVital.vitalKey">Vital Key</Translate>
            </span>
          </dt>
          <dd>{dataVitalEntity.vitalKey}</dd>
          <dt>
            <Translate contentKey="v4App.dataVital.wearable">Wearable</Translate>
          </dt>
          <dd>{dataVitalEntity.wearable ? dataVitalEntity.wearable.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/data-vital" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/data-vital/${dataVitalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DataVitalDetail;
