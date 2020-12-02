package com.epam.coffeecorner.service;

import com.epam.coffeecorner.model.Product;
import com.epam.coffeecorner.model.StampCard;

import java.util.List;

public interface PrintService {

    void printProductsMenu();

    void printProducts(List<Product> productList);

    void printProductsReceiptView(List<Product> productList);

    void printAllExistingStampCards();

    void printReceipt(StampCard stampCard, List<Product> products);

}
