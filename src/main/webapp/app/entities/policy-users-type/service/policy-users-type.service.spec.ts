import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPolicyUsersType, PolicyUsersType } from '../policy-users-type.model';

import { PolicyUsersTypeService } from './policy-users-type.service';

describe('PolicyUsersType Service', () => {
  let service: PolicyUsersTypeService;
  let httpMock: HttpTestingController;
  let elemDefault: IPolicyUsersType;
  let expectedResult: IPolicyUsersType | IPolicyUsersType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PolicyUsersTypeService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      lastModified: 'AAAAAAA',
      lastModifiedBy: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a PolicyUsersType', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new PolicyUsersType()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PolicyUsersType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          lastModified: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PolicyUsersType', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          lastModified: 'BBBBBB',
        },
        new PolicyUsersType()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PolicyUsersType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          lastModified: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a PolicyUsersType', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPolicyUsersTypeToCollectionIfMissing', () => {
      it('should add a PolicyUsersType to an empty array', () => {
        const policyUsersType: IPolicyUsersType = { id: 123 };
        expectedResult = service.addPolicyUsersTypeToCollectionIfMissing([], policyUsersType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(policyUsersType);
      });

      it('should not add a PolicyUsersType to an array that contains it', () => {
        const policyUsersType: IPolicyUsersType = { id: 123 };
        const policyUsersTypeCollection: IPolicyUsersType[] = [
          {
            ...policyUsersType,
          },
          { id: 456 },
        ];
        expectedResult = service.addPolicyUsersTypeToCollectionIfMissing(policyUsersTypeCollection, policyUsersType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PolicyUsersType to an array that doesn't contain it", () => {
        const policyUsersType: IPolicyUsersType = { id: 123 };
        const policyUsersTypeCollection: IPolicyUsersType[] = [{ id: 456 }];
        expectedResult = service.addPolicyUsersTypeToCollectionIfMissing(policyUsersTypeCollection, policyUsersType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(policyUsersType);
      });

      it('should add only unique PolicyUsersType to an array', () => {
        const policyUsersTypeArray: IPolicyUsersType[] = [{ id: 123 }, { id: 456 }, { id: 42012 }];
        const policyUsersTypeCollection: IPolicyUsersType[] = [{ id: 123 }];
        expectedResult = service.addPolicyUsersTypeToCollectionIfMissing(policyUsersTypeCollection, ...policyUsersTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const policyUsersType: IPolicyUsersType = { id: 123 };
        const policyUsersType2: IPolicyUsersType = { id: 456 };
        expectedResult = service.addPolicyUsersTypeToCollectionIfMissing([], policyUsersType, policyUsersType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(policyUsersType);
        expect(expectedResult).toContain(policyUsersType2);
      });

      it('should accept null and undefined values', () => {
        const policyUsersType: IPolicyUsersType = { id: 123 };
        expectedResult = service.addPolicyUsersTypeToCollectionIfMissing([], null, policyUsersType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(policyUsersType);
      });

      it('should return initial array if no PolicyUsersType is added', () => {
        const policyUsersTypeCollection: IPolicyUsersType[] = [{ id: 123 }];
        expectedResult = service.addPolicyUsersTypeToCollectionIfMissing(policyUsersTypeCollection, undefined, null);
        expect(expectedResult).toEqual(policyUsersTypeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
