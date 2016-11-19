<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 
<html>
<head>
    <title>Myyntipaikan varaus</title>
</head>
 
<body>
    <form:form method="post" modelAttribute="reservation">
        <table>
            <tr>
                <td><spring: text="Paikkanro" /></td>
                <td><form:input path="firstName" /></td>
            </tr>
            <tr>
                <td><spring:message code="lbl.lastName" text="Alkupvm" /></td>
                <td><form:input path="lastName" /></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Varaa"/></td>
            </tr>
        </table>
    </form:form>
</body>
</html>
