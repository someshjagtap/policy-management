import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PolicyUsersService } from '../service/policy-users.service';
import { IPolicyUsers, PolicyUsers } from '../policy-users.model';
import { IPolicyUsersType } from 'app/entities/policy-users-type/policy-users-type.model';
import { PolicyUsersTypeService } from 'app/entities/policy-users-type/service/policy-users-type.service';

import { PolicyUsersUpdateComponent } from './policy-users-update.component';

describe('PolicyUsers Management Update Component', () => {
  let comp: PolicyUsersUpdateComponent;
  let fixture: ComponentFixture<PolicyUsersUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let policyUsersService: PolicyUsersService;
  let policyUsersTypeService: PolicyUsersTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PolicyUsersUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PolicyUsersUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PolicyUsersUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    policyUsersService = TestBed.inject(PolicyUsersService);
    policyUsersTypeService = TestBed.inject(PolicyUsersTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call policyUsersType query and add missing value', () => {
      const policyUsers: IPolicyUsers = { id: 456 };
      const policyUsersType: IPolicyUsersType = { id: 92593 };
      policyUsers.policyUsersType = policyUsersType;

      const policyUsersTypeCollection: IPolicyUsersType[] = [{ id: 51163 }];
      jest.spyOn(policyUsersTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: policyUsersTypeCollection })));
      const expectedCollection: IPolicyUsersType[] = [policyUsersType, ...policyUsersTypeCollection];
      jest.spyOn(policyUsersTypeService, 'addPolicyUsersTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ policyUsers });
      comp.ngOnInit();

      expect(policyUsersTypeService.query).toHaveBeenCalled();
      expect(policyUsersTypeService.addPolicyUsersTypeToCollectionIfMissing).toHaveBeenCalledWith(
        policyUsersTypeCollection,
        policyUsersType
      );
      expect(comp.policyUsersTypesCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const policyUsers: IPolicyUsers = { id: 456 };
      const policyUsersType: IPolicyUsersType = { id: 83923 };
      policyUsers.policyUsersType = policyUsersType;

      activatedRoute.data = of({ policyUsers });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(policyUsers));
      expect(comp.policyUsersTypesCollection).toContain(policyUsersType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PolicyUsers>>();
      const policyUsers = { id: 123 };
      jest.spyOn(policyUsersService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ policyUsers });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: policyUsers }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(policyUsersService.update).toHaveBeenCalledWith(policyUsers);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PolicyUsers>>();
      const policyUsers = new PolicyUsers();
      jest.spyOn(policyUsersService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ policyUsers });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: policyUsers }));
      saveSubject.complete();

      // THEN
      expect(policyUsersService.create).toHaveBeenCalledWith(policyUsers);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PolicyUsers>>();
      const policyUsers = { id: 123 };
      jest.spyOn(policyUsersService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ policyUsers });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(policyUsersService.update).toHaveBeenCalledWith(policyUsers);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPolicyUsersTypeById', () => {
      it('Should return tracked PolicyUsersType primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPolicyUsersTypeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
