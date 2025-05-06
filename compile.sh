#!/bin/bash

# First compile the base classes
javac stocks/*.java
javac reports/Report.java
javac transactions/Transaction.java
javac transactions/IncomingTransaction.java transactions/OutgoingTransaction.java

# Then compile the reports
javac reports/*.java

# Finally compile the main package
javac main/util/*.java
javac main/*.java 
# Compile tests
javac -cp "lib/*:." test/*.java

# Compile tests
javac -cp "lib/*:." test/*.java
