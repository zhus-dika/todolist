/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import TodoitemDetailComponent from '@/entities/todoitem/todoitem-details.vue';
import TodoitemClass from '@/entities/todoitem/todoitem-details.component';
import TodoitemService from '@/entities/todoitem/todoitem.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Todoitem Management Detail Component', () => {
    let wrapper: Wrapper<TodoitemClass>;
    let comp: TodoitemClass;
    let todoitemServiceStub: SinonStubbedInstance<TodoitemService>;

    beforeEach(() => {
      todoitemServiceStub = sinon.createStubInstance<TodoitemService>(TodoitemService);

      wrapper = shallowMount<TodoitemClass>(TodoitemDetailComponent, {
        store,
        localVue,
        provide: { todoitemService: () => todoitemServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTodoitem = { id: 123 };
        todoitemServiceStub.find.resolves(foundTodoitem);

        // WHEN
        comp.retrieveTodoitem(123);
        await comp.$nextTick();

        // THEN
        expect(comp.todoitem).toBe(foundTodoitem);
      });
    });
  });
});
