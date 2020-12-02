package com.epam.coffeecorner.service.impl;

import com.epam.coffeecorner.model.Product;
import com.epam.coffeecorner.model.StampCard;
import com.epam.coffeecorner.service.InputParser;
import com.epam.coffeecorner.service.ProductService;
import com.epam.coffeecorner.service.StampCardService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InputParserImpl implements InputParser {

    private ProductService productService;
    private StampCardService stampCardService;

    public InputParserImpl(ProductService productService, StampCardService stampCardService) {
        this.productService = productService;
        this.stampCardService = stampCardService;
    }

    @Override
    public List<Product> parseProductsFrom(String input) {
        List<String> orderPieces = Arrays.stream(input.split(","))
                .map(s -> s.trim()).collect(Collectors.toList());
        List<Product> orderedProducts = new ArrayList<>();
        Product foundProduct;
        for (String piece : orderPieces) {
            List<String> subPieces = Arrays.stream(piece.split("with"))
                    .map(s -> s.trim()).collect(Collectors.toList());
            foundProduct = productService.findProductByName(subPieces.get(0));
            if (foundProduct == null) {
                System.out.println(String.format("No product found under name %s", piece));
                continue;
            }
            addExtrasToProductIfExists(foundProduct, subPieces);
            orderedProducts.add(foundProduct);
        }
        return orderedProducts;
    }

    @Override
    public StampCard findStampCardByInputString(String cardNumber) {
        try {
            int id = Integer.valueOf(cardNumber.trim());
            return stampCardService.getStampCardById(id);
        } catch (NumberFormatException NFE) {
            System.out.println(String.format("Wrong format for card number. Should be Integer.\n", cardNumber));
        }
        return null;
    }

    private void addExtrasToProductIfExists(Product foundProduct, List<String> subPieces) {
        if (subPieces.size() <= 1) {
            return;
        }
        Product extra;
        for (int i = 1; i <= subPieces.size() - 1; i = i + 1) {
            extra = productService.findExtrasByName(subPieces.get(i));
            if (extra == null) {
                System.out.println(String.format("No extras found under name %s", subPieces.get(i)));
                continue;
            }else
                foundProduct.setExtra(extra);
        }
    }
}
