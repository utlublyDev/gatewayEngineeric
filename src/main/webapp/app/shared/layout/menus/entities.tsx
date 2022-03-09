import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/store-owner">
      <Translate contentKey="global.menu.entities.storeOwner" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/product-category">
      <Translate contentKey="global.menu.entities.productProductCategory" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/reviews">
      <Translate contentKey="global.menu.entities.productReviews" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/notification">
      <Translate contentKey="global.menu.entities.notificationNotification" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/product-order">
      <Translate contentKey="global.menu.entities.productProductOrder" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/invoice">
      <Translate contentKey="global.menu.entities.invoiceInvoice" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/customer">
      <Translate contentKey="global.menu.entities.customer" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/product">
      <Translate contentKey="global.menu.entities.productProduct" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/store-categories">
      <Translate contentKey="global.menu.entities.storeCategories" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/shipment">
      <Translate contentKey="global.menu.entities.invoiceShipment" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/order-item">
      <Translate contentKey="global.menu.entities.productOrderItem" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/top-banner-information">
      <Translate contentKey="global.menu.entities.topBannerInformation" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/slider-image-slider">
      <Translate contentKey="global.menu.entities.sliderImageSlider" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/store-adv-banner">
      <Translate contentKey="global.menu.entities.storeAdvBanner" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/payment">
      <Translate contentKey="global.menu.entities.invoicePayment" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
