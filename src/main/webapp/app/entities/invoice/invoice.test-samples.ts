import dayjs from 'dayjs/esm';

import { IInvoice, NewInvoice } from './invoice.model';

export const sampleWithRequiredData: IInvoice = {
  id: 25480,
  date: dayjs('2024-11-28T17:16'),
  status: 'ISSUED',
  paymentMethod: 'CREDIT_CARD',
  paymentDate: dayjs('2024-11-28T18:29'),
  paymentAmount: 11902.73,
};

export const sampleWithPartialData: IInvoice = {
  id: 10659,
  date: dayjs('2024-11-29T03:47'),
  details: 'although sequester untried',
  status: 'CANCELLED',
  paymentMethod: 'PAYPAL',
  paymentDate: dayjs('2024-11-29T00:36'),
  paymentAmount: 21647.5,
};

export const sampleWithFullData: IInvoice = {
  id: 19758,
  date: dayjs('2024-11-29T03:43'),
  details: 'phew',
  status: 'ISSUED',
  paymentMethod: 'CREDIT_CARD',
  paymentDate: dayjs('2024-11-29T07:25'),
  paymentAmount: 6985.35,
};

export const sampleWithNewData: NewInvoice = {
  date: dayjs('2024-11-29T01:14'),
  status: 'ISSUED',
  paymentMethod: 'PAYPAL',
  paymentDate: dayjs('2024-11-28T21:25'),
  paymentAmount: 15538.95,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
