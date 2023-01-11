<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
			<h1 class="fs-4 border p-2 bg-light">게시글 목록</h1>
		</div>
	</div>
	<div class="row mb-3">
		<div class="col">
			<p>
				게시글 목록을 확인하세요.
				<!-- 로그인한 사용자만 링크를 출력한다. -->
				<c:if test="${not empty loginUser }">
					<a href="insert" class="btn btn-primary btn-sm float-end">새 글쓰기</a>
				</c:if>
			</p>
			<table class="table table-sm">
				<colgroup>
					<col width="7%">
					<col width="*">
					<col width="10%">
					<col width="10%">
					<col width="10%">
					<col width="15%">
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th class="text-center">작성자</th>
						<th class="text-center">조회수</th>
						<th class="text-center">댓글수</th>
						<th class="text-center">등록일</th>
					</tr>
				</thead>
				<tbody>
					<!-- 등록된 게시글이 없으면 아래 내용을 출력한다. -->
						<c:choose>
							<c:when test="${empty posts }">
								<tr>
									<td colspan="6" class="text-center">게시글이 없습니다.</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="post" items="${posts }"> <!-- 배열의 반복처리  -->
									<tr>
										<td>${post.no }</td>
										<td><a href="read?postNo=${post.no }" class="text-decoration-none">${post.title }</a></td><!-- /없으니까 상대경로 -->
										<td class="text-center">${post.userName }</td>
										<td class="text-center">${post.readCount }</td>
										<td class="text-center">${post.commentCount }</td>
										<td class="text-center"><fmt:formatDate value="${post.createdDate }" /></td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
				</tbody>
			</table>
			
			<c:if test="${not empty posts }">
            <nav aria-label="Page navigation example">
               <ul class="pagination justify-content-center">
                  <li class="page-item">
                     <a class="page-link ${pagination.first ? 'disabled' : '' }" 
                        href="list?page=${pagination.prevPage }">이전</a>
                  </li>   
                  <c:forEach var="num" begin="${pagination.beginPage }" end="${pagination.endPage }">
                     <li class="page-item">
                        <a class="page-link ${pagination.page eq num ? 'active' : '' }" href="list?page=${num }">${num }</a>
                     </li>   
                  </c:forEach>
                  
                  <li class="page-item">
                     <a class="page-link ${pagination.last ? 'disabled' : '' }" 
                        href="list?page=${pagination.nextPage }">다음</a>
                  </li>   
               </ul>
            </nav>
         </c:if>
		</div>
	</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
</body>
</html>