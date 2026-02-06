FROM openjdk:27-ea-trixie
LABEL authors="Akif"
COPY target/assetmanagement-app.jar assetguardian.jar

ENTRYPOINT ["java", "-jar", "/assetguardian.jar"]