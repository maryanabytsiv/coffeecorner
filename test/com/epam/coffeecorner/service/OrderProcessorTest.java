package com.epam.coffeecorner.service;

import com.epam.coffeecorner.model.Product;
import com.epam.coffeecorner.model.Receipt;
import com.epam.coffeecorner.model.StampCard;
import com.epam.coffeecorner.model.TypeOfProduct;
import com.epam.coffeecorner.service.impl.OrderProcessorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderProcessorTest {

    private OrderProcessor orderProcessor = new OrderProcessorImpl();

    @Test
    void testProcessIfNullInputsThenNoCrash(){
        Receipt receipt = orderProcessor.process(null, Collections.emptyList());
        Assertions.assertEquals(receipt.getOrderedProduct(), Collections.EMPTY_LIST);
    }

    @Test
    void processWithCorrectCardAndEmptyOrderList(){
        StampCard stampCard = new StampCard(1,5);
        Receipt receipt = orderProcessor.process(stampCard, Collections.emptyList());
        Assertions.assertEquals(Collections.EMPTY_LIST, receipt.getOrderedProduct());
    }

    @Test
    void processWithBeverageProductAndNoCard(){
        List<Product> products = new ArrayList<>();
        products.add(new Product("coffee", 5, TypeOfProduct.BEVERAGE));
        Receipt receipt = orderProcessor.process(null, products);
        Assertions.assertEquals(receipt.getOrderedProduct(), products);
    }

    @Test
    void processCheckCardValuesIfBeveragesInOrder(){
        StampCard stampCard = new StampCard(2,5);
        List<Product> products = new ArrayList<>();
        products.add(new Product("coffee", 5, TypeOfProduct.BEVERAGE));
        products.add(new Product("juice", 5, TypeOfProduct.BEVERAGE));
        products.add(new Product("tea", 5, TypeOfProduct.BEVERAGE));
        products.add(new Product("milk", 5, TypeOfProduct.BEVERAGE));
        orderProcessor.process(stampCard, products);
        Assertions.assertEquals(4, stampCard.getNumberOfStampsAlreadyThere());
    }

    @Test
    void processCheckCardValuesIfBeveragesAndExtrasInOrder(){
        StampCard stampCard = new StampCard(3,5);
        List<Product> products = new ArrayList<>();
        products.add(new Product("coffee", 5, TypeOfProduct.BEVERAGE));
        products.add(new Product("juice", 5, TypeOfProduct.BEVERAGE));
        products.add(new Product("tea", 5, TypeOfProduct.EXTRAS));
        products.add(new Product("milk", 5, TypeOfProduct.BEVERAGE));
        orderProcessor.process(stampCard, products);
        Assertions.assertEquals(3, stampCard.getNumberOfStampsAlreadyThere());
    }

    @Test
    void processCheckPriceAndCardIf5BeveragesAlreadyExceed(){
        StampCard stampCard = new StampCard(3,5);
        List<Product> products = new ArrayList<>();
        products.add(new Product("coffee", 5, TypeOfProduct.BEVERAGE));
        products.add(new Product("juice", 5, TypeOfProduct.BEVERAGE));
        products.add(new Product("tea", 5, TypeOfProduct.BEVERAGE));
        products.add(new Product("tea green", 5, TypeOfProduct.BEVERAGE));
        products.add(new Product("milk", 5, TypeOfProduct.BEVERAGE));

        Receipt receipt = orderProcessor.process(stampCard, products);

        Assertions.assertEquals(0, stampCard.getNumberOfStampsAlreadyThere());
        Assertions.assertEquals(0, receipt.getOrderedProduct().get(0).getPrice());
    }

    @Test
    void processCheckSum(){
        StampCard stampCard = new StampCard(3,5);
        List<Product> products = new ArrayList<>();
        products.add(new Product("coffee", 5, TypeOfProduct.BEVERAGE));
        products.add(new Product("juice", 5, TypeOfProduct.BEVERAGE));

        Receipt receipt = orderProcessor.process(stampCard, products);

        Assertions.assertEquals(10,receipt.getSum());
    }

    @Test
    void processCheckSumIfExtrasInThere(){
        StampCard stampCard = new StampCard(3,5);
        List<Product> products = new ArrayList<>();
        Product productWithExtra = new Product("coffee", 5, TypeOfProduct.BEVERAGE);
        productWithExtra.setExtra(new Product("milk", 5, TypeOfProduct.EXTRAS));
        products.add(productWithExtra);
        products.add(new Product("juice", 5, TypeOfProduct.BEVERAGE));

        Receipt receipt = orderProcessor.process(stampCard, products);

        Assertions.assertEquals(15,receipt.getSum());
    }

}
