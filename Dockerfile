FROM openjdk:17-oracle
COPY target/quotation-management-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]