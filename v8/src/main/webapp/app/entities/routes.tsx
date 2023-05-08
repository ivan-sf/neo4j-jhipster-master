import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DataVital from './data-vital';
import UserCollect from './user-collect';
import Wearable from './wearable';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="data-vital/*" element={<DataVital />} />
        <Route path="user-collect/*" element={<UserCollect />} />
        <Route path="wearable/*" element={<Wearable />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
