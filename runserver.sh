mvn install -DskipTests
cd Mage.Server
#mvn exec:java -Dexec.mainClass=mage.server.Main 
mvn exec:java -Dexec.mainClass=mage.server.Main -Dexec.args="-testMode=true"