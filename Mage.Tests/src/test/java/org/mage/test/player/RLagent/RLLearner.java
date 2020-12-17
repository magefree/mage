package org.mage.test.player.RLagent;

import mage.abilities.*;
import mage.MageObject;
import mage.abilities.common.PassAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.p.PermeatingMass;
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
import org.deeplearning4j.nn.conf.graph.ReshapeVertex;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.nd4j.evaluation.classification.Evaluation;
import org.slf4j.LoggerFactory;
import mage.game.permanent.Battlefield;
import org.deeplearning4j.nn.conf.layers.ActivationLayer;
import org.deeplearning4j.nn.conf.layers.misc.RepeatVector;
/**
 * @author Elchanan Haas
 */


//Main learner 
//Takes in a game state and a list of RLAction and can choose an action
//Records its experiences and can learn from them 
public class RLLearner {
    private static final Logger logger = Logger.getLogger(RLLearner.class);
    protected LinkedList<GameSequence> games;//keep an eye on the size of this array, if it gets out 
    //of hand, the game representation will need to be compressed, which could be tricky
    //There is almost certainly some unused information to cut, but that would require memory profiling
    //and adding a prune method to Game. 
    final int max_represents=256; //Probably need to increase later
    final int input_seqlen=2;
    final int no_attack=1;
    final int no_block=2;
    final int max_representable_actions=8;
    final int max_representable_permanents=32; 
    final int model_inputlen=max_representable_actions*input_seqlen;
    final int internal_dim=32; //For speed for now, should be good enough
    final int game_reals=4;
    protected double epsilon=.5f; //For epsilon greedy learning
    protected Boolean evaluateMode; //Sets mode to testing, no experience should be collected  
    int current_represent=3;
    public HashMap<String,Integer> actionToIndex;
    ComputationGraph model;
    public RLLearner(){
        games=new LinkedList<GameSequence>(); 
        actionToIndex=new HashMap<String,Integer>();
        model=constructModel();
        evaluateMode=false;
    }
    public void setEvaluateMode(Boolean isEvaluateMode){
        evaluateMode=isEvaluateMode;
    }
    public void setEpsilon(float eps){
        epsilon=eps;
    }
    public void newGame(Player player){
        games.add(new GameSequence(player));
    }
    protected int getGreedy(Game game, List<RLAction> actions){
        INDArray actionRepr=representActions(game,actions);
        actionRepr=Nd4j.expandDims(actionRepr, 0);
        Map<String,INDArray> qmodelOut=model.feedForward(actionRepr,true);
        INDArray q_values=qmodelOut.get("linout").reshape(max_representable_actions);
        double max_q=Double.NEGATIVE_INFINITY;
        int max_index=0;
        for(int i=0;i<Math.min(actions.size(),max_representable_actions);i++){
            double value=q_values.getDouble(i);
            if(value>max_q){
                max_q=value;
                max_index=i;
            }
        }
        return max_index;
    }
    public int choose(Game game, List<RLAction> actions){
        //logger.info(q_values);
        //logger.info(actionRepr);
        int choice;
        if(RandomUtil.nextDouble()<epsilon){//random action
            choice=RandomUtil.nextInt(actions.size());
        }
        else{//Greedy action from q learner
            choice=getGreedy(game, actions);
        }
        Experience exp=new Experience(game.copy(),actions,choice);
        getCurrentGame().addExperience(exp);
        return choice;
    }
    ComputationGraph constructModel(){
        ComputationGraphConfiguration conf = new NeuralNetConfiguration.Builder()
        .updater(new Sgd(0.01))
        .graphBuilder()
        .addInputs("actionIDs","otherReal","permanentIDs") //can use any label for this        
        .addLayer("embedPermanent", new EmbeddingSequenceLayer.Builder().nIn(max_represents).nOut(internal_dim).inputLength(max_representable_permanents).build(),"permanentIDs")
        .addLayer("poolPermanent", new GlobalPoolingLayer.Builder(PoolingType.AVG).build(),"embedPermanent")
        .addLayer("combinedGame", new DenseLayer.Builder().nIn(game_reals+internal_dim).nOut(internal_dim).build(), "poolPermanent","otherReal")
        .addLayer("actGame1",new ActivationLayer.Builder().activation(Activation.RELU).build(),"combinedGame")
        .addLayer("repeatGame", new Upsampling1D.Builder().size(max_representable_actions).build(), "actGame1")
        .addLayer("embedAction",new EmbeddingSequenceLayer.Builder().nIn(max_represents).nOut(internal_dim/input_seqlen).inputLength(model_inputlen).build(),"actionIDs")
        .addVertex("refold", new ReshapeVertex(-1,internal_dim,max_representable_actions), "embedAction") 
        .addLayer("actlin1", new Convolution1D.Builder().nIn(internal_dim).nOut(internal_dim).kernelSize(1).build(), "refold")
        .addVertex("combined", new , "actlin1","repeatGame")
        .addLayer("linout", new Convolution1D.Builder().nIn(internal_dim).nOut(1).kernelSize(1).activation(Activation.TANH).build(), "lin1")
        .setOutputs("linout")    //We need to specify the network outputs and their order
        .build();
    ComputationGraph net = new ComputationGraph(conf);
    net.init();
    return net;
    }
    public void endGame(String winner){
        getCurrentGame().setWinner(winner);
    }
    public GameSequence getCurrentGame(){
        return games.getLast();
    }

    public INDArray representActions(Game game,List<RLAction> actions){
        List<INDArray> represented=new ArrayList<INDArray>();
        for(int i=0;i<max_representable_actions;i++){
            if(i<actions.size()){
                represented.add(representAction(game, actions.get(i)));
            }
            else{
                represented.add(Nd4j.createFromArray(new int[input_seqlen]));
            }
        }
        INDArray packed=Nd4j.pile(represented);
        INDArray flat=packed.reshape(model_inputlen);
        //logger.info("flat shape is "+flat.shapeInfoToString());
        return flat;
    }
    private String nameObject(MageObject obj){
        return obj.getName(); 
    }
    //Takes the string representation of a permanent or
    //an actin and maps it to it's ID. If it is not in the list yet,
    //it assigns a new one
    private int getActionID(String actionString){
        Integer ret=actionToIndex.putIfAbsent(actionString, current_represent);
        if(ret==null) {current_represent++;
            if(current_represent>=max_represents){
                throw new IllegalStateException("The maximum number of representable object in"
                +"the machine learning model has been exceeded. increase max_represents");
            }
        }
        ret=actionToIndex.get(actionString);
        return ret;
    }
    float[] playerToArray(Player player){
        float[] data=new float[game_reals/2];
        data[0]=player.getLife()/20f;
        data[1]=player.getHand().size()/7f;
        return data;
    }
    //First value is real numbers, second is embedding IDs (ints)
    public List<INDArray> representGame(Game game,Player LPlayer){
        UUID learnerId=LPlayer.getId(); 
        UUID opponentId=game.getOpponents(learnerId).iterator().next();
        Player OPlayer=game.getPlayer(opponentId);
        Battlefield field=game.getBattlefield();
        float[] gameReals=new float[game_reals];
        System.arraycopy(playerToArray(LPlayer), 0, gameReals, 0, game_reals/2);
        System.arraycopy(playerToArray(OPlayer), 0, gameReals, game_reals/2, game_reals/2);
        int[] embeds=new int[max_representable_permanents];
        Iterator<Permanent> perms=field.getAllPermanents().iterator();
        for(int i=0;i<max_representable_permanents;i++){
            if(perms.hasNext()){
                Permanent perm=perms.next();
                String controllerName;
                if(perm.getControllerId().equals(learnerId)){
                    controllerName="Learner:";
                }else if(perm.getControllerId().equals(opponentId)){
                    controllerName="Opponent:";
                }else{
                    throw new IllegalStateException("Unable to determine Permenants owner");
                }
                String permName="Permanent:"+controllerName+nameObject(perm);
                embeds[i]=getActionID(permName);
            }
        }
        INDArray realList=Nd4j.createFromArray(gameReals);
        INDArray embedList=Nd4j.createFromArray(embeds);
        ArrayList<INDArray> ret=new ArrayList<INDArray>();
        ret.add(realList);
        ret.add(embedList);
        return ret;
    }


    public INDArray representAction(Game game, RLAction action){
        int[] embeds=new int[input_seqlen];
        if(action instanceof ActionAbility){
            Ability ability=((ActionAbility) action).ability;
            MageObject source=ability.getSourceObjectIfItStillExists(game);
            String abilityName;
            if(ability instanceof PassAbility){
                abilityName="Ability:Pass";
            }
            else if(source==null){
                //logger.info("source is NULL!");
                abilityName="Ability:NULL";
                logger.info(ability.getRule());
            }
            else{
                abilityName="Ability:"+nameObject(source);
            }
            embeds[0]=getActionID(abilityName);
        }
        else if(action instanceof ActionAttack){
            ActionAttack attack=((ActionAttack) action);
            if(attack.isAttack){
                Permanent attacker=attack.perm;
                String attackName="Attacker:"+nameObject(attacker);
                embeds[0]=getActionID(attackName);
            }
            else{
                embeds[0]=no_attack;
            }
        }
        else if (action instanceof ActionBlock){
            ActionBlock actBlock=(ActionBlock) action;
            if(actBlock.isBlock){
                Permanent attacker=actBlock.attacker;
                Permanent blocker=actBlock.blocker;
                String NameBlockAttacker="BlockAttacker:"+nameObject(attacker);
                String NameBlocker="Blocker:"+nameObject(blocker);
                embeds[0]=getActionID(NameBlockAttacker);
                embeds[1]=getActionID(NameBlocker);
            }else{
                embeds[0]=no_block;
                embeds[1]=no_block;
            }
        }else{
            throw new java.lang.UnsupportedOperationException("Unable to represent action type yet");
        }
        return Nd4j.createFromArray(embeds);
    }
}
