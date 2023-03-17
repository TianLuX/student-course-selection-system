<%@page contentType="text/html; charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html style="height: 100%">
<head>
<meta charset="utf-8">
<link  rel="stylesheet"  href="${basePath}static/css/styles.css" />
		<link rel="stylesheet"  href="${basePath}static/css/font-awesome-4.7.0/css/font-awesome.min.css" />
		<script  src="${basePath}static/js/jquery.min.js" type="text/javascript"></script>
		<script  src="${basePath}static/js/jquery-validation-1.14.0/jquery.validate.js" type="text/javascript"></script>
		<script  src="${basePath}static/js/jquery-validation-1.14.0/localization/messages_zh.js" type="text/javascript"></script>
	<script type="text/javascript">
		// 是否为空的判断
		$(function(){
			$("#editForm").validate({
				rules:{
					stdPhone:{
						maxlength:11,
						maxlength:11,
						isMobile:true
					},
					stdEmail:{
						email: true
					}
				},
				messages: {
					stdEmail: "请输入一个正确的邮箱",
					stdPhone: {
						maxlength:"请填写11位的手机号",
						maxlength:"请填写11位的手机号",
						isMobile:"请填写正确的手机号码"
					}
				}
			});
		});
	</script>
</head>
<body style="height: 100%; margin: 0">
<div class="add">
	<table class="tablelist" style="width: 50%;margin-left:50px">
		<c:if test="${type == 2}">
			<tr>
				<td width="120px">ID</td>
				<td>${user.id}</td>
			</tr>
			<tr>
				<td width="120px">用户名</td>
				<td>${user.userName}</td>
			</tr>
			<tr>
				<td width="120px">姓名</td>
				<td>${user.name}</td>
			</tr>
		</c:if>
		<c:if test="${type == 1}">
			<tr>
				<td width="120px">ID</td>
				<td>${user.tId}</td>
			</tr>
			<tr>
				<td width="120px">用户名</td>
				<td>${user.userName}</td>
			</tr>
			<tr>
				<td width="120px">姓名</td>
				<td>${user.tName}</td>
			</tr>
		</c:if>
		<c:if test="${type == 0}">
			${msg}
			<form id="editForm" action="${basePath}student?method=editsubmit1" method="post">
				<input type="hidden" name="stuId" value="${user.stdId}">
				<table class="tableadd" style="width: 50%;">
					<tr>
						<td>学号</td>
						<td><input type="text" name="stuNo" value="${user.stdNo}" disabled="disabled"></td>
					</tr>
					<tr>
						<td>姓名</td>
						<td ><input type="text" name="stuName" value="${user.stdName}"disabled="disabled"></td>
					</tr>
					<tr>
						<td>电话</td>
						<td><input type="text" name="stdPhone" value="${user.stdPhone}"></td>
					</tr>
					<tr>
						<td>邮箱</td>
						<td><input type="text" name="stdEmail" value="${user.stdEmail}"></td>
					</tr>
					<tr>
						<td>住址</td>
						<td><input type="text" name="stdAddress" value="${user.stdAddress}"></td>
					</tr>
					<tr>
						<td colspan="4" align="left">
							<button class="remove" type="submit">
								<i class="fa fa-save"></i>
								提交
							</button>
						</td>
					</tr>
				</table>
			</form>
		</c:if>
		
	</table>
	</div>
</body>
</html>