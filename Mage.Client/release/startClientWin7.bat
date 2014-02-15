@ECHO OFF
IF NOT EXIST "C:\Program Files (x86)\Java\jre7\bin\" GOTO NOJAVADIR
set JAVA_HOME="C:\Program Files (x86)\Java\jre7\"
set CLASSPATH=%JAVA_HOME%/bin;%CLASSPATH%
set PATH=%JAVA_HOME%/bin;%PATH%
:NOJAVADIR
start javaw -Xms256m -Xmx512m -XX:MaxPermSize=384m -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -jar .\lib\mage-client-${project.version}.jar