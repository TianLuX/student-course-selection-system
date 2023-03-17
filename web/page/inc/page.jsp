<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%--在include文件夹中，分页功能单独实现--%>
<table class="page">
	<td>
        <%--首页的时候key值等于1--%>
		<button type="button" key="1">首页</button>
        <%--每次点击的时候传值上一页--%>
		<button type="button" <c:if test="${pageInfo.firstPage == true}">disabled</c:if> key="${pageInfo.prePage}">上一页</button>
		<%--每次点击的时候传值下一页--%>
		<button type="button" <c:if test="${pageInfo.lastPage == true}">disabled</c:if>  key="${pageInfo.nextPage}">下一页</button>
		<button type="button" key="${pageInfo.totalPage}">尾页</button>
<%--			输入的页码数--%>
		<input  type="text"  class="page-no"  id="page-no" value="${pageInfo.pageNo}" />
<%--			type要写清楚为button，否则默认submit--%>
		<button type="button" class="zhuan">转</button>
		总记录条数${pageInfo.totalCount}条，当前${pageInfo.pageNo}/${pageInfo.totalPage}页  每页${pageInfo.pageSize}条数据
	</td>
</table>
<script type="text/javascript">
	$(function(){
		// 转功能的实现
		$('.page .zhuan').click(function(){
			// parseInt($('#page-no').val()拿到value值，页码数，且在小于总页码数的时候才能点击，否则不能点击
			if(parseInt($('#page-no').val())<=parseInt("${pageInfo.totalPage}")){
				$('input[name="pageNo"]').val($('#page-no').val());
				$("#tableList").submit();
			}else{
				// 警告输入页数不能大于总页数
				alert("不能大于总页数");
			}
		});
		// jQuery选择器，选择除了转按钮以外的
		$('.page button:not(.zhuan)').click(function(){
			$('input[name="pageNo"]').val($(this).attr("key"));
			$("#tableList").submit();
		})
	})
</script>