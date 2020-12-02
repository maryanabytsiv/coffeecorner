package com.epam.coffeecorner.service.impl;

import com.epam.coffeecorner.model.Constants;
import com.epam.coffeecorner.model.StampCard;
import com.epam.coffeecorner.service.StampCardService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StampCardServiceImpl implements StampCardService {

    private static List<StampCard> stampCardList = new ArrayList<>();

    public StampCardServiceImpl() {
        generateStampCards(Constants.QUANTITY_OF_STAMP_CARDS);
    }

    private void generateStampCards(int quantity) {
        Random random = new Random();

        for (int i = 0; i < quantity - 1; i++) {
            stampCardList.add(new StampCard(random.nextInt(9999)
                    , Constants.NUMBER_OF_BEVERAGE_FOR_FREE_DEFAULT));
        }
    }

    @Override
    public StampCard getStampCardById(int id) {

        StampCard foundStampCard = stampCardList.stream()
                .filter(stampCard -> stampCard.getId() == id).findFirst().orElse(null);
        if (foundStampCard == null) {
            System.out.println(String.format("No such card with id '%s'", id));
        }
        return foundStampCard;
    }

    @Override
    public List<StampCard> getAllStampCards() {
        return stampCardList;
    }

}
