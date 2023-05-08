import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UserCollect from './user-collect';
import UserCollectDetail from './user-collect-detail';
import UserCollectUpdate from './user-collect-update';
import UserCollectDeleteDialog from './user-collect-delete-dialog';

const UserCollectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UserCollect />} />
    <Route path="new" element={<UserCollectUpdate />} />
    <Route path=":id">
      <Route index element={<UserCollectDetail />} />
      <Route path="edit" element={<UserCollectUpdate />} />
      <Route path="delete" element={<UserCollectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UserCollectRoutes;
