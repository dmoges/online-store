import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IShipment } from '../shipment.model';

@Component({
  standalone: true,
  selector: 'jhi-shipment-detail',
  templateUrl: './shipment-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ShipmentDetailComponent {
  shipment = input<IShipment | null>(null);

  previousState(): void {
    window.history.back();
  }
}