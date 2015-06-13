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
	<title>添加地址</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	<script type="text/javascript" src="javascript/user.js"></script> 
	<script type="text/javascript" src="javascript/jquery-1.7.1.min.js"></script>
	<script>
		function updateDistrict(data){
			var dataDistrict=data[0];
			var objectDistrict=document.getElementById('districtid'); 
			objectDistrict.length=0;
			for(var i=0;i<dataDistrict.length;i++){
				objectDistrict.add(new Option(dataDistrict[i].name,dataDistrict[i].id));
			}			
			
			var dataRegion=data[1];
			var objectRegion=document.getElementById('regionid'); 
			objectRegion.length=0;
			for(var i=0;i<dataRegion.length;i++){
				objectRegion.add(new Option(dataRegion[i].name,dataRegion[i].id));
			}	 
		}	
		function updateRegion(data){
			var object=document.getElementById('regionid'); 
			object.length=0;
			for(var i=0;i<data.length;i++){
				object.add(new Option(data[i].name,data[i].id));
			}			 
		}	
	</script>  
</head>
<body>
	<%@ include file="../common/header.jsp" %>
	<table align="center">
		<tr>
		<td>
			<form:form modelAttribute="addr" action="weixin/addr/add.html" method="post">
				<table>
					<tr>
						<td></td>
						<td><form:errors path="name" /></td>
					</tr>
					<tr>
						<td>名称</td>
						<td><form:input path="name" /></td>
					</tr>
					<tr>
						<td></td>
						<td><form:errors path="cellnum" /></td>
					</tr>
					<tr>
						<td>手机号</td>
						<td><form:input path="cellnum" /></td>
					</tr>
					<tr>
						<td>城市</td>
						<td>    
							<select id="cityid" name="cityid" onchange="getData('cityid','weixin/addr/select.html',updateDistrict)">
								<c:forEach var="city" items="${citys}">
								<option value="${city.id }">${city.name }</option>
								</c:forEach>
							</select>           					
        				</td>
					</tr>
					<tr>
						<td>区</td>
						<td>    
							<select id="districtid" name="districtid" onchange="getData('districtid','weixin/addr/select.html',updateRegion)">
								<c:forEach var="district" items="${districts}">
								<option value="${district.id }">${district.name }</option>
								</c:forEach>
							</select>           					
        				</td>
					</tr>
					<tr>
						<td>小区</td>
						<td>      
							<form:select path="regionid" items="${regions}" itemLabel="name" itemValue="id"></form:select>           					
        				</td>
					</tr>
					<tr>
						<td></td>
						<td><form:errors path="detail" /></td>
					</tr>
					<tr>
						<td>详细地址</td>
						<td><form:input path="detail"/></td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="submit" value="添加地址" />
						</td>
					</tr>
				</table>
			</form:form>
		</td>
		</tr>
	</table>
</body>
</html>