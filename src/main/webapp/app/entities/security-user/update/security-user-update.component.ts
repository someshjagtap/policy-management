import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISecurityUser, SecurityUser } from '../security-user.model';
import { SecurityUserService } from '../service/security-user.service';
import { ISecurityPermission } from 'app/entities/security-permission/security-permission.model';
import { SecurityPermissionService } from 'app/entities/security-permission/service/security-permission.service';
import { ISecurityRole } from 'app/entities/security-role/security-role.model';
import { SecurityRoleService } from 'app/entities/security-role/service/security-role.service';

@Component({
  selector: 'jhi-security-user-update',
  templateUrl: './security-user-update.component.html',
})
export class SecurityUserUpdateComponent implements OnInit {
  isSaving = false;

  securityPermissionsSharedCollection: ISecurityPermission[] = [];
  securityRolesSharedCollection: ISecurityRole[] = [];

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    designation: [],
    login: [null, [Validators.required]],
    passwordHash: [null, [Validators.required]],
    email: [null, []],
    imageUrl: [],
    activated: [null, [Validators.required]],
    langKey: [],
    activationKey: [],
    resetKey: [],
    resetDate: [],
    mobileNo: [],
    oneTimePassword: [],
    otpExpiryTime: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    securityPermissions: [],
    securityRoles: [],
  });

  constructor(
    protected securityUserService: SecurityUserService,
    protected securityPermissionService: SecurityPermissionService,
    protected securityRoleService: SecurityRoleService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ securityUser }) => {
      this.updateForm(securityUser);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const securityUser = this.createFromForm();
    if (securityUser.id !== undefined) {
      this.subscribeToSaveResponse(this.securityUserService.update(securityUser));
    } else {
      this.subscribeToSaveResponse(this.securityUserService.create(securityUser));
    }
  }

  trackSecurityPermissionById(index: number, item: ISecurityPermission): number {
    return item.id!;
  }

  trackSecurityRoleById(index: number, item: ISecurityRole): number {
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

  getSelectedSecurityRole(option: ISecurityRole, selectedVals?: ISecurityRole[]): ISecurityRole {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISecurityUser>>): void {
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

  protected updateForm(securityUser: ISecurityUser): void {
    this.editForm.patchValue({
      id: securityUser.id,
      firstName: securityUser.firstName,
      lastName: securityUser.lastName,
      designation: securityUser.designation,
      login: securityUser.login,
      passwordHash: securityUser.passwordHash,
      email: securityUser.email,
      imageUrl: securityUser.imageUrl,
      activated: securityUser.activated,
      langKey: securityUser.langKey,
      activationKey: securityUser.activationKey,
      resetKey: securityUser.resetKey,
      resetDate: securityUser.resetDate,
      mobileNo: securityUser.mobileNo,
      oneTimePassword: securityUser.oneTimePassword,
      otpExpiryTime: securityUser.otpExpiryTime,
      lastModified: securityUser.lastModified,
      lastModifiedBy: securityUser.lastModifiedBy,
      securityPermissions: securityUser.securityPermissions,
      securityRoles: securityUser.securityRoles,
    });

    this.securityPermissionsSharedCollection = this.securityPermissionService.addSecurityPermissionToCollectionIfMissing(
      this.securityPermissionsSharedCollection,
      ...(securityUser.securityPermissions ?? [])
    );
    this.securityRolesSharedCollection = this.securityRoleService.addSecurityRoleToCollectionIfMissing(
      this.securityRolesSharedCollection,
      ...(securityUser.securityRoles ?? [])
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

    this.securityRoleService
      .query()
      .pipe(map((res: HttpResponse<ISecurityRole[]>) => res.body ?? []))
      .pipe(
        map((securityRoles: ISecurityRole[]) =>
          this.securityRoleService.addSecurityRoleToCollectionIfMissing(securityRoles, ...(this.editForm.get('securityRoles')!.value ?? []))
        )
      )
      .subscribe((securityRoles: ISecurityRole[]) => (this.securityRolesSharedCollection = securityRoles));
  }

  protected createFromForm(): ISecurityUser {
    return {
      ...new SecurityUser(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      login: this.editForm.get(['login'])!.value,
      passwordHash: this.editForm.get(['passwordHash'])!.value,
      email: this.editForm.get(['email'])!.value,
      imageUrl: this.editForm.get(['imageUrl'])!.value,
      activated: this.editForm.get(['activated'])!.value,
      langKey: this.editForm.get(['langKey'])!.value,
      activationKey: this.editForm.get(['activationKey'])!.value,
      resetKey: this.editForm.get(['resetKey'])!.value,
      resetDate: this.editForm.get(['resetDate'])!.value,
      mobileNo: this.editForm.get(['mobileNo'])!.value,
      oneTimePassword: this.editForm.get(['oneTimePassword'])!.value,
      otpExpiryTime: this.editForm.get(['otpExpiryTime'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      securityPermissions: this.editForm.get(['securityPermissions'])!.value,
      securityRoles: this.editForm.get(['securityRoles'])!.value,
    };
  }
}
