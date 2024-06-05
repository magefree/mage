@ECHO OFF
IF NOT EXIST "C:\Program Files (x86)\Java\jre7\bin\" GOTO NOJAVADIR
set JAVA_HOME="C:\Program Files (x86)\Java\jre7\"
set CLASSPATH=%JAVA_HOME%/bin;%CLASSPATH%
set PATH=%JAVA_HOME%/bin;%PATH%
:NOJAVADIR
java -Xmx1024m -XX:MaxPermSize=384m -jar ./lib/mage-server-${project.version}.jar