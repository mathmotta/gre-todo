# Golden Real Estate ToDo

A Todo App for a fictional company Golden Real Estate using VueJS and SpringBoot.

## Application Requirements

* It should have three Entities: person, building and project
* CRUD WebApi for Person, Building and Project
* Project's status can be filtered by building and person

## Pre-requisites

Java 11
Apache Maven 3.6.1

## Running the app



## Decisions

### Lombok
I really like Project Lombok (https://projectlombok.org/) to remove boilerplate code, improve readability and avoid needing to unit test data containers. It has actually generated a LOT of heated discussion amongst other developers in my company. Since I don't know the fellow developers that will look into this project, I'm going to progress as I would in a situation where I need to wait for a "decision meeting": I will NOT use Lombok, but will also NOT make Unit Tests on DAO/DTO's or other data containers "until there's a common agreement".

### Postgres
By default, the Postgres driver does not accept users with no passwords. Although the description of the project mentions an user without password, I do not agree with that as it will never happen in a real-world project anyway - so no reason to go on about changing stuff just for the sake of it. Therefore, to use the project, a user 'sylvain' with password 'sylvain' is needed.


## Challenges

Although I sometimes work with maven projects, the majority of my projects use gradle - which has lots of common characteristics, but I had to learn how to deal with Maven sometimes.

## External Resources

Dealing with multiple projects in Maven vs. Gradle:

https://maven.apache.org/guides/mini/guide-multiple-modules.html

When running the app, eventhough I was using java.version == 11, Maven was still complaining that I was using 1.5
Found out on this article how to solve this issue:

https://www.baeldung.com/maven-java-version

Getting all of hibernate properties right in the yaml needed a few Google searches, some sources where I found important info:

https://stackoverflow.com/questions/47823564/postgresql-failure-in-spring-boot
https://stackoverflow.com/questions/50213381/how-do-i-configure-hikaricp-for-postgresql

Specification overview for general filter implementation:

https://www.baeldung.com/rest-api-search-language-spring-data-specifications

Using specification to compare foreign key filters:

https://stackoverflow.com/questions/60358134/jpa-specification-multiple-join-based-on-foreignkey

Getting all Asgard realms names accents correctly:

https://en.wikipedia.org/wiki/Asgard#Realms_of_Asgard

Usually we take care of paging and sorting our own way, so I never really looked into Spring's Pageable/Page objects (which includes sorting!) so I took the opportunity to learn more about it

https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html
https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/domain/Page.html
https://www.baeldung.com/spring-data-jpa-pagination-sorting