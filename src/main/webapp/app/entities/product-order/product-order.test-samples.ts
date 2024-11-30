import dayjs from 'dayjs/esm';

import { IProductOrder, NewProductOrder } from './product-order.model';

export const sampleWithRequiredData: IProductOrder = {
  id: 21362,
  placedDate: dayjs('2024-11-28T20:13'),
  status: 'PENDING',
  code: 'or',
};

export const sampleWithPartialData: IProductOrder = {
  id: 22551,
  placedDate: dayjs('2024-11-29T07:01'),
  status: 'COMPLETED',
  code: 'beside mad ferociously',
};

export const sampleWithFullData: IProductOrder = {
  id: 11955,
  placedDate: dayjs('2024-11-29T02:35'),
  status: 'CANCELLED',
  code: 'horn boohoo regular',
};

export const sampleWithNewData: NewProductOrder = {
  placedDate: dayjs('2024-11-28T15:32'),
  status: 'CANCELLED',
  code: 'upon',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
