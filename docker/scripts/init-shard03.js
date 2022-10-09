load('./utils.js');
let rsConf = getReplicaSetConfig('rs-shard-03');
rs.initiate(rsConf);