package com.assignment.shoppingcartbackend.service.impl;

import com.assignment.shoppingcartbackend.dto.ProductPrice;
import com.assignment.shoppingcartbackend.model.Carton;
import com.assignment.shoppingcartbackend.model.Product;
import com.assignment.shoppingcartbackend.repository.CartonRepository;
import com.assignment.shoppingcartbackend.util.Tuple2;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @MockBean
    private CartonRepository cartonRepository;

    @Test
    void ShouldReturnInputCartonPrice_WhenCartonIs_Zero() {
        Double actualCartonPrice = productService.getActualCartonPrice(100.00, 0);
        assertEquals(100.00, actualCartonPrice);
    }

    @Test
    void ShouldReturnInputCartonPrice_WhenCartonSizeLessThan_DiscountApplyCartonSize() {
        Double actualCartonPrice = productService.getActualCartonPrice(175.00, 2);
        assertEquals(175.00, actualCartonPrice);
    }

    @Test
    void ShouldReturnCartonPrice_WhenCartonSizeHigherThan_DiscountApplyCartonSize() {
        Double actualCartonPrice = productService.getActualCartonPrice(175.00, 4);
        assertEquals(157.5, actualCartonPrice);
    }

    @Test
    void ShouldReturnCartonPrice_WhenCartonSizeEqual_DiscountApplyCartonSize() {
        Double actualCartonPrice = productService.getActualCartonPrice(100.00, 3);
        assertEquals(90.00, actualCartonPrice);
    }

    @Test
    void ShouldReturnUnitPrice() {
        Double unitPrice = productService.getUnitPrice(20, 157.50);
        assertEquals(10.2375, unitPrice);
    }

    @Test
    void ShouldReturnPrice_WhenCartonSizeLessThan_DiscountApplyCartonSize() {
        Product product = new Product(1, "Penguin-ears");
        Carton carton = new Carton(1, 20, 175.00, product);
        Double price = productService.calculateProductPrice(carton, 2, 0);
        assertEquals(350.00, price);
    }

    @Test
    void ShouldReturnPrice_WhenCartonSizeHigherThan_DiscountApplyCartonSize() {
        Product product = new Product(1, "Penguin-ears");
        Carton carton = new Carton(1, 20, 175.00, product);
        Double price = productService.calculateProductPrice(carton, 4, 1);
        assertEquals(640.24, price);
    }

    @Test
    void ShouldReturnPrice_WhenCartonSizeEqual_DiscountApplyCartonSize() {
        Product product = new Product(1, "Penguin-ears");
        Carton carton = new Carton(1, 20, 175.00, product);
        Double price = productService.calculateProductPrice(carton, 3, 1);
        assertEquals(482.74, price);
    }

    @Test
    void ShouldReturnPrice_WhenCartonIs_Zero_And_UnitsHas_Value() {
        Product product = new Product(1, "Penguin-ears");
        Carton carton = new Carton(1, 20, 175.00, product);
        Double price = productService.calculateProductPrice(carton, 0, 10);
        assertEquals(113.75, price);
    }

    @Test
    void ShouldReturnZeroCartons_WhenTotalUnitLessThan_UnitsPerCarton() {
        Tuple2<Integer, Integer> cartonsAndUnits = productService.calculateCartonsAndUnits(10, 20);
        assertEquals(0, cartonsAndUnits.getFirst());
        assertEquals(10, cartonsAndUnits.getSecond());
    }

    @Test
    void ShouldReturnNotZeroCartonsAndUnits_WhenTotalUnitHigherThan_UnitsPerCarton() {
        Tuple2<Integer, Integer> cartonsAndUnits = productService.calculateCartonsAndUnits(48, 20);
        assertEquals(2, cartonsAndUnits.getFirst());
        assertEquals(8, cartonsAndUnits.getSecond());
    }

    @Test
    void ShouldReturnOneCartons_WhenTotalUnitEqual_UnitsPerCarton() {
        Tuple2<Integer, Integer> cartonsAndUnits = productService.calculateCartonsAndUnits(20, 20);
        assertEquals(1, cartonsAndUnits.getFirst());
        assertEquals(0, cartonsAndUnits.getSecond());
    }

    @Test
    void ShouldReturnProductPrice_ProductId_And_productCountIs_Valid() {
        int productId = 1;
        int productCount = 1;
        Product product = new Product(1, "Penguin-ears");
        Carton carton = new Carton(1, 20, 175.00, product);
        Mockito.when(cartonRepository.findCartonByProductId(productId)).thenReturn(carton);
        ProductPrice productPrices = productService.getProductPrices(productId, productCount);
        assertEquals(1, productPrices.getPriceModel().size());
        assertEquals(11.38, productPrices.getPriceModel().get(0).getPrice());
        assertEquals(1, productPrices.getPriceModel().get(0).getTotalUnits());
    }

    @Test
    void ShouldReturnThrownException_ProductIdIs_Invalid() {
        int productId = 1;
        int productCount = 1;
        Mockito.when(cartonRepository.findCartonByProductId(productId)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> productService.getProductPrices(productId, productCount));
    }

}