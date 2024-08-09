package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.demo.vo.Article;

@Controller
public class UsrArticleController {
	int lastArticleId;
	List<Article> articles;

	public UsrArticleController() {
		lastArticleId = 3;
		articles = new ArrayList<>();
		makeTestData();
	}

	private Article writeArticle(String title, String body) {
		int id = lastArticleId + 1;
		Article article = new Article(id, title, body);
		articles.add(article);
		lastArticleId++;
		return article;
	}

	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		Article article = writeArticle(title, body);
		return article;
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		Article article = getArticlebyId(id);

		if (article == null) {
			return id + "번 글은 없음";
		}

		articles.remove(article);

		return id + "번 글이 삭제됨";

	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public Object doModify(int id,String title, String body) {
		Article article = getArticlebyId(id);

		if (article == null) {
			return id + "번 글은 없음";
		}
         article.setTitle(title);
         article.setBody(body);
		
         return id + "번글이 수정됨" + article;
	}

	private Article getArticlebyId(int id) {
		for (Article article : articles) {
			if (article.get(id) == id) {
				return article;
			}
		}
		return null;
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {

		return articles;

	}

	@RequestMapping("/usr/article/makeTestData")
	@ResponseBody
	public void makeTestData() {
		for (int i = 1; i <= 10; i++) {
			String title = "제목" + i;
			String body = "내용" + i;

			writeArticle(title, body);
		}

	}

}
