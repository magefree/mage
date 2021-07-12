package mage.player.ai.RLAgent;
import mage.abilities.*;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.PassAbility;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.player.ai.RLAction;
import mage.players.Player;

import java.io.Serializable;
import java.util.*;

import com.google.gson.JsonObject;

import org.apache.log4j.Logger;
import mage.game.permanent.Battlefield;
import mage.game.combat.CombatGroup;
import mage.game.combat.Combat;
import mage.player.ai.RLAgent.HParams;

public class Representer implements Serializable{
    private static final Logger logger = Logger.getLogger(Representer.class);
    HashMap<String,Integer> nameToEmbedding;
    HashMap<Integer,String> EmbeddingToName;//Reverse lookup, for debugging/display purposes
    int nextVal=1;
    public Representer(){
        nameToEmbedding=new HashMap<String,Integer>();
        EmbeddingToName=new HashMap<Integer,String>();
    }
    private Integer getEmbedInt(String name){
        if (nameToEmbedding.containsKey(name)){
            return nameToEmbedding.get(name);
        }
        else{
            nameToEmbedding.put(name, nextVal);
            EmbeddingToName.put(nextVal,name);
            nextVal+=1;
            return nextVal;
        }
    }
    //Gets the name of an object
    protected String nameObject(MageObject obj){
        return obj.getName(); 
    }
    
    public RepresentedState represent(Game game,Player player, List<RLAction> actionRepr){
        List<List<Integer>> actions;
        List<List<Integer>> permanents;
        List<Integer> gameInts=getGameInts(game, player, OPlayer, numAgentPerms, numOpponentPerms);
        return new RepresentedState(actions, permanents, gameInts);
        //boolean isDone=game.checkIfGameIsOver();
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

    protected List<Permanent> getPlayerPermanents(Game game,Player player){
        List<Permanent> playerPerms=new ArrayList<Permanent>();
        Iterator<Permanent> perms=game.getBattlefield().getAllPermanents().iterator();
        while(perms.hasNext()){
            Permanent perm=perms.next();
            if(perm.getControllerId()==player.getId()){
                playerPerms.add(perm);
            }
        }
        return playerPerms;
    }

    //extracts relevent decimal quanitites from a player
    protected List<Integer> playerToArray(Game game,Player player){
        List<Integer> playerData=new ArrayList<Integer>();
        playerData.add(getEmbedInt("Life:"+player.getLife()));
        playerData.add(getEmbedInt("HandSize:"+player.getHand().size()));
        playerData.add(getEmbedInt("NumPerms:"+getPlayerPermanents(game, player).size()));
        return playerData;
    }
    protected Player getOpponent(Game game,Player LPlayer){
        UUID learnerId=LPlayer.getId(); 
        UUID opponentId=game.getOpponents(learnerId).iterator().next();
        Player OPlayer=game.getPlayer(opponentId);
        return OPlayer;
    }

    protected List<Integer> getGameInts(Game game, Player LPlayer){
        Player OPlayer=getOpponent(game, LPlayer);
        List<Integer> gameInts=new ArrayList<Integer>();
        gameInts.addAll(playerToArray(game,LPlayer));
        gameInts.addAll(playerToArray(game,OPlayer));
        return gameInts;
    }
    protected List<List<Integer>> representPerms(Game game,Player LPlayer){
        List<List<Integer>> repr=new ArrayList<List<Integer>>();
        Player OPlayer=getOpponent(game, LPlayer);
        UUID learnerId=LPlayer.getId(); 
        UUID opponentId=OPlayer.getId();
        Iterator<Permanent> perms=game.getBattlefield().getAllPermanents().iterator();
        while(perms.hasNext()){
            Permanent perm=perms.next();
            List<Integer> attributes=new ArrayList<Integer>();
            String controllerName;
            if(perm.getControllerId().equals(learnerId)){
                controllerName="Agent";
            }else if(perm.getControllerId().equals(opponentId)){
                controllerName="Opponent";
            }else{
                throw new IllegalStateException("Unable to determine Permenants owner");
            }
            attributes.add(getEmbedInt(controllerName+perm.getName()));
            Combat combat=game.getCombat();
            if(Objects.nonNull(combat)){
                if(combat.getAttackers().contains(perm.getId())){
                    attributes.add(getEmbedInt("Combat:Attacker"));
                }
                else if(combat.getBlockers().contains(perm.getId())){
                    attributes.add(getEmbedInt("Combat:Blocker"));
                }
                else{
                    attributes.add(getEmbedInt("Combat:None"));
                }
            }
            attributes.add(getEmbedInt("Power:"+perm.getPower().getValue()));
            attributes.add(getEmbedInt("Toughness:"+perm.getToughness().getValue()));
            repr.add(attributes);
        }
        return repr;
    }
    protected  representGame(Game game,Player LPlayer){
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
            repr.put("power",perm.getPower().getEmbedIntue());
            repr.put("toughness",perm.getToughness().getEmbedIntue());
            namedPerms.add(repr);
        }
        gameRepr.put("permanents",namedPerms);
        gameRepr.put("reals",getGameReals(game, LPlayer, OPlayer,agentPerms,opponentPerms));
        return gameRepr;
    }
}
