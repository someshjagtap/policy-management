import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PolicyUsersTypeComponent } from '../list/policy-users-type.component';
import { PolicyUsersTypeDetailComponent } from '../detail/policy-users-type-detail.component';
import { PolicyUsersTypeUpdateComponent } from '../update/policy-users-type-update.component';
import { PolicyUsersTypeRoutingResolveService } from './policy-users-type-routing-resolve.service';

const policyUsersTypeRoute: Routes = [
  {
    path: '',
    component: PolicyUsersTypeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PolicyUsersTypeDetailComponent,
    resolve: {
      policyUsersType: PolicyUsersTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PolicyUsersTypeUpdateComponent,
    resolve: {
      policyUsersType: PolicyUsersTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PolicyUsersTypeUpdateComponent,
    resolve: {
      policyUsersType: PolicyUsersTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(policyUsersTypeRoute)],
  exports: [RouterModule],
})
export class PolicyUsersTypeRoutingModule {}
