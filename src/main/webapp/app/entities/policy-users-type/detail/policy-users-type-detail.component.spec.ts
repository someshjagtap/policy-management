import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PolicyUsersTypeDetailComponent } from './policy-users-type-detail.component';

describe('PolicyUsersType Management Detail Component', () => {
  let comp: PolicyUsersTypeDetailComponent;
  let fixture: ComponentFixture<PolicyUsersTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PolicyUsersTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ policyUsersType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PolicyUsersTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PolicyUsersTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load policyUsersType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.policyUsersType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
