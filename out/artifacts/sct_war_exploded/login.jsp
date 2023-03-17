<%@page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>选课系统登录页面</title>
		<style type="text/css">
			*{
				margin: 0px;
				padding: 0px;
				font-family: "microsoft yahei";
			}
			html,body{
				background-image: url(static/img/login_background.jpg);
				background-size: 100% 100%;
				height: 100%;
			}
			/*顶部css*/
			.header .bcground {
				position: relative;
				height: 64px;
				background-color: #a50001;
			}
			.header .bcground img {
				position: absolute;
				left: -12px;
				top: 2px;
				width: 204px;
				height: 61px;
			}
			.header .bcground h3 {
				position: absolute;
				left: 173px;
				top: 14px;
				font-size: 25px;
				font-weight: 400;
				color: #fff;
			}
			.login{
				position: absolute;
				background-color: rgba(255,255,255,20);
				top: 25%;
				left: 35%;
				right: 35%;
				bottom: 35%;
				border-radius: 5px;
			}
			.title,.u,.p,.l,.tips,.s{
				position: absolute;
				width: 100%;
			}
			input{
				height: 35px;
				border: 0px;
				border-radius: 5px;
				width: 80%;
				padding-left: 18px;
				box-sizing: border-box;
			}
			.uname{
				background: url(static/img/login_user.png) no-repeat left;
				background-color: #ffe4ec;
			}
			.pwd{
				background: url(static/img/login_pwd.png) no-repeat left;
				background-color: #ffe4ec;
			}
			button{
				background-color:#a50001;
				height: 35px;
				width: 80%;
				border: 0px;
				border-radius: 5px;
				color: #FFF;
				font-size: 16px;
			}
			select{
				width: 80%;
				height: 35px;
				border-radius: 4px;
				border:1px solid #e1e1e1;
			}
			.title{
				top: 0%;
				bottom: 80%;
				text-align: center;
				font-size: 22px;
				font-weight: bold;
				padding-top: 10px;
				box-sizing: border-box;
			}
			.u{
				top: 20%;
				bottom: 60%;
				left: 10%;
			}
			.p{
				top: 40%;
				bottom: 40%;
				left: 10%;
			}
			.s{
				top: 57%;
				bottom: 13%;
				left: 10%;
			}
			.l{
				top: 75%;
				bottom: 15%;
				left: 10%;
			}
			.tips{
				top: 90%;
				font-size: 13px;
				color: red;
				text-align: center;
			}
		</style>
	</head>
	<body>
	<!-- header头部模块开始 -->
	<header class="header">
		<div class="bcground">
			<a href="https://www.sdu.edu.cn/" target="_blank"><img src="static/img/山东大学logo.png" alt="山东大学logo.png"></a>
			<h3>学生信息管理系统</h3></a>
		</div>
	</header>
		<div class="login">
			<div class="title">
				学生选课系统
			</div>
<%--			登录信息表单部分--%>
			<form action="login" method="post">
				<div class="u">
					<input type="text" class="uname" name="userName" value=""  />
				</div>
				<div class="p">
					<input type="password" class="pwd" name="password" value=""  />
				</div>
				<div class="s">
<%--					登录类型选择--%>
					<select name="position">
						<option value="">请选择登陆类型</option>
						<option value="0">学生</option>
						<option value="1">老师</option>
						<option value="2">管理员</option>
					</select>
				</div>
				<div class="l">
					<button type="submit">登录</button>
				</div>
			</form>
<%--			返回错误信息--%>
			<div class="tips">
				${error}
			</div>
		</div>
	</body>
</html>
