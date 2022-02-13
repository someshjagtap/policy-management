import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PolicyUsersTypeComponent } from './list/policy-users-type.component';
import { PolicyUsersTypeDetailComponent } from './detail/policy-users-type-detail.component';
import { PolicyUsersTypeUpdateComponent } from './update/policy-users-type-update.component';
import { PolicyUsersTypeDeleteDialogComponent } from './delete/policy-users-type-delete-dialog.component';
import { PolicyUsersTypeRoutingModule } from './route/policy-users-type-routing.module';

@NgModule({
  imports: [SharedModule, PolicyUsersTypeRoutingModule],
  declarations: [
    PolicyUsersTypeComponent,
    PolicyUsersTypeDetailComponent,
    PolicyUsersTypeUpdateComponent,
    PolicyUsersTypeDeleteDialogComponent,
  ],
  entryComponents: [PolicyUsersTypeDeleteDialogComponent],
})
export class PolicyUsersTypeModule {}
