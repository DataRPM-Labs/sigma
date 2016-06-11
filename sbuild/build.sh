#!/bin/sh
scriptHome="`dirname "$0"`"
scriptHome="`cd "$scriptHome"; pwd`"
cd $scriptHome/../;
projectHome=$scriptHome/../

mvn clean install 
