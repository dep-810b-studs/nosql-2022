load('/scripts/utils.js');
let rsConf = getReplicaSetConfig('rs-shard-02', shard02Nodes);
rs.initiate(rsConf);