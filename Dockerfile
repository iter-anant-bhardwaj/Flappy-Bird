FROM eclipse-temurin:25-jre
WORKDIR /app
COPY target/*.jar app.jar 
CMD ["java", "-jar", "app.jar"] 