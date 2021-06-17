#!/bin/sh
echo $zk_command
$zk_command rmr /tw
$zk_command create /tw ''

$zk_command create /tw/stationDataNYC ''
$zk_command create /tw/stationDataNYC/kafkaBrokers $kafka_server
$zk_command create /tw/stationDataNYC/topic station_data_nyc
$zk_command create /tw/stationDataNYC/checkpointLocation hdfs://$hdfs_server/tw/rawData/stationDataNYC/checkpoints
$zk_command create /tw/stationDataNYC/dataLocation hdfs://$hdfs_server/tw/rawData/stationDataNYC/data

$zk_command create /tw/stationDataSF ''
$zk_command create /tw/stationDataSF/kafkaBrokers $kafka_server
$zk_command create /tw/stationDataSF/topic station_data_sf
$zk_command create /tw/stationDataSF/checkpointLocation hdfs://$hdfs_server/tw/rawData/stationDataSF/checkpoints
$zk_command create /tw/stationDataSF/dataLocation hdfs://$hdfs_server/tw/rawData/stationDataSF/data

$zk_command create /tw/stationDataFR_MS ''
$zk_command create /tw/stationDataFR_MS/kafkaBrokers $kafka_server
$zk_command create /tw/stationDataFR_MS/topic station_data_fr_ms
$zk_command create /tw/stationDataFR_MS/checkpointLocation hdfs://$hdfs_server/tw/rawData/stationDataFR_MS/checkpoints
$zk_command create /tw/stationDataFR_MS/dataLocation hdfs://$hdfs_server/tw/rawData/stationDataFR_MS/data

$zk_command create /tw/output ''
$zk_command create /tw/output/checkpointLocation hdfs://$hdfs_server/tw/stationMart/checkpoints
$zk_command create /tw/output/dataLocation hdfs://$hdfs_server/tw/stationMart/data
