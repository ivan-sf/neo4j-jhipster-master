import dayjs from 'dayjs';
import { IUserCollect } from 'app/shared/model/user-collect.model';

export interface IDataVital {
  id?: string;
  date?: string | null;
  data?: string | null;
  eventType?: string | null;
  vitalKey?: string | null;
  vitalKey?: IUserCollect | null;
}

export const defaultValue: Readonly<IDataVital> = {};
