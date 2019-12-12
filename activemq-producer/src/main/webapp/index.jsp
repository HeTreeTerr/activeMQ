<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>this is my activemq producer</title>
</head>
<body>
	<center>
		<form action="<%=basePath %>sendMessage" method="post">
			
			收件人：<input type="text" name="to"/><br/>
			主题：<input type="text" name="subject"/><br/>
			正文：<textarea row="3" cols="22" name="content" ></textarea><br>
			<input type="submit" value="发送" />&nbsp;
			<input type="reset" value="重置" />
		</form>
	</center>
</body>
</html>
