import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISecurityRole, SecurityRole } from '../security-role.model';
import { SecurityRoleService } from '../service/security-role.service';
import { ISecurityPermission } from 'app/entities/security-permission/security-permission.model';
import { SecurityPermissionService } from 'app/entities/security-permission/service/security-permission.service';

@Component({
  selector: 'jhi-security-role-update',
  templateUrl: './security-role-update.component.html',
})
export class SecurityRoleUpdateComponent implements OnInit {
  isSaving = false;

  securityPermissionsSharedCollection: ISecurityPermission[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    securityPermissions: [],
  });

  constructor(
    protected securityRoleService: SecurityRoleService,
    protected securityPermissionService: SecurityPermissionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ securityRole }) => {
      this.updateForm(securityRole);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const securityRole = this.createFromForm();
    if (securityRole.id !== undefined) {
      this.subscribeToSaveResponse(this.securityRoleService.update(securityRole));
    } else {
      this.subscribeToSaveResponse(this.securityRoleService.create(securityRole));
    }
  }

  trackSecurityPermissionById(index: number, item: ISecurityPermission): number {
    return item.id!;
  }

  getSelectedSecurityPermission(option: ISecurityPermission, selectedVals?: ISecurityPermission[]): ISecurityPermission {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISecurityRole>>): void {
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

  protected updateForm(securityRole: ISecurityRole): void {
    this.editForm.patchValue({
      id: securityRole.id,
      name: securityRole.name,
      description: securityRole.description,
      lastModified: securityRole.lastModified,
      lastModifiedBy: securityRole.lastModifiedBy,
      securityPermissions: securityRole.securityPermissions,
    });

    this.securityPermissionsSharedCollection = this.securityPermissionService.addSecurityPermissionToCollectionIfMissing(
      this.securityPermissionsSharedCollection,
      ...(securityRole.securityPermissions ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.securityPermissionService
      .query()
      .pipe(map((res: HttpResponse<ISecurityPermission[]>) => res.body ?? []))
      .pipe(
        map((securityPermissions: ISecurityPermission[]) =>
          this.securityPermissionService.addSecurityPermissionToCollectionIfMissing(
            securityPermissions,
            ...(this.editForm.get('securityPermissions')!.value ?? [])
          )
        )
      )
      .subscribe((securityPermissions: ISecurityPermission[]) => (this.securityPermissionsSharedCollection = securityPermissions));
  }

  protected createFromForm(): ISecurityRole {
    return {
      ...new SecurityRole(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      securityPermissions: this.editForm.get(['securityPermissions'])!.value,
    };
  }
}
