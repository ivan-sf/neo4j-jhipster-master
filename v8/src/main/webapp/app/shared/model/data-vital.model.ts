import dayjs from 'dayjs';
import { IWearable } from 'app/shared/model/wearable.model';

export interface IDataVital {
  id?: string;
  date?: string | null;
  data?: string | null;
  eventType?: string | null;
  vitalKey?: string | null;
  wearable?: IWearable | null;
}

export const defaultValue: Readonly<IDataVital> = {};
