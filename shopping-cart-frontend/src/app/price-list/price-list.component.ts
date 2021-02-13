import {Component, OnInit} from '@angular/core';
import {PriceService} from '../service/price.service';

@Component({
  selector: 'app-price-list',
  templateUrl: './price-list.component.html',
  styleUrls: ['./price-list.component.css']
})
export class PriceListComponent implements OnInit {
  products: any;
  productPrice = new Map();

  constructor(private priceService: PriceService) {
  }

  ngOnInit(): void {
    this.priceService.getProducts().subscribe(products => this.products = products);
  }

  public loadDetails(productId): void {
    this.priceService.getProductPrice(productId).subscribe(productPrice => {
      this.productPrice.set(productId, productPrice);
      console.log(this.productPrice);
    });
  }
}
