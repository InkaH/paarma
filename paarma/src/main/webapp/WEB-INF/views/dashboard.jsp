<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
   <head>
      <title>Paarma-varausjärjestelmä</title>
   </head>
   <body>      
      <h3>${msg}</h3>
      <h5>Käyttäjätiedot:</h5>    
      <p>Asiakastunnus: ${user.id}</p>
      <p>Etunimi: ${user.firstName}</p>
      <p>Sukunimi: ${user.lastName}</p>
      
      <h3><a href="newReservation">Varaa myyntipaikka</a></h3>
      <h3> <a href="editUser">Päivitä tilitietojasi</a></h3>
   </body>
</html>