import { IDataVital } from 'app/shared/model/data-vital.model';

export interface IUserCollect {
  id?: string;
  name?: string | null;
  lastName?: string | null;
  username?: string | null;
  vitalKey?: string | null;
  vitalKeys?: IDataVital[] | null;
}

export const defaultValue: Readonly<IUserCollect> = {};
