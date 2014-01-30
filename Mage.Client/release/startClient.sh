#!/bin/sh

java -Xms1024m -Xmx1024m -XX:MaxPermSize=256m -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -jar ./lib/mage-client-${project.version}.jar &