#!/bin/sh

java -Xmx1024m -Dfile.encoding=UTF-8 -Djava.net.preferIPv4Stack=true .\lib\mage-client-${project.version}.jar &
