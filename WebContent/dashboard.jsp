<%@page import="java.util.function.Consumer"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.sql.Date" %>
<%@page import="java.lang.Number" %>
<%@page import="java.util.List" %>

<jsp:include page="base_begin.jsp" />


<div class="row">
    <div class="container">
    	<canvas id="myAreaChart" width="100%" height="40"></canvas>
    </div>
</div>

<script type="text/javascript">
window.addEventListener('DOMContentLoaded', event => {
	let labels = [];
	let data = [];

	<%
		List<Date> labels = (List<Date>) request.getAttribute("labels");
		List<Number> data = (List<Number>) request.getAttribute("data");
	%>

	<% for (int i = 0; i < labels.size(); ++i) { %>
	labels.push("<%= labels.get(i) %>");
	data.push(<%= data.get(i) %>);
	<% } %>

	drawChart(labels, data);
});
</script>

<jsp:include page="base_end.jsp" />
