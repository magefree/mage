package org.mage.test.player.RLagent;

import mage.abilities.*;
import mage.MageObject;
import mage.abilities.common.PassAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.cards.Card;
import mage.cards.Cards;
import mage.choices.Choice;
import mage.constants.Outcome;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.player.ai.ComputerPlayer;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetAmount;
import mage.target.TargetCard;
import mage.util.RandomUtil;
import mage.players.PlayerList;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;

import java.io.Serializable;
import java.util.*;
import org.apache.log4j.Logger;
import org.hamcrest.core.IsInstanceOf;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.api.buffer.DataType;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.EmbeddingSequenceLayer;
import org.deeplearning4j.nn.conf.layers.GlobalPoolingLayer;
import org.deeplearning4j.nn.conf.layers.LocallyConnected1D;
import org.deeplearning4j.nn.conf.layers.Convolution1D;
import org.nd4j.linalg.learning.config.Sgd;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.PoolingType;
import org.deeplearning4j.nn.conf.layers.Upsampling1D;
import org.deeplearning4j.nn.conf.layers.misc.RepeatVector;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.distribution.UniformDistribution;
import org.deeplearning4j.nn.conf.graph.ElementWiseVertex;
import org.deeplearning4j.nn.conf.graph.ReshapeVertex;
import org.deeplearning4j.nn.conf.graph.ElementWiseVertex.Op;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.nd4j.evaluation.classification.Evaluation;
import org.slf4j.LoggerFactory;
import mage.game.permanent.Battlefield;
import org.deeplearning4j.nn.conf.layers.ActivationLayer;
import org.deeplearning4j.optimize.listeners.CollectScoresIterationListener;
/**
 * @author Elchanan Haas
 */


//Main learner 
//Takes in a game state and a list of RLAction and can choose an action
//Records its experiences and can learn from them 
public class RLLearner {
    private static final Logger logger = Logger.getLogger(RLLearner.class);
    protected LinkedList<GameSequence> games;
    public CollectScoresIterationListener losses;
    protected double epsilon=.5f; //For epsilon greedy learning. Set to 0 for evaluation
    protected Boolean evaluateMode; //Sets mode to testing, no experience should be collected  
    Representer representer;
    ComputationGraph model;
    //Constructor--creates a RLLearner
    public RLLearner(){
        games=new LinkedList<GameSequence>(); 
        model=constructModel();
        representer=new Representer();
        evaluateMode=false;
    }
    //Sets evalueate mode. When evaluate mode is 
    //True, no experience is collected
    public void setEvaluateMode(Boolean isEvaluateMode){
        evaluateMode=isEvaluateMode;
    }
    //Epsilon controlls the tradeoff between random
    //and greedy actions, lower values being more greedy
    //Prerequisites: epsilon in [0_1]
    public void setEpsilon(float eps){
        epsilon=eps;
    }
    //Creates a new GameSequence and adds it to the list
    //to later learn from. This game will be returned from
    //getCurrentGame 
    public void newGame(Player player){
        games.add(new GameSequence());
    }
    //Decides which games to sample experiences from, the exact
    //experience is chosen later
    protected List<Integer> sampleGames(int size){
        List<Integer> sizes=new ArrayList<Integer>();
        int totalSize=0;
        for(GameSequence game:games){
            int gameSize=game.experiences.size();
            sizes.add(gameSize);
            totalSize+=gameSize;
        }
        List<Integer> gamesToSample=new ArrayList<Integer>();
        for(int i=0;i<size;i++){
            int expIndex=RandomUtil.nextInt(totalSize);
            int count=0;
            while(expIndex>=sizes.get(count)){
                expIndex-=sizes.get(count);
                count+=1;
            }
            gamesToSample.add(count);
        }
        return gamesToSample;
    }
    //Represents a batch of data as a list of INDArrays
    //Remember--this simply doens't represent NULL elements
    //Only getTargets can currently handle NULL elements
    protected List<INDArray> represent_batch(List<RepresentedGame> reprGames){
        List<List<INDArray>> prePiled=new ArrayList<List<INDArray>>();
        for(int i=0;i<HParams.total_model_inputs;i++){
            prePiled.add(new ArrayList<INDArray>());
        }
        for(int i=0;i<reprGames.size();i++){
            INDArray[] repr=reprGames.get(i).asModelInputs();
            for(int j=0;j<HParams.total_model_inputs;j++){
                prePiled.get(j).add(repr[j]);
            }
        }
        List<INDArray> batchRepr=new ArrayList<INDArray>();
        for(int i=0;i<prePiled.size();i++){
            for(int j=0;j<prePiled.get(i).size();j++){
                String s1=prePiled.get(i).get(j).shapeInfoToString();
                String s2=prePiled.get(i).get(0).shapeInfoToString();
                if(!s1.equals(s2)){
                    logger.info(i+"\n"+s1+"\n"+s2+"\n---\n");
                }
            }
        }
        for(int i=0;i<prePiled.size();i++){
            
            INDArray piled=Nd4j.pile(prePiled.get(i));
            INDArray dataCol=Nd4j.squeeze(piled,1);
            batchRepr.add(dataCol);
        }
        return batchRepr;
    } 
    //Runs the model on given inputs
    protected INDArray runModel(List<RepresentedGame> reprGames){
        List<INDArray> representedExps=represent_batch(reprGames);
        Map<String,INDArray> qmodelOut=model.feedForward(representedExps.toArray(new INDArray[representedExps.size()]),true);
        INDArray QValues=qmodelOut.get("linout").reshape(-1,HParams.max_representable_actions);
        return QValues;
    }
    //Calculates the targets of the ML model for the actions taken
    protected List<Double> getTargets(List<GameSequence> sampledGames,List<RepresentedGame> reprGames){
        INDArray QValues=runModel(reprGames);
        INDArray maxQ=QValues.max(1);
        List<Double> targets=new ArrayList<Double>();
        for(int i=0;i<reprGames.size();i++){
            if(!reprGames.get(i).isDummy){
                targets.add(maxQ.getDouble(i));
            }
            else{
                //logger.info("adding game win/loss "+sampledGames.get(i).getValue());
                targets.add((double) sampledGames.get(i).getValue());
            }
        }
        return targets;
    }
    //Trains the ML model on a batch of data of the given size
    //Past the end of the game, the next state is represented as NULL
    public void trainBatch(int size){
        List<Integer> gamesToSample=sampleGames(size);
        List<GameSequence> sampledGames=new ArrayList<GameSequence>();
        List<Experience> currentExps=new ArrayList<Experience>();
        List<RepresentedGame> currentReprs=new ArrayList<RepresentedGame>();
        List<RepresentedGame> nextReprs=new ArrayList<RepresentedGame>();
        for(int i=0;i<size;i++){
            GameSequence sampledGame=games.get(gamesToSample.get(i));
            sampledGames.add(sampledGame);
            int expIndex=RandomUtil.nextInt(sampledGame.experiences.size());
            Experience exp=sampledGame.experiences.get(expIndex);
            currentExps.add(exp);
            currentReprs.add(exp.repr);
            if(expIndex+1 != sampledGame.experiences.size()){
                nextReprs.add(sampledGame.experiences.get(expIndex+1).repr);
            }
            else{
                nextReprs.add(representer.emptyGame());
            }
        }
        INDArray target=runModel(currentReprs);
        List<Double> targets=getTargets(sampledGames, nextReprs);
        //logger.info(targets.toString());
        for(int i=0;i<size;i++){
            target.putScalar(i,currentExps.get(i).chosen,targets.get(i));
        }
        
        target=Nd4j.expandDims(target, 1);
        List<INDArray> representedExps=represent_batch(currentReprs);
        INDArray[] inputs=representedExps.toArray(new INDArray[representedExps.size()]);
        model.fit(inputs, new INDArray[]{target});
    } 
    //Gets the action the model thinks is best in this game state
    protected int getGreedy(RepresentedGame repr){
        INDArray inputs[]=repr.asModelInputs();
        Map<String,INDArray> qmodelOut=model.feedForward(inputs,true);
        INDArray q_values=qmodelOut.get("linout").reshape(HParams.max_representable_actions);
        double max_q=Double.NEGATIVE_INFINITY;
        int max_index=0;
        for(int i=0;i<Math.min(repr.numActions,HParams.max_representable_actions);i++){
            double value=q_values.getDouble(i);
            if(value>max_q){
                max_q=value;
                max_index=i;
            }
        }
        return max_index;
    }
    //Rund the epsilon greedy algorithm
    public int choose(Game game, Player player,List<RLAction> actions){
        int choice;
        
        /*if(1<2){
        return RandomUtil.nextInt(actions.size());
        }*/
        RepresentedGame repr=representer.represent(game, player, actions);
        if(RandomUtil.nextDouble()<epsilon){//random action
            choice=RandomUtil.nextInt(Math.min(actions.size(),HParams.max_representable_actions));
        }
        else{//Greedy action from q learner
            choice=getGreedy(repr);
        }
        Experience exp=new Experience(repr,choice);
        getCurrentGame().addExperience(exp);
        return choice;
    }
    //Constructs the model. Should only be run once in constructor,
    //because it sets the training listener
    ComputationGraph constructModel(){
        ComputationGraphConfiguration conf = new NeuralNetConfiguration.Builder()
        .updater(new Adam(0.005))
        .graphBuilder()
        .addInputs("actionIDs","otherReal","permanentIDs") //can use any label for this        
        .addLayer("embedPermanent", new EmbeddingSequenceLayer.Builder().nIn(HParams.max_represents).nOut(HParams.internal_dim).inputLength(HParams.max_representable_permanents).build(),"permanentIDs")
        .addLayer("poolPermanent", new GlobalPoolingLayer.Builder(PoolingType.AVG).build(),"embedPermanent")
        .addLayer("combinedGame", new DenseLayer.Builder().nIn(HParams.game_reals+HParams.internal_dim).nOut(HParams.internal_dim).build(), "poolPermanent","otherReal")
        .addLayer("actGame1",new ActivationLayer.Builder().activation(Activation.RELU).build(),"combinedGame")
        .addVertex("repeatGame", new ReshapeVertex(-1,HParams.internal_dim,1), "actGame1")
        .addLayer("embedAction",new EmbeddingSequenceLayer.Builder().nIn(HParams.max_represents).nOut(HParams.internal_dim/HParams.input_seqlen).inputLength(HParams.model_inputlen).build(),"actionIDs")
        .addVertex("refold", new ReshapeVertex(-1,HParams.internal_dim,HParams.max_representable_actions), "embedAction") 
        .addLayer("actlin1", new Convolution1D.Builder().nIn(HParams.internal_dim).nOut(HParams.internal_dim).kernelSize(1).build(), "refold")
        .addVertex("combined", new ElementWiseVertex(Op.Product), "actlin1","repeatGame")
        .addLayer("lastlin", new Convolution1D.Builder().nIn(HParams.internal_dim).nOut(1).kernelSize(1).activation(Activation.TANH).build(), "combined")
        .addLayer("linout",new RnnOutputLayer.Builder(LossFunctions.LossFunction.MSE).activation(Activation.IDENTITY).nIn(1).nOut(1).build(),"lastlin")
        .setOutputs("linout")    //We need to specify the network outputs and their order
        .build();

    ComputationGraph net = new ComputationGraph(conf);
    losses=new CollectScoresIterationListener(1);
    net.setListeners(losses);
    net.init();
    return net;
    }
    //Ends the current game, no more experiences should be 
    //added after this
    public void endGame(Player player,String winner){
        getCurrentGame().setWinner(player,winner);
    }
    public GameSequence getCurrentGame(){
        return games.getLast();
    }

    
    
}
