<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" 
	trimDirectiveWhitespaces="true"%>
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
			<h1 class="border bg-light p-2 fs-4">탈퇴하기</h1>
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
						<a href="/user/info" class="list-group-item list-group-item-action">내 정보 보기</a>
						<a href="/user/password" class="list-group-item list-group-item-action">비밀번호 변경</a>
						<a href="/user/delete" class="list-group-item list-group-item-action active">탈퇴하기</a>
					</div>
				</div>
			</div>
		</div>
		<div class="col-9">
			<p><strong>${loginUser.name }</strong>님 탈퇴를 요청하였습니다. 비밀번호를 입력하고 탈퇴버튼을 클릭하세요</p>
			<form class="border bg-light p-3" method="post" action="delete">
				<div class="mb-3">
					<label class="form-label">비밀번호</label>
					<input type="password" class="form-control" name="password">
				</div>
				<div class="text-end">
					<button type="submit" class="btn btn-primary btn-sm">탈퇴</button>
				</div>
			</form>
		</div>
	</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
</body>
</html>