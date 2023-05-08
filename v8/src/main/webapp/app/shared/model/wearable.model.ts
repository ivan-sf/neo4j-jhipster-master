import { IDataVital } from 'app/shared/model/data-vital.model';
import { IUserCollect } from 'app/shared/model/user-collect.model';

export interface IWearable {
  id?: string;
  nameWearable?: string | null;
  type?: string | null;
  dataVitals?: IDataVital[] | null;
  userCollect?: IUserCollect | null;
}

export const defaultValue: Readonly<IWearable> = {};
