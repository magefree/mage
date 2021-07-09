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
    
    public JSONObject represent(Game game,Player player, JSONArray actionRepr){
        JSONObject gameRepr=representGame(game, player);
        JSONObject wholeState= new JSONObject();
        boolean isDone=game.checkIfGameIsOver();
        wholeState.put("game", gameRepr);
        wholeState.put("actions",actionRepr);
        wholeState.put("isDone",isDone);
        wholeState.put("winReward",getWinReward(game, player));
        wholeState.put("name",player.getName());
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



    //extracts relevent decimal quanitites from a player
    protected JSONObject playerToArray(Player player,int permanents){
        JSONObject playerData=new JSONObject();
        playerData.put("life",player.getLife());
        playerData.put("cards in hand",player.getHand().size());
        playerData.put("number of permanents",permanents);
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
    protected JSONObject getGameReals(Game game, Player LPlayer,Player OPlayer,int agentPerms,int opponentPerms){
        JSONObject gameReals=new JSONObject();
        gameReals.put("agent",playerToArray(LPlayer,agentPerms));
        gameReals.put("opponent",playerToArray(OPlayer,opponentPerms));
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
        int agentPerms=0;
        int opponentPerms=0;
        Iterator<Permanent> perms=field.getAllPermanents().iterator();
        while(perms.hasNext()){
            Permanent perm=perms.next();
            String controllerName;
            if(perm.getControllerId().equals(learnerId)){
                controllerName="Agent";
                agentPerms+=1;
            }else if(perm.getControllerId().equals(opponentId)){
                controllerName="Opponent";
                opponentPerms+=1;
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
        gameRepr.put("reals",getGameReals(game, LPlayer, OPlayer,agentPerms,opponentPerms));
        return gameRepr;
    }
}
