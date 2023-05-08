import dayjs from 'dayjs';
import { IWearable } from 'app/shared/model/wearable.model';
import { IUserCollect } from 'app/shared/model/user-collect.model';

export interface IDataVital {
  id?: string;
  vitalKey?: string | null;
  eventType?: string | null;
  date?: string | null;
  data?: string | null;
  wearable?: IWearable | null;
  userCollect?: IUserCollect | null;
}

export const defaultValue: Readonly<IDataVital> = {};
