package com.example.feedsnewhome.model;

import lombok.Data;

import java.util.List;

/**
 * Created by zhangjiajia on 18-10-16
 */
@Data
public class FeedsRequest {

    private String imeiSHA1;
    private String imeiMD5;
    private List<CardRequest> cardRequests;
}
