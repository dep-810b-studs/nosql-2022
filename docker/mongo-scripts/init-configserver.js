load('/scripts/utils.js');
let rsConf = getReplicaSetConfig('rs-config-server', number = false,
    congigsvr = true, memberPrefix = 'configsvr');
rs.initiate(rsConf);