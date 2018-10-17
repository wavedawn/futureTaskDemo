package com.example.feedsnewhome.service;

import com.example.feedsnewhome.model.*;
import lombok.extern.slf4j.Slf4j;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * Created by zhangjiajia on 18-10-16
 */
@Slf4j
@Service
public class FeedsService {
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public FeedsResponse getNewHome(FeedsRequest feedsRequest) {
        log.info("request: {}", feedsRequest);
        long startTime = System.currentTimeMillis();

        List<CardResponse> cardResponses = new ArrayList<>();
        for (CardRequest cardRequest : feedsRequest.getCardRequests()) {
            int cardId = cardRequest.getCardId();
            log.info("cardId: {}", cardId);
            cardResponses.add(getCard(cardId, cardId));
        }

        FeedsResponse feedsResponse = new FeedsResponse();
        feedsResponse.setRequestId(UUID.randomUUID().toString());
        feedsResponse.setCardsList(cardResponses);
        log.info("cost time: {}", System.currentTimeMillis() - startTime);
        log.info("response: {}", feedsResponse);
        return feedsResponse;
    }

    private CardResponse getCard(int index, int cardId) {
        log.info("getCard, threadID: {}", Thread.currentThread().getId());

        String sourceName = "";
        if (cardId == 1) {
            sourceName = "今日头条";
        } else if (cardId == 2) {
            sourceName = "微博";
        }
        List<CardResponseTab> tabList = new ArrayList<>();
        tabList.add(getTab());
        tabList.add(getTab());

        CardResponse card = new CardResponse();
        card.setIndex(index);
        card.setCardId(cardId);
        card.setSourceName(sourceName);
        card.setTabs(tabList);
        card.setTabNum(tabList.size());
        int n = 1000000;
        while (n > 0) {
            long current = Calendar.getInstance().getTimeInMillis();
            n--;
        }
        return card;
    }

    private CardResponseTab getTab() {
        CardResponseTab tab = new CardResponseTab();
        tab.setTabName("推荐");
        tab.setTabId(0);
        tab.setTitleColor("ff5511");
        tab.setSelectedTitleColor("00dd66");
        List<CardResponseTabItem> itemList = new ArrayList<>();
        itemList.add(getItem());
        itemList.add(getItem());
        tab.setItems(itemList);
        return tab;
    }

    private CardResponseTabItem getItem() {
        CardResponseTabItem item = new CardResponseTabItem();
        item.setId("6658809ae94bda0d7f3fe0321deaa441");
        item.setTitle("杨紫票数反超热巴却未获奖，真相来了！昨天杨紫这样回应金鹰节！");
        item.setUrl("https://xw.qq.com/partner/mibrowser/20181015A0L63J/20181015A0L63J00?ADTAG=mibrowser&pgv_ref=mibrowser&name=mibrowser");
        List<String> images = new ArrayList<>();
        images.add("https://inews.gtimg.com/newsapp_bt/0/5790091182/641");
        images.add("https://inews.gtimg.com/newsapp_bt/0/5774427776/641");
        item.setImages(images.toString());
        item.setImgNum(images.size());
        return item;
    }

    public FeedsResponse getNewHomeConcurrent(FeedsRequest feedsRequest) {
        log.info("request: {}", feedsRequest);
        long startTime = System.currentTimeMillis();
        List<FutureTask<CardResponse>> futureTaskList = new ArrayList<>();
        for (CardRequest cardRequest : feedsRequest.getCardRequests()) {
            int cardId = cardRequest.getCardId();
            FutureTask<CardResponse> futureTask = new FutureTask<>(new CardTask(cardId));
            executorService.execute(futureTask);
            futureTaskList.add(futureTask);
        }

        List<CardResponse> cardResponseList = new ArrayList<>();
        for (FutureTask<CardResponse> futureTask : futureTaskList) {
            if (!futureTask.isDone()) {
                log.info("future task is not done");
            }
            try {
                CardResponse cardResponse = futureTask.get();
                log.info("card id: {}, sourceName: {}", cardResponse.getCardId(), cardResponse.getSourceName());
                cardResponseList.add(cardResponse);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        FeedsResponse feedsResponse = new FeedsResponse();
        feedsResponse.setCardsList(cardResponseList);
        feedsResponse.setRequestId(UUID.randomUUID().toString());
        log.info("cost time: {}", System.currentTimeMillis() - startTime);
        log.info("response: {}", feedsResponse);
        return feedsResponse;
    }

    class CardTask implements Callable<CardResponse> {
        private int cardId;

        private CardTask(int cardId) {
            this.cardId = cardId;
        }

        @Override
        public CardResponse call() {
            CardResponse card = getCard(this.cardId, this.cardId);
            return card;
        }
    }

}
