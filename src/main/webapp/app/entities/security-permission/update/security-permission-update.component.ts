import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ISecurityPermission, SecurityPermission } from '../security-permission.model';
import { SecurityPermissionService } from '../service/security-permission.service';

@Component({
  selector: 'jhi-security-permission-update',
  templateUrl: './security-permission-update.component.html',
})
export class SecurityPermissionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
  });

  constructor(
    protected securityPermissionService: SecurityPermissionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ securityPermission }) => {
      this.updateForm(securityPermission);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const securityPermission = this.createFromForm();
    if (securityPermission.id !== undefined) {
      this.subscribeToSaveResponse(this.securityPermissionService.update(securityPermission));
    } else {
      this.subscribeToSaveResponse(this.securityPermissionService.create(securityPermission));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISecurityPermission>>): void {
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

  protected updateForm(securityPermission: ISecurityPermission): void {
    this.editForm.patchValue({
      id: securityPermission.id,
      name: securityPermission.name,
      description: securityPermission.description,
      lastModified: securityPermission.lastModified,
      lastModifiedBy: securityPermission.lastModifiedBy,
    });
  }

  protected createFromForm(): ISecurityPermission {
    return {
      ...new SecurityPermission(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }
}
