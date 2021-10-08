package com.cos.navercraw.web;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.navercraw.domain.News;
import com.cos.navercraw.domain.NewsRepository;
import com.cos.navercraw.util.NewsCraw;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@RestController
public class NewsController {
	private final NewsRepository newsRepository;
	private final NewsCraw newsCraw;
	
	@GetMapping(value = "/news", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<News> findAll(){
		return newsRepository.mFindAll()
				.subscribeOn(Schedulers.boundedElastic());
	}
	
	@PostMapping("/news")
	public Flux<News> newsCrawSave(){
		List<News> newsList = newsCraw.collect();
		return newsRepository.saveAll(newsList);
	}
}
