<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
<title>애플리케이션</title>
</head>
<body>
<c:set var="menu" value="user" />
<%@ include file="../common/navbar.jsp" %>
<div class="container">
	<div class="row mb-3">
		<div class="col-12">
			<h1 class="border bg-light p-2 fs-4">내 정보</h1>
		</div>
	</div>
	<div class="row mb-3">
		<div class="col-3">
			<div class="card">
				<div class="card-header">
					메뉴
				</div>
				<div class="card-body">
					<div class="list-group">
						<a href="/user/info" class="list-group-item list-group-item-action active">내 정보 보기</a>
						<a href="/user/password" class="list-group-item list-group-item-action">비밀번호 변경</a>
						<a href="/user/delete" class="list-group-item list-group-item-action">탈퇴하기</a>
					</div>
				</div>
			</div>
		</div>
		<div class="col-9">
			<p>내 정보를 확인하세요.</p>
			<table class="table table-bordered">
				<colgroup>
					<col width="15%">
					<col width="35%">
					<col width="15%">
					<col width="35%">
				</colgroup>
				<tbody>
					<tr>
						<th>아이디</th>
						<td>${user.id }</td>
						<th>가입일</th>
						<td><fmt:formatDate value="${user.createdDate }" /></td>
					</tr>
					<tr>
						<th>이름</th>
						<td>${user.name }</td>
						<th>접근권한</th>
						<td>
							<c:forEach var="userRole" items="${user.userRoles }">
								<c:choose>
									<c:when test="${userRole.roleName eq 'ROLE_GUEST' }">
										<span class="badget text-bg-primary">${userRole.roleName }</span>
									</c:when>
									<c:when test="${userRole.roleName eq 'ROLE_USER' }">
										<span class="badget text-bg-warning">${userRole.roleName }</span>
									</c:when>
									<c:when test="${userRole.roleName eq 'ROLE_ADMIN' }">
										<span class="badget text-bg-success">${userRole.roleName }</span>
									</c:when>
								</c:choose>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th>이메일</th>
						<td>${user.email }</td>
						<th>전화번호</th>
						<td>${user.tel }</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
</body>
</html>