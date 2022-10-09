load('./utils.js');
let rsConf = getReplicaSetConfig('rs-shard-01');
rs.initiate(rsConf);