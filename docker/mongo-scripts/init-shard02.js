load('/scripts/utils.js');
let rsConf = getReplicaSetConfig('rs-shard-02', 2);
rs.initiate(rsConf);