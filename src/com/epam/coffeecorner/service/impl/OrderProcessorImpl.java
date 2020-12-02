package com.epam.coffeecorner.service.impl;

import com.epam.coffeecorner.model.TypeOfProduct;
import com.epam.coffeecorner.model.Constants;
import com.epam.coffeecorner.model.Product;
import com.epam.coffeecorner.model.Receipt;
import com.epam.coffeecorner.model.StampCard;
import com.epam.coffeecorner.service.OrderProcessor;

import java.util.List;
import java.util.stream.Collectors;

public class OrderProcessorImpl implements OrderProcessor {

    @Override
    public Receipt process(StampCard stampCard, List<Product> orderedProducts) {
        String header = Constants.ADDRESS_AND_NAME + Constants.SPACES;

        if (stampCard != null) {
            setFreePriceByStampCard(stampCard, orderedProducts);
        }
        double sum = orderedProducts.stream().mapToDouble(Product::getPrice).sum();
        sum = addExtrasSum(orderedProducts, sum);
        String footer = ((stampCard == null) ? "" : stampCard.toString()) + Constants.FOOTER;
        return new Receipt(header, orderedProducts, sum, footer);
    }

    private double addExtrasSum(List<Product> orderedProducts, double sum) {
        for (Product product : orderedProducts
        ) {
            if (product.getExtra() != null)
                sum += product.getExtra().getPrice();

        }
        return sum;
    }

    private void setPriceToZeroForFreeBeverages(List<Product> beveragesInOrder, int freeBeverages) {
        for (int i = 0; i < freeBeverages; i = i + 1) {
            beveragesInOrder.get(i).setPrice(0);
        }
    }

    private int getQuantityOfFreeBeveragesInOrder(StampCard stampCard, int quantityOfBeverages) {
        int stampsOnCard = makeStampCardStamped(stampCard, quantityOfBeverages);
        return stampsOnCard / Constants.NUMBER_OF_BEVERAGE_FOR_FREE_DEFAULT;
    }

    private int makeStampCardStamped(StampCard stampCard, int quantityOfBeverages) {
        int stampsOnCard = stampCard.getNumberOfStampsAlreadyThere() + quantityOfBeverages;
        stampCard.setNumberOfStampsAlreadyThere(stampsOnCard % Constants.NUMBER_OF_BEVERAGE_FOR_FREE_DEFAULT);
        return stampsOnCard;
    }

    private List<Product> getBeverages(List<Product> orderedProducts) {
        return orderedProducts.stream()
                .filter(product -> product.getTypeOfProduct() == TypeOfProduct.BEVERAGE).collect(Collectors.toList());
    }

    private void setFreePriceByStampCard(StampCard stampCard, List<Product> orderedProducts) {
        List<Product> beveragesInOrder = getBeverages(orderedProducts);
        int quantityOfBeveragesInOrder = beveragesInOrder.size();
        if (beveragesInOrder.size() > 0) {
            int freeBeverages = getQuantityOfFreeBeveragesInOrder(stampCard, quantityOfBeveragesInOrder);
            if (freeBeverages > 0) {
                setPriceToZeroForFreeBeverages(beveragesInOrder, freeBeverages);
            }
        }
    }
}
