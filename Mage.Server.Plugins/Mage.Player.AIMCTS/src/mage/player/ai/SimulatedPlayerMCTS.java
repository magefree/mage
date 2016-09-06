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

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.Mode;
import mage.abilities.Modes;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.PassAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.cards.Card;
import mage.cards.Cards;
import mage.choices.Choice;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetAmount;
import mage.target.TargetCard;
import mage.util.RandomUtil;
import org.apache.log4j.Logger;

/**
 *
 * plays randomly
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SimulatedPlayerMCTS extends MCTSPlayer {

    private boolean isSimulatedPlayer;
    private int actionCount = 0;
    private static final Logger logger = Logger.getLogger(SimulatedPlayerMCTS.class);

    public SimulatedPlayerMCTS(UUID id, boolean isSimulatedPlayer) {
        super(id);
        this.isSimulatedPlayer = isSimulatedPlayer;
    }

    public SimulatedPlayerMCTS(final SimulatedPlayerMCTS player) {
        super(player);
        this.isSimulatedPlayer = player.isSimulatedPlayer;
    }

    @Override
    public SimulatedPlayerMCTS copy() {
        return new SimulatedPlayerMCTS(this);
    }

    public boolean isSimulatedPlayer() {
        return this.isSimulatedPlayer;
    }

    public int getActionCount() {
        return actionCount;
    }

    @Override
    public boolean priority(Game game) {
//        logger.info("priority");
        boolean didSomething = false;
        Ability ability = getAction(game);
//        logger.info("simulate " + ability.toString());
        if (!(ability instanceof PassAbility)) {
            didSomething = true;
        }

        activateAbility((ActivatedAbility) ability, game);

        actionCount++;
        return didSomething;
    }

    private Ability getAction(Game game) {
        List<Ability> playables = getPlayableAbilities(game);
        Ability ability;
        while (true) {
            if (playables.size() == 1) {
                ability = playables.get(0);
            } else {
                ability = playables.get(RandomUtil.nextInt(playables.size()));
            }
            List<Ability> options = getPlayableOptions(ability, game);
            if (!options.isEmpty()) {
                if (options.size() == 1) {
                    ability = options.get(0);
                } else {
                    ability = options.get(RandomUtil.nextInt(options.size()));
                }
            }
            if (ability.getManaCosts().getVariableCosts().size() > 0) {
                int amount = getAvailableManaProducers(game).size() - ability.getManaCosts().convertedManaCost();
                if (amount > 0) {
                    ability = ability.copy();
                    ability.getManaCostsToPay().add(new GenericManaCost(RandomUtil.nextInt(amount)));
                }
            }
            // check if ability kills player, if not then it's ok to play
//            if (ability.isUsesStack()) {
//                Game testSim = game.copy();
//                activateAbility((ActivatedAbility) ability, testSim);
//                StackObject testAbility = testSim.getStack().pop();
//                testAbility.resolve(testSim);
//                testSim.applyEffects();
//                testSim.checkStateAndTriggered();
//                if (!testSim.getPlayer(playerId).hasLost()) {
//                    break;
//                }
//            }
//            else {
            break;
//            }
        }
        return ability;
    }

    @Override
    public boolean triggerAbility(TriggeredAbility source, Game game) {
//        logger.info("trigger");
        if (source != null && source.canChooseTarget(game)) {
            Ability ability;
            List<Ability> options = getPlayableOptions(source, game);
            if (options.isEmpty()) {
                ability = source;
            } else {
                if (options.size() == 1) {
                    ability = options.get(0);
                } else {
                    ability = options.get(RandomUtil.nextInt(options.size()));
                }
            }
            if (ability.isUsesStack()) {
                game.getStack().push(new StackAbility(ability, playerId));
                if (ability.activate(game, false)) {
                    game.fireEvent(new GameEvent(GameEvent.EventType.TRIGGERED_ABILITY, ability.getId(), ability.getSourceId(), ability.getControllerId()));
                    actionCount++;
                    return true;
                }
            } else {
                if (ability.activate(game, false)) {
                    ability.resolve(game);
                    actionCount++;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void selectAttackers(Game game, UUID attackingPlayerId) {
        //useful only for two player games - will only attack first opponent
//        logger.info("select attackers");
        UUID defenderId = game.getOpponents(playerId).iterator().next();
        List<Permanent> attackersList = super.getAvailableAttackers(defenderId, game);
        //use binary digits to calculate powerset of attackers
        int powerElements = (int) Math.pow(2, attackersList.size());
        int value = RandomUtil.nextInt(powerElements);
        StringBuilder binary = new StringBuilder();
        binary.append(Integer.toBinaryString(value));
        while (binary.length() < attackersList.size()) {
            binary.insert(0, "0");  //pad with zeros
        }
        for (int i = 0; i < attackersList.size(); i++) {
            if (binary.charAt(i) == '1') {
                setStoredBookmark(game.bookmarkState()); // makes it possible to UNDO a declared attacker with costs from e.g. Propaganda
                if (!game.getCombat().declareAttacker(attackersList.get(i).getId(), defenderId, playerId, game)) {
                    game.undo(playerId);
                }
            }
        }
        actionCount++;
    }

    @Override
    public void selectBlockers(Game game, UUID defendingPlayerId) {
//        logger.info("select blockers");
        int numGroups = game.getCombat().getGroups().size();
        if (numGroups == 0) {
            return;
        }

        List<Permanent> blockers = getAvailableBlockers(game);
        for (Permanent blocker : blockers) {
            int check = RandomUtil.nextInt(numGroups + 1);
            if (check < numGroups) {
                CombatGroup group = game.getCombat().getGroups().get(check);
                if (group.getAttackers().size() > 0) {
                    this.declareBlocker(this.getId(), blocker.getId(), group.getAttackers().get(0), game);
                }
            }
        }
        actionCount++;
    }

    @Override
    public void abort() {
        abort = true;
    }

    protected boolean chooseRandom(Target target, Game game) {
        Set<UUID> possibleTargets = target.possibleTargets(playerId, game);
        if (possibleTargets.isEmpty()) {
            return false;
        }
        if (possibleTargets.size() == 1) {
            target.add(possibleTargets.iterator().next(), game);
            return true;
        }
        Iterator<UUID> it = possibleTargets.iterator();
        int targetNum = RandomUtil.nextInt(possibleTargets.size());
        UUID targetId = it.next();
        for (int i = 0; i < targetNum; i++) {
            targetId = it.next();
        }
        target.add(targetId, game);
        return true;
    }

    protected boolean chooseRandomTarget(Target target, Ability source, Game game) {
        Set<UUID> possibleTargets = target.possibleTargets(source == null ? null : source.getSourceId(), playerId, game);
        if (possibleTargets.isEmpty()) {
            return false;
        }
        if (!target.isRequired(source)) {
            if (RandomUtil.nextInt(possibleTargets.size() + 1) == 0) {
                return false;
            }
        }
        if (possibleTargets.size() == 1) {
            target.addTarget(possibleTargets.iterator().next(), source, game);
            return true;
        }
        Iterator<UUID> it = possibleTargets.iterator();
        int targetNum = RandomUtil.nextInt(possibleTargets.size());
        UUID targetId = it.next();
        for (int i = 0; i < targetNum; i++) {
            targetId = it.next();
        }
        target.addTarget(targetId, source, game);
        return true;
    }

    @Override
    public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game) {
        if (this.isHuman()) {
            return chooseRandom(target, game);
        }
        return super.choose(outcome, target, sourceId, game);
    }

    @Override
    public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game, Map<String, Serializable> options) {
        if (this.isHuman()) {
            return chooseRandom(target, game);
        }
        return super.choose(outcome, target, sourceId, game, options);
    }

    @Override
    public boolean choose(Outcome outcome, Cards cards, TargetCard target, Game game) {
        if (this.isHuman()) {
            if (cards.isEmpty()) {
                return false;
            }
            Set<UUID> possibleTargets = target.possibleTargets(playerId, cards, game);
            if (possibleTargets.isEmpty()) {
                return false;
            }
            Iterator<UUID> it = possibleTargets.iterator();
            int targetNum = RandomUtil.nextInt(possibleTargets.size());
            UUID targetId = it.next();
            for (int i = 0; i < targetNum; i++) {
                targetId = it.next();
            }
            target.add(targetId, game);
            return true;
        }
        return super.choose(outcome, cards, target, game);
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game) {
        return chooseRandomTarget(target, source, game);
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
        if (cards.isEmpty()) {
            return !target.isRequired(source);
        }
        Card card = cards.getRandom(game);
        target.addTarget(card.getId(), source, game);
        return true;
    }

    @Override
    public boolean chooseTargetAmount(Outcome outcome, TargetAmount target, Ability source, Game game) {
        Set<UUID> possibleTargets = target.possibleTargets(source == null ? null : source.getSourceId(), playerId, game);
        if (possibleTargets.isEmpty()) {
            return !target.isRequired(source);
        }
        if (!target.isRequired(source)) {
            if (RandomUtil.nextInt(possibleTargets.size() + 1) == 0) {
                return false;
            }
        }
        if (possibleTargets.size() == 1) {
            target.addTarget(possibleTargets.iterator().next(), target.getAmountRemaining(), source, game);
            return true;
        }
        Iterator<UUID> it = possibleTargets.iterator();
        int targetNum = RandomUtil.nextInt(possibleTargets.size());
        UUID targetId = it.next();
        for (int i = 0; i < targetNum; i++) {
            targetId = it.next();
        }
        target.addTarget(targetId, RandomUtil.nextInt(target.getAmountRemaining()) + 1, source, game);
        return true;
    }

    @Override
    public boolean chooseMulligan(Game game) {
        return RandomUtil.nextBoolean();
    }

    @Override
    public boolean chooseUse(Outcome outcome, String message, Ability source, Game game) {
        if (this.isHuman()) {
            return RandomUtil.nextBoolean();
        }
        return super.chooseUse(outcome, message, source, game);
    }

    @Override
    public boolean choosePile(Outcome outcome, String message, List<? extends Card> pile1, List<? extends Card> pile2, Game game) {
        if (this.isHuman()) {
            return RandomUtil.nextBoolean();
        }
        return super.choosePile(outcome, message, pile1, pile2, game);
    }

    @Override
    public boolean choose(Outcome outcome, Choice choice, Game game) {
        if (this.isHuman()) {
            Iterator<String> it = choice.getChoices().iterator();
            String sChoice = it.next();
            int choiceNum = RandomUtil.nextInt(choice.getChoices().size());
            for (int i = 0; i < choiceNum; i++) {
                sChoice = it.next();
            }
            choice.setChoice(sChoice);
            return true;
        }
        return super.choose(outcome, choice, game);
    }

    @Override
    public int chooseReplacementEffect(Map<String, String> rEffects, Game game) {
        if (this.isHuman()) {
            return RandomUtil.nextInt(rEffects.size());
        }
        return super.chooseReplacementEffect(rEffects, game);
    }

    @Override
    public TriggeredAbility chooseTriggeredAbility(List<TriggeredAbility> abilities, Game game) {
        if (this.isHuman()) {
            return abilities.get(RandomUtil.nextInt(abilities.size()));
        }
        return super.chooseTriggeredAbility(abilities, game);
    }

    @Override
    public Mode chooseMode(Modes modes, Ability source, Game game) {
        if (this.isHuman()) {
            Iterator<Mode> it = modes.getAvailableModes(source, game).iterator();
            Mode mode = it.next();
            if (modes.size() == 1) {
                return mode;
            }
            int modeNum = RandomUtil.nextInt(modes.getAvailableModes(source, game).size());
            for (int i = 0; i < modeNum; i++) {
                mode = it.next();
            }
            return mode;
        }
        return super.chooseMode(modes, source, game);
    }

    @Override
    public UUID chooseAttackerOrder(List<Permanent> attackers, Game game) {
        if (this.isHuman()) {
            return attackers.get(RandomUtil.nextInt(attackers.size())).getId();
        }
        return super.chooseAttackerOrder(attackers, game);
    }

    @Override
    public UUID chooseBlockerOrder(List<Permanent> blockers, CombatGroup combatGroup, List<UUID> blockerOrder, Game game) {
        if (this.isHuman()) {
            return blockers.get(RandomUtil.nextInt(blockers.size())).getId();
        }
        return super.chooseBlockerOrder(blockers, combatGroup, blockerOrder, game);
    }

    @Override
    public void assignDamage(int damage, List<UUID> targets, String singleTargetName, UUID sourceId, Game game) {
        if (this.isHuman()) {
            int remainingDamage = damage;
            UUID targetId;
            int amount;
            while (remainingDamage > 0) {
                if (targets.size() == 1) {
                    targetId = targets.get(0);
                    amount = remainingDamage;
                } else {
                    targetId = targets.get(RandomUtil.nextInt(targets.size()));
                    amount = RandomUtil.nextInt(damage + 1);
                }
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null) {
                    permanent.damage(amount, sourceId, game, false, true);
                    remainingDamage -= amount;
                } else {
                    Player player = game.getPlayer(targetId);
                    if (player != null) {
                        player.damage(amount, sourceId, game, false, true);
                        remainingDamage -= amount;
                    }
                }
                targets.remove(targetId);
            }
        } else {
            super.assignDamage(damage, targets, singleTargetName, sourceId, game);
        }
    }

    @Override
    public int getAmount(int min, int max, String message, Game game) {
        if (this.isHuman()) {
            return RandomUtil.nextInt(max - min) + min;
        }
        return super.getAmount(min, max, message, game);
    }

}
