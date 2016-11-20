<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 
<html>
<head>
    <title>Käyttäjätietojen päivitys</title>
</head>
 
<body>
    <h2>Muokkaa käyttäjätiliä</h2>
    <br/>
    <h3>${msg}</h3>
    
    <form:form method="post" modelAttribute="user">
    	<label for="idInput">Asiakasnumero: </label>
	    <form:input path="id" id="idInput" readonly="true"></form:input>
	    <br />
	    
	    <label for="firstNameInput">Etunimi: </label>
	    <form:input path="firstName" id="nameInput"></form:input>
	    <form:errors path="firstName" cssclass="error"></form:errors>
	    <br />
     
	    <label for="lastNameInput">Sukunimi: </label>
	    <form:input path="lastName" id="ageInput"></form:input>
	    <form:errors path="lastName" cssclass="error"></form:errors>
	    <br />
    
    	<input type="submit" value="Luo tili" />
    </form:form>
</body>
</html>
