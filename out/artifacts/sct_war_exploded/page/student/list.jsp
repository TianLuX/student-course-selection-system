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
			//删除确认
			$(function(){
				$('.remove').click(function(){
					if(confirm("确定要删除吗？")){
						window.location.href="${basePath}student?method=delete&id="+$(this).attr("keyword");
					}
				})
			})
		</script>
	</head>
	<body>
		<form action="${basePath}student?method=list" method="post">
			<div class="condition">
				ID：<input type="text" name="stdId" value="${student.stdId}">
				姓名：<input type="text" name="stdName" value="${student.stdName}">
				学号：<input type="text" name="stdNo" value="${student.stdNo}">
				<button>
					<i class="fa fa-search"></i>
					查询
				</button>
				<%--type的属性必须写为button，不然默认submit--%>
				<button type="button" onclick="window.location.href='page/student/add.jsp'">
					<i class="fa fa-plus"></i>
					新增
				</button>
			</div>
		</form>
<%--		method需要用post，如果用get的话会覆盖，下面的input隐藏掉--%>
		<form action="${basePath}student?method=list" id="tableList" method="post">
<%--			分页表单里需要再重复写一次，才能在下一页分页使用的时候，仍然保持查询条件--%>
		<input type="hidden" name="pageNo" value="${pageInfo.pageNo}">
		<input type="hidden" name="stdId" value="${student.stdId}">
		<input type="hidden" name="stdName" value="${student.stdName}">
		<input type="hidden" name="stdNo" value="${student.stdNo}">
		<table class="tablelist">
			<thead>
				<tr>
					<th>ID</th>
					<th>姓名</th>
					<th>学号</th>
					<th>电话</th>
					<th>Email</th>
					<th>住址</th>
					<th width="120px">操作</th>
				</tr>
			</thead>
<%--			通过foreach循环遍历添加--%>
			<c:forEach items="${pageInfo.list}" var="student">
			<tr>
				<td>${student.stdId}</td>
				<td>${student.stdName}</td>
				<td>${student.stdNo}</td>
				<td>${student.stdPhone}</td>
				<td>${student.stdEmail}</td>
				<td>${student.stdAddress}</td>
				<td>
<%--					通过onclick方法路径跳转--%>
					<button class="edit" type="button" onclick="window.location.href='${basePath}student?method=edit&id=${student.stdId}'">
						<i class="fa fa-edit"></i>
						修改
					</button>
<%--				根据关键词去删除--%>
					<button class="remove" type="button" keyword="${student.stdId}">
						<i class="fa fa-remove"></i>
						删除
					</button>
				</td>
			</tr>
			</c:forEach>
		</table>
<%--			分页功能单独实现在inc/page.jsp中--%>
		<%@include file="../inc/page.jsp"%>
		</form>
	</body>
</html>
