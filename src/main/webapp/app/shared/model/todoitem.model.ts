import { IUser } from '@/shared/model/user.model';

export interface ITodoitem {
  id?: number;
  created?: Date;
  status?: string;
  task?: any;
  user?: IUser;
}

export class Todoitem implements ITodoitem {
  constructor(public id?: number, public created?: Date, public status?: string, public task?: any, public user?: IUser) {}
}
