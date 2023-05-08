import { IWearable } from 'app/shared/model/wearable.model';

export interface IUserCollect {
  id?: string;
  name?: string | null;
  lastName?: string | null;
  username?: string | null;
  vitalKey?: string | null;
  wearable?: IWearable | null;
}

export const defaultValue: Readonly<IUserCollect> = {};
