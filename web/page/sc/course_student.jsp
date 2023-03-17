<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%--老师录入评分界面--%>
<html>
	<head>
		<meta charset="utf-8">
		<title>选课评分</title>
		<link  rel="stylesheet"  href="${basePath}static/css/styles.css" />
		<link rel="stylesheet"  href="${basePath}static/css/font-awesome-4.7.0/css/font-awesome.min.css" />
		<script  src="${basePath}static/js/jquery.min.js" type="text/javascript"></script>
		<script  src="${basePath}static/js/jquery-validation-1.14.0/jquery.validate.js" type="text/javascript"></script>
		<script  src="${basePath}static/js/jquery-validation-1.14.0/localization/messages_zh.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(function(){
				// 限制评分为0-100分，而且必须填写
				$("#addForm").validate({
					rules:{
						score:{
							required:true,
							digits:true,
							max:100,
							min:0
						},
					}
				});
			});
		</script>
	</head>
	<body>
<%--	    通过cif设置massage信息，不使用servlet返回中文，不稳定--%>
		<c:if test="${param.msg == 0}">
			<span style="color:red;">操作失败！</span>
		</c:if>
		<c:if test="${param.msg == 1}">
			<span style="color:green;">操作成功！</span>
		</c:if>
<%--		评分提交--%>
		<form id="addForm" action="${basePath}sc?method=score_submit" method="post">
<%--			传入课程cid--%>
			<input type="hidden" name="cId" value="${cId}">
			<table class="tablelist">
				<thead>
					<tr>
						<th>ID</th>
						<th>学生姓名</th>
						<th>评分</th>
					</tr>
				</thead>
				<c:forEach items="${list}" var="student">
				<tr>
					<td>${student.stdId}</td>
					<td>${student.stdName}</td>
					<td style="color: red;">
<%--						更新数据，防止重复填写--%>
						<input type="text" name="score" value="${student.score}">
						<input type="hidden" name="stuId" value="${student.stdId}">
					</td>
				</tr>
				</c:forEach>
			</table>
			<br>
	        暂存：<input type="radio" name="scid" value="0">&nbsp;&nbsp;提交：<input type="radio" name="scid"value="1"><br>
			<button type="submit" class="mybtn">评分保存</button>
		</form>
	</body>
</html>
