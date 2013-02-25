@ECHO OFF
java -Djava.security.policy=./config/security.policy -Djava.util.logging.config.file=./config/logging.config -Dlog4j.configuration=file:./config/log4j.properties -jar ./lib/mage-server-${project.version}.jar -Xms256M -Xmx512M -XX:MaxPermSize=256m
pause
