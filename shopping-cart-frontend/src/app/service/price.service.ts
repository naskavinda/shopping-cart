import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PriceService {

  private readonly baseUrl: string = environment.restApiBaseUrl;

  constructor(private http: HttpClient) {
  }

  public getProducts(): Observable<any> {
    const url = this.baseUrl + '/api/products';
    return this.http.get<any>(url);
  }

  public getProductPrice(productId): Observable<any> {
    const url = this.baseUrl + '/api/products/prices/' + productId;
    return this.http.get<any>(url);
  }
}
