package com.epam.coffeecorner.service.impl;

import com.epam.coffeecorner.model.Product;
import com.epam.coffeecorner.service.ProductService;

import java.util.Arrays;
import java.util.List;

import static com.epam.coffeecorner.model.TypeOfProduct.*;

public class ProductServiceImpl implements ProductService {

    private static List<Product> productList = createProducts();

    private static List<Product> createProducts() {

        productList = Arrays.asList(
                new Product("Small coffee", 2.5, BEVERAGE),
                new Product("Medium coffee", 3.0, BEVERAGE),
                new Product("Large coffee", 3.5, BEVERAGE),
                new Product("Bacon Roll", 4.5, SNACK),
                new Product("Orange juice (0.25l)", 3.95, BEVERAGE),

                new Product("extra milk", 0.3, EXTRAS),
                new Product("foamed milk", 0.5, EXTRAS),
                new Product("special roast", 0.9, EXTRAS));
        return productList;

    }

    @Override
    public List<Product> getProductList() {
        return productList;
    }

    @Override
    public Product findProductByName(String name) {
        Product foundProduct = productList.stream()
                .filter(product -> product.getTypeOfProduct() != EXTRAS)
                .filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()))
                .findAny()
                .orElse(null);
        return (foundProduct == null) ? null : new Product(foundProduct.getName(), foundProduct.getPrice(), foundProduct.getTypeOfProduct());
    }

    @Override
    public Product findExtrasByName(String name) {
        Product extras = productList.stream()
                .filter(product -> product.getTypeOfProduct() == EXTRAS)
                .filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()))
                .findAny()
                .orElse(null);
        return (extras == null) ? null : new Product(extras.getName(), extras.getPrice(), extras.getTypeOfProduct());
    }

}
