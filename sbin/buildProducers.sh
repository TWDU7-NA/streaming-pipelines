TRAINING_COHORT="twdu7-na"
ssh ingester.${TRAINING_COHORT}.training <<EOF
set -e

function kill_process {
    query=\$1
    pid=`ps aux | grep \$query | grep -v "grep" |  awk "{print \\\$2}"`

    if [ -z "\$pid" ];
    then
        echo "no \${query} process running"
    else
        kill -9 \$pid
    fi
}

station_information="station-information"
station_status="station-status"
station_san_francisco="station-san-francisco"
station_fr_ms="station-france-marseille"


echo "====Kill running producers===="

kill_process \${station_san_francisco}
kill_process \${station_fr_ms}

echo "====Runing Producers Killed===="

echo "====Deploy Producers===="
nohup java -jar /tmp/tw-citibike-apis-producer0.1.0.jar --spring.profiles.active=\${station_san_francisco} --producer.topic=station_data_sf --kafka.brokers=kafka.${TRAINING_COHORT}.training:9092 1>/tmp/\${station_san_francisco}.log 2>/tmp/\${station_san_francisco}.error.log &
nohup java -jar /tmp/tw-citibike-apis-producer0.1.0.jar --spring.profiles.active=\${station_fr_ms} --kafka.brokers=kafka.${TRAINING_COHORT}.training:9092 1>/tmp/\${station_fr_ms}.log 2>/tmp/\${station_fr_ms}.error.log &

echo "====Producers Deployed===="
EOF


