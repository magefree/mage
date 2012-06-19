/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 * 
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 * 
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 * 
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.player.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.PassAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.game.Game;
import mage.game.permanent.Permanent;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MCTSPlayer extends ComputerPlayer<MCTSPlayer> {

     private final static transient Logger logger = Logger.getLogger(MCTSPlayer.class);

    protected PassAbility pass = new PassAbility();

    private NextAction nextAction;

    public enum NextAction {
        PRIORITY, SELECT_ATTACKERS, SELECT_BLOCKERS;
    }

    public MCTSPlayer(UUID id) {
        super(id);
        this.pass.setControllerId(id);
    }

    public MCTSPlayer(final MCTSPlayer player) {
        super(player);
        this.pass = player.pass.copy();
        this.nextAction = player.nextAction;
    }

    @Override
    public MCTSPlayer copy() {
        return new MCTSPlayer(this);
    }

    protected List<Ability> getPlayableAbilities(Game game) {
        List<Ability> playables = getPlayable(game, true);
        playables.add(pass);
        return playables;
    }

    public List<Ability> getPlayableOptions(Game game) {
        List<Ability> all = new ArrayList<Ability>();
        List<Ability> playables = getPlayableAbilities(game);
        for (Ability ability: playables) {
            List<Ability> options = game.getPlayer(playerId).getPlayableOptions(ability, game);
            if (options.isEmpty()) {
                if (ability.getManaCosts().getVariableCosts().size() > 0) {
                    simulateVariableCosts(ability, all, game);
                }
                else {
                    all.add(ability);
                }
            }
            else {
                for (Ability option: options) {
                    if (ability.getManaCosts().getVariableCosts().size() > 0) {
                        simulateVariableCosts(option, all, game);
                    }
                    else {
                        all.add(option);
                    }
                }
            }
        }
        return all;
    }

    protected void simulateVariableCosts(Ability ability, List<Ability> options, Game game) {
        int numAvailable = getAvailableManaProducers(game).size() - ability.getManaCosts().convertedManaCost();
        int start = 0;
        if (!(ability instanceof SpellAbility)) {
            //only use x=0 on spell abilities
            if (numAvailable == 0)
                return;
            else
                start = 1;
        }
        for (int i = start; i < numAvailable; i++) {
            Ability newAbility = ability.copy();
            newAbility.getManaCostsToPay().add(new GenericManaCost(i));
            options.add(newAbility);
        }
    }

    public List<List<UUID>> getAttacks(Game game) {
        List<List<UUID>> engagements = new ArrayList<List<UUID>>();
        List<Permanent> attackersList = super.getAvailableAttackers(game);
        //use binary digits to calculate powerset of attackers
        int powerElements = (int) Math.pow(2, attackersList.size());
        StringBuilder binary = new StringBuilder();
        for (int i = powerElements - 1; i >= 0; i--) {
            binary.setLength(0);
            binary.append(Integer.toBinaryString(i));
            while (binary.length() < attackersList.size()) {
                binary.insert(0, "0");
            }
            List<UUID> engagement = new ArrayList<UUID>();
            for (int j = 0; j < attackersList.size(); j++) {
                if (binary.charAt(j) == '1')
                    engagement.add(attackersList.get(j).getId());
            }
            engagements.add(engagement);
        }
        return engagements;
    }

    public List<List<List<UUID>>> getBlocks(Game game) {
        List<List<List<UUID>>> engagements = new ArrayList<List<List<UUID>>>();
        int numGroups = game.getCombat().getGroups().size();
        if (numGroups == 0) return engagements;

        //add a node with no blockers
        List<List<UUID>> engagement = new ArrayList<List<UUID>>();
        for (int i = 0; i < numGroups; i++) {
            engagement.add(new ArrayList<UUID>());
        }
        engagements.add(engagement);

        List<Permanent> blockers = getAvailableBlockers(game);
        addBlocker(game, engagement, blockers, engagements);

        return engagements;
    }

    private List<List<UUID>> copyEngagement(List<List<UUID>> engagement) {
        List<List<UUID>> newEngagement = new ArrayList<List<UUID>>();
        for (List<UUID> group: engagement) {
            newEngagement.add(new ArrayList<UUID>(group));
        }
        return newEngagement;
    }

    protected void addBlocker(Game game, List<List<UUID>> engagement, List<Permanent> blockers, List<List<List<UUID>>> engagements) {
        if (blockers.isEmpty())
            return;
        int numGroups = game.getCombat().getGroups().size();
        //try to block each attacker with each potential blocker
        Permanent blocker = blockers.get(0);
//        if (logger.isDebugEnabled())
//            logger.debug("simulating -- block:" + blocker);
        List<Permanent> remaining = remove(blockers, blocker);
        for (int i = 0; i < numGroups; i++) {
            if (game.getCombat().getGroups().get(i).canBlock(blocker, game)) {
                List<List<UUID>>newEngagement = copyEngagement(engagement);
                newEngagement.get(i).add(blocker.getId());
                engagements.add(newEngagement);
//                    logger.debug("simulating -- found redundant block combination");
                addBlocker(game, newEngagement, remaining, engagements);  // and recurse minus the used blocker
            }
        }
        addBlocker(game, engagement, remaining, engagements);
    }

    public NextAction getNextAction() {
        return nextAction;
    }

    public void setNextAction(NextAction action) {
        this.nextAction = action;
    }

    @Override
    public boolean priority(Game game) {
//        logger.info("Paused for Priority for player:" + getName());
        game.pause();
        nextAction = NextAction.PRIORITY;
        return false;
    }

//    @Override
//    public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game) {
//        game.end();
//    }
//
//    @Override
//    public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game, Map<String, Serializable> options) {
//        game.end();
//    }
//
//    @Override
//    public boolean choose(Outcome outcome, Cards cards, TargetCard target, Game game) {
//        game.end();
//    }
//
//    @Override
//    public boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game) {
//        game.end();
//    }
//
//    @Override
//    public boolean chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
//        game.end();
//    }
//
//    @Override
//    public boolean chooseTargetAmount(Outcome outcome, TargetAmount target, Ability source, Game game) {
//        game.end();
//    }
//
//    @Override
//    public boolean chooseMulligan(Game game) {
//        game.end();
//    }
//
//    @Override
//    public boolean chooseUse(Outcome outcome, String message, Game game) {
//        game.pause();
//        nextAction = NextAction.CHOOSE_USE;
//        return false;
//    }
//
//    @Override
//    public boolean choose(Outcome outcome, Choice choice, Game game) {
//        game.end();
//    }
//
//    @Override
//    public int chooseEffect(List<ReplacementEffect> rEffects, Game game) {
//        game.end();
//    }
//
//    @Override
//    public TriggeredAbility chooseTriggeredAbility(TriggeredAbilities abilities, Game game) {
//        game.end();
//    }
//
//    @Override
//    public Mode chooseMode(Modes modes, Ability source, Game game) {
//        game.end();
//    }

    @Override
    public void selectAttackers(Game game) {
        game.pause();
        nextAction = NextAction.SELECT_ATTACKERS;
    }

    @Override
    public void selectBlockers(Game game) {
        game.pause();
        nextAction = NextAction.SELECT_BLOCKERS;
    }

//    @Override
//    public UUID chooseAttackerOrder(List<Permanent> attacker, Game game) {
//        game.end();
//    }
//
//    @Override
//    public UUID chooseBlockerOrder(List<Permanent> blockers, Game game) {
//        game.end();
//    }
//
//    @Override
//    public void assignDamage(int damage, List<UUID> targets, String singleTargetName, UUID sourceId, Game game) {
//        game.end();
//    }
//
//    @Override
//    public int getAmount(int min, int max, String message, Game game) {
//        game.end();
//    }

}
