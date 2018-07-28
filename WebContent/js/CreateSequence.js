/**
 * 
 */

function getValueForNextSequence(sequenceOfName){

    var sequenceDoc = db.counter.findAndModify({
       query:{_id: sequenceOfName },
       update: {$inc:{sequence_value:1}},
       new:true
    });

    return sequenceDoc.sequence_value;
 }