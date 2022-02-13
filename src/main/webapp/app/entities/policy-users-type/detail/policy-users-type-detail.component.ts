import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPolicyUsersType } from '../policy-users-type.model';

@Component({
  selector: 'jhi-policy-users-type-detail',
  templateUrl: './policy-users-type-detail.component.html',
})
export class PolicyUsersTypeDetailComponent implements OnInit {
  policyUsersType: IPolicyUsersType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ policyUsersType }) => {
      this.policyUsersType = policyUsersType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
