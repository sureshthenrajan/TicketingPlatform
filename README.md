# Ticketing Service
Simple ticket service that facilitates the discovery, temporary hold, and final reservation 
of seats within a high-demand performance venue.

## Assumptions:
1. Multi-event booking support available, but data pre-populated for a single event in db.
2. H2(db) is pre-populated with minimal data for one event for running integration tests. 
3. For every new event, a db script will be run to pre-populate db(seat_hold table) with 
available seats. No separate count is maintained for keeping track of total seats.
4. No payment/purchase flow implemented.
5. Service decides the best seat to reserve depending on the availability, no choice given to the user.
6. Customer details are not maintained in the system except for customer email.
7. No user registration flow implemented.
8. No User Interface provided.
9. Schema designed to support multi-event ticket booking simultaneously. Service methods
not provided.

## Notes
1. APIs provided for accessing ticketing service methods(details in 'APIs' section).
2. In-memory db, embedded tomcat used for the application.


## Tech Stack
* Spring Boot
* H2(In-memory)
* Tomcat(Embedded)

## Pre-requisites
* maven - 3.x
* JDK - 1.8

## How to run the application

### Stand-alone mode: 
	mvn spring-boot:run
This will start the app in a stand-alone mode. An embedded tomcat and h2 db
Build and run unit/integration tests: mvn clean install


### Code coverage
	mvn clean cobertura:cobertura
Reports available in target/site/cobertura/index.html

### APIs:

Following RESTful APIs provide access to the ticketing service methods.
For simplicity/ease of access, all required params are provided as query params.
Sample Request provided for all APIs.    

- GET /tickets/heartBeat
http://localhost:8080/tickets/heartBeat
	Response:
```
{
	"heartBeat": "Ok",
	"Code": "200"
}
```
-	GET /tickets/numSeatsAvailable
	- http://localhost:8080/tickets/numSeatsAvailable?level=1

- 	POST /tickets/findAndHoldSeats
```	
curl -X POST -H "Content-Type: multipart/form-data; boundary=testRun" "http://localhost:8080/tickets/findAndHoldSeats?numReqSeats=3&minLevel=1&maxLevel=4&customerEmail=test"
Response:

		{
   			"seatHoldId":45824118,
   			"seats":[
      				{
         			"seatId":1,
         			"eventId":1,
         			"eventName":"beethoven",
         			"levelId":1,
         			"price":100.0,
         			"rowNumber":2,
         			"columnNumber":11,
         			"holdId":100,
         			"holdTtl":1445395384695,
         			"reserved":false
      				}
   				],
   			"customer_email":"test",
   			"errorMessage":null,
   			"holdDone":true
		}	
```
-	POST /tickets/reserveSeats
```		
curl -X POST -H "Content-Type: multipart/form-data; boundary=testRun" "http://localhost:8080/tickets/reserveSeats?seatHoldId=100&customerEmail=test"
Response:
	
		{
   			"statusCode":"200",
   			"reservationCode":"sdff-0909"
		}
```