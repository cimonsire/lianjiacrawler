package com.fuchun.lianjiacrawler.crawler;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CrawlerTest {

    private static Crawler crawler;

    @Before
    public void setUp() throws Exception {
        crawler = new Crawler();
    }

    @Test
    public void run() {
        crawler.run();
    }
}