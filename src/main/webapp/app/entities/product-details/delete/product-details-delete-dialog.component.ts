import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductDetails } from '../product-details.model';
import { ProductDetailsService } from '../service/product-details.service';

@Component({
  templateUrl: './product-details-delete-dialog.component.html',
})
export class ProductDetailsDeleteDialogComponent {
  productDetails?: IProductDetails;

  constructor(protected productDetailsService: ProductDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productDetailsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
