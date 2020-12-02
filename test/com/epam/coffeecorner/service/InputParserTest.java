package com.epam.coffeecorner.service;

import com.epam.coffeecorner.model.Product;
import com.epam.coffeecorner.model.StampCard;
import com.epam.coffeecorner.service.impl.InputParserImpl;
import com.epam.coffeecorner.service.impl.ProductServiceImpl;
import com.epam.coffeecorner.service.impl.StampCardServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.epam.coffeecorner.model.TypeOfProduct.*;

public class InputParserTest {

    private StampCardService stampCardService = new StampCardServiceImpl();
    private ProductService productService = new ProductServiceImpl();

    InputParser inputParser = new InputParserImpl(productService, stampCardService);

    @Test
    void testParseExistingStampCard() {
        List<StampCard> cards = stampCardService.getAllStampCards();
        StampCard card1 = new StampCard(1, 5);
        StampCard card2 = new StampCard(1111, 3);
        cards.add(card1);
        cards.add(card2);

        StampCard foundCard1 = inputParser.findStampCardByInputString("1");
        StampCard foundCard1111 = inputParser.findStampCardByInputString("1111");

        Assertions.assertEquals(card2, foundCard1111);
        Assertions.assertEquals(card1, foundCard1);
    }

    @Test
    void testParseNotExistingStampCard() {
        StampCard foundCard = inputParser.findStampCardByInputString("10001");

        Assertions.assertEquals(null, foundCard);

    }

    @Test
    void testTryToParseInputWIthWrongIdFormatStampCard() {
        StampCard foundCard = inputParser.findStampCardByInputString("qwer");

        Assertions.assertEquals(null, foundCard);

    }

    @Test
    void testParseExistingProducts() {
        List<Product> expectedProducts = new ArrayList<>();
        Product largeCoffee = new Product("Large coffee", 3.5, BEVERAGE);
        largeCoffee.setExtra(new Product("extra milk", 0.3, EXTRAS));
        Product smallCoffee = new Product("Small coffee", 2.5, BEVERAGE);
        smallCoffee.setExtra(new Product("special roast", 0.9, EXTRAS));
        Product baconRoll = new Product("Bacon Roll", 4.5, SNACK);
        Product juice = new Product("Orange juice (0.25l)", 3.95, BEVERAGE);
        expectedProducts.add(largeCoffee);
        expectedProducts.add(smallCoffee);
        expectedProducts.add(baconRoll);
        expectedProducts.add(juice);


        List<Product> actualProducts = inputParser.parseProductsFrom("large coffee with extra milk, small coffee with special roast," +
                " bacon roll, orange juice");

        Assertions.assertEquals(expectedProducts, actualProducts);

    }

    @Test
    void testParseNotAllExistingProducts() {
        List<Product> expectedProducts = new ArrayList<>();
        Product baconRoll = new Product("Bacon Roll", 4.5, SNACK);
        expectedProducts.add(baconRoll);

        List<Product> actualProducts = inputParser.parseProductsFrom("hahaha," +
                " bacon roll, tratata");

        Assertions.assertEquals(expectedProducts, actualProducts);

    }

    @Test
    void testParseNotAllExistingExtras() {
        List<Product> expectedProducts = new ArrayList<>();
        Product smallCoffee = new Product("Small coffee", 2.5, BEVERAGE);
        expectedProducts.add(smallCoffee);

        List<Product> actualProducts = inputParser.parseProductsFrom("small coffee with tratata");

        Assertions.assertEquals(expectedProducts, actualProducts);

    }

    @Test
    void testParseExistingWithSpaces() {
        List<Product> expectedProducts = new ArrayList<>();
        Product smallCoffee = new Product("Small coffee", 2.5, BEVERAGE);
        Product baconRoll = new Product("Bacon Roll", 4.5, SNACK);
        expectedProducts.add(smallCoffee);
        expectedProducts.add(baconRoll);

        List<Product> actualProducts = inputParser.parseProductsFrom("small coffee    ,   bacon roll  ");

        Assertions.assertEquals(expectedProducts, actualProducts);

    }

    @Test
    void testParseExistingWithDifferentLetters() {
        List<Product> expectedProducts = new ArrayList<>();
        Product smallCoffee = new Product("Small coffee", 2.5, BEVERAGE);
        Product baconRoll = new Product("Bacon Roll", 4.5, SNACK);
        expectedProducts.add(smallCoffee);
        expectedProducts.add(baconRoll);

        List<Product> actualProducts = inputParser.parseProductsFrom("smaLL CoffeE, BAcon ROLL  ");

        Assertions.assertEquals(expectedProducts, actualProducts);

    }

}
