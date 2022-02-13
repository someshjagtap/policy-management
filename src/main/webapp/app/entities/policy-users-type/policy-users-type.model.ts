import { IPolicyUsers } from 'app/entities/policy-users/policy-users.model';

export interface IPolicyUsersType {
  id?: number;
  name?: string | null;
  lastModified?: string;
  lastModifiedBy?: string;
  policyUsers?: IPolicyUsers | null;
}

export class PolicyUsersType implements IPolicyUsersType {
  constructor(
    public id?: number,
    public name?: string | null,
    public lastModified?: string,
    public lastModifiedBy?: string,
    public policyUsers?: IPolicyUsers | null
  ) {}
}

export function getPolicyUsersTypeIdentifier(policyUsersType: IPolicyUsersType): number | undefined {
  return policyUsersType.id;
}
