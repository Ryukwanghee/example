<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.2/font/bootstrap-icons.css">
<title>애플리케이션</title>
</head>
<body>
<c:set var="menu" value="post" />
<%@ include file="../common/navbar.jsp" %>
<div class="container my-3">
	<div class="row mb-3">
		<div class="col">
			<h1 class="fs-4 border p-2 bg-light">게시글 상세정보</h1>
		</div>
	</div>
	<div class="row mb-3">
		<div class="col">
			<p>
				게시글 상세정보를 확인하세요. 
				<!-- 로그인한 상태인 경우 아래 버튼이 출력된다. -->
				<c:if test="${not empty loginUser }">
				<button class="btn btn-primary btn-sm float-end" data-bs-toggle="modal" data-bs-target="#modal-form-comments">댓글 쓰기</button>
				</c:if>
			</p>
			<table class="table table-sm">
				<colgroup>
					<col width="15%">
					<col width="35%">
					<col width="15%">
					<col width="35%">
				</colgroup>
				<tbody>
					<tr>
						<th>제목</th>
						<td colspan="3">${post.title }</td>
					</tr>
					<tr>
						<th>번호</th>
						<td>${post.no }</td>
						<th>작성자</th>
						<td>${post.userName }</td>
					</tr>
					<tr>
						<th>등록일</th>
						<td><fmt:formatDate value="${post.createdDate }" /></td>
						<th>최종수정일</th>
						<td><fmt:formatDate value="${post.updatedDate }" /></td>
					</tr>
					<tr>
						<th>조회수</th>
						<td>${post.readCount }</td>
						<th>댓글수</th>
						<td>${post.commentCount }</td>
					</tr>
					<tr>
						<th>내용</th>
						<td colspan="3">${post.content }</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<!-- 
		해당 게시글의 작성자와 로그인한 사용자의 아이디가 일치하는 경우 아래 내용이 출력된다.
	 -->
	<div class="row mb-3">
		<div class="col-12">
			<a href="modify?postNo=100" class="btn btn-warning btn-sm">수정</a>
			<a href="delete?postNo=100" class="btn btn-danger btn-sm">삭제</a>
		</div>
	</div>
	
	<!-- 댓글 목록  -->
	<div class="row mb-3">
		<div class="col">
			<div class="border p-2 mb-2">
				<p class="mb-0">등록된 댓글이 없습니다.</p>
			</div>
			<div class="border p-2 mb-2">
				<p class="mb-0 small">
					<span class="text-muted">홍길동</span> 
					<span class="text-muted float-end">2023. 1. 10</span>
				</p>
				<p class="mb-0">
					댓글 내용입니다.
					<!-- 
						댓글작성자와 로그인한 사용자의 아이디가 일치하는 경우 아래 링크가 출력된다.
					 -->
					<a href="delete-comment?postNo=100&commentNo=2000" class="float-end"><i class="bi bi-trash-fill text-danger"></i></a>
				</p>
			</div>
		</div>
	</div>
</div>
<!-- 댓글 등록폼 -->
<div class="modal" tabindex="-1" id="modal-form-comments">
	<div class="modal-dialog">
		<form id="form-add-depts" class="p-3" method="post" action="insert-comment">
		<!-- 
			히든 필드에 게시글을 글번호를 설정합니다.
		 -->
		<input type="hidden" name="postNo" value="100">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">댓글 등록폼</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
					<div class="row mb-2">
						<div class="col-sm-12">
							<textarea class="form-control" rows="3" name="content"></textarea>
						</div>
					</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary btn-xs" data-bs-dismiss="modal">닫기</button>
				<button type="submit" class="btn btn-primary btn-xs">등록</button>
			</div>
		</div>
		</form>
	</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
</body>
</html>