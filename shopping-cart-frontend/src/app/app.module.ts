import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { PriceListComponent } from './price-list/price-list.component';
import { PriceCalculatorComponent } from './price-calculator/price-calculator.component';

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    PriceListComponent,
    PriceCalculatorComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
