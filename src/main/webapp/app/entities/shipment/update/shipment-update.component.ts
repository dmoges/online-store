import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IInvoice } from 'app/entities/invoice/invoice.model';
import { InvoiceService } from 'app/entities/invoice/service/invoice.service';
import { IShipment } from '../shipment.model';
import { ShipmentService } from '../service/shipment.service';
import { ShipmentFormGroup, ShipmentFormService } from './shipment-form.service';

@Component({
  standalone: true,
  selector: 'jhi-shipment-update',
  templateUrl: './shipment-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ShipmentUpdateComponent implements OnInit {
  isSaving = false;
  shipment: IShipment | null = null;

  invoicesSharedCollection: IInvoice[] = [];

  protected shipmentService = inject(ShipmentService);
  protected shipmentFormService = inject(ShipmentFormService);
  protected invoiceService = inject(InvoiceService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ShipmentFormGroup = this.shipmentFormService.createShipmentFormGroup();

  compareInvoice = (o1: IInvoice | null, o2: IInvoice | null): boolean => this.invoiceService.compareInvoice(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shipment }) => {
      this.shipment = shipment;
      if (shipment) {
        this.updateForm(shipment);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shipment = this.shipmentFormService.getShipment(this.editForm);
    if (shipment.id !== null) {
      this.subscribeToSaveResponse(this.shipmentService.update(shipment));
    } else {
      this.subscribeToSaveResponse(this.shipmentService.create(shipment));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShipment>>): void {
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

  protected updateForm(shipment: IShipment): void {
    this.shipment = shipment;
    this.shipmentFormService.resetForm(this.editForm, shipment);

    this.invoicesSharedCollection = this.invoiceService.addInvoiceToCollectionIfMissing<IInvoice>(
      this.invoicesSharedCollection,
      shipment.invoice,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.invoiceService
      .query()
      .pipe(map((res: HttpResponse<IInvoice[]>) => res.body ?? []))
      .pipe(map((invoices: IInvoice[]) => this.invoiceService.addInvoiceToCollectionIfMissing<IInvoice>(invoices, this.shipment?.invoice)))
      .subscribe((invoices: IInvoice[]) => (this.invoicesSharedCollection = invoices));
  }
}
