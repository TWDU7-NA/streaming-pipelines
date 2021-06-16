#!/usr/bin/env bash
set -e
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PRODUCER="${DIR}/../CitibikeApiProducer/"

echo "====Building Producer JARs===="
$PRODUCER/gradlew test -p $PRODUCER
$PRODUCER/gradlew -p $PRODUCER clean bootJar
echo "====Building Consumer JARs===="
cd $DIR/../StationConsumer
sbt test
sbt package
cd $DIR/../RawDataSaver
sbt test
sbt package

