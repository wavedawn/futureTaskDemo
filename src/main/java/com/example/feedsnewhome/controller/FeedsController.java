package com.example.feedsnewhome.controller;

import com.example.feedsnewhome.model.CardRequest;
import com.example.feedsnewhome.model.FeedsRequest;
import com.example.feedsnewhome.model.FeedsResponse;
import com.example.feedsnewhome.service.FeedsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjiajia on 18-10-16
 */
@Slf4j
@RestController
@RequestMapping("/")
public class FeedsController {

    @Autowired
    FeedsService feedsService;

    @GetMapping("newHome")
    public FeedsResponse getNewHome() {
        FeedsRequest feedsRequest = new FeedsRequest();
        List<CardRequest> cardRequestList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            CardRequest cardRequest = new CardRequest();
            cardRequest.setCardId(i + 1);
            cardRequestList.add(cardRequest);
        }
        feedsRequest.setCardRequests(cardRequestList);
        feedsRequest.setImeiSHA1("imeiSHA1");
        feedsRequest.setImeiMD5("imeiMD5");
        return feedsService.getNewHome(feedsRequest);
    }

    @GetMapping("newHomeConcur")
    public FeedsResponse getNewHomeParallel() {
        List<CardRequest> cardRequestList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            CardRequest card = new CardRequest();
            card.setCardId(i + 1);
            card.setTokenId("tokenid");
            cardRequestList.add(card);
        }
        FeedsRequest feedsRequest = new FeedsRequest();
        feedsRequest.setImeiSHA1("imeisha1");
        feedsRequest.setCardRequests(cardRequestList);
        return feedsService.getNewHomeConcurrent(feedsRequest);
    }
}
