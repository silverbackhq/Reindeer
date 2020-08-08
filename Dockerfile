FROM gradle:4.7.0-jdk8-alpine as builder

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN make build

FROM openjdk:8-jre-slim

RUN mkdir -p /app/releases
RUN mkdir -p /app/configs
RUN mkdir -p /app/storage

VOLUME /app/storage

# Switch Reingeer to use env vars
ENV REINDEER_LOAD_FROM=system

ENV DB_DATABASE=/app/storage/db

COPY --from=builder /home/gradle/src/build/libs/reindeer-0.0.1-fat.jar /app/releases/reindeer-0.0.1-fat.jar
COPY --from=builder /home/gradle/src/build/libs/.env.example /app/configs/.env

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "/app/releases/reindeer-0.0.1-fat.jar", "--env=/app/configs/.env"]