#!/bin/bash

echo "==========Running mongo cluster=========="

docker compose -f docker-compose.mongo.yml up -d

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