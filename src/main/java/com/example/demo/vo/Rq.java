package com.example.demo.vo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;

public class Rq {

	@Getter
	private boolean isLogined;
	@Getter
	private int loginedMemberId;

	public Rq(HttpServletRequest req) {
		
		HttpSession httpSession = req.getSession();
		
		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}
	}
}