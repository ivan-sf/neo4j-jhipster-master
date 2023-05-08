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
import { getEntity, updateEntity, createEntity, reset } from './user-collect.reducer';

export const UserCollectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const wearables = useAppSelector(state => state.wearable.entities);
  const userCollectEntity = useAppSelector(state => state.userCollect.entity);
  const loading = useAppSelector(state => state.userCollect.loading);
  const updating = useAppSelector(state => state.userCollect.updating);
  const updateSuccess = useAppSelector(state => state.userCollect.updateSuccess);

  const handleClose = () => {
    navigate('/user-collect');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getWearables({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...userCollectEntity,
      ...values,
      wearable: wearables.find(it => it.id.toString() === values.wearable.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...userCollectEntity,
          wearable: userCollectEntity?.wearable?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="v4App.userCollect.home.createOrEditLabel" data-cy="UserCollectCreateUpdateHeading">
            <Translate contentKey="v4App.userCollect.home.createOrEditLabel">Create or edit a UserCollect</Translate>
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
                  id="user-collect-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('v4App.userCollect.name')} id="user-collect-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('v4App.userCollect.lastName')}
                id="user-collect-lastName"
                name="lastName"
                data-cy="lastName"
                type="text"
              />
              <ValidatedField
                label={translate('v4App.userCollect.username')}
                id="user-collect-username"
                name="username"
                data-cy="username"
                type="text"
              />
              <ValidatedField
                label={translate('v4App.userCollect.vitalKey')}
                id="user-collect-vitalKey"
                name="vitalKey"
                data-cy="vitalKey"
                type="text"
              />
              <ValidatedField
                id="user-collect-wearable"
                name="wearable"
                data-cy="wearable"
                label={translate('v4App.userCollect.wearable')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/user-collect" replace color="info">
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

export default UserCollectUpdate;
