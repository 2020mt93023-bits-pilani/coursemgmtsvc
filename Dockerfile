FROM openjdk:13-alpine3.9
RUN mkdir -p /opt/coursesvc/jar
WORKDIR /opt/coursesvc/jar
COPY target/*.jar .
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "courseservice-0.0.1-SNAPSHOT.jar"]