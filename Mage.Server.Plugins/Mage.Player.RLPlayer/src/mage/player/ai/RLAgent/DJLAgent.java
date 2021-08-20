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
import java.util.concurrent.ThreadLocalRandom;   
import java.nio.file.*;
import java.io.*;


public class DJLAgent extends Agent implements Serializable{
    public Representer representer;
    private static final Logger logger = Logger.getLogger(DJLAgent.class);
    List<RepresentedState> experience;
    transient NDManager baseND;
    transient Policy policy;//This will be loaded using its own load methods
    transient Critic critic;//This too
    public transient boolean runMode=false;
    public DJLAgent(){
        representer=new Representer();
        experience=new ArrayList<RepresentedState>();
        baseND=NDManager.newBaseManager();
        policy=new Policy();
        critic=new Critic();
    }
    public void save(int iter) throws IOException{
        String home=System.getProperty("user.home");
        Path path = Paths.get(home,"xmage-models");
        save(path.toString(),iter);
    }
    public void save(String path,int iter) throws IOException{
        Files.createDirectories(Paths.get(path));
        FileOutputStream fileOut =new FileOutputStream(path+File.separator+"representer.bin");
         ObjectOutputStream out = new ObjectOutputStream(fileOut);
         out.writeObject(this);
         out.close();
         fileOut.close();
        critic.save(path,iter);
        policy.save(path,iter);
    }
    public void loadNets(String path){
        baseND=NDManager.newBaseManager();
        policy=new Policy();
        critic=new Critic();
        policy.load(path,"policy-net");
        critic.load(path,"critic-net");
    }
    public void trainIfReady(){
        if(experience.size()>HParams.expForTrain){
            train();
        }
    } 
    void train(){
        try(NDManager nd=baseND.newSubManager();){
            NDList netInput=prepare(nd,experience);
            for(int i=0;i<netInput.size();i++){
                netInput.get(i).setRequiresGradient(true);
            }
            NDArray baseLogProbs=policy.logProbs(netInput);
            int numExp=experience.size();
            float[] arrRewards=new float[numExp];
            float[] arrRewardScale=new float[numExp];
            int[] arrChosenActs=new int[numExp];
            for(int i=0;i<experience.size();i++){
                arrRewards[i]=experience.get(i).reward;
                arrChosenActs[i]=experience.get(i).chosenAction;
                arrRewardScale[i]=experience.get(i).rewardScale;
            }
            NDArray rewards=nd.create(arrRewards, new Shape(numExp,1));
            NDArray rewardScale=nd.create(arrRewardScale, new Shape(numExp,1));
            int actionSize=(int) netInput.get(0).getShape().get(1);
            NDArray actsTaken=nd.create(arrChosenActs, new Shape(numExp)).oneHot(actionSize);
            NDArray predReward=critic.predict(netInput);
            NDList label=new NDList(actsTaken,predReward,rewards,baseLogProbs,rewardScale);
            /*for(int i=0;i<label.size();i++){
                label.get(i).setRequiresGradient(true);
            }*/
            policy.train(nd.newSubManager(),netInput,label);
            critic.train(nd.newSubManager(),netInput,new NDList(rewards));
            experience=new ArrayList<RepresentedState>();
        }
    }
    public int choose(Game game, RLPlayer player, List<RLAction> actions){
        RepresentedState state=representer.represent(game, player, actions);
        List<RepresentedState> stateSingle=new ArrayList<RepresentedState>();
        stateSingle.add(state);
        try(NDManager nd=baseND.newSubManager();){
            NDList netInput=prepare(nd,stateSingle);
            NDArray logProbs=policy.logProbs(netInput);
            int choice=sample(logProbs);
            if(choice >=actions.size()){
                throw new RuntimeException("Choice bigger than array");
            }
            state.chosenAction=choice;
            player.addExperience(state);
            return choice;
        }
    }
    public void addExperiences(List<RepresentedState> exp){
        experience.addAll(exp);
    }
    int sample(NDArray logProbs){
        NDArray probs=logProbs.exp();
        probs=probs.reshape(-1);
        probs=probs.div(probs.sum()); //This extra normalization is needed to ensure that 
        //The true sum is less than 1+1e-12, or it will crash. Rounding error ensures that 
        //happens without the extra normalization

        //the builtin randomMultinomial is broken, so I am writing my own implementation of it
        float target=ThreadLocalRandom.current().nextFloat();
        float origTarget=target;
        int i=0;
        while(target>probs.getFloat(i)){
            target-=probs.getFloat(i);
            i++;
        }
        return i;
    }
    //These series of functions are returining java arrays becuase there is an 
    //overhead on each DJL operation so setting values 1 by 1 in the array
    //becomes a bottleneck according to a profiler
    int[][] listToArray2D(List<List<Integer>> data,int nestedSize){
        int[][] res=new int[data.size()][nestedSize];
        for(int i=0;i<data.size();i++){
            if(data.get(i).size()!=nestedSize){
                throw new IllegalStateException("Wrong size provided for array creation");
            }
            for(int j=0;j<nestedSize;j++){
                res[i][j]=data.get(i).get(j);
            }
        }
        return res;
    }
    //Requires all input arrays the same shape in second dimention
    //Returns the arrays stacked and a mask where the stacked 
    //values are 
    List<NDArray> paddedStack(NDManager nd,List<int[][]> inputs,int nestedSize){
        int maxSize=0;
        for(int i=0;i<inputs.size();i++){
            int arrSize=inputs.get(i).length;
            if(arrSize>maxSize) maxSize=arrSize;
        }
        int[][] data=new int[inputs.size()*maxSize][nestedSize];//Must be 2D to allow for transformation
        //into NDarray becuase DJL can't create an array from a 3D inputs 
        int[][] mask=new int[inputs.size()][maxSize];
        for(int i=0;i<inputs.size();i++){
            int[][] slice=inputs.get(i);
            if(slice.length>maxSize) {
                throw new RuntimeException("too big slice");
            }
            for(int j=0;j<slice.length;j++){
                if(slice[j].length!=nestedSize){
                    throw new RuntimeException("wrong nested size");
                } 
                for(int k=0;k<nestedSize;k++){
                    data[i*maxSize+j][k]=slice[j][k];
                }
                mask[i][j]=1;
            }
        }
        NDArray NDdata;
        if(maxSize>0){
            NDdata=nd.create(data);
        }else{
            NDdata=nd.zeros(new Shape(0));//will be reshaped to right size later
        }

        NDdata=NDdata.reshape(inputs.size(),maxSize,nestedSize);
        NDArray NDmask=nd.create(mask);
        return new NDList(NDdata,NDmask);
    }
    NDList prepare(NDManager nd,List<RepresentedState> states){
        List<int[][]> unstackedActions=new ArrayList<int[][]>();
        List<int[][]> unstackedPermanents=new ArrayList<int[][]>();
        int[][] gameInts=new int[states.size()][HParams.numGameInts]; 
        for(int i=0;i<states.size();i++){
            unstackedActions.add(listToArray2D(states.get(i).actions,HParams.actionParts));
            unstackedPermanents.add(listToArray2D(states.get(i).permanents,HParams.numPermParts));
            for(int j=0;j<HParams.numGameInts;j++){
                gameInts[i][j]=states.get(i).gameInts.get(j);
            }
        }
        List<NDArray> actions=paddedStack(nd,unstackedActions, HParams.actionParts);
        List<NDArray> permanents=paddedStack(nd,unstackedPermanents, HParams.numPermParts);
        NDArray NDGameInts=nd.create(gameInts);
        NDList result=new NDList();
        result.addAll(actions);
        result.addAll(permanents);
        result.add(NDGameInts);
        return result;
    }
}

