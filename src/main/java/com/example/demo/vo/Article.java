package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {

	private int id;
	private String regDate;
	private String updateDate;
	private int memberId;
	private int boardId;
	private String title;
	private String body;
	private int hitCount;

	private String extra__writer;
	
	private String goodReactionPoint;
	private String badReactionPoint;
	
	private boolean userCanModify;
	private boolean userCanDelete;
}