import { ICustomer, NewCustomer } from './customer.model';

export const sampleWithRequiredData: ICustomer = {
  id: 7784,
  firstName: 'Velva',
  lastName: 'Bins',
  gender: 'FEMALE',
  email: 'ebVS@|~:;*H@.f%=iQ,',
  phone: '1-767-540-6784',
  addressLine1: 'discourse difficult',
  addressLine2: 'helpfully',
  citry: 'within off sauerkraut',
  country: 'Poland',
};

export const sampleWithPartialData: ICustomer = {
  id: 12898,
  firstName: 'Ignatius',
  lastName: 'Treutel',
  gender: 'FEMALE',
  email: 'R+K@d5@.mw&S',
  phone: '1-767-691-1359 x08966',
  addressLine1: 'woefully annex',
  addressLine2: 'gee fatally suitcase',
  citry: 'handover',
  country: 'Bouvet Island',
};

export const sampleWithFullData: ICustomer = {
  id: 25462,
  firstName: 'Eunice',
  lastName: 'Kreiger',
  gender: 'FEMALE',
  email: '{D=.E0@5hB}@.(8[(V',
  phone: '(349) 509-1062 x53331',
  addressLine1: 'legal',
  addressLine2: 'edible whoa',
  citry: 'aged contradict',
  country: 'Tanzania',
};

export const sampleWithNewData: NewCustomer = {
  firstName: 'Ivy',
  lastName: 'Oberbrunner',
  gender: 'MALE',
  email: 'qar@U@.|',
  phone: '224-558-9476 x13052',
  addressLine1: 'release vanadyl',
  addressLine2: 'interchange',
  citry: 'unexpectedly mostly of',
  country: 'Tokelau',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
