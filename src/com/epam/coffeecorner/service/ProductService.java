package com.epam.coffeecorner.service;

import com.epam.coffeecorner.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProductList();

    Product findProductByName(String name);

    Product findExtrasByName(String name);

}
