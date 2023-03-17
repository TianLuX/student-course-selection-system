<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="container4"></div>
<script type="text/javascript" src="${basePath}static/echarts/echarts.min.js"></script>
<script type="text/javascript">
var dom = document.getElementById("container4");
var myChart = echarts.init(dom);
var app = {};
option = null;
option = {
	title: {
        text: '科目及格率统计图'
    },
	tooltip: {
        trigger: 'axis'
    },
    xAxis: {
        type: 'category',
        axisLabel: {
               interval:0
           },
        data: [
        	<c:forEach items="${list}" var="temp">
       		'${temp.cname}',
       		</c:forEach>
        ]
    },
    yAxis: {
        type: 'value'
    },
    series: [{
        data: [
        	<c:forEach items="${list}" var="temp">
       		${temp.jgl},
       		</c:forEach>
        ],
        type: 'bar',
        showBackground: true,
        backgroundStyle: {
            color: 'rgba(220, 220, 220, 0.8)'
        }
    }]
};
;
if (option && typeof option === "object") {
    myChart.setOption(option, true);
}
</script>