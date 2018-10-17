package com.example.feedsnewhome.model;

import lombok.Data;

/**
 * Created by zhangjiajia on 18-10-16
 */
@Data
public class CardResponseTabItem {

    private String id;
    private String title;
    private String url;
    private int imgNum;
    private String images;
    private String source;
    private String comment;
    private String updateTime;
}
