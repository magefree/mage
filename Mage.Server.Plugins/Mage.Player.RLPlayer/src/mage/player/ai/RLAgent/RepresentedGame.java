package mage.player.ai.RLAgent;

import org.nd4j.linalg.api.ndarray.INDArray;
import mage.abilities.*;
import mage.MageObject;
import mage.abilities.common.PassAbility;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.io.Serializable;
import java.util.*;
import org.apache.log4j.Logger;
import org.nd4j.linalg.factory.Nd4j;
import mage.game.permanent.Battlefield;

public class RepresentedGame implements Serializable{
    protected int numActions;
    protected List<INDArray> actionRepr;
    protected List<INDArray> gameRepr;
    protected boolean isDummy;
    RepresentedGame(int numActions, List<INDArray> gameRepr,List<INDArray> actionRepr){
        this(numActions, gameRepr,actionRepr,false);
    }
    RepresentedGame(int numActions, List<INDArray> gameRepr,List<INDArray> actionRepr,boolean isDummy){
        this.numActions=numActions;
        this.gameRepr=gameRepr;
        this.actionRepr=actionRepr;
        this.isDummy=isDummy;
    }

    //returns the data prepared to be fed into the model
    public INDArray[] asModelInputs(){
        INDArray inputs[]=new INDArray[HParams.total_model_inputs];
        inputs[0]=Nd4j.expandDims(actionRepr.get(0), 0);
        inputs[1]=Nd4j.expandDims(gameRepr.get(0), 0);
        inputs[2]=Nd4j.expandDims(gameRepr.get(1), 0);
        return inputs;
    }
    public int getNumActions(){
        return numActions;
    }
}
