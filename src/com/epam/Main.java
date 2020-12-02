package com.epam;

import com.epam.coffeecorner.model.Product;
import com.epam.coffeecorner.model.StampCard;
import com.epam.coffeecorner.service.*;
import com.epam.coffeecorner.service.impl.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static ProductService productService = new ProductServiceImpl();
    public static StampCardServiceImpl stampCardService = new StampCardServiceImpl();
    public static OrderProcessorImpl orderProcessor = new OrderProcessorImpl();
    public static PrintService printService = new PrintServiceImpl(productService, stampCardService, orderProcessor);
    public static InputParser inputParser = new InputParserImpl(productService, stampCardService);

    public static void main(String[] args) {

        printService.printProductsMenu();
        printService.printAllExistingStampCards();


        System.out.print("============================");

        Scanner scanner = new Scanner(System.in);

        String inputString = "";
        do {
            System.out.print("\nEnter order - ");
            inputString = scanner.nextLine();

            List<Product> orderedProducts = inputParser.parseProductsFrom(inputString);

            System.out.print("Please enter Stamp card# if you have:");
            inputString = scanner.nextLine();
            StampCard stampCard = inputParser.findStampCardByInputString(inputString);

            printService.printReceipt(stampCard, orderedProducts);

            System.out.println("\nDo you want to enter a new order - (Y|N)");
            inputString = scanner.nextLine();
            checkExitCommand(inputString);
        } while (true);

    }

    private static void checkExitCommand(String inputString) {
        if (inputString.toLowerCase().equals("n")) {
            System.out.print("Exiting a program...");
            System.exit(0);
        } else {
            printService.printProductsMenu();
            printService.printAllExistingStampCards();
        }
    }
}
