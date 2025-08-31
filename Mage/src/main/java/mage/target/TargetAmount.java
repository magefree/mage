package mage.target;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.cards.Cards;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Distribute value between targets list (damage, counters, etc)
 *
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public abstract class TargetAmount extends TargetImpl {

    boolean amountWasSet = false;
    DynamicValue amount;
    int remainingAmount; // before any change to it - make sure you call prepareAmount

    protected TargetAmount(DynamicValue amount, int minNumberOfTargets, int maxNumberOfTargets) {
        this.amount = amount;
        setMinNumberOfTargets(minNumberOfTargets);
        setMaxNumberOfTargets(maxNumberOfTargets);
    }

    protected TargetAmount(final TargetAmount target) {
        super(target);
        this.amount = target.amount;
        this.remainingAmount = target.remainingAmount;
        this.amountWasSet = target.amountWasSet;
    }

    @Override
    public abstract TargetAmount copy();

    public int getAmountRemaining() {
        return remainingAmount;
    }

    @Override
    public boolean isChosen(Game game) {
        if (!super.isChosen(game)) {
            return false;
        }

        // selection not started
        if (!amountWasSet) {
            return false;
        }

        // distribution
        if (getMinNumberOfTargets() == 0 && this.targets.isEmpty()) {
            // allow 0 distribution, e.g. for "up to" targets like Vivien, Arkbow Ranger
            return true;
        } else {
            // need full distribution
            return remainingAmount == 0;
        }
    }

    @Override
    public boolean isChoiceCompleted(UUID abilityControllerId, Ability source, Game game, Cards fromCards) {
        // make sure target request called one time minimum (for "up to" targets)
        // choice is selected after any addTarget call (by test, AI or human players)
        if (!isChoiceSelected()) {
            return false;
        }

        // make sure selected targets are valid
        if (!isChosen(game)) {
            return false;
        }

        // already selected
        if (this.getSize() >= getMaxNumberOfTargets()) {
            return true;
        }

        // TODO: need auto-choose here? See super

        // all other use cases are fine
        return true;
    }

    @Override
    public void clearChosen() {
        super.clearChosen();
        amountWasSet = false;
        // remainingAmount = amount; // auto-calced on target remove
    }

    public void setAmountDefinition(DynamicValue amount) {
        this.amount = amount;
    }

    /**
     * Prepare new targets for choosing
     */
    public void prepareAmount(Ability source, Game game) {
        if (!amountWasSet) {
            remainingAmount = amount.calculate(game, source, null);
            amountWasSet = true;
        }
    }

    public DynamicValue getAmount() {
        return amount;
    }

    public int getAmountTotal(Game game, Ability source) {
        return amount.calculate(game, source, null);
    }

    @Override
    public void addTarget(UUID id, int amount, Ability source, Game game, boolean skipEvent) {
        prepareAmount(source, game);

        if (amount <= remainingAmount) {
            remainingAmount -= amount;
            super.addTarget(id, amount, source, game, skipEvent);
        }
    }

    @Override
    public void remove(UUID id) {
        int usedAmount = getTargetAmount(id);
        super.remove(id);
        this.remainingAmount += usedAmount;
    }

    @Override
    public boolean choose(Outcome outcome, UUID playerId, UUID sourceId, Ability source, Game game) {
        throw new IllegalArgumentException("Wrong code usage. TargetAmount must be called by player.chooseTarget, not player.choose");
    }

    @Override
    @Deprecated // TODO: replace by player.chooseTargetAmount call
    public boolean chooseTarget(Outcome outcome, UUID playerId, Ability source, Game game) {
        Player targetController = getTargetController(game, playerId);
        if (targetController == null) {
            return false;
        }

        prepareAmount(source, game);

        chosen = false;
        do {
            int prevTargetsCount = this.getTargets().size();

            // stop by disconnect
            if (!targetController.canRespond()) {
                break;
            }

            // MAKE A CHOICE
            if (isRandom()) {
                // random choice
                throw new IllegalArgumentException("Wrong code usage. TargetAmount do not support random choices");
            } else {
                // player's choice

                // TargetAmount do not support auto-choice

                // manual

                // stop by cancel/done
                if (!targetController.chooseTargetAmount(outcome, this, source, game)) {
                    break;
                }

                // continue to next target
            }

            chosen = isChosen(game);

            // stop by full complete
            if (isChoiceCompleted(targetController.getId(), source, game, null)) {
                break;
            }

            // stop by nothing to choose (actual for human and done button?)
            if (prevTargetsCount == this.getTargets().size()) {
                break;
            }

            // can select next target
        } while (true);

        chosen = isChosen(game);
        return chosen && !this.getTargets().isEmpty();
    }

    @Override
    final public List<? extends TargetAmount> getTargetOptions(Ability source, Game game) {
        prepareAmount(source, game);

        List<TargetAmount> options = new ArrayList<>();
        Set<UUID> possibleTargets = possibleTargets(source.getControllerId(), source, game);

        // optimizations for less memory/cpu consumptions
        TargetOptimization.printTargetsVariationsForTargetAmount("target amount - before optimize", game, possibleTargets, options, false);
        // it must have additional threshold to keep more variations for analyse
        // bad example:
        // - Blessings of Nature
        // - Distribute four +1/+1 counters among any number of target creatures.
        // on low targets threshold AI can put 1/1 to opponent's creature instead own, see TargetAmountAITest.test_AI_SimulateTargets
        int maxPossibleTargetsToSimulate = CardUtil.overflowMultiply(this.remainingAmount, 2);
        TargetOptimization.optimizePossibleTargets(source, game, possibleTargets, maxPossibleTargetsToSimulate);
        TargetOptimization.printTargetsVariationsForTargetAmount("target amount - after optimize", game, possibleTargets, options, false);

        // calc possible amount variations
        addTargets(this, possibleTargets, options, source, game);
        TargetOptimization.printTargetsVariationsForTargetAmount("target amount - after calc", game, possibleTargets, options, true);

        return options;
    }

    final protected void addTargets(TargetAmount target, Set<UUID> possibleTargets, List<TargetAmount> options, Ability source, Game game) {
        Set<UUID> usedTargets = new HashSet<>();
        for (UUID targetId : possibleTargets) {
            usedTargets.add(targetId);
            for (int n = 1; n <= target.remainingAmount; n++) {
                TargetAmount t = target.copy();
                t.addTarget(targetId, n, source, game, true);
                if (t.remainingAmount > 0) {
                    if (possibleTargets.size() > 1) {
                        // don't use that target again
                        Set<UUID> newPossibleTargets = possibleTargets.stream().filter(newTarget -> !usedTargets.contains(newTarget)).collect(Collectors.toSet());
                        addTargets(t, newPossibleTargets, options, source, game);
                    }
                } else {
                    options.add(t);
                }
            }
        }
    }

    public void setTargetAmount(UUID targetId, int amount, Ability source, Game game) {
        prepareAmount(source, game);

        remainingAmount -= (amount - this.getTargetAmount(targetId));
        this.setTargetAmount(targetId, amount, game);
    }

    @Override
    protected boolean getUseAnyNumber() {
        int min = getMinNumberOfTargets();
        int max = getMaxNumberOfTargets();
        if (min != 0) {
            return false;
        }
        if (max == Integer.MAX_VALUE) {
            return true;
        }
        // For a TargetAmount with a min of 0:
        // A max that equals the amount, when the amount is a StaticValue,
        // usually represents "any number of target __s", since you can't target more than the amount.
        //
        // 601.2d. If the spell requires the player to divide or distribute an effect
        // (such as damage or counters) among one or more targets, the player announces the division.
        // Each of these targets must receive at least one of whatever is being divided.
        return amount instanceof StaticValue && max == ((StaticValue) amount).getValue();
    }

    @Override
    public String toString() {
        if (amountWasSet) {
            return super.toString() + String.format(" (remain amount %d of %s)", this.remainingAmount, this.amount.toString());
        } else {
            return super.toString() + String.format(" (remain not prepared, %s)", this.amount.toString());
        }
    }
}
