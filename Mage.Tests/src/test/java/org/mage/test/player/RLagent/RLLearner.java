package org.mage.test.player.RLagent;

import mage.abilities.*;
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

import java.io.Serializable;
import java.util.*;
import org.apache.log4j.Logger;
import org.mage.test.player.RLagent.*;

/**
 * @author Elchanan Haas
 */


//Main learner 
//Takes in a game state and a list of RLAction and can choose an action
//Records its experiences and can learn from them 
public class RLLearner {
    LinkedList<GameSequence> games;//keep an eye on the size of this array, if it gets out 
    //of hand, the game representation will need to be compressed, which could be tricky
    //There is almost certainly some unused information to cut, but that would require memory profiling
    //and adding a prune method to Game. 
    public RLLearner(){
        games=new LinkedList<GameSequence>(); 
    }
    public void newGame(){
        games.add(new GameSequence());
    }
    public int choose(Game game, List<RLAction> actions){
        int choice=RandomUtil.nextInt(actions.size());
        Experience exp=new Experience(game.copy(),actions,choice);
        getCurrent().addExperience(exp);
        return choice;
    }
    public void endGame(String winner){
        getCurrent().setWinner(winner);
    }
    public GameSequence getCurrent(){
        return games.getLast();
    }
}
