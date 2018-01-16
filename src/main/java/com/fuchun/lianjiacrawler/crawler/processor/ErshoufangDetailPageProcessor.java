package com.fuchun.lianjiacrawler.crawler.processor;

import com.fuchun.lianjiacrawler.model.dto.Ershoufang;
import org.jsoup.nodes.Document;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.handler.SubPageProcessor;

public class ErshoufangDetailPageProcessor implements SubPageProcessor {
    @Override
    public MatchOther processPage(Page page) {
        Ershoufang ershoufang = new Ershoufang();

        Document ershoufangDetail = page.getHtml().getDocument();

        float price = Float.parseFloat(ershoufangDetail
                        .selectFirst("body > div.overview > div.content > div.price > span.total")
                        .ownText());
        ershoufang.setPrice(price);

        Ershoufang.Room room = new Ershoufang.Room();
        room.setMainInfo(ershoufangDetail
                .selectFirst("body > div.overview > div.content > div.houseInfo > div.room > div.mainInfo").ownText());
        page.getUrl();
        return MatchOther.NO;
    }

    @Override
    public boolean match(Request page) {
        return !page.getUrl().contains("/rs/");
    }
}
