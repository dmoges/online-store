import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: 'ef46642a-efda-42ae-904e-b79e5301b46f',
};

export const sampleWithPartialData: IAuthority = {
  name: '3d8aa0fd-2a67-468a-a601-fe0bc8afb259',
};

export const sampleWithFullData: IAuthority = {
  name: '09d85790-0f7d-4cf1-8378-659129b32bb6',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
