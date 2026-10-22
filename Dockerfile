FROM eclipse-temurin:25-jdk AS builder
WORKDIR /build
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=builder /build/target/*.jar app.jar
EXPOSE 8080
ENV JAVA_OPTS="-XX:+UseSerialGC -Xss256k -Xms64m -Xmx256m -XX:MaxRAM=400m"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
