import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICustomer } from 'app/entities/customer/customer.model';
import { CustomerService } from 'app/entities/customer/service/customer.service';
import { OrderStatus } from 'app/entities/enumerations/order-status.model';
import { ProductOrderService } from '../service/product-order.service';
import { IProductOrder } from '../product-order.model';
import { ProductOrderFormGroup, ProductOrderFormService } from './product-order-form.service';

@Component({
  standalone: true,
  selector: 'jhi-product-order-update',
  templateUrl: './product-order-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ProductOrderUpdateComponent implements OnInit {
  isSaving = false;
  productOrder: IProductOrder | null = null;
  orderStatusValues = Object.keys(OrderStatus);

  customersSharedCollection: ICustomer[] = [];

  protected productOrderService = inject(ProductOrderService);
  protected productOrderFormService = inject(ProductOrderFormService);
  protected customerService = inject(CustomerService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ProductOrderFormGroup = this.productOrderFormService.createProductOrderFormGroup();

  compareCustomer = (o1: ICustomer | null, o2: ICustomer | null): boolean => this.customerService.compareCustomer(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productOrder }) => {
      this.productOrder = productOrder;
      if (productOrder) {
        this.updateForm(productOrder);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productOrder = this.productOrderFormService.getProductOrder(this.editForm);
    if (productOrder.id !== null) {
      this.subscribeToSaveResponse(this.productOrderService.update(productOrder));
    } else {
      this.subscribeToSaveResponse(this.productOrderService.create(productOrder));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductOrder>>): void {
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

  protected updateForm(productOrder: IProductOrder): void {
    this.productOrder = productOrder;
    this.productOrderFormService.resetForm(this.editForm, productOrder);

    this.customersSharedCollection = this.customerService.addCustomerToCollectionIfMissing<ICustomer>(
      this.customersSharedCollection,
      productOrder.customer,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.customerService
      .query()
      .pipe(map((res: HttpResponse<ICustomer[]>) => res.body ?? []))
      .pipe(
        map((customers: ICustomer[]) =>
          this.customerService.addCustomerToCollectionIfMissing<ICustomer>(customers, this.productOrder?.customer),
        ),
      )
      .subscribe((customers: ICustomer[]) => (this.customersSharedCollection = customers));
  }
}
