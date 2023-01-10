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
			<h1 class="border bg-light p-2 fs-4">비밀번호 변경하기</h1>
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
						<a href="/user/password" class="list-group-item list-group-item-action active">비밀번호 변경</a>
						<a href="/user/delete" class="list-group-item list-group-item-action">탈퇴하기</a>
					</div>
				</div>
			</div>
		</div>
		<div class="col-9">
			<p><strong>${loginUser.name }</strong>님 이전 비밀번호와 새 비밀번호를 입력하세요</p>
			<form id="form-change-password" class="border bg-light p-3" method="post" action="password">
				<div class="mb-3">
					<label class="form-label">이전 비밀번호</label>
					<input type="password" class="form-control" name="oldPassword">
				</div> <!-- name있는것만 값 전달 -->
				<div class="mb-3">
					<label class="form-label">새 비밀번호</label>
					<input type="password" class="form-control" id="password" name="password">
				</div>
				<div class="mb-3">
					<label class="form-label">비밀번호 확인</label>
					<input type="password" class="form-control" id="password-confirm">
				</div>
				<div class="text-end">
					<button type="submit" class="btn btn-primary btn-sm">비밀번호 변경</button>
				</div>
			</form>
		</div>
	</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script type="text/javascript">
$(function() {
	$("#form-change-password").submit(function() {
		let oldPassword = $(":password[name=oldPassword]").val();
		let password = $("#password").val();
		let passwordConfirm = $("#password-confirm").val();
		
		if (oldPassword == "") {
			alert("이전 비밀번호는 필수 입력값입니다.");
			return false;
		}
		if (password == "") {
			alert("새 비밀번호는 필수 입력값입니다.");
			return false;
		}
		if (passwordConfirm == "") {
			alert("비밀번호 확인은 필수 입력값입니다.");
			return false;
		}
		if (oldPassword == password) {
			alert("새 비밀번호를 이전 비밀번호와 같은 값으로 지정할 수 없습니다.");
			return false;
		}
		if (password != passwordConfirm) {
			alert("새 비밀번호와 비밀번호 확인 값이 서로 일치하지 않습니다.");
			return false;
		}
		return true;
		
		
	});
})
</script>
</body>
</html>