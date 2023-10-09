include .env

# Define the target directory as a variable with a default value.
# You can override this with an environment variable, ex
# TARGET_DIR=my_custom_directory make deploy
# Alternatively, you can set this variable in the .env file
TARGET_DIR ?= deploy/

.PHONY: deploy
deploy:
	# Building project
	cd . && mvn clean install package -DskipTests
	# Packaging Mage.Client to zip
	cd Mage.Client && mvn assembly:assembly -DskipTests
	# Packaging Mage.Server to zip
	cd Mage.Server && mvn assembly:assembly
	# Copying the files to the target directory
	mkdir -p $(TARGET_DIR)
	cp ./Mage.Server/target/mage-server.zip $(TARGET_DIR)
	cp ./Mage.Client/target/mage-client.zip $(TARGET_DIR)

