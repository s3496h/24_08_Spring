package com.example.demo.vo;

import java.io.IOException;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.example.demo.service.MemberService;
import com.example.demo.util.Ut;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Rq {
    
    // 현재 로그인 상태를 나타내는 플래그
    @Getter
    private boolean isLogined;
    
    // 로그인된 회원의 ID를 저장
    @Getter
    private int loginedMemberId;
    
    // 로그인된 회원의 정보를 저장
    @Getter
    private Member loginedMember;

    // 요청과 응답 객체를 저장
    private HttpServletRequest req;
    private HttpServletResponse resp;

    // 세션 객체를 저장
    private HttpSession session;

    // 생성자: HttpServletRequest, HttpServletResponse, MemberService 객체를 받아 초기화
    public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) {
        this.req = req;
        this.resp = resp;
        this.session = req.getSession();
        HttpSession httpSession = req.getSession();
        
        // 세션에 저장된 로그인 정보 확인 및 설정
        if (httpSession.getAttribute("loginedMemberId") != null) {
            isLogined = true;
            loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
            loginedMember = memberService.getMemberById(loginedMemberId);
        }

        // 현재 Rq 객체를 요청에 설정하여 다른 곳에서도 접근 가능하도록 함
        this.req.setAttribute("rq", this);
    }

    // 경고 메시지를 출력하고 이전 페이지로 이동시키는 메서드
    public void printHistoryBack(String msg) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        println("<script>");
        if (!Ut.isEmpty(msg)) {
            println("alert('" + msg + "');");
        }
        println("history.back();");
        println("</script>");
    }

    // 문자열을 줄 바꿈 포함하여 출력하는 메서드
    private void println(String str) {
        print(str + "\n");
    }

    // 문자열을 출력하는 메서드
    private void print(String str) {
        try {
            resp.getWriter().append(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 로그아웃 처리: 세션에서 로그인 정보를 제거
    public void logout() {
        session.removeAttribute("loginedMemberId");
        session.removeAttribute("loginedMember");
    }

    // 로그인 처리: 세션에 로그인된 회원의 정보를 저장
    public void login(Member member) {
        session.setAttribute("loginedMemberId", member.getId());
        session.setAttribute("loginedMember", member);
    }

    // 액션 인터셉터 초기화 전에 실행되는 메서드 (디버깅용 메시지 출력)
    public void initBeforeActionInterceptor() {
        System.err.println("initBeforeActionInterceptor 실행");
    }

    // 메시지를 포함하여 이전 페이지로 이동시키는 뷰 반환 메서드
    public String historyBackOnView(String msg) {
        req.setAttribute("msg", msg);
        req.setAttribute("historyBack", true);
        return "usr/common/js";
    }

    // 현재 요청 URI를 반환하는 메서드
    public String getCurrentUri() {
        String currentUri = req.getRequestURI();
        String queryString = req.getQueryString();
        System.err.println(currentUri);
        System.err.println(queryString);
        
        // 쿼리 스트링이 있는 경우 URI에 추가
        if (currentUri != null && queryString != null) {
            currentUri += "?" + queryString;
        }
        System.out.println(currentUri);
        return currentUri;
    }

	
	public void printReplace(String resultCode, String msg, String replaceUri) {
		resp.setContentType("text/html; charset=UTF-8");
		print(Ut.jsReplace(resultCode, msg, replaceUri));
	}

	public String getEncodedCurrentUri() {
		return Ut.getEncodedCurrentUri(getCurrentUri());
	}
	public String getLoginUri() {
		return "../member/login?afterLoginUri=" + getAfterLoginUri();
	}

	private String getAfterLoginUri() {
		return getEncodedCurrentUri();
	}
}