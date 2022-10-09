load('./utils.js');
let rsConf = getReplicaSetConfig('rs-config-server',
    congigsvr = true, memberPrefix = 'configsvr');
rs.initiate(rsConf);