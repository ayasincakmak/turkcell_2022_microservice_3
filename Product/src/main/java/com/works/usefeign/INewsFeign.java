package com.works.usefeign;

import com.works.props.NewsData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "news", url = "https://newsapi.org/v2/")
public interface INewsFeign {

    @GetMapping("top-headlines")
    NewsData news(
            @RequestParam String country,
            @RequestParam String category,
            @RequestParam String apiKey
    );

}
