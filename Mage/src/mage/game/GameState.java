/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.DelayedTriggeredAbilities;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.MageSingleton;
import mage.abilities.Mode;
import mage.abilities.SpecialActions;
import mage.abilities.StaticAbility;
import mage.abilities.TriggeredAbilities;
import mage.abilities.TriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffects;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.cards.SplitCard;
import mage.choices.Choice;
import mage.constants.Zone;
import mage.game.combat.Combat;
import mage.game.combat.CombatGroup;
import mage.game.command.Command;
import mage.game.command.CommandObject;
import mage.game.events.GameEvent;
import mage.game.permanent.Battlefield;
import mage.game.permanent.Permanent;
import mage.game.stack.SpellStack;
import mage.game.stack.StackObject;
import mage.game.turn.Turn;
import mage.game.turn.TurnMods;
import mage.players.Player;
import mage.players.PlayerList;
import mage.players.Players;
import mage.target.Target;
import mage.util.Copyable;
import mage.util.ThreadLocalStringBuilder;
import mage.watchers.Watcher;
import mage.watchers.Watchers;

/**
 *
 * @author BetaSteward_at_googlemail.com
 *
 * since at any time the game state may be copied and restored you cannot rely
 * on any object maintaining it's instance it then becomes necessary to only
 * refer to objects by their ids since these will always remain constant
 * throughout its lifetime
 *
 */
public class GameState implements Serializable, Copyable<GameState> {

    private static final transient ThreadLocalStringBuilder threadLocalBuilder = new ThreadLocalStringBuilder(1024);

    private final Players players;
    private final PlayerList playerList;
    private UUID choosingPlayerId; // player that makes a choice at game start

    // revealed cards <Name, <Cards>>, will be reset if all players pass priority
    private final Revealed revealed;
    private final Map<UUID, LookedAt> lookedAt = new HashMap<>();

    private DelayedTriggeredAbilities delayed;
    private SpecialActions specialActions;
    private Watchers watchers;
    private Turn turn;
    private TurnMods turnMods;
    private UUID activePlayerId; // playerId which turn it is
    private UUID priorityPlayerId; // player that has currently priority
    private SpellStack stack;
    private Command command;
    private Exile exile;
    private Battlefield battlefield;
    private int turnNum = 1;
    private int stepNum = 0;
    private UUID turnId = null;
    private boolean extraTurn = false;
    private boolean legendaryRuleActive = true;
    private boolean gameOver;
    private boolean paused;
    private ContinuousEffects effects;
    private TriggeredAbilities triggers;
    private List<TriggeredAbility> triggered = new ArrayList<>();
    private Combat combat;
    private Map<String, Object> values = new HashMap<>();
    private Map<UUID, Zone> zones = new HashMap<>();
    private List<GameEvent> simultaneousEvents = new ArrayList<>();
    private Map<UUID, CardState> cardState = new HashMap<>();
    private Map<UUID, CardAttribute> cardAttribute = new HashMap<>();
    private Map<UUID, Integer> zoneChangeCounter = new HashMap<>();
    private Map<UUID, Card> copiedCards = new HashMap<>();
    private int permanentOrderNumber;

    public GameState() {
        players = new Players();
        playerList = new PlayerList();
        turn = new Turn();
        stack = new SpellStack();
        command = new Command();
        exile = new Exile();
        revealed = new Revealed();
        battlefield = new Battlefield();
        effects = new ContinuousEffects();
        triggers = new TriggeredAbilities();
        delayed = new DelayedTriggeredAbilities();
        specialActions = new SpecialActions();
        combat = new Combat();
        turnMods = new TurnMods();
        watchers = new Watchers();
    }

    public GameState(final GameState state) {
        this.players = state.players.copy();
        this.playerList = state.playerList.copy();
        this.choosingPlayerId = state.choosingPlayerId;
        this.revealed = state.revealed.copy();
        this.lookedAt.putAll(state.lookedAt);
        this.gameOver = state.gameOver;
        this.paused = state.paused;

        this.activePlayerId = state.activePlayerId;
        this.priorityPlayerId = state.priorityPlayerId;
        this.turn = state.turn.copy();

        this.stack = state.stack.copy();
        this.command = state.command.copy();
        this.exile = state.exile.copy();
        this.battlefield = state.battlefield.copy();
        this.turnNum = state.turnNum;
        this.stepNum = state.stepNum;
        this.extraTurn = state.extraTurn;
        this.legendaryRuleActive = state.legendaryRuleActive;
        this.effects = state.effects.copy();
        for (TriggeredAbility trigger : state.triggered) {
            this.triggered.add(trigger.copy());
        }
        this.triggers = state.triggers.copy();
        this.delayed = state.delayed.copy();
        this.specialActions = state.specialActions.copy();
        this.combat = state.combat.copy();
        this.turnMods = state.turnMods.copy();
        this.watchers = state.watchers.copy();
        for (Map.Entry<String, Object> entry : state.values.entrySet()) {
            this.values.put(entry.getKey(), entry.getValue());
        }
        this.zones.putAll(state.zones);
        this.simultaneousEvents.addAll(state.simultaneousEvents);
        for (Map.Entry<UUID, CardState> entry : state.cardState.entrySet()) {
            cardState.put(entry.getKey(), entry.getValue().copy());
        }
        for (Map.Entry<UUID, CardAttribute> entry : state.cardAttribute.entrySet()) {
            cardAttribute.put(entry.getKey(), entry.getValue().copy());
        }
        this.zoneChangeCounter.putAll(state.zoneChangeCounter);
        this.copiedCards.putAll(state.copiedCards);
        this.permanentOrderNumber = state.permanentOrderNumber;
    }

    public void restoreForRollBack(GameState state) {
        restore(state);
        this.turn = state.turn;
    }

    public void restore(GameState state) {
        this.activePlayerId = state.activePlayerId;
        this.playerList.setCurrent(state.activePlayerId);
        this.priorityPlayerId = state.priorityPlayerId;
        this.stack = state.stack;
        this.command = state.command;
        this.exile = state.exile;
        this.battlefield = state.battlefield;
        this.turnNum = state.turnNum;
        this.stepNum = state.stepNum;
        this.extraTurn = state.extraTurn;
        this.legendaryRuleActive = state.legendaryRuleActive;
        this.effects = state.effects;
        this.triggered = state.triggered;
        this.triggers = state.triggers;
        this.delayed = state.delayed;
        this.specialActions = state.specialActions;
        this.combat = state.combat;
        this.turnMods = state.turnMods;
        this.watchers = state.watchers;
        this.values = state.values;
        for (Player copyPlayer : state.players.values()) {
            Player origPlayer = players.get(copyPlayer.getId());
            origPlayer.restore(copyPlayer);
        }
        this.zones = state.zones;
        this.simultaneousEvents = state.simultaneousEvents;
        this.cardState = state.cardState;
        this.cardAttribute = state.cardAttribute;
        this.zoneChangeCounter = state.zoneChangeCounter;
        this.copiedCards = state.copiedCards;
        this.permanentOrderNumber = state.permanentOrderNumber;
    }

    @Override
    public GameState copy() {
        return new GameState(this);
    }

    public void addPlayer(Player player) {
        players.put(player.getId(), player);
        playerList.add(player.getId());
    }

    public String getValue(boolean useHidden) {
        StringBuilder sb = threadLocalBuilder.get();

        sb.append(turn.getValue(turnNum));
        sb.append(activePlayerId).append(priorityPlayerId);

        for (Player player : players.values()) {
            sb.append("player").append(player.getLife()).append("hand");
            if (useHidden) {
                sb.append(player.getHand());
            } else {
                sb.append(player.getHand().size());
            }
            sb.append("library").append(player.getLibrary().size()).append("graveyard").append(player.getGraveyard());
        }

        sb.append("permanents");
        for (Permanent permanent : battlefield.getAllPermanents()) {
            sb.append(permanent.getValue());
        }

        sb.append("spells");
        for (StackObject spell : stack) {
            sb.append(spell.getControllerId()).append(spell.getName());
        }

        for (ExileZone zone : exile.getExileZones()) {
            sb.append("exile").append(zone.getName()).append(zone);
        }

        sb.append("combat");
        for (CombatGroup group : combat.getGroups()) {
            sb.append(group.getDefenderId()).append(group.getAttackers()).append(group.getBlockers());
        }

        return sb.toString();
    }

    public String getValue(boolean useHidden, Game game) {
        StringBuilder sb = threadLocalBuilder.get();

        sb.append(turn.getValue(turnNum));
        sb.append(activePlayerId).append(priorityPlayerId);

        for (Player player : players.values()) {
            sb.append("player").append(player.isPassed()).append(player.getLife()).append("hand");
            if (useHidden) {
                sb.append(player.getHand().getValue(game));
            } else {
                sb.append(player.getHand().size());
            }
            sb.append("library").append(player.getLibrary().size());
            sb.append("graveyard");
            sb.append(player.getGraveyard().getValue(game));
        }

        sb.append("permanents");
        List<String> perms = new ArrayList<>();
        for (Permanent permanent : battlefield.getAllPermanents()) {
            perms.add(permanent.getValue());
        }
        Collections.sort(perms);
        sb.append(perms);

        sb.append("spells");
        for (StackObject spell : stack) {
            sb.append(spell.getControllerId()).append(spell.getName());
            sb.append(spell.getStackAbility().toString());
            for (Mode mode : spell.getStackAbility().getModes().values()) {
                if (!mode.getTargets().isEmpty()) {
                    sb.append("targets");
                    for (Target target : mode.getTargets()) {
                        sb.append(target.getTargets());
                    }
                }
                if (!mode.getChoices().isEmpty()) {
                    sb.append("choices");
                    for (Choice choice : mode.getChoices()) {
                        sb.append(choice.getChoice());
                    }
                }
            }
        }

        for (ExileZone zone : exile.getExileZones()) {
            sb.append("exile").append(zone.getName()).append(zone.getValue(game));
        }

        sb.append("combat");
        for (CombatGroup group : combat.getGroups()) {
            sb.append(group.getDefenderId()).append(group.getAttackers()).append(group.getBlockers());
        }

        return sb.toString();
    }

    public String getValue(Game game, UUID playerId) {
        StringBuilder sb = threadLocalBuilder.get();

        sb.append(turn.getValue(turnNum));
        sb.append(activePlayerId).append(priorityPlayerId);

        for (Player player : players.values()) {
            sb.append("player").append(player.isPassed()).append(player.getLife()).append("hand");
            if (playerId == player.getId()) {
                sb.append(player.getHand().getValue(game));
            } else {
                sb.append(player.getHand().size());
            }
            sb.append("library").append(player.getLibrary().size());
            sb.append("graveyard");
            sb.append(player.getGraveyard().getValue(game));
        }

        sb.append("permanents");
        List<String> perms = new ArrayList<>();
        for (Permanent permanent : battlefield.getAllPermanents()) {
            perms.add(permanent.getValue());
        }
        Collections.sort(perms);
        sb.append(perms);

        sb.append("spells");
        for (StackObject spell : stack) {
            sb.append(spell.getControllerId()).append(spell.getName());
            sb.append(spell.getStackAbility().toString());
            for (Mode mode : spell.getStackAbility().getModes().values()) {
                if (!mode.getTargets().isEmpty()) {
                    sb.append("targets");
                    for (Target target : mode.getTargets()) {
                        sb.append(target.getTargets());
                    }
                }
                if (!mode.getChoices().isEmpty()) {
                    sb.append("choices");
                    for (Choice choice : mode.getChoices()) {
                        sb.append(choice.getChoice());
                    }
                }
            }
        }

        for (ExileZone zone : exile.getExileZones()) {
            sb.append("exile").append(zone.getName()).append(zone.getValue(game));
        }

        sb.append("combat");
        for (CombatGroup group : combat.getGroups()) {
            sb.append(group.getDefenderId()).append(group.getAttackers()).append(group.getBlockers());
        }

        return sb.toString();
    }

    public Players getPlayers() {
        return players;
    }

    public Player getPlayer(UUID playerId) {
        return players.get(playerId);
    }

    public UUID getActivePlayerId() {
        return activePlayerId;
    }

    public void setActivePlayerId(UUID activePlayerId) {
        this.activePlayerId = activePlayerId;
    }

    public UUID getPriorityPlayerId() {
        return priorityPlayerId;
    }

    public void setPriorityPlayerId(UUID priorityPlayerId) {
        this.priorityPlayerId = priorityPlayerId;
    }

    public UUID getChoosingPlayerId() {
        return choosingPlayerId;
    }

    public void setChoosingPlayerId(UUID choosingPlayerId) {
        this.choosingPlayerId = choosingPlayerId;
    }

    public Battlefield getBattlefield() {
        return this.battlefield;
    }

    public SpellStack getStack() {
        return this.stack;
    }

    public Exile getExile() {
        return exile;
    }

    public Command getCommand() {
        return command;
    }

    public Revealed getRevealed() {
        return revealed;
    }

    public LookedAt getLookedAt(UUID playerId) {
        if (lookedAt.get(playerId) == null) {
            LookedAt lookedAtCards = new LookedAt();
            lookedAt.put(playerId, lookedAtCards);
            return lookedAtCards;
        }
        return lookedAt.get(playerId);
    }

    public void clearRevealed() {
        revealed.clear();
    }

    public void clearLookedAt() {
        lookedAt.clear();
    }

    public Turn getTurn() {
        return turn;
    }

    public Combat getCombat() {
        return combat;
    }

    /**
     * Gets the game step counter. This counter isgoing one up for every played
     * step during the game.
     *
     * @return
     */
    public int getStepNum() {
        return stepNum;
    }

    public void increaseStepNum() {
        this.stepNum++;
    }

    public int getTurnNum() {
        return turnNum;
    }

    public void setTurnNum(int turnNum) {
        this.turnNum = turnNum;
    }

    public UUID getTurnId() {
        return this.turnId;
    }

    public void setTurnId(UUID turnId) {
        this.turnId = turnId;
    }

    public boolean isExtraTurn() {
        return extraTurn;
    }

    public void setExtraTurn(boolean extraTurn) {
        this.extraTurn = extraTurn;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    public TurnMods getTurnMods() {
        return this.turnMods;
    }

    public Watchers getWatchers() {
        return this.watchers;
    }

    public SpecialActions getSpecialActions() {
        return this.specialActions;
    }

    public void endGame() {
        this.gameOver = true;
    }

    public void applyEffects(Game game) {
        game.resetShortLivingLKI();
        for (Player player : players.values()) {
            player.reset();
        }
        battlefield.reset(game);
        combat.reset(game);
        this.reset();
        effects.apply(game);
        combat.checkForRemoveFromCombat(game);
    }

    // Remove End of Combat effects
    public void removeEocEffects(Game game) {
        effects.removeEndOfCombatEffects();
        delayed.removeEndOfCombatAbilities();
        applyEffects(game);
    }

    public void removeEotEffects(Game game) {
        effects.removeEndOfTurnEffects();
        delayed.removeEndOfTurnAbilities();
        applyEffects(game);
    }

    public void addEffect(ContinuousEffect effect, Ability source) {
        effects.addEffect(effect, source);
    }

    public void addEffect(ContinuousEffect effect, UUID sourceId, Ability source) {
        if (sourceId == null) {
            effects.addEffect(effect, source);
        } else {
            effects.addEffect(effect, sourceId, source);
        }
    }

//    public void addMessage(String message) {
//        this.messages.add(message);
//    }
    /**
     * Returns a list of all players of the game ignoring range or if a player
     * has lost or left the game.
     *
     * @return playerList
     */
    public PlayerList getPlayerList() {
        return playerList;
    }

    /**
     * Returns a list of all active players of the game, setting the playerId to
     * the current player of the list.
     *
     * @param playerId
     * @return playerList
     */
    public PlayerList getPlayerList(UUID playerId) {
        PlayerList newPlayerList = new PlayerList();
        for (Player player : players.values()) {
            if (!player.hasLeft() && !player.hasLost()) {
                newPlayerList.add(player.getId());
            }
        }
        newPlayerList.setCurrent(playerId);
        return newPlayerList;
    }

    /**
     * Returns a list of all active players of the game in range of playerId,
     * also setting the playerId to the first/current player of the list. Also
     * returning the other players in turn order.
     *
     * @param playerId
     * @param game
     * @return playerList
     */
    public PlayerList getPlayersInRange(UUID playerId, Game game) {
        PlayerList newPlayerList = new PlayerList();
        Player currentPlayer = game.getPlayer(playerId);
        if (currentPlayer != null) {
            for (Player player : players.values()) {
                if (!player.hasLeft() && !player.hasLost() && currentPlayer.getInRange().contains(player.getId())) {
                    newPlayerList.add(player.getId());
                }
            }
            newPlayerList.setCurrent(playerId);
        }
        return newPlayerList;
    }

    public Permanent getPermanent(UUID permanentId) {
        if (permanentId != null && battlefield.containsPermanent(permanentId)) {
            Permanent permanent = battlefield.getPermanent(permanentId);
            setZone(permanent.getId(), Zone.BATTLEFIELD); // shouldn't this be set anyway? (LevelX2)
            return permanent;
        }
        return null;
    }

    public Zone getZone(UUID id) {
        if (id != null && zones.containsKey(id)) {
            return zones.get(id);
        }
        return null;
    }

    public void setZone(UUID id, Zone zone) {
        zones.put(id, zone);
    }

    public void addSimultaneousEvent(GameEvent event, Game game) {
        simultaneousEvents.add(event);
    }

    public void handleSimultaneousEvent(Game game) {
        if (!simultaneousEvents.isEmpty() && !getTurn().isEndTurnRequested()) {
            // it can happen, that the events add new simultaneous events, so copy the list before
            List<GameEvent> eventsToHandle = new ArrayList<>();
            eventsToHandle.addAll(simultaneousEvents);
            simultaneousEvents.clear();
            for (GameEvent event : eventsToHandle) {
                this.handleEvent(event, game);
            }
        }
    }

    public boolean hasSimultaneousEvents() {
        return !simultaneousEvents.isEmpty();
    }

    public void handleEvent(GameEvent event, Game game) {
        watchers.watch(event, game);
        delayed.checkTriggers(event, game);
        triggers.checkTriggers(event, game);
    }

    public boolean replaceEvent(GameEvent event, Game game) {
        if (effects.preventedByRuleModification(event, null, game, false)) {
            return true;
        }
        return effects.replaceEvent(event, game);
    }

    public void addCard(Card card) {
        setZone(card.getId(), Zone.OUTSIDE);
        for (Ability ability : card.getAbilities()) {
            addAbility(ability, card);
        }
    }

    public void removeCopiedCard(Card card) {
        if (copiedCards.containsKey(card.getId())) {
            copiedCards.remove(card.getId());
            cardState.remove(card.getId());
            zones.remove(card.getId());
            zoneChangeCounter.remove(card.getId());
        }
        // TODO Watchers?
        // TODO Abilities?
        if (card.isSplitCard()) {
            removeCopiedCard(((SplitCard) card).getLeftHalfCard());
            removeCopiedCard(((SplitCard) card).getRightHalfCard());
        }
    }

    /**
     * Used for adding abilities that exist permanent on cards/permanents and
     * are not only gained for a certain time (e.g. until end of turn).
     *
     * @param ability
     * @param attachedTo
     */
    public void addAbility(Ability ability, Card attachedTo) {
        addAbility(ability, null, attachedTo);
    }

    public void addAbility(Ability ability, MageObject attachedTo) {
        if (ability instanceof StaticAbility) {
            for (Mode mode : ability.getModes().values()) {
                for (Effect effect : mode.getEffects()) {
                    if (effect instanceof ContinuousEffect) {
                        addEffect((ContinuousEffect) effect, ability);
                    }
                }
            }
        } else if (ability instanceof TriggeredAbility) {
            this.triggers.add((TriggeredAbility) ability, attachedTo);
        }
    }

    /**
     * Abilities that are applied to other objects or applie for a certain time
     * span
     *
     * @param ability
     * @param sourceId
     * @param attachedTo
     */
    public void addAbility(Ability ability, UUID sourceId, Card attachedTo) {
        if (ability instanceof StaticAbility) {
            for (Mode mode : ability.getModes().values()) {
                for (Effect effect : mode.getEffects()) {
                    if (effect instanceof ContinuousEffect) {
                        addEffect((ContinuousEffect) effect, sourceId, ability);
                    }
                }
            }
        } else if (ability instanceof TriggeredAbility) {
            // TODO: add sources for triggers - the same way as in addEffect: sources
            this.triggers.add((TriggeredAbility) ability, sourceId, attachedTo);
        }
        List<Watcher> watcherList = new ArrayList<>(ability.getWatchers()); // Workaround to prevent ConcurrentModificationException, not clear to me why this is happening now
        for (Watcher watcher : watcherList) {
            // TODO: Check that watcher for commanderAbility (where attachedTo = null) also work correctly
            watcher.setControllerId(attachedTo == null ? ability.getControllerId() : attachedTo.getOwnerId());
            watcher.setSourceId(attachedTo == null ? ability.getSourceId() : attachedTo.getId());
            watchers.add(watcher);
        }
        for (Ability sub : ability.getSubAbilities()) {
            addAbility(sub, sourceId, attachedTo);
        }
    }

    public void addCommandObject(CommandObject commandObject) {
        getCommand().add(commandObject);
        setZone(commandObject.getId(), Zone.COMMAND);
        for (Ability ability : commandObject.getAbilities()) {
            addAbility(ability, commandObject);
        }
    }

    /**
     * Removes all waiting triggers (needed for turn end effects)
     */
    public void clearTriggeredAbilities() {
        this.triggered.clear();
    }

    public void addTriggeredAbility(TriggeredAbility ability) {
        this.triggered.add(ability);
    }

    public void removeTriggeredAbility(TriggeredAbility ability) {
        this.triggered.remove(ability);
    }

    public void addDelayedTriggeredAbility(DelayedTriggeredAbility ability) {
        this.delayed.add(ability);
    }

    public void removeDelayedTriggeredAbility(UUID abilityId) {
        for (DelayedTriggeredAbility ability : delayed) {
            if (ability.getId().equals(abilityId)) {
                delayed.remove(ability);
                break;
            }
        }
    }

    public List<TriggeredAbility> getTriggered(UUID controllerId) {
        List<TriggeredAbility> triggereds = new ArrayList<>();
        for (TriggeredAbility ability : triggered) {
            if (ability.getControllerId().equals(controllerId)) {
                triggereds.add(ability);
            }
        }
        return triggereds;
    }

    public DelayedTriggeredAbilities getDelayed() {
        return this.delayed;
    }

    public ContinuousEffects getContinuousEffects() {
        return effects;
    }

    public Object getValue(String valueId) {
        return values.get(valueId);
    }

    /**
     * Best only use immutable objects, otherwise the states/values of the
     * object may be changed by AI simulation, because the Value objects are not
     * copied as the state class is copied.
     *
     * @param valueId
     * @param value
     */
    public void setValue(String valueId, Object value) {
        values.put(valueId, value);
    }

    /**
     * Other abilities are used to implement some special kind of continuous
     * effects that give abilities to non permanents.
     *
     * Crucible of Worlds - You may play land cards from your graveyard. Past in
     * Flames - Each instant and sorcery card in your graveyard gains flashback
     * until end of turn. The flashback cost is equal to its mana cost. Varolz,
     * the Scar-Striped - Each creature card in your graveyard has scavenge. The
     * scavenge cost is equal to its mana cost.
     *
     * @param objectId
     * @param zone
     * @return
     */
    public Abilities<ActivatedAbility> getActivatedOtherAbilities(UUID objectId, Zone zone) {
        if (cardState.containsKey(objectId)) {
            return cardState.get(objectId).getAbilities().getActivatedAbilities(zone);
        }
        return null;
    }

    public Abilities<Ability> getAllOtherAbilities(UUID objectId) {
        if (cardState.containsKey(objectId)) {
            return cardState.get(objectId).getAbilities();
        }
        return null;
    }

    /**
     * Adds the ability to continuous or triggered abilities
     *
     * @param attachedTo
     * @param ability
     */
    public void addOtherAbility(Card attachedTo, Ability ability) {
        Ability newAbility;
        if (ability instanceof MageSingleton) {
            newAbility = ability;
        } else {
            newAbility = ability.copy();
        }
        newAbility.setSourceId(attachedTo.getId());
        newAbility.setControllerId(attachedTo.getOwnerId());
        if (!cardState.containsKey(attachedTo.getId())) {
            cardState.put(attachedTo.getId(), new CardState());
        }
        cardState.get(attachedTo.getId()).addAbility(newAbility);
        addAbility(newAbility, attachedTo.getId(), attachedTo);
    }

    /**
     * Removes Triggered abilities that belong to sourceId This is used if a
     * token leaves the battlefield
     *
     * @param sourceId
     */
    public void removeTriggersOfSourceId(UUID sourceId) {
        triggers.removeAbilitiesOfSource(sourceId);
    }

    /**
     * Called before applyEffects
     */
    private void reset() {
        // All gained abilities have to be removed to prevent adding it multiple times
        triggers.removeAllGainedAbilities();
        getContinuousEffects().removeAllTemporaryEffects();
        this.setLegendaryRuleActive(true);
        for (CardState state : cardState.values()) {
            state.clearAbilities();
        }
        cardAttribute.clear();
    }

    public void clear() {
        battlefield.clear();
        effects.clear();
        triggers.clear();
        delayed.clear();
        triggered.clear();
        stack.clear();
        exile.clear();
        command.clear();
        revealed.clear();
        lookedAt.clear();
        turnNum = 0;
        stepNum = 0;
        extraTurn = false;
        legendaryRuleActive = true;
        gameOver = false;
        specialActions.clear();
        cardState.clear();
        combat.clear();
        turnMods.clear();
        watchers.clear();
        values.clear();
        zones.clear();
        simultaneousEvents.clear();
        copiedCards.clear();
        permanentOrderNumber = 0;
    }

    public void pause() {
        this.paused = true;
    }

    public void resume() {
        this.paused = false;
    }

    public boolean isPaused() {
        return this.paused;
    }

    public boolean isLegendaryRuleActive() {
        return legendaryRuleActive;
    }

    public void setLegendaryRuleActive(boolean legendaryRuleActive) {
        this.legendaryRuleActive = legendaryRuleActive;
    }

    /**
     * Only used for diagnostic purposes of tests
     *
     * @return
     */
    public TriggeredAbilities getTriggers() {
        return triggers;
    }

    public CardState getCardState(UUID cardId) {
        if (!cardState.containsKey(cardId)) {
            cardState.put(cardId, new CardState());
        }
        return cardState.get(cardId);
    }

    public CardAttribute getCardAttribute(UUID cardId) {
        return cardAttribute.get(cardId);
    }

    public CardAttribute getCreateCardAttribute(Card card) {
        CardAttribute cardAtt = cardAttribute.get(card.getId());
        if (cardAtt == null) {
            cardAtt = new CardAttribute(card);
            cardAttribute.put(card.getId(), cardAtt);
        }
        return cardAtt;
    }

    public void addWatcher(Watcher watcher) {
        this.watchers.add(watcher);
    }

    public int getZoneChangeCounter(UUID objectId) {
        if (this.zoneChangeCounter.containsKey(objectId)) {
            return this.zoneChangeCounter.get(objectId);
        }
        return 1;
    }

    public void updateZoneChangeCounter(UUID objectId) {
        Integer value = getZoneChangeCounter(objectId);
        value++;
        this.zoneChangeCounter.put(objectId, value);
        // card is changing zone so clear state
        if (cardState.containsKey(objectId)) {
            this.cardState.get(objectId).clear();
        }
    }

    public void setZoneChangeCounter(UUID objectId, int value) {
        this.zoneChangeCounter.put(objectId, value);
    }

    public Card getCopiedCard(UUID cardId) {
        return copiedCards.get(cardId);
    }

    public Collection<Card> getCopiedCards() {
        return copiedCards.values();
    }

    public Card copyCard(Card cardToCopy, Ability source, Game game) {
        Card copiedCard = cardToCopy.copy();
        copiedCard.assignNewId();
        copiedCard.setOwnerId(source.getControllerId());
        copiedCard.setCopy(true);
        copiedCards.put(copiedCard.getId(), copiedCard);
        addCard(copiedCard);
        if (copiedCard.isSplitCard()) {
            Card leftCard = ((SplitCard) copiedCard).getLeftHalfCard();
            copiedCards.put(leftCard.getId(), leftCard);
            addCard(leftCard);
            Card rightCard = ((SplitCard) copiedCard).getRightHalfCard();
            copiedCards.put(rightCard.getId(), rightCard);
            addCard(rightCard);
        }
        return copiedCard;
    }

    public int getNextPermanentOrderNumber() {
        return permanentOrderNumber++;
    }
}
