import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDataVital } from 'app/shared/model/data-vital.model';
import { getEntities } from './data-vital.reducer';

export const DataVital = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const dataVitalList = useAppSelector(state => state.dataVital.entities);
  const loading = useAppSelector(state => state.dataVital.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="data-vital-heading" data-cy="DataVitalHeading">
        <Translate contentKey="v4App.dataVital.home.title">Data Vitals</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="v4App.dataVital.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/data-vital/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="v4App.dataVital.home.createLabel">Create new Data Vital</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {dataVitalList && dataVitalList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="v4App.dataVital.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="v4App.dataVital.vitalKey">Vital Key</Translate>
                </th>
                <th>
                  <Translate contentKey="v4App.dataVital.eventType">Event Type</Translate>
                </th>
                <th>
                  <Translate contentKey="v4App.dataVital.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="v4App.dataVital.data">Data</Translate>
                </th>
                <th>
                  <Translate contentKey="v4App.dataVital.wearable">Wearable</Translate>
                </th>
                <th>
                  <Translate contentKey="v4App.dataVital.userCollect">User Collect</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {dataVitalList.map((dataVital, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/data-vital/${dataVital.id}`} color="link" size="sm">
                      {dataVital.id}
                    </Button>
                  </td>
                  <td>{dataVital.vitalKey}</td>
                  <td>{dataVital.eventType}</td>
                  <td>{dataVital.date ? <TextFormat type="date" value={dataVital.date} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{dataVital.data}</td>
                  <td>{dataVital.wearable ? <Link to={`/wearable/${dataVital.wearable.id}`}>{dataVital.wearable.id}</Link> : ''}</td>
                  <td>
                    {dataVital.userCollect ? <Link to={`/user-collect/${dataVital.userCollect.id}`}>{dataVital.userCollect.id}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/data-vital/${dataVital.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/data-vital/${dataVital.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/data-vital/${dataVital.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="v4App.dataVital.home.notFound">No Data Vitals found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default DataVital;
