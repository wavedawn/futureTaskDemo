package com.example.feedsnewhome.model;

import lombok.Data;

import java.util.List;

/**
 * Created by zhangjiajia on 18-10-16
 */
@Data
public class CardResponse {

    private int index;
    private int cardId;
    private String sourceName;
    private String sourceIcon;
    private String headBackgroundColor;
    private String headTitleColor;
    private int tabNum;
    private List<CardResponseTab> tabs;

}
