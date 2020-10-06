import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { ITodoitem } from '@/shared/model/todoitem.model';
import TodoitemService from './todoitem.service';

@Component
export default class TodoitemDetails extends mixins(JhiDataUtils) {
  @Inject('todoitemService') private todoitemService: () => TodoitemService;
  public todoitem: ITodoitem = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.todoitemId) {
        vm.retrieveTodoitem(to.params.todoitemId);
      }
    });
  }

  public retrieveTodoitem(todoitemId) {
    this.todoitemService()
      .find(todoitemId)
      .then(res => {
        this.todoitem = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
