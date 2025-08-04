package mage.player.ai;

import mage.MageObject;
import mage.abilities.*;
import mage.abilities.common.PassAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.cards.Card;
import mage.cards.Cards;
import mage.choices.Choice;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.match.MatchPlayer;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetAmount;
import mage.target.TargetCard;
import mage.util.RandomUtil;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;

/**
 * AI: helper class to simulate games with MCTS AI (each player replaced by simulated)
 * <p>
 * Plays randomly
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class SimulatedPlayerMCTS extends MCTSPlayer {

    private boolean isSimulatedPlayer;
    private int actionCount = 0;
    private static final Logger logger = Logger.getLogger(SimulatedPlayerMCTS.class);

    public SimulatedPlayerMCTS(Player originalPlayer, boolean isSimulatedPlayer) {
        super(originalPlayer.getId());
        this.isSimulatedPlayer = isSimulatedPlayer;
        this.matchPlayer = new MatchPlayer(originalPlayer.getMatchPlayer(), this);
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
                    ability.addManaCostsToPay(new GenericManaCost(RandomUtil.nextInt(amount)));
                }
            }
            break;
        }
        return ability;
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
                game.getStack().push(game, new StackAbility(ability, playerId));
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
        UUID defenderId = game.getOpponents(playerId, true).iterator().next();
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

    private boolean chooseRandom(Target target, Ability source, Game game) {
        Set<UUID> possibleTargets = target.possibleTargets(playerId, source, game);
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
        target.addTarget(targetId, source, game);// todo: addtryaddtarget or return type (see computerPlayer)
        return true;
    }

    @Override
    public boolean choose(Outcome outcome, Target target, Ability source, Game game) {
        if (this.isHuman()) {
            return chooseRandom(target, source, game);
        }
        return super.choose(outcome, target, source, game);
    }

    @Override
    public boolean choose(Outcome outcome, Target target, Ability source, Game game, Map<String, Serializable> options) {
        if (this.isHuman()) {
            return chooseRandom(target, source, game);
        }
        return super.choose(outcome, target, source, game, options);
    }

    @Override
    public boolean choose(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
        if (this.isHuman()) {
            if (cards.isEmpty()) {
                return false;
            }
            Set<UUID> possibleTargets = target.possibleTargets(playerId, source, game, cards);
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
        return super.choose(outcome, cards, target, source, game);
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

        // nothing to choose
        target.prepareAmount(source, game);
        if (target.getAmountRemaining() <= 0) {
            return false;
        }
        if (target.getMaxNumberOfTargets() == 0 && target.getMinNumberOfTargets() == 0) {
            return false;
        }

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
            choice.setRandomChoice();
            return true;
        }
        return super.choose(outcome, choice, game);
    }

    @Override
    public int chooseReplacementEffect(Map<String, String> effectsMap, Map<String, MageObject> objectsMap, Game game) {
        if (this.isHuman()) {
            return RandomUtil.nextInt(effectsMap.size());
        }
        return super.chooseReplacementEffect(effectsMap, objectsMap, game);
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
    public int getAmount(int min, int max, String message, Ability source, Game game) {
        if (this.isHuman()) {
            return RandomUtil.nextInt(max - min + 1) + min;
        }
        return super.getAmount(min, max, message, source, game);
    }

}
