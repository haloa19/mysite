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
							
					<c:set var="i" value="0"/>
					<c:forEach items="${list }" var="vo" varStatus="status">
						<tr>
						<c:choose>
							<c:when test="${vo.hit == -1 }">
								<c:if test="${vo.depth == 0}">
									<td>${groupNum - (i + 5*(nowPage-1))}</td>
									<c:set var="i" value="${i+1 }"/>
								</c:if>	
								<c:if test="${vo.depth != 0 }">
									<td></td>
								</c:if>					
								<td style="text-align:left; padding-left:${30*vo.depth}px">
								<c:if test="${vo.depth > 0}">
									<img src="${pageContext.request.contextPath }/assets/images/reply.png"/>
								</c:if>삭제된 글입니다.</td>
								<td></td>
								<td></td>
								<td></td>		
								<td></td>
								<td></td>							
							</c:when>
							<c:otherwise>
								<c:if test="${vo.depth == 0}">
									<td>${groupNum - (i + 5*(nowPage-1))}</td>
									<c:set var="i" value="${i+1 }"/>
								</c:if>	
								<c:if test="${vo.depth != 0 }">
									<td></td>									
								</c:if>					
								<td style="text-align:left; padding-left:${30*vo.depth}px">
								<c:if test="${vo.depth > 0}">
									<img src="${pageContext.request.contextPath }/assets/images/reply.png"/>
								</c:if>
								<a href="${ pageContext.request.contextPath }/board?a=view&no=${vo.no}">${vo.title }</a></td>
								<td>${vo.name }</td>
								<td>${vo.hit }</td>
								<td>${vo.regDate }</td>		
								<td>
									<a href="${ pageContext.request.contextPath }/board?a=writeform&kind=addwrite&no=${vo.no }&gNo=${ vo.gNo }&oNo=${vo.oNo}&depth=${vo.depth}">댓글</a>	
								</td>					
								<c:if test="${ not empty authUser && authUser.no == vo.userNo }">
									<td><a href="${pageContext.servletContext.contextPath }/board?a=deleteform&no=${vo.no }&gNo=${vo.gNo}" class="del">삭제</a></td>
								</c:if>	
							</c:otherwise>
						
						</c:choose>				
						</tr>					
					</c:forEach>	
					
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:choose>
							<c:when test="${nowPage == 1 }"><li>◁</li></c:when>
							<c:otherwise><li><a href="${pageContext.servletContext.contextPath }/board?a=list&page=${nowPage-1 }&bpage=${beginPage}">◀</a></li></c:otherwise>
						</c:choose>		
							<c:forEach var="i" begin="${beginPage }" end="${endPage}">
								<c:choose>
									<c:when test="${nowPage == i }">
										<li class="selected"><a href="${pageContext.servletContext.contextPath }/board?a=list&page=${i }">${i }</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="${pageContext.servletContext.contextPath }/board?a=list&page=${i }">${i }</a></li>
									</c:otherwise>			
								</c:choose>	
												
							</c:forEach>
						<c:choose>
							<c:when test="${endPage == totalPage && nowPage == totalPage }"><li>▷</li></c:when>
							<c:otherwise><li><a href="${pageContext.servletContext.contextPath }/board?a=list&page=${nowPage+1 }&bpage=${beginPage}">▶</a></li></c:otherwise>
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