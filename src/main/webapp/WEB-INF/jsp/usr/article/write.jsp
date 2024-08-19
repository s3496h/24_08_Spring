<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="WRITE"></c:set>
<%@ include file="../common/head.jspf"%>
<hr />


<section class="mt-24 text-xl px-4">
	<div class="mx-auto">
		<form action="../article/doWrite" method="POST">
			<table class="table" border="1" cellspacing="0" cellpadding="5" style="width: 100%; border-collapse: collapse;">
				<tbody>
					
					<tr>
						<th>게시판</th>
						<td style="text-align: center;"><select name="boardId">
								<option value="" selected disabled>게시판을 선택해주세요.</option>
								<option value="1">공지사항</option>
								<option value="2">자유</option>
								<option value="3">질의응답</option>
						</select></td>

					</tr>
					
					
					<tr>
						<th>제목</th>
						<td style="text-align: center;"><input class="input input-bordered input-primary input-sm w-full max-w-xs"
							name="title" autocomplete="off" type="text" placeholder="제목을 입력해" /></td>

					</tr>
					<tr>
						<th>내용</th>
						<td style="text-align: center;"><input class="input input-bordered input-primary input-sm w-full max-w-xs"
							name="body" autocomplete="off" type="text" placeholder="내용을 입력해" /></td>

					</tr>
					<tr>
						<th></th>
						<td style="text-align: center;"><button class="btn btn-primary">작성</button> <!-- 						<input class="btn btn-primary" -->
							<!-- 							type="submit" value="작성" />  --></td>

					</tr>
				</tbody>
			</table>
		</form>
		<div class="btns">
			<button class="btn" type="button" onclick="history.back()">뒤로가기</button>
		</div>
	</div>
</section>

<%@ include file="../common/foot.jspf"%>