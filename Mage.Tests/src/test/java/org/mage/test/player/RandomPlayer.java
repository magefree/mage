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

/**
 * plays randomly
 *
 * @author BetaSteward_at_googlemail.com
 */
public class RandomPlayer extends ComputerPlayer {

    private final boolean isSimulatedPlayer;
    private int actionCount = 0;

    protected PassAbility pass = new PassAbility();

    public RandomPlayer(String name) {
        super(name, RangeOfInfluence.ALL);
        this.isSimulatedPlayer = true;
    }

    public RandomPlayer(final RandomPlayer player) {
        super(player);
        this.isSimulatedPlayer = player.isSimulatedPlayer;
    }

    @Override
    public RandomPlayer copy() {
        return new RandomPlayer(this);
    }

    public boolean isSimulatedPlayer() {
        return this.isSimulatedPlayer;
    }

    public int getActionCount() {
        return actionCount;
    }

    @Override
    public boolean priority(Game game) {
        boolean didSomething = false;
        Ability ability = getAction(game);
        if (!(ability instanceof PassAbility)) {
            didSomething = true;
        }

        activateAbility((ActivatedAbility) ability, game);

        actionCount++;
        return didSomething;
    }

    private Ability getAction(Game game) {
        List<ActivatedAbility> playables = getPlayableAbilities(game);
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
            if (!ability.getManaCosts().getVariableCosts().isEmpty()) {
                int amount = getAvailableManaProducers(game).size() - ability.getManaCosts().manaValue();
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

    protected List<ActivatedAbility> getPlayableAbilities(Game game) {
        List<ActivatedAbility> playables = getPlayable(game, true);
        playables.add(pass);
        return playables;
    }

    @Override
    public boolean triggerAbility(TriggeredAbility source, Game game) {
        if (source != null && source.canChooseTarget(game, playerId)) {
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
                    game.fireEvent(new GameEvent(GameEvent.EventType.TRIGGERED_ABILITY, ability.getId(), ability, ability.getControllerId()));
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
        UUID defenderId = game.getOpponents(playerId).iterator().next();
        List<Permanent> attackersList = super.getAvailableAttackers(defenderId, game);
        //use binary digits to calculate powerset of attackers
        int powerElements = (int) Math.pow(2, attackersList.size());
        int value = RandomUtil.nextInt(powerElements);
        StringBuilder binary = new StringBuilder();
        binary.append(Integer.toBinaryString(value));
        while (binary.length() < attackersList.size()) {
            binary.insert(0, '0');  //pad with zeros
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
    public void selectBlockers(Ability source, Game game, UUID defendingPlayerId) {
        int numGroups = game.getCombat().getGroups().size();
        if (numGroups == 0) {
            return;
        }

        List<Permanent> blockers = getAvailableBlockers(game);
        for (Permanent blocker : blockers) {
            int check = RandomUtil.nextInt(numGroups + 1);
            if (check < numGroups) {
                CombatGroup group = game.getCombat().getGroups().get(check);
                if (!group.getAttackers().isEmpty()) {
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
        Set<UUID> possibleTargets = target.possibleTargets(playerId, source, game);
        if (possibleTargets.isEmpty()) {
            return false;
        }
        if (!target.isRequired(source)) {
            if (RandomUtil.nextInt(possibleTargets.size() + 1) == 0) {
                return false;
            }
        }
        if (possibleTargets.size() == 1) {
            target.addTarget(possibleTargets.iterator().next(), source, game); // todo: addtryaddtarget or return type (see computerPlayer)
            return true;
        }
        Iterator<UUID> it = possibleTargets.iterator();
        int targetNum = RandomUtil.nextInt(possibleTargets.size());
        UUID targetId = it.next();
        for (int i = 0; i < targetNum; i++) {
            targetId = it.next();
        }
        target.addTarget(targetId, source, game); // todo: addtryaddtarget or return type (see computerPlayer)
        return true;
    }

    @Override
    public boolean choose(Outcome outcome, Target target, Ability source, Game game) {
        return chooseRandom(target, game);
    }

    @Override
    public boolean choose(Outcome outcome, Target target, Ability source, Game game, Map<String, Serializable> options) {
        return chooseRandom(target, game);
    }

    @Override
    public boolean choose(Outcome outcome, Cards cards, TargetCard target, Game game) {
        if (cards.isEmpty()) {
            return false;
        }
        Set<UUID> possibleTargets = target.possibleTargets(playerId, cards, game);
        if (possibleTargets.isEmpty()) {
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
        if (card != null) {
            target.addTarget(card.getId(), source, game); // todo: addtryaddtarget or return type (see computerPlayer)
            return true;
        }
        return false;
    }

    @Override
    public boolean chooseTargetAmount(Outcome outcome, TargetAmount target, Ability source, Game game) {
        Set<UUID> possibleTargets = target.possibleTargets(playerId, source, game);
        if (possibleTargets.isEmpty()) {
            return !target.isRequired(source);
        }
        if (!target.isRequired(source)) {
            if (RandomUtil.nextInt(possibleTargets.size() + 1) == 0) {
                return false;
            }
        }
        if (possibleTargets.size() == 1) {
            target.addTarget(possibleTargets.iterator().next(), target.getAmountRemaining(), source, game); // todo: addtryaddtarget or return type (see computerPlayer)
            return true;
        }
        Iterator<UUID> it = possibleTargets.iterator();
        int targetNum = RandomUtil.nextInt(possibleTargets.size());
        UUID targetId = it.next();
        for (int i = 0; i < targetNum; i++) {
            targetId = it.next();
        }
        target.addTarget(targetId, RandomUtil.nextInt(target.getAmountRemaining()) + 1, source, game); // todo: addtryaddtarget or return type (see computerPlayer)
        return true;
    }

    @Override
    public boolean chooseMulligan(Game game) {
        return RandomUtil.nextBoolean();
    }

    @Override
    public boolean chooseUse(Outcome outcome, String message, Ability source, Game game) {
        return RandomUtil.nextBoolean();
    }

    @Override
    public boolean choosePile(Outcome outcome, String message, List<? extends Card> pile1, List<? extends Card> pile2, Game game) {
        return RandomUtil.nextBoolean();
    }

    @Override
    public boolean choose(Outcome outcome, Choice choice, Game game) {
        choice.setRandomChoice();
        return true;
    }

    @Override
    public int chooseReplacementEffect(Map<String, String> rEffects, Game game) {
        return RandomUtil.nextInt(rEffects.size());
    }

    @Override
    public TriggeredAbility chooseTriggeredAbility(List<TriggeredAbility> abilities, Game game) {
        return abilities.get(RandomUtil.nextInt(abilities.size()));
    }

    @Override
    public Mode chooseMode(Modes modes, Ability source, Game game) {
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

    @Override
    public UUID chooseAttackerOrder(List<Permanent> attackers, Game game) {
        return attackers.get(RandomUtil.nextInt(attackers.size())).getId();
    }

    @Override
    public UUID chooseBlockerOrder(List<Permanent> blockers, CombatGroup combatGroup, List<UUID> blockerOrder, Game game) {
        return blockers.get(RandomUtil.nextInt(blockers.size())).getId();
    }

    @Override
    public void assignDamage(int damage, List<UUID> targets, String singleTargetName, UUID attackerId, Ability source, Game game) {
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
                permanent.damage(amount, attackerId, source, game, false, true);
                remainingDamage -= amount;
            } else {
                Player player = game.getPlayer(targetId);
                if (player != null) {
                    player.damage(amount, attackerId, source, game);
                    remainingDamage -= amount;
                }
            }
            targets.remove(targetId);
        }
    }

    @Override
    public int getAmount(int min, int max, String message, Game game) {
        return RandomUtil.nextInt(max - min) + min;
    }

}
