import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPolicyUsersType, PolicyUsersType } from '../policy-users-type.model';
import { PolicyUsersTypeService } from '../service/policy-users-type.service';

@Injectable({ providedIn: 'root' })
export class PolicyUsersTypeRoutingResolveService implements Resolve<IPolicyUsersType> {
  constructor(protected service: PolicyUsersTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPolicyUsersType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((policyUsersType: HttpResponse<PolicyUsersType>) => {
          if (policyUsersType.body) {
            return of(policyUsersType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PolicyUsersType());
  }
}
