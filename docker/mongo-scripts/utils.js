getReplicaSetConfig = (id, number = null, configsvr = false, memberPrefix ='shard', membersCount = 3) => {
    let members = [...Array(membersCount).keys()]
        .map(index => {
            let memberPostfix = '';

            if(!configsvr){
                let aAsciiCode = 'a'.charCodeAt(0);
                let shardKey = String.fromCharCode(aAsciiCode + index);
                memberPostfix = `0${number}-${shardKey}`;
            }else{
                memberPostfix = `0${index + 1}`;
            }

            return {
                _id: index,
                host: `${memberPrefix}${memberPostfix}:27017`
            }
        });

    let rsConf = {
        _id: id,
        configsvr: configsvr,
        version: 1,
        members: members
    };

    return rsConf;
};