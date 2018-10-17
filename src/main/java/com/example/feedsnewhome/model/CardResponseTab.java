package com.example.feedsnewhome.model;

import lombok.Data;

import java.util.List;

/**
 * Created by zhangjiajia on 18-10-16
 */
@Data
public class CardResponseTab {

    private String tabName;
    private int tabId;
    private String titleColor;
    private String selectedTitleColor;
    private List<CardResponseTabItem> items;
}
