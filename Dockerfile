FROM openjdk:11
COPY . /app
WORKDIR /app
RUN ./gradlew --no-daemon bootJar
ENTRYPOINT ["java", "-jar", "build/libs/alfabanktest-0.0.1-SNAPSHOT.jar"]