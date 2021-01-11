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
import com.google.gson.*;
import java.io.*;
import com.google.gson.Gson; 
import com.google.gson.GsonBuilder; 
import com.google.gson.TypeAdapter; 
import com.google.gson.stream.JsonReader; 
import com.google.gson.stream.JsonToken; 
import com.google.gson.stream.JsonWriter;  

class INDArrayAdapter extends TypeAdapter<INDArray>{
    @Override 
    //Non need to deserialize on the java side
    public INDArray read(JsonReader reader) throws IOException { 
        System.out.println("deserialization of INDArrays not supported");
        System.exit(-1);
        return Nd4j.zeros(0);
    } 
    
    @Override 
    public void write(JsonWriter writer, INDArray array) throws IOException { 
        writer.jsonValue(array.toString());
    } 
}
public class RepresentedGame implements Serializable{
    protected int numActions;
    protected List<INDArray> actionRepr;
    protected List<INDArray> gameRepr;
    protected boolean isDone;
    protected float reward;
    RepresentedGame(int numActions, List<INDArray> gameRepr,List<INDArray> actionRepr,float reward){
        this(numActions, gameRepr,actionRepr,reward,false);
    }
    RepresentedGame(int numActions, List<INDArray> gameRepr,List<INDArray> actionRepr,float reward, boolean isDone){
        this.numActions=numActions;
        this.gameRepr=gameRepr;
        this.actionRepr=actionRepr;
        this.isDone=isDone;
        this.reward=reward;
    }
    String asJsonString(){
        GsonBuilder builder = new GsonBuilder(); 
        builder.registerTypeAdapter(INDArray.class, new INDArrayAdapter()); 
        Gson gson = builder.create(); 
        return gson.toJson(this);
    }
    //returns the data prepared to be fed into the model
    public INDArray[] asModelInputs(){
        HParams hparams=new HParams();
        INDArray inputs[]=new INDArray[hparams.total_model_inputs];
        inputs[0]=Nd4j.expandDims(actionRepr.get(0), 0);
        for(int i=0;i<gameRepr.size();i++){
            inputs[i+1]=Nd4j.expandDims(gameRepr.get(i), 0);
        }
        return inputs;
    }
    public int getNumActions(){
        return numActions;
    }
}
