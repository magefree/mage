package org.mage.test.player;

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
import java.util.stream.Collectors;
/**
 * uses a reinforcement learning based AI
 *
 * @author Elchanan Haas
 */

public class RLPlayer extends RandomPlayer{
    public RLLearner learner;
    private static final Logger logger = Logger.getLogger(RLPlayer.class);
    public RLPlayer(String name,RLLearner inLearner) {  
        super(name);
        learner=inLearner;
    }
    public RLPlayer(final RLPlayer player) {
        super(player);   
    }
    private Ability chooseAbility(Game game, List<Ability> options){
        Ability ability=pass;
        if (!options.isEmpty()) {
            if (options.size() == 1) { //Similar 
                ability = options.get(0);
            } else {
                List<RLAction> toUse=new ArrayList<RLAction>();
                for(int i=0;i<options.size();i++){
                    toUse.add((RLAction) new ActionAbility(options.get(i)));
                }
                int choice=learner.choose(game,toUse);
                ActionAbility chosenAction=(ActionAbility) toUse.get(choice);
                ability = chosenAction.ability;
            }
        }
        return ability;
    }

    @Override
    protected Ability getAction(Game game) {
        //logger.info("Getting action");
        List<ActivatedAbility> playables = getPlayableAbilities(game); //already contains pass
        List<Ability> castPlayables=playables.stream().map(element->(Ability) element).collect(Collectors.toList());
        Ability ability;
        ability=chooseAbility(game, castPlayables);
        List<Ability> options = getPlayableOptions(ability, game);
        if (!options.isEmpty()) {
            ability=chooseAbility(game, options);
        }
        if (!ability.getManaCosts().getVariableCosts().isEmpty()) {//leave random for now-variable spells, AI can wait
            int amount = getAvailableManaProducers(game).size() - ability.getManaCosts().convertedManaCost();
            if (amount > 0) {
                ability = ability.copy();
                ability.getManaCostsToPay().add(new GenericManaCost(RandomUtil.nextInt(amount)));
            }
        }
        return ability;
    }
    @Override
    public void selectAttackers(Game game, UUID attackingPlayerId) { //Recorded by AI now!
        //logger.info("life total of " + getName() +" is "+getLife());
        UUID defenderId = game.getOpponents(playerId).iterator().next();
        List<Permanent> attackersList = super.getAvailableAttackers(defenderId, game);
        for(int i=0;i<attackersList.size();i++){
            Permanent attacker=attackersList.get(i);
            List<RLAction> toattack= Arrays.asList((RLAction) new ActionAttack(attacker,false),(RLAction) new ActionAttack(attacker,true));
            int index=learner.choose(game,toattack);
            if(index==1){//chose to attack
                setStoredBookmark(game.bookmarkState()); // makes it possible to UNDO a declared attacker with costs from e.g. Propaganda
                if (!game.getCombat().declareAttacker(attacker.getId(), defenderId, playerId, game)) {
                    game.undo(playerId);
                }
            }
        }
        actionCount++;
    }

    @Override
    public void selectBlockers(Ability source,Game game, UUID defendingPlayerId) {
        //logger.info("selcting blockers");
        int numGroups = game.getCombat().getGroups().size();
        if (numGroups == 0) {
            return;
        }

        List<Permanent> blockers = getAvailableBlockers(game);
        for (Permanent blocker : blockers) {
            List<RLAction> toblock=new ArrayList<RLAction>();
            List<CombatGroup> groups=game.getCombat().getGroups();
            for(int i=0;i<numGroups;i++){
                UUID attacker=groups.get(i).getAttackers().get(0);
                toblock.add((RLAction) new ActionBlock(game.getPermanent(attacker),blocker,true));
            }
            toblock.add((RLAction) new ActionBlock(null,null,false)); // Don't block anything
            int choice=learner.choose(game,toblock);
            ActionBlock chosenAction=(ActionBlock) toblock.get(choice);

            if (chosenAction.isBlock) {
                CombatGroup group = groups.get(choice);
                if (!group.getAttackers().isEmpty()) {
                    this.declareBlocker(this.getId(), blocker.getId(), group.getAttackers().get(0), game);
                }
            }
        }
        actionCount++;
    }
}
