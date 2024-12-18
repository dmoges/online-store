import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProductOrder } from '../product-order.model';
import { ProductOrderService } from '../service/product-order.service';

const productOrderResolve = (route: ActivatedRouteSnapshot): Observable<null | IProductOrder> => {
  const id = route.params.id;
  if (id) {
    return inject(ProductOrderService)
      .find(id)
      .pipe(
        mergeMap((productOrder: HttpResponse<IProductOrder>) => {
          if (productOrder.body) {
            return of(productOrder.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default productOrderResolve;
