@ECHO OFF
IF NOT EXIST "C:\Program Files\Java\jre6" GOTO NOJAVADIR
set JAVA_HOME="C:\Program Files\Java\jre6"
set CLASSPATH=%JAVA_HOME%/bin;%CLASSPATH%
set PATH=%JAVA_HOME%/bin;%PATH%
:NOJAVADIR
java -Djava.security.policy=./config/security.policy -Djava.util.logging.config.file=./config/logging.config -jar ./Mage-Server-${project.version}.jar
pause