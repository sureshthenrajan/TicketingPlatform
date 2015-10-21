DROP TABLE IF exists seat_hold;
create table seat_hold (
	seat_id INT NOT NULL,
	event_id INT NOT NULL,
	hold_id INT,
	level_id INT NOT NULL,
	event_name VARCHAR(128),	
	seat_row INT NOT NULL,
	seat_column INT NOT NULL,
	ticket_price DOUBLE NOT NULL,
	hold_ttl TIMESTAMP,
	reserved BOOLEAN,
	customer_email VARCHAR(256),
	lastupdated TIMESTAMP,
	UNIQUE hold (seat_id,event_id)
);

/*DROP TABLE IF exists customers;
create table customers (
	id INT NOT NULL,
	name VARCHAR(256) NOT NULL,
	email VARCHAR(256) NOT NULL
);*/

DROP table IF exists reservation;
create table reservation (
	confirmation_code VARCHAR(128) NOT NULL UNIQUE,
	customer_email VARCHAR(128) NOT NULL,
	event_id INT,
	event_name VARCHAR(128),
	reserved_seats INT,
	lastupdated TIMESTAMP
);
/*DROP table IF exists seat_count;
create table seat_count (
level_id INT NOT NULL,
event_id INT NOT NULL,
event_name VARCHAR(256) NOT NULL,
total_seats INT NOT NULL,
booked_seats INT NOT NULL,
lastupdated TIMESTAMP,
UNIQUE seat_total (level_id,event_id)
);*/


