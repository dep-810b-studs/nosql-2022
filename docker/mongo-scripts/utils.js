const shard01Nodes = [
    "rs-shard-01/shard01-a:27017",
    "rs-shard-01/shard01-b:27017",
    "rs-shard-01/shard01-c:27017",
];

const shard02Nodes = [
    "rs-shard-02/shard02-a:27017",
    "rs-shard-02/shard02-b:27017",
    "rs-shard-02/shard02-c:27017",
];

const shard03Nodes = [
    "rs-shard-03/shard03-a:27017",
    "rs-shard-03/shard03-b:27017",
    "rs-shard-03/shard03-c:27017",
];

const allShards = [
    ...shard01Nodes,
    ...shard02Nodes,
    ...shard03Nodes,
];

getReplicaSetConfig = (id, members, configsvr = false) => {
    return {
        _id: id,
        configsvr: configsvr,
        version: 1,
        members: members
    };;
};