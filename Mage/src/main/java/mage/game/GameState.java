package mage.game;

import static java.util.Collections.emptyList;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.*;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffects;
import mage.abilities.effects.Effect;
import mage.cards.*;
import mage.constants.PhaseStep;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.designations.Designation;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.combat.Combat;
import mage.game.combat.CombatGroup;
import mage.game.command.Command;
import mage.game.command.CommandObject;
import mage.game.command.Emblem;
import mage.game.command.Plane;
import mage.game.events.*;
import mage.game.permanent.Battlefield;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.PermanentToken;
import mage.game.stack.SpellStack;
import mage.game.stack.StackObject;
import mage.game.turn.Phase;
import mage.game.turn.Step;
import mage.game.turn.Turn;
import mage.game.turn.TurnMods;
import mage.players.Player;
import mage.players.PlayerList;
import mage.players.Players;
import mage.target.Target;
import mage.util.CardUtil;
import mage.util.Copyable;
import mage.util.ThreadLocalStringBuilder;
import mage.watchers.Watcher;
import mage.watchers.Watchers;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 * <p>
 * since at any time the game state may be copied and restored you cannot rely
 * on any object maintaining it's instance it then becomes necessary to only
 * refer to objects by their ids since these will always remain constant
 * throughout its lifetime
 */
public class GameState implements Serializable, Copyable<GameState> {

    private static final Logger logger = Logger.getLogger(GameState.class);
    private static final ThreadLocalStringBuilder threadLocalBuilder = new ThreadLocalStringBuilder(1024);

    // save copied cards between game cycles (lki workaround)
    // warning, do not use another keys with same starting text cause copy code search and clean all related values
    public static final String COPIED_CARD_KEY = "CopiedCard";

    private final Players players;
    private final PlayerList playerList;
    private UUID choosingPlayerId; // player that makes a choice at game start

    // revealed cards <Name, <Cards>>, will be reset if all players pass priority
    private final Revealed revealed;
    private final Map<UUID, LookedAt> lookedAt = new HashMap<>();
    private final Revealed companion;

    private SpecialActions specialActions;
    private Watchers watchers;
    private Turn turn;
    private TurnMods turnMods; // one time turn modifications (turn, phase or step)
    private UUID activePlayerId; // playerId which turn it is
    private UUID priorityPlayerId; // player that has currently priority (setup before any choose)
    private UUID playerByOrderId; // player that has currently priority
    private UUID monarchId; // player that is the monarch
    private UUID initiativeId; // player that has the initiative
    private SpellStack stack;
    private Command command;
    private boolean isPlaneChase;
    private List<String> seenPlanes = new ArrayList<>();
    private List<Designation> designations = new ArrayList<>();
    private List<Emblem> inherentEmblems = new ArrayList<>();
    private Exile exile;
    private Battlefield battlefield;
    private int turnNum = 1;
    private int stepNum = 0;
    private UUID extraTurnId = null; // id of the current extra turn (null on normal turn or after game stopped)
    private boolean gameOver;
    private boolean paused;
    private ContinuousEffects effects;
    private TriggeredAbilities triggers; // all normal triggers
    private DelayedTriggeredAbilities delayed; // all delayed triggers
    private List<TriggeredAbility> triggered = new ArrayList<>(); // raised triggers, waiting to resolve (can contains both normal and delayed)
    private Combat combat;
    private Map<String, Object> values = new HashMap<>();
    private Map<UUID, Zone> zones = new HashMap<>();
    private List<GameEvent> simultaneousEvents = new ArrayList<>();
    private Map<UUID, CardState> cardState = new HashMap<>();
    private Map<MageObjectReference, Map<String, Object>> permanentCostsTags = new HashMap<>(); // Permanent reference -> map of (tag -> values) describing how the permanent's spell was cast
    private Map<UUID, MageObjectAttribute> mageObjectAttribute = new HashMap<>();
    private Map<UUID, Integer> zoneChangeCounter = new HashMap<>();
    private Map<UUID, Card> copiedCards = new HashMap<>();
    private int permanentOrderNumber;
    private final Map<UUID, FilterCreaturePermanent> usePowerInsteadOfToughnessForDamageLethalityFilters = new HashMap<>();
    private Set<MageObjectReference> commandersToStay = new HashSet<>(); // commanders that do not go back to command zone
    private boolean manaBurn = false;
    private boolean hasDayNight = false;
    private boolean isDaytime = true;
    private boolean reverseTurnOrder = false;

    private int applyEffectsCounter; // Upcounting number of each applyEffects execution

    public GameState() {
        players = new Players();
        playerList = new PlayerList();
        turn = new Turn();
        stack = new SpellStack();
        command = new Command();
        exile = new Exile();
        revealed = new Revealed();
        companion = new Revealed();
        battlefield = new Battlefield();
        effects = new ContinuousEffects();
        triggers = new TriggeredAbilities();
        delayed = new DelayedTriggeredAbilities();
        specialActions = new SpecialActions();
        combat = new Combat();
        turnMods = new TurnMods();
        watchers = new Watchers();
        applyEffectsCounter = 0;
    }

    protected GameState(final GameState state) {
        this.players = state.players.copy();
        this.playerList = state.playerList.copy();
        this.choosingPlayerId = state.choosingPlayerId;
        this.revealed = state.revealed.copy();
        this.lookedAt.putAll(state.lookedAt);
        this.companion = state.companion.copy();
        this.gameOver = state.gameOver;
        this.paused = state.paused;

        this.activePlayerId = state.activePlayerId;
        this.priorityPlayerId = state.priorityPlayerId;
        this.playerByOrderId = state.playerByOrderId;
        this.monarchId = state.monarchId;
        this.initiativeId = state.initiativeId;
        this.turn = state.turn.copy();

        this.stack = state.stack.copy();
        this.command = state.command.copy();
        this.isPlaneChase = state.isPlaneChase;
        this.seenPlanes.addAll(state.seenPlanes);
        this.designations.addAll(state.designations);
        this.inherentEmblems = CardUtil.deepCopyObject(state.inherentEmblems);
        this.exile = state.exile.copy();
        this.battlefield = state.battlefield.copy();
        this.turnNum = state.turnNum;
        this.stepNum = state.stepNum;
        this.extraTurnId = state.extraTurnId;
        this.effects = state.effects.copy();
        this.triggered = CardUtil.deepCopyObject(state.triggered);
        this.triggers = state.triggers.copy();
        this.delayed = state.delayed.copy();
        this.specialActions = state.specialActions.copy();
        this.combat = state.combat.copy();
        this.turnMods = state.turnMods.copy();
        this.watchers = state.watchers.copy();
        this.values = CardUtil.deepCopyObject(state.values);
        this.zones.putAll(state.zones);
        this.simultaneousEvents.addAll(state.simultaneousEvents);
        this.cardState = CardUtil.deepCopyObject(state.cardState);
        this.permanentCostsTags = CardUtil.deepCopyObject(state.permanentCostsTags);
        this.mageObjectAttribute = CardUtil.deepCopyObject(state.mageObjectAttribute);
        this.zoneChangeCounter.putAll(state.zoneChangeCounter);
        this.copiedCards.putAll(state.copiedCards);
        this.permanentOrderNumber = state.permanentOrderNumber;
        this.applyEffectsCounter = state.applyEffectsCounter;
        state.usePowerInsteadOfToughnessForDamageLethalityFilters.forEach((uuid, filter)
                -> this.usePowerInsteadOfToughnessForDamageLethalityFilters.put(uuid, filter.copy()));
        this.commandersToStay.addAll(state.commandersToStay);
        this.hasDayNight = state.hasDayNight;
        this.isDaytime = state.isDaytime;
        this.reverseTurnOrder = state.reverseTurnOrder;
    }

    public void clearOnGameRestart() {
        // special code for Karn Liberated
        // must clear game data on restart, but also must keep some info (wtf, why?)
        // if you catch freezes or bugs with Karn then research here
        // test example: testCommanderRestoredToBattlefieldAfterKarnUltimate
        // TODO: must be implemented as full data clear?

        battlefield.clear();
        effects.clear();
        triggers.clear();
        delayed.clear();
        triggered.clear();
        stack.clear();
        exile.clear();
        command.clear();
        designations.clear();
        inherentEmblems.clear();
        seenPlanes.clear();
        isPlaneChase = false;
        revealed.clear();
        lookedAt.clear();
        companion.clear();
        turnNum = 1;
        stepNum = 0;
        extraTurnId = null;
        gameOver = false;
        specialActions.clear();
        cardState.clear();
        permanentCostsTags.clear();
        combat.clear();
        turnMods.clear();
        watchers.clear();
        values.clear();
        zones.clear();
        simultaneousEvents.clear();
        copiedCards.clear();
        usePowerInsteadOfToughnessForDamageLethalityFilters.clear();
        permanentOrderNumber = 0;
    }

    public void restoreForRollBack(GameState state) {
        restore(state);
        this.turn = state.turn;
    }

    public void restore(GameState state) {
        // no needs in copy here cause GameState already copied on save and it will be used only one time here
        this.activePlayerId = state.activePlayerId;
        this.playerList.setCurrent(state.activePlayerId);
        this.playerByOrderId = state.playerByOrderId;
        this.priorityPlayerId = state.priorityPlayerId;
        this.monarchId = state.monarchId;
        this.initiativeId = state.initiativeId;
        this.stack = state.stack;
        this.command = state.command;
        this.isPlaneChase = state.isPlaneChase;
        this.seenPlanes = state.seenPlanes;
        this.designations = state.designations;
        this.inherentEmblems = state.inherentEmblems;
        this.exile = state.exile;
        this.battlefield = state.battlefield;
        this.turnNum = state.turnNum;
        this.stepNum = state.stepNum;
        this.extraTurnId = state.extraTurnId;
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
        this.permanentCostsTags = state.permanentCostsTags;
        this.mageObjectAttribute = state.mageObjectAttribute;
        this.zoneChangeCounter = state.zoneChangeCounter;
        this.copiedCards = state.copiedCards;
        this.permanentOrderNumber = state.permanentOrderNumber;
        this.applyEffectsCounter = state.applyEffectsCounter;
        state.usePowerInsteadOfToughnessForDamageLethalityFilters.forEach((uuid, filter)
                -> this.usePowerInsteadOfToughnessForDamageLethalityFilters.put(uuid, filter.copy()));
        this.commandersToStay = state.commandersToStay;
        this.hasDayNight = state.hasDayNight;
        this.isDaytime = state.isDaytime;
        this.reverseTurnOrder = state.reverseTurnOrder;
    }

    @Override
    public GameState copy() {
        return new GameState(this);
    }

    public void addPlayer(Player player) {
        players.put(player.getId(), player);
        playerList.add(player.getId());
    }

    /**
     * AI related: monitor changes in game state (if it changed then AI must re-calculate current actions chain)
     */
    public String getValue(boolean useHidden) {
        StringBuilder sb = threadLocalBuilder.get();

        sb.append(turn.getValue(turnNum));
        sb.append(activePlayerId).append(priorityPlayerId).append(playerByOrderId);

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
            sb.append(permanent.getValue(this));
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

    /**
     * AI related: monitor changes in game state (if it changed then AI must re-calculate current actions chain)
     */
    public String getValue(boolean useHidden, Game game) {
        StringBuilder sb = threadLocalBuilder.get();

        sb.append(turn.getValue(turnNum));
        sb.append(activePlayerId).append(priorityPlayerId).append(playerByOrderId);

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
            perms.add(permanent.getValue(this));
        }
        Collections.sort(perms);
        sb.append(perms);

        sb.append("spells");
        for (StackObject spell : stack) {
            sb.append(spell.getControllerId()).append(spell.getName());
            sb.append(spell.getStackAbility().toString());
            for (UUID modeId : spell.getStackAbility().getModes().getSelectedModes()) {
                Mode mode = spell.getStackAbility().getModes().get(modeId);
                if (!mode.getTargets().isEmpty()) {
                    sb.append("targets");
                    for (Target target : mode.getTargets()) {
                        sb.append(target.getTargets());
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

    /**
     * AI related: monitor changes in game state (if it changed then AI must re-calculate current actions chain)
     */
    public String getValue(Game game, UUID playerId) {
        StringBuilder sb = threadLocalBuilder.get();

        sb.append(turn.getValue(turnNum));
        sb.append(activePlayerId).append(priorityPlayerId).append(playerByOrderId);

        for (Player player : players.values()) {
            sb.append("player").append(player.isPassed()).append(player.getLife()).append("hand");
            if (Objects.equals(playerId, player.getId())) {
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
            perms.add(permanent.getValue(this));
        }
        Collections.sort(perms);
        sb.append(perms);

        sb.append("spells");
        for (StackObject spell : stack) {
            sb.append(spell.getControllerId()).append(spell.getName());
            sb.append(spell.getStackAbility().toString());
            for (UUID modeId : spell.getStackAbility().getModes().getSelectedModes()) {
                Mode mode = spell.getStackAbility().getModes().get(modeId);
                if (!mode.getTargets().isEmpty()) {
                    sb.append("targets");
                    for (Target target : mode.getTargets()) {
                        sb.append(target.getTargets());
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

    public UUID getPlayerByOrderId() {
        return playerByOrderId;
    }

    public void setPlayerByOrderId(UUID playerByOrderId) {
        this.playerByOrderId = playerByOrderId;
    }

    public UUID getPriorityPlayerId() {
        return priorityPlayerId;
    }

    public void setPriorityPlayerId(UUID priorityPlayerId) {
        this.priorityPlayerId = priorityPlayerId;
    }

    public UUID getMonarchId() {
        return monarchId;
    }

    public void setMonarchId(UUID monarchId) {
        this.monarchId = monarchId;
    }

    public UUID getInitiativeId() {
        return initiativeId;
    }

    public void setInitiativeId(UUID initiativeId) {
        this.initiativeId = initiativeId;
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

    public List<Designation> getDesignations() {
        return designations;
    }

    public List<Emblem> getInherentEmblems() {
        return inherentEmblems;
    }

    public Plane getCurrentPlane() {
        if (command != null && command.size() > 0) {
            for (CommandObject cobject : command) {
                if (cobject instanceof Plane) {
                    return (Plane) cobject;
                }
            }
        }
        return null;
    }

    public List<String> getSeenPlanes() {
        return seenPlanes;
    }

    public boolean isPlaneChase() {
        return isPlaneChase;
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

    public Revealed getCompanion() {
        return companion;
    }

    public void clearRevealed() {
        revealed.clear();
    }

    public void clearLookedAt() {
        lookedAt.clear();
    }

    public void clearCompanion() {
        companion.clear();
    }

    public Turn getTurn() {
        return turn;
    }

    public PhaseStep getTurnStepType() {
        Turn turn = this.getTurn();
        Phase phase = turn != null ? turn.getPhase() : null;
        Step step = phase != null ? phase.getStep() : null;
        return step != null ? step.getType() : null;
    }

    public TurnPhase getTurnPhaseType() {
        Turn turn = this.getTurn();
        Phase phase = turn != null ? turn.getPhase() : null;
        return phase != null ? phase.getType() : null;
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

    public UUID getExtraTurnId() {
        return this.extraTurnId;
    }

    public void setExtraTurnId(UUID extraTurnId) {
        this.extraTurnId = extraTurnId;
    }

    public boolean isExtraTurn() {
        return this.extraTurnId != null;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    public TurnMods getTurnMods() {
        return this.turnMods;
    }

    /**
     * Find game scope watcher
     */
    public <T extends Watcher> T getWatcher(Class<T> watcherClass) {
        return getWatcher(watcherClass, null);
    }

    /**
     * Find card/player scope watcher
     */
    public <T extends Watcher> T getWatcher(Class<T> watcherClass, UUID uuid) {
        String watcherKey = (uuid == null ? "" : uuid.toString()) + watcherClass.getSimpleName();
        return watcherClass.cast(getWatcher(watcherKey));
    }

    public Watcher getWatcher(String key) {
        return watchers.get(key);
    }

    public SpecialActions getSpecialActions() {
        return this.specialActions;
    }

    public void endGame() {
        this.gameOver = true;
    }

    /**
     * Must be called between effects/steps in the ability's resolve
     * <p>
     * 608.2e
     * Some spells and abilities have multiple steps or actions, denoted by separate sentences or clauses,
     * that involve multiple players. In these cases, the choices for the first action are made in APNAP order,
     * and then the first action is processed simultaneously. Then the choices for the second action are made in
     * APNAP order, and then that action is processed simultaneously, and so on. See rule 101.4.
     */
    public void processAction(Game game) {
        game.getState().handleSimultaneousEvent(game);
        game.applyEffects();
        game.getState().getTriggers().checkStateTriggers(game);
    }

    public void applyEffects(Game game) {
        applyEffectsCounter++;
        for (Player player : players.values()) {
            player.reset();
        }
        battlefield.reset(game);
        combat.reset(game);
        this.reset();
        effects.apply(game);
        combat.checkForRemoveFromCombat(game);
    }

    // remove end of combat effects
    public void removeEocEffects(Game game) {
        effects.removeEndOfCombatEffects();
        delayed.removeEndOfCombatAbilities();
        game.applyEffects();
    }

    // remove end of turn effects
    public void removeEotEffects(Game game) {
        effects.removeEndOfTurnEffects(game);
        delayed.removeEndOfTurnAbilities(game);
        exile.cleanupEndOfTurnZones(game);
        game.applyEffects();
    }

    public void removeTurnStartEffect(Game game) {
        delayed.removeStartOfNewTurn(game);
    }

    public void addEffect(ContinuousEffect effect, Ability source) {
        addEffect(effect, null, source);
    }

    public void addEffect(ContinuousEffect effect, UUID sourceId, Ability source) {
        if (sourceId == null) {
            effects.addEffect(effect, source);
        } else {
            effects.addEffect(effect, sourceId, source);
        }
    }

    private void addTrigger(TriggeredAbility ability, UUID sourceId, MageObject attachedTo) {
        if (sourceId == null) {
            triggers.add(ability, attachedTo);
        } else {
            triggers.add(ability, sourceId, attachedTo);
        }
    }

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
     * <p>
     * Not safe for continuous effects, see rule 800.4k (effects must work until
     * end of turn even after player leaves) Use Player.InRange() to find active
     * players list at the start of the turn
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
                if (player.isInGame() && currentPlayer.getInRange().contains(player.getId())) {
                    newPlayerList.add(player.getId());
                }
            }
            newPlayerList.setCurrent(playerId);
        }
        return newPlayerList;
    }

    public Permanent getPermanent(UUID permanentId) {
        if (permanentId != null && battlefield.containsPermanent(permanentId)) {
            return battlefield.getPermanent(permanentId);
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
        if (zone == null) {
            zones.remove(id);
        } else {
            zones.put(id, zone);
        }
    }

    public void addSimultaneousEvent(GameEvent event, Game game) {
        simultaneousEvents.add(event);
    }

    public void handleSimultaneousEvent(Game game) {
        if (!simultaneousEvents.isEmpty() && !getTurn().isEndTurnRequested()) {
            // it can happen, that the events add new simultaneous events, so copy the list before
            List<GameEvent> eventsToHandle = new ArrayList<>();
            List<GameEvent> eventGroups = createEventGroups(simultaneousEvents, game);
            eventsToHandle.addAll(simultaneousEvents);
            eventsToHandle.addAll(eventGroups);
            simultaneousEvents.clear();
            for (GameEvent event : eventsToHandle) {
                this.handleEvent(event, game);
            }
        }
    }

    public boolean hasSimultaneousEvents() {
        return !simultaneousEvents.isEmpty();
    }

    // There might be no damage dealt, but we want to fire that damage (in a batch) could have been dealt.
    // Of note, DamagedBatchCouldHaveFiredEvent is not a batch event in the sense it doesn't contain sub events.
    public void addBatchDamageCouldHaveBeenFired(boolean combat, Game game) {
        for (GameEvent event : simultaneousEvents) {
            if (event instanceof DamagedBatchCouldHaveFiredEvent
                    && ((DamagedBatchCouldHaveFiredEvent) event).isCombat() == combat) {
                return;
            }
        }
        addSimultaneousEvent(new DamagedBatchCouldHaveFiredEvent(combat), game);
    }

    public void addSimultaneousDamage(DamagedEvent damagedEvent, Game game) {
        // Combine multiple damage events in the single event (batch)
        // Note: one event can be stored in multiple batches
        if (damagedEvent instanceof DamagedPlayerEvent) {
            // DAMAGED_BATCH_FOR_PLAYERS + DAMAGED_BATCH_FOR_ONE_PLAYER
            addSimultaneousDamageToPlayerBatches((DamagedPlayerEvent) damagedEvent, game);
        } else if (damagedEvent instanceof DamagedPermanentEvent) {
            // DAMAGED_BATCH_FOR_PERMANENTS + DAMAGED_BATCH_FOR_ONE_PERMANENT
            addSimultaneousDamageToPermanentBatches((DamagedPermanentEvent) damagedEvent, game);
        }
        // DAMAGED_BATCH_FOR_ALL
        addSimultaneousDamageToBatchForAll(damagedEvent, game);
    }

    public void addSimultaneousDamageToPlayerBatches(DamagedPlayerEvent damagedPlayerEvent, Game game) {
        // find existing batches first
        boolean isTotalBatchUsed = false;
        boolean isPlayerBatchUsed = false;
        for (GameEvent event : simultaneousEvents) {
            if (event instanceof DamagedBatchForPlayersEvent) {
                ((DamagedBatchForPlayersEvent) event).addEvent(damagedPlayerEvent);
                isTotalBatchUsed = true;
            } else if (event instanceof DamagedBatchForOnePlayerEvent
                    && damagedPlayerEvent.getTargetId().equals(event.getTargetId())) {
                ((DamagedBatchForOnePlayerEvent) event).addEvent(damagedPlayerEvent);
                isPlayerBatchUsed = true;
            }
        }
        // new batches if necessary
        if (!isTotalBatchUsed) {
            addSimultaneousEvent(new DamagedBatchForPlayersEvent(damagedPlayerEvent), game);
        }
        if (!isPlayerBatchUsed) {
            addSimultaneousEvent(new DamagedBatchForOnePlayerEvent(damagedPlayerEvent), game);
        }
    }

    public void addSimultaneousDamageToPermanentBatches(DamagedPermanentEvent damagedPermanentEvent, Game game) {
        // find existing batches first
        boolean isTotalBatchUsed = false;
        boolean isSingleBatchUsed = false;
        for (GameEvent event : simultaneousEvents) {
            if (event instanceof DamagedBatchForPermanentsEvent) {
                ((DamagedBatchForPermanentsEvent) event).addEvent(damagedPermanentEvent);
                isTotalBatchUsed = true;
            } else if (event instanceof DamagedBatchForOnePermanentEvent
                    && damagedPermanentEvent.getTargetId().equals(event.getTargetId())) {
                ((DamagedBatchForOnePermanentEvent) event).addEvent(damagedPermanentEvent);
                isSingleBatchUsed = true;
            }
        }
        // new batches if necessary
        if (!isTotalBatchUsed) {
            addSimultaneousEvent(new DamagedBatchForPermanentsEvent(damagedPermanentEvent), game);
        }
        if (!isSingleBatchUsed) {
            addSimultaneousEvent(new DamagedBatchForOnePermanentEvent(damagedPermanentEvent), game);
        }
    }

    public void addSimultaneousDamageToBatchForAll(DamagedEvent damagedEvent, Game game) {
        boolean isBatchUsed = false;
        for (GameEvent event : simultaneousEvents) {
            if (event instanceof DamagedBatchAllEvent) {
                ((DamagedBatchAllEvent) event).addEvent(damagedEvent);
                isBatchUsed = true;
            }
        }
        if (!isBatchUsed) {
            addSimultaneousEvent(new DamagedBatchAllEvent(damagedEvent), game);
        }
    }

    public void addSimultaneousMilledCardToBatch(MilledCardEvent milledEvent, Game game) {
        // Combine multiple mill cards events in the single event (batch)
        // see GameEvent.MILLED_CARDS_BATCH_FOR_ONE_PLAYER and GameEvent.MILLED_CARDS_BATCH_FOR_ALL

        // existing batch
        boolean isBatchUsed = false;
        boolean isBatchForPlayerUsed = false;
        for (GameEvent event : simultaneousEvents) {
            if (event instanceof MilledBatchAllEvent) {
                ((MilledBatchAllEvent) event).addEvent(milledEvent);
                isBatchUsed = true;
            } else if (event instanceof MilledBatchForOnePlayerEvent
                    && event.getPlayerId().equals(milledEvent.getPlayerId())) {
                ((MilledBatchForOnePlayerEvent) event).addEvent(milledEvent);
                isBatchForPlayerUsed = true;
            }
        }

        // new batch
        if (!isBatchUsed) {
            addSimultaneousEvent(new MilledBatchAllEvent(milledEvent), game);
        }
        if (!isBatchForPlayerUsed) {
            addSimultaneousEvent(new MilledBatchForOnePlayerEvent(milledEvent), game);
        }
    }

    public void addSimultaneousLifeLossToBatch(LifeLostEvent lifeLossEvent, Game game) {
        // Combine multiple life loss events in the single event (batch)
        // see GameEvent.LOST_LIFE_BATCH

        // existing batch
        boolean isLifeLostBatchUsed = false;
        for (GameEvent event : simultaneousEvents) {
            if (event instanceof LifeLostBatchEvent) {
                ((LifeLostBatchEvent) event).addEvent(lifeLossEvent);
                isLifeLostBatchUsed = true;
            }
        }

        // new batch
        if (!isLifeLostBatchUsed) {
            addSimultaneousEvent(new LifeLostBatchEvent(lifeLossEvent), game);
        }
    }

    public void addSimultaneousTappedToBatch(TappedEvent tappedEvent, Game game) {
        // Combine multiple tapped events in the single event (batch)

        boolean isTappedBatchUsed = false;
        for (GameEvent event : simultaneousEvents) {
            if (event instanceof TappedBatchEvent) {
                // Adding to the existing batch
                ((TappedBatchEvent) event).addEvent(tappedEvent);
                isTappedBatchUsed = true;
                break;
            }
        }

        // new batch
        if (!isTappedBatchUsed) {
            addSimultaneousEvent(new TappedBatchEvent(tappedEvent), game);
        }
    }

    public void addSimultaneousUntappedToBatch(UntappedEvent untappedEvent, Game game) {
        // Combine multiple untapped events in the single event (batch)

        boolean isUntappedBatchUsed = false;
        for (GameEvent event : simultaneousEvents) {
            if (event instanceof UntappedBatchEvent) {
                // Adding to the existing batch
                ((UntappedBatchEvent) event).addEvent(untappedEvent);
                isUntappedBatchUsed = true;
                break;
            }
        }

        // new batch
        if (!isUntappedBatchUsed) {
            addSimultaneousEvent(new UntappedBatchEvent(untappedEvent), game);
        }
    }

    public void handleEvent(GameEvent event, Game game) {
        watchers.watch(event, game);
        delayed.checkTriggers(event, game);
        triggers.checkTriggers(event, game);
    }

    public boolean replaceEvent(GameEvent event, Game game) {
        return replaceEvent(event, null, game);
    }

    public boolean replaceEvent(GameEvent event, Ability targetAbility, Game game) {
        if (effects.preventedByRuleModification(event, targetAbility, game, false)) {
            return true;
        }
        return effects.replaceEvent(event, game);
    }

    public List<GameEvent> createEventGroups(List<GameEvent> events, Game game) {

        class ZoneChangeData {

            private final Zone fromZone;
            private final Zone toZone;
            private final UUID sourceId;
            private final UUID playerId;
            Ability source;

            public ZoneChangeData(Ability source, UUID sourceId, UUID playerId, Zone fromZone, Zone toZone) {
                this.sourceId = sourceId;
                this.playerId = playerId;
                this.fromZone = fromZone;
                this.toZone = toZone;
                this.source = source;
            }

            @Override
            public int hashCode() {
                return (this.fromZone.ordinal() + 1) * 1
                        + (this.toZone.ordinal() + 1) * 10
                        + (this.sourceId != null ? this.sourceId.hashCode() : 0)
                        + (this.source != null ? this.source.hashCode() : 0);
            }

            @Override
            public boolean equals(Object obj) {
                if (obj instanceof ZoneChangeData) {
                    ZoneChangeData data = (ZoneChangeData) obj;
                    return this.fromZone == data.fromZone
                            && this.toZone == data.toZone
                            && Objects.equals(this.sourceId, data.sourceId)
                            && Objects.equals(this.source, data.source);
                }
                return false;
            }
        }

        Map<ZoneChangeData, List<GameEvent>> eventsByKey = new HashMap<>();
        List<GameEvent> groupEvents = new LinkedList<>();
        ZoneChangeBatchEvent batchEvent = new ZoneChangeBatchEvent();
        for (GameEvent event : events) {
            if (event instanceof ZoneChangeEvent) {
                ZoneChangeEvent castEvent = (ZoneChangeEvent) event;
                batchEvent.addEvent(castEvent);
                ZoneChangeData key = new ZoneChangeData(
                        castEvent.getSource(),
                        castEvent.getSourceId(),
                        castEvent.getPlayerId(),
                        castEvent.getFromZone(),
                        castEvent.getToZone());
                if (eventsByKey.containsKey(key)) {
                    eventsByKey.get(key).add(event);
                } else {
                    List<GameEvent> list = new LinkedList<>();
                    list.add(event);
                    eventsByKey.put(key, list);
                }
            }
        }
        for (Map.Entry<ZoneChangeData, List<GameEvent>> entry : eventsByKey.entrySet()) {
            Set<Card> movedCards = new LinkedHashSet<>();
            Set<PermanentToken> movedTokens = new LinkedHashSet<>();
            for (Iterator<GameEvent> it = entry.getValue().iterator(); it.hasNext(); ) {
                GameEvent event = it.next();
                ZoneChangeEvent castEvent = (ZoneChangeEvent) event;
                UUID targetId = castEvent.getTargetId();
                Card card = ZonesHandler.getTargetCard(game, targetId);
                if (card instanceof PermanentToken) {
                    movedTokens.add((PermanentToken) card);
                } else if (game.getObject(targetId) instanceof PermanentToken) {
                    movedTokens.add((PermanentToken) game.getObject(targetId));
                } else if (card != null) {
                    movedCards.add(card);
                }
            }
            ZoneChangeData eventData = entry.getKey();
            if (!movedCards.isEmpty() || !movedTokens.isEmpty()) {
                ZoneChangeGroupEvent event = new ZoneChangeGroupEvent(
                        movedCards,
                        movedTokens,
                        eventData.sourceId,
                        eventData.source,
                        eventData.playerId,
                        eventData.fromZone,
                        eventData.toZone);
                groupEvents.add(event);
            }
        }
        if (!batchEvent.getEvents().isEmpty()) {
            groupEvents.add(batchEvent);
        }
        return groupEvents;
    }

    public void addCard(Card card) {
        // all new cards and tokens must enter from outside
        addCard(card, Zone.OUTSIDE);
    }

    private void addCard(Card card, Zone zone) {
        setZone(card.getId(), zone);

        // add card specific abilities to game
        for (Ability ability : card.getInitAbilities()) {
            addAbility(ability, null, card);
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
            for (UUID modeId : ability.getModes().getSelectedModes()) {
                Mode mode = ability.getModes().get(modeId);
                for (Effect effect : mode.getEffects()) {
                    if (effect instanceof ContinuousEffect) {
                        addEffect((ContinuousEffect) effect, ability);
                    }
                }
            }
        } else if (ability instanceof TriggeredAbility) {
            addTrigger((TriggeredAbility) ability, null, attachedTo);
        }
    }

    /**
     * Abilities that are applied to other objects or applied for a certain time
     * span
     *
     * @param ability
     * @param sourceId   - if source object can be moved between zones then you
     *                   must set it here (each game cycle clear all source related triggers)
     * @param attachedTo
     */
    public void addAbility(Ability ability, UUID sourceId, MageObject attachedTo) {
        if (ability instanceof StaticAbility) {
            for (UUID modeId : ability.getModes().getSelectedModes()) {
                Mode mode = ability.getModes().get(modeId);
                for (Effect effect : mode.getEffects()) {
                    if (effect instanceof ContinuousEffect) {
                        addEffect((ContinuousEffect) effect, sourceId, ability);
                    }
                }
            }
        } else if (ability instanceof TriggeredAbility) {
            addTrigger((TriggeredAbility) ability, sourceId, attachedTo);
        }

        for (Watcher watcher : ability.getWatchers()) {
            // TODO: Check that watcher for commanderAbility (where attachedTo = null) also work correctly
            UUID controllerId = ability.getControllerId();
            if (attachedTo instanceof Card) {
                controllerId = ((Card) attachedTo).getOwnerId();
            } else if (attachedTo instanceof Controllable) {
                controllerId = ((Controllable) attachedTo).getControllerId();
            }

            Watcher newWatcher = watcher.copy();
            newWatcher.setControllerId(controllerId);
            newWatcher.setSourceId(attachedTo == null ? ability.getSourceId() : attachedTo.getId());
            watchers.add(newWatcher);
        }

        for (Ability sub : ability.getSubAbilities()) {
            addAbility(sub, sourceId, attachedTo);
        }
    }

    /**
     * Inherent triggers (Rad counters) in the rules have no source.
     * However to fit better with the engine, we make a fake emblem source,
     * which is not displayed in any game zone. That allows the trigger to
     * have a source, which helps with a bunch of situation like hosting,
     * rather than having a  trigger.
     * <p>
     * Should not be used except in very specific situations
     */
    public void addInherentEmblem(Emblem emblem, UUID controllerId) {
        getInherentEmblems().add(emblem);
        emblem.setControllerId(controllerId);
        for (Ability ability : emblem.getInitAbilities()) {
            ability.setControllerId(controllerId);
            ability.setSourceId(emblem.getId());
            addAbility(ability, null, emblem);
        }
    }

    public void addDesignation(Designation designation, Game game, UUID controllerId) {
        getDesignations().add(designation);
        for (Ability ability : designation.getInitAbilities()) {
            ability.setControllerId(controllerId);
            addAbility(ability, designation.getId(), null);
        }
    }

    public void addSeenPlane(Plane plane, Game game, UUID controllerId) {
        if (plane != null) {
            getSeenPlanes().add(plane.getName());
        }
    }

    public void resetSeenPlanes() {
        getSeenPlanes().clear();
    }

    public void setPlaneChase(Game game, boolean isPlaneChase) {
        this.isPlaneChase = isPlaneChase;
    }

    public void addCommandObject(CommandObject commandObject) {
        getCommand().add(commandObject);
        setZone(commandObject.getId(), Zone.COMMAND);

        // must add only command object specific abilities, all other abilities adds from card parts (on loadCards)
        for (Ability ability : commandObject.getInitAbilities()) {
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

        List<Watcher> watcherList = new ArrayList<>(ability.getWatchers()); // Workaround to prevent ConcurrentModificationException, not clear to me why this is happening now
        for (Watcher watcher : watcherList) {
            Watcher newWatcher = watcher.copy();
            newWatcher.setControllerId(ability.getControllerId());
            newWatcher.setSourceId(ability.getSourceId());
            this.watchers.add(newWatcher);
        }
    }

    public void removeDelayedTriggeredAbility(UUID abilityId) {
        delayed.removeIf(ability -> ability.getId().equals(abilityId));
    }

    public List<TriggeredAbility> getTriggered(UUID controllerId) {
        return triggered.stream().filter(triggeredAbility -> controllerId.equals(triggeredAbility.getControllerId()))
                .collect(Collectors.toList());
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

    public Object computeValueIfAbsent(String valueId, Function<String, ?> mappingFunction) {
        return values.computeIfAbsent(valueId, mappingFunction);
    }

    /**
     * Return values list starting with searching key.
     * <p>
     * Usage example: if you want to find all saved values from related
     * ability/effect
     *
     * @param startWithValue
     * @return
     */
    public Map<String, Object> getValues(String startWithValue) {
        if (startWithValue == null || startWithValue.isEmpty()) {
            throw new IllegalArgumentException("Can't use empty search value");
        }
        Map<String, Object> res = new HashMap<>();
        for (Map.Entry<String, Object> entry : this.values.entrySet()) {
            if (entry.getKey().startsWith(startWithValue)) {
                res.put(entry.getKey(), entry.getValue());
            }
        }
        return res;
    }

    /**
     * Best only use immutable objects, otherwise the states/values of the
     * object may be changed by AI simulation or rollbacks, because the Value
     * objects are not copied as the state class is copied. Mutable supported:
     * HashSet with immutable entries (e.g. HashSet< UUID > or HashSet< String
     * > and EnumSets)
     *
     * @param valueId
     * @param value
     */
    public void setValue(String valueId, Object value) {
        values.put(valueId, value);
    }

    /**
     * Remove saved value
     *
     * @param valueId
     */
    public void removeValue(String valueId) {
        values.remove(valueId);
    }

    /**
     * Other abilities are used to implement some special kind of continuous
     * effects that give abilities to non permanents.
     * <p>
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
        addOtherAbility(attachedTo, ability, true);
    }

    /**
     * Adds the ability to continuous or triggered abilities
     *
     * @param attachedTo
     * @param ability
     * @param copyAbility copies non MageSingleton abilities before adding to
     *                    state (allows to have multiple instances in one object, e.g. false param
     *                    will simulate keyword/singleton)
     */
    public void addOtherAbility(Card attachedTo, Ability ability, boolean copyAbility) {
        checkWrongDynamicAbilityUsage(attachedTo, ability);

        Ability newAbility;
        if (ability instanceof MageSingleton || !copyAbility) {
            // Avoid adding another instance of an ability where multiple copies are redundant
            if (attachedTo.getAbilities().contains(ability)
                    || (getAllOtherAbilities(attachedTo.getId()) != null
                    && getAllOtherAbilities(attachedTo.getId()).contains(ability))) {
                return;
            }
            newAbility = ability;
        } else {
            // must use new id, so you can add multiple instances of the same ability
            // (example: gained Cascade from multiple Imoti, Celebrant of Bounty)
            newAbility = ability.copy();
            newAbility.newId();
        }
        newAbility.setSourceId(attachedTo.getId());
        newAbility.setControllerId(attachedTo.getOwnerId());
        if (!cardState.containsKey(attachedTo.getId())) {
            cardState.put(attachedTo.getId(), new CardState());
        }
        cardState.get(attachedTo.getId()).addAbility(newAbility);
        addAbility(newAbility, attachedTo.getId(), attachedTo);
    }

    private void checkWrongDynamicAbilityUsage(Card attachedTo, Ability ability) {
        // dynamic abilities for card only
        // permanent's abilities are static and generated each reset cycle
        if (attachedTo instanceof PermanentCard) {
            throw new IllegalArgumentException("Error, wrong code usage. If you want to add new ability to the "
                    + "permanent then use a permanent.addAbility(a, source, game): "
                    + ability.getClass().getCanonicalName() + " - " + ability);
        }
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
        for (CardState state : cardState.values()) {
            state.clearAbilities();
        }
        mageObjectAttribute.clear();
        this.setManaBurn(false);
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

    /**
     * Only used for diagnostic purposes of tests
     *
     * @return
     */
    public TriggeredAbilities getTriggers() {
        return triggers;
    }

    public CardState getCardState(UUID cardId) {
        cardState.putIfAbsent(cardId, new CardState());
        return cardState.get(cardId);
    }

    public MageObjectAttribute getMageObjectAttribute(UUID cardId) {
        return mageObjectAttribute.get(cardId);
    }

    public MageObjectAttribute getCreateMageObjectAttribute(MageObject mageObject, Game game) {
        MageObjectAttribute mageObjectAtt = mageObjectAttribute.computeIfAbsent(mageObject.getId(), k -> new MageObjectAttribute(mageObject, game));
        return mageObjectAtt;
    }

    public Map<MageObjectReference, Map<String, Object>> getPermanentCostsTags() {
        return permanentCostsTags;
    }

    /**
     * Store the tags of source ability using the MOR as a reference
     */
    void storePermanentCostsTags(MageObjectReference permanentMOR, Ability source) {
        if (source.getCostsTagMap() != null) {
            permanentCostsTags.put(permanentMOR, CardUtil.deepCopyObject(source.getCostsTagMap()));
        }
    }

    /**
     * Removes the cost tags if the corresponding permanent is no longer on the battlefield.
     * Only use if the stack is empty and nothing can refer to them anymore (such as at EOT, the current behavior)
     */
    public void cleanupPermanentCostsTags(Game game) {
        getPermanentCostsTags().entrySet().removeIf(entry ->
                !(entry.getKey().getZoneChangeCounter() == game.getState().getZoneChangeCounter(entry.getKey().getSourceId()) - 1)
        ); // The stored MOR is the stack-moment MOR so need to subtract one from the permanent's ZCC for the check
    }

    /**
     * Must add copy of the original watcher, e.g. from an ability
     */
    public void addWatcher(Watcher newWatcher) {
        this.watchers.add(newWatcher);
    }

    public void resetWatchers() {
        this.watchers.reset();
    }

    public int getZoneChangeCounter(UUID objectId) {
        return zoneChangeCounter.getOrDefault(objectId, 1);
    }

    public void updateZoneChangeCounter(UUID objectId) {
        int value = getZoneChangeCounter(objectId);
        value++;
        setZoneChangeCounter(objectId, value);

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

    /**
     * Make full copy of the card and all of the card's parts and put to the
     * game.
     *
     * @param mainCardToCopy
     * @param newController
     * @param game
     * @return
     */
    public Card copyCard(Card mainCardToCopy, UUID newController, Game game) {
        // runtime check
        if (!mainCardToCopy.getId().equals(mainCardToCopy.getMainCard().getId())) {
            // copyCard allows for main card only, if you catch it then check your targeting code
            throw new IllegalArgumentException("Wrong code usage. You can copy only main card.");
        }

        // must copy all card's parts
        // zcc and zone must be new cause zcc copy logic need card usage info here, but it haven't:
        // * reason 1: copied land must be played (+1 zcc), but copied spell must be put on stack and cast (+2 zcc)
        // * reason 2: copied card or spell can be used later as blueprint for real copies (see Epic ability)
        List<Card> copiedParts = new ArrayList<>();

        // main part (prepare must be called after other parts)
        Card copiedCard = mainCardToCopy.copy();
        copiedParts.add(copiedCard);

        // other parts
        if (copiedCard instanceof SplitCard) {
            // left
            SplitCardHalf leftOriginal = ((SplitCard) copiedCard).getLeftHalfCard();
            SplitCardHalf leftCopied = leftOriginal.copy();
            prepareCardForCopy(leftOriginal, leftCopied, newController);
            copiedParts.add(leftCopied);
            // right
            SplitCardHalf rightOriginal = ((SplitCard) copiedCard).getRightHalfCard();
            SplitCardHalf rightCopied = rightOriginal.copy();
            prepareCardForCopy(rightOriginal, rightCopied, newController);
            copiedParts.add(rightCopied);
            // sync parts
            ((SplitCard) copiedCard).setParts(leftCopied, rightCopied);
        } else if (copiedCard instanceof ModalDoubleFacedCard) {
            // left
            ModalDoubleFacedCardHalf leftOriginal = ((ModalDoubleFacedCard) copiedCard).getLeftHalfCard();
            ModalDoubleFacedCardHalf leftCopied = leftOriginal.copy();
            prepareCardForCopy(leftOriginal, leftCopied, newController);
            copiedParts.add(leftCopied);
            // right
            ModalDoubleFacedCardHalf rightOriginal = ((ModalDoubleFacedCard) copiedCard).getRightHalfCard();
            ModalDoubleFacedCardHalf rightCopied = rightOriginal.copy();
            prepareCardForCopy(rightOriginal, rightCopied, newController);
            copiedParts.add(rightCopied);
            // sync parts
            ((ModalDoubleFacedCard) copiedCard).setParts(leftCopied, rightCopied);
        } else if (copiedCard instanceof AdventureCard) {
            // right
            AdventureCardSpell rightOriginal = ((AdventureCard) copiedCard).getSpellCard();
            AdventureCardSpell rightCopied = rightOriginal.copy();
            prepareCardForCopy(rightOriginal, rightCopied, newController);
            copiedParts.add(rightCopied);
            // sync parts
            ((AdventureCard) copiedCard).setParts(rightCopied);
        }

        // main part prepare (must be called after other parts cause it change ids for all)
        prepareCardForCopy(mainCardToCopy, copiedCard, newController);

        // 707.12. An effect that instructs a player to cast a copy of an object (and not just copy a spell) follows the rules for casting spells, except that the copy is created in the same zone the object is in and then cast while another spell or ability is resolving.
        Zone copyToZone = game.getState().getZone(mainCardToCopy.getId());
        if (copyToZone == Zone.BATTLEFIELD) {
            throw new UnsupportedOperationException("Cards cannot be copied while on the Battlefield");
        }

        // add all parts to the game
        copiedParts.forEach(card -> {
            copiedCards.put(card.getId(), card);
            addCard(card, copyToZone);
        });

        // copied cards removes from game after battlefield/stack leaves, so remember it here as workaround to fix freeze, see https://github.com/magefree/mage/issues/5437
        // TODO: remove that workaround after LKI will be rewritten to support cross-steps/turns data transition and support copied cards
        copiedParts.forEach(card -> {
            this.setValue(COPIED_CARD_KEY + card.getId(), card.copy());
        });

        return copiedCard;
    }

    private void prepareCardForCopy(Card originalCard, Card copiedCard, UUID newController) {
        copiedCard.assignNewId();
        copiedCard.setOwnerId(newController);
        copiedCard.setCopy(true, originalCard);
    }

    public int getNextPermanentOrderNumber() {
        return permanentOrderNumber++;
    }

    public int getApplyEffectsCounter() {
        return applyEffectsCounter;
    }

    public void addPowerInsteadOfToughnessForDamageLethalityFilter(UUID source, FilterCreaturePermanent filter) {
        usePowerInsteadOfToughnessForDamageLethalityFilters.put(source, filter);
    }

    public List<FilterCreaturePermanent> getActivePowerInsteadOfToughnessForDamageLethalityFilters() {
        return usePowerInsteadOfToughnessForDamageLethalityFilters.isEmpty() ? emptyList() : getBattlefield().getAllActivePermanents().stream()
                .map(Card::getId)
                .filter(usePowerInsteadOfToughnessForDamageLethalityFilters::containsKey)
                .map(usePowerInsteadOfToughnessForDamageLethalityFilters::get)
                .collect(Collectors.toList());
    }

    boolean checkCommanderShouldStay(Card card, Game game) {
        return commandersToStay.stream().anyMatch(mor -> mor.refersTo(card, game));
    }

    void setCommanderShouldStay(Card card, Game game) {
        commandersToStay.add(new MageObjectReference(card, game));
    }

    public void setManaBurn(boolean manaBurn) {
        this.manaBurn = manaBurn;
    }

    public boolean isManaBurn() {
        return manaBurn;
    }

    boolean isHasDayNight() {
        return hasDayNight;
    }

    boolean setDaytime(boolean daytime) {
        boolean flag = this.hasDayNight && this.isDaytime != daytime;
        this.hasDayNight = true;
        this.isDaytime = daytime;
        return flag;
    }

    boolean isDaytime() {
        return isDaytime;
    }

    @Override
    public String toString() {
        return CardUtil.getTurnInfo(this);
    }

    public boolean setReverseTurnOrder(boolean reverse) {
        if (this.reverseTurnOrder && reverse) {
            this.reverseTurnOrder = false;
        } else {
            this.reverseTurnOrder = reverse;
        }
        return this.reverseTurnOrder;
    }

    public boolean getReverseTurnOrder() {
        return this.reverseTurnOrder;
    }
}
