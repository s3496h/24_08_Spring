package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.MemberService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.Member;
import com.example.demo.vo.ResultData;
import com.example.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UsrMemberController {

    @Autowired
    private Rq rq;  // Rq 객체를 주입받음 (로그인 상태 관리 및 기타 유틸리티 기능 제공)

    @Autowired
    private MemberService memberService;  // 회원 관련 서비스 객체를 주입받음

    /**
     * 로그아웃 처리 메소드
     * @param req HttpServletRequest 객체
     * @return 로그아웃 성공 메시지와 함께 홈 페이지로 리다이렉트
     */
    @RequestMapping("/usr/member/doLogout")
    @ResponseBody
    public String doLogout(HttpServletRequest req) {
        rq.logout();  // 현재 로그인된 사용자를 로그아웃 처리
        return Ut.jsReplace("S-1", Ut.f("로그아웃 성공"), "/");  // 성공 메시지와 함께 홈 페이지로 리다이렉트
    }

    /**
     * 로그인 페이지를 보여주는 메소드
     * @paramreq HttpServletRequest 객체
     * @return 로그인 페이지의 뷰 이름
     */
    @RequestMapping("/usr/member/login")
    public String showLogin(HttpServletRequest req) {
        return "/usr/member/login";  // 로그인 페이지로 이동
    }

    /**
     * 로그인 처리 메소드
     * @param req HttpServletRequest 객체
     * @param loginId 로그인 ID
     * @param loginPw 로그인 비밀번호
     * @param afterLoginUri 로그인 후 리다이렉트할 URI
     * @return 로그인 성공 메시지와 함께 리다이렉트 또는 이전 페이지로 돌아감
     */
    @RequestMapping("/usr/member/doLogin")
    @ResponseBody
    public String doLogin(HttpServletRequest req, String loginId, String loginPw, String afterLoginUri) {
        Rq rq = (Rq) req.getAttribute("rq");  // 요청에서 Rq 객체를 가져옴

        // 입력값 검증
        if (Ut.isEmptyOrNull(loginId)) {
            return Ut.jsHistoryBack("F-1", "loginId 입력 x");
        }
        if (Ut.isEmptyOrNull(loginPw)) {
            return Ut.jsHistoryBack("F-2", "loginPw 입력 x");
        }

        // 회원 정보 조회
        Member member = memberService.getMemberByLoginId(loginId);
        if (member == null) {
            return Ut.jsHistoryBack("F-3", Ut.f("%s는(은) 존재 x", loginId));
        }

        // 비밀번호 확인
        if (!member.getLoginPw().equals(loginPw)) {
            return Ut.jsHistoryBack("F-4", Ut.f("비밀번호 틀림"));
        }

        rq.login(member);  // 로그인 처리
        // 로그인 후 URI가 존재하면 해당 URI로 리다이렉트, 없으면 홈 페이지로 리다이렉트
        if (afterLoginUri != null && afterLoginUri.length() > 0) {
            return Ut.jsReplace("S-1", Ut.f("%s님 환영합니다", member.getNickname()), afterLoginUri);
        }
        return Ut.jsReplace("S-1", Ut.f("%s님 환영합니다", member.getNickname()), "/");
    }

    /**
     * 회원 가입 페이지를 보여주는 메소드
     * @param req HttpServletRequest 객체
     * @return 회원 가입 페이지의 뷰 이름
     */
    @RequestMapping("/usr/member/join")
    public String showJoin(HttpServletRequest req) {
        return "/usr/member/join";  // 회원 가입 페이지로 이동
    }

    /**
     * 회원 가입 처리 메소드
     * @param req HttpServletRequest 객체
     * @param loginId 로그인 ID
     * @param loginPw 로그인 비밀번호
     * @param name 이름
     * @param nickname 닉네임
     * @param cellphoneNum 전화번호
     * @param email 이메일
     * @return 회원 가입 성공 메시지와 함께 로그인 페이지로 리다이렉트
     */
    @RequestMapping("/usr/member/doJoin")
    @ResponseBody
    public String doJoin(HttpServletRequest req, String loginId, String loginPw, String name, String nickname,
            String cellphoneNum, String email) {

        // 입력값 검증
        if (Ut.isEmptyOrNull(loginId)) {
            return Ut.jsHistoryBack("F-1", "loginId 입력 x");
        }
        if (Ut.isEmptyOrNull(loginPw)) {
            return Ut.jsHistoryBack("F-2", "loginPw 입력 x");
        }
        if (Ut.isEmptyOrNull(name)) {
            return Ut.jsHistoryBack("F-3", "name 입력 x");
        }
        if (Ut.isEmptyOrNull(nickname)) {
            return Ut.jsHistoryBack("F-4", "nickname 입력 x");
        }
        if (Ut.isEmptyOrNull(cellphoneNum)) {
            return Ut.jsHistoryBack("F-5", "cellphoneNum 입력 x");
        }
        if (Ut.isEmptyOrNull(email)) {
            return Ut.jsHistoryBack("F-6", "email 입력 x");
        }

        // 회원 가입 처리
        ResultData joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNum, email);
        if (joinRd.isFail()) {
            return Ut.jsHistoryBack(joinRd.getResultCode(), joinRd.getMsg());
        }

        // 가입된 회원 정보 조회
        Member member = memberService.getMemberById((int) joinRd.getData1());
        return Ut.jsReplace(joinRd.getResultCode(), joinRd.getMsg(), "../member/login");
    }

    /**
     * 내 정보 페이지를 보여주는 메소드
     * @return 내 정보 페이지의 뷰 이름
     */
    @RequestMapping("/usr/member/myPage")
    public String showmyPage() {
        return "usr/member/myPage";  // 내 정보 페이지로 이동
    }

    /**
     * 비밀번호 확인 페이지를 보여주는 메소드
     * @return 비밀번호 확인 페이지의 뷰 이름
     */
    @RequestMapping("/usr/member/checkPw")
    public String showCheckPw() {
        return "usr/member/checkPw";  // 비밀번호 확인 페이지로 이동
    }
	@RequestMapping("/usr/member/doCheckPw")
	@ResponseBody
	public String doCheckPw(String loginPw) {
		if (Ut.isEmptyOrNull(loginPw)) {
			return Ut.jsHistoryBack("F-1","비번 써");
		}

		if (rq.getLoginedMember().getLoginPw().equals(loginPw) == false) {
			return Ut.jsHistoryBack("F-2","비번 틀림");
		}

		return Ut.jsReplace("S-1", Ut.f("비밀번호 확인 성공"), "modify");
	}

	@RequestMapping("/usr/member/modify")
	public String showmyModify() {
		return "usr/member/modify";
	}
	@RequestMapping("/usr/member/doModify")
	public String doModify(HttpServletRequest req, int id,String loginId, String loginPw, String name, String nickname,
            String cellphoneNum, String email) {
		  // 요청에서 Rq 객체를 가져옴
        Rq rq = (Rq) req.getAttribute("rq");

        // Null 체크 x 비번은 안바꾸는거 가능
        if (Ut.isEmptyOrNull(name)) {
            return Ut.jsHistoryBack("F-3", "name 입력 x");
        }
        if (Ut.isEmptyOrNull(nickname)) {
            return Ut.jsHistoryBack("F-4", "nickname 입력 x");
        }
        if (Ut.isEmptyOrNull(cellphoneNum)) {
            return Ut.jsHistoryBack("F-5", "cellphoneNum 입력 x");
        }
        if (Ut.isEmptyOrNull(email)) {
            return Ut.jsHistoryBack("F-6", "email 입력 x");
        }
        
        ResultData modifyRd;
         
    	if (Ut.isEmptyOrNull(loginPw)) {
			modifyRd = memberService.modifyWithoutPw(rq.getLoginedMemberId(), name, nickname, cellphoneNum, email);
		} else {
			modifyRd = memberService.modify(rq.getLoginedMemberId(), loginPw, name, nickname, cellphoneNum, email);
		}
      

        return Ut.jsReplace(modifyRd.getResultCode(), modifyRd.getMsg(), "../member/myPage");
	}
	@RequestMapping("/usr/member/getLoginIdDup")
	@ResponseBody
	public ResultData getLoginIdDup(String loginId) {

		if (Ut.isEmpty(loginId)) {
			return ResultData.from("F-1", "아이디를 입력해주세요");
		}

		Member existsMember = memberService.getMemberByLoginId(loginId);

		if (existsMember != null) {
			return ResultData.from("F-2", "해당 아이디는 이미 사용중이야", "loginId", loginId);
		}

		return ResultData.from("S-1", "사용 가능!", "loginId", loginId);
	}
} 
	 