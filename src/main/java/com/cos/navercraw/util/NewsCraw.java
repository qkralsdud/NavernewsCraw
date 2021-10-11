package com.cos.navercraw.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cos.navercraw.domain.News;

@Component
public class NewsCraw {
	int aidNum = 1;

	public List<News> collect() {
		RestTemplate rt = new RestTemplate();
		List<News> newsList = new ArrayList<>();

		String aid = String.format("%010d", aidNum);
		String url = "https://news.naver.com/main/read.naver?mode=LSD&mid=shm&sid1=103&oid=437&aid=" + aid;

		try {
			String html = rt.getForObject(url, String.class);

			Document doc = Jsoup.parse(html);

			Element companyElement = doc.selectFirst("#main_content > div.article_header > div.press_logo > a > img");
			Element titleElement = doc.selectFirst("#articleTitle");
			Element createdAtElement = doc.selectFirst(".t11");
			String company = companyElement.attr("alt");
			String title = titleElement.text();
			String cAt = createdAtElement.text();

			Timestamp tcAt = Timestamp.valueOf(cAt);
			LocalDateTime lcAt = tcAt.toLocalDateTime().minusDays(1).plusHours(9);
			Timestamp createdAt = Timestamp.valueOf(lcAt);

			LocalDateTime startTime = LocalDateTime.now().minusDays(1).plusHours(9);
			Timestamp startTs = Timestamp.valueOf(startTime);

			if (createdAt == startTs) {
				News news = News.builder().company(company).title(title).createdAt(createdAt).build();

				newsList.add(news);
			}

		} catch (Exception e) {
			System.out.println("오류:" + e.getMessage());
		}

		aidNum++;

		return newsList;
	}

}
