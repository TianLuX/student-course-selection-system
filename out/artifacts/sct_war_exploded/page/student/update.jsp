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
						stuNo:{
							required:true,
							digits:true
						},
						stuName:"required",
						stuPwd:{
							required:true,
							rangelength:[6,15]
						},
						stdPhone:{
							maxlength:11,
							minlength:11,
							digits:true
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
				<input type="hidden" name="stuId" value="${student.stdId}">
				<table class="tableadd" style="width: 50%;">
					<tr>
						<td>学号</td>
						<td><input type="text" name="stuNo" value="${student.stdNo}"></td>
					</tr>
					<tr>
						<td>姓名</td>
						<td style="color: red;"><input type="text" name="stuName" value="${student.stdName}"></td>
					</tr>
					<tr>
						<td>电话</td>
						<td><input type="text" name="stdPhone" value="${student.stdPhone}"></td>
					</tr>
					<tr>
						<td>邮箱</td>
						<td><input type="text" name="stdEmail" value="${student.stdEmail}"></td>
					</tr>
					<tr>
						<td>住址</td>
						<td><input type="text" name="stdAddress" value="${student.stdAddress}"></td>
					</tr>
					<tr>
						<td colspan="4" align="left">
<%--							返回上一级路径--%>
							<button class="edit" type="button" onclick="window.history.back(-1);">
								<i class="fa fa-arrow-left"></i>
								返回
							</button>
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
