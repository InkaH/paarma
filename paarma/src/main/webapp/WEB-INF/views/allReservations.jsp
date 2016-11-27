<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" 
           uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
   <head>
      <link href="css/bootstrap.min.css" rel="stylesheet">
      <title>Paarma-varausjärjestelmä</title>
   </head>
   <body>      
      <h3>${msg}</h3>
          <c:if test="${empty reservations}">
                Ei varauksia.
          </c:if>
          <c:if test="${not empty reservations}">

	      <c:forEach var="reservation" items="${reservations}">
	      <h5>Varaustiedot:</h5>    
	      <p>Asiakastunnus: ${reservation.userId}</p>
	      <p>Varaustunnus: ${reservation.id}</p>
	      <p>Pöytänumero: ${reservation.table}</p>
	      <p>Varauksen alkupvm: <fmt:formatDate pattern="dd.MM.yyyy" 
	            value="${reservation.startDate}" /></p>
	      <p>Varauksen loppupvm: <fmt:formatDate pattern="dd.MM.yyyy" 
	            value="${reservation.endDate}" /></p>
	      <p>Varausjaksoja: ${reservation.numPeriods}</p>
	      <p>Hinta/varausjakso: <fmt:formatNumber type="number" 
            maxFractionDigits="2" value="${reservation.tablePrice}" />€</p>
      	  <p>Kokonaishinta: <fmt:formatNumber type="number" 
            maxFractionDigits="2" value="${reservation.tablePrice}" />€</p>
            
	      <p> <a href="deleteReservation">Poista varaus</a></p>   
	      <p> <a href="editReservation">Muokkaa varausta</a></p>   
	      </c:forEach></c:if>
	      
      <h3><a href="dashboard">Takaisin</a></h3>
   </body>
</html>