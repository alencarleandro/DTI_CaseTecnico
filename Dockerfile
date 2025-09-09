# Etapa 1: Build da aplicação
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Imagem para rodar a aplicação
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*jar-with-dependencies.jar app.jar
COPY --from=build /app/jogos.db ./
ENTRYPOINT ["java", "-jar", "app.jar"]
