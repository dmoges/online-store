import { IOrderItem, NewOrderItem } from './order-item.model';

export const sampleWithRequiredData: IOrderItem = {
  id: 32065,
  quantity: 4482,
  totalPrice: 13140.78,
  status: 'AVAILABLE',
};

export const sampleWithPartialData: IOrderItem = {
  id: 5040,
  quantity: 905,
  totalPrice: 15359.82,
  status: 'AVAILABLE',
};

export const sampleWithFullData: IOrderItem = {
  id: 25871,
  quantity: 5241,
  totalPrice: 22649.25,
  status: 'AVAILABLE',
};

export const sampleWithNewData: NewOrderItem = {
  quantity: 8454,
  totalPrice: 2012.44,
  status: 'BACK_ORDER',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
