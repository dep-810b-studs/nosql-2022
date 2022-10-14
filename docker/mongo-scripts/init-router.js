load('/scripts/utils.js');

allShards.forEach(shard => {
    sh.addShard(shard);
});