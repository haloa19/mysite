<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="${ pageContext.request.contextPath }/board" method="post">
					<input type='hidden' name='a' value='list' />
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
					<a href="${ pageContext.request.contextPath }/board">전체목록</a>
				</form>
				
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
						<th>&nbsp;</th>
					</tr>	
							
					
					<c:forEach items="${list }" var="vo" varStatus="status">
						<tr>
							<td>${status.count + 5*(nowPage-1)}</td>		
							<td style="text-align:left; padding-left:0px">
							<a href="${ pageContext.request.contextPath }/board?a=view&no=${vo.no}">${vo.title }</a></td>
							<td>${vo.name }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>		
							<td>
								<a href="${ pageContext.request.contextPath }/board?a=writeform&kind=addwrite&no=${vo.no }&gNo=${ vo.gNo }&oNo=${vo.oNo}&depth=${vo.depth}">댓글</a>	
							</td>					
							<c:if test="${ not empty authUser && authUser.no == vo.userNo }">
								<td><a href="${pageContext.servletContext.contextPath }/board?a=deleteform&no=${vo.no}" class="del">삭제</a></td>
							</c:if>					
						</tr>					
					</c:forEach>	
					
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:choose>
							<c:when test="${nowPage == 1 }"><li>◁</li></c:when>
							<c:otherwise><li><a href="${pageContext.servletContext.contextPath }/board?a=list&page=${nowPage-1 }&bpage=${beginPage}&keyword=${keyword}">◀</a></li></c:otherwise>
						</c:choose>		
							<c:forEach var="i" begin="${beginPage }" end="${endPage}">
								<c:choose>
									<c:when test="${nowPage == i }">
										<li class="selected"><a href="${pageContext.servletContext.contextPath }/board?a=list&page=${i }&keyword=${keyword}">${i }</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="${pageContext.servletContext.contextPath }/board?a=list&page=${i }&keyword=${keyword}">${i }</a></li>
									</c:otherwise>			
								</c:choose>	
												
							</c:forEach>
						<c:choose>
							<c:when test="${endPage == totalPage && nowPage == totalPage }"><li>▷</li></c:when>
							<c:otherwise><li><a href="${pageContext.servletContext.contextPath }/board?a=list&page=${nowPage+1 }&bpage=${beginPage}&keyword=${keyword}">▶</a></li></c:otherwise>
						</c:choose>	
						
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<div class="bottom">
					<a href="${ pageContext.request.contextPath }/board?a=writeform&kind=newwrite&no=${ vo.no }" id="new-book">글쓰기</a>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>