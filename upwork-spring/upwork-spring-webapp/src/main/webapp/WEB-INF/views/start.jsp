<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Shopping</title>
</head>
<body>
	<form:form id="startTransaction" modelAttribute="start" method="post"
		action="http://localhost:9080/CNPMerchantService/start">
		<input type="hidden" name="signed_Order_B64" value="${start.signedOrderB64}" />
		<input type="hidden" name="email" value="${start.email}" />
		<input type="hidden" name="backLink" value="${start.backLink}" />
		<input type="hidden" name="postLink" value="${start.postLink}" />
		<input type="hidden" name="failurePostLink" value="${start.failurePostLink}" />
		<input type="hidden" name="appendix" value="${start.appendix}" />
		<input type="submit" value="Pay" />
	</form:form>
</body>
</html>
