FROM eclipse-temurin:17-jre-alpine

LABEL component="signature-provider"
LABEL componentVersion="@project.version@"
LABEL maintainer="kolychevgv92@gmail.com"

ARG USER=signature-provider
ARG WORKDIR=/$USER
ARG JAR_FILE=build/libs/*.jar

RUN addgroup -S "$USER" && adduser -S "$USER" -G "$USER"

USER $USER
COPY --chown=$USER $JAR_FILE $WORKDIR/signature-provider-app.jar
WORKDIR $WORKDIR

EXPOSE 8080
EXPOSE 8081

ENTRYPOINT  ["java", "-jar", "signature-provider-app.jar"]
