CREATE TABLE reservations(
	id INTEGER IDENTITY PRIMARY KEY,
	userId INTEGER,
	startDate DATE,
	numPeriods INTEGER,
	tablenum INTEGER,
	tableprice DOUBLE,
	FOREIGN KEY (userId) REFERENCES users(id)
);
