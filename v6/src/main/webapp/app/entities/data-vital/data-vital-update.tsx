import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWearable } from 'app/shared/model/wearable.model';
import { getEntities as getWearables } from 'app/entities/wearable/wearable.reducer';
import { IUserCollect } from 'app/shared/model/user-collect.model';
import { getEntities as getUserCollects } from 'app/entities/user-collect/user-collect.reducer';
import { IDataVital } from 'app/shared/model/data-vital.model';
import { getEntity, updateEntity, createEntity, reset } from './data-vital.reducer';

export const DataVitalUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const wearables = useAppSelector(state => state.wearable.entities);
  const userCollects = useAppSelector(state => state.userCollect.entities);
  const dataVitalEntity = useAppSelector(state => state.dataVital.entity);
  const loading = useAppSelector(state => state.dataVital.loading);
  const updating = useAppSelector(state => state.dataVital.updating);
  const updateSuccess = useAppSelector(state => state.dataVital.updateSuccess);

  const handleClose = () => {
    navigate('/data-vital');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getWearables({}));
    dispatch(getUserCollects({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.date = convertDateTimeToServer(values.date);

    const entity = {
      ...dataVitalEntity,
      ...values,
      wearable: wearables.find(it => it.id.toString() === values.wearable.toString()),
      userCollect: userCollects.find(it => it.id.toString() === values.userCollect.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          date: displayDefaultDateTime(),
        }
      : {
          ...dataVitalEntity,
          date: convertDateTimeFromServer(dataVitalEntity.date),
          wearable: dataVitalEntity?.wearable?.id,
          userCollect: dataVitalEntity?.userCollect?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="v4App.dataVital.home.createOrEditLabel" data-cy="DataVitalCreateUpdateHeading">
            <Translate contentKey="v4App.dataVital.home.createOrEditLabel">Create or edit a DataVital</Translate>
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
                  id="data-vital-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('v4App.dataVital.vitalKey')}
                id="data-vital-vitalKey"
                name="vitalKey"
                data-cy="vitalKey"
                type="text"
              />
              <ValidatedField
                label={translate('v4App.dataVital.eventType')}
                id="data-vital-eventType"
                name="eventType"
                data-cy="eventType"
                type="text"
              />
              <ValidatedField
                label={translate('v4App.dataVital.date')}
                id="data-vital-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label={translate('v4App.dataVital.data')} id="data-vital-data" name="data" data-cy="data" type="text" />
              <ValidatedField
                id="data-vital-wearable"
                name="wearable"
                data-cy="wearable"
                label={translate('v4App.dataVital.wearable')}
                type="select"
              >
                <option value="" key="0" />
                {wearables
                  ? wearables.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="data-vital-userCollect"
                name="userCollect"
                data-cy="userCollect"
                label={translate('v4App.dataVital.userCollect')}
                type="select"
              >
                <option value="" key="0" />
                {userCollects
                  ? userCollects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/data-vital" replace color="info">
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

export default DataVitalUpdate;
