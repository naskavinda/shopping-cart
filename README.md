# shopping-cart

##Assumptions

* If carton size is higher than discount carton size. Discount carton price is used to calculate unit price.


##Limitation

* Increase and discount percentages can configure in only property file level.


## How to Run

### Run docker compose file

Go to `<project_location>/shopping-cart/`

`docker-compose -f shopping-cart-compose.yml up`

### Run backend 

Go to `<project_location>/shopping-cart/shopping-cart-backend` 

`mvn spring-boot:run`

### Run frontend
  
Go to `<project_location>/shopping-cart/shopping-cart-frontend`

```
npm install
ng serve -o
```