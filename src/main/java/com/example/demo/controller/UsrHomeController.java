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
public class UsrHomeController {

	@RequestMapping("/usr/home/getArticle")
	@ResponseBody
	public Article getArticle() {

		Article article = new Article(1, "제목1", "내용1");

		return article;
	}



}

