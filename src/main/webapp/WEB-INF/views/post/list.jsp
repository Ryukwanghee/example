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
			<table class="table table-sm" id="table-posts">
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
										<td class="text-center"><a href="" data-user-id="${post.userId }" class="text-decoration-none">${post.userName }</td>
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

<!-- 사용자 정보 표시 모달창 -->
<div class="modal" tabindex="-1" id="modal-user-detail">
   <div class="modal-dialog modal-md">
      <div class="modal-content">
         <div class="modal-header">
            <h5 class="modal-title">사용자 정보</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
         </div>
         <div class="modal-body">
            <table class="table" id="table-user">
               <colgroup>
                  <col width="20%">
                  <col width="35%">
                  <col width="15%">
                  <col width="35%">
               </colgroup>
               <tbody>
                  <tr>
                     <th>아이디</th><td><span id="user-id"></span></td>
                     <th>이름</th><td><span id="user-name"></span></td>
                  </tr>
                  <tr>
                     <th>이메일</th><td><span id="user-email"></span></td>
                     <th>등록일</th><td><span id="user-created-date"></span></td>
                  </tr>
                  <tr>
                     <th>보유권한</th><td colspan="3"><span id="user-role"></span></td>
                  </tr>
               </tbody>
            </table>
         </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-secondary btn-xs" data-bs-dismiss="modal">확인</button>
         </div>
      </div>
   </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script type="text/javascript">
$(function() {
	// 사용자 상세정보를 표시하는 모달객체 생성
	let modal = new bootstrap.Modal('#modal-user-detail');
	
	// 아이디가 table-posts인 엘리먼트의 자손 중에서 a엘리먼트이고 data-user-id 속성을 가지고 있는 것을 선택하고,
	// 선택된 엘리먼트에서 클릭이벤트가 발생했을 때 실행될 이벤트 핸들러 함수를 등록한다.
	$("#table-posts a[data-user-id]").click(function(event) {	
		
		// a 태그에서 클릭이벤트 발생했을 링크로 이동하는 기본동작이 실행되지 않게 한다.
		event.preventDefault(); //해당 a태그의 기본동작이 일어나지 않게
		//alert("조회"); //코딩을 할때 이런식으로 순서대로 확인을 하고 넘어가야 에러를 찾기가 쉽다.
		
		// 지금 클릭이벤트가 발생한 그 엘리먼트의 속성값을 조회한다.
		let id = $(this).attr('data-user-id'); // 이벤트함수 this는 지금 클릭한것을 의미
		// alert(id); //코딩을 할때 이런식으로 순서대로 확인을 하고 넘어가야 에러를 찾기가 쉽다.
		
		// 서버로 ajax요청을 보낸다.
		// http://localhost/user/detail.json?userId=hong
		$.getJSON('/user/detail.json', {userId: id}, function(user) { //userId라는 이름으로 id를 보냄, function은 성공적인 응답이 왔을 떄 실행 , 매개변수 user에는 응답이 담겨있음 값이 담겨있는것
			// 모달창의 엘리먼트에 값을 표현
			$("#user-id").text(user.id);
			$("#user-name").text(user.name);
			$("#user-email").text(user.email);
			$("#user-created-date").text(user.createdDate);
			
			// user.userRoles -> [{"userId":"leess","roleName":"ROLE_GUEST"},{"userId":"leess","roleName":"ROLE_USER"}]
			// roles -> ["ROLE_GUEST", "ROLE_USER"] -> "ROLE_GUEST, ROLE_USER"
			// .map() -> ["ROLE_GUEST", "ROLE_USER"]
			// .join() -> "ROLE_GUEST, ROLE_USER"
			let roles = user.userRoles.map(function(item, index) { 
				return item.roleName
			}).join(', ');
			$("#user-role").text(roles);
			
			// 모달창을 표시함
			modal.show();
			/*
				response(user) 변수에 대입되어 있는 객체는 아래와 같다.
			{
				"id":"leess",
				"name":"이순신",
				"email":"leess@gmail.com",
				"createdDate":"2023-01-16",
				"userRoles":[
								{"userId":"leess","roleName":"ROLE_GUEST"},
								{"userId":"leess","roleName":"ROLE_USER"}
							]
			}
			*/
		}) 
	});
})
</script>
</body>
</html>