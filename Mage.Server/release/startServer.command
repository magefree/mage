#!/bin/sh

cd "`dirname "$0"`"

java -Djava.security.policy=./config/security.policy -Djava.util.logging.config.file=./config/logging.config -Dlog4j.configuration=file:./config/log4j.properties -jar ./lib/Mage-Server-${project.version}.jar -Xms256M -Xmx512M
