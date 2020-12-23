package org.mage.test.player.RLagent;

import mage.abilities.*;
import mage.MageObject;
import mage.abilities.common.PassAbility;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import java.util.*;
import org.apache.log4j.Logger;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import mage.game.permanent.Battlefield;
import org.nd4j.linalg.api.buffer.DataType;

public class Representer {
    HashMap<String,Integer> nameToIndex;
    int current_represent=3;
    private static final Logger logger = Logger.getLogger(Representer.class);
    public Representer(){
        nameToIndex=new HashMap<String,Integer>();
    }
    //Gets the name of an object
    protected String nameObject(MageObject obj){
        return obj.getName(); 
    }

    public RepresentedGame represent(Game game,Player player, List<RLAction> actions){
        List<INDArray> gameRepr=representGame(game, player);
        List<INDArray> actionRepr=representActions(game, actions);
        return new RepresentedGame(actions.size(),gameRepr,actionRepr);
    }

    public RepresentedGame emptyGame(){
        INDArray[] gameZeros=new INDArray[HParams.num_game_reprs];
        gameZeros[0]=Nd4j.zeros(DataType.FLOAT,HParams.game_reals);
        gameZeros[1]=Nd4j.zeros(DataType.INT32,HParams.max_representable_permanents);
        List<INDArray> gameRepr=Arrays.asList(gameZeros);
        List<INDArray> actionRepr=Arrays.asList(Nd4j.zeros(DataType.INT32,HParams.model_inputlen));
        return new RepresentedGame(0,gameRepr,actionRepr,true);
    }
    //Takes the string representation of a permanent or
    //an action and maps it to it's ID. If it is not in 
    //the list yet, it assigns a new ID
    protected int getActionID(String actionString){
        Integer ret=nameToIndex.putIfAbsent(actionString, current_represent);
        if(ret==null) {current_represent++;
            if(current_represent>=HParams.max_represents){
                throw new IllegalStateException("The maximum number of representable object in"
                +"the machine learning model has been exceeded. increase HParams.max_represents");
            }
        }
        ret=nameToIndex.get(actionString);
        return ret;
    }
        //Represents the actions in preperation of being
    //fed into the model
    protected List<INDArray> representActions(Game game,List<RLAction> actions){
        List<INDArray> represented=new ArrayList<INDArray>();
        for(int i=0;i<HParams.max_representable_actions;i++){
            if(i<actions.size()){
                represented.add(representAction(game, actions.get(i)));
            }
            else{
                represented.add(Nd4j.createFromArray(new int[HParams.input_seqlen]));
            }
        }
        INDArray packed=Nd4j.pile(represented);
        INDArray flat=packed.reshape(HParams.model_inputlen);
        //logger.info("flat shape is "+flat.shapeInfoToString());
        ArrayList<INDArray> ret=new ArrayList<INDArray>();
        ret.add(flat);
        return ret;
    }


    //extracts relevent decimal quanitites from a player
    protected float[] playerToArray(Player player){
        float[] data=new float[HParams.game_reals/2];
        data[0]=player.getLife()/20f;
        data[1]=player.getHand().size()/7f;
        return data;
    }
    //represents a game state by extracting relevent information
    //First value is real numbers, second is embedding IDs (ints)
    protected List<INDArray> representGame(Game game,Player LPlayer){
        UUID learnerId=LPlayer.getId(); 
        UUID opponentId=game.getOpponents(learnerId).iterator().next();
        Player OPlayer=game.getPlayer(opponentId);
        Battlefield field=game.getBattlefield();
        float[] gameReals=new float[HParams.game_reals];
        System.arraycopy(playerToArray(LPlayer), 0, gameReals, 0, HParams.game_reals/2);
        System.arraycopy(playerToArray(OPlayer), 0, gameReals, HParams.game_reals/2, HParams.game_reals/2);
        int[] embeds=new int[HParams.max_representable_permanents];
        Iterator<Permanent> perms=field.getAllPermanents().iterator();
        for(int i=0;i<HParams.max_representable_permanents;i++){
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
    //represents a single action.
    protected INDArray representAction(Game game, RLAction action){
        int[] embeds=new int[HParams.input_seqlen];
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
                embeds[0]=HParams.no_attack;
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
                embeds[0]=HParams.no_block;
                embeds[1]=HParams.no_block;
            }
        }else{
            throw new java.lang.UnsupportedOperationException("Unable to represent action type yet");
        }
        return Nd4j.createFromArray(embeds);
    }
}
