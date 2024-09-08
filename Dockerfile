FROM maven:3.9 AS builder

COPY . .
RUN mvn clean install -DskipTests \ 
    && cd ./Mage.Client \
    && mvn package assembly:single \
    && cd ../Mage.Server \
    && mvn package assembly:single

FROM openjdk:8-jre

ENV MAGE_SERVER_ADDRESS="0.0.0.0" \
    MAGE_PORT="17171" \
    MAGE_SECONDARY_BIND_PORT="17179" \
    MAGE_MAX_SECONDS_IDLE="600" \
    MAGE_AUTHENTICATION_ACTIVATED="false" \
    MAGE_SERVER_NAME="mage-server"

EXPOSE 17171 17179
WORKDIR /xmage

COPY --from=builder Mage.Server/target/mage-server.zip .

RUN unzip mage-server.zip \
    && rm mage-server.zip

COPY dockerContainerStart.sh /xmage/

RUN chmod +x \
    /xmage/startServer.sh \
    /xmage/dockerContainerStart.sh

CMD [ "./dockerContainerStart.sh" ]