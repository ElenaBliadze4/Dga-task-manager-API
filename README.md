# Task Management Elena DGA REST API

### წინამდებარე რესთ აპლიკაცია განკუთვნილა დავალებების მართვისთვის, ანუ შექმნილია თასქების მართვის სისტემა, რომელსაც გააჩნია მონაც.ბაზა PostgreSQL-ზე და რესთ ეპიაი აწყობილია Spring Boot ტექნოლოგიით.

## გამოყენებული ტექნოლოგიები
- Java 25
- Spring Boot 4.0.6
- Spring Data JPA / Hibernate
- PostgresSQL
- Git
- Github

## პროექტის გამართვა და გაშვება

თავდაპირველად ვრთავთ დოკერში კონტეინერს, და პარალელურად გვაქვს გაწერილი properties ში , ბაზასთან მაკავშირებელი კონფიგურაცია:

`spring.datasource.url=jdbc:postgresql://localhost:5432/postgres?currentSchema=public`

`spring.datasource.username=postgres`

`spring.datasource.password=შესაბამისი პაროლი ბაზის`

`spring.datasource.driver-class-name=org.postgresql.Driver`

შემდეგ ვუშვებთ აპლიკაციას და გადავდივართ swagger ზე შემდეგ ლინკზე:

`http://localhost:8080/swagger-ui/index.html#/`

