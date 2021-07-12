package mage.player.ai.RLAgent;

import java.io.Serializable;


//Static members are not used so it can 
//be serialized for sending to Python
public class HParams implements Serializable {
    public HParams(){

    }
    public static final int maxRepresents=512;

}
