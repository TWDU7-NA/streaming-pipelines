#!/usr/bin/env bash
set -e
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PRODUCER_DIR="${DIR}/../CitibikeApiProducer/"
CONSUMER_DIR="${DIR}/../StationConsumer"
RAW_DATA_SAVER_DIR="${DIR}/../RawDataSaver"
function sbtBuildAndTest() {
  cd $1
  sbt test
  sbt package
}
echo "====Building Producer JARs===="
$PRODUCER_DIR/gradlew test -p $PRODUCER_DIR
$PRODUCER_DIR/gradlew -p $PRODUCER_DIR clean bootJar
sbtBuildAndTest $CONSUMER_DIR
sbtBuildAndTest $RAW_DATA_SAVER_DIR

