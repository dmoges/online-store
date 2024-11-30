import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'onlineStoreApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'product',
    data: { pageTitle: 'onlineStoreApp.product.home.title' },
    loadChildren: () => import('./product/product.routes'),
  },
  {
    path: 'product-category',
    data: { pageTitle: 'onlineStoreApp.productCategory.home.title' },
    loadChildren: () => import('./product-category/product-category.routes'),
  },
  {
    path: 'customer',
    data: { pageTitle: 'onlineStoreApp.customer.home.title' },
    loadChildren: () => import('./customer/customer.routes'),
  },
  {
    path: 'product-order',
    data: { pageTitle: 'onlineStoreApp.productOrder.home.title' },
    loadChildren: () => import('./product-order/product-order.routes'),
  },
  {
    path: 'order-item',
    data: { pageTitle: 'onlineStoreApp.orderItem.home.title' },
    loadChildren: () => import('./order-item/order-item.routes'),
  },
  {
    path: 'invoice',
    data: { pageTitle: 'onlineStoreApp.invoice.home.title' },
    loadChildren: () => import('./invoice/invoice.routes'),
  },
  {
    path: 'shipment',
    data: { pageTitle: 'onlineStoreApp.shipment.home.title' },
    loadChildren: () => import('./shipment/shipment.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
