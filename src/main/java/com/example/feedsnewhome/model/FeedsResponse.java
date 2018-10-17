package com.example.feedsnewhome.model;

import lombok.Data;

import java.util.List;

/**
 * Created by zhangjiajia on 18-10-16
 */
@Data
public class FeedsResponse {

    private String requestId;
    private List<CardResponse> cardsList;
}


