<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
			+ request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%=basePath%>">
	<title>添加常见问题</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
</head>
<body>
	<c:if test="${errMsg!=null}">
		<script>
			alert('${errMsg}');
		</script>
	</c:if>
	<%@ include file="../common/header.jsp"%>
	<%@ include file="infoheader.jsp"%>
	<table align="center">
		<tr>
			<td>
				<c:if test="${infoMsg!=null}">
					${infoMsg}
				</c:if>
			</td>
			<td align="right"></td>
		</tr>
		<tr>
			<td colspan="2">
				<form:form modelAttribute="faq" action="back/info/addfaq.html" method="post">
					<table>
						<tr>
							<td></td>
							<td><form:errors path="question" /></td>
						</tr>
						<tr>
							<td>问题：</td>
							<td><textarea rows="1" cols="135" name="question">${faq.question}</textarea></td>
						</tr>
						<tr>
							<td></td>
							<td><form:errors path="answer" /></td>
						</tr>
						<tr>
							<td>答案：</td>
							<td><textarea rows="10" cols="135" name="answer">${faq.answer}</textarea></td>
						</tr>
						<tr>
							<td><input type="submit" value="添加" /></td>
							<td><input type="reset" value="重置" /></td>
						</tr>
					</table>
				</form:form>
			</td>
		</tr>
	</table>
</body>
</html>