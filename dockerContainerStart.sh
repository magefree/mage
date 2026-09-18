#!/bin/sh

XMAGE_CONFIG=/xmage/config/config.xml

sed -i -e "s#\(serverAddress=\)[\"].*[\"]#\1\"$MAGE_SERVER_ADDRESS\"#g" ${XMAGE_CONFIG}
sed -i -e "s#\(serverName=\)[\"].*[\"]#\1\"$MAGE_SERVER_NAME\"#g" ${XMAGE_CONFIG}
sed -i -e "s#\(port=\)[\"].*[\"]#\1\"$MAGE_PORT\"#g" ${XMAGE_CONFIG}
sed -i -e "s#\(secondaryBindPort=\)[\"].*[\"]#\1\"$MAGE_SECONDARY_BIND_PORT\"#g" ${XMAGE_CONFIG}
sed -i -e "s#\(maxSecondsIdle=\)[\"].*[\"]#\1\"$MAGE_MAX_SECONDS_IDLE\"#g" ${XMAGE_CONFIG}
sed -i -e "s#\(authenticationActivated=\)[\"].*[\"]#\1\"$MAGE_AUTHENTICATION_ACTIVATED\"#g" ${XMAGE_CONFIG}

java -Xms256M -Xmx512M -XX:MaxPermSize=256m -Djava.security.policy=./config/security.policy -Djava.util.logging.config.file=./config/logging.config -Dlog4j.configuration=file:./config/log4j.properties -jar ./lib/mage-server-*.jar