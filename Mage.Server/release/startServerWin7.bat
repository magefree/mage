@ECHO OFF
IF NOT EXIST "C:\Program Files (x86)\Java\jre7\bin\" GOTO NOJAVADIR
set JAVA_HOME="C:\Program Files (x86)\Java\jre7\"
set CLASSPATH=%JAVA_HOME%/bin;%CLASSPATH%
set PATH=%JAVA_HOME%/bin;%PATH%
:NOJAVADIR
java -Xms256M -Xmx512M -Dfile.encoding=UTF-8 -Djava.security.policy=./config/security.policy -jar ./lib/mage-server-${project.version}.jar
