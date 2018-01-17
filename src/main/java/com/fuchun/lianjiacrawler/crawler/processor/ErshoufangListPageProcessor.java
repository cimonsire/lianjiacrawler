package com.fuchun.lianjiacrawler.crawler.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.handler.SubPageProcessor;

import java.util.List;

@Component
@Slf4j
public class ErshoufangListPageProcessor implements SubPageProcessor {

    @Override
    public MatchOther processPage(Page page) {
        // 获取二手房后续页面的urls
        List<String> ershoufangListUrls = page.getHtml().css("body > div.content > div.leftContent " +
                "> div.contentBottom.clear > div.page-box.fr > div > a").links().all();
        log.info("从url\"{}\"获取到列表页数量: {}, urls：{}", page.getUrl(), ershoufangListUrls.size(), ershoufangListUrls);
        page.addTargetRequests(ershoufangListUrls);
        // 获取二手房详细页面的urls
        List<String> ershoufangDetailUrls =  page.getHtml().css("body > div.content > " +
                "div.leftContent > ul > li > " +
                "div.info.clear > div.title > a").links().all();
        log.info("从url\"{}\"获取到详情页数量：{}, urls：{}", page.getUrl(), ershoufangDetailUrls.size(), ershoufangDetailUrls);
        page.addTargetRequests(ershoufangDetailUrls);
        return MatchOther.NO;
    }

    @Override
    public boolean match(Request page) {
        boolean isMatch = !page.getUrl().matches("https://cq.lianjia.com/ershoufang/[0-9]+\\.html");
        if (isMatch) {
            log.info("url\"{}\"为二手房列表页, 抽取该页列表数据和详情页数据", page.getUrl());
        }

        return isMatch;
    }
}
