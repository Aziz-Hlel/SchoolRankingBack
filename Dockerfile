# ---------- Stage 1: Build ----------
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build

WORKDIR /build

# Leverage caching for dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Add source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Stage 2: Runtime (Slim + Secure) ----------
FROM eclipse-temurin:17-jre-alpine

# Create a non-root user (recommended for security)
RUN addgroup -S spring && adduser -S spring -G spring

USER spring
WORKDIR /app

# Copy only the built artifact
COPY --from=build /build/target/*.jar app.jar

# JVM + Spring Boot prod profile
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseContainerSupport -Dspring.profiles.active=prod"

# Expose only what you need
EXPOSE 8080

# Run the app with proper entrypoint
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]
