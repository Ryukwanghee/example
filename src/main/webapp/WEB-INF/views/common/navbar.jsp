<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<nav class="navbar navbar-expand-sm bg-dark navbar-dark mb-3">
	<div class="container">
		<ul class="navbar-nav me-auto">
			<li class="nav-item"><a class="nav-link ${menu eq 'home' ? 'active' : '' }" href="/home">샘플 애플리케이션</a></li>
			<li class="nav-item"><a class="nav-link ${menu eq 'post' ? 'active' : '' }" href="/post/list">게시판</a></li>
		</ul>
		<c:if test="${not empty loginUser }">
			<span class="navbar-text"><strong class="text-white">${loginUser.name }</strong>님 환영합니다.</span>
		</c:if>
		<ul class="navbar-nav">
			<c:choose>
				<c:when test="${not empty loginUser }">
					<li class="nav-item"><a class="nav-link ${menu eq 'user' ? 'activce' : '' }" href="/user/info">내정보 보기</a></li>
					<li class="nav-item"><a class="nav-link" href="/logout">로그아웃</a></li>
				</c:when>
				<c:otherwise>
					<li class="nav-item"><a class="nav-link ${menu eq 'login' ? 'active' : '' }" href="/login">로그인</a></li>
					<li class="nav-item"><a class="nav-link ${menu eq 'register' ? 'active' : '' }" href="/register">회원가입</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
</nav>