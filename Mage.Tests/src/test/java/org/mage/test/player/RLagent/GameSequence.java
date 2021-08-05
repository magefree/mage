package org.mage.test.player.RLagent;
import java.util.*;
import mage.players.Player;
import org.apache.log4j.Logger;

public class GameSequence {
    public List<Experience> experiences;
    private String winner;
    private static final Logger logger = Logger.getLogger(GameSequence.class);
    GameSequence(){
        experiences= new ArrayList<Experience>();
        winner="";
        
    }
    public void setWinner(String win){
        winner=win;
    }
    public void addExperience(Experience exp){
        experiences.add(exp);
    }
    public int getValue(Player player){ 
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
