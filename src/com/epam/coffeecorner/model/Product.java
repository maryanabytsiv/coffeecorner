package com.epam.coffeecorner.model;

import java.util.Objects;

public class Product {

    String name;
    double price;
    TypeOfProduct typeOfProduct;
    Product extra;

    public Product(String name, double price, TypeOfProduct typeOfProduct) {
        this.name = name;
        this.price = price;
        this.typeOfProduct = typeOfProduct;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public TypeOfProduct getTypeOfProduct() {
        return typeOfProduct;
    }

    public Product getExtra() {
        return extra;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setExtra(Product extra) {
        this.extra = extra;
    }

       @Override
    public String toString() {
        return name + String.format(" %,.2f", price)
                + String.format(" %s", Constants.CURRENCY);


    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
                name.equals(product.name) &&
                typeOfProduct == product.typeOfProduct &&
                Objects.equals(extra, product.extra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, typeOfProduct, extra);
    }
}
