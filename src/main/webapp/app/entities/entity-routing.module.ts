import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'policy',
        data: { pageTitle: 'policyManagementApp.policy.home.title' },
        loadChildren: () => import('./policy/policy.module').then(m => m.PolicyModule),
      },
      {
        path: 'premiun-details',
        data: { pageTitle: 'policyManagementApp.premiunDetails.home.title' },
        loadChildren: () => import('./premiun-details/premiun-details.module').then(m => m.PremiunDetailsModule),
      },
      {
        path: 'member',
        data: { pageTitle: 'policyManagementApp.member.home.title' },
        loadChildren: () => import('./member/member.module').then(m => m.MemberModule),
      },
      {
        path: 'vehicle-class',
        data: { pageTitle: 'policyManagementApp.vehicleClass.home.title' },
        loadChildren: () => import('./vehicle-class/vehicle-class.module').then(m => m.VehicleClassModule),
      },
      {
        path: 'vehicle-details',
        data: { pageTitle: 'policyManagementApp.vehicleDetails.home.title' },
        loadChildren: () => import('./vehicle-details/vehicle-details.module').then(m => m.VehicleDetailsModule),
      },
      {
        path: 'parameter-lookup',
        data: { pageTitle: 'policyManagementApp.parameterLookup.home.title' },
        loadChildren: () => import('./parameter-lookup/parameter-lookup.module').then(m => m.ParameterLookupModule),
      },
      {
        path: 'nominee',
        data: { pageTitle: 'policyManagementApp.nominee.home.title' },
        loadChildren: () => import('./nominee/nominee.module').then(m => m.NomineeModule),
      },
      {
        path: 'bank-details',
        data: { pageTitle: 'policyManagementApp.bankDetails.home.title' },
        loadChildren: () => import('./bank-details/bank-details.module').then(m => m.BankDetailsModule),
      },
      {
        path: 'product',
        data: { pageTitle: 'policyManagementApp.product.home.title' },
        loadChildren: () => import('./product/product.module').then(m => m.ProductModule),
      },
      {
        path: 'product-details',
        data: { pageTitle: 'policyManagementApp.productDetails.home.title' },
        loadChildren: () => import('./product-details/product-details.module').then(m => m.ProductDetailsModule),
      },
      {
        path: 'product-type',
        data: { pageTitle: 'policyManagementApp.productType.home.title' },
        loadChildren: () => import('./product-type/product-type.module').then(m => m.ProductTypeModule),
      },
      {
        path: 'company',
        data: { pageTitle: 'policyManagementApp.company.home.title' },
        loadChildren: () => import('./company/company.module').then(m => m.CompanyModule),
      },
      {
        path: 'company-type',
        data: { pageTitle: 'policyManagementApp.companyType.home.title' },
        loadChildren: () => import('./company-type/company-type.module').then(m => m.CompanyTypeModule),
      },
      {
        path: 'agency',
        data: { pageTitle: 'policyManagementApp.agency.home.title' },
        loadChildren: () => import('./agency/agency.module').then(m => m.AgencyModule),
      },
      {
        path: 'rider',
        data: { pageTitle: 'policyManagementApp.rider.home.title' },
        loadChildren: () => import('./rider/rider.module').then(m => m.RiderModule),
      },
      {
        path: 'policy-users',
        data: { pageTitle: 'policyManagementApp.policyUsers.home.title' },
        loadChildren: () => import('./policy-users/policy-users.module').then(m => m.PolicyUsersModule),
      },
      {
        path: 'address',
        data: { pageTitle: 'policyManagementApp.address.home.title' },
        loadChildren: () => import('./address/address.module').then(m => m.AddressModule),
      },
      {
        path: 'policy-users-type',
        data: { pageTitle: 'policyManagementApp.policyUsersType.home.title' },
        loadChildren: () => import('./policy-users-type/policy-users-type.module').then(m => m.PolicyUsersTypeModule),
      },
      {
        path: 'security-user',
        data: { pageTitle: 'policyManagementApp.securityUser.home.title' },
        loadChildren: () => import('./security-user/security-user.module').then(m => m.SecurityUserModule),
      },
      {
        path: 'user-access',
        data: { pageTitle: 'policyManagementApp.userAccess.home.title' },
        loadChildren: () => import('./user-access/user-access.module').then(m => m.UserAccessModule),
      },
      {
        path: 'security-role',
        data: { pageTitle: 'policyManagementApp.securityRole.home.title' },
        loadChildren: () => import('./security-role/security-role.module').then(m => m.SecurityRoleModule),
      },
      {
        path: 'security-permission',
        data: { pageTitle: 'policyManagementApp.securityPermission.home.title' },
        loadChildren: () => import('./security-permission/security-permission.module').then(m => m.SecurityPermissionModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
