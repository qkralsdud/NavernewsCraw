package com.cos.navercraw.batch;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cos.navercraw.domain.News;
import com.cos.navercraw.domain.NewsRepository;
import com.cos.navercraw.util.NewsCraw;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class NewsBatch {
	private final NewsRepository newsRepository;
	private final NewsCraw newscraw;
	
	@Scheduled(fixedDelay = 1000*60*1)
	public void newsCrawSave() {
		List<News> newList = newscraw.collect();
		newsRepository.saveAll(newList);
	}

}
