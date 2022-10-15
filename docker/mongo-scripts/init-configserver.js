load('/scripts/utils.js');

let members = [
    "configsvr01:27017",
    "configsvr02:27017",
    "configsvr03:27017",
];

let rsConf = getReplicaSetConfig('rs-config-server', members, congigsvr = true);
rs.initiate(rsConf);