package com.fuchun.lianjiacrawler.crawler.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.handler.SubPageProcessor;

import java.util.List;

public class ErshoufangListPageProcessor implements SubPageProcessor {

    @Override
    public MatchOther processPage(Page page) {
        // 获取二手房后续页面的urls
        List<String> ershoufangListUrls = page.getHtml().css("body > div.content > div.leftContent " +
                "> div.contentBottom.clear > div.page-box.fr > div > a").links().all();
        page.addTargetRequests(ershoufangListUrls);
        // 获取二手房详细页面的urls
        List<String> ershoufangDetailUrls =  page.getHtml().css("body > div.content > " +
                "div.leftContent > ul > li > " +
                "div.info.clear > div.title > a").links().all();
        page.addTargetRequests(ershoufangDetailUrls);
        return MatchOther.NO;
    }

    @Override
    public boolean match(Request page) {
        return page.getUrl().contains("/rs/");
    }
}
