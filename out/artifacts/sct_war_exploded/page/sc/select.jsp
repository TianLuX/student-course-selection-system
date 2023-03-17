<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--选课的list--%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>选课</title>
		<link  rel="stylesheet"  href="${basePath}static/css/styles.css" />
		<link rel="stylesheet"  href="${basePath}static/css/font-awesome-4.7.0/css/font-awesome.min.css" />
		<script  src="${basePath}static/js/jquery.min.js" type="text/javascript"></script>
		<script type="text/javascript">
		</script>
		<script>
			function creatClassTable(myClassTime,myClassName) {
				var myClass1=document.getElementById('myClass').rows[1].cells;
				if(myClassTime=="周一12"){
					myClass1[0].innerHTML=myClassName;
				}
				if(myClassTime=="周二12"){
					myClass1[1].innerHTML=myClassName;
				}
				if(myClassTime=="周三12"){
					myClass1[2].innerHTML=myClassName;
				}
				if(myClassTime=="周四12"){
					myClass1[3].innerHTML=myClassName;
				}
				if(myClassTime=="周五12"){
					myClass1[4].innerHTML=myClassName;
				}
				var myClass2=document.getElementById('myClass').rows[2].cells;
				if(myClassTime=="周一34"){
					myClass2[0].innerHTML=myClassName;
				}
				if(myClassTime=="周二34"){
					myClass2[1].innerHTML=myClassName;
				}
				if(myClassTime=="周三34"){
					myClass2[2].innerHTML=myClassName;
				}
				if(myClassTime=="周四34"){
					myClass2[3].innerHTML=myClassName;
				}
				if(myClassTime=="周五34"){
					myClass2[4].innerHTML=myClassName;
				}
				var myClass3=document.getElementById('myClass').rows[3].cells;
				if(myClassTime=="周一56"){
					myClass3[0].innerHTML=myClassName;
				}
				if(myClassTime=="周二56"){
					myClass3[1].innerHTML=myClassName;
				}
				if(myClassTime=="周三56"){
					myClass3[2].innerHTML=myClassName;
				}
				if(myClassTime=="周四56"){
					myClass3[3].innerHTML=myClassName;
				}
				if(myClassTime=="周五56"){
					myClass3[4].innerHTML=myClassName;
				}
				var myClass4=document.getElementById('myClass').rows[4].cells;
				if(myClassTime=="周一78"){
					myClass4[0].innerHTML=myClassName;
				}
				if(myClassTime=="周二78"){
					myClass4[1].innerHTML=myClassName;
				}
				if(myClassTime=="周三78"){
					myClass4[2].innerHTML=myClassName;
				}
				if(myClassTime=="周四78"){
					myClass4[3].innerHTML=myClassName;
				}
				if(myClassTime=="周五78"){
					myClass4[4].innerHTML=myClassName;
				}

			}
		</script>
	</head>
	<body>
<%--	    返回选课结果--%>
<%--        param.msg无法直接写选课成功失败，不要传中文，无法显示，不稳定--%>
		<c:if test="${param.msg == 0}">
			<span style="color:red;">操作失败！</span>
		</c:if>
		<c:if test="${param.msg == 1}">
			<span style="color:green;">操作成功！</span>
		</c:if>
<%--        method写post防止cid覆盖--%>
		<form action="${basePath}sc?method=submit" method="post">
			<table class="tablelist">
				<c:forEach items="${courses}" var="course">
				<tr>
					<td width="50px" align="center">
<%--						循环遍历scs，通过list，将选中的cid的checked设为checked--%>
						<input type="checkbox"
							<c:forEach items="${scs}" var="sc">
								<c:if test="${sc.cId eq course.cId}">checked="checked"</c:if>
							</c:forEach>
						 name="cId" value="${course.cId}">
					</td>
					<td>
					${course.cName}
					</td>
					<td>
					${course.teacher.tName}
					</td>
					<td>
					${course.cTime}
					</td>
				</tr>
				</c:forEach>
			</table>
			<br/>
			<c:if test="${stuid!=15&&stuid != 3}">
				<button class="mybtn">
					<i class="fa fa-save"></i>
					提交选课
				</button>
			</c:if>
			<c:if test="${stuid==15||stuid == 3}">
				<button class="mybtn" disabled="disabled">
					<i class="fa fa-save"></i>
					当前不再选课时间内！
				</button>
			</c:if>
		</form>
		<form action="${basePath}sc?method=time">

			<div class="condition">
				选课开始时间：
				<c:if test="${stuid !=15 && stuid != 3}" var="sc">
					<input type="text"name="start"disabled="true"value="2021/12/20">
					结束时间：<input type="text"name="start"disabled="true"value="2021/12/26">
				</c:if>
				<c:if test="${stuid == 15|| stuid == 3}" var="sc">
					<input type="text"name="start"disabled="true"value="2021/12/13">
					结束时间：<input type="text"name="start"disabled="true"value="2021/12/19">
				</c:if>
			</div>
		</form>

	    <table class="tablelist" id="myClass">
			<thead>
			<tr>
				<td>周一</td>
				<td>周二</td>
				<td>周三</td>
				<td>周四</td>
				<td>周五</td>
			</tr>
			</thead>
			<tbody >
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			</tbody>
		</table>
        <c:forEach items="${courses}" var="course">
			<c:forEach items="${scs}" var="sc">
				<c:if test="${sc.cId eq course.cId}">
					<script>
						creatClassTable('${course.cTime}','${course.cName}');
					</script>
		         </c:if>
			</c:forEach>
        </c:forEach>

	</body>
</html>
