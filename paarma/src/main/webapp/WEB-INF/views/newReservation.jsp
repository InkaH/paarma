<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<html>
<head>
    <title>Uusi paikkavaraus</title>
</head>
 
<body>
    <h2>Tee varaus</h2>
    <br/>
    <h3>${msg}</h3>
    
    <form:form method="post" modelAttribute="reservation">
    <label>Pöytänumero: </label>
    <form:input path="table"></form:input>
    <form:errors path="table" cssclass="error"></form:errors>
    <br />
     <br />
    <label>Aloituspvm: </label>
    <form:input path="startDate"></form:input>
    <form:errors path="startDate" cssclass="error"></form:errors>
    <br />
    <br />
    <label>Varausjaksoja (yksi jakso on 6 päivää): </label>
    <form:input path="numPeriods"></form:input>
    <form:errors path="numPeriods" cssclass="error"></form:errors>
    <br />
    <br />
    <p>Varausjakso päättyy: [placeholder]<p>
    <input type="submit" value="Tee varaus" />
    </form:form>
</body>
</html>
