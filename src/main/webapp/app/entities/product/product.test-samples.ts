import { IProduct, NewProduct } from './product.model';

export const sampleWithRequiredData: IProduct = {
  id: 11220,
  name: 'whereas',
  price: 4439.82,
  sizeType: 'XXL',
};

export const sampleWithPartialData: IProduct = {
  id: 13875,
  name: 'but yearningly cook',
  description: 'midst questionably hydrant',
  price: 8395.21,
  sizeType: 'S',
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
};

export const sampleWithFullData: IProduct = {
  id: 26416,
  name: 'yippee transparency',
  description: 'sweetly huzzah near',
  price: 23327.06,
  sizeType: 'XXL',
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
};

export const sampleWithNewData: NewProduct = {
  name: 'minus though nor',
  price: 9186.5,
  sizeType: 'S',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
