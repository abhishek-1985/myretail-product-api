# myretail-product-api
The repository contains source code for myretail-product-api RESTFUL service

## Leveraged Technology  
Role | Technology
-----|-----------
Runtime | Java 8
Database | Mongodb
Repository | MongoRepository
HTTP Server | Spring Boot (embedded Tomcat)
Task Automation | Gradle
Unit Tests | JUnit
IDE | Intellij Idea

## Getting Setup
To begin, please install the following technology on your machine:

Technology | Version | Installation Link
-----------|---------|------------------
Java SE Development Kit | 1.8u51 (64-bit) | http://www.oracle.com/technetwork/java/javase/downloads/java-archive-javase8-2177648.html#jdk-8u51-oth-JPR
Intellij Idea IDE for J2EE Developers | Community Edition | https://www.jetbrains.com/idea/download/
Mongodb | Mongodb Binaries | Follow the instructions in teh follwoing link to install Mongodb and start a mongo server on your local machine :- https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/

After downloading and installing the softwares above, you should be able to clone this repository and import it as an existing gradle project into your Intellij Idea workspace. 

## Database Setup

The mongo shell is an interactive JavaScript interface to MongoDB. You can use the mongo shell to query and update data as well as perform administrative operations.

To start the mongo shell and connect to your MongoDB instance running on localhost with default port:

   * At a prompt in a terminal window (or a command prompt for Windows), go to your <mongodb installation dir>: ```cd <mongodb installation dir>```

   * Type ./bin/mongo to start mongo: ```./bin/mongo```

   * Type the following command to create a new database - pricingdb ```use pricingdb```
   
   * Create pricing document and insert the follwoing data :-
      ```
          db.pricing.insertMany(
             [
               { item: "15117729", value: 50, currency_code: "USD", },
               { item: "16483589", value: 14.49, currency_code: "USD",  },
               { item: "16696652", value: 30, currency_code: "USD",  },
               { item: "16752456", value: 16.99, currency_code: "USD",  },
               { item: "15643793", value: 17.99, currency_code: "USD", }
             ]
          )
      ```    

## Service Interaction Points
### Item
Method | URI | Action
-------|-----|-------
GET | /products/{id} | Retrieves Item data with id
PUT | /products/{id} | Updates pricing data for id
