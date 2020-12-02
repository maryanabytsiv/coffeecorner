package com.epam.coffeecorner.service;

import com.epam.coffeecorner.model.Product;
import com.epam.coffeecorner.model.StampCard;

import java.util.List;

public interface InputParser {

    List<Product> parseProductsFrom(String input);

    StampCard findStampCardByInputString(String cardNumber);


}
