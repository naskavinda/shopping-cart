import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private readonly baseUrl: string = environment.restApiBaseUrl;

  constructor(private http: HttpClient) {
  }

  public getProducts(): Observable<any> {
    const url = this.baseUrl + '/api/products';
    return this.http.get<any>(url);
  }

  public getProductPrice(productId: number): Observable<any> {
    const url = this.baseUrl + '/api/products/prices/' + productId;
    return this.http.get<any>(url);
  }

  public searchProduct(keyword: string): Observable<any> {
    const url = this.baseUrl + '/api/products/' + keyword;
    return this.http.get<any>(url);
  }

  public calculatePrice(productId: number, type: string, qty: number): Observable<any> {
    const url = this.baseUrl + '/api/products/' + productId + '/' + type + '/' + qty;
    return this.http.get<any>(url);
  }
}
