package mage.player.ai;

import mage.abilities.Ability;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Generic before/after state delta for AI simulations.
 *
 * This intentionally stays card-agnostic: it records the shape of state changes
 * caused by actions or triggered/replacement effects, not named mechanics.
 */
public final class AiStateImpact {

    private static final int MAX_EXAMPLES = 12;

    private int playerLifeDelta;
    private int opponentsLifeDelta;
    private int ownEntered;
    private int opponentEntered;
    private int ownLeft;
    private int opponentLeft;
    private int ownCreaturesLeft;
    private int opponentCreaturesLeft;
    private int ownPowerToughnessChanged;
    private int opponentPowerToughnessChanged;
    private int ownAbilitiesChanged;
    private int opponentAbilitiesChanged;
    private int ownCountersChanged;
    private int opponentCountersChanged;
    private int ownDamageChanged;
    private int opponentDamageChanged;
    private int ownTappedChanged;
    private int opponentTappedChanged;
    private int ownCombatChanged;
    private int opponentCombatChanged;
    private final List<String> examples = new ArrayList<>();

    public static AiStateImpact between(Game before, Game after, UUID playerId) {
        AiStateImpact impact = new AiStateImpact();
        if (before == null || after == null || playerId == null) {
            return impact;
        }
        impact.compareLife(before, after, playerId);
        impact.compareBattlefield(before, after, playerId);
        return impact;
    }

    private void compareLife(Game before, Game after, UUID playerId) {
        Map<UUID, Integer> beforeLife = lifeByPlayer(before);
        Map<UUID, Integer> afterLife = lifeByPlayer(after);
        Set<UUID> ids = new HashSet<>();
        ids.addAll(beforeLife.keySet());
        ids.addAll(afterLife.keySet());
        for (UUID id : ids) {
            int delta = afterLife.getOrDefault(id, 0) - beforeLife.getOrDefault(id, 0);
            if (delta == 0) {
                continue;
            }
            if (playerId.equals(id)) {
                playerLifeDelta += delta;
                addExample("life " + beforeLife.getOrDefault(id, 0) + "->" + afterLife.getOrDefault(id, 0));
            } else {
                opponentsLifeDelta += delta;
            }
        }
    }

    private static Map<UUID, Integer> lifeByPlayer(Game game) {
        Map<UUID, Integer> result = new HashMap<>();
        for (Player player : game.getPlayers().values()) {
            result.put(player.getId(), player.getLife());
        }
        return result;
    }

    private void compareBattlefield(Game before, Game after, UUID playerId) {
        Map<UUID, PermanentState> beforePermanents = permanentStates(before);
        Map<UUID, PermanentState> afterPermanents = permanentStates(after);
        Set<UUID> ids = new HashSet<>();
        ids.addAll(beforePermanents.keySet());
        ids.addAll(afterPermanents.keySet());
        for (UUID id : ids) {
            PermanentState oldState = beforePermanents.get(id);
            PermanentState newState = afterPermanents.get(id);
            if (oldState == null) {
                countEntered(newState, playerId);
                continue;
            }
            if (newState == null) {
                countLeft(oldState, playerId);
                continue;
            }
            countChanges(oldState, newState, playerId);
        }
    }

    private static Map<UUID, PermanentState> permanentStates(Game game) {
        Map<UUID, PermanentState> result = new HashMap<>();
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
            result.put(permanent.getId(), new PermanentState(permanent, game));
        }
        return result;
    }

    private void countEntered(PermanentState state, UUID playerId) {
        if (state == null) {
            return;
        }
        if (state.isControlledBy(playerId)) {
            ownEntered++;
            addExample("own entered " + state.name);
        } else {
            opponentEntered++;
            addExample("opponent entered " + state.name);
        }
    }

    private void countLeft(PermanentState state, UUID playerId) {
        if (state == null) {
            return;
        }
        if (state.isControlledBy(playerId)) {
            ownLeft++;
            if (state.creature) {
                ownCreaturesLeft++;
            }
            addExample("own left " + state.name);
        } else {
            opponentLeft++;
            if (state.creature) {
                opponentCreaturesLeft++;
            }
            addExample("opponent left " + state.name);
        }
    }

    private void countChanges(PermanentState oldState, PermanentState newState, UUID playerId) {
        boolean own = newState.isControlledBy(playerId);
        if (oldState.power != newState.power || oldState.toughness != newState.toughness) {
            if (own) {
                ownPowerToughnessChanged++;
            } else {
                opponentPowerToughnessChanged++;
            }
            addExample(newState.name + " P/T " + oldState.power + "/" + oldState.toughness + "->" + newState.power + "/" + newState.toughness);
        }
        if (!Objects.equals(oldState.abilities, newState.abilities)) {
            if (own) {
                ownAbilitiesChanged++;
            } else {
                opponentAbilitiesChanged++;
            }
            addExample(newState.name + " abilities changed");
        }
        if (!Objects.equals(oldState.counters, newState.counters)) {
            if (own) {
                ownCountersChanged++;
            } else {
                opponentCountersChanged++;
            }
            addExample(newState.name + " counters " + oldState.counters + "->" + newState.counters);
        }
        if (oldState.damage != newState.damage) {
            if (own) {
                ownDamageChanged++;
            } else {
                opponentDamageChanged++;
            }
            addExample(newState.name + " damage " + oldState.damage + "->" + newState.damage);
        }
        if (oldState.tapped != newState.tapped) {
            if (own) {
                ownTappedChanged++;
            } else {
                opponentTappedChanged++;
            }
            addExample(newState.name + (newState.tapped ? " tapped" : " untapped"));
        }
        if (oldState.attacking != newState.attacking || oldState.blocking != newState.blocking) {
            if (own) {
                ownCombatChanged++;
            } else {
                opponentCombatChanged++;
            }
            addExample(newState.name + " combat state changed");
        }
    }

    private void addExample(String example) {
        if (examples.size() < MAX_EXAMPLES) {
            examples.add(example);
        }
    }

    public boolean hasImpact() {
        return playerLifeDelta != 0
                || opponentsLifeDelta != 0
                || ownEntered != 0
                || opponentEntered != 0
                || ownLeft != 0
                || opponentLeft != 0
                || ownCreaturesLeft != 0
                || opponentCreaturesLeft != 0
                || ownPowerToughnessChanged != 0
                || opponentPowerToughnessChanged != 0
                || ownAbilitiesChanged != 0
                || opponentAbilitiesChanged != 0
                || ownCountersChanged != 0
                || opponentCountersChanged != 0
                || ownDamageChanged != 0
                || opponentDamageChanged != 0
                || ownTappedChanged != 0
                || opponentTappedChanged != 0
                || ownCombatChanged != 0
                || opponentCombatChanged != 0;
    }

    public int getPlayerLifeDelta() {
        return playerLifeDelta;
    }

    public int getOpponentsLifeDelta() {
        return opponentsLifeDelta;
    }

    public int getOwnEntered() {
        return ownEntered;
    }

    public int getOpponentEntered() {
        return opponentEntered;
    }

    public int getOwnLeft() {
        return ownLeft;
    }

    public int getOpponentLeft() {
        return opponentLeft;
    }

    public int getOwnCreaturesLeft() {
        return ownCreaturesLeft;
    }

    public int getOpponentCreaturesLeft() {
        return opponentCreaturesLeft;
    }

    public int getOwnPowerToughnessChanged() {
        return ownPowerToughnessChanged;
    }

    public int getOpponentPowerToughnessChanged() {
        return opponentPowerToughnessChanged;
    }

    public int getOwnAbilitiesChanged() {
        return ownAbilitiesChanged;
    }

    public int getOpponentAbilitiesChanged() {
        return opponentAbilitiesChanged;
    }

    public int getOwnCountersChanged() {
        return ownCountersChanged;
    }

    public int getOpponentCountersChanged() {
        return opponentCountersChanged;
    }

    public int getOwnDamageChanged() {
        return ownDamageChanged;
    }

    public int getOpponentDamageChanged() {
        return opponentDamageChanged;
    }

    public int getOwnTappedChanged() {
        return ownTappedChanged;
    }

    public int getOpponentTappedChanged() {
        return opponentTappedChanged;
    }

    public int getOwnCombatChanged() {
        return ownCombatChanged;
    }

    public int getOpponentCombatChanged() {
        return opponentCombatChanged;
    }

    public List<String> getExamples() {
        return examples;
    }

    private static final class PermanentState {
        private final UUID controllerId;
        private final String name;
        private final boolean creature;
        private final int power;
        private final int toughness;
        private final int damage;
        private final boolean tapped;
        private final boolean attacking;
        private final int blocking;
        private final String counters;
        private final String abilities;

        private PermanentState(Permanent permanent, Game game) {
            this.controllerId = permanent.getControllerId();
            this.name = permanent.getName();
            this.creature = permanent.isCreature(game);
            this.power = creature ? permanent.getPower().getValue() : 0;
            this.toughness = creature ? permanent.getToughness().getValue() : 0;
            this.damage = permanent.getDamage();
            this.tapped = permanent.isTapped();
            this.attacking = permanent.isAttacking();
            this.blocking = permanent.getBlocking();
            this.counters = permanent.getCounters(game).entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey())
                    .map(entry -> entry.getKey() + "=" + entry.getValue().getCount())
                    .collect(Collectors.joining(","));
            this.abilities = permanent.getAbilities(game)
                    .stream()
                    .map(Ability::getRule)
                    .filter(Objects::nonNull)
                    .sorted()
                    .collect(Collectors.joining(" | "));
        }

        private boolean isControlledBy(UUID playerId) {
            return controllerId != null && controllerId.equals(playerId);
        }
    }
}
