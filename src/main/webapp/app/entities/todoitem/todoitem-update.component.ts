import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import UserService from '@/admin/user-management/user-management.service';

import AlertService from '@/shared/alert/alert.service';
import { ITodoitem, Todoitem } from '@/shared/model/todoitem.model';
import TodoitemService from './todoitem.service';

const validations: any = {
  todoitem: {
    created: {
      required,
    },
    status: {
      required,
    },
    task: {
      required,
      maxLength: maxLength(1024),
    },
    user: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class TodoitemUpdate extends mixins(JhiDataUtils) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('todoitemService') private todoitemService: () => TodoitemService;
  public todoitem: ITodoitem = new Todoitem();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.todoitemId) {
        vm.retrieveTodoitem(to.params.todoitemId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.todoitem.id) {
      this.todoitemService()
        .update(this.todoitem)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Todoitem is updated with identifier ' + param.id;
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.todoitemService()
        .create(this.todoitem)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Todoitem is created with identifier ' + param.id;
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date) {
      return format(date, DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.todoitem[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.todoitem[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.todoitem[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.todoitem[field] = null;
    }
  }

  public retrieveTodoitem(todoitemId): void {
    this.todoitemService()
      .find(todoitemId)
      .then(res => {
        res.created = new Date(res.created);
        this.todoitem = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
  }
}
