load('/scripts/utils.js');
let rsConf = getReplicaSetConfig('rs-shard-01', shard01Nodes);
rs.initiate(rsConf);