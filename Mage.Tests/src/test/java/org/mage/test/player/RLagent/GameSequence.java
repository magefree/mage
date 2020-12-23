package org.mage.test.player.RLagent;
import java.util.*;
import mage.players.Player;
import org.apache.log4j.Logger;

public class GameSequence {
    public List<Experience> experiences;
    private static final Logger logger = Logger.getLogger(GameSequence.class);
    protected int value;
    public GameSequence(){
        experiences= new ArrayList<Experience>();
    }
    public void setWinner(Player player,String winner){
        value=setValue(player, winner);
    }
    public void addExperience(Experience exp){
        experiences.add(exp);
    }
    public int getValue(){
        return value;
    }
    protected int setValue(Player player,String winner){ 
        String winLine="Player "+player.getName()+" is the winner";
        if(winner.equals(winLine)){
            return 1;
        }
        else if(winner.equals("Game is a draw")){
            return 0;
        }
        else{
            return -1;
        }
    }
}
