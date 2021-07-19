package mage.player.ai.RLAgent;
import mage.game.Game;
import mage.game.GameState;
import mage.players.Player;
import java.util.*;
import org.apache.log4j.Logger;
import mage.player.ai.RLAction;
import mage.player.ai.RLPlayer;
import ai.djl.*;
import ai.djl.nn.*;
import ai.djl.nn.core.*;
import ai.djl.ndarray.*;
import ai.djl.ndarray.types.*;
import ai.djl.ndarray.index.*;

public class DJLAgent{
    public Representer representer;
    PyConnection conn;
    private static final Logger logger = Logger.getLogger(DJLAgent.class);
    List<RepresentedState> experience;
    NDManager nd;
    Model net;
    public DJLAgent(){
        representer=new Representer();
        experience=new ArrayList<RepresentedState>();
        nd=NDManager.newBaseManager();
        net=Model.newInstance("Main net");

    }

    public int choose(Game game, RLPlayer player, List<RLAction> actions){
        RepresentedState state=representer.represent(game, player, actions);
        List<RepresentedState> stateSingle=new ArrayList<RepresentedState>();
        stateSingle.add(state);
        NDList netInput=prepare(stateSingle);//Pack into a list
        int choice=run(net,netInput);
        state.chosenAction=choice;
        player.addExperience(state);
        return choice;
    }
    NDArray listToNDArray2D(List<List<Integer>> data,int nestedSize){
        NDArray arr=nd.zeros(new Shape(data.size(),nestedSize),DataType.INT32);
        for(int i=0;i<data.size();i++){
            if(data.get(i).size()!=nestedSize){
                throw new IllegalStateException("Wrong size provided for array creation");
            }
            for(int j=0;j<nestedSize;j++){
                arr.setScalar(new NDIndex(i,j), data.get(i).get(j));
            }
        }
        return arr;
    }
    //Requires all input arrays the same shape in second dimention
    //Returns the arrays stacked and a mask where the stacked 
    //values are 
    List<NDArray> paddedStack(List<NDArray> inputs,int nestedSize){
        long maxSize=0;
        for(int i=0;i<inputs.size();i++){
            long arrSize=inputs.get(i).getShape().get(0);
            if(arrSize>maxSize) maxSize=arrSize;
        }
        NDArray data=nd.zeros(new Shape(inputs.size(),maxSize,nestedSize),DataType.INT32);
        NDArray mask=nd.zeros(new Shape(inputs.size(),maxSize));
        for(int i=0;i<inputs.size();i++){
            NDIndex setLoc=new NDIndex(i+",0:"+inputs.get(i).getShape().get(0));
            data.set(setLoc, inputs.get(i));
            for(int j=0;j<inputs.get(i).getShape().get(0);j++){
                mask.set(new NDIndex(i,j),1);
            }
        }
        List<NDArray> result=new ArrayList<NDArray>();
        result.add(data);
        result.add(mask);
        return result;
    }
    NDList prepare(List<RepresentedState> states){
        List<NDArray> unstackedActions=new ArrayList<NDArray>();
        List<NDArray> unstackedPermanents=new ArrayList<NDArray>();
        NDArray gameInts=nd.create(new Shape(states.size(),HParams.numGameInts),DataType.INT32);
        for(int i=0;i<states.size();i++){
            unstackedActions.add(listToNDArray2D(states.get(i).actions,HParams.actionParts));
            unstackedPermanents.add(listToNDArray2D(states.get(i).permanents,HParams.numPermParts));
            List<Integer> stateGameInts=states.get(i).gameInts;
            for(int j=0;j<HParams.numGameInts;j++){
                gameInts.setScalar(new NDIndex(i,j), stateGameInts.get(j));
            }
        }
        List<NDArray> actions=paddedStack(unstackedActions, HParams.actionParts);
        List<NDArray> permanents=paddedStack(unstackedActions, HParams.actionParts);
        NDList result=new NDList();
        result.addAll(actions);
        result.addAll(permanents);
        result.add(gameInts);
        return result;
    }
}

