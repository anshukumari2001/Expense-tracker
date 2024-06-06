FROM openjdk:17

COPY build/libs/expensetrackerapi.jar expensetrackerapi.jar

ENTRYPOINT ["java", "-jar", "/expensetrackerapi.jar"]