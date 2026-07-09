# ──────────────────────────────────────────────
#  STAGE 1: Build con Maven + JDK 21
# ──────────────────────────────────────────────
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copiar el pom.xml
COPY pom.xml .

# Descargar dependencias
RUN maven_opts="-XX:+TieredCompilation -XX:TieredStopAtLevel=1" mvn dependency:go-offline -B

# Copiar el código fuente y compilar
COPY src src
RUN mvn clean package -DskipTests -B

# ──────────────────────────────────────────────
#  STAGE 2: Imagen final liviana con solo JRE 21
# ──────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar el JAR generado
COPY --from=build /app/target/*.jar app.jar

# Puerto interno
EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar"]
