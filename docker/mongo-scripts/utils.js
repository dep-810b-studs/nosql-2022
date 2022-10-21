const shard01Nodes = [
    "shard01-a:27017",
    "shard01-b:27017",
    "shard01-c:27017",
];

const shard02Nodes = [
    "shard02-a:27017",
    "shard02-b:27017",
    "shard02-c:27017",
];

const shard03Nodes = [
    "shard03-a:27017",
    "shard03-b:27017",
    "shard03-c:27017",
];

const allShards = [
    ...shard01Nodes.map(node => `rs-shard-01/${node}`),
    ...shard02Nodes.map(node => `rs-shard-02/${node}`),
    ...shard03Nodes.map(node => `rs-shard-03/${node}`),
];


getReplicaSetConfig = (id, rsMembers, configsvr = false) => {

    let indexedMembers = rsMembers.map((member, index) => {
        return {
            _id: index,
            host: member,
        }
    });

    return {
        _id: id,
        configsvr: configsvr,
        version: 1,
        members: indexedMembers,
    };;
};