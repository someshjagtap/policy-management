import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPolicyUsersType, getPolicyUsersTypeIdentifier } from '../policy-users-type.model';

export type EntityResponseType = HttpResponse<IPolicyUsersType>;
export type EntityArrayResponseType = HttpResponse<IPolicyUsersType[]>;

@Injectable({ providedIn: 'root' })
export class PolicyUsersTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/policy-users-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(policyUsersType: IPolicyUsersType): Observable<EntityResponseType> {
    return this.http.post<IPolicyUsersType>(this.resourceUrl, policyUsersType, { observe: 'response' });
  }

  update(policyUsersType: IPolicyUsersType): Observable<EntityResponseType> {
    return this.http.put<IPolicyUsersType>(
      `${this.resourceUrl}/${getPolicyUsersTypeIdentifier(policyUsersType) as number}`,
      policyUsersType,
      { observe: 'response' }
    );
  }

  partialUpdate(policyUsersType: IPolicyUsersType): Observable<EntityResponseType> {
    return this.http.patch<IPolicyUsersType>(
      `${this.resourceUrl}/${getPolicyUsersTypeIdentifier(policyUsersType) as number}`,
      policyUsersType,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPolicyUsersType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPolicyUsersType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPolicyUsersTypeToCollectionIfMissing(
    policyUsersTypeCollection: IPolicyUsersType[],
    ...policyUsersTypesToCheck: (IPolicyUsersType | null | undefined)[]
  ): IPolicyUsersType[] {
    const policyUsersTypes: IPolicyUsersType[] = policyUsersTypesToCheck.filter(isPresent);
    if (policyUsersTypes.length > 0) {
      const policyUsersTypeCollectionIdentifiers = policyUsersTypeCollection.map(
        policyUsersTypeItem => getPolicyUsersTypeIdentifier(policyUsersTypeItem)!
      );
      const policyUsersTypesToAdd = policyUsersTypes.filter(policyUsersTypeItem => {
        const policyUsersTypeIdentifier = getPolicyUsersTypeIdentifier(policyUsersTypeItem);
        if (policyUsersTypeIdentifier == null || policyUsersTypeCollectionIdentifiers.includes(policyUsersTypeIdentifier)) {
          return false;
        }
        policyUsersTypeCollectionIdentifiers.push(policyUsersTypeIdentifier);
        return true;
      });
      return [...policyUsersTypesToAdd, ...policyUsersTypeCollection];
    }
    return policyUsersTypeCollection;
  }
}
