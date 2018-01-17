package com.fuchun.lianjiacrawler.crawler.pipeline;

import com.fuchun.lianjiacrawler.dao.LianjiaRepository;
import com.fuchun.lianjiacrawler.model.dto.Ershoufang;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.handler.SubPipeline;

@Component
@Slf4j
public class ErshoufangPipeline implements SubPipeline {

    private LianjiaRepository lianjiaRepository;

    @Autowired
    public void setLianjiaRepository(LianjiaRepository lianjiaRepository) {
        this.lianjiaRepository = lianjiaRepository;
    }

    @Override
    public MatchOther processResult(ResultItems resultItems, Task task) {
        Ershoufang ershoufang = resultItems.<Ershoufang>get("ershoufang");
        log.info("抽取到的二手房信息：{}", ershoufang);
        lianjiaRepository.save(ershoufang);

        return MatchOther.NO;
    }

    @Override
    public boolean match(Request page) {
        boolean isMatch = page.getUrl().matches("https://cq.lianjia.com/ershoufang/[0-9]+\\.html");

        if (isMatch) {
            log.info("url\"{}\"为二手房详情页数据，该页数据持久化到数据库", page.getUrl());
        }

        return isMatch;
    }
}
