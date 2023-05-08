import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/data-vital">
        <Translate contentKey="global.menu.entities.dataVital" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/user-collect">
        <Translate contentKey="global.menu.entities.userCollect" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
