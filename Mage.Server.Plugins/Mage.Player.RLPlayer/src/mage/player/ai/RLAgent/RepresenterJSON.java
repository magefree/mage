package mage.player.ai.RLAgent;
import mage.abilities.*;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.PassAbility;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.io.Serializable;
import java.util.*;

import com.google.gson.JsonObject;

import org.apache.log4j.Logger;
import mage.game.permanent.Battlefield;
import mage.game.combat.CombatGroup;
import mage.game.combat.Combat;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.JSONArray;

public class RepresenterJSON implements Serializable{
    private static final Logger logger = Logger.getLogger(RepresenterJSON.class);
    public RepresenterJSON(){
    }
    //Gets the name of an object
    protected String nameObject(MageObject obj){
        return obj.getName(); 
    }
    
    public JSONObject represent(Game game,Player player, List<RLAction> actions){
        JSONObject gameRepr=representGame(game, player);
        JSONArray actionRepr=representActions(game, actions);
        JSONObject wholeState= new JSONObject();
        boolean isDone=game.checkIfGameIsOver();
        wholeState.put("game", gameRepr);
        wholeState.put("actions",actionRepr);
        wholeState.put("isDone",isDone);
        wholeState.put("winReward",getWinReward(game, player));
        return wholeState;
    }

    protected float getWinReward(Game game,Player LPlayer){
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
        return 0;
    }

        //Represents the actions in preperation of being
    //fed into the model
    protected JSONArray representActions(Game game,List<RLAction> actions){
        JSONArray list = new JSONArray();
        for(int i=0;i<actions.size();i++){
            list.add(representAction(game, actions.get(i)));
        }
        return list;
    }


    //extracts relevent decimal quanitites from a player
    protected JSONObject playerToArray(Player player){
        JSONObject playerData=new JSONObject();
        playerData.put("life",player.getLife());
        playerData.put("cards in hand",player.getHand().size());
        return playerData;
    }
    protected Player getOpponent(Game game,Player LPlayer){
        UUID learnerId=LPlayer.getId(); 
        UUID opponentId=game.getOpponents(learnerId).iterator().next();
        Player OPlayer=game.getPlayer(opponentId);
        return OPlayer;
    }
    //represents a game state by extracting relevent information
    //First value is real numbers, second is embedding IDs (ints)
    protected JSONObject getGameReals(Game game, Player LPlayer,Player OPlayer){
        JSONObject gameReals=new JSONObject();
        gameReals.put("agent",playerToArray(LPlayer));
        gameReals.put("opponent",playerToArray(OPlayer));
        gameReals.put("turns",game.getTurnNum());
        return gameReals;
    }
    protected JSONObject representGame(Game game,Player LPlayer){
        JSONObject gameRepr=new JSONObject();
        JSONArray namedPerms = new JSONArray();
        Player OPlayer=getOpponent(game, LPlayer);
        UUID learnerId=LPlayer.getId(); 
        UUID opponentId=OPlayer.getId();
        Battlefield field=game.getBattlefield();
        Iterator<Permanent> perms=field.getAllPermanents().iterator();
        while(perms.hasNext()){
            Permanent perm=perms.next();
            String controllerName;
            if(perm.getControllerId().equals(learnerId)){
                controllerName="Agent";
            }else if(perm.getControllerId().equals(opponentId)){
                controllerName="Opponent";
            }else{
                throw new IllegalStateException("Unable to determine Permenants owner");
            }
            JSONObject repr=new JSONObject();
            repr.put("controller",controllerName);
            repr.put("name",nameObject(perm));
            Combat combat=game.getCombat();
            if(Objects.nonNull(combat)){
                if(combat.getAttackers().contains(perm.getId())){
                    repr.put("combat","attacker");
                }
                else if(combat.getBlockers().contains(perm.getId())){
                    repr.put("combat","blocker");
                }
                else{
                    repr.put("combat","none");
                }
            }
            repr.put("power",perm.getPower().getValue());
            repr.put("toughness",perm.getToughness().getValue());
            namedPerms.add(repr);
        }
        gameRepr.put("permanents",namedPerms);
        gameRepr.put("reals",getGameReals(game, LPlayer, OPlayer));
        return gameRepr;
    }
    //represents a single action.
    protected JSONObject representAction(Game game, RLAction action){
        JSONObject actRepr=new JSONObject();
        if(action instanceof ActionAbility){
            String abilityName=action.getText();
            actRepr.put("action",abilityName);
        }
        else if(action instanceof ActionAttack){
            ActionAttack attack=((ActionAttack) action);
            if(attack.isAttack){
                String abilityName=action.getText();
                actRepr.put("action",abilityName);
            }
            else{
                actRepr.put("action","NoAttack");
            }
        }
        else if (action instanceof ActionBlock){
            ActionBlock actBlock=(ActionBlock) action;
            if(actBlock.isBlock){
                Permanent attacker=actBlock.attacker;
                Permanent blocker=actBlock.blocker;
                String NameBlockAttacker="BlockAttacker:"+nameObject(attacker);
                String NameBlocker="Blocker:"+nameObject(blocker);
                actRepr.put("attacker",NameBlockAttacker);
                actRepr.put("blocker",NameBlocker);
            }else{
                actRepr.put("action","NoBlock");
            }
        }
        else if(action instanceof ActionMulligan){
            ActionMulligan actMull=(ActionMulligan) action;
            if(actMull.isMulligan){
                actRepr.put("action","yesMulligan");
            }else{
                actRepr.put("action","noMulligan");
            }
        }
        else{
            throw new java.lang.UnsupportedOperationException("Unable to represent action type yet");
        }
        return actRepr;
    }
}
