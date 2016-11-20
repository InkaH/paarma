CREATE TABLE users (
  id INTEGER IDENTITY PRIMARY KEY,
  firstname VARCHAR(30),
  lastname  VARCHAR(50)
);

CREATE TABLE reservations (
	id INTEGER IDENTITY PRIMARY KEY,
	userId INTEGER,
	startDate VARCHAR(50),
	numPeriods INTEGER,
	tablenum INTEGER,
	tableprice DOUBLE,
	FOREIGN KEY (userId) REFERENCES users(id)
);
