<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 
<html>
<head>
    <title>Uusi käyttäjä</title>
</head>
 
<body>
    <h2><spring:message code="lbl.page" text="Luo käyttäjätili" /></h2>
    <br/>
    <form:form method="post" modelAttribute="user">
        <table>
            <tr>
                <td><spring:message code="lbl.firstName" text="Etunimi" /></td>
                <td><form:input path="firstName" /></td>
            </tr>
            <tr>
                <td><spring:message code="lbl.lastName" text="Sukunimi" /></td>
                <td><form:input path="lastName" /></td>
            </tr>
            <tr>
                <td><spring:message code="lbl.streetAddress" text="Katuosoite" /></td>
                <td><form:input path="streetAddress" /></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Luo tili"/></td>
            </tr>
        </table>
    </form:form>
</body>
</html>
