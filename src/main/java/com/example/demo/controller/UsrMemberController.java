package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.MemberService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Member;

@Controller
public class UsrMemberController {

	@Autowired
	private MemberService memberService;

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public Object doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {

		if (Ut.isEmptyOrNull(loginId)) {
			return "loginId를 입력 해주세요";
		}
		if (Ut.isEmptyOrNull(loginPw)) {
			return "loginPw를 입력 해주세요";
		}
		if (Ut.isEmptyOrNull(name)) {
			return "name를 입력해주세요";
		}
		if (Ut.isEmptyOrNull(nickname)) {
			return "nickname를 입력해주세요";
		}
		if (Ut.isEmptyOrNull(cellphoneNum)) {
			return "cellphoneNum를 입력해주세요";
		}
		if (Ut.isEmptyOrNull(email)) {
			return "email를 입력해주세요";
		}

		int id = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);

		if (id == -1) {
			return "이미 사용중인 아이디";
		}

		Member member = memberService.getMemberById(id);

		return member;
	}

}