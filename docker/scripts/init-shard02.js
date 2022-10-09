load('./utils.js');
let rsConf = getReplicaSetConfig('rs-shard-02');
rs.initiate(rsConf);