package mage.player.ai.RLAgent;

import java.io.Serializable;


//Static members are not used so it can 
//be serialized for sending to Python
public class HParams implements Serializable {
    public HParams(){

    }
    public static final int maxRepresents=512;
    public static final int actionParts=2;
    public static final int numGameInts=6;
    public static final int numPermParts=4;
    public static final int embedDim=32;
    public static final int batchSize=32;
    public static final int expForTrain=1;

}
