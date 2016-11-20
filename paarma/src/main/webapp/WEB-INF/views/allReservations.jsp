<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
   <head>
      <title>Paarma-varausjärjestelmä</title>
   </head>
   <body>      
      <h3>${msg}</h3>
      <h5>Varaustiedot:</h5>    
      <p>Asiakastunnus: ${user.id}</p>
      <p>Varaustunnus: ${reservation.id}</p>
      <p>Pöytänumero: ${reservation.table}</p>
      <p>Varauksen alkupvm: ${reservation.startDate}</p>
      <p>Varauksen loppupvm: ${reservation.endDate}</p>
      <p>Varausjaksoja: ${reservation.numPeriods}</p>
      <p>Hinta/varausjakso: ${reservation.tablePrice}</p>
      <p>Kokonaishinta: ${reservation.totalPrice}</p>
      <br>
      
      <h3><a href="dashboard">Takaisin</a></h3>
      <h3> <a href="deleteReservation">Poista varaus</a></h3>
   </body>
</html>