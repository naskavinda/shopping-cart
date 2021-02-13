import {Component, OnInit} from '@angular/core';
import {ProductService} from '../service/product.service';

@Component({
  selector: 'app-price-calculator',
  templateUrl: './price-calculator.component.html',
  styleUrls: ['./price-calculator.component.css']
})
export class PriceCalculatorComponent implements OnInit {

  selectedProduct: any;
  filteredProducts: any[];
  countries: any[];
  isSelectCarton = true;
  cartons = 0;
  units = 0;
  quantity: number = null;
  price: number;

  constructor(private priceService: ProductService) {
  }

  ngOnInit(): void {
  }

  filterProduct(event): void {
    const filtered: any[] = [];
    const query = event.query;

    this.priceService.searchProduct(query).subscribe(products => {
      for (const product of products) {
        filtered.push({name: product.productName, id: product.id, unitsPerCarton: product.unitsPerCarton});
      }
      this.filteredProducts = filtered;
    });
  }

  calculateUnitsAndCartons(): void {
    this.price = null;
    const unitsPerCarton: number = this.selectedProduct.unitsPerCarton;
    if (this.quantity && this.quantity > 0) {
      if (!this.isSelectCarton) {
        if (this.quantity < unitsPerCarton) {
          this.cartons = 0;
          this.units = this.quantity;
        } else {
          this.units = this.quantity % unitsPerCarton;
          this.cartons = (this.quantity - this.units) / unitsPerCarton;
        }
      } else {
        this.cartons = this.quantity;
        this.units = 0;
      }
    } else {
      this.cartons = 0;
      this.units = 0;
    }
  }

  isProductSelected(): boolean {
    return !(this.selectedProduct === undefined || this.selectedProduct === '' ||
      (this.selectedProduct && this.selectedProduct.name === undefined));
  }

  resetToDefault(): void {
    if (!(this.selectedProduct !== undefined && this.selectedProduct.name !== undefined)) {
      this.quantity = null;
      this.isSelectCarton = true;
      this.cartons = 0;
      this.units = 0;
      this.price = null;
    }
  }

  calculate(): void {
    const type = this.isSelectCarton ? 'cartons' : 'units';
    this.priceService.calculatePrice(this.selectedProduct.id, type, this.quantity)
      .subscribe(price => this.price = price);
  }

  quantityValidator(event: KeyboardEvent): boolean {
    const regex = this.quantity ? /[0-9]/ : /[1-9]/;
    return regex.test(event.code);
  }
}
