# Money transfer API

Simple project for money transfering between two accounts

# TODO
Resolve overdraft when accessing concurrently the transfering \
Make database connection be thread safe \
Create concurrency tests

## Technologies
Java 11 \
Vertx 3.8.1 \
H2 Database 1.4.199 \
Jooq 3.11.11 \
JUnit 5.4.2 

## Build
```
mvn clean install
```

## Run 
```
mvn vertx:run
```