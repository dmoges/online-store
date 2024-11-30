import dayjs from 'dayjs/esm';

import { IShipment, NewShipment } from './shipment.model';

export const sampleWithRequiredData: IShipment = {
  id: 18092,
  date: dayjs('2024-11-29T01:25'),
};

export const sampleWithPartialData: IShipment = {
  id: 25960,
  trackingCode: 'inasmuch',
  date: dayjs('2024-11-28T16:01'),
  details: 'promptly as pish',
};

export const sampleWithFullData: IShipment = {
  id: 23563,
  trackingCode: 'precedent which',
  date: dayjs('2024-11-28T15:10'),
  details: 'behind',
};

export const sampleWithNewData: NewShipment = {
  date: dayjs('2024-11-29T02:39'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
