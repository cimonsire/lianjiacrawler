package com.fuchun.lianjiacrawler.crawler;

import com.fuchun.lianjiacrawler.crawler.downloader.ErshoufangListPageDownloader;
import com.fuchun.lianjiacrawler.crawler.processor.ErshoufangDetailPageProcessor;
import com.fuchun.lianjiacrawler.crawler.processor.ErshoufangListPageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.handler.CompositePageProcessor;
import us.codecraft.webmagic.handler.CompositePipeline;
import us.codecraft.webmagic.handler.SubPageProcessor;
import us.codecraft.webmagic.handler.SubPipeline;

import java.util.HashSet;
import java.util.Set;

@Component("lianjiaCrawler")
public class Crawler {

    private Downloader downloader;

    private SubPageProcessor[] subPageProcessors;

    private SubPipeline[] subPipelines;

    @Autowired
    public void setDownloader(Downloader downloader) {
        this.downloader = downloader;
    }

    @Autowired
    public void setSubPageProcessors(SubPageProcessor[] subPageProcessors) {
        this.subPageProcessors = subPageProcessors;
    }

    @Autowired
    public void setSubPipelines(SubPipeline[] subPipelines) {
        this.subPipelines = subPipelines;
    }

    public void run() {
        CompositePageProcessor ershoufangProcessor
                = new CompositePageProcessor(Site.me().setSleepTime(10000).setRetryTimes(3));
        ershoufangProcessor.setSubPageProcessors(subPageProcessors);

        CompositePipeline ershoufangPipeline = new CompositePipeline();
        ershoufangPipeline.setSubPipeline(subPipelines);

        Spider.create(ershoufangProcessor)
                .setDownloader(downloader)
                .addPipeline(ershoufangPipeline)
                .addUrl("https://cq.lianjia.com/ershoufang/rs/")
                .thread(1)
                .run();
    }
}
