package mage.target;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class TargetAmount extends TargetImpl {

    boolean amountWasSet = false;
    DynamicValue amount;
    int remainingAmount;

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
        return doneChoosing(game);
    }

    @Override
    public boolean doneChoosing(Game game) {
        return amountWasSet
                && (remainingAmount == 0
                || (getMinNumberOfTargets() < getMaxNumberOfTargets()
                && getTargets().size() >= getMinNumberOfTargets()));
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

    public void setAmount(Ability source, Game game) {
        remainingAmount = amount.calculate(game, source, null);
        amountWasSet = true;
    }

    public DynamicValue getAmount() {
        return amount;
    }

    public int getAmountTotal(Game game, Ability source) {
        return amount.calculate(game, source, null);
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
        int usedAmount = getTargetAmount(id);
        super.remove(id);
        this.remainingAmount += usedAmount;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, UUID playerId, Ability source, Game game) {
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return false;
        }

        if (!amountWasSet) {
            setAmount(source, game);
        }
        chosen = isChosen(game);
        while (remainingAmount > 0) {
            if (!player.canRespond()) {
                return chosen;
            }
            if (!getTargetController(game, playerId).chooseTargetAmount(outcome, this, source, game)) {
                return chosen;
            }
            chosen = isChosen(game);
        }
        return chosen;
    }

    @Override
    public List<? extends TargetAmount> getTargetOptions(Ability source, Game game) {
        List<TargetAmount> options = new ArrayList<>();
        Set<UUID> possibleTargets = possibleTargets(source.getControllerId(), source, game);

        addTargets(this, possibleTargets, options, source, game);

        // debug target variations
        //printTargetsVariations(possibleTargets, options);

        return options;
    }

    private void printTargetsVariations(Set<UUID> possibleTargets, List<TargetAmount> options) {
        // debug target variations
        // permanent index + amount
        // example: 7 -> 2; 8 -> 3; 9 -> 1
        List<UUID> list = new ArrayList<>(possibleTargets);
        HashMap<UUID, Integer> targetNumbers = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            targetNumbers.put(list.get(i), i);
        }
        List<String> res = options
                .stream()
                .map(t -> t.getTargets()
                        .stream()
                        .map(id -> targetNumbers.get(id) + " -> " + t.getTargetAmount(id))
                        .sorted()
                        .collect(Collectors.joining("; ")))
                .collect(Collectors.toList());
        Collections.sort(res);
        System.out.println();
        System.out.println(res.stream().collect(Collectors.joining("\n")));
        System.out.println();
    }

    protected void addTargets(TargetAmount target, Set<UUID> possibleTargets, List<TargetAmount> options, Ability source, Game game) {
        if (!amountWasSet) {
            setAmount(source, game);
        }
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
        if (!amountWasSet) {
            setAmount(source, game);
        }
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
}
