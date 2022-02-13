import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPolicyUsersType, PolicyUsersType } from '../policy-users-type.model';
import { PolicyUsersTypeService } from '../service/policy-users-type.service';

import { PolicyUsersTypeRoutingResolveService } from './policy-users-type-routing-resolve.service';

describe('PolicyUsersType routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PolicyUsersTypeRoutingResolveService;
  let service: PolicyUsersTypeService;
  let resultPolicyUsersType: IPolicyUsersType | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(PolicyUsersTypeRoutingResolveService);
    service = TestBed.inject(PolicyUsersTypeService);
    resultPolicyUsersType = undefined;
  });

  describe('resolve', () => {
    it('should return IPolicyUsersType returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPolicyUsersType = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPolicyUsersType).toEqual({ id: 123 });
    });

    it('should return new IPolicyUsersType if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPolicyUsersType = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPolicyUsersType).toEqual(new PolicyUsersType());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PolicyUsersType })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPolicyUsersType = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPolicyUsersType).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
