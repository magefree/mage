package org.mage.test.player.RLagent;

import mage.abilities.*;
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

import java.io.Serializable;
import java.util.*;
import org.apache.log4j.Logger;
import org.hamcrest.core.IsInstanceOf;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.api.buffer.DataType;

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
    final int max_represents=512; //Probably need to increase later
    final int input_seqs=2;

    final int no_attack=1;
    final int no_block=2;
    int current_represent=3;
    HashMap<Ability,Integer> abilityIndexs;
    HashMap<Permanent,Integer> attackIndexs;
    HashMap<Permanent,Integer> blockAgainstIndexs;
    HashMap<Permanent,Integer> blockIndexs;
    public RLLearner(){
        games=new LinkedList<GameSequence>(); 
        abilityIndexs=new HashMap<Ability,Integer>();
        attackIndexs=new HashMap<Permanent,Integer>();
        blockAgainstIndexs=new HashMap<Permanent,Integer>();
        blockIndexs=new HashMap<Permanent,Integer>();
    }
    public void newGame(){
        games.add(new GameSequence());
    }
    public int choose(Game game, List<RLAction> actions){
        int choice=RandomUtil.nextInt(actions.size());
        Experience exp=new Experience(game.copy(),actions,choice);
        getCurrentGame().addExperience(exp);
        return choice;
    }
    public void endGame(String winner){
        getCurrentGame().setWinner(winner);
    }
    public GameSequence getCurrentGame(){
        return games.getLast();
    }
    public INDArray representActions(List<RLAction> actions){
        List<INDArray> represented=new ArrayList<INDArray>();
        for(int i=0;i<actions.size();i++){
            represented.add(representAction(actions.get(i)));
        }
        INDArray result=Nd4j.pile(represented);
        return result;
    }

    public INDArray representAction(RLAction action){
        int[] embeds={0,0};
        if(action instanceof ActionAbility){
            Ability ability=((ActionAbility) action).ability;
            Integer ret=abilityIndexs.putIfAbsent(ability, current_represent);
            if(ret!=null) current_represent++;
            embeds[0]=abilityIndexs.get(ability);
        }
        else if(action instanceof ActionAttack){
            ActionAttack attack=((ActionAttack) action);
            Permanent attacker=attack.perm;
            if(attack.isAttack){
                Integer ret=attackIndexs.putIfAbsent(attacker, current_represent);
                if(ret!=null) current_represent++;
                embeds[0]=attackIndexs.get(attacker);
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
                Integer ret=blockAgainstIndexs.putIfAbsent(attacker, current_represent);
                if(ret!=null) current_represent++;
                embeds[0]=blockAgainstIndexs.get(attacker);
                ret=blockIndexs.putIfAbsent(blocker, current_represent);
                if(ret!=null) current_represent++;
                embeds[0]=blockIndexs.get(blocker);
            }else{
                embeds[0]=no_block;
            }
        }else{
            throw new java.lang.UnsupportedOperationException("Unable to represent action type yet");
        }
        return Nd4j.createFromArray(embeds);
    }
}
