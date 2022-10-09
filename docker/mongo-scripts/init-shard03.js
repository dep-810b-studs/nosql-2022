load('/scripts/utils.js');
let rsConf = getReplicaSetConfig('rs-shard-03', 3);
rs.initiate(rsConf);