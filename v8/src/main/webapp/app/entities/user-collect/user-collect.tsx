import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUserCollect } from 'app/shared/model/user-collect.model';
import { getEntities } from './user-collect.reducer';

export const UserCollect = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const userCollectList = useAppSelector(state => state.userCollect.entities);
  const loading = useAppSelector(state => state.userCollect.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="user-collect-heading" data-cy="UserCollectHeading">
        <Translate contentKey="v4App.userCollect.home.title">User Collects</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="v4App.userCollect.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/user-collect/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="v4App.userCollect.home.createLabel">Create new User Collect</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {userCollectList && userCollectList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="v4App.userCollect.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="v4App.userCollect.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="v4App.userCollect.lastName">Last Name</Translate>
                </th>
                <th>
                  <Translate contentKey="v4App.userCollect.username">Username</Translate>
                </th>
                <th>
                  <Translate contentKey="v4App.userCollect.vitalKey">Vital Key</Translate>
                </th>
                <th>
                  <Translate contentKey="v4App.userCollect.wearable">Wearable</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {userCollectList.map((userCollect, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/user-collect/${userCollect.id}`} color="link" size="sm">
                      {userCollect.id}
                    </Button>
                  </td>
                  <td>{userCollect.name}</td>
                  <td>{userCollect.lastName}</td>
                  <td>{userCollect.username}</td>
                  <td>{userCollect.vitalKey}</td>
                  <td>{userCollect.wearable ? <Link to={`/wearable/${userCollect.wearable.id}`}>{userCollect.wearable.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/user-collect/${userCollect.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/user-collect/${userCollect.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/user-collect/${userCollect.id}/delete`}
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
              <Translate contentKey="v4App.userCollect.home.notFound">No User Collects found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default UserCollect;
