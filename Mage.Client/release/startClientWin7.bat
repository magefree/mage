@ECHO OFF
IF NOT EXIST "C:\Program Files (x86)\Java\jre6\bin\" GOTO NOJAVADIR
set JAVA_HOME="C:\Program Files (x86)\Java\jre6\"
set CLASSPATH=%JAVA_HOME%/bin;%CLASSPATH%
set PATH=%JAVA_HOME%/bin;%PATH%
:NOJAVADIR
start javaw -jar .\lib\mage-client-${project.version}.jar -Xms256M -Xmx1024M -XX:MaxPermSize=256M