#!/bin/bash

# Run all tests
java -jar lib/junit-platform-console-standalone.jar -cp ".:test" --scan-classpath
