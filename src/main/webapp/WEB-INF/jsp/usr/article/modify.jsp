<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="MODIFY"></c:set>
<%@ include file="../common/head.jspf"%>
<hr />

<section class="mt-24 text-xl px-4">
	<div class="mx-auto">
		<form action="../article/doModify" method="POST">
			<input type="hidden" name="id" value="${article.id}" />
			<table class="table" border="1" cellspacing="0" cellpadding="5" style="width: 100%; border-collapse: collapse;">
				<tbody>
					<tr>
						<th style="text-align: center;">ID</th>
						<td style="text-align: center;">${article.id}</td>
					</tr>
					<tr>
						<th style="text-align: center;">Registration Date</th>
						<td style="text-align: center;">${article.regDate.substring(0,10)}</td>
					</tr>
					<tr>
						<th style="text-align: center;">Modified date</th>
						<td style="text-align: center;">${article.updateDate}</td>
					</tr>
					<tr>
						<th style="text-align: center;">Writer</th>
						<td style="text-align: center;">${article.extra__writer}</td>
					</tr>
					<tr>
						<th style="text-align: center;">Title</th>
						<td style="text-align: center;"><input name="title" value="${article.title}" type="text" autocomplete="off"
							placeholder="새 제목을 입력해" class="input input-bordered input-primary w-full max-w-xs input-sm " /></td>
					</tr>
					<tr>
						<th style="text-align: center;">Body</th>
						<td style="text-align: center;"><input name="body" value="${article.body}" type="text" autocomplete="off"
							placeholder="새 내용을 입력해" class="input input-bordered input-primary w-full max-w-xs input-sm " /></td>
					</tr>
					<tr>
						<th></th>
						<td style="text-align: center;">
							<button class="btn btn-primary">수정</button>
						</td>
					</tr>

				</tbody>
			</table>
		</form>
		<div class="btns">
			<button class="btn" type="button" onclick="history.back()">뒤로가기</button>
			<c:if test="${article.userCanModify }">
				<a class="btn" href="../article/modify?id=${article.id }">수정</a>
			</c:if>
			<c:if test="${article.userCanDelete }">
				<a class="btn" href="../article/doDelete?id=${article.id }">삭제</a>
			</c:if>

		</div>
	</div>
</section>

<%@ include file="../common/foot.jspf"%>