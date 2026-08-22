-include .env

# The target directory is used for setting where the output zip files will end up
# You can override this with an environment variable, ex
# TARGET_DIR=my_custom_directory make deploy
# Alternatively, you can set this variable in the .env file
TARGET_DIR ?= deploy/

.PHONY: clean
clean:
	mvn clean

.PHONY: build
build:
	mvn install package -DskipTests

.PHONY: test
test:
	mvn test -B -Dxmage.dataCollectors.printGameLogs=false

.PHONY: test-verify-cards
test-verify-cards:
	# Optional vars:
	# VERIFY_CHECK_SET_CODES=MSH or VERIFY_CHECK_SET_CODES='MSH;MSC' to limit sets
	mvn -B -pl Mage.Verify \
		-Dxmage.dataCollectors.printGameLogs=false \
		-Dtest=VerifyCardDataTest \
		-Dxmage.tests.verifyCheckSetCodes="$(VERIFY_CHECK_SET_CODES)" \
		test

test-with-game-logs:
	mvn -B -pl Mage.Tests -am \
		-Dxmage.dataCollectors.printGameLogs=true \
		-Dxmage.build.tests.treeViewRunnerShowAllLogs=true \
		test 2>&1 | tee tests_results.log


MAX_GAMES_AMOUNT ?= 1
test-ai-games-build:
	mvn -q -pl Mage.Tests -am \
	compile test-compile dependency:build-classpath \
	-Dmdep.outputFile=/tmp/mage-cp.txt
	
test-ai-games-run: test-ai-games-build
	java -cp "Mage.Tests/target/classes:Mage.Tests/target/test-classes:$$(cat /tmp/mage-cp.txt)" \
		--add-opens=java.base/java.io=ALL-UNNAMED \
		-Dxmage.dataCollectors.printGameLogs=true \
		-Dxmage.loadTests.maxGamesAmount=$(MAX_GAMES_AMOUNT) \
		org.junit.runner.JUnitCore org.mage.test.load.LoadTest \
		2>&1 | tee tests_results.log

.PHONY: package
package:
	# Packaging Mage.Client to zip
	cd Mage.Client && mvn package assembly:single
	# Packaging Mage.Server to zip
	cd Mage.Server && mvn package assembly:single
	# Copying the files to the target directory
	mkdir -p $(TARGET_DIR)
	cp ./Mage.Server/target/mage-server.zip $(TARGET_DIR)
	cp ./Mage.Client/target/mage-client.zip $(TARGET_DIR)

# Note that the proper install script is located under ./Utils/build-and-package.pl
# and that should be used instead. This script is purely for convenience.
# The perl script bundles the artifacts into a single zip
.PHONY: install
install: clean build package
