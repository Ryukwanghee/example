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
<c:set var="menu" value="register" />
<%@ include file="common/navbar.jsp" %>
<div class="container">
	<div class="row mb-3">
		<div class="col-12">
			<h1 class="border bg-light p-2 fs-4">회원가입</h1>
		</div>
	</div>
	<div class="row mb-3">
		<div class="col-12">
			<p>회원가입 정보를 입력하세요</p>
			
			<%-- <c:if test="${not empty message }">
				<div class="alert alert-danger">
					${message }
				</div>
			</c:if> --%>
			
			<%-- HomeController에서 Model을 쓰지않고 redirect를 사용했을 떄 
				<c:if test="${param.error eq 'fail' }">
				<div class="alert alert-danger">
					회원가입 중 오류가 발생하였습니다.
				</div>
			</c:if> --%>
			
			<form id="form-register" class="border bg-light p-3" method="post" action="register">
				<div class="mb-3">
					<label class="form-label">접속 권한</label>
					<div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="checkbox" name="roleName" value="ROLE_GUEST" checked>
							<label class="form-check-label">게스트</label>
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="checkbox" name="roleName" value="ROLE_USER" disabled="disabled">
							<label class="form-check-label">사용자</label>
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="checkbox" name="roleName" value="ROLE_ADMIN" disabled="disabled">
							<label class="form-check-label">관리자</label>
						</div>
					</div>
				</div>
				<div class="mb-3">
					<label class="form-label">아이디</label>
					<input type="text" class="form-control form-control-sm" name="id" />
				</div>
				<div class="mb-3">
					<label class="form-label">비밀번호</label>
					<input type="password" class="form-control form-control-sm" name="password" />
				</div>
				<div class="mb-3">
					<label class="form-label">이름</label>
					<input type="text" class="form-control form-control-sm" name="name" />
				</div>
				<div class="mb-3">
					<label class="form-label">이메일</label>
					<input type="text" class="form-control form-control-sm" name="email" />
				</div>
				<div class="mb-3">
					<label class="form-label">전화번호</label>
					<input type="text" class="form-control form-control-sm" name="tel" />
				</div>
				<div class="text-end">
					<a href="/home" class="btn btn-secondary btn-sm">취소</a>
					<button type="submit" class="btn btn-primary btn-sm">가입</button>
				</div>
			</form>
		</div>
	</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
</body>
</html>