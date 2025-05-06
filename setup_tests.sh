#!/bin/bash

# Create lib directory if it doesn't exist
mkdir -p lib

# Download JUnit dependencies
curl -L https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter-api/5.8.1/junit-jupiter-api-5.8.1.jar -o lib/junit-jupiter-api.jar
curl -L https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter-engine/5.8.1/junit-jupiter-engine-5.8.1.jar -o lib/junit-jupiter-engine.jar
curl -L https://repo1.maven.org/maven2/org/junit/platform/junit-platform-commons/1.8.1/junit-platform-commons-1.8.1.jar -o lib/junit-platform-commons.jar
curl -L https://repo1.maven.org/maven2/org/junit/platform/junit-platform-engine/1.8.1/junit-platform-engine-1.8.1.jar -o lib/junit-platform-engine.jar
curl -L https://repo1.maven.org/maven2/org/opentest4j/opentest4j/1.2.0/opentest4j-1.2.0.jar -o lib/opentest4j.jar
curl -L https://repo1.maven.org/maven2/org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2.jar -o lib/apiguardian-api.jar
curl -L https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.8.1/junit-platform-console-standalone-1.8.1.jar -o lib/junit-platform-console-standalone.jar

# Create test directory if it doesn't exist
mkdir -p test

# Update compile script to include tests
echo '
# Compile tests
javac -cp "lib/*:." test/*.java' >> compile.sh

# Create run tests script
echo '#!/bin/bash

# Run all tests
java -jar lib/junit-platform-console-standalone.jar -cp ".:test" --scan-classpath' > run_tests.sh

# Make scripts executable
chmod +x setup_tests.sh run_tests.sh 