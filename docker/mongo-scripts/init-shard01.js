load('/scripts/utils.js');
let rsConf = getReplicaSetConfig('rs-shard-01', number = 1);
rs.initiate(rsConf);