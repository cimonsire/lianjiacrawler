package com.fuchun.lianjiacrawler.crawler;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerTest {

    private static Crawler crawler;

    @Autowired
    public void setCrawler(Crawler crawler) {
        CrawlerTest.crawler = crawler;
    }

    @Test
    public void run() {
        crawler.run();
    }
}