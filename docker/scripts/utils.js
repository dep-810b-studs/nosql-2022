getReplicaSetConfig = (id, configsvr = false, memberPrefix ='shard', membersCount = 3) => {
    let members = [...Array(membersCount).keys()];

    return {
        _id: id,
        configsvr: configsvr,
        version: 1,
        members: members.map(index => {
                let aAsciiCode = 'a'.charCodeAt(0);
                let shardKey = String.fromCharCode(aAsciiCode + index);

                return {
                    _id: index,
                    host: `shard0${index}-${shardKey}:27017`
                }
        })
    };
};