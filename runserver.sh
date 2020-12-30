cd Mage.Server
mvn install -DskipTests
#mvn exec:java -Dexec.mainClass=mage.server.Main 
mvn exec:java -Dexec.mainClass=mage.server.Main -Dexec.args="-testMode=true"