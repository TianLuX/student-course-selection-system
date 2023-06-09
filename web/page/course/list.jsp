<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>列表</title>
		<link  rel="stylesheet"  href="${basePath}static/css/styles.css" />
		<link rel="stylesheet"  href="${basePath}static/css/font-awesome-4.7.0/css/font-awesome.min.css" />
		<script  src="${basePath}static/js/jquery.min.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(function(){
				$('.remove').click(function(){
					if(confirm("确定要删除吗？")){
						window.location.href="${basePath}course?method=delete&id="+$(this).attr("keyword");
					}
				})
			})
			function myFunction(){
				alert("选课时间确定成功！");
			}
		</script>
	</head>
	<body>
		<form action="${basePath}course?method=list" method="post">
			<div class="condition">
				ID：<input type="text" name="cId" value="${course.cId}">
				课程名：<input type="text" name="cName" value="${course.cName}">
				老师：<input type="text" name="tName" value="${course.teacher.tName}">
				老师账号：<input type="text" name="userName" value="${course.teacher.userName}">
				<button>
					<i class="fa fa-search"></i>
					查询
				</button>
<%--				不要跳转到jsp,用servlet将老师的信息传递到新增页面--%>
<%--				v_add是查询老师信息,添加到add.jsp中老师的select--%>
				<button type="button"  onclick="window.location.href='course?method=v_add'">
					<i class="fa fa-plus"></i>
					新增
				</button>
			</div>
		</form>
		<form action="${basePath}course?method=list" id="tableList" method="post">
		<input type="hidden" name="pageNo" value="${pageInfo.pageNo}">
		<input type="hidden" name="cId" value="${course.cId}">
		<input type="hidden" name="cName" value="${course.cName}">
		<input type="hidden" name="tName" value="${course.teacher.tName}">
		<input type="hidden" name="userName" value="${course.teacher.userName}">
		<table class="tablelist">
			<thead>
				<tr>
					<th>ID</th>
					<th>课程名</th>
					<th>老师</th>
					<th>课程时间</th>
					<th width="120px">操作</th>
				</tr>
			</thead>
			<c:forEach items="${pageInfo.list}" var="course">
			<tr>
				<td>${course.cId}</td>
				<td>${course.cName}</td>
				<td>${course.teacher.tName}</td>
				<td>${course.cTime}</td>
				<td>
					<button class="edit" type="button" onclick="window.location.href='${basePath}course?method=edit&id=${course.cId}'">
						<i class="fa fa-edit"></i>
						修改
					</button>
					<button class="remove" type="button" keyword="${course.cId}">
						<i class="fa fa-remove"></i>
						删除
					</button>
				</td>
			</tr>
			</c:forEach>
		</table>
		<%@include file="../inc/page.jsp"%>
		</form>
		<form action="${basePath}course?method=time"method="post">
			<div class="condition">
				选课开始时间：<input type="date"name="start"value="2021/11/10">结束时间：<input type="date"name="end" value="2021/11/12">
			<button type="button" onclick="myFunction()">
				确定
			</button>
			</div>
		</form>

	</body>
</html>
