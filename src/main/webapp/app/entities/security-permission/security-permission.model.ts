import { ISecurityRole } from 'app/entities/security-role/security-role.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';

export interface ISecurityPermission {
  id?: number;
  name?: string;
  description?: string | null;
  lastModified?: string;
  lastModifiedBy?: string;
  securityRoles?: ISecurityRole[] | null;
  securityUsers?: ISecurityUser[] | null;
}

export class SecurityPermission implements ISecurityPermission {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string | null,
    public lastModified?: string,
    public lastModifiedBy?: string,
    public securityRoles?: ISecurityRole[] | null,
    public securityUsers?: ISecurityUser[] | null
  ) {}
}

export function getSecurityPermissionIdentifier(securityPermission: ISecurityPermission): number | undefined {
  return securityPermission.id;
}
