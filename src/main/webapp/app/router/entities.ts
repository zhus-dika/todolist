import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Todoitem = () => import('@/entities/todoitem/todoitem.vue');
// prettier-ignore
const TodoitemUpdate = () => import('@/entities/todoitem/todoitem-update.vue');
// prettier-ignore
const TodoitemDetails = () => import('@/entities/todoitem/todoitem-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/todoitem',
    name: 'Todoitem',
    component: Todoitem,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/todoitem/new',
    name: 'TodoitemCreate',
    component: TodoitemUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/todoitem/:todoitemId/edit',
    name: 'TodoitemEdit',
    component: TodoitemUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/todoitem/:todoitemId/view',
    name: 'TodoitemView',
    component: TodoitemDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/todoitem',
    name: 'Todoitem',
    component: Todoitem,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/todoitem/new',
    name: 'TodoitemCreate',
    component: TodoitemUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/todoitem/:todoitemId/edit',
    name: 'TodoitemEdit',
    component: TodoitemUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/todoitem/:todoitemId/view',
    name: 'TodoitemView',
    component: TodoitemDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/todoitem',
    name: 'Todoitem',
    component: Todoitem,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/todoitem/new',
    name: 'TodoitemCreate',
    component: TodoitemUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/todoitem/:todoitemId/edit',
    name: 'TodoitemEdit',
    component: TodoitemUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/todoitem/:todoitemId/view',
    name: 'TodoitemView',
    component: TodoitemDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
