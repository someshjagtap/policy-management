import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PolicyUsersTypeService } from '../service/policy-users-type.service';
import { IPolicyUsersType, PolicyUsersType } from '../policy-users-type.model';

import { PolicyUsersTypeUpdateComponent } from './policy-users-type-update.component';

describe('PolicyUsersType Management Update Component', () => {
  let comp: PolicyUsersTypeUpdateComponent;
  let fixture: ComponentFixture<PolicyUsersTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let policyUsersTypeService: PolicyUsersTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PolicyUsersTypeUpdateComponent],
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
      .overrideTemplate(PolicyUsersTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PolicyUsersTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    policyUsersTypeService = TestBed.inject(PolicyUsersTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const policyUsersType: IPolicyUsersType = { id: 456 };

      activatedRoute.data = of({ policyUsersType });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(policyUsersType));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PolicyUsersType>>();
      const policyUsersType = { id: 123 };
      jest.spyOn(policyUsersTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ policyUsersType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: policyUsersType }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(policyUsersTypeService.update).toHaveBeenCalledWith(policyUsersType);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PolicyUsersType>>();
      const policyUsersType = new PolicyUsersType();
      jest.spyOn(policyUsersTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ policyUsersType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: policyUsersType }));
      saveSubject.complete();

      // THEN
      expect(policyUsersTypeService.create).toHaveBeenCalledWith(policyUsersType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PolicyUsersType>>();
      const policyUsersType = { id: 123 };
      jest.spyOn(policyUsersTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ policyUsersType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(policyUsersTypeService.update).toHaveBeenCalledWith(policyUsersType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
