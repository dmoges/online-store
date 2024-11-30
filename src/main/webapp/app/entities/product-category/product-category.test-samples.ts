import { IProductCategory, NewProductCategory } from './product-category.model';

export const sampleWithRequiredData: IProductCategory = {
  id: 32354,
  name: 'husband furthermore lox',
};

export const sampleWithPartialData: IProductCategory = {
  id: 6583,
  name: 'provided hollow including',
  description: 'along intently',
};

export const sampleWithFullData: IProductCategory = {
  id: 18785,
  name: 'inasmuch powerless',
  description: 'around geez ack',
};

export const sampleWithNewData: NewProductCategory = {
  name: 'than unselfish',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
