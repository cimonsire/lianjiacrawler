package com.fuchun.lianjiacrawler.crawler.downloader;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.selector.PlainText;

@Component
public class ErshoufangListPageDownloader implements Downloader {

    @Override
    public Page download(Request request, Task task) {
        WebDriver webDriver = new HtmlUnitDriver(true);
        webDriver.get(request.getUrl());
        Page page = new Page();
        page.setUrl(new PlainText(request.getUrl()));
        page.setCharset("utf-8");
        page.setDownloadSuccess(true);
        page.setRawText(webDriver.getPageSource());
        page.setRequest(request);
        return page;
    }

    @Override
    public void setThread(int threadNum) {
        return;
    }
}
