import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Wearable from './wearable';
import WearableDetail from './wearable-detail';
import WearableUpdate from './wearable-update';
import WearableDeleteDialog from './wearable-delete-dialog';

const WearableRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Wearable />} />
    <Route path="new" element={<WearableUpdate />} />
    <Route path=":id">
      <Route index element={<WearableDetail />} />
      <Route path="edit" element={<WearableUpdate />} />
      <Route path="delete" element={<WearableDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WearableRoutes;
