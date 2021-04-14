package mage.player.ai.RLAgent;
import mage.abilities.*;
import mage.MageObject;
import mage.abilities.common.PassAbility;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.io.Serializable;
import java.util.*;
import org.apache.log4j.Logger;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import mage.game.permanent.Battlefield;
import org.nd4j.linalg.api.buffer.DataType;
import mage.game.combat.CombatGroup;
import mage.game.combat.Combat;
public class Representer implements Serializable{
    HashMap<String,Integer> nameToIndex;
    int current_represent;
    HParams hparams;
    private static final Logger logger = Logger.getLogger(Representer.class);
    public Representer(){
        nameToIndex=new HashMap<String,Integer>();
        hparams=new HParams();
        current_represent=hparams.counter_start;
    }
    //Gets the name of an object
    protected String nameObject(MageObject obj){
        return obj.getName(); 
    }
    public String indexToName(int index){
        for (Map.Entry<String,Integer> e : nameToIndex.entrySet()) {
            String key = e.getKey();
            Integer value = e.getValue();
            if(value==index){
                return key;
            }
        }
        return "";
    }

    
    public RepresentedGame represent(Game game,Player player, List<RLAction> actions){
        List<INDArray> gameRepr=representGame(game, player);
        List<INDArray> actionRepr=representActions(game, actions);
        float reward=getReward(game, player);
        boolean isDone=game.checkIfGameIsOver();
        return new RepresentedGame(actions.size(),gameRepr,actionRepr,reward,isDone);
    }

    public RepresentedGame emptyGame(){
        INDArray[] gameZeros=new INDArray[hparams.num_game_reprs];
        gameZeros[0]=Nd4j.zeros(DataType.FLOAT,hparams.game_reals);
        gameZeros[1]=Nd4j.zeros(DataType.INT32,hparams.max_representable_permanents);
        gameZeros[2]=Nd4j.zeros(DataType.FLOAT,hparams.perm_features,hparams.max_representable_permanents);
        List<INDArray> gameRepr=Arrays.asList(gameZeros);
        List<INDArray> actionRepr=Arrays.asList(Nd4j.zeros(DataType.INT32,hparams.model_inputlen));
        return new RepresentedGame(0,gameRepr,actionRepr,0.0f,true);
    }
    protected float getReward(Game game,Player LPlayer){
        Player OPlayer=getOpponent(game, LPlayer);
        boolean isOver=game.checkIfGameIsOver();
        if(isOver){
            String winner=game.getWinner();
            String winLine="Player "+LPlayer.getName()+" is the winner";
            if(winner.equals(winLine)){
                return 1.0f;
            }
            else if(winner.equals("Game is a draw")){
                return 0.0f;
            }
            else{
                return -1.0f;
            }
        }
        return (LPlayer.getLife()-OPlayer.getLife())/(1000.0f);
    }
    //Takes the string representation of a permanent or
    //an action and maps it to it's ID. If it is not in 
    //the list yet, it assigns a new ID
    protected int getActionID(String actionString){
        Integer ret=nameToIndex.putIfAbsent(actionString, current_represent);
        if(ret==null) {current_represent++;
            if(current_represent>=hparams.max_represents){
                throw new IllegalStateException("The maximum number of representable object in"
                +"the machine learning model has been exceeded. increase hparams.max_represents");
            }
        }
        ret=nameToIndex.get(actionString);
        return ret;
    }
        //Represents the actions in preperation of being
    //fed into the model
    protected List<INDArray> representActions(Game game,List<RLAction> actions){
        List<INDArray> represented=new ArrayList<INDArray>();
        for(int i=0;i<hparams.max_representable_actions;i++){
            if(i<actions.size()){
                represented.add(representAction(game, actions.get(i)));
            }
            else{
                represented.add(Nd4j.createFromArray(new int[hparams.input_seqlen]));
            }
        }
        INDArray packed=Nd4j.pile(represented);
        //INDArray flat=packed.reshape(hparams.model_inputlen);
        //logger.info("flat shape is "+flat.shapeInfoToString());
        ArrayList<INDArray> ret=new ArrayList<INDArray>();
        ret.add(packed);
        return ret;
    }


    //extracts relevent decimal quanitites from a player
    protected float[] playerToArray(Player player){
        float[] data=new float[hparams.player_reals];
        data[0]=player.getLife()/20f;
        data[1]=player.getHand().size()/7f;
        return data;
    }
    protected Player getOpponent(Game game,Player LPlayer){
        UUID learnerId=LPlayer.getId(); 
        UUID opponentId=game.getOpponents(learnerId).iterator().next();
        Player OPlayer=game.getPlayer(opponentId);
        return OPlayer;
    }
    //represents a game state by extracting relevent information
    //First value is real numbers, second is embedding IDs (ints)
    protected float[] getGameReals(Game game, Player LPlayer,Player OPlayer){
        float[] gameReals=new float[hparams.game_reals];
        System.arraycopy(playerToArray(LPlayer), 0, gameReals, 0,hparams.player_reals);
        System.arraycopy(playerToArray(OPlayer), 0, gameReals, hparams.player_reals, hparams.player_reals);
        int nextIndex=2*hparams.player_reals;
        gameReals[nextIndex]=game.getTurnNum()/20.0f;
        return gameReals;
    }
    protected List<INDArray> representGame(Game game,Player LPlayer){
        Player OPlayer=getOpponent(game, LPlayer);
        UUID learnerId=LPlayer.getId(); 
        UUID opponentId=OPlayer.getId();
        Battlefield field=game.getBattlefield();
        int[] embeds=new int[hparams.max_representable_permanents];
        float[][] extra_perm_info=new float[hparams.max_representable_permanents][hparams.perm_features];
        Iterator<Permanent> perms=field.getAllPermanents().iterator();
        for(int i=0;i<hparams.max_representable_permanents;i++){
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
                Combat combat=game.getCombat();
                if(Objects.nonNull(combat)){
                    if(combat.getAttackers().contains(perm.getId())){
                        extra_perm_info[i][0]=1;
                    }
                    if(combat.getBlockers().contains(perm.getId())){
                        extra_perm_info[i][1]=1;
                    }
                }
                extra_perm_info[i][2]=perm.getPower().getValue()/5.0f;
                extra_perm_info[i][3]=perm.getToughness().getValue()/5.0f;
            }
        }
        INDArray realList=Nd4j.createFromArray(getGameReals(game, LPlayer, OPlayer));
        INDArray embedList=Nd4j.createFromArray(embeds);
        ArrayList<INDArray> ret=new ArrayList<INDArray>();
        ret.add(realList);
        ret.add(embedList);
        ret.add(Nd4j.createFromArray(extra_perm_info).transpose());
        return ret;
    }
    //represents a single action.
    protected INDArray representAction(Game game, RLAction action){
        int[] embeds=new int[hparams.input_seqlen];
        if(action instanceof ActionAbility){
            String abilityName=action.getText();
            embeds[0]=getActionID(abilityName);
        }
        else if(action instanceof ActionAttack){
            ActionAttack attack=((ActionAttack) action);
            if(attack.isAttack){
                String attackName=action.getText();
                embeds[0]=getActionID(attackName);
            }
            else{
                embeds[0]=hparams.no_attack;
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
                embeds[0]=hparams.no_block;
                embeds[1]=hparams.no_block;
            }
        }
        else if(action instanceof ActionMulligan){
            ActionMulligan actMull=(ActionMulligan) action;
            if(actMull.isMulligan){
                embeds[0]=hparams.yes_mulligan;
            }else{
                embeds[0]=hparams.no_mulligan;
            }
        }
        else{
            throw new java.lang.UnsupportedOperationException("Unable to represent action type yet");
        }
        return Nd4j.createFromArray(embeds);
    }
}
