CREATE TABLE users (
  id INTEGER IDENTITY PRIMARY KEY,
  firstname VARCHAR(30),
  lastname  VARCHAR(50)
);

CREATE TABLE reservations (
	id INTEGER IDENTITY PRIMARY KEY,
	userId INTEGER,
	startDate DATE,
	endDate DATE,
	numPeriods INTEGER,
	tablenum VARCHAR(20),
	tableprice DOUBLE,
	FOREIGN KEY (userId) REFERENCES users(id)
);
