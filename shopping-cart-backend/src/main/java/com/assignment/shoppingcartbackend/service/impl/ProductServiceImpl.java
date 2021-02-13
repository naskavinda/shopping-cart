package com.assignment.shoppingcartbackend.service.impl;

import com.assignment.shoppingcartbackend.dto.PriceModel;
import com.assignment.shoppingcartbackend.dto.ProductDetails;
import com.assignment.shoppingcartbackend.dto.ProductPrice;
import com.assignment.shoppingcartbackend.entity.Carton;
import com.assignment.shoppingcartbackend.entity.Product;
import com.assignment.shoppingcartbackend.repository.CartonRepository;
import com.assignment.shoppingcartbackend.repository.ProductRepository;
import com.assignment.shoppingcartbackend.service.ProductService;
import com.assignment.shoppingcartbackend.util.Tuple2;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final int MIN_DISCOUNT_APPLY_SIZE = 3;
    private static final double PRICE_FACTOR_WITH_DISCOUNT = 0.9;
    private static final double PRICE_FACTOR_WITH_INCREMENT = 1.3;

    private final ProductRepository productRepository;
    private final CartonRepository cartonRepository;

    private final DecimalFormat decimalFormat = new DecimalFormat("#.00");

    public ProductServiceImpl(ProductRepository productRepository, CartonRepository cartonRepository) {
        this.productRepository = productRepository;
        this.cartonRepository = cartonRepository;
    }

    @Override
    public List<ProductDetails> searchProducts(String keyword) {
        return productRepository.findProductsByProductNameContains(keyword)
                .stream().map(Product::getId)
                .map(cartonRepository::findCartonByProductId)
                .map(ProductServiceImpl::mapToProductDetails)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDetails> getProducts() {
        return cartonRepository.findAll().stream()
                .map(ProductServiceImpl::mapToProductDetails)
                .collect(Collectors.toList());
    }

    private static ProductDetails mapToProductDetails(Carton carton) {
        Product product = carton.getProduct();
        return new ProductDetails(product.getId(), product.getProductName(), carton.getUnitsPerCarton());
    }

    @Override
    public Double calculatePrice(Integer productId, String type, Integer qty) {
        Carton carton = cartonRepository.findCartonByProductId(productId);
        Tuple2<Integer, Integer> cartonsAndUnits = calculateCartonsAndUnits(type, qty, carton);
        return calculateProductPrice(carton, cartonsAndUnits);
    }

    private Tuple2<Integer, Integer> calculateCartonsAndUnits(String type, Integer qty, Carton carton) {
        Tuple2<Integer, Integer> cartonsAndUnits;
        if (type.equals("cartons")) {
            cartonsAndUnits = new Tuple2<>(qty, 0);
        } else {
            cartonsAndUnits = calculateCartonsAndUnits(qty, carton.getUnitsPerCarton());
        }
        return cartonsAndUnits;
    }

    @Override
    public ProductPrice getProductPrices(Integer productId, Integer productCount) {
        Carton carton = cartonRepository.findCartonByProductId(productId);

        List<PriceModel> priceModel = new ArrayList<>();
        for (int i = 1; i <= productCount; i++) {
            Tuple2<Integer, Integer> cartonsAndUnits = calculateCartonsAndUnits(i, carton.getUnitsPerCarton());

            Double price = calculateProductPrice(carton, cartonsAndUnits);

            priceModel.add(new PriceModel(i, price));
        }
        return new ProductPrice(carton.getProduct().getId(), carton.getProduct().getProductName(), priceModel);
    }

    protected Double calculateProductPrice(Carton carton, Tuple2<Integer, Integer> cartonsAndUnits) {
        Integer cartons = cartonsAndUnits.getFirst();
        Integer units = cartonsAndUnits.getSecond();
        double actualCartonPrice = getActualCartonPrice(carton.getPrice(), cartons);
        double unitPrice = 0.0;
        if (units > 0) {
            // Assume: if carton size is higher then discount carton size. calculate unit price using discount carton price.
            unitPrice = getUnitPrice(carton.getUnitsPerCarton(), actualCartonPrice);
        }
        double price = (cartons * actualCartonPrice) + (units * unitPrice);
        return Double.valueOf(decimalFormat.format(price));
    }

    protected Double getActualCartonPrice(Double cartonPrice, Integer cartons) {
        Double actualPrice = cartonPrice;
        if (cartons >= MIN_DISCOUNT_APPLY_SIZE) {
            actualPrice = cartonPrice * PRICE_FACTOR_WITH_DISCOUNT;
        }
        return actualPrice;
    }

    protected Double getUnitPrice(int unitsPerCarton, Double actualCartonPrice) {
        return (actualCartonPrice * PRICE_FACTOR_WITH_INCREMENT) / unitsPerCarton;
    }

    protected Tuple2<Integer, Integer> calculateCartonsAndUnits(Integer totalUnits, Integer unitsPerCarton) {
        int cartons = 0;
        int units = totalUnits;
        if (totalUnits >= unitsPerCarton) {
            cartons = totalUnits / unitsPerCarton;
            units = totalUnits - (cartons * unitsPerCarton);
        }
        return new Tuple2<>(cartons, units);
    }
}
