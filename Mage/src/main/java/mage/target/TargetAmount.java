package mage.target;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.constants.Outcome;
import mage.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class TargetAmount extends TargetImpl {

    boolean amountWasSet = false;
    DynamicValue amount;
    int remainingAmount;

    public TargetAmount(int amount) {
        this(new StaticValue(amount));
    }

    public TargetAmount(DynamicValue amount) {
        this.amount = amount;
        //this.remainingAmount = amount;
        amountWasSet = false;
    }

    public TargetAmount(final TargetAmount target) {
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
    public boolean isChosen() {
        return doneChosing();
    }

    @Override
    public boolean doneChosing() {
        return amountWasSet && remainingAmount == 0;
    }

    @Override
    public void clearChosen() {
        super.clearChosen();
        amountWasSet = false;
        // remainingAmount = amount;
    }

    public void setAmountDefinition(DynamicValue amount) {
        this.amount = amount;
    }

    public void setAmount(Ability source, Game game) {
        remainingAmount = amount.calculate(game, source, null);
        amountWasSet = true;
    }


    @Override
    public void addTarget(UUID id, int amount, Ability source, Game game, boolean skipEvent) {
        if (!amountWasSet) {
            setAmount(source, game);
        }
        if (amount <= remainingAmount) {
            super.addTarget(id, amount, source, game, skipEvent);
            remainingAmount -= amount;
        }
    }

    @Override
    public void remove(UUID id) {
        int amount = getTargetAmount(id);
        super.remove(id);
        this.remainingAmount += amount;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, UUID playerId, Ability source, Game game) {
        if (!amountWasSet) {
            setAmount(source, game);
        }
        chosen = remainingAmount == 0;
        while (remainingAmount > 0) {
            if (!getTargetController(game, playerId).chooseTargetAmount(outcome, this, source, game)) {
                return chosen;
            }
            chosen = remainingAmount == 0;
        }
        return chosen = true;
    }

    @Override
    public List<? extends TargetAmount> getTargetOptions(Ability source, Game game) {
        List<TargetAmount> options = new ArrayList<>();
        Set<UUID> possibleTargets = possibleTargets(source.getSourceId(), source.getControllerId(), game);

        addTargets(this, possibleTargets, options, source, game);

        return options;
    }

    protected void addTargets(TargetAmount target, Set<UUID> targets, List<TargetAmount> options, Ability source, Game game) {
        if (!amountWasSet) {
            setAmount(source, game);
        }
        for (UUID targetId : targets) {
            for (int n = 1; n <= target.remainingAmount; n++) {
                TargetAmount t = target.copy();
                t.addTarget(targetId, n, source, game, true);
                if (t.remainingAmount > 0) {
                    if (targets.size() > 1) {
                        Set<UUID> newTargets = targets.stream().filter(newTarget -> !newTarget.equals(targetId)).collect(Collectors.toSet());
                        addTargets(t, newTargets, options, source, game);
                    }
                } else {
                    options.add(t);
                }
            }
        }
    }
}
