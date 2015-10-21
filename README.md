# Ticketing Service

## Assumptions:
1. Multi-event booking support available.
2. DB(H2) is pre-populated with minimal data for one event for running integration tests. 
3. For every new event, a db script will be run to pre-populate db(seat_hold table) with available seats. No separate
count is maintained for keeping track of total seats.
4. No payment option implemented.
5. Service decides which best seat to reserve depending on the availability, no choice given to the user.
6. Customer details are not maintained in the system except for customer email.

## Tech Stack
**Spring Boot**
**H2(In-memory)**
**Tomcat(Embedded)**

## Pre-requisites
**maven - 3.x**
**JDK - 1.8**

## How to run the application

### Stand-alone mode: 
	mvn spring-boot:run
This will start the app in a stand-alone mode. An embedded tomcat and h2 db
Build and run unit/integration tests: mvn clean install


### Code coverage
	mvn clean cobertura:cobertura
Reports available in target/site/cobertura/index.html

### APIs:

	- GET /tickets/heartBeat
	Sample: http://localhost:8080/tickets/heartBeat
	{
		"heartBeat": "Ok",
    	"Code": "200"
	}

	- GET /tickets/numSeatsAvailable
	Sample: http://localhost:8080/tickets/numSeatsAvailable?level=1

	- POST /tickets/findAndHoldSeats
	Sample: curl -X POST -H "Content-Type: multipart/form-data; boundary=testRun" "http://localhost:8080/tickets/findAndHoldSeats?numReqSeats=3&minLevel=1&maxLevel=4&customerEmail=test"
	- POST /tickets/reserveSeats
	Sample: curl -X POST -H "Content-Type: multipart/form-data; boundary=testRun" "http://localhost:8080/tickets/reserveSeats?seatHoldId=100&customerEmail=test"