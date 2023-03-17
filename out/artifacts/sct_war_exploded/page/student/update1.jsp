<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>新增</title>
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
					}
				});
			});
		</script>
	</head>
	<body>
	<div class="add">
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
	</div>
	</body>
</html>
