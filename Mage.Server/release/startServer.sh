#!/bin/sh -x

script_dir="$(readlink -f -- "$BASH_SOURCE")"
script_dir="$(dirname -- $script_dir)"
cd $script_dir

java -Xms256M -Xmx512M -XX:MaxPermSize=256m "-Djava.security.policy=${script_dir}/config/security.policy" "-Dlog4j.configuration=file:${script_dir}/config/log4j.properties" -jar "${script_dir}/lib/mage-server-${project.version}.jar"
