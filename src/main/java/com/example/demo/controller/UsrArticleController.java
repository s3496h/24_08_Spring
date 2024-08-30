package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ArticleService;
import com.example.demo.service.BoardService;
import com.example.demo.service.ReactionPointService;
import com.example.demo.service.ReplyService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.Board;
import com.example.demo.vo.Reply;
import com.example.demo.vo.ResultData;
import com.example.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UsrArticleController {

    // Rq 클래스의 인스턴스를 주입받아 사용
    @Autowired
    private Rq rq;

    // ArticleService 클래스의 인스턴스를 주입받아 사용
    @Autowired
    private ArticleService articleService;

    // BoardService 클래스의 인스턴스를 주입받아 사용
    @Autowired
    private BoardService boardService;

    // ReactionPointService 클래스의 인스턴스를 주입받아 사용
    @Autowired
    private ReactionPointService reactionPointService;
    
    // ReplyService 클래스의 인스턴스를 주입받아 사용
    @Autowired
    private ReplyService replyService;

    // 게시글 상세 페이지를 보여주는 메서드
    @RequestMapping("/usr/article/detail")
    public String showDetail(HttpServletRequest req, Model model, int id ) {

        // 요청에서 Rq 객체를 가져옴
        Rq rq = (Rq) req.getAttribute("rq");
        
        // 게시글 상세 정보를 가져옴
        Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
        
        // 사용자가 해당 게시글에 대해 반응할 수 있는지 확인
        ResultData usersReactionRd = reactionPointService.usersReaction(rq.getLoginedMemberId(), "article", id);

        if (usersReactionRd.isSuccess()) {
            model.addAttribute("userCanMakeReaction", usersReactionRd.isSuccess());
        }
        
        // 게시글에 대한 댓글 목록을 가져옴
        List<Reply> replies = replyService.getForPrintReplies(rq.getLoginedMemberId(), "article", id);
        
        int repliesCount = replies.size();
    
        // 모델에 필요한 데이터를 추가
        model.addAttribute("article", article);
        model.addAttribute("replies", replies);
        model.addAttribute("repliesCount", repliesCount);
        model.addAttribute("isAlreadyAddGoodRp",
                reactionPointService.isAlreadyAddGoodRp(rq.getLoginedMemberId(), id, "article"));
        model.addAttribute("isAlreadyAddBadRp",
                reactionPointService.isAlreadyAddBadRp(rq.getLoginedMemberId(), id, "article"));
        return "usr/article/detail";
    }

    // 조회수 증가 처리 메서드
    @RequestMapping("/usr/article/doIncreaseHitCountRd")
    @ResponseBody
    public ResultData doIncreaseHitCount(int id) {

        // 조회수 증가 작업 수행
        ResultData increaseHitCountRd = articleService.increaseHitCount(id);

        if (increaseHitCountRd.isFail()) {
            return increaseHitCountRd;
        }

        // 증가된 조회수를 반환
        ResultData rd = ResultData.newData(increaseHitCountRd, "hitCount", articleService.getArticleHitCount(id));
        
        rd.setData2("조회수가 증가된 게시물 번호", id);
        return rd;
    }

    // 게시글 수정 페이지를 보여주는 메서드
    @RequestMapping("/usr/article/modify")
    public String showModify(HttpServletRequest req, Model model, int id) {

        // 요청에서 Rq 객체를 가져옴
        Rq rq = (Rq) req.getAttribute("rq");

        // 수정하려는 게시글 정보를 가져옴
        Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

        if (article == null) {
            return Ut.jsHistoryBack("F-1", Ut.f("%d번 게시글은 없습니다", id));
        }

        model.addAttribute("article", article);

        return "/usr/article/modify";
    }

    // 게시글 수정 처리 메서드
    @RequestMapping("/usr/article/doModify")
    @ResponseBody
    public String doModify(HttpServletRequest req, int id, String title, String body) {

        // 요청에서 Rq 객체를 가져옴
        Rq rq = (Rq) req.getAttribute("rq");

        // 수정하려는 게시글 정보를 가져옴
        Article article = articleService.getArticleById(id);

        if (article == null) {
            return Ut.jsHistoryBack("F-1", Ut.f("%d번 게시글은 없습니다", id));
        }

        // 사용자가 해당 게시글을 수정할 수 있는지 확인
        ResultData userCanModifyRd = articleService.userCanModify(rq.getLoginedMemberId(), article);

        if (userCanModifyRd.isFail()) {
            return Ut.jsHistoryBack(userCanModifyRd.getResultCode(), userCanModifyRd.getMsg());
        }

        if (userCanModifyRd.isSuccess()) {
            articleService.modifyArticle(id, title, body);
        }

        article = articleService.getArticleById(id);

        return Ut.jsReplace(userCanModifyRd.getResultCode(), userCanModifyRd.getMsg(), "../article/detail?id=" + id);
    }

    // 게시글 삭제 처리 메서드
    @RequestMapping("/usr/article/doDelete")
    @ResponseBody
    public String doDelete(HttpServletRequest req, int id) {

        // 요청에서 Rq 객체를 가져옴
        Rq rq = (Rq) req.getAttribute("rq");

        // 삭제하려는 게시글 정보를 가져옴
        Article article = articleService.getArticleById(id);

        if (article == null) {
            return Ut.jsHistoryBack("F-1", Ut.f("%d번 게시글은 없습니다", id));
        }

        // 사용자가 해당 게시글을 삭제할 수 있는지 확인
        ResultData userCanDeleteRd = articleService.userCanDelete(rq.getLoginedMemberId(), article);

        if (userCanDeleteRd.isFail()) {
            return Ut.jsHistoryBack(userCanDeleteRd.getResultCode(), userCanDeleteRd.getMsg());
        }

        if (userCanDeleteRd.isSuccess()) {
            articleService.deleteArticle(id);
        }

        return Ut.jsReplace(userCanDeleteRd.getResultCode(), userCanDeleteRd.getMsg(), "../article/list");
    }

    // 게시글 작성 페이지를 보여주는 메서드
    @RequestMapping("/usr/article/write")
    public String showWrite(HttpServletRequest req) {

        return "usr/article/write";
    }

    // 게시글 작성 처리 메서드
    @RequestMapping("/usr/article/doWrite")
    @ResponseBody
    public String doWrite(HttpServletRequest req, String title, String body, String boardId) {

        // 요청에서 Rq 객체를 가져옴
        Rq rq = (Rq) req.getAttribute("rq");

        // 제목이 비어있는지 확인
        if (Ut.isEmptyOrNull(title)) {
            return Ut.jsHistoryBack("F-1", "제목을 입력해주세요");
        }
        // 내용이 비어있는지 확인
        if (Ut.isEmptyOrNull(body)) {
            return Ut.jsHistoryBack("F-2", "내용을 입력해주세요");
        }
        // 게시판 ID가 비어있는지 확인
        if (Ut.isEmptyOrNull(boardId)) {
            return Ut.jsHistoryBack("F-3", "게시판을 선택해주세요");
        }

        System.err.println(boardId);

        // 게시글 작성 작업 수행
        ResultData writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), title, body, boardId);

        int id = (int) writeArticleRd.getData1();

        Article article = articleService.getArticleById(id);

        return Ut.jsReplace(writeArticleRd.getResultCode(), writeArticleRd.getMsg(), "../article/detail?id=" + id);

    }

    // 게시글 목록 페이지를 보여주는 메서드
    @RequestMapping("/usr/article/list")
    public String showList(HttpServletRequest req, Model model, 
            @RequestParam(defaultValue = "1") int boardId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "title,body") String searchKeywordTypeCode,
            @RequestParam(defaultValue = "") String searchKeyword) throws IOException {
            
        // 요청에서 Rq 객체를 가져옴
        Rq rq = (Rq) req.getAttribute("rq");

        // 게시판 정보를 가져옴
        Board board = boardService.getBoardById(boardId);

        // 검색 조건에 맞는 게시글 수를 가져옴
        int articlesCount = articleService.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);

        // 한 페이지에 보여줄 게시글 수 설정
        int itemsInAPage = 10;

        // 전체 페이지 수 계산
        int pagesCount = (int) Math.ceil(articlesCount / (double) itemsInAPage);
        
        // 해당 페이지에 맞는 게시글 목록을 가져옴
        List<Article> articles = articleService.getForPrintArticles(boardId, itemsInAPage, page, searchKeywordTypeCode, searchKeyword);

        if (board == null) {
            return rq.historyBackOnView("없는 게시판임");
        }
              
        // 모델에 필요한 데이터를 추가
        model.addAttribute("articles", articles);
        model.addAttribute("articlesCount", articlesCount);
        model.addAttribute("pagesCount", pagesCount);
        model.addAttribute("board", board);
        model.addAttribute("page", page);
        model.addAttribute("boardId", boardId);
        model.addAttribute("searchKeywordTypeCode", searchKeywordTypeCode);
        model.addAttribute("searchKeyword", searchKeyword);

        return "usr/article/list";
    }
    
}