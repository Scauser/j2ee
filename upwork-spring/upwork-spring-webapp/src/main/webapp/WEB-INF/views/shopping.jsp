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
	<form:form id="startTransaction" modelAttribute="shopping"
		method="post" action="start">
		<table border="0">
			<tr>
				<td><form:label path="merchantId">Merchant ID</form:label></td>
				<td><form:input path="merchantId" /></td>
			</tr>
			<tr>
				<td><form:label path="terminalId">Terminal ID</form:label></td>
				<td><form:input path="terminalId" /></td>
			</tr>
			<tr>
				<td><form:label path="languageCode">Language Code</form:label></td>
				<td><form:input path="languageCode" /></td>
			</tr>
			<tr>
				<td><form:label path="merchantLocalDate">Merchant Date</form:label></td>
				<td><form:input path="merchantLocalDate" /></td>
			</tr>
			<tr>
				<td><form:label path="returnUrl">Return URL</form:label></td>
				<td><form:input path="returnUrl" size="120" /></td>
			</tr>
			<tr>
				<td><form:label path="purchaserName">Purchaser Name</form:label></td>
				<td><form:input path="purchaserName" /></td>
			</tr>
			<tr>
				<td><form:label path="purchaserEmail">Purchaser Email</form:label></td>
				<td><form:input path="purchaserEmail" /></td>
			</tr>
			<tr>
				<td><form:label path="orderId">Order ID</form:label></td>
				<td><form:input path="orderId" /></td>
			</tr>
			<tr>
				<td><form:label path="totalAmount">Amount</form:label></td>
				<td><form:input path="totalAmount" /></td>
			</tr>
			<tr>
				<td><form:label path="currencyCode">Currency Code</form:label></td>
				<td><form:input path="currencyCode" /></td>
			</tr>
			<tr>
				<td>GoodsItems</td>
				<td>
					<table border="1">
						<c:forEach var="goodsItem" items="${shopping.goodsItems}">
							<tr>
								<td>Goods Name</td>
								<td>${goodsItem.nameOfGoods}</td>
							</tr>
							<tr>
								<td>Amount</td>
								<td>${goodsItem.amount}</td>
							</tr>
							<tr>
								<td>Currency code</td>
								<td>${goodsItem.currencyCode}</td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Start" /></td>
			</tr>
		</table>
	</form:form>
	<form:form id="addGoodsItem" modelAttribute="goodsItem" method="post"
		action="addGoodsItem">
		<table>
			<tr>
				<td><form:label path="nameOfGoods">Goods name</form:label></td>
				<td><form:input path="nameOfGoods" /></td>
			</tr>
			<tr>
				<td><form:label path="amount">Amount</form:label></td>
				<td><form:input path="amount" /></td>
			</tr>
			<tr>
				<td><form:label path="currencyCode">Currency code</form:label></td>
				<td><form:input path="currencyCode" /></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Add GoodsItem" /></td>
			</tr>
		</table>
	</form:form>
	<form:form id="test" method="get"
		action="https://test.processing.kz/kmod/CNPMerchantService/test">
		<input type="submit" value="Test" />
	</form:form>
</body>
</html>
