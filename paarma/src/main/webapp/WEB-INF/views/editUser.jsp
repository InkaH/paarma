<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 
<html>
<head>
    <title>Käyttäjätietojen päivitys</title>
</head>
 
<body>
    <h2>Käyttäjätietojen päivitys</h2>
    <br/>
    <form:form method="post" modelAttribute="user">
        <table>
            <tr>
                <td>Etunimi</td>
                <td><form:input path="firstName" /></td>
            </tr>
            <tr>
                <td>Sukunimi</td>
                <td><form:input path="lastName" /></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Tallenna"/></td>
            </tr>
        </table>
    </form:form>
</body>
</html>
