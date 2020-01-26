#!/bin/sh

cd "`dirname "$0"`"

java -Xms256M -Xmx512M -XX:MaxPermSize=256m -Djava.security.policy=./config/security.policy -Dlog4j.configuration=file:./config/log4j.properties -jar ./lib/mage-server-${project.version}.jar
