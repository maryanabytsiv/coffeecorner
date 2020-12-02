package com.epam.coffeecorner.model;

import java.util.List;

public class Receipt {

    String header;
    List<Product> orderedProduct;
    double sum;
    String footer;


    public Receipt(String header, List<Product> orderedProduct, double sum, String footer) {
        this.header = header;
        this.orderedProduct = orderedProduct;
        this.sum = sum;
        this.footer = footer;
    }

    @Override
    public String toString() {
        return "\n" + header
                + "\n" + orderedProduct +
                "\n" + footer;
    }

    public String getHeader() {
        return header;
    }

    public List<Product> getOrderedProduct() {
        return orderedProduct;
    }

    public String getFooter() {
        return footer;
    }

    public double getSum() {
        return sum;
    }
}
