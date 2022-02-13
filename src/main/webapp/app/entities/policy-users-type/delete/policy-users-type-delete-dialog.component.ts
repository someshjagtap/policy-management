import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPolicyUsersType } from '../policy-users-type.model';
import { PolicyUsersTypeService } from '../service/policy-users-type.service';

@Component({
  templateUrl: './policy-users-type-delete-dialog.component.html',
})
export class PolicyUsersTypeDeleteDialogComponent {
  policyUsersType?: IPolicyUsersType;

  constructor(protected policyUsersTypeService: PolicyUsersTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.policyUsersTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
