import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DataVital from './data-vital';
import DataVitalDetail from './data-vital-detail';
import DataVitalUpdate from './data-vital-update';
import DataVitalDeleteDialog from './data-vital-delete-dialog';

const DataVitalRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DataVital />} />
    <Route path="new" element={<DataVitalUpdate />} />
    <Route path=":id">
      <Route index element={<DataVitalDetail />} />
      <Route path="edit" element={<DataVitalUpdate />} />
      <Route path="delete" element={<DataVitalDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DataVitalRoutes;
