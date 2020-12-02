package com.epam.coffeecorner.service.impl;

import com.epam.coffeecorner.model.Constants;
import com.epam.coffeecorner.model.Product;
import com.epam.coffeecorner.model.Receipt;
import com.epam.coffeecorner.model.StampCard;
import com.epam.coffeecorner.service.OrderProcessor;
import com.epam.coffeecorner.service.PrintService;
import com.epam.coffeecorner.service.ProductService;
import com.epam.coffeecorner.service.StampCardService;

import java.util.List;

public class PrintServiceImpl implements PrintService {

    private ProductService productService;
    private StampCardService stampCardService;
    private OrderProcessor orderProcessor;

    public PrintServiceImpl(ProductService productService, StampCardService stampCardService, OrderProcessor orderProcessor) {
        this.productService = productService;
        this.stampCardService = stampCardService;
        this.orderProcessor = orderProcessor;
    }


    @Override
    public void printProducts(List<Product> productList) {
        for (Product product : productList) {
            System.out.println(product.toString());
        }
    }

    @Override
    public void printProductsReceiptView(List<Product> productList) {
        for (Product product : productList) {
            if (product.getExtra() == null) {
                System.out.println(String.format("%-20s %20s", product.getName(),
                        Constants.CURRENCY + String.format(" %,.2f", product.getPrice())));
            } else {
                System.out.print(String.format("%-20s", product.getName() + " with"));

                System.out.println(String.format("\n%-20s %20s",
                        product.getExtra().getName(),
                        Constants.CURRENCY + String.format(" %,.2f", product.getPrice() + product.getExtra().getPrice())));
            }
        }
    }

    @Override
    public void printProductsMenu() {
        System.out.println("\nMenu:");
        printProducts(productService.getProductList());
    }

    @Override
    public void printAllExistingStampCards() {
        System.out.println("\nExisting Stamp cards:");
        for (StampCard stampCard : stampCardService.getAllStampCards()) {
            System.out.println(stampCard.toString());
        }
    }

    @Override
    public void printReceipt(StampCard stampCard, List<Product> products) {
        Receipt receipt = orderProcessor.process(stampCard, products);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(receipt.getHeader());
        System.out.println();
        printProductsReceiptView(receipt.getOrderedProduct());
        System.out.println(Constants.DOTS + String.format("%-20s %20s", "Total:",
                String.format(Constants.CURRENCY + " %,.2f", receipt.getSum())));
        System.out.println(receipt.getFooter());
        System.out.println();
        System.out.println();
        System.out.println();

    }
}
