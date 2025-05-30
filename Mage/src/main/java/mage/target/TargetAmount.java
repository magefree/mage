package mage.target;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.DebugUtil;
import mage.util.RandomUtil;

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
        return isChoiceCompleted(game);
    }

    @Override
    public boolean isChoiceCompleted(Game game) {
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

        while (remainingAmount > 0) {
            chosen = false;
            if (!player.canRespond()) {
                chosen = isChosen(game);
                break;
            }
            if (!getTargetController(game, playerId).chooseTargetAmount(outcome, this, source, game)) {
                chosen = isChosen(game);
                break;
            }
            chosen = isChosen(game);
        }

        return isChosen(game);
    }

    @Override
    final public List<? extends TargetAmount> getTargetOptions(Ability source, Game game) {
        if (!amountWasSet) {
            setAmount(source, game);
        }

        List<TargetAmount> options = new ArrayList<>();
        Set<UUID> possibleTargets = possibleTargets(source.getControllerId(), source, game);

        // optimizations for less memory/cpu consumptions
        printTargetsTableAndVariations("before optimize", game, possibleTargets, options, false);
        optimizePossibleTargets(source, game, possibleTargets);
        printTargetsTableAndVariations("after optimize", game, possibleTargets, options, false);

        // calc possible amount variations
        addTargets(this, possibleTargets, options, source, game);
        printTargetsTableAndVariations("after calc", game, possibleTargets, options, true);

        return options;
    }

    /**
     * AI related, trying to reduce targets for simulations
     */
    private void optimizePossibleTargets(Ability source, Game game, Set<UUID> possibleTargets) {
        // remove duplicated/same creatures (example: distribute 3 damage between 10+ same tokens)

        // it must have additional threshold to keep more variations for analyse
        //
        // bad example:
        // - Blessings of Nature
        // - Distribute four +1/+1 counters among any number of target creatures.
        // on low targets threshold AI can put 1/1 to opponent's creature instead own, see TargetAmountAITest.test_AI_SimulateTargets

        int maxPossibleTargetsToSimulate = this.remainingAmount * 2;
        if (possibleTargets.size() < maxPossibleTargetsToSimulate) {
            return;
        }

        // split targets by groups
        Map<UUID, String> targetGroups = new HashMap<>();
        possibleTargets.forEach(id -> {
            String groupKey = "";

            // player
            Player player = game.getPlayer(id);
            if (player != null) {
                groupKey = getTargetGroupKeyAsPlayer(player);
            }

            // game object
            MageObject object = game.getObject(id);
            if (object != null) {
                groupKey = object.getName();
                if (object instanceof Permanent) {
                    groupKey += getTargetGroupKeyAsPermanent(game, (Permanent) object);
                } else if (object instanceof Card) {
                    groupKey += getTargetGroupKeyAsCard(game, (Card) object);
                } else {
                    groupKey += getTargetGroupKeyAsOther(game, object);
                }
            }

            // unknown - use all
            if (groupKey.isEmpty()) {
                groupKey = id.toString();
            }

            targetGroups.put(id, groupKey);
        });

        Map<String, List<UUID>> groups = new HashMap<>();
        targetGroups.forEach((id, groupKey) -> {
            groups.computeIfAbsent(groupKey, k -> new ArrayList<>());
            groups.get(groupKey).add(id);
        });

        // optimize logic:
        // - use one target from each target group all the time
        // - add random target from random group until fill all remainingAmount condition

        // use one target per group
        Set<UUID> newPossibleTargets = new HashSet<>();
        groups.forEach((groupKey, groupTargets) -> {
            UUID targetId = RandomUtil.randomFromCollection(groupTargets);
            if (targetId != null) {
                newPossibleTargets.add(targetId);
                groupTargets.remove(targetId);
            }
        });

        // use random target until fill condition
        while (newPossibleTargets.size() < maxPossibleTargetsToSimulate) {
            String groupKey = RandomUtil.randomFromCollection(groups.keySet());
            if (groupKey == null) {
                break;
            }
            List<UUID> groupTargets = groups.getOrDefault(groupKey, null);
            if (groupTargets == null || groupTargets.isEmpty()) {
                groups.remove(groupKey);
                continue;
            }
            UUID targetId = RandomUtil.randomFromCollection(groupTargets);
            if (targetId != null) {
                newPossibleTargets.add(targetId);
                groupTargets.remove(targetId);
            }
        }

        // keep final result
        possibleTargets.clear();
        possibleTargets.addAll(newPossibleTargets);
    }

    private String getTargetGroupKeyAsPlayer(Player player) {
        // use all
        return String.join(";", Arrays.asList(
                player.getName(),
                String.valueOf(player.getId().hashCode())
        ));
    }

    private String getTargetGroupKeyAsPermanent(Game game, Permanent permanent) {
        // split by name and stats
        // TODO: rework and combine with PermanentEvaluator (to use battlefield score)

        // try to use short text/hash for lesser data on debug
        return String.join(";", Arrays.asList(
                permanent.getName(),
                String.valueOf(permanent.getControllerId().hashCode()),
                String.valueOf(permanent.getOwnerId().hashCode()),
                String.valueOf(permanent.isTapped()),
                String.valueOf(permanent.getPower().getValue()),
                String.valueOf(permanent.getToughness().getValue()),
                String.valueOf(permanent.getDamage()),
                String.valueOf(permanent.getCardType(game).toString().hashCode()),
                String.valueOf(permanent.getSubtype(game).toString().hashCode()),
                String.valueOf(permanent.getCounters(game).getTotalCount()),
                String.valueOf(permanent.getAbilities(game).size()),
                String.valueOf(permanent.getRules(game).toString().hashCode())
        ));
    }

    private String getTargetGroupKeyAsCard(Game game, Card card) {
        // split by name and stats
        return String.join(";", Arrays.asList(
                card.getName(),
                String.valueOf(card.getOwnerId().hashCode()),
                String.valueOf(card.getCardType(game).toString().hashCode()),
                String.valueOf(card.getSubtype(game).toString().hashCode()),
                String.valueOf(card.getCounters(game).getTotalCount()),
                String.valueOf(card.getAbilities(game).size()),
                String.valueOf(card.getRules(game).toString().hashCode())
        ));
    }

    private String getTargetGroupKeyAsOther(Game game, MageObject item) {
        // use all
        return String.join(";", Arrays.asList(
                item.getName(),
                String.valueOf(item.getId().hashCode())
        ));
    }

    /**
     * Debug only. Print targets table and variations.
     */
    private void printTargetsTableAndVariations(String info, Game game, Set<UUID> possibleTargets, List<TargetAmount> options, boolean isPrintOptions) {
        if (!DebugUtil.AI_SHOW_TARGET_OPTIMIZATION_LOGS) return;

        // output example:
        //
        // Targets (after optimize): 5
        // 0. Balduvian Bears [ac8], C, BalduvianBears, DKM:22::0, 2/2
        // 1. PlayerA (SimulatedPlayer2)
        //
        // Target variations (info): 126
        // 0 -> 1; 1 -> 1; 2 -> 1; 3 -> 1; 4 -> 1
        // 0 -> 1; 1 -> 1; 2 -> 1; 3 -> 2
        // 0 -> 1; 1 -> 1; 2 -> 1; 4 -> 2

        // print table
        List<UUID> list = new ArrayList<>(possibleTargets);
        Collections.sort(list);
        HashMap<UUID, Integer> targetNumbers = new HashMap<>();
        System.out.println();
        System.out.println(String.format("Targets (%s): %d", info, list.size()));
        for (int i = 0; i < list.size(); i++) {
            targetNumbers.put(list.get(i), i);
            String targetName;
            Player player = game.getPlayer(list.get(i));
            if (player != null) {
                targetName = player.toString();
            } else {
                MageObject object = game.getObject(list.get(i));
                if (object != null) {
                    targetName = object.toString();
                } else {
                    targetName = "unknown";
                }
            }
            System.out.println(String.format("%d. %s", i, targetName));
        }
        System.out.println();

        if (!isPrintOptions) {
            return;
        }

        // print amount variations
        List<String> res = options
                .stream()
                .map(t -> t.getTargets()
                        .stream()
                        .map(id -> targetNumbers.get(id) + " -> " + t.getTargetAmount(id))
                        .sorted()
                        .collect(Collectors.joining("; "))).sorted().collect(Collectors.toList());
        System.out.println();
        System.out.println(String.format("Target variations (info): %d", options.size()));
        System.out.println(String.join("\n", res));
        System.out.println();
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
