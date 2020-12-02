package com.epam.coffeecorner.service;

import com.epam.coffeecorner.model.Product;
import com.epam.coffeecorner.model.Receipt;
import com.epam.coffeecorner.model.StampCard;

import java.util.List;

public interface OrderProcessor {

    Receipt process(StampCard stampCard, List<Product> orderedProducts);
}
