#!/bin/bash

function usage() {
  echo 'Starts new mongo instance.
Usage:
  up_mongo.sh [OPTIONS]
Options:
  -d, --distributed         Run mongo in distributed mode with 3 shards.'
  exit 1
}

function initDistributedMongo() {
    echo "==========Configuration server initialization=========="
    docker exec mongo-config-01 sh -c "mongosh < /scripts/init-configserver.js"

    echo "==========Shard №1 initialization=========="
    docker exec shard-01-node-a sh -c "mongosh < /scripts//init-shard01.js"

    echo "==========Shard №2 initialization=========="
    docker exec shard-02-node-a sh -c "mongosh < /scripts/init-shard02.js"

    echo "==========Shard №3 initialization=========="
    docker exec shard-03-node-a sh -c "mongosh < /scripts/init-shard03.js"

    sleep 20

    echo "==========Router initialization=========="
    docker exec router-01 sh -c "mongosh < /scripts/init-router.js"
}

mongo_mode='standalone'

while [[ $# -gt 0 ]]; do
  arg_lower="$(awk '{print tolower($0)}' <<<"${1}")"
  case $arg_lower in
    -d | --distributed) mongo_mode="distributed"; shift;;
    *) usage;;
  esac
  shift
done

echo "==========Running mongo in $mongo_mode mode=========="
docker compose -f docker-compose.mongo-$mongo_mode.yml up -d

if [ "$mongo_mode" = "distributed" ]; then
  initDistributedMongo
fi