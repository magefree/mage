package mage.target;

import mage.MageObject;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.DebugUtil;
import mage.util.RandomUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Helper class to optimize possible targets list for AI and playable calcs
 * <p>
 * Features:
 * - less possible targets, less combinations, less CPU/memory usage for sims
 * - group all possible targets by same characteristics;
 * - fill target one by one from each group
 *
 * @author JayDi85
 */
public class TargetOptimization {

    // for up to or any amount - limit max game sims to analyse
    // (it's useless to calc all possible combinations on too much targets)
    static public int AI_MAX_POSSIBLE_TARGETS_TO_CHOOSE = 18;

    public static void optimizePossibleTargets(Ability source, Game game, Set<UUID> possibleTargets, int maxPossibleTargetsToSimulate) {
        // remove duplicated/same creatures
        // example: distribute 3 damage between 10+ same tokens
        // example: target x1 from x10 forests - it's useless to recalc each forest

        if (possibleTargets.size() <= maxPossibleTargetsToSimulate) {
            return;
        }

        // split targets by groups
        Map<String, ArrayList<UUID>> targetGroups = createGroups(game, possibleTargets, maxPossibleTargetsToSimulate, false);

        // optimize logic:
        // - use one target from each target group all the time
        // - add random target from random group until fill all remainingAmount condition

        // use one target per group
        Set<UUID> newPossibleTargets = new HashSet<>();
        targetGroups.forEach((groupKey, groupTargets) -> {
            UUID targetId = RandomUtil.randomFromCollection(groupTargets);
            if (targetId != null) {
                newPossibleTargets.add(targetId);
                groupTargets.remove(targetId);
            }
        });

        // use random target until fill condition
        while (newPossibleTargets.size() < maxPossibleTargetsToSimulate) {
            String groupKey = RandomUtil.randomFromCollection(targetGroups.keySet());
            if (groupKey == null) {
                break;
            }
            List<UUID> groupTargets = targetGroups.getOrDefault(groupKey, null);
            if (groupTargets == null || groupTargets.isEmpty()) {
                targetGroups.remove(groupKey);
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

    private static Map<String, ArrayList<UUID>> createGroups(Game game, Set<UUID> possibleTargets, int maxPossibleTargetsToSimulate, boolean isLoose) {
        Map<String, ArrayList<UUID>> targetGroups = new HashMap<>();

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
                    groupKey += getTargetGroupKeyAsPermanent(game, (Permanent) object, isLoose);
                } else if (object instanceof Card) {
                    groupKey += getTargetGroupKeyAsCard(game, (Card) object, isLoose);
                } else {
                    groupKey += getTargetGroupKeyAsOther(game, object);
                }
            }

            // unknown - use all
            if (groupKey.isEmpty()) {
                groupKey = id.toString();
            }

            targetGroups.computeIfAbsent(groupKey, k -> new ArrayList<>()).add(id);
        });

        if (targetGroups.size() > maxPossibleTargetsToSimulate && !isLoose) {
            // If too many possible target groups, regroup with less specific characteristics
            return createGroups(game, possibleTargets, maxPossibleTargetsToSimulate, true);
        }

        // Return appropriate target groups or, if still too many possible targets after loose grouping,
        // allow optimizePossibleTargets (defined above) to choose random targets within limit
        return targetGroups;
    }

    private static String getTargetGroupKeyAsPlayer(Player player) {
        // use all
        return String.join(";", Arrays.asList(
                player.getName(),
                String.valueOf(player.getId().hashCode())
        ));
    }

    private static String getTargetGroupKeyAsPermanent(Game game, Permanent permanent, boolean isLoose) {
        // split by name and stats
        // TODO: rework and combine with PermanentEvaluator (to use battlefield score)

        // try to use short text/hash for lesser data on debug
        if (!isLoose) {
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
        else {
            return String.join(";", Arrays.asList(
                    String.valueOf(permanent.getControllerId().hashCode()),
                    String.valueOf(permanent.getPower().getValue()),
                    String.valueOf(permanent.getToughness().getValue()),
                    String.valueOf(permanent.getDamage()),
                    String.valueOf(permanent.getCardType(game).toString().hashCode())
            ));
        }
    }

    private static String getTargetGroupKeyAsCard(Game game, Card card, boolean isLoose) {
        // split by name and stats
        if (!isLoose) {
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
        else {
            return String.join(";", Arrays.asList(
                    String.valueOf(card.getOwnerId().hashCode()),
                    String.valueOf(card.getCardType(game).toString().hashCode())
            ));
        }
    }

    private static String getTargetGroupKeyAsOther(Game game, MageObject item) {
        // use all
        return String.join(";", Arrays.asList(
                item.getName(),
                String.valueOf(item.getId().hashCode())
        ));
    }

    public static void printTargetsVariationsForTarget(String info, Game game, Set<UUID> possibleTargets, List<TargetImpl> options, boolean isPrintOptions) {
        List<Target> usedOptions = options.stream()
                .filter(Objects::nonNull)
                .map(TargetImpl.class::cast)
                .collect(Collectors.toList());
        printTargetsTableAndVariationsInner(info, game, possibleTargets, usedOptions, isPrintOptions);
    }

    public static void printTargetsVariationsForTargetAmount(String info, Game game, Set<UUID> possibleTargets, List<TargetAmount> options, boolean isPrintOptions) {
        List<Target> usedOptions = options.stream()
                .filter(Objects::nonNull)
                .map(TargetImpl.class::cast)
                .collect(Collectors.toList());
        printTargetsTableAndVariationsInner(info, game, possibleTargets, usedOptions, isPrintOptions);
    }

    private static void printTargetsTableAndVariationsInner(String info, Game game, Set<UUID> possibleTargets, List<Target> options, boolean isPrintOptions) {
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
                        .map(id -> targetNumbers.get(id) + (t instanceof TargetAmount ? " -> " + t.getTargetAmount(id) : ""))
                        .sorted()
                        .collect(Collectors.joining("; "))).sorted().collect(Collectors.toList());
        System.out.println();
        System.out.println(String.format("Target variations (info): %d", options.size()));
        System.out.println(String.join("\n", res));
        System.out.println();
    }

}
