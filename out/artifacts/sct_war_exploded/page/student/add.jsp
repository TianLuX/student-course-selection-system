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
<%--		jquery.validata表单验证--%>
		<script  src="${basePath}static/js/jquery-validation-1.14.0/jquery.validate.js" type="text/javascript"></script>
		<script  src="${basePath}static/js/jquery-validation-1.14.0/localization/messages_zh.js" type="text/javascript"></script>
		<script type="text/javascript">
			// 姓名学号不能为空，且密码长度在6-15
			$(function(){
				$("#addForm").validate({
					rules:{
						stuNo:{
							// required是必填字段
							required:true,
							// digits是只能输入数字
							digits:true
						},
						stuName:"required",
						stuPwd:{
							required:true,
							// rangelength字符长度控制
							rangelength:[6,15]
						}
					}
				});
			});
		</script>
	</head>
	<body>
		<div class="add">
			<form id="addForm" action="${basePath}student?method=add" method="post">
				<table class="tableadd" style="width: 50%;">
					<tr>
						<td>学号</td>
						<td><input type="text" name="stuNo"></td>
					</tr>
					<tr>
						<td>姓名</td>
						<td style="color: red;"><input type="text" name="stuName"></td>
					</tr>
					<tr>
						<td>密码</td>
						<td>
							<input type="password" name="stuPwd" value="123456">
							初始密码为123456
						</td>
					</tr>
					<tr>
						<td colspan="4" align="left">
<%--							onclick="window.history.back(-1);返回前面界面--%>
<%--							要写类型type为button，否则默认submit--%>
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
