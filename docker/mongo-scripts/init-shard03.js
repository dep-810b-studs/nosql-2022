load('/scripts/utils.js');
let rsConf = getReplicaSetConfig('rs-shard-03', shard03Nodes);
rs.initiate(rsConf);