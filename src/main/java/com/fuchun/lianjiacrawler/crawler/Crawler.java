package com.fuchun.lianjiacrawler.crawler;

import com.fuchun.lianjiacrawler.crawler.downloader.ErshoufangListPageDownloader;
import com.fuchun.lianjiacrawler.crawler.processor.ErshoufangDetailPageProcessor;
import com.fuchun.lianjiacrawler.crawler.processor.ErshoufangListPageProcessor;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.handler.CompositePageProcessor;

import java.util.HashSet;
import java.util.Set;

@Component("lianjiaCrawler")
public class Crawler {

    public void run() {
        CompositePageProcessor ershoufangProcessor
                = new CompositePageProcessor(Site.me().setSleepTime(10000).setRetryTimes(3));

        ershoufangProcessor.addSubPageProcessor(new ErshoufangListPageProcessor());
        ershoufangProcessor.addSubPageProcessor(new ErshoufangDetailPageProcessor());


        Spider.create(ershoufangProcessor)
                .setDownloader(new ErshoufangListPageDownloader())
                .addUrl("https://cq.lianjia.com/ershoufang/rs/")
                .thread(1)
                .run();

//        ershoufangDetailUrlSpider.get
//        List<String> ershoufangDetailUrls = ershoufangDetailUrlSpider.getAll(urls);
//        ershoufangUrls.forEach();
    }
}
