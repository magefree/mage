package mage.collectors.services;

import java.util.*;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Abilities;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectsList;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.command.CommandObject;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 * Helper class to prepare json diff between two game states.
 * Warning, it's for human and AI tools only and contains only basic info about game state changes
 *
 * TODO: known miss use cases
 * - mana abilities
 * - mana payment from mana pool
 * - play lands
 * - combat damage
 * - zone change and zone replacement effects like exile instead put to graveyard after combat or other non stack events
 * - gain abilities to non-battlefield objects (cards in hand/library/etc, spells on stack)
 *
 * @author JayDi85
 */
public class GameStateDiffBuilder {

    private static final Logger logger = Logger.getLogger(GameStateDiffBuilder.class);

    // null fields required for AI's duckdb and other tools
    private static final Gson gson = new GsonBuilder().serializeNulls().create();

    /**
     * @param prevGame game state snapshot before resolve
     * @param game     game state after resolve
     * @param applied  return value of the resolve() call itself - WARNING, unreliable,
     *                 some effects return false even after successfully changing state
     *                 (copy-paste mistakes)
     */
    public static String buildDiffJson(Game prevGame, Game game, boolean applied) {
        StackObject top = prevGame.getStack().getFirstOrNull();
        if (top == null) {
            logger.error("buildDiffJson: something wrong, top stack object is null, gameId=" + game.getId(), new Throwable());
            return "{}";
        }

        JsonObject root = new JsonObject();
        JsonArray playersDiff = new JsonArray();
        JsonArray zonesDiff = new JsonArray();
        JsonArray permanentsDiff = new JsonArray();
        boolean anyChanged = false;

        // 1 of 4 - game and resolving object info
        root.addProperty("gameId", game.getId().toString());
        root.addProperty("turn", game.getState().getTurnNum());
        root.addProperty("step", game.getState().getTurnStepType() != null ? game.getState().getTurnStepType().toString() : null);
        root.addProperty("applied", applied);
        root.addProperty("timestampMs", System.currentTimeMillis());

        // extra info for unit tests
        root.addProperty("testClassName", findCurrentTestClassName(game.getPlayers().values().iterator().next()));

        // game cycle counter - increments every time applyEffects() is called - can be used as game state ref id
        JsonObject counterField = new JsonObject();
        counterField.addProperty("before", prevGame.getState().getApplyEffectsCounter());
        counterField.addProperty("after", game.getState().getApplyEffectsCounter());
        root.add("applyEffectsCounter", counterField);

        root.addProperty("sourceId", top.getSourceId() != null ? top.getSourceId().toString() : null);

        // ability's effects list
        root.add("effectClasses", getEffectClassesJson(top));

        root.add("resolvedObject", stackObjectToJson(top, prevGame));

        // 2 of 4 - player changes (players and their controlled objects)
        for (Player player : game.getState().getPlayers().values()) {
            UUID playerId = player.getId();
            Player prevPlayer = prevGame.getState().getPlayers().get(playerId);
            if (prevPlayer == null) {
                continue;
            }

            // player changes (life, counters like energy/experience/poison, etc)
            anyChanged |= addPlayerDiff(playersDiff, player, prevPlayer);

            // zone changes
            anyChanged |= addZoneDiff(zonesDiff, "HAND", playerId,
                    prevPlayer.getHand().getCards(prevGame), player.getHand().getCards(game), prevGame, game);
            anyChanged |= addZoneDiff(zonesDiff, "LIBRARY", playerId,
                    prevPlayer.getLibrary().getCards(prevGame), player.getLibrary().getCards(game), prevGame, game);
            anyChanged |= addZoneDiff(zonesDiff, "GRAVEYARD", playerId,
                    prevPlayer.getGraveyard().getCards(prevGame), player.getGraveyard().getCards(game), prevGame, game);
            anyChanged |= addZoneDiff(zonesDiff, "EXILE", playerId,
                    getExileCardsForOwner(prevGame, playerId), getExileCardsForOwner(game, playerId), prevGame, game);
            anyChanged |= addZoneDiff(zonesDiff, "BATTLEFIELD", playerId,
                    getBattlefieldPermanentsForController(prevGame, playerId),
                    getBattlefieldPermanentsForController(game, playerId), prevGame, game);
            anyChanged |= addZoneDiff(zonesDiff, "COMMAND", playerId,
                    getCommandObjectsForController(prevGame, playerId),
                    getCommandObjectsForController(game, playerId), prevGame, game);
        }

        // 3 of 4 - other game state changes

        // stack changes (ignore resolving object for clean diff)
        JsonObject stackDiff = buildStackDiff(prevGame, game, top.getId());
        if (stackDiff != null) {
            zonesDiff.add(stackDiff);
            anyChanged = true;
        }

        // permanents changes (P/T, counters, tapped, and other fields)
        // before:null - it's a new permanent
        anyChanged |= addPermanentsDiff(permanentsDiff, prevGame, game);

        // layer effects changes (added or removed continuous effects)
        anyChanged |= addLayerEffectsDiff(root, prevGame, game);

        // triggered abilities changes (registered/removed - both normal and delayed)
        anyChanged |= addTriggeredAbilitiesDiff(root, prevGame, game);
        anyChanged |= addDelayedTriggeredAbilitiesDiff(root, prevGame, game);

        // 4 of 4 - final result and changed flag
        root.addProperty("changed", anyChanged);
        root.add("players", playersDiff);
        root.add("zones", zonesDiff);
        root.add("permanents", permanentsDiff);

        return gson.toJson(root);
    }

    private static String findCurrentTestClassName(Player player) {
        // optional info about test file for faster analysis in unit tests
        if (!player.isTestMode()) {
            return null;
        }
        String result = null;
        for (StackTraceElement e : Thread.currentThread().getStackTrace()) {
            String className = e.getClassName();
            if (className.startsWith("org.mage.test.")) {
                result = className.substring(className.lastIndexOf('.') + 1) + "." + e.getMethodName();
            }
        }
        return result;
    }

    private static JsonArray getEffectClassesJson(StackObject so) {
        JsonArray arr = new JsonArray();
        Ability ability = getStackObjectAbility(so);
        if (ability == null) {
            return arr;
        }
        for (Effect effect : ability.getEffects()) {
            arr.add(effect.getClass().getSimpleName());
        }
        return arr;
    }

    private static Ability getStackObjectAbility(StackObject so) {
        if (so instanceof Spell) {
            return ((Spell) so).getSpellAbility();
        } else if (so instanceof StackAbility) {
            return (Ability) so;
        }
        return null;
    }

    private static boolean addPlayerDiff(JsonArray target, Player player, Player prevPlayer) {
        JsonObject playerDiff = new JsonObject();
        boolean playerChanged = addIfChanged(playerDiff, "life", prevPlayer.getLife(), player.getLife());
        playerChanged |= addPlayerCountersIfChanged(playerDiff, prevPlayer, player);

        if (playerChanged) {
            playerDiff.addProperty("playerId", player.getId().toString());
            playerDiff.addProperty("playerName", player.getName());
            target.add(playerDiff);
        }
        return playerChanged;
    }

    private static boolean addPlayerCountersIfChanged(JsonObject target, Player prevPlayer, Player player) {
        // player-scope counters (energy, experience, poison, etc)
        Map<String, Integer> beforeCounters = countersToMap(prevPlayer.getCountersAsCopy());
        Map<String, Integer> afterCounters = countersToMap(player.getCountersAsCopy());

        if (beforeCounters.equals(afterCounters)) {
            return false;
        }

        addMapField(target, "counters", beforeCounters, afterCounters);
        return true;
    }

    private static boolean addZoneDiff(JsonArray target, String zoneName, UUID playerId,
                                        Collection<? extends MageObject> beforeObjects, Collection<? extends MageObject> afterObjects,
                                        Game prevGame, Game game) {
        // changed objects list (moves between zones), also store zone change counter (zcc) to detect "left and came back" cases
        // support: cards, permanents, and command objects
        Map<UUID, Integer> beforeZcc = new HashMap<>();
        for (MageObject obj : beforeObjects) {
            beforeZcc.put(obj.getId(), obj.getZoneChangeCounter(prevGame));
        }

        Map<UUID, Integer> afterZcc = new HashMap<>();
        for (MageObject obj : afterObjects) {
            afterZcc.put(obj.getId(), obj.getZoneChangeCounter(game));
        }

        Set<UUID> removedIds = new HashSet<>();
        for (Map.Entry<UUID, Integer> entry : beforeZcc.entrySet()) {
            Integer afterValue = afterZcc.get(entry.getKey());
            if (afterValue == null || !afterValue.equals(entry.getValue())) {
                removedIds.add(entry.getKey());
            }
        }

        Set<UUID> addedIds = new HashSet<>();
        for (Map.Entry<UUID, Integer> entry : afterZcc.entrySet()) {
            Integer beforeValue = beforeZcc.get(entry.getKey());
            if (beforeValue == null || !beforeValue.equals(entry.getValue())) {
                addedIds.add(entry.getKey());
            }
        }

        if (removedIds.isEmpty() && addedIds.isEmpty()) {
            return false;
        }

        JsonObject zoneDiff = new JsonObject();
        zoneDiff.addProperty("zone", zoneName);
        zoneDiff.addProperty("playerId", playerId.toString());
        if (!removedIds.isEmpty()) {
            zoneDiff.add("removed", objectRefsToJson(removedIds, prevGame));
        }
        if (!addedIds.isEmpty()) {
            zoneDiff.add("added", objectRefsToJson(addedIds, game));
        }
        target.add(zoneDiff);
        return true;
    }

    private static List<Permanent> getBattlefieldPermanentsForController(Game game, UUID playerId) {
        return game.getBattlefield().getAllActivePermanents(playerId);
    }

    private static List<CommandObject> getCommandObjectsForController(Game game, UUID playerId) {
        List<CommandObject> result = new ArrayList<>();
        for (CommandObject commandObject : game.getState().getCommand()) {
            if (playerId.equals(commandObject.getControllerId())) {
                result.add(commandObject);
            }
        }
        return result;
    }

    private static boolean addPermanentsDiff(JsonArray target, Game prevGame, Game game) {
        // permanent-scope changes in many fields like P/T, counters, tapped, transformed, and other
        // newly added permanents gets before:null for all fields, removed permanents are not reported (they're in zone changes)
        boolean anyChanged = false;
        for (Permanent p : game.getBattlefield().getAllActivePermanents()) {
            Permanent prevP = prevGame.getBattlefield().getPermanent(p.getId());

            JsonObject permDiff = new JsonObject();
            boolean changed;

            if (prevP == null) {
                // new permanent - always report its current state, before is null everywhere
                addNullBeforeField(permDiff, "power", p.getPower().getValue());
                addNullBeforeField(permDiff, "toughness", p.getToughness().getValue());
                addMapField(permDiff, "counters", null, countersToMap(p.getCounters(game)));
                addNullBeforeField(permDiff, "tapped", p.isTapped());
                addNullBeforeField(permDiff, "transformed", p.isTransformed());
                addNullBeforeField(permDiff, "faceDown", p.isFaceDown(game));
                addNullBeforeUuidField(permDiff, "controllerId", p.getControllerId());
                addNullBeforeField(permDiff, "damage", p.getDamage());
                addNullBeforeUuidField(permDiff, "attachedTo", p.getAttachedTo());
                addMapField(permDiff, "abilities", null, abilitiesToKeyCountMap(p.getAbilities(game)));
                changed = true;
            } else {
                changed = addIfChanged(permDiff, "power", prevP.getPower().getValue(), p.getPower().getValue());
                changed |= addIfChanged(permDiff, "toughness", prevP.getToughness().getValue(), p.getToughness().getValue());
                changed |= addCountersIfChanged(permDiff, prevP, p, prevGame, game);
                changed |= addIfChanged(permDiff, "tapped", prevP.isTapped(), p.isTapped());
                changed |= addIfChanged(permDiff, "transformed", prevP.isTransformed(), p.isTransformed());
                changed |= addIfChanged(permDiff, "faceDown", prevP.isFaceDown(prevGame), p.isFaceDown(game));
                changed |= addIfChangedUuid(permDiff, "controllerId", prevP.getControllerId(), p.getControllerId());
                changed |= addIfChanged(permDiff, "damage", prevP.getDamage(), p.getDamage());
                changed |= addIfChangedUuid(permDiff, "attachedTo", prevP.getAttachedTo(), p.getAttachedTo());
                changed |= addAbilitiesIfChanged(permDiff, prevP, p, prevGame, game);
            }

            if (changed) {
                permDiff.addProperty("id", p.getId().toString());
                permDiff.addProperty("zcc", p.getZoneChangeCounter(game));
                permDiff.addProperty("objectName", p.getName());
                target.add(permDiff);
                anyChanged = true;
            }
        }
        return anyChanged;
    }

    private static boolean addAbilitiesIfChanged(JsonObject target, Permanent prevP, Permanent p, Game prevGame, Game game) {
        // permanent-scope changes in abilities list
        // store class name + rule text as key co detect multiple instances of the same ability (shield-like effects)
        Map<String, Integer> beforeCounts = abilitiesToKeyCountMap(prevP.getAbilities(prevGame));
        Map<String, Integer> afterCounts = abilitiesToKeyCountMap(p.getAbilities(game));

        if (beforeCounts.equals(afterCounts)) {
            return false;
        }

        addMapField(target, "abilities", beforeCounts, afterCounts);
        return true;
    }

    private static Map<String, Integer> abilitiesToKeyCountMap(Iterable<? extends Ability> abilities) {
        Map<String, Integer> result = new TreeMap<>(); // sorted, so equals()/output is stable
        for (Ability a : abilities) {
            String key = a.getClass().getSimpleName() + "::" + a.getRule();
            result.merge(key, 1, Integer::sum);
        }
        return result;
    }

    private static boolean addLayerEffectsDiff(JsonObject root, Game prevGame, Game game) {
        // game-scope changes in continuous effects list
        Map<String, Integer> beforeCounts = layerEffectsToKeyCountMap(prevGame);
        Map<String, Integer> afterCounts = layerEffectsToKeyCountMap(game);

        if (beforeCounts.equals(afterCounts)) {
            return false;
        }

        addMapField(root, "layerEffects", beforeCounts, afterCounts);
        return true;
    }

    private static Map<String, Integer> layerEffectsToKeyCountMap(Game game) {
        Map<String, Integer> result = new TreeMap<>(); // sorted map for stable equals()/output
        for (ContinuousEffectsList<?> layer : game.getState().getContinuousEffects().allEffectsLists) {
            for (ContinuousEffect effect : layer) {
                String text = getContinuousEffectText(layer, effect);
                String key = effect.getClass().getSimpleName() + "::" + text;
                result.merge(key, 1, Integer::sum);
            }
        }
        return result;
    }

    private static String getContinuousEffectText(ContinuousEffectsList<?> layer, ContinuousEffect effect) {
        // text generation require a selected mode info
        // TODO: add multi modes support here
        Set<Ability> abilities = layer.getAbility(effect.getId());
        if (abilities == null || abilities.isEmpty()) {
            return "";
        }
        Ability ability = abilities.iterator().next();
        if (ability == null || ability.getModes() == null || ability.getModes().getMode() == null) {
            return "";
        }
        try {
            String text = effect.getText(ability.getModes().getMode());
            return text != null ? text : "";
        } catch (Exception e) {
            return "";
        }
    }

    private static boolean addTriggeredAbilitiesDiff(JsonObject root, Game prevGame, Game game) {
        // game-scope changes in triggered abilities
        Map<String, Integer> beforeCounts = abilitiesToKeyCountMap(prevGame.getState().getTriggers().values());
        Map<String, Integer> afterCounts = abilitiesToKeyCountMap(game.getState().getTriggers().values());

        if (beforeCounts.equals(afterCounts)) {
            return false;
        }

        addMapField(root, "triggeredAbilities", beforeCounts, afterCounts);
        return true;
    }

    private static boolean addDelayedTriggeredAbilitiesDiff(JsonObject root, Game prevGame, Game game) {
        // game-scope changes in delayed triggered abilities
        Map<String, Integer> beforeCounts = abilitiesToKeyCountMap(prevGame.getState().getDelayed());
        Map<String, Integer> afterCounts = abilitiesToKeyCountMap(game.getState().getDelayed());

        if (beforeCounts.equals(afterCounts)) {
            return false;
        }

        addMapField(root, "delayedTriggeredAbilities", beforeCounts, afterCounts);
        return true;
    }

    private static void addNullBeforeField(JsonObject target, String fieldName, int after) {
        JsonObject field = new JsonObject();
        field.add("before", JsonNull.INSTANCE);
        field.addProperty("after", after);
        target.add(fieldName, field);
    }

    private static void addNullBeforeField(JsonObject target, String fieldName, boolean after) {
        JsonObject field = new JsonObject();
        field.add("before", JsonNull.INSTANCE);
        field.addProperty("after", after);
        target.add(fieldName, field);
    }

    private static void addNullBeforeUuidField(JsonObject target, String fieldName, UUID after) {
        JsonObject field = new JsonObject();
        field.add("before", JsonNull.INSTANCE);
        field.addProperty("after", after != null ? after.toString() : null);
        target.add(fieldName, field);
    }

    private static boolean addCountersIfChanged(JsonObject target, Permanent prevP, Permanent p, Game prevGame, Game game) {
        Map<String, Integer> beforeCounters = countersToMap(prevP.getCounters(prevGame));
        Map<String, Integer> afterCounters = countersToMap(p.getCounters(game));

        if (beforeCounters.equals(afterCounters)) {
            return false;
        }

        addMapField(target, "counters", beforeCounters, afterCounters);
        return true;
    }

    private static void addMapField(JsonObject target, String fieldName, Map<String, Integer> before, Map<String, Integer> after) {
        JsonObject field = new JsonObject();
        field.add("before", before != null ? buildMapToJson(before) : JsonNull.INSTANCE);
        field.add("after", buildMapToJson(after));
        target.add(fieldName, field);
    }

    private static Map<String, Integer> countersToMap(Counters counters) {
        Map<String, Integer> result = new TreeMap<>(); // sorted, so equals()/output is stable
        for (String name : counters.keySet()) {
            result.put(name, counters.getCount(name));
        }
        return result;
    }

    private static JsonObject buildMapToJson(Map<String, Integer> counters) {
        JsonObject obj = new JsonObject();
        for (Map.Entry<String, Integer> entry : counters.entrySet()) {
            obj.addProperty(entry.getKey(), entry.getValue());
        }
        return obj;
    }

    private static JsonObject buildStackDiff(Game prevGame, Game game, UUID topId) {
        Set<UUID> beforeIds = new HashSet<>();
        for (StackObject so : prevGame.getState().getStack()) {
            beforeIds.add(so.getId());
        }

        Set<UUID> afterIds = new HashSet<>();
        for (StackObject so : game.getState().getStack()) {
            afterIds.add(so.getId());
        }

        Set<UUID> removedIds = new HashSet<>(beforeIds);
        removedIds.removeAll(afterIds);
        removedIds.remove(topId); // the resolved object's own removal is not a "change"

        Set<UUID> addedIds = new HashSet<>(afterIds);
        addedIds.removeAll(beforeIds);

        if (removedIds.isEmpty() && addedIds.isEmpty()) {
            return null;
        }

        JsonObject stackDiff = new JsonObject();
        stackDiff.addProperty("zone", "STACK");
        if (!removedIds.isEmpty()) {
            stackDiff.add("removed", stackRefsToJson(removedIds, prevGame));
        }
        if (!addedIds.isEmpty()) {
            stackDiff.add("added", stackRefsToJson(addedIds, game));
        }
        return stackDiff;
    }

    private static JsonArray stackRefsToJson(Set<UUID> stackObjectIds, Game game) {
        JsonArray arr = new JsonArray();
        for (UUID id : stackObjectIds) {
            StackObject so = findStackObject(game, id);
            arr.add(stackObjectToJson(so, game));
        }
        return arr;
    }

    private static JsonObject stackObjectToJson(StackObject so, Game game) {
        JsonObject ref = new JsonObject();
        if (so == null) {
            ref.addProperty("id", (String) null);
            ref.addProperty("zcc", (Integer) null);
            ref.addProperty("type", (String) null);
            ref.addProperty("name", (String) null);
            ref.addProperty("rule", (String) null);
            return ref;
        }
        ref.addProperty("id", so.getId().toString());
        // only Spell is a Card and has a zone change counter, StackAbility isn't tied to one
        ref.addProperty("zcc", so instanceof Spell ? ((Spell) so).getZoneChangeCounter(game) : null);
        ref.addProperty("type", so instanceof Spell ? "SPELL" : "ABILITY");
        ref.addProperty("name", so.getName());
        ref.addProperty("rule", getStackObjectRule(so, game));
        return ref;
    }

    private static String getStackObjectRule(StackObject so, Game game) {
        if (so instanceof Spell) {
            List<String> rules = ((Spell) so).getRules(game);
            return rules != null ? String.join(" @ ", rules) : null;
        } else if (so instanceof StackAbility) {
            return ((StackAbility) so).getRule();
        }
        return null;
    }

    private static StackObject findStackObject(Game game, UUID id) {
        for (StackObject so : game.getState().getStack()) {
            if (so.getId().equals(id)) {
                return so;
            }
        }
        return null;
    }

    private static List<Card> getExileCardsForOwner(Game game, UUID playerId) {
        return game.getExile().getCardsOwned(game, playerId);
    }

    private static JsonArray objectRefsToJson(Set<UUID> objectIds, Game game) {
        // object info for zone moves history
        // use class name to find real class under changed name like face down, mutated, copied, etc
        JsonArray arr = new JsonArray();
        for (UUID objectId : objectIds) {
            MageObject obj = game.getObject(objectId);
            JsonObject ref = new JsonObject();
            ref.addProperty("id", objectId.toString());
            ref.addProperty("zcc", obj != null ? obj.getZoneChangeCounter(game) : null);
            ref.addProperty("objectName", obj != null ? obj.getName() : null);
            ref.addProperty("type", getObjectType(obj));
            ref.addProperty("class", obj != null ? obj.getClass().getSimpleName() : null);
            arr.add(ref);
        }
        return arr;
    }

    private static String getObjectType(MageObject obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Permanent) {
            return "PERMANENT";
        } else if (obj instanceof CommandObject) {
            return "COMMAND_OBJECT";
        } else if (obj instanceof Card) {
            return "CARD";
        }
        return obj.getClass().getSimpleName();
    }

    private static boolean addIfChanged(JsonObject target, String fieldName, int before, int after) {
        if (before == after) {
            return false;
        }
        JsonObject field = new JsonObject();
        field.addProperty("before", before);
        field.addProperty("after", after);
        target.add(fieldName, field);
        return true;
    }

    private static boolean addIfChanged(JsonObject target, String fieldName, boolean before, boolean after) {
        if (before == after) {
            return false;
        }
        JsonObject field = new JsonObject();
        field.addProperty("before", before);
        field.addProperty("after", after);
        target.add(fieldName, field);
        return true;
    }

    private static boolean addIfChangedUuid(JsonObject target, String fieldName, UUID before, UUID after) {
        if (Objects.equals(before, after)) {
            return false;
        }
        JsonObject field = new JsonObject();
        field.addProperty("before", before != null ? before.toString() : null);
        field.addProperty("after", after != null ? after.toString() : null);
        target.add(fieldName, field);
        return true;
    }
}