package com.epam.coffeecorner.service;

import com.epam.coffeecorner.model.StampCard;

import java.util.List;

public interface StampCardService {

    StampCard getStampCardById(int id);

    List<StampCard> getAllStampCards();

}
