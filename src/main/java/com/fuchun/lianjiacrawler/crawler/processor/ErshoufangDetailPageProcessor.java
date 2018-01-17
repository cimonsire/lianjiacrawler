package com.fuchun.lianjiacrawler.crawler.processor;

import com.fuchun.lianjiacrawler.model.dto.Ershoufang;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.handler.SubPageProcessor;

import java.text.ParseException;
import java.util.Date;

@Slf4j
@Component
public class ErshoufangDetailPageProcessor implements SubPageProcessor {
    @Override
    public MatchOther processPage(Page page) {
        Ershoufang ershoufang = new Ershoufang();

        Document ershoufangDetail = page.getHtml().getDocument();

        float price = Float.parseFloat(ershoufangDetail
                        .selectFirst("body > div.overview > div.content > div.price > span.total")
                        .ownText());
        ershoufang.setPrice(price);

        Element houseInfoElement = ershoufangDetail.selectFirst("body > div.overview > div.content > div.houseInfo");
        Ershoufang.Room room = new Ershoufang.Room();
        room.setMainInfo(houseInfoElement.selectFirst("div.room > div.mainInfo").ownText());
        room.setSubInfo(houseInfoElement.selectFirst("div.room > div.subInfo").ownText());
        ershoufang.setRoom(room);
        // house type
        Ershoufang.Type type = new Ershoufang.Type();
        type.setMainInfo(houseInfoElement.selectFirst("div.type > div.mainInfo").ownText());
        type.setSubInfo(houseInfoElement.selectFirst("div.type > div.subInfo").ownText());
        ershoufang.setType(type);

        Element aroundInfoElement = ershoufangDetail.selectFirst("body > div.overview > div.content > div.aroundInfo");
        ershoufang.setCommunityName(aroundInfoElement.selectFirst("div.communityName > a.info").ownText());
        Ershoufang.Location location = new Ershoufang.Location();
        location.setDistrict(aroundInfoElement.selectFirst("div.areaName > span.info > a:nth-child(1)").ownText());
        location.setRoad(aroundInfoElement.selectFirst("div.areaName > span.info > a:nth-child(2)").ownText());
        location.setSupplement(aroundInfoElement.selectFirst("div.areaName > a").ownText());
        ershoufang.setLocation(location);

        ershoufang.setLianjiaId(ershoufangDetail.selectFirst("div.houseRecord > span.info").ownText());

        Ershoufang.Base base = new Ershoufang.Base();
        Elements baseElements = ershoufangDetail
                .select("#introduction > div > div > div.base > div.content > ul > li");
        baseElements.forEach(element -> {
            switch (element.selectFirst("span").ownText()) {
                case "房屋户型": base.setHouseType(element.ownText()); break;
                case "所在楼层": base.setFloor(element.ownText()); break;
                case "建筑面积": base.setGrossArea(Float.parseFloat(element.ownText().replace("㎡", ""))); break;
                case "户型结构": base.setStructure(element.ownText()); break;
                case "套内面积": base.setTeachingArea(Float.parseFloat(element.ownText().replace("㎡", ""))); break;
                case "建筑类型": base.setBuildingType(element.ownText()); break;
                case "房屋朝向": base.setOrientation(element.ownText()); break;
                case "建筑结构": base.setBuildingStructure(element.ownText()); break;
                case "装修情况": base.setDecoration(element.ownText()); break;
                case "梯户比例": base.setLadderHouseholdProportion(element.ownText()); break;
                case "配备电梯": base.setElevator(element.ownText()); break;
                case "产权年限": base.setPropertyRight(Integer.parseInt(element.ownText().replace("年", ""))); break;
                default: log.info("基本属性\"{}\"不需要抽取", element.selectFirst("span").ownText());
            }
        });

        Elements transactionElements = ershoufangDetail.select("#introduction > div > div > " +
                "div.transaction > div.content > ul > li");
        Ershoufang.Transaction transaction = new Ershoufang.Transaction();
        transactionElements.forEach(element -> {
            try {
                switch (element.selectFirst("span").ownText()) {
                    case "挂牌时间":
                        Date saleDate = DateUtils.parseDate(element.ownText(), "yyyy-MM-dd");
                        transaction.setSaleDate(saleDate);
                        break;
                    case "交易权属":
                        transaction.setTradingOwnership(element.ownText());
                        break;
                    case "上次交易":
                        Date lastTransactionDate = DateUtils.parseDate(element.ownText(), "yyyy-MM-dd");
                        transaction.setLastTransactionDate(lastTransactionDate);
                        break;
                    case "房屋用途":
                        transaction.setHouseUsage(element.ownText());
                        break;
                    case "房屋年限":
                        transaction.setServiceLife(element.ownText());
                        break;
                    case "产权所属":
                        transaction.setPropertyOwn(element.ownText());
                        break;
                    case "抵押信息":
                        transaction.setMortgage(element.ownText());
                        break;
                    default:
                        log.info("交易属性\"{}\"不需要抽取", element.selectFirst("span").ownText());
                }
            } catch (ParseException e) {
                log.error("交易属性\"{}\"=\"{}\"的日期格式不对",
                        element.selectFirst("span").ownText(), element.ownText());
            }
        });
        ershoufang.setTransaction(transaction);

        page.putField("ershoufang", ershoufang);
        return MatchOther.NO;
    }

    @Override
    public boolean match(Request page) {
        boolean isMatch = page.getUrl().matches("https://cq.lianjia.com/ershoufang/[0-9]+\\.html");

        if (isMatch) {
            log.info("url\"{}\"为二手房详情页，抽取该页二手房数据", page.getUrl());
        }

        return isMatch;
    }
}
