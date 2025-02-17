#!/bin/bash

set -e

$hadoop_path fs -fs hdfs://$hdfs_server -mkdir -p /tw/rawData/stationDataSF/checkpoints \
&& $hadoop_path fs -fs hdfs://$hdfs_server -mkdir -p /tw/rawData/stationDataSF/data \
&& $hadoop_path fs -fs hdfs://$hdfs_server -mkdir -p /tw/rawData/stationDataFR_MS/checkpoints \
&& $hadoop_path fs -fs hdfs://$hdfs_server -mkdir -p /tw/rawData/stationDataFR_MS/data \
&& $hadoop_path fs -fs hdfs://$hdfs_server -mkdir -p /tw/rawData/stationDataNYC/checkpoints \
&& $hadoop_path fs -fs hdfs://$hdfs_server -mkdir -p /tw/rawData/stationDataNYC/data \
&& $hadoop_path fs -fs hdfs://$hdfs_server -mkdir -p /tw/stationMart/checkpoints \
&& $hadoop_path fs -fs hdfs://$hdfs_server -mkdir -p /tw/stationMart/data
