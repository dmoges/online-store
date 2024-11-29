import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 17754,
  login: 'E*xl@gzt\\=nbpLT',
};

export const sampleWithPartialData: IUser = {
  id: 20727,
  login: '=6T@4L4o\\td\\|2\\$i4YyOG',
};

export const sampleWithFullData: IUser = {
  id: 16426,
  login: '2QG',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
