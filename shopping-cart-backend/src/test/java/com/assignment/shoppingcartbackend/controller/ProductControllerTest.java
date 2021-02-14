package com.assignment.shoppingcartbackend.controller;

import com.assignment.shoppingcartbackend.dto.ProductDetails;
import com.assignment.shoppingcartbackend.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void Should_ReturnSearchResult_WhenProductsAvailable() throws Exception {
        ProductDetails productDetails = new ProductDetails(1, "Penguin-ears", 20);
        List<ProductDetails> productDetailsList = new ArrayList<>();
        productDetailsList.add(productDetails);
        Mockito.when(productService.searchProducts("p")).thenReturn(productDetailsList);

        this.mockMvc
                .perform(get("/api/products/p"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productName").value("Penguin-ears"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void Should_ReturnEmptyResponse_WhenMatchProductsNotAvailable() throws Exception {
        List<ProductDetails> productDetailsList = new ArrayList<>();
        Mockito.when(productService.searchProducts("tv")).thenReturn(productDetailsList);

        this.mockMvc
                .perform(get("/api/products/tv"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(0))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void Should_ReturnProductPrice_WhenProductAvailable() throws Exception {
        Mockito.when(productService.calculatePrice(1,"cartons", 1)).thenReturn(175.00);

        this.mockMvc
                .perform(get("/api/products/1/cartons/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(175.00))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}