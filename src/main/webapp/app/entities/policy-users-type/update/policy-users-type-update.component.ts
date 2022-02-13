import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPolicyUsersType, PolicyUsersType } from '../policy-users-type.model';
import { PolicyUsersTypeService } from '../service/policy-users-type.service';

@Component({
  selector: 'jhi-policy-users-type-update',
  templateUrl: './policy-users-type-update.component.html',
})
export class PolicyUsersTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
  });

  constructor(
    protected policyUsersTypeService: PolicyUsersTypeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ policyUsersType }) => {
      this.updateForm(policyUsersType);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const policyUsersType = this.createFromForm();
    if (policyUsersType.id !== undefined) {
      this.subscribeToSaveResponse(this.policyUsersTypeService.update(policyUsersType));
    } else {
      this.subscribeToSaveResponse(this.policyUsersTypeService.create(policyUsersType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPolicyUsersType>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(policyUsersType: IPolicyUsersType): void {
    this.editForm.patchValue({
      id: policyUsersType.id,
      name: policyUsersType.name,
      lastModified: policyUsersType.lastModified,
      lastModifiedBy: policyUsersType.lastModifiedBy,
    });
  }

  protected createFromForm(): IPolicyUsersType {
    return {
      ...new PolicyUsersType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }
}
