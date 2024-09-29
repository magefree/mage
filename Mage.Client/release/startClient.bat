@ECHO OFF
IF NOT EXIST "C:\Program Files\Java\jre7\bin\" GOTO NOJAVADIR
set JAVA_HOME="C:\Program Files\Java\jre7\"
set CLASSPATH=%JAVA_HOME%/bin;%CLASSPATH%
set PATH=%JAVA_HOME%/bin;%PATH%
:NOJAVADIR
java -Xmx1024m -Dfile.encoding=UTF-8 -Djava.net.preferIPv4Stack=true -jar .\lib\mage-client-${project.version}.jar