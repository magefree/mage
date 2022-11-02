package mage.game;

import mage.MageException;
import mage.MageObject;
import mage.abilities.*;
import mage.abilities.common.AttachableToRestrictedAbility;
import mage.abilities.common.CantHaveMoreThanAmountCountersSourceAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffects;
import mage.abilities.effects.Effect;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.keyword.ShieldCounterEffect;
import mage.abilities.effects.keyword.StunCounterEffect;
import mage.abilities.keyword.*;
import mage.abilities.mana.DelayedTriggeredManaAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.actions.impl.MageAction;
import mage.cards.*;
import mage.cards.decks.Deck;
import mage.choices.Choice;
import mage.constants.*;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.designations.Designation;
import mage.designations.Initiative;
import mage.designations.Monarch;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.LegendRuleAppliesPredicate;
import mage.game.combat.Combat;
import mage.game.command.*;
import mage.game.command.dungeons.UndercityDungeon;
import mage.game.events.*;
import mage.game.events.TableEvent.EventType;
import mage.game.mulligan.Mulligan;
import mage.game.permanent.Battlefield;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.stack.Spell;
import mage.game.stack.SpellStack;
import mage.game.stack.StackObject;
import mage.game.turn.Phase;
import mage.game.turn.Step;
import mage.game.turn.Turn;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.players.PlayerList;
import mage.players.Players;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.util.CardUtil;
import mage.util.GameLog;
import mage.util.MessageToClient;
import mage.util.RandomUtil;
import mage.util.functions.CopyApplier;
import mage.watchers.Watcher;
import mage.watchers.common.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Game object. It must contain static data (e.g. no changeable in the game like game settings)
 * <p>
 * "transient field" logic uses for serialization/replays (mark temporary fields as transient,
 * also look for non restored fields in copy constructor for details)
 * <p>
 * WARNING, if you add new fields then don't forget to add it to copy constructor (deep copy, not ref).
 * If it's a temporary/auto-generated data then mark that field as transient and comment in copy constructor.
 */
public abstract class GameImpl implements Game {

    private static final int ROLLBACK_TURNS_MAX = 4;
    private static final String UNIT_TESTS_ERROR_TEXT = "Error in unit tests";
    private static final Logger logger = Logger.getLogger(GameImpl.class);

    private transient Object customData; // temporary data, used in AI simulations
    private transient Player losingPlayer; // temporary data, used in AI simulations
    protected boolean simulation = false;
    protected boolean checkPlayableState = false;

    protected final UUID id;

    protected boolean ready;
    protected transient TableEventSource tableEventSource = new TableEventSource();
    protected transient PlayerQueryEventSource playerQueryEventSource = new PlayerQueryEventSource();

    protected Map<UUID, Card> gameCards = new HashMap<>();
    protected Map<UUID, MeldCard> meldCards = new HashMap<>(0);

    protected Map<Zone, Map<UUID, MageObject>> lki = new EnumMap<>(Zone.class);
    protected Map<Zone, Map<UUID, CardState>> lkiCardState = new EnumMap<>(Zone.class);
    protected Map<UUID, Map<Integer, MageObject>> lkiExtended = new HashMap<>();
    // Used to check if an object was moved by the current effect in resolution (so Wrath like effect can be handled correctly)
    protected Map<Zone, Set<UUID>> lkiShortLiving = new EnumMap<>(Zone.class);

    // Permanents entering the Battlefield while handling replacement effects before they are added to the battlefield
    protected Map<UUID, Permanent> permanentsEntering = new HashMap<>();
    // used to set the counters a permanent adds the battlefield (if no replacement effect is used e.g. Persist)
    protected Map<UUID, Counters> enterWithCounters = new HashMap<>();

    protected GameState state;
    private transient Stack<Integer> savedStates = new Stack<>();
    protected transient GameStates gameStates = new GameStates();

    // game states to allow player rollback
    protected transient Map<Integer, GameState> gameStatesRollBack = new HashMap<>();
    protected transient boolean executingRollback;
    protected transient int turnToGoToForRollback;

    protected Date startTime;
    protected Date endTime;
    protected UUID startingPlayerId;
    protected UUID winnerId;
    protected boolean gameStopped = false;

    protected RangeOfInfluence range;
    protected Mulligan mulligan;

    protected MultiplayerAttackOption attackOption;
    protected GameOptions gameOptions;
    protected String startMessage;

    private boolean scopeRelevant = false; // replacement effects: used to indicate that currently applied replacement effects have to check for scope relevance (614.12 13/01/18)
    private boolean saveGame = false; // replay code, not done
    private int priorityTime; // match time limit
    private final int startingLife;
    private final int startingHandSize;
    protected transient PlayerList playerList; // auto-generated from state, don't copy

    // infinite loop check (temporary data, do not copy)
    private transient int infiniteLoopCounter; // used to check if the game is in an infinite loop
    private transient int lastNumberOfAbilitiesOnTheStack; // used to check how long no new ability was put to stack
    private transient List<Integer> lastPlayersLifes = null; // if life is going down, it's no infinite loop
    private transient final LinkedList<UUID> stackObjectsCheck = new LinkedList<>(); // used to check if different sources used the stack

    // temporary store for income concede commands, don't copy
    private final LinkedList<UUID> concedingPlayers = new LinkedList<>();

    public GameImpl(MultiplayerAttackOption attackOption, RangeOfInfluence range, Mulligan mulligan, int startingLife, int startingHandSize) {
        this.id = UUID.randomUUID();
        this.range = range;
        this.mulligan = mulligan;
        this.attackOption = attackOption;
        this.state = new GameState();
        this.startingLife = startingLife;
        this.executingRollback = false;
        this.startingHandSize = startingHandSize;

        initGameDefaultWatchers();
    }

    public GameImpl(final GameImpl game) {
        //this.customData = game.customData; // temporary data, no need on game copy
        //this.losingPlayer = game.losingPlayer; // temporary data, no need on game copy
        this.simulation = game.simulation;
        this.checkPlayableState = game.checkPlayableState;

        this.id = game.id;

        this.ready = game.ready;
        //this.tableEventSource = game.tableEventSource; // client-server part, not need on copy/simulations
        //this.playerQueryEventSource = game.playerQueryEventSource; // client-server part, not need on copy/simulations

        for (Entry<UUID, Card> entry : game.gameCards.entrySet()) {
            this.gameCards.put(entry.getKey(), entry.getValue().copy());
        }
        for (Entry<UUID, MeldCard> entry : game.meldCards.entrySet()) {
            this.meldCards.put(entry.getKey(), entry.getValue().copy());
        }

        // lki
        for (Entry<Zone, Map<UUID, MageObject>> entry : game.lki.entrySet()) {
            Map<UUID, MageObject> lkiMap = new HashMap<>();
            for (Entry<UUID, MageObject> entryMap : entry.getValue().entrySet()) {
                lkiMap.put(entryMap.getKey(), entryMap.getValue().copy());
            }
            this.lki.put(entry.getKey(), lkiMap);
        }
        // lkiCardState
        for (Entry<Zone, Map<UUID, CardState>> entry : game.lkiCardState.entrySet()) {
            Map<UUID, CardState> lkiMap = new HashMap<>();
            for (Entry<UUID, CardState> entryMap : entry.getValue().entrySet()) {
                lkiMap.put(entryMap.getKey(), entryMap.getValue().copy());
            }
            this.lkiCardState.put(entry.getKey(), lkiMap);
        }
        // lkiExtended
        for (Entry<UUID, Map<Integer, MageObject>> entry : game.lkiExtended.entrySet()) {
            Map<Integer, MageObject> lkiMap = new HashMap<>();
            for (Entry<Integer, MageObject> entryMap : entry.getValue().entrySet()) {
                lkiMap.put(entryMap.getKey(), entryMap.getValue().copy());
            }
            this.lkiExtended.put(entry.getKey(), lkiMap);
        }
        // lkiShortLiving
        for (Entry<Zone, Set<UUID>> entry : game.lkiShortLiving.entrySet()) {
            this.lkiShortLiving.put(entry.getKey(), new HashSet<>(entry.getValue()));
        }

        for (Entry<UUID, Permanent> entry : game.permanentsEntering.entrySet()) {
            this.permanentsEntering.put(entry.getKey(), entry.getValue().copy());
        }
        for (Entry<UUID, Counters> entry : game.enterWithCounters.entrySet()) {
            this.enterWithCounters.put(entry.getKey(), entry.getValue().copy());
        }

        this.state = game.state.copy();
        // client-server part, not need on copy/simulations:
        /*
        this.savedStates = game.savedStates;
        this.gameStates = game.gameStates;
        this.gameStatesRollBack = game.gameStatesRollBack;
        this.executingRollback = game.executingRollback;
        this.turnToGoToForRollback = game.turnToGoToForRollback;
        */

        this.startTime = game.startTime;
        this.endTime = game.endTime;
        this.startingPlayerId = game.startingPlayerId;
        this.winnerId = game.winnerId;
        this.gameStopped = game.gameStopped;

        this.range = game.range;
        this.mulligan = game.mulligan.copy();

        this.attackOption = game.attackOption;
        this.gameOptions = game.gameOptions.copy();
        this.startMessage = game.startMessage;

        this.scopeRelevant = game.scopeRelevant;
        this.saveGame = game.saveGame;
        this.priorityTime = game.priorityTime;
        this.startingLife = game.startingLife;
        this.startingHandSize = game.startingHandSize;
        //this.playerList = game.playerList; // auto-generated list, don't copy

        // loop check code, no need to copy
        /*
        this.infiniteLoopCounter = game.infiniteLoopCounter;
        this.lastNumberOfAbilitiesOnTheStack = game.lastNumberOfAbilitiesOnTheStack;
        this.lastPlayersLifes = game.lastPlayersLifes;
        this.stackObjectsCheck = game.stackObjectsCheck;
         */
    }

    @Override
    public boolean isSimulation() {
        return simulation;
    }

    @Override
    public void setSimulation(boolean simulation) {
        this.simulation = simulation;
    }

    @Override
    public void setCheckPlayableState(boolean checkPlayableState) {
        this.checkPlayableState = checkPlayableState;
    }

    @Override
    public boolean inCheckPlayableState() {
        return checkPlayableState;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Object getCustomData() {
        return customData;
    }

    @Override
    public void setCustomData(Object data) {
        this.customData = data;
    }

    @Override
    public GameOptions getOptions() {
        if (gameOptions != null) {
            return gameOptions;
        }
        return new GameOptions(); // happens during the first game updates
    }

    @Override
    public void loadCards(Set<Card> cards, UUID ownerId) {
        for (Card card : cards) {
            if (card instanceof PermanentCard) {
                card = ((PermanentCard) card).getCard();
            }

            // init each card by parts... if you add new type here then getInitAbilities must be
            // implemented too (it allows to split abilities between card and parts)

            // main card
            card.setOwnerId(ownerId);
            addCardToState(card);

            // parts
            if (card instanceof SplitCard) {
                // left
                Card leftCard = ((SplitCard) card).getLeftHalfCard();
                leftCard.setOwnerId(ownerId);
                addCardToState(leftCard);
                // right
                Card rightCard = ((SplitCard) card).getRightHalfCard();
                rightCard.setOwnerId(ownerId);
                addCardToState(rightCard);
            } else if (card instanceof ModalDoubleFacesCard) {
                // left
                Card leftCard = ((ModalDoubleFacesCard) card).getLeftHalfCard();
                leftCard.setOwnerId(ownerId);
                addCardToState(leftCard);
                // right
                Card rightCard = ((ModalDoubleFacesCard) card).getRightHalfCard();
                rightCard.setOwnerId(ownerId);
                addCardToState(rightCard);
            } else if (card instanceof AdventureCard) {
                Card spellCard = ((AdventureCard) card).getSpellCard();
                spellCard.setOwnerId(ownerId);
                addCardToState(spellCard);
            }
        }
    }

    private void addCardToState(Card card) {
        gameCards.put(card.getId(), card);
        state.addCard(card);
    }

    @Override
    public Collection<Card> getCards() {
        return gameCards.values();
    }

    @Override
    public void addMeldCard(UUID meldId, MeldCard meldCard) {
        meldCards.put(meldId, meldCard);
    }

    @Override
    public MeldCard getMeldCard(UUID meldId) {
        return meldCards.get(meldId);
    }

    @Override
    public void addPlayer(Player player, Deck deck) {
        player.useDeck(deck, this);
        state.addPlayer(player);
        initPlayerDefaultWatchers(player.getId());
    }

    @Override
    public RangeOfInfluence getRangeOfInfluence() {
        return range;
    }

    @Override
    public MultiplayerAttackOption getAttackOption() {
        return attackOption;
    }

    @Override
    public Player getPlayer(UUID playerId) {
        if (playerId == null) {
            return null;
        }
        return state.getPlayer(playerId);
    }

    @Override
    public Player getPlayerOrPlaneswalkerController(UUID playerId) {
        Player player = getPlayer(playerId);
        if (player != null) {
            return player;
        }
        Permanent permanent = getPermanent(playerId);
        if (permanent == null) {
            return null;
        }
        player = getPlayer(permanent.getControllerId());
        return player;
    }

    @Override
    public MageObject getObject(UUID objectId) {
        if (objectId == null) {
            return null;
        }
        MageObject object;
        if (state.getBattlefield().containsPermanent(objectId)) {
            object = state.getBattlefield().getPermanent(objectId);
            return object;
        }
        if (getPermanentsEntering().containsKey(objectId)) {
            return getPermanentEntering(objectId);
        }
        for (StackObject item : state.getStack()) {
            if (item.getId().equals(objectId)) {
                return item;
            }
            if (item instanceof Spell && item.getSourceId().equals(objectId)) {
                return item;
            }
        }

        for (CommandObject commandObject : state.getCommand()) {
            if (commandObject.getId().equals(objectId)) {
                return commandObject;
            }
        }

        object = getCard(objectId);

        if (object == null) {
            for (Designation designation : state.getDesignations()) {
                if (designation.getId().equals(objectId)) {
                    return designation;
                }
            }
            // can be an ability of a sacrificed Token trying to get it's source object
            object = getLastKnownInformation(objectId, Zone.BATTLEFIELD);
        }

        return object;
    }

    @Override
    public MageObject getObject(Ability source) {
        return source != null ? getObject(source.getSourceId()) : null;
    }

    /**
     * Get permanent, card or command object (not spell or ability on the stack)
     *
     * @param objectId
     * @return
     */
    @Override
    public MageObject getBaseObject(UUID objectId) {
        if (objectId == null) {
            return null;
        }
        MageObject object;
        if (state.getBattlefield().containsPermanent(objectId)) {
            object = state.getBattlefield().getPermanent(objectId);
            return object;
        }
        // can be an ability of a sacrificed Token trying to get it's source object
        object = getLastKnownInformation(objectId, Zone.BATTLEFIELD);
        if (object != null) {
            return object;
        }
        for (CommandObject commandObject : state.getCommand()) {
            if (commandObject instanceof Commander && commandObject.getId().equals(objectId)) {
                return commandObject;
            }
        }
        object = getCard(objectId);
        if (object == null) {
            for (CommandObject commandObject : state.getCommand()) {
                if (commandObject.getId().equals(objectId)) {
                    return commandObject;
                }
            }
        }
        return object;
    }

    @Override
    public MageObject getEmblem(UUID objectId) {
        if (objectId == null) {
            return null;
        }
        for (CommandObject commandObject : state.getCommand()) {
            if (commandObject.getId().equals(objectId)) {
                return commandObject;
            }
        }
        return null;
    }

    @Override
    public Dungeon getDungeon(UUID objectId) {
        return state
                .getCommand()
                .stream()
                .filter(commandObject -> commandObject.getId().equals(objectId))
                .filter(Dungeon.class::isInstance)
                .map(Dungeon.class::cast)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Dungeon getPlayerDungeon(UUID playerId) {
        return state
                .getCommand()
                .stream()
                .filter(commandObject -> commandObject.isControlledBy(playerId))
                .filter(Dungeon.class::isInstance)
                .map(Dungeon.class::cast)
                .findFirst()
                .orElse(null);
    }

    private void removeDungeon(Dungeon dungeon) {
        if (dungeon == null) {
            return;
        }
        Player player = getPlayer(dungeon.getControllerId());
        if (player != null) {
            informPlayers(player.getLogName() + " has completed " + dungeon.getLogName());
        }
        state.getCommand().remove(dungeon);
        fireEvent(GameEvent.getEvent(
                GameEvent.EventType.DUNGEON_COMPLETED, dungeon.getId(), null,
                dungeon.getControllerId(), dungeon.getName(), 0
        ));
    }

    private Dungeon getOrCreateDungeon(UUID playerId, boolean undercity) {
        Dungeon dungeon = this.getPlayerDungeon(playerId);
        if (dungeon != null && dungeon.hasNextRoom()) {
            return dungeon;
        }
        removeDungeon(dungeon);
        return this.addDungeon(undercity ? new UndercityDungeon() : Dungeon.selectDungeon(playerId, this), playerId);
    }

    @Override
    public void ventureIntoDungeon(UUID playerId, boolean undercity) {
        if (playerId == null) {
            return;
        }
        if (replaceEvent(GameEvent.getEvent(GameEvent.EventType.VENTURE, playerId, null, playerId))) {
            return;
        }
        this.getOrCreateDungeon(playerId, undercity).moveToNextRoom(playerId, this);
        fireEvent(GameEvent.getEvent(GameEvent.EventType.VENTURED, playerId, null, playerId));
    }

    @Override
    public boolean hasDayNight() {
        return state.isHasDayNight();
    }

    @Override
    public void setDaytime(boolean daytime) {
        if (!state.isHasDayNight()) {
            informPlayers("It has become " + (daytime ? "day" : "night"));
        }
        if (!state.setDaytime(daytime)) {
            return;
        }
        // TODO: add day/night sound effect
        informPlayers("It has become " + (daytime ? "day" : "night"));
        fireEvent(GameEvent.getEvent(GameEvent.EventType.BECOMES_DAY_NIGHT, null, null, null));
        for (Permanent permanent : state.getBattlefield().getAllPermanents()) {
            if ((daytime && permanent.getAbilities(this).containsClass(NightboundAbility.class))
                    || (!daytime && permanent.getAbilities(this).containsClass(DayboundAbility.class))) {
                permanent.transform(null, this, true);
            }
        }
    }

    @Override
    public boolean checkDayNight(boolean daytime) {
        return state.isHasDayNight() && state.isDaytime() == daytime;
    }

    @Override
    public UUID getOwnerId(UUID objectId) {
        return getOwnerId(getObject(objectId));
    }

    @Override
    public UUID getOwnerId(MageObject object) {
        if (object instanceof Spell) {
            return ((Spell) object).getOwnerId();
        }

        if (object instanceof StackObject) {
            // maybe this is not correct in all cases?
            return ((StackObject) object).getControllerId();
        }

        if (object instanceof CommandObject) {
            return ((CommandObject) object).getControllerId();
        }

        if (object instanceof Card) {
            return ((Card) object).getOwnerId();
        }

        return null;
    }

    @Override
    public UUID getControllerId(UUID objectId) {
        if (objectId == null) {
            return null;
        }
        MageObject object = getObject(objectId);
        if (object != null) {
            if (object instanceof StackObject) {
                return ((StackObject) object).getControllerId();
            } else if (object instanceof Permanent) {
                return ((Permanent) object).getControllerId();
            } else if (object instanceof CommandObject) {
                return ((CommandObject) object).getControllerId();
            }
            UUID controllerId = getContinuousEffects().getControllerOfSourceId(objectId);
            if (controllerId != null) {
                return controllerId;
            }
            // TODO: When is a player the damage source itself? If not possible remove this
            Player player = getPlayer(objectId);
            if (player != null) {
                return player.getId();
            }
            // No object with controller found so return owner if possible
            if (object instanceof Card) {
                return ((Card) object).getOwnerId();
            }
        }
        return null;
    }

    @Override
    public Spell getSpell(UUID spellId) {
        return state.getStack().getSpell(spellId);
    }

    /**
     * Given the UUID of a spell, this method returns the spell object. If the current game
     * state does not contain a spell with the given UUID, this method checks the last known
     * information on the stack to look for the spell.
     *
     * @param spellId - The UUID of a spell to retrieve from the current game state
     * @return - The spell object with the given UUID, or null if no spell with the given UUID
     * is found
     */
    @Override
    public Spell getSpellOrLKIStack(UUID spellId) {
        Spell spell = state.getStack().getSpell(spellId);
        if (spell == null) {
            MageObject obj = this.getLastKnownInformation(spellId, Zone.STACK);
            // Copied activated abilities may also be retrieved from the stack here.
            // This check that obj is instanceof Spell is necessary to avoid throwing
            // a ClassCastException, as a StackAbility cannot be cast to Spell. See
            // SyrCarahTheBoldTest.java for an example of when this check is relevant.
            if (obj instanceof Spell) {
                spell = (Spell) obj;
            } else if (obj != null) {
                logger.error(String.format(
                        "getSpellOrLKIStack got non-spell id %s correlating to non-spell object %s.",
                        obj.getClass().getName(), obj.getName()),
                        new Throwable()
                );
            }
        }
        return spell;
    }

    @Override
    public Permanent getPermanent(UUID permanentId) {
        return state.getPermanent(permanentId);
    }

    @Override
    public Permanent getPermanentOrLKIBattlefield(UUID permanentId) {
        Permanent permanent = state.getPermanent(permanentId);
        if (permanent == null) {
            permanent = (Permanent) this.getLastKnownInformation(permanentId, Zone.BATTLEFIELD);
        }
        return permanent;
    }

    @Override
    public Permanent getPermanentEntering(UUID permanentId) {
        return permanentsEntering.get(permanentId);
    }

    @Override
    public Map<UUID, Permanent> getPermanentsEntering() {
        return permanentsEntering;
    }

    @Override
    public Card getCard(UUID cardId) {
        if (cardId == null) {
            return null;
        }
        Card card = gameCards.get(cardId);
        if (card == null) {
            card = state.getCopiedCard(cardId);
        }
        if (card == null) {
            card = this.getMeldCard(cardId);
        }

        // copied cards removes, but delayed triggered possible from it, see https://github.com/magefree/mage/issues/5437
        // TODO: remove that workround after LKI rework, see GameState.copyCard
        if (card == null) {
            card = (Card) state.getValue(GameState.COPIED_CARD_KEY + cardId);
        }
        return card;
    }

    @Override
    public Optional<Ability> getAbility(UUID abilityId, UUID sourceId) {
        MageObject object = getObject(sourceId);
        if (object != null) {
            return object.getAbilities().get(abilityId);
        }
        return Optional.empty();
    }

    @Override
    public void setZone(UUID objectId, Zone zone) {
        state.setZone(objectId, zone);
    }

    @Override
    public GameStates getGameStates() {
        return gameStates;
    }

    @Override
    public void loadGameStates(GameStates states) {
        this.gameStates = states;
    }

    @Override
    public void saveState(boolean bookmark) {
        if (!simulation && gameStates != null) {
            if (bookmark || saveGame) {
                gameStates.save(state);
            }
        }
    }

    @Override
    public void setConcedingPlayer(UUID playerId) {
        Player player = null;
        if (state.getChoosingPlayerId() != null) {
            player = getPlayer(state.getChoosingPlayerId());
        } else if (state.getPriorityPlayerId() != null) {
            player = getPlayer(state.getPriorityPlayerId());
        }
        if (player != null) {
            if (!player.hasLeft() && player.isHuman()) {
                if (!concedingPlayers.contains(playerId)) {
                    logger.debug("Game over for player Id: " + playerId + " gameId " + getId());
                    concedingPlayers.add(playerId);
                    player.signalPlayerConcede();
                }
            } else {
                // no asynchronous action so check directly
                concedingPlayers.add(playerId);
                checkConcede();
            }
        } else {
            checkConcede();
            checkIfGameIsOver();
        }
    }

    public void checkConcede() {
        while (!concedingPlayers.isEmpty()) {
            leave(concedingPlayers.removeFirst());
        }
    }

    @Override
    public boolean checkIfGameIsOver() {
        if (state.isGameOver()) {
            return true;
        }
        int remainingPlayers = 0;
        int numLosers = 0;
        for (Player player : state.getPlayers().values()) {
            if (!player.hasLeft()) {
                remainingPlayers++;
            }
            if (player.hasLost()) {
                numLosers++;
            }
        }
        if (remainingPlayers <= 1 || numLosers >= state.getPlayers().size() - 1) {
            end();
            if (remainingPlayers == 0 && logger.isDebugEnabled()) {
                logger.debug("DRAW for gameId: " + getId());
                for (Player player : state.getPlayers().values()) {
                    logger.debug("-- " + player.getName() + " left: " + (player.hasLeft() ? "Y" : "N") + " lost: " + (player.hasLost() ? "Y" : "N"));
                }
            }
            for (Player player : state.getPlayers().values()) {
                if (!player.hasLeft() && !player.hasLost()) {
                    logger.debug("Player " + player.getName() + " has won gameId: " + this.getId());
                    player.won(this);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean hasEnded() {
        return endTime != null;
    }

    @Override
    public boolean isADraw() {
        return hasEnded() && winnerId == null;
    }

    @Override
    public String getWinner() {
        if (winnerId == null) {
            return "Game is a draw";
        }
        return "Player " + state.getPlayer(winnerId).getName() + " is the winner";
    }

    @Override
    public GameState getState() {
        return state;
    }

    @Override
    public int bookmarkState() {
        if (!simulation) {
            saveState(true);
            if (logger.isTraceEnabled()) {
                logger.trace("Bookmarking state: " + gameStates.getSize());
            }
            savedStates.push(gameStates.getSize() - 1);
            return savedStates.size();
        }
        return savedStates.size();
    }

    /**
     * Warning, for inner usage only, use player.restoreState as much as possible instead
     *
     * @param bookmark
     * @param context  additional information for error message
     * @return current restored state (if all fine)
     */
    @Override
    public GameState restoreState(int bookmark, String context) {
        if (!simulation && !this.hasEnded()) { // if player left or game is over no undo is possible - this could lead to wrong winner
            if (bookmark != 0) {
                if (!savedStates.contains(bookmark - 1)) {
                    if (!savedStates.isEmpty()) { // empty if rollback to a turn was requested before, otherwise unclear why
                        logger.error("It was not possible to do the requested undo operation (bookmark " + (bookmark - 1) + " does not exist) context: " + context);
                        logger.info("Saved states: " + savedStates.toString());
                    }
                } else {
                    int stateNum = savedStates.get(bookmark - 1);
                    removeBookmark(bookmark);
                    GameState restore = gameStates.rollback(stateNum);
                    if (restore != null) {
                        state.restore(restore);
                        playerList.setCurrent(state.getPlayerByOrderId());
                        return state;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void removeBookmark(int bookmark
    ) {
        if (!simulation) {
            if (bookmark != 0) {
                while (savedStates.size() > bookmark) {
                    savedStates.pop();
                }
                gameStates.remove(bookmark);
            }
        }
    }

    private void clearAllBookmarks() {
        if (!simulation) {
            while (!savedStates.isEmpty()) {
                savedStates.pop();
            }
            gameStates.remove(0);
            for (Player player : getPlayers().values()) {
                player.setStoredBookmark(-1);
            }
        }
    }

    @Override
    public int getSavedStateSize() {
        if (!simulation) {
            return savedStates.size();
        }
        return 0;
    }

    @Override
    public void cleanUp() {
        gameCards.clear();
        meldCards.clear();
    }

    @Override
    public void start(UUID choosingPlayerId) {
        startTime = new Date();
        if (state.getPlayers().values().iterator().hasNext()) {
            init(choosingPlayerId);
            play(startingPlayerId);
        }
    }

    protected void play(UUID nextPlayerId) {
        boolean forcedToFinished = false;
        if (!isPaused() && !checkIfGameIsOver()) {
            playerList = state.getPlayerList(nextPlayerId);
            Player playerByOrder = getPlayer(playerList.get());
            state.setPlayerByOrderId(playerByOrder == null ? null : playerByOrder.getId());

            // PLAY game
            while (!isPaused() && !checkIfGameIsOver()) {
                if (!playExtraTurns()) {
                    break;
                }
                if (playerByOrder == null) {
                    logger.error("Can't find next player by order, but game stil run. Finish it.");
                    forcedToFinished = true;
                    break;
                }

                GameEvent event = new GameEvent(GameEvent.EventType.PLAY_TURN, null, null, playerByOrder.getId());
                if (!replaceEvent(event)) {
                    if (!playTurn(playerByOrder)) {
                        break;
                    }
                }
                if (!playExtraTurns()) {
                    break;
                }
                playerByOrder = playerList.getNext(this, true);
                if (playerByOrder != null) {
                    state.setPlayerByOrderId(playerByOrder.getId());
                }
            }
        }

        // END game
        if (checkIfGameIsOver() && !isSimulation() || forcedToFinished) {
            winnerId = findWinnersAndLosers();
            StringBuilder sb = new StringBuilder("GAME END gameId: ").append(this.getId()).append(' ');
            int count = 0;
            for (Player player : this.getState().getPlayers().values()) {
                if (count > 0) {
                    sb.append(" - ");
                }
                sb.append('[').append(player.getName()).append(" => ");
                sb.append(player.hasWon() ? "W" : "");
                sb.append(player.hasLost() ? "L" : "");
                sb.append(player.hasQuit() ? "Q" : "");
                sb.append(player.hasIdleTimeout() ? "I" : "");
                sb.append(player.hasTimerTimeout() ? "T" : "");
                sb.append(']');
                count++;
            }
            logger.debug(sb.toString());
        }
    }

    private boolean playExtraTurns() {
        //20091005 - 500.7
        TurnMod extraTurn = getNextExtraTurn();
        while (extraTurn != null) {
            GameEvent event = new GameEvent(GameEvent.EventType.PLAY_TURN, null, null, extraTurn.getPlayerId());
            if (!replaceEvent(event)) {
                Player extraPlayer = this.getPlayer(extraTurn.getPlayerId());
                if (extraPlayer != null && extraPlayer.canRespond()) {
                    state.setExtraTurn(true);
                    state.setTurnId(extraTurn.getId());
                    if (!this.isSimulation()) {
                        informPlayers(extraPlayer.getLogName() + " takes an extra turn");
                    }
                    if (!playTurn(extraPlayer)) {
                        return false;
                    }
                }
            }
            extraTurn = getNextExtraTurn();
        }
        state.setTurnId(null);
        state.setExtraTurn(false);
        return true;
    }

    private TurnMod getNextExtraTurn() {
        boolean checkForExtraTurn = true;
        while (checkForExtraTurn) {
            TurnMod extraTurn = getState().getTurnMods().getNextExtraTurn();
            if (extraTurn != null) {
                GameEvent event = new GameEvent(GameEvent.EventType.EXTRA_TURN, extraTurn.getId(), null, extraTurn.getPlayerId());
                if (!replaceEvent(event)) {
                    return extraTurn;
                }
            } else {
                checkForExtraTurn = false;
            }
        }
        return null;
    }

    private boolean playTurn(Player player) {
        boolean skipTurn = false;
        do {
            if (executingRollback) {
                rollbackTurnsExecution(turnToGoToForRollback);
                player = getPlayer(state.getActivePlayerId());
            } else {
                state.setActivePlayerId(player.getId());
                saveRollBackGameState();
            }
            if (checkStopOnTurnOption()) {
                return false;
            }
            skipTurn = state.getTurn().play(this, player);
        } while (executingRollback);

        if (isPaused() || checkIfGameIsOver()) {
            return false;
        }
        if (!skipTurn) {
            endOfTurn();
            state.setTurnNum(state.getTurnNum() + 1);
        }

        return true;
    }

    @Override
    public void resume() {
        playerList = state.getPlayerList(state.getActivePlayerId());
        Player player = getPlayer(playerList.get());
        boolean wasPaused = state.isPaused();
        state.resume();
        if (!checkIfGameIsOver()) {
            fireInformEvent("Turn " + state.getTurnNum());
            if (checkStopOnTurnOption()) {
                return;
            }
            state.getTurn().resumePlay(this, wasPaused);
            if (!isPaused() && !checkIfGameIsOver()) {
                endOfTurn();
                Player nextPlayer = playerList.getNext(this, true);
                if (nextPlayer != null) {
                    player = nextPlayer;
                }
                state.setTurnNum(state.getTurnNum() + 1);
            }
        }
        play(player.getId());
    }

    private boolean checkStopOnTurnOption() {
        if (gameOptions.stopOnTurn != null && gameOptions.stopAtStep == PhaseStep.UNTAP) {
            if (gameOptions.stopOnTurn.equals(state.getTurnNum())) {
                winnerId = null; //DRAW
                saveState(false);
                return true;
            }
        }
        return false;
    }

    protected void init(UUID choosingPlayerId) {
        for (Player player : state.getPlayers().values()) {
            player.beginTurn(this);
            // init only if match is with timer (>0) and time left was not set yet (== MAX_VALUE).
            // otherwise the priorityTimeLeft is set in {@link MatchImpl.initGame)
            if (priorityTime > 0 && player.getPriorityTimeLeft() == Integer.MAX_VALUE) {
                initTimer(player.getId());
            }
        }
        if (startMessage == null || startMessage.isEmpty()) {
            startMessage = "Game has started";
        }
        fireStatusEvent(startMessage, false, false);

        saveState(false);

        if (checkIfGameIsOver()) {
            return;
        }

        // Apply shield counter mechanic from SNC
        state.addAbility(new SimpleStaticAbility(Zone.ALL, new ShieldCounterEffect()), null);

        // Apply stun counter mechanic
        state.addAbility(new SimpleStaticAbility(Zone.ALL, new StunCounterEffect()), null);

        // Handle companions
        Map<Player, Card> playerCompanionMap = new HashMap<>();
        for (Player player : state.getPlayers().values()) {
            // Make a list of legal companions present in the sideboard
            Set<Card> cards = new HashSet<>(player.getLibrary().getCards(this));
            Set<Card> potentialCompanions = new HashSet<>();
            for (Card card : player.getSideboard().getUniqueCards(this)) {
                for (Ability ability : card.getAbilities(this)) {
                    if (ability instanceof CompanionAbility) {
                        CompanionAbility companionAbility = (CompanionAbility) ability;
                        if (companionAbility.isLegal(cards, startingHandSize)) {
                            potentialCompanions.add(card);
                            break;
                        }
                    }
                }
            }
            // Choose a companion from the list of legal companions
            for (Card card : potentialCompanions) {
                if (player.chooseUse(Outcome.Benefit, "Use " + card.getLogName() + " as your companion?", null, this)) {
                    playerCompanionMap.put(player, card);
                    break;
                }
            }
        }

        // Announce companions and set the companion effect
        playerCompanionMap.forEach((player, companion) -> {
            if (companion != null) {
                this.informPlayers(player.getLogName() + " has chosen " + companion.getLogName() + " as their companion.");
                this.getState().getCompanion().update(player.getName() + "'s companion", new CardsImpl(companion));
            }
        });

        //20091005 - 103.1
        if (!gameOptions.skipInitShuffling) { //don't shuffle in test mode for card injection on top of player's libraries
            for (Player player : state.getPlayers().values()) {
                player.shuffleLibrary(null, this);
            }
        }

        //20091005 - 103.2
        Player choosingPlayer = null;
        if (startingPlayerId == null) {
            TargetPlayer targetPlayer = new TargetPlayer();
            targetPlayer.setTargetName("starting player");
            if (choosingPlayerId != null) {
                choosingPlayer = this.getPlayer(choosingPlayerId);
                if (choosingPlayer != null && !choosingPlayer.canRespond()) {
                    choosingPlayer = null;
                }
            }
            if (choosingPlayer == null) {
                choosingPlayerId = pickChoosingPlayer();
                if (choosingPlayerId == null) {
                    return;
                }
                choosingPlayer = getPlayer(choosingPlayerId);
            }
            if (choosingPlayer == null) {
                return;
            }
            getState().setChoosingPlayerId(choosingPlayerId); // needed to start/stop the timer if active
            if (choosingPlayer.choose(Outcome.Benefit, targetPlayer, null, this)) {
                startingPlayerId = targetPlayer.getTargets().get(0);
            } else if (getState().getPlayers().size() < 3) {
                // not possible to choose starting player, choosing player has probably conceded, so stop here
                return;
            }
        }
        if (startingPlayerId == null) {
            // choose any available player as starting player
            for (Player player : state.getPlayers().values()) {
                if (player.canRespond()) {
                    startingPlayerId = player.getId();
                    break;
                }
            }
            if (startingPlayerId == null) {
                return;
            }
        }
        Player startingPlayer = state.getPlayer(startingPlayerId);
        if (startingPlayer == null) {
            logger.debug("Starting player not found. playerId:" + startingPlayerId);
            return;
        }
        sendStartMessage(choosingPlayer, startingPlayer);

        //20091005 - 103.3
        int startingHandSize = 7;
        for (UUID playerId : state.getPlayerList(startingPlayerId)) {
            Player player = getPlayer(playerId);
            if (!gameOptions.testMode || player.getLife() == 0) {
                player.initLife(this.getStartingLife());
            }
            if (!gameOptions.testMode) {
                player.drawCards(startingHandSize, null, this);
            }
        }

        //20091005 - 103.4
        mulligan.executeMulliganPhase(this, startingHandSize);
        getState().setChoosingPlayerId(null);
        state.resetWatchers(); // watcher objects from cards are reused during match so reset all card watchers already added

        //20100716 - 103.5
        for (UUID playerId : state.getPlayerList(startingPlayerId)) {
            Player player = getPlayer(playerId);
            Cards cardsWithOpeningAction = new CardsImpl();
            for (Card card : player.getHand().getCards(this)) {
                for (Ability ability : card.getAbilities(this)) {
                    if (ability instanceof OpeningHandAction) {
                        OpeningHandAction action = (OpeningHandAction) ability;
                        if (action.isOpeningHandActionAllowed(card, player, this)) {
                            cardsWithOpeningAction.add(card);
                        }
                    }
                }
            }
            while (!cardsWithOpeningAction.isEmpty() && player.canRespond()) {
                Card card;
                if (cardsWithOpeningAction.size() > 1) {
                    TargetCard targetCard = new TargetCard(1, Zone.HAND, new FilterCard("card for opening hand action"));
                    player.chooseTarget(Outcome.Benefit, cardsWithOpeningAction, targetCard, null, this);
                    card = getCard(targetCard.getFirstTarget());
                } else {
                    card = cardsWithOpeningAction.getRandom(this);
                }
                if (card != null) {
                    for (Ability ability : card.getAbilities(this)) {
                        if (ability instanceof OpeningHandAction) {
                            OpeningHandAction action = (OpeningHandAction) ability;
                            if (action.askUseOpeningHandAction(card, player, this)) {
                                action.doOpeningHandAction(card, player, this);
                            }
                        }

                    }
                }
                cardsWithOpeningAction.remove(card);
            }
        }

        // 20180408 - 901.5
        if (gameOptions.planeChase) {
            Plane plane = Plane.createRandomPlane();
            plane.setControllerId(startingPlayerId);
            addPlane(plane, null, startingPlayerId);
            state.setPlaneChase(this, gameOptions.planeChase);
        }
    }

    public void initGameDefaultWatchers() {
        List<Watcher> newWatchers = new ArrayList<>();
        newWatchers.add(new MorbidWatcher());
        newWatchers.add(new CastSpellLastTurnWatcher());
        newWatchers.add(new CastSpellYourLastTurnWatcher());
        newWatchers.add(new PlayerLostLifeWatcher());
        newWatchers.add(new PlayerLostLifeNonCombatWatcher());
        newWatchers.add(new BlockedAttackerWatcher());
        newWatchers.add(new DamageDoneWatcher());
        newWatchers.add(new PlanarRollWatcher());
        newWatchers.add(new AttackedThisTurnWatcher());
        newWatchers.add(new PlayersAttackedThisTurnWatcher());
        newWatchers.add(new CardsDrawnThisTurnWatcher());
        newWatchers.add(new ManaSpentToCastWatcher());
        newWatchers.add(new ManaPaidSourceWatcher());
        newWatchers.add(new BlockingOrBlockedWatcher());
        newWatchers.add(new EndStepCountWatcher());
        newWatchers.add(new CommanderPlaysCountWatcher()); // commander plays count uses in non commander games by some cards

        // runtime check - allows only GAME scope (one watcher per game)
        newWatchers.forEach(watcher -> {
            if (!watcher.getScope().equals(WatcherScope.GAME)) {
                throw new IllegalStateException("Game default watchers must have GAME scope: " + watcher.getClass().getCanonicalName());
            }
        });

        newWatchers.forEach(getState()::addWatcher);
    }

    public void initPlayerDefaultWatchers(UUID playerId) {
        PlayerDamagedBySourceWatcher playerDamagedBySourceWatcher = new PlayerDamagedBySourceWatcher();
        playerDamagedBySourceWatcher.setControllerId(playerId);

        getState().addWatcher(playerDamagedBySourceWatcher);

        BloodthirstWatcher bloodthirstWatcher = new BloodthirstWatcher();
        bloodthirstWatcher.setControllerId(playerId);
        getState().addWatcher(bloodthirstWatcher);
    }

    protected void sendStartMessage(Player choosingPlayer, Player startingPlayer) {
        StringBuilder message = new StringBuilder();
        if (choosingPlayer != null) {
            message.append(choosingPlayer.getLogName()).append(" chooses that ");
        }
        if (choosingPlayer != null && choosingPlayer.getId().equals(startingPlayer.getId())) {
            message.append("they");
        } else {
            message.append(startingPlayer.getLogName());
        }
        message.append(" take the first turn");

        this.informPlayers(message.toString());
    }

    protected UUID findWinnersAndLosers() {
        UUID winnerIdFound = null;
        for (Player player : state.getPlayers().values()) {
            if (player.hasWon()) {
                logger.debug(player.getName() + " has won gameId: " + getId());
                winnerIdFound = player.getId();
                break;
            }
            if (!player.hasLost() && !player.hasLeft()) {
                logger.debug(player.getName() + " has not lost so they won gameId: " + this.getId());
                player.won(this);
                winnerIdFound = player.getId();
                break;
            }
        }
        for (Player player : state.getPlayers().values()) {
            if (winnerIdFound != null && !player.getId().equals(winnerIdFound) && !player.hasLost()) {
                player.lost(this);
            }
        }
        return winnerIdFound;
    }

    protected void endOfTurn() {
        for (Player player : getPlayers().values()) {
            player.endOfTurn(this);
        }
        state.resetWatchers();
    }

    protected UUID pickChoosingPlayer() {
        UUID[] players = getPlayers().keySet().toArray(new UUID[0]);
        UUID playerId;
        while (!hasEnded()) {
            playerId = players[RandomUtil.nextInt(players.length)];
            Player player = getPlayer(playerId);
            if (player != null && player.canRespond()) {
                fireInformEvent(state.getPlayer(playerId).getLogName() + " won the toss");
                return player.getId();
            }
        }
        logger.debug("Game was not possible to pick a choosing player. GameId:" + getId());
        return null;
    }

    @Override
    public void pause() {
        state.pause();
    }

    @Override
    public boolean isPaused() {
        return state.isPaused();
    }

    @Override
    public void end() {
        if (!state.isGameOver()) {
            logger.debug("END of gameId: " + this.getId());
            endTime = new Date();
            state.endGame();
            for (Player player : state.getPlayers().values()) {
                player.abort();
            }
        }
    }

    @Override
    public void addTableEventListener(Listener<TableEvent> listener) {
        tableEventSource.addListener(listener);
    }

    @Override
    public int mulliganDownTo(UUID playerId) {
        return mulligan.mulliganDownTo(this, playerId);
    }

    @Override
    public void endMulligan(UUID playerId) {
        mulligan.endMulligan(this, playerId);
    }

    @Override
    public void mulligan(UUID playerId) {
        mulligan.mulligan(this, playerId);
    }

    @Override
    public synchronized void timerTimeout(UUID playerId) {
        Player player = state.getPlayer(playerId);
        if (player != null) {
            player.timerTimeout(this);
        } else {
            logger.error(new StringBuilder("timerTimeout - player not found - playerId: ").append(playerId));
        }
    }

    @Override
    public synchronized void idleTimeout(UUID playerId) {
        Player player = state.getPlayer(playerId);
        if (player != null) {
            player.idleTimeout(this);
        } else {
            logger.error(new StringBuilder("idleTimeout - player not found - playerId: ").append(playerId));
        }
    }

    @Override
    public synchronized void concede(UUID playerId) {
        Player player = state.getPlayer(playerId);
        if (player != null && !player.hasLost()) {
            logger.debug("Player " + player.getName() + " concedes game " + this.getId());
            fireInformEvent(player.getLogName() + " has conceded.");
            player.concede(this);
        }
    }

    @Override
    public synchronized void undo(UUID playerId) {
        Player player = state.getPlayer(playerId);
        if (player != null) {
            int bookmark = player.getStoredBookmark();
            if (bookmark != -1) {
                player.restoreState(bookmark, "undo", this);
                player.setStoredBookmark(-1);
                fireUpdatePlayersEvent();
            }
        }
    }

    @Override
    public void sendPlayerAction(PlayerAction playerAction, UUID playerId, Object data) {
        Player player = state.getPlayer(playerId);
        if (player != null) {
            player.sendPlayerAction(playerAction, this, data);
        }
    }

    @Override
    public synchronized void setManaPaymentMode(UUID playerId, boolean autoPayment) {
        Player player = state.getPlayer(playerId);
        if (player != null) {
            player.getUserData().setManaPoolAutomatic(autoPayment);
            player.getManaPool().setAutoPayment(autoPayment);
        }
    }

    @Override
    public synchronized void setManaPaymentModeRestricted(UUID playerId, boolean autoPaymentRestricted) {
        Player player = state.getPlayer(playerId);
        if (player != null) {
            player.getUserData().setManaPoolAutomaticRestricted(autoPaymentRestricted);
            player.getManaPool().setAutoPaymentRestricted(autoPaymentRestricted);
        }
    }

    @Override
    public synchronized void setUseFirstManaAbility(UUID playerId, boolean useFirstManaAbility) {
        Player player = state.getPlayer(playerId);
        if (player != null) {
            player.getUserData().setUseFirstManaAbility(useFirstManaAbility);
        }
    }

    @Override
    public void playPriority(UUID activePlayerId, boolean resuming) {
        int errorContinueCounter = 0;
        infiniteLoopCounter = 0;
        int rollbackBookmark = 0;
        clearAllBookmarks();
        try {
            applyEffects();
            while (!isPaused() && !checkIfGameIsOver() && !this.getTurn().isEndTurnRequested()) {
                if (!resuming) {
                    state.getPlayers().resetPassed();
                    state.getPlayerList().setCurrent(activePlayerId);
                } else {
                    state.getPlayerList().setCurrent(this.getPriorityPlayerId());
                }
                fireUpdatePlayersEvent();
                Player player;
                while (!isPaused() && !checkIfGameIsOver()) {
                    try {
                        if (rollbackBookmark == 0) {
                            rollbackBookmark = bookmarkState();
                        }
                        player = getPlayer(state.getPlayerList().get());
                        state.setPriorityPlayerId(player.getId());
                        while (!player.isPassed() && player.canRespond() && !isPaused() && !checkIfGameIsOver()) {
                            if (!resuming) {
                                // 603.3. Once an ability has triggered, its controller puts it on the stack as an object that's not a card the next time a player would receive priority
                                checkStateAndTriggered();
                                applyEffects();
                                if (state.getStack().isEmpty()) {
                                    resetLKI();
                                }
                                saveState(false);
                                if (isPaused() || checkIfGameIsOver()) {
                                    return;
                                }
                                // resetPassed should be called if player performs any action
                                if (player.priority(this)) {
                                    if (executingRollback()) {
                                        return;
                                    }
                                    getState().handleSimultaneousEvent(this); // needed here to handle triggers e.g. from paying costs like sacrificing a creatures before LKIShort is cleared
                                    applyEffects();
                                }
                                if (isPaused()) {
                                    return;
                                }
                            }
                            resuming = false;
                        }
                        resetShortLivingLKI();
                        resuming = false;
                        if (isPaused() || checkIfGameIsOver()) {
                            return;
                        }
                        if (allPassed()) {
                            if (!state.getStack().isEmpty()) {
                                //20091005 - 115.4
                                resolve();
                                checkConcede();
                                applyEffects();
                                state.getPlayers().resetPassed();
                                fireUpdatePlayersEvent();
                                resetShortLivingLKI();
                                break;
                            } else {
                                resetLKI();
                                return;
                            }
                        }
                    } catch (Exception ex) {
                        logger.fatal("Game exception gameId: " + getId(), ex);
                        if ((ex instanceof NullPointerException)
                                && errorContinueCounter == 0 && ex.getStackTrace() != null) {
                            logger.fatal(ex.getStackTrace());
                        }
                        this.fireErrorEvent("Game exception occurred: ", ex);

                        // stack info
                        String info = this.getStack().stream().map(MageObject::toString).collect(Collectors.joining("\n"));
                        logger.info(String.format("\nStack before error %d: \n%s\n", this.getStack().size(), info));

                        // rollback game to prev state
                        GameState restoredState = restoreState(rollbackBookmark, "Game exception: " + ex.getMessage());
                        rollbackBookmark = 0;

                        if (errorContinueCounter > 15) {
                            throw new MageException("Iterated player priority after game exception too often, game ends! Last error:\n "
                                    + ex.getMessage());
                        }

                        if (restoredState != null) {
                            this.informPlayers(String.format("Auto-restored to %s due game error: %s", restoredState, ex.getMessage()));
                        } else {
                            logger.error("Can't auto-restore to prev state.");
                        }

                        Player activePlayer = this.getPlayer(getActivePlayerId());
                        if (activePlayer != null && !activePlayer.isTestsMode()) {
                            errorContinueCounter++;
                            continue;
                        } else {
                            throw new MageException(UNIT_TESTS_ERROR_TEXT);
                        }
                    } finally {
                        setCheckPlayableState(false);
                    }
                    state.getPlayerList().getNext();
                }
            }
        } catch (Exception ex) {
            logger.fatal("Game exception " + ex.getMessage(), ex);
            this.fireErrorEvent("Game exception occurred: ", ex);
            this.end();

            // don't catch game errors in unit tests, so test framework can process it (example: errors in AI simulations)
            if (ex.getMessage() != null && ex.getMessage().equals(UNIT_TESTS_ERROR_TEXT)) {
                //this.getContinuousEffects().traceContinuousEffects(this);
                throw new IllegalStateException(UNIT_TESTS_ERROR_TEXT);
            }
        } finally {
            resetLKI();
            clearAllBookmarks();
            setCheckPlayableState(false);
        }
    }

    //resolve top StackObject
    protected void resolve() {
        StackObject top = null;
        try {
            top = state.getStack().peek();
            top.resolve(this);
            resetControlAfterSpellResolve(top.getId());
        } finally {
            if (top != null) {
                state.getStack().remove(top, this); // seems partly redundant because move card from stack to grave is already done and the stack removed
                rememberLKI(top.getSourceId(), Zone.STACK, top);
                checkInfiniteLoop(top.getSourceId());
                if (!getTurn().isEndTurnRequested()) {
                    while (state.hasSimultaneousEvents()) {
                        state.handleSimultaneousEvent(this);
                    }
                }
            }
        }
    }

    @Override
    public void resetControlAfterSpellResolve(UUID topId) {
        // for Word of Command
        Spell spell = getSpellOrLKIStack(topId);
        if (spell != null) {
            if (spell.getCommandedBy() != null) {
                UUID commandedBy = spell.getCommandedBy();
                UUID spellControllerId;
                if (commandedBy.equals(spell.getControllerId())) {
                    spellControllerId = spell.getSpellAbility().getFirstTarget(); // i.e. resolved spell is Word of Command
                } else {
                    spellControllerId = spell.getControllerId(); // i.e. resolved spell is the target opponent's spell
                }
                if (spellControllerId != null) {
                    Player turnController = getPlayer(commandedBy);
                    if (turnController != null) {
                        Player targetPlayer = getPlayer(spellControllerId);
                        if (targetPlayer != null) {
                            targetPlayer.setGameUnderYourControl(true, false);
                            informPlayers(turnController.getLogName() + " lost control over " + targetPlayer.getLogName());
                            if (targetPlayer.getTurnControlledBy().equals(turnController.getId())) {
                                turnController.getPlayersUnderYourControl().remove(targetPlayer.getId());
                            }
                        }
                    }
                }
                spell.setCommandedBy(null);
            }
        }
    }

    /**
     * This checks if the stack gets filled iterated, without ever getting empty
     * If the defined number of iterations with not more than 4 different
     * sourceIds for the removed stack Objects is reached, the players in range
     * of the stackObject get asked to confirm a draw. If they do, all
     * confirming players get set to a draw.
     *
     * @param removedStackObjectSourceId
     */
    protected void checkInfiniteLoop(UUID removedStackObjectSourceId) {
        if (stackObjectsCheck.contains(removedStackObjectSourceId)
                && getStack().size() >= lastNumberOfAbilitiesOnTheStack) {
            // Create a list of players life
            List<Integer> newLastPlayersLifes = new ArrayList<>();
            for (Player player : this.getPlayers().values()) {
                newLastPlayersLifes.add(player.getLife());
            }
            // Check if a player is loosing life
            if (lastPlayersLifes != null && lastPlayersLifes.size() == newLastPlayersLifes.size()) {
                for (int i = 0; i < newLastPlayersLifes.size(); i++) {
                    if (newLastPlayersLifes.get(i) < lastPlayersLifes.get(i)) {
                        // player is loosing life
                        lastPlayersLifes = null;
                        infiniteLoopCounter = 0; // reset the infinite counter
                        break;
                    }
                }
            } else {
                lastPlayersLifes = newLastPlayersLifes;
            }
            infiniteLoopCounter++;
            if (infiniteLoopCounter > 15) {
                Player controller = getPlayer(getControllerId(removedStackObjectSourceId));
                if (controller != null) {
                    for (UUID playerId : getState().getPlayersInRange(controller.getId(), this)) {
                        Player player = getPlayer(playerId);
                        if (!player.chooseUse(Outcome.Detriment, "Draw game because of infinite looping?", null, this)) {
                            informPlayers(controller.getLogName() + " has NOT confirmed that the game is a draw because of infinite looping.");
                            infiniteLoopCounter = 0;
                            return;
                        }
                        informPlayers(controller.getLogName() + " has confirmed that the game is a draw because of infinite looping.");
                    }
                    for (UUID playerId : getState().getPlayersInRange(controller.getId(), this)) {
                        Player player = getPlayer(playerId);
                        if (player != null) {
                            player.drew(this);
                        }
                    }
                }
            }
        } else {
            stackObjectsCheck.add(removedStackObjectSourceId);
            if (stackObjectsCheck.size() > 4) {
                stackObjectsCheck.removeFirst();
            }
        }
        lastNumberOfAbilitiesOnTheStack = getStack().size();
    }

    protected boolean allPassed() {
        for (Player player : state.getPlayers().values()) {
            if (!player.isPassed() && player.canRespond()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void emptyManaPools(Ability source) {
        for (Player player : getPlayers().values()) {
            int amount = player.getManaPool().emptyPool(this);
            if (state.isManaBurn() && amount > 0) {
                player.loseLife(amount, this, source, false);
            }
        }
    }

    @Override
    public synchronized void applyEffects() {
        resetShortLivingLKI();
        state.applyEffects(this);
    }

    @Override
    public void addEffect(ContinuousEffect continuousEffect, Ability source) {
        Ability newAbility = source.copy();
        newAbility.setSourceObjectZoneChangeCounter(getState().getZoneChangeCounter(source.getSourceId()));
        ContinuousEffect newEffect = continuousEffect.copy();

        newEffect.newId();
        newEffect.init(newAbility, this);

        state.addEffect(newEffect, newAbility);
    }

    @Override
    public void addEmblem(Emblem emblem, MageObject sourceObject, Ability source) {
        addEmblem(emblem, sourceObject, source.getControllerId());
    }

    /**
     * @param emblem
     * @param sourceObject
     * @param toPlayerId   controller and owner of the emblem
     */
    @Override
    public void addEmblem(Emblem emblem, MageObject sourceObject, UUID toPlayerId) {
        Emblem newEmblem = emblem.copy();
        newEmblem.setSourceObject(sourceObject);
        newEmblem.setControllerId(toPlayerId);
        newEmblem.assignNewId();
        newEmblem.getAbilities().newId();
        for (Ability ability : newEmblem.getAbilities()) {
            ability.setSourceId(newEmblem.getId());
        }
        state.addCommandObject(newEmblem);
    }

    /**
     * @param plane
     * @param sourceObject
     * @param toPlayerId   controller and owner of the plane (may only be one
     *                     per game..)
     * @return boolean - whether the plane was added successfully or not
     */
    @Override
    public boolean addPlane(Plane plane, MageObject sourceObject, UUID toPlayerId) {
        // Implementing planechase as if it were 901.15. Single Planar Deck Option
        // Here, can enforce the world plane restriction (the Grand Melee format may have some impact on this implementation)

        // Enforce 'world' rule for planes
        for (CommandObject cobject : state.getCommand()) {
            if (cobject instanceof Plane) {
                return false;
            }
        }
        Plane newPlane = plane.copy();
        newPlane.setSourceObject(sourceObject);
        newPlane.setControllerId(toPlayerId);
        newPlane.assignNewId();
        newPlane.getAbilities().newId();
        for (Ability ability : newPlane.getAbilities()) {
            ability.setSourceId(newPlane.getId());
        }
        state.addCommandObject(newPlane);
        informPlayers("You have planeswalked to " + newPlane.getLogName());

        // Fire off the planeswalked event
        GameEvent event = new GameEvent(GameEvent.EventType.PLANESWALK, newPlane.getId(), null, newPlane.getId(), 0, true);
        if (!replaceEvent(event)) {
            GameEvent ge = new GameEvent(GameEvent.EventType.PLANESWALKED, newPlane.getId(), null, newPlane.getId(), 0, true);
            fireEvent(ge);
        }

        return true;
    }

    @Override
    public void addCommander(Commander commander) {
        state.addCommandObject(commander);
    }

    @Override
    public Dungeon addDungeon(Dungeon dungeon, UUID playerId) {
        dungeon.setControllerId(playerId);
        state.addCommandObject(dungeon);
        return dungeon;
    }

    @Override
    public void addPermanent(Permanent permanent, int createOrder) {
        if (createOrder == 0) {
            createOrder = getState().getNextPermanentOrderNumber();
        }
        permanent.setCreateOrder(createOrder);
        getBattlefield().addPermanent(permanent);
    }

    @Override
    public Permanent copyPermanent(Permanent copyFromPermanent, UUID copyToPermanentId, Ability source, CopyApplier applier) {
        return copyPermanent(Duration.Custom, copyFromPermanent, copyToPermanentId, source, applier);
    }

    @Override
    public Permanent copyPermanent(Duration duration, Permanent copyFromPermanent, UUID copyToPermanentId, Ability source, CopyApplier applier) {
        Permanent newBluePrint = null;
        // handle copies of copies
        for (Effect effect : getState().getContinuousEffects().getLayeredEffects(this)) {
            if (effect instanceof CopyEffect) {
                CopyEffect copyEffect = (CopyEffect) effect;
                // there is another copy effect that our targetPermanent copies stats from
                if (copyEffect.getSourceId().equals(copyFromPermanent.getId())) {
                    MageObject oldBluePrint = ((CopyEffect) effect).getTarget();
                    if (oldBluePrint instanceof Permanent) {
                        // copy it and apply the applier if any
                        newBluePrint = ((Permanent) oldBluePrint).copy();
                    }
                }
            }
        }

        // if it was no copy of copy take the target itself
        if (newBluePrint == null) {
            newBluePrint = copyFromPermanent.copy();
            newBluePrint.reset(this);

            //getState().addCard(permanent);
            if (copyFromPermanent.isMorphed() || copyFromPermanent.isManifested()
                    || copyFromPermanent.isFaceDown(this)) {
                MorphAbility.setPermanentToFaceDownCreature(newBluePrint, this);
            }
            newBluePrint.assignNewId();
            if (copyFromPermanent.isTransformed()) {
                TransformAbility.transformPermanent(newBluePrint, newBluePrint.getSecondCardFace(), this, source);
            }
        }
        if (applier != null) {
            applier.apply(this, newBluePrint, source, copyToPermanentId);
        }

        // save original copy link (handle copy of copies too)
        newBluePrint.setCopy(true, (copyFromPermanent.getCopyFrom() != null ? copyFromPermanent.getCopyFrom() : copyFromPermanent));

        CopyEffect newEffect = new CopyEffect(duration, newBluePrint, copyToPermanentId);
        newEffect.newId();
        newEffect.setApplier(applier);
        Ability newAbility = source.copy();
        newEffect.init(newAbility, this);

        // If there are already copy effects with duration = Custom to the same object, remove the existing effects because they no longer have any effect
        if (duration == Duration.Custom) {
            for (Effect effect : getState().getContinuousEffects().getLayeredEffects(this)) {
                if (effect instanceof CopyEffect) {
                    CopyEffect copyEffect = (CopyEffect) effect;
                    // there is another copy effect that copies to the same permanent
                    if (copyEffect.getSourceId().equals(copyToPermanentId) && copyEffect.getDuration() == Duration.Custom) {
                        copyEffect.discard();
                    }
                }
            }
        }
        state.addEffect(newEffect, newAbility);
        return newBluePrint;
    }

    @Override
    public Card copyCard(Card cardToCopy, Ability source, UUID newController) {
        return state.copyCard(cardToCopy, newController, this);
    }

    /**
     * For internal use only
     *
     * @param ability
     * @param triggeringEvent
     */
    @Override
    public void addTriggeredAbility(TriggeredAbility ability, GameEvent triggeringEvent) {
        if (ability.getControllerId() == null) {
            String sourceName = "no sourceId";
            if (ability.getSourceId() != null) {
                MageObject mageObject = getObject(ability.getSourceId());
                if (mageObject != null) {
                    sourceName = mageObject.getName();
                }
            }
            logger.fatal("Added triggered ability without controller: " + sourceName + " rule: " + ability.getRule());
            return;
        }
        if (ability instanceof TriggeredManaAbility || ability instanceof DelayedTriggeredManaAbility) {
            // 20110715 - 605.4
            Ability manaAbility = ability.copy();
            if (manaAbility.getSourceObjectZoneChangeCounter() == 0) {
                manaAbility.setSourceObjectZoneChangeCounter(getState().getZoneChangeCounter(ability.getSourceId()));
            }
            manaAbility.activate(this, false);
            manaAbility.resolve(this);
        } else {
            TriggeredAbility newAbility = ability.copy();
            newAbility.newId();
            if (newAbility.getSourceObjectZoneChangeCounter() == 0) {
                newAbility.setSourceObjectZoneChangeCounter(getState().getZoneChangeCounter(ability.getSourceId()));
            }
            if (!(newAbility instanceof DelayedTriggeredAbility)) {
                newAbility.setSourcePermanentTransformCount(this);
            }
            newAbility.setTriggerEvent(triggeringEvent);
            state.addTriggeredAbility(newAbility);
        }
    }

    @Override
    public UUID addDelayedTriggeredAbility(DelayedTriggeredAbility delayedAbility, Ability source) {
        if (source != null) {
            delayedAbility.setSourceId(source.getSourceId());
            delayedAbility.setControllerId(source.getControllerId());
        }
        // return addDelayedTriggeredAbility(delayedAbility);
        DelayedTriggeredAbility newAbility = delayedAbility.copy();
        newAbility.newId();
        if (source != null) {
            newAbility.setSourceObjectZoneChangeCounter(getState().getZoneChangeCounter(source.getSourceId()));
            newAbility.setSourcePermanentTransformCount(this);
        }
        newAbility.init(this);
        getState().addDelayedTriggeredAbility(newAbility);
        return newAbility.getId();
    }

    @Override
    public UUID fireReflexiveTriggeredAbility(ReflexiveTriggeredAbility reflexiveAbility, Ability source) {
        UUID uuid = this.addDelayedTriggeredAbility(reflexiveAbility, source);
        this.fireEvent(GameEvent.getEvent(GameEvent.EventType.OPTION_USED, source.getOriginalId(), source, source.getControllerId()));
        return uuid;
    }

    /**
     * 116.5. Each time a player would get priority, the game first performs all
     * applicable state-based actions as a single event (see rule 704,
     * “State-Based Actions”), then repeats this process until no state-based
     * actions are performed. Then triggered abilities are put on the stack (see
     * rule 603, “Handling Triggered Abilities”). These steps repeat in order
     * until no further state-based actions are performed and no abilities
     * trigger. Then the player who would have received priority does so.
     *
     * @return
     */
    @Override
    public boolean checkStateAndTriggered() {
        boolean somethingHappened = false;
        //20091005 - 115.5
        while (!isPaused() && !checkIfGameIsOver()) {
            if (!checkStateBasedActions()) {
                // nothing happened so check triggers
                state.handleSimultaneousEvent(this);
                if (isPaused() || checkIfGameIsOver() || getTurn().isEndTurnRequested() || !checkTriggered()) {
                    break;
                }
            }
            this.getState().processAction(this); // needed e.g if boost effects end and cause creatures to die
            somethingHappened = true;
        }
        checkConcede();
        return somethingHappened;
    }

    /**
     * Sets the waiting triggered abilities (if there are any) to the stack in
     * the chosen order by player
     *
     * @return
     */
    public boolean checkTriggered() {
        boolean played = false;
        state.getTriggers().checkStateTriggers(this);
        for (UUID playerId : state.getPlayerList(state.getActivePlayerId())) {
            Player player = getPlayer(playerId);
            while (player.canRespond()) { // player can die or win caused by triggered abilities or leave the game
                List<TriggeredAbility> abilities = state.getTriggered(player.getId());
                if (abilities.isEmpty()) {
                    break;
                }
                // triggered abilities that don't use the stack have to be executed first (e.g. Banisher Priest Return exiled creature
                for (Iterator<TriggeredAbility> it = abilities.iterator(); it.hasNext(); ) {
                    TriggeredAbility triggeredAbility = it.next();
                    if (!triggeredAbility.isUsesStack()) {
                        state.removeTriggeredAbility(triggeredAbility);
                        played |= player.triggerAbility(triggeredAbility, this);
                        it.remove();
                    }
                }
                if (abilities.isEmpty()) {
                    break;
                }
                if (abilities.size() == 1) {
                    state.removeTriggeredAbility(abilities.get(0));
                    played |= player.triggerAbility(abilities.get(0), this);
                } else {
                    TriggeredAbility ability = player.chooseTriggeredAbility(abilities, this);
                    if (ability != null) {
                        state.removeTriggeredAbility(ability);
                        played |= player.triggerAbility(ability, this);
                    }
                }
            }
        }
        return played;
    }

    /**
     * 116.5. Each time a player would get priority, the game first performs all
     * applicable state-based actions as a single event (see rule 704,
     * “State-Based Actions”), then repeats this process until no state-based
     * actions are performed. Then triggered abilities are put on the stack (see
     * rule 603, “Handling Triggered Abilities”). These steps repeat in order
     * until no further state-based actions are performed and no abilities
     * trigger. Then the player who would have received priority does so.
     *
     * @return
     */
    protected boolean checkStateBasedActions() {
        boolean somethingHappened = false;

        //20091005 - 704.5a/704.5b/704.5c
        for (Player player : state.getPlayers().values()) {
            if (!player.hasLost()
                    && ((player.getLife() <= 0 && player.canLoseByZeroOrLessLife())
                    || player.getLibrary().isEmptyDraw()
                    || player.getCounters().getCount(CounterType.POISON) >= 10)) {
                player.lost(this);
            }
        }

        // If a Dungeon is on its last room and is not the source of any triggered abilities, it is removed
        Set<Dungeon> dungeonsToRemove = new HashSet<>();
        for (CommandObject commandObject : state.getCommand()) {
            if (!(commandObject instanceof Dungeon)) {
                continue;
            }
            Dungeon dungeon = (Dungeon) commandObject;
            boolean removeDungeon = !dungeon.hasNextRoom()
                    && this.getStack()
                    .stream()
                    .filter(DungeonRoom::isRoomTrigger)
                    .map(StackObject::getSourceId)
                    .noneMatch(dungeon.getId()::equals)
                    && this.state
                    .getTriggered(dungeon.getControllerId())
                    .stream()
                    .filter(DungeonRoom::isRoomTrigger)
                    .map(Ability::getSourceId)
                    .noneMatch(dungeon.getId()::equals);
            if (removeDungeon) {
                dungeonsToRemove.add(dungeon);
            }
        }
        for (Dungeon dungeon : dungeonsToRemove) {
            this.removeDungeon(dungeon);
            somethingHappened = true;
        }

        // If a commander is in a graveyard or in exile and that card was put into that zone
        // since the last time state-based actions were checked, its owner may put it into the command zone.
        // signature spells goes to command zone all the time
        for (Player player : state.getPlayers().values()) {
            Set<UUID> commanderIds = getCommandersIds(player, CommanderCardType.COMMANDER_OR_OATHBREAKER, false);
            if (commanderIds.isEmpty()) {
                continue;
            }
            Set<Card> commanders = new HashSet<>();
            Cards toMove = new CardsImpl();
            player.getGraveyard()
                    .stream()
                    .filter(commanderIds::contains)
                    .map(this::getCard)
                    .filter(Objects::nonNull)
                    .forEach(commanders::add);
            commanderIds
                    .stream()
                    .map(uuid -> getExile().getCard(uuid, this))
                    .filter(Objects::nonNull)
                    .forEach(commanders::add);
            commanders.removeIf(card -> state.checkCommanderShouldStay(card, this));
            for (Card card : commanders) {
                Zone currentZone = this.getState().getZone(card.getId());
                String currentZoneInfo = (currentZone == null ? "(error)" : "(" + currentZone.name() + ")");
                if (player.chooseUse(Outcome.Benefit, "Move " + card.getIdName()
                                + " to the command zone or leave it in current zone " + currentZoneInfo + "?", "You can only make this choice once per object",
                        "Move to command", "Leave in current zone " + currentZoneInfo, null, this)) {
                    toMove.add(card);
                } else {
                    state.setCommanderShouldStay(card, this);
                }
            }
            if (toMove.isEmpty()) {
                continue;
            }
            player.moveCards(toMove, Zone.COMMAND, null, this);
            somethingHappened = true;
        }

        // 704.5e
        // If a copy of a spell is in a zone other than the stack, it ceases to exist.
        // If a copy of a card is in any zone other than the stack or the battlefield, it ceases to exist.
        // (Isochron Scepter) 12/1/2004: If you don't want to cast the copy, you can choose not to; the copy ceases
        // to exist the next time state-based actions are checked.
        //
        // Copied cards can be stored in GameState.copiedCards or in game state value (until LKI rework)
        // Copied cards list contains all parts of split/adventure/mdfc
        Set<Card> allCopiedCards = new HashSet<>();
        allCopiedCards.addAll(this.getState().getCopiedCards());
        Map<String, Object> stateSavedCopiedCards = this.getState().getValues(GameState.COPIED_CARD_KEY);
        allCopiedCards.addAll(stateSavedCopiedCards.values()
                .stream()
                .map(object -> (Card) object)
                .filter(Objects::nonNull)
                .collect(Collectors.toList())
        );
        Set<Card> copiedCardsToRemove = new HashSet<>();
        for (Card copiedCard : allCopiedCards) {
            // 1. Zone must be checked from main card only cause mdf parts can have different zones
            //    (one side on battlefield, another side on outside)
            // 2. Copied card creates in OUTSIDE zone and put to stack manually in the same code,
            //    so no SBA calls before real zone change (you will see here only unused cards like Isochron Scepter)
            //    (Isochron Scepter) 12/1/2004: If you don't want to cast the copy, you can choose not to; the copy ceases
            //    to exist the next time state-based actions are checked.
            Zone zone = state.getZone(copiedCard.getMainCard().getId());
            // TODO: remember LKI of copied cards here after LKI rework
            switch (zone) {
                case OUTSIDE:
                case BATTLEFIELD: {
                    // keep in battlefield
                    // keep in outside (it's a final zone for all copied cards)
                    continue;
                }

                case STACK: {
                    // copied cards aren't moves and keeps in Stack zone after resolve,
                    // so it must be moved manually as SBA (see Outside zone change at the end)
                    MageObject object = getStack().getStackObject(copiedCard.getId());
                    if (object != null) {
                        // keep in stack until resolve
                        continue;
                    }
                    break;
                }

                case GRAVEYARD: {
                    for (Player player : getPlayers().values()) {
                        if (player.getGraveyard().contains(copiedCard.getId())) {
                            player.getGraveyard().remove(copiedCard);
                            break;
                        }
                    }
                    break;
                }

                case HAND: {
                    for (Player player : getPlayers().values()) {
                        if (player.getHand().contains(copiedCard.getId())) {
                            player.getHand().remove(copiedCard);
                            break;
                        }
                    }
                    break;
                }

                case LIBRARY: {
                    for (Player player : getPlayers().values()) {
                        if (player.getLibrary().getCard(copiedCard.getId(), this) != null) {
                            player.getLibrary().remove(copiedCard.getId(), this);
                            break;
                        }
                    }
                    break;
                }

                case EXILED: {
                    getExile().removeCard(copiedCard, this);
                    break;
                }

                case COMMAND:
                default: {
                    break;
                }
            }

            // copied card can be removed to Outside
            copiedCardsToRemove.add(copiedCard);
        }
        // real remove
        copiedCardsToRemove.forEach(card -> {
            card.setZone(Zone.OUTSIDE, this);
            this.getState().getCopiedCards().remove(card);
            // must keep card in game state as LKI alternative until LKI rework, so don't remove from it
            // TODO: change after LKI rework
            //this.getState().removeValue(GameState.COPIED_CARD_KEY + copiedCard.getId().toString());
        });

        List<Permanent> legendary = new ArrayList<>();
        List<Permanent> worldEnchantment = new ArrayList<>();
        List<FilterCreaturePermanent> usePowerInsteadOfToughnessForDamageLethalityFilters = getState().getActivePowerInsteadOfToughnessForDamageLethalityFilters();
        for (Permanent perm : getBattlefield().getAllActivePermanents()) {
            if (perm.isCreature(this)) {
                //20091005 - 704.5f
                if (perm.getToughness().getValue() <= 0) {
                    if (movePermanentToGraveyardWithInfo(perm)) {
                        somethingHappened = true;
                        continue;
                    }
                } //20091005 - 704.5g/704.5h
                else {
                    /*
                     * for handling Zilortha, Strength Incarnate:
                     * 2020-04-17: Any time the game is checking whether damage is lethal or if a creature should be destroyed for having lethal damage marked on it, use the power of your creatures rather than their toughness to check the damage against. This includes being assigned trample damage, damage from Flame Spill, and so on.
                     */
                    boolean usePowerInsteadOfToughnessForDamageLethality = usePowerInsteadOfToughnessForDamageLethalityFilters.stream()
                            .anyMatch(filter -> filter.match(perm, this));
                    int lethalDamageThreshold = usePowerInsteadOfToughnessForDamageLethality
                            ? // Zilortha, Strength Incarnate, 2020-04-17: A creature with 0 power isn’t destroyed unless it has at least 1 damage marked on it.
                            Math.max(perm.getPower().getValue(), 1) : perm.getToughness().getValue();
                    if (lethalDamageThreshold <= perm.getDamage() || perm.isDeathtouched()) {
                        if (perm.destroy(null, this, false)) {
                            somethingHappened = true;
                            continue;
                        }
                    }
                }
                if (perm.getPairedCard() != null) {
                    //702.93e.: ...another player gains control
                    // ...or the creature it's paired with leaves the battlefield.
                    Permanent paired = perm.getPairedCard().getPermanent(this);
                    if (paired == null || !perm.isControlledBy(paired.getControllerId()) || paired.getPairedCard() == null) {
                        perm.setPairedCard(null);
                        if (paired != null && paired.getPairedCard() != null) {
                            paired.setPairedCard(null);
                        }
                        somethingHappened = true;
                    }
                }
                if (perm.getBandedCards() != null && !perm.getBandedCards().isEmpty()) {
                    for (UUID bandedId : new ArrayList<>(perm.getBandedCards())) {
                        Permanent banded = getPermanent(bandedId);
                        if (banded == null || !perm.isControlledBy(banded.getControllerId()) || !banded.getBandedCards().contains(perm.getId())) {
                            perm.removeBandedCard(bandedId);
                            if (banded != null && banded.getBandedCards().contains(perm.getId())) {
                                banded.removeBandedCard(perm.getId());
                            }
                            somethingHappened = true;
                        }
                    }
                }
            } else if (perm.getPairedCard() != null) {
                //702.93e.: ...stops being a creature
                Permanent paired = perm.getPairedCard().getPermanent(this);
                perm.setPairedCard(null);
                if (paired != null) {
                    paired.setPairedCard(null);
                }
                somethingHappened = true;
            } else if (perm.getBandedCards() != null && !perm.getBandedCards().isEmpty()) {
                perm.clearBandedCards();
                for (UUID bandedId : perm.getBandedCards()) {
                    Permanent banded = getPermanent(bandedId);
                    if (banded != null) {
                        banded.removeBandedCard(perm.getId());
                    }
                    somethingHappened = true;
                }
            }
            if (perm.isPlaneswalker(this)) {
                //20091005 - 704.5i
                if (perm.getCounters(this).getCount(CounterType.LOYALTY) == 0) {
                    if (movePermanentToGraveyardWithInfo(perm)) {
                        somethingHappened = true;
                        continue;
                    }
                }
            }
            if (perm.isWorld()) {
                worldEnchantment.add(perm);
            }
            if (perm.hasSubtype(SubType.AURA, this)) {
                //20091005 - 704.5n, 702.14c
                if (perm.getAttachedTo() == null) {
                    if (!perm.isCreature(this) && !perm.getAbilities(this).containsClass(BestowAbility.class)) {
                        if (movePermanentToGraveyardWithInfo(perm)) {
                            somethingHappened = true;
                        }
                    }
                } else {
                    Ability spellAbility = perm.getSpellAbility();
                    if (spellAbility == null) {
                        if (!perm.getAbilities().isEmpty()) {
                            spellAbility = perm.getAbilities().get(0); // Can happen for created tokens (e.g. Estrid, the Masked)
                        }
                    }
                    if (spellAbility.getTargets().isEmpty()) {
                        for (Ability ability : perm.getAbilities(this)) {
                            if ((ability instanceof SpellAbility)
                                    && SpellAbilityType.BASE_ALTERNATE == ((SpellAbility) ability).getSpellAbilityType()
                                    && !ability.getTargets().isEmpty()) {
                                spellAbility = ability;
                                break;
                            }
                        }
                    }
                    if (spellAbility.getTargets().isEmpty()) {
                        Permanent enchanted = this.getPermanent(perm.getAttachedTo());
                        logger.error("Aura without target: " + perm.getName() + " attached to " + (enchanted == null ? " null" : enchanted.getName()));
                    } else {
                        Target target = spellAbility.getTargets().get(0);
                        if (target instanceof TargetPermanent) {
                            Permanent attachedTo = getPermanent(perm.getAttachedTo());
                            if (attachedTo == null || !attachedTo.getAttachments().contains(perm.getId())) {
                                // handle bestow unattachment
                                Card card = this.getCard(perm.getId());
                                if (card != null && card.isCreature(this)) {
                                    UUID wasAttachedTo = perm.getAttachedTo();
                                    perm.attachTo(null, null, this);
                                    fireEvent(new UnattachedEvent(wasAttachedTo, perm.getId(), perm, null));
                                } else if (movePermanentToGraveyardWithInfo(perm)) {
                                    somethingHappened = true;
                                }
                            } else {
                                Filter auraFilter = spellAbility.getTargets().get(0).getFilter();
                                if (auraFilter instanceof FilterPermanent) {
                                    if (!((FilterPermanent) auraFilter).match(attachedTo, perm.getControllerId(), perm.getSpellAbility(), this)
                                            || attachedTo.cantBeAttachedBy(perm, null, this, true)) {
                                        Card card = this.getCard(perm.getId());
                                        if (card != null && card.isCreature(this)) {
                                            UUID wasAttachedTo = perm.getAttachedTo();
                                            perm.attachTo(null, null, this);
                                            BestowAbility.becomeCreature(perm, this);
                                            fireEvent(new UnattachedEvent(wasAttachedTo, perm.getId(), perm, null));
                                        } else if (movePermanentToGraveyardWithInfo(perm)) {
                                            somethingHappened = true;
                                        }
                                    }
                                } else if (!auraFilter.match(attachedTo, this) || attachedTo.cantBeAttachedBy(perm, null, this, true)) {
                                    // handle bestow unattachment
                                    Card card = this.getCard(perm.getId());
                                    if (card != null && card.isCreature(this)) {
                                        UUID wasAttachedTo = perm.getAttachedTo();
                                        perm.attachTo(null, null, this);
                                        BestowAbility.becomeCreature(perm, this);
                                        fireEvent(new UnattachedEvent(wasAttachedTo, perm.getId(), perm, null));
                                    } else if (movePermanentToGraveyardWithInfo(perm)) {
                                        somethingHappened = true;
                                    }
                                }
                            }
                        } else if (target instanceof TargetPlayer) {
                            Player attachedToPlayer = getPlayer(perm.getAttachedTo());
                            if (attachedToPlayer == null || attachedToPlayer.hasLost()) {
                                if (movePermanentToGraveyardWithInfo(perm)) {
                                    somethingHappened = true;
                                }
                            } else {
                                Filter auraFilter = spellAbility.getTargets().get(0).getFilter();
                                if (!auraFilter.match(attachedToPlayer, this) || attachedToPlayer.hasProtectionFrom(perm, this)) {
                                    if (movePermanentToGraveyardWithInfo(perm)) {
                                        somethingHappened = true;
                                    }
                                }
                            }
                        } else if (target instanceof TargetCard) {
                            Card attachedTo = getCard(perm.getAttachedTo());
                            if (attachedTo == null
                                    || !(spellAbility.getTargets().get(0)).canTarget(perm.getControllerId(), perm.getAttachedTo(), spellAbility, this)) {
                                if (movePermanentToGraveyardWithInfo(perm)) {
                                    if (attachedTo != null) {
                                        attachedTo.removeAttachment(perm.getId(), null, this);
                                    }
                                    somethingHappened = true;
                                }
                            }
                        }
                    }
                }
            }
            // 704.5s If the number of lore counters on a Saga permanent is greater than or equal to its final chapter number
            // and it isn't the source of a chapter ability that has triggered but not yet left the stack, that Saga's controller sacrifices it.
            if (perm.hasSubtype(SubType.SAGA, this)) {
                int maxChapter = perm
                        .getAbilities(this)
                        .stream()
                        .filter(SagaAbility.class::isInstance)
                        .map(SagaAbility.class::cast)
                        .map(SagaAbility::getMaxChapter)
                        .mapToInt(SagaChapter::getNumber)
                        .max()
                        .orElse(0);

                boolean sacSaga = maxChapter <= perm
                        .getCounters(this)
                        .getCount(CounterType.LORE)
                        && this.getStack()
                        .stream()
                        .filter(SagaAbility::isChapterAbility)
                        .map(StackObject::getSourceId)
                        .noneMatch(perm.getId()::equals)
                        && this.state
                        .getTriggered(perm.getControllerId())
                        .stream()
                        .filter(SagaAbility::isChapterAbility)
                        .map(Ability::getSourceId)
                        .noneMatch(perm.getId()::equals);
                if (sacSaga) {
                    // After the last chapter ability has left the stack, you'll sacrifice the Saga
                    perm.sacrifice(null, this);
                    somethingHappened = true;
                }
            }
            if (perm.isLegendary() && perm.legendRuleApplies()) {
                legendary.add(perm);
            }
            if (StaticFilters.FILTER_PERMANENT_EQUIPMENT.match(perm, this)) {
                //20091005 - 704.5p, 702.14d
                if (perm.getAttachedTo() != null) {
                    Permanent attachedTo = getPermanent(perm.getAttachedTo());
                    if (attachedTo != null) {
                        for (Ability ability : perm.getAbilities(this)) {
                            if (ability instanceof AttachableToRestrictedAbility) {
                                if (!((AttachableToRestrictedAbility) ability).canEquip(attachedTo, null, this)) {
                                    attachedTo = null;
                                    break;
                                }
                            }
                        }
                    }
                    if (attachedTo == null || !attachedTo.getAttachments().contains(perm.getId())) {
                        UUID wasAttachedTo = perm.getAttachedTo();
                        perm.attachTo(null, null, this);
                        fireEvent(new UnattachedEvent(wasAttachedTo, perm.getId(), perm, null));
                    } else if (!attachedTo.isCreature(this) || attachedTo.hasProtectionFrom(perm, this)) {
                        if (attachedTo.removeAttachment(perm.getId(), null, this)) {
                            somethingHappened = true;
                        }
                    }
                }
            }
            if (StaticFilters.FILTER_PERMANENT_FORTIFICATION.match(perm, this)) {
                if (perm.getAttachedTo() != null) {
                    Permanent land = getPermanent(perm.getAttachedTo());
                    if (land == null || !land.getAttachments().contains(perm.getId())) {
                        perm.attachTo(null, null, this);
                    } else if (!land.isLand(this) || land.hasProtectionFrom(perm, this)) {
                        if (land.removeAttachment(perm.getId(), null, this)) {
                            somethingHappened = true;
                        }
                    }
                }
            }
            //20091005 - 704.5q If a creature is attached to an object or player, it becomes unattached and remains on the battlefield.
            // Similarly, if a permanent that's neither an Aura, an Equipment, nor a Fortification is attached to an object or player,
            // it becomes unattached and remains on the battlefield.
            if (!perm.getAttachments().isEmpty()) {
                for (UUID attachmentId : perm.getAttachments()) {
                    Permanent attachment = getPermanent(attachmentId);
                    if (attachment != null
                            && (attachment.isCreature(this)
                            || !(attachment.hasSubtype(SubType.AURA, this)
                            || attachment.hasSubtype(SubType.EQUIPMENT, this)
                            || attachment.hasSubtype(SubType.FORTIFICATION, this)))) {
                        if (perm.removeAttachment(attachment.getId(), null, this)) {
                            somethingHappened = true;
                            break;
                        }
                    }
                }
            }

            //20110501 - 704.5r
            if (perm.getCounters(this).containsKey(CounterType.P1P1) && perm.getCounters(this).containsKey(CounterType.M1M1)) {
                int p1p1 = perm.getCounters(this).getCount(CounterType.P1P1);
                int m1m1 = perm.getCounters(this).getCount(CounterType.M1M1);
                int min = Math.min(p1p1, m1m1);
                perm.getCounters(this).removeCounter(CounterType.P1P1, min);
                perm.getCounters(this).removeCounter(CounterType.M1M1, min);
            }

            // 20170120 - 704.5s
            // If a permanent with an ability that says it can't have more than N counters of a certain kind on it
            // has more than N counters of that kind on it, all but N of those counters are removed from it.
            for (Ability ability : perm.getAbilities(this)) {
                if (ability instanceof CantHaveMoreThanAmountCountersSourceAbility) {
                    CantHaveMoreThanAmountCountersSourceAbility counterAbility = (CantHaveMoreThanAmountCountersSourceAbility) ability;
                    int count = perm.getCounters(this).getCount(counterAbility.getCounterType());
                    if (count > counterAbility.getAmount()) {
                        perm.removeCounters(counterAbility.getCounterType().getName(), count - counterAbility.getAmount(), counterAbility, this);
                        somethingHappened = true;
                    }
                }
            }
        }
        //201300713 - 704.5k
        // If a player controls two or more legendary permanents with the same name, that player
        // chooses one of them, and the rest are put into their owners' graveyards.
        // This is called the "legend rule."

        if (legendary.size() > 1) {  //don't bother checking if less than 2 legends in play
            for (Permanent legend : legendary) {
                FilterPermanent filterLegendName = new FilterPermanent();
                filterLegendName.add(SuperType.LEGENDARY.getPredicate());
                filterLegendName.add(new NamePredicate(legend.getName()));
                filterLegendName.add(new ControllerIdPredicate(legend.getControllerId()));
                filterLegendName.add(LegendRuleAppliesPredicate.instance);
                if (!getBattlefield().contains(filterLegendName, legend.getControllerId(), null, this, 2)) {
                    continue;
                }
                Player controller = this.getPlayer(legend.getControllerId());
                if (controller == null) {
                    continue;
                }
                Target targetLegendaryToKeep = new TargetPermanent(filterLegendName);
                targetLegendaryToKeep.setNotTarget(true);
                targetLegendaryToKeep.setTargetName(legend.getName() + " to keep (Legendary Rule)?");
                controller.choose(Outcome.Benefit, targetLegendaryToKeep, null, this);
                for (Permanent dupLegend : getBattlefield().getActivePermanents(filterLegendName, legend.getControllerId(), this)) {
                    if (!targetLegendaryToKeep.getTargets().contains(dupLegend.getId())) {
                        movePermanentToGraveyardWithInfo(dupLegend);
                    }
                }
                return true;
            }
        }
        //704.5k  - World Enchantments
        if (worldEnchantment.size() > 1) {
            int newestCard = -1;
            Set<UUID> controllerIdOfNewest = new HashSet<>();
            Permanent newestPermanent = null;
            for (Permanent permanent : worldEnchantment) {
                if (newestCard == -1) {
                    newestCard = permanent.getCreateOrder();
                    newestPermanent = permanent;
                    controllerIdOfNewest.clear();
                    controllerIdOfNewest.add(permanent.getControllerId());
                } else if (newestCard < permanent.getCreateOrder()) {
                    newestCard = permanent.getCreateOrder();
                    newestPermanent = permanent;
                    controllerIdOfNewest.clear();
                    controllerIdOfNewest.add(permanent.getControllerId());
                } else if (newestCard == permanent.getCreateOrder()) {
                    //  In the event of a tie for the shortest amount of time, all are put into their owners’ graveyards. This is called the “world rule.”
                    newestPermanent = null;
                    controllerIdOfNewest.add(permanent.getControllerId());
                }
            }
            for (UUID controllerId : controllerIdOfNewest) {
                PlayerList newestPermanentControllerRange = state.getPlayersInRange(controllerId, this);

                // 801.12 The "world rule" applies to a permanent only if other world permanents are within its controller's range of influence.
                for (Permanent permanent : worldEnchantment) {
                    if (newestPermanentControllerRange.contains(permanent.getControllerId())
                            && !Objects.equals(newestPermanent, permanent)) {
                        movePermanentToGraveyardWithInfo(permanent);
                        somethingHappened = true;
                    }
                }
            }
        }

        // Daybound/Nightbound permanents should be transformed according to day/night
        // This is not a state-based action but it's unclear where else to put it
        if (hasDayNight()) {
            for (Permanent permanent : getBattlefield().getAllActivePermanents()) {
                if ((permanent.getAbilities(this).containsClass(DayboundAbility.class) && !state.isDaytime())
                        || (permanent.getAbilities(this).containsClass(NightboundAbility.class) && state.isDaytime())) {
                    somethingHappened = permanent.transform(null, this, true) || somethingHappened;
                }
            }
        }

        //TODO: implement the rest
        return somethingHappened;
    }

    private boolean movePermanentToGraveyardWithInfo(Permanent permanent) {
        boolean result = false;
        if (permanent.moveToZone(Zone.GRAVEYARD, null, this, false)) {
            if (!this.isSimulation()) {
                this.informPlayers(permanent.getLogName() + " is put into graveyard from battlefield");
            }
            result = true;
        }
        return result;
    }

    @Override
    public void addPlayerQueryEventListener(Listener<PlayerQueryEvent> listener) {
        playerQueryEventSource.addListener(listener);
    }

    @Override
    public synchronized void firePriorityEvent(UUID playerId) {
        if (simulation) {
            return;
        }
        String message;
        if (this.canPlaySorcery(playerId)) {
            message = "Play spells and abilities.";
        } else {
            message = "Play instants and activated abilities.";
        }
        playerQueryEventSource.select(playerId, message);
        getState().clearLookedAt();
        getState().clearRevealed();
    }

    @Override
    public synchronized void fireSelectEvent(UUID playerId, String message) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.select(playerId, message);
    }

    @Override
    public synchronized void fireSelectEvent(UUID playerId, String message, Map<String, Serializable> options) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.select(playerId, message, options);
    }

    @Override
    public void firePlayManaEvent(UUID playerId, String message, Map<String, Serializable> options) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.playMana(playerId, message, options);
    }

    @Override
    public void firePlayXManaEvent(UUID playerId, String message) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.playXMana(playerId, message);
    }

    @Override
    public void fireAskPlayerEvent(UUID playerId, MessageToClient message, Ability source) {
        fireAskPlayerEvent(playerId, message, source, null);
    }

    @Override
    public void fireAskPlayerEvent(UUID playerId, MessageToClient message, Ability source, Map<String, Serializable> options) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.ask(playerId, message.getMessage(), source, addMessageToOptions(message, options));
    }

    @Override
    public void fireGetChoiceEvent(UUID playerId, String message, MageObject object, List<? extends ActivatedAbility> choices) {
        if (simulation) {
            return;
        }
        String objectName = null;
        if (object != null) {
            objectName = object.getName();
        }
        playerQueryEventSource.chooseAbility(playerId, message, objectName, choices);
    }

    @Override
    public void fireGetModeEvent(UUID playerId, String message, Map<UUID, String> modes) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.chooseMode(playerId, message, modes);
    }

    @Override
    public void fireSelectTargetEvent(UUID playerId, MessageToClient message, Set<UUID> targets, boolean required, Map<String, Serializable> options) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.target(playerId, message.getMessage(), targets, required, addMessageToOptions(message, options));
    }

    @Override
    public void fireSelectTargetEvent(UUID playerId, MessageToClient message, Cards cards, boolean required, Map<String, Serializable> options) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.target(playerId, message.getMessage(), cards, required, addMessageToOptions(message, options));
    }

    /**
     * Only used from human players to select order triggered abilities go to
     * the stack.
     *
     * @param playerId
     * @param message
     * @param abilities
     */
    @Override
    public void fireSelectTargetTriggeredAbilityEvent(UUID playerId, String message, List<TriggeredAbility> abilities) {
        playerQueryEventSource.target(playerId, message, abilities);
    }

    @Override
    public void fireSelectTargetEvent(UUID playerId, String message, List<Permanent> perms, boolean required) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.target(playerId, message, perms, required);
    }

    @Override
    public void fireGetAmountEvent(UUID playerId, String message, int min, int max) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.amount(playerId, message, min, max);
    }

    @Override
    public void fireGetMultiAmountEvent(UUID playerId, List<String> messages, int min, int max, Map<String, Serializable> options) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.multiAmount(playerId, messages, min, max, options);
    }

    @Override
    public void fireChooseChoiceEvent(UUID playerId, Choice choice) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.chooseChoice(playerId, choice);
    }

    @Override
    public void fireChoosePileEvent(UUID playerId, String message, List<? extends Card> pile1, List<? extends Card> pile2) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.choosePile(playerId, message, pile1, pile2);
    }

    @Override
    public void informPlayers(String message) {
        // Uncomment to print game messages
        // System.out.println(message.replaceAll("\\<.*?\\>", ""));
        if (simulation) {
            return;
        }
        fireInformEvent(message);
    }

    @Override
    public void debugMessage(String message) {
        logger.warn(message);
    }

    @Override
    public void fireInformEvent(String message) {
        if (simulation) {
            return;
        }
        tableEventSource.fireTableEvent(EventType.INFO, message, this);
    }

    @Override
    public void fireStatusEvent(String message, boolean withTime, boolean withTurnInfo) {
        if (simulation) {
            return;
        }
        tableEventSource.fireTableEvent(EventType.STATUS, message, withTime, withTurnInfo, this);
    }

    @Override
    public void fireUpdatePlayersEvent() {
        if (simulation) {
            return;
        }
        logger.trace("fireUpdatePlayersEvent");
        tableEventSource.fireTableEvent(EventType.UPDATE, null, this);
        getState().clearLookedAt();
        getState().clearRevealed();
    }

    @Override
    public void fireGameEndInfo() {
        if (simulation) {
            return;
        }
        logger.trace("fireGameEndIfo");
        tableEventSource.fireTableEvent(EventType.END_GAME_INFO, null, this);
    }

    @Override
    public void fireErrorEvent(String message, Exception ex) {
        tableEventSource.fireTableEvent(EventType.ERROR, message, ex, this);
    }

    @Override
    public Players getPlayers() {
        return state.getPlayers();
    }

    /**
     * Return a list of all players ignoring the range of visible players
     *
     * @return
     */
    @Override
    public PlayerList getPlayerList() {
        return state.getPlayerList();
    }

    @Override
    public Turn getTurn() {
        return state.getTurn();
    }

    @Override
    public Phase getPhase() {
        return state.getTurn().getPhase();
    }

    @Override
    public Step getStep() {
        return state.getTurn().getStep();
    }

    @Override
    public Battlefield getBattlefield() {
        return state.getBattlefield();
    }

    @Override
    public SpellStack getStack() {
        return state.getStack();
    }

    @Override
    public Exile getExile() {
        return state.getExile();
    }

    @Override
    public Combat getCombat() {
        return state.getCombat();
    }

    @Override
    public int getTurnNum() {
        return state.getTurnNum();
    }

    @Override
    public boolean isMainPhase() {
        return state.getTurn().getStepType() == PhaseStep.PRECOMBAT_MAIN || state.getTurn().getStepType() == PhaseStep.POSTCOMBAT_MAIN;
    }

    @Override
    public boolean canPlaySorcery(UUID playerId) {
        return isMainPhase() && isActivePlayer(playerId) && getStack().isEmpty();
    }

    /**
     * 800.4a When a player leaves the game, all objects (see rule 109) owned by
     * that player leave the game and any effects which give that player control
     * of any objects or players end. Then, if that player controlled any
     * objects on the stack not represented by cards, those objects cease to
     * exist. Then, if there are any objects still controlled by that player,
     * those objects are exiled. This is not a state-based action. It happens as
     * soon as the player leaves the game. If the player who left the game had
     * priority at the time they left, priority passes to the next player in
     * turn order who's still in the game. #
     *
     * @param playerId
     */
    protected void leave(UUID playerId) { // needs to be executed from the game thread, not from the concede thread of conceding player!
        Player player = getPlayer(playerId);
        if (player == null || player.hasLeft()) {
            logger.debug("Player already left " + (player != null ? player.getName() : playerId));
            return;
        }
        logger.debug("Start leave game: " + player.getName());
        player.leave();
        if (checkIfGameIsOver()) {
            // no need to remove objects if only one player is left so the game is over
            return;
        }
        //20100423 - 800.4a
        Set<Card> toOutside = new HashSet<>();
        for (Iterator<Permanent> it = getBattlefield().getAllPermanents().iterator(); it.hasNext(); ) {
            Permanent perm = it.next();
            if (perm.isOwnedBy(playerId)) {
                if (perm.getAttachedTo() != null) {
                    Permanent attachedTo = getPermanent(perm.getAttachedTo());
                    if (attachedTo != null) {
                        attachedTo.removeAttachment(perm.getId(), null, this);
                    } else {
                        Player attachedToPlayer = getPlayer(perm.getAttachedTo());
                        if (attachedToPlayer != null) {
                            attachedToPlayer.removeAttachment(perm, null, this);
                        }
                    }
                }
                // check if it's a creature and must be removed from combat
                if (perm.isCreature(this) && this.getCombat() != null) {
                    perm.removeFromCombat(this, true);
                }
                toOutside.add(perm);
            } else if (perm.isControlledBy(player.getId())) {
                // and any effects which give that player control of any objects or players end
                Effects:
                for (ContinuousEffect effect : getContinuousEffects().getLayeredEffects(this)) {
                    if (effect.hasLayer(Layer.ControlChangingEffects_2)) {
                        for (Ability ability : getContinuousEffects().getLayeredEffectAbilities(effect)) {
                            if (effect.getTargetPointer().getTargets(this, ability).contains(perm.getId())) {
                                effect.discard();
                                continue Effects;
                            }
                            for (Target target : ability.getTargets()) {
                                for (UUID targetId : target.getTargets()) {
                                    if (targetId.equals(perm.getId())) {
                                        effect.discard();
                                        continue Effects;
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
        for (Card card : toOutside) {
            rememberLKI(card.getId(), Zone.BATTLEFIELD, card);
        }
        // needed to send event that permanent leaves the battlefield to allow non stack effects to execute
        player.moveCards(toOutside, Zone.OUTSIDE, null, this);
        // triggered abilities that don't use the stack have to be executed
        List<TriggeredAbility> abilities = state.getTriggered(player.getId());
        for (Iterator<TriggeredAbility> it = abilities.iterator(); it.hasNext(); ) {
            TriggeredAbility triggeredAbility = it.next();
            if (!triggeredAbility.isUsesStack()) {
                state.removeTriggeredAbility(triggeredAbility);
                player.triggerAbility(triggeredAbility, this);
                it.remove();
            }
        }
        // Then, if that player controlled any objects on the stack not represented by cards, those objects cease to exist.
        this.getState().getContinuousEffects().removeInactiveEffects(this);
        getStack().removeIf(object -> object.isControlledBy(playerId));
        // Then, if there are any objects still controlled by that player, those objects are exiled.
        applyEffects(); // to remove control from effects removed meanwhile
        List<Permanent> permanents = this.getBattlefield().getAllActivePermanents(playerId);
        for (Permanent permanent : permanents) {
            permanent.moveToExile(null, "", null, this);
        }

        // Remove cards from the player in all exile zones
        for (ExileZone exile : this.getExile().getExileZones()) {
            for (Iterator<UUID> it = exile.iterator(); it.hasNext(); ) {
                Card card = this.getCard(it.next());
                if (card != null && card.isOwnedBy(playerId)) {
                    it.remove();
                }
            }
        }

        //Remove all commander/emblems/plane the player controls
        boolean addPlaneAgain = false;
        for (Iterator<CommandObject> it = this.getState().getCommand().iterator(); it.hasNext(); ) {
            CommandObject obj = it.next();
            if (obj.isControlledBy(playerId)) {
                if (obj instanceof Emblem) {
                    ((Emblem) obj).discardEffects();// This may not be the best fix but it works
                }
                if (obj instanceof Plane) {
                    ((Plane) obj).discardEffects();
                    // Readd a new one
                    addPlaneAgain = true;
                }
                it.remove();
            }
        }

        if (addPlaneAgain) {
            boolean addedAgain = false;
            for (Player aplayer : state.getPlayers().values()) {
                if (!aplayer.hasLeft() && !addedAgain) {
                    addedAgain = true;
                    Plane plane = Plane.createRandomPlane();
                    plane.setControllerId(aplayer.getId());
                    addPlane(plane, null, aplayer.getId());
                }
            }
        }
        Iterator<Entry<UUID, Card>> it = gameCards.entrySet().iterator();

        while (it.hasNext()) {
            Entry<UUID, Card> entry = it.next();
            Card card = entry.getValue();
            if (card.isOwnedBy(playerId)) {
                it.remove();
            }
        }
        // Make sure effects of no longer existing objects are removed
        getContinuousEffects().removeInactiveEffects(this);
        // If the current monarch leaves the game. When that happens, the player whose turn it is becomes the monarch.
        // If the monarch leaves the game on their turn, the next player in turn order becomes the monarch.
        if (playerId.equals(getMonarchId())) {
            if (!isActivePlayer(playerId) && getActivePlayerId() != null) {
                setMonarchId(null, getActivePlayerId());
            } else {
                Player nextPlayer = getPlayerList().getNext(this, true);
                if (nextPlayer != null) {
                    setMonarchId(null, nextPlayer.getId());
                }
            }
        }
        // 801.2c The particular players within each player‘s range of influence are determined as each turn begins.
        // So no update of range if influence yet
    }

    @Override
    public UUID getActivePlayerId() {
        return state.getActivePlayerId();
    }

    @Override
    public UUID getPriorityPlayerId() {
        if (state.getPriorityPlayerId() == null) {
            return state.getActivePlayerId();
        }
        return state.getPriorityPlayerId();
    }

    @Override
    public void addSimultaneousEvent(GameEvent event) {
        state.addSimultaneousEvent(event, this);
    }

    @Override
    public void fireEvent(GameEvent event) {
        state.handleEvent(event, this);
    }

    @Override
    public boolean replaceEvent(GameEvent event) {
        return state.replaceEvent(event, this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability targetAbility) {
        return state.replaceEvent(event, targetAbility, this);
    }

    @Override
    public PreventionEffectData preventDamage(GameEvent event, Ability source, Game game, int amountToPrevent) {
        PreventionEffectData result = new PreventionEffectData(amountToPrevent);
        if (!event.getFlag()) { // damage is not preventable
            return result;
        }
        if (!(event instanceof DamageEvent)) {
            result.setError(true);
            return result;
        }
        DamageEvent damageEvent = (DamageEvent) event;
        GameEvent preventEvent = new PreventDamageEvent(damageEvent.getTargetId(), damageEvent.getSourceId(), source, source.getControllerId(), damageEvent.getAmount(), damageEvent.isCombatDamage());
        if (game.replaceEvent(preventEvent)) {
            result.setReplaced(true);
            return result;
        }

        if (event.getAmount() > amountToPrevent) {
            result.setPreventedDamage(amountToPrevent);
            damageEvent.setAmount(event.getAmount() - amountToPrevent);
        } else {
            result.setPreventedDamage(event.getAmount());
            damageEvent.setAmount(0);

        }
        if (amountToPrevent != Integer.MAX_VALUE) {
            // set remaining amount
            result.setRemainingAmount(amountToPrevent - result.getPreventedDamage());
        }
        MageObject damageSource = game.getObject(damageEvent.getSourceId());
        MageObject preventionSource = game.getObject(source);

        if (damageSource != null && preventionSource != null) {
            MageObject targetObject = game.getObject(event.getTargetId());
            String targetName = "";
            if (targetObject == null) {
                Player targetPlayer = game.getPlayer(event.getTargetId());
                if (targetPlayer != null) {
                    targetName = targetPlayer.getLogName();
                }
            } else {
                targetName = targetObject.getLogName();
            }
            if (!game.isSimulation()) {
                StringBuilder message = new StringBuilder(preventionSource.getLogName()).append(": Prevented ");
                message.append(result.getPreventedDamage()).append(" damage from ").append(damageSource.getLogName());
                if (!targetName.isEmpty()) {
                    message.append(" to ").append(targetName);
                }
                game.informPlayers(message.toString());
            }
        }
        game.fireEvent(new PreventedDamageEvent(damageEvent.getTargetId(), source.getSourceId(), source, source.getControllerId(), result.getPreventedDamage()));
        return result;

    }

    protected void removeCreaturesFromCombat() {
        //20091005 - 511.3
        getCombat().endCombat(this);
    }

    @Override
    public ContinuousEffects getContinuousEffects() {
        return state.getContinuousEffects();
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        //initialize transient objects during deserialization
        in.defaultReadObject();
        savedStates = new Stack<>();
        tableEventSource = new TableEventSource();
        playerQueryEventSource = new PlayerQueryEventSource();
        gameStates = new GameStates();
    }

    /**
     * Gets last known information about object in the zone. At the moment
     * doesn't take into account zone (it is expected that it doesn't really
     * matter, if not, then Map<UUID, Map<Zone, Card>> should be used instead).
     * <p>
     * Can return null.
     *
     * @param objectId
     * @param zone
     * @return
     */
    @Override
    public MageObject getLastKnownInformation(UUID objectId, Zone zone) {
        /*if (!lki.containsKey(objectId)) {
         return getCard(objectId);
         }*/
        Map<UUID, MageObject> lkiMap = lki.get(zone);
        if (lkiMap != null) {
            MageObject object = lkiMap.get(objectId);
            if (object != null) {
                return object.copy();
            }
            for (MageObject mageObject : lkiMap.values()) {
                if (mageObject instanceof Spell) {
                    if (((Spell) mageObject).getCard().getId().equals(objectId)) {
                        return mageObject;
                    }
                }

            }
        }
        return null;
    }

    @Override
    public MageObject getLastKnownInformation(UUID objectId, Zone zone, int zoneChangeCounter) {
        if (zone == Zone.BATTLEFIELD) {
            Map<Integer, MageObject> lkiMapExtended = lkiExtended.get(objectId);

            if (lkiMapExtended != null) {
                MageObject object = lkiMapExtended.get(zoneChangeCounter);
                if (object != null) {
                    return object.copy();
                }
            }
        }

        return getLastKnownInformation(objectId, zone);
    }

    @Override
    public CardState getLastKnownInformationCard(UUID objectId, Zone zone) {
        if (zone.isPublicZone()) {
            Map<UUID, CardState> lkiCardStateMap = lkiCardState.get(zone);
            if (lkiCardStateMap != null) {
                CardState cardState = lkiCardStateMap.get(objectId);
                return cardState;
            }
        }
        return null;
    }

    @Override
    public boolean getShortLivingLKI(UUID objectId, Zone zone) {
        Set<UUID> idSet = lkiShortLiving.get(zone);
        if (idSet != null) {
            return idSet.contains(objectId);
        }
        return false;
    }

    /**
     * Remembers object state to be used as Last Known Information.
     *
     * @param objectId
     * @param zone
     * @param object
     */
    @Override
    public void rememberLKI(UUID objectId, Zone zone, MageObject object) {
        if (object instanceof Permanent || object instanceof StackObject) {
            MageObject copy = object.copy();

            Map<UUID, MageObject> lkiMap = lki.computeIfAbsent(zone, k -> new HashMap<>());
            lkiMap.put(objectId, copy);

            // remembers if a object was in a zone during the resolution of an effect
            // e.g. Wrath destroys all and you the question is is the replacement effect to apply because the source was also moved by the same effect
            // because it happens all at the same time the replacement effect has still to be applied
            Set<UUID> idSet = lkiShortLiving.computeIfAbsent(zone, k -> new HashSet<>());
            idSet.add(objectId);
            if (object instanceof Permanent) {
                Map<Integer, MageObject> lkiExtendedMap = lkiExtended.computeIfAbsent(objectId, k -> new HashMap<>());
                lkiExtendedMap.put(object.getZoneChangeCounter(this), copy);
            }
        } else if (zone.isPublicZone()) {
            // Remember card state in this public zone (mainly removed/gained abilities)
            // Must save all card parts (mdf, split)
            CardUtil.getObjectParts(object).forEach(partId -> {
                Map<UUID, CardState> lkiMap = lkiCardState.computeIfAbsent(zone, k -> new HashMap<>());
                lkiMap.put(partId, getState().getCardState(partId).copy());
            });
        }
    }

    /**
     * Reset objects stored for Last Known Information. (Happens if all effects
     * are applied und stack is empty)
     */
    @Override
    public void resetLKI() {
        lki.clear();
        lkiExtended.clear();
        lkiCardState.clear();
        infiniteLoopCounter = 0;
        stackObjectsCheck.clear();
    }

    @Override
    public void resetShortLivingLKI() {
        lkiShortLiving.clear();
    }

    @Override
    public void cheat(UUID ownerId, Map<Zone, String> commands) {
        if (commands != null) {
            Player player = getPlayer(ownerId);
            if (player != null) {
                for (Map.Entry<Zone, String> command : commands.entrySet()) {
                    switch (command.getKey()) {
                        case HAND:
                            if (command.getValue().equals("clear")) {
                                player.getHand().clear();
                            }
                            break;
                        case LIBRARY:
                            if (command.getValue().equals("clear")) {
                                player.getLibrary().clear();
                            }
                            break;
                        case OUTSIDE:
                            if (command.getValue().contains("life:")) {
                                String[] s = command.getValue().split(":");
                                if (s.length == 2) {
                                    try {
                                        int amount = Integer.parseInt(s[1]);
                                        player.setLife(amount, this, null);
                                        logger.debug("Setting player's life: ");
                                    } catch (NumberFormatException e) {
                                        logger.fatal("error setting life", e);
                                    }
                                }

                            }
                            break;
                    }
                }
            }
        }
    }

    @Override
    public Map<Zone, Map<UUID, MageObject>> getLKI() {
        return lki;
    }

    @Override
    public void cheat(UUID ownerId, List<Card> library, List<Card> hand, List<PermanentCard> battlefield, List<Card> graveyard, List<Card> command) {
        // fake test ability for triggers and events
        Ability fakeSourceAbilityTemplate = new SimpleStaticAbility(Zone.OUTSIDE, new InfoEffect("adding testing cards"));
        fakeSourceAbilityTemplate.setControllerId(ownerId);

        Player player = getPlayer(ownerId);
        if (player != null) {
            loadCards(ownerId, library);
            loadCards(ownerId, hand);
            loadCards(ownerId, battlefield);
            loadCards(ownerId, graveyard);
            loadCards(ownerId, command);

            for (Card card : library) {
                player.getLibrary().putOnTop(card, this);
            }

            for (Card card : hand) {
                card.setZone(Zone.HAND, this);
                player.getHand().add(card);
            }

            for (Card card : graveyard) {
                card.setZone(Zone.GRAVEYARD, this);
                player.getGraveyard().add(card);
            }

            // as commander (only commander games, look at init code in GameCommanderImpl)
            if (this instanceof GameCommanderImpl) {
                for (Card card : command) {
                    ((GameCommanderImpl) this).addCommander(card, player);
                    // no needs in initCommander call -- it's uses on game startup (init)
                }
            } else if (!command.isEmpty()) {
                throw new IllegalArgumentException("Command zone supports in commander test games");
            }

            for (PermanentCard permanentCard : battlefield) {
                Ability fakeSourceAbility = fakeSourceAbilityTemplate.copy();
                fakeSourceAbility.setSourceId(permanentCard.getId());
                CardUtil.putCardOntoBattlefieldWithEffects(fakeSourceAbility, this, permanentCard, player);
            }

            applyEffects();
        }
    }

    private void loadCards(UUID ownerId, List<? extends Card> cards) {
        if (cards == null) {
            return;
        }
        Set<Card> set = new HashSet<>(cards);
        loadCards(set, ownerId);
    }

    @Override
    public boolean endTurn(Ability source) {
        getTurn().endTurn(this, getActivePlayerId(), source);
        return true;
    }

    @Override
    public int doAction(Ability source, MageAction action) {
        return action.doAction(source, this);
    }

    @Override
    public Date getStartTime() {
        if (startTime == null) {
            return null;
        }
        return new Date(startTime.getTime());
    }

    @Override
    public Date getEndTime() {
        if (endTime == null) {
            return null;
        }
        return new Date(endTime.getTime());
    }

    @Override
    public void setGameOptions(GameOptions options) {
        this.gameOptions = options;
    }

    @Override
    public void setLosingPlayer(Player player) {
        this.losingPlayer = player;
    }

    @Override
    public Player getLosingPlayer() {
        return this.losingPlayer;
    }

    @Override
    public void informPlayer(Player player, String message) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.informPlayer(player.getId(), message);
    }

    /**
     * If true, only self scope replacement effects are applied
     *
     * @param scopeRelevant
     */
    @Override
    public void setScopeRelevant(boolean scopeRelevant) {
        this.scopeRelevant = scopeRelevant;
    }

    /**
     * @return - true if only self scope replacement effects have to be applied
     */
    @Override
    public boolean getScopeRelevant() {
        return this.scopeRelevant;
    }

    @Override
    public boolean isSaveGame() {
        return saveGame;
    }

    @Override
    public void setSaveGame(boolean saveGame) {
        this.saveGame = saveGame;
    }

    public void setStartMessage(String startMessage) {
        this.startMessage = startMessage;
    }

    @Override
    public void initTimer(UUID playerId) {
        if (priorityTime > 0) {
            tableEventSource.fireTableEvent(EventType.INIT_TIMER, playerId, null, this);
        }
    }

    @Override
    public void resumeTimer(UUID playerId) {
        if (priorityTime > 0) {
            tableEventSource.fireTableEvent(EventType.RESUME_TIMER, playerId, null, this);
        }
    }

    @Override
    public void pauseTimer(UUID playerId) {
        if (priorityTime > 0) {
            tableEventSource.fireTableEvent(EventType.PAUSE_TIMER, playerId, null, this);
        }
    }

    @Override
    public int getPriorityTime() {
        return priorityTime;
    }

    @Override
    public void setPriorityTime(int priorityTime) {
        this.priorityTime = priorityTime;
    }

    @Override
    public UUID getStartingPlayerId() {
        return startingPlayerId;
    }

    @Override
    public void setStartingPlayerId(UUID startingPlayerId) {
        this.startingPlayerId = startingPlayerId;
    }

    @Override
    public int getStartingLife() {
        return startingLife;
    }

    @Override
    public void setDraw(UUID playerId) {
        Player player = getPlayer(playerId);
        if (player != null) {
            for (UUID playerToSetId : getState().getPlayersInRange(playerId, this)) {
                Player playerToDraw = getPlayer(playerToSetId);
                if (playerToDraw != null) {
                    playerToDraw.drew(this);
                }
            }
        }
    }

    @Override
    public void saveRollBackGameState() {
        if (gameOptions.rollbackTurnsAllowed) {
            int toDelete = getTurnNum() - ROLLBACK_TURNS_MAX;
            if (toDelete > 0) {
                gameStatesRollBack.remove(toDelete);
            }
            gameStatesRollBack.put(getTurnNum(), state.copy());
        }
    }

    @Override
    public boolean canRollbackTurns(int turnsToRollback) {
        int turnToGoTo = getTurnNum() - turnsToRollback;
        return turnToGoTo > 0 && gameStatesRollBack.containsKey(turnToGoTo);
    }

    private void rollbackTurnsExecution(int turnToGoToForRollback) {
        GameState restore = gameStatesRollBack.get(turnToGoToForRollback);
        if (restore != null) {
            informPlayers(GameLog.getPlayerRequestColoredText("Player request: Rolling back to start of turn " + restore.getTurnNum()));
            state.restoreForRollBack(restore);
            playerList.setCurrent(state.getPlayerByOrderId());
            // Reset temporary created bookmarks because no longer valid after rollback
            savedStates.clear();
            gameStates.clear();
            // because restore uses the objects without copy each copy the state again
            gameStatesRollBack.put(getTurnNum(), state.copy());

            for (Player playerObject : getPlayers().values()) {
                if (playerObject.isInGame()) {
                    playerObject.abortReset();
                }
            }
        }
        executingRollback = false;
    }

    @Override
    public synchronized void rollbackTurns(int turnsToRollback) {
        if (gameOptions.rollbackTurnsAllowed && !executingRollback) {
            int turnToGoTo = getTurnNum() - turnsToRollback;
            if (turnToGoTo < 1 || !gameStatesRollBack.containsKey(turnToGoTo)) {
                informPlayers(GameLog.getPlayerRequestColoredText("Player request: It's not possible to rollback " + turnsToRollback + " turn(s)"));
            } else {
                executingRollback = true;
                turnToGoToForRollback = turnToGoTo;
                for (Player playerObject : getPlayers().values()) {
                    if (playerObject.isHuman() && playerObject.canRespond()) {
                        playerObject.resetStoredBookmark(this);
                        playerObject.abort();
                        playerObject.resetPlayerPassedActions();
                    }
                }
                fireUpdatePlayersEvent();
                if (gameOptions.testMode && gameStopped) { // in test mode execute rollback directly
                    rollbackTurnsExecution(turnToGoToForRollback);
                }
            }
        }
    }

    @Override
    public boolean executingRollback() {
        return executingRollback;
    }

    @Override
    public void setEnterWithCounters(UUID sourceId, Counters counters) {
        if (counters == null) {
            enterWithCounters.remove(sourceId);
            return;
        }
        enterWithCounters.put(sourceId, counters);
    }

    @Override
    public Counters getEnterWithCounters(UUID sourceId) {
        return enterWithCounters.get(sourceId);
    }

    private Map<String, Serializable> addMessageToOptions(MessageToClient message, Map<String, Serializable> options) {
        if (message.getSecondMessage() != null) {
            if (options == null) {
                options = new HashMap<>();
            }
            options.put("secondMessage", message.getSecondMessage());
        }
        if (message.getHintText() != null) {
            if (options == null) {
                options = new HashMap<>();
            }
            options.put("hintText", message.getHintText());
        }
        return options;
    }

    @Override
    public UUID getMonarchId() {
        return getState().getMonarchId();
    }

    @Override
    public void setMonarchId(Ability source, UUID monarchId) {
        if (monarchId.equals(getMonarchId())) {
            // Nothing happens if you're already the monarch
            return;
        }

        if (replaceEvent(GameEvent.getEvent(GameEvent.EventType.BECOME_MONARCH, monarchId, source, monarchId))) {
            return;
        }

        if (getMonarchId() == null) {
            getState().addDesignation(new Monarch(), this, monarchId);
        }

        Player newMonarch = getPlayer(monarchId);
        if (newMonarch != null) {
            getState().setMonarchId(monarchId);
            informPlayers(newMonarch.getLogName() + " is the monarch");
            fireEvent(new GameEvent(GameEvent.EventType.BECOMES_MONARCH, monarchId, source, monarchId));
        }
    }

    @Override
    public UUID getInitiativeId() {
        return getState().getInitiativeId();
    }

    @Override
    public void takeInitiative(Ability source, UUID initiativeId) {
        // First time someone takes the initiative
        if (getInitiativeId() == null) { // 1. Nobody has initiative
            getState().addDesignation(new Initiative(), this, initiativeId);
        }

        // Update it every time, even if it doesn't have to change to make the code simpler.
        // It only really has to change under 2 circumstances:
        //      1. First time someone takes the initiative
        //      2. A player taking the initiative when another player currently has it.
        getState().setInitiativeId(initiativeId);

        informPlayers(getPlayer(initiativeId).getLogName() + " takes the initiative");
        fireEvent(new GameEvent(GameEvent.EventType.TOOK_INITIATIVE, initiativeId, source, initiativeId));
    }

    @Override
    public int damagePlayerOrPlaneswalker(UUID playerOrWalker, int damage, UUID attackerId, Ability source, Game game, boolean combatDamage, boolean preventable) {
        return damagePlayerOrPlaneswalker(playerOrWalker, damage, attackerId, source, game, combatDamage, preventable, null);
    }

    @Override
    public int damagePlayerOrPlaneswalker(UUID playerOrWalker, int damage, UUID attackerId, Ability source, Game game, boolean combatDamage, boolean preventable, List<UUID> appliedEffects) {
        Player player = getPlayer(playerOrWalker);
        if (player != null) {
            return player.damage(damage, attackerId, source, game, combatDamage, preventable, appliedEffects);
        }
        Permanent permanent = getPermanent(playerOrWalker);
        if (permanent != null) {
            return permanent.damage(damage, attackerId, source, game, combatDamage, preventable, appliedEffects);
        }
        return 0;
    }

    @Override
    public Mulligan getMulligan() {
        return mulligan;
    }

    @Override
    public Set<UUID> getCommandersIds(Player player, CommanderCardType commanderCardType, boolean returnAllCardParts) {
        //noinspection deprecation - it's ok to use it in inner method
        Set<UUID> mainCards = player.getCommandersIds();
        return filterCommandersBySearchZone(mainCards, returnAllCardParts);
    }

    final protected Set<UUID> filterCommandersBySearchZone(Set<UUID> commanderMainCards, boolean returnAllCardParts) {
        // filter by zone search (example: if you search commanders on battlefield then must see all sides of mdf cards)
        Set<UUID> filteredCards = new HashSet<>();
        if (returnAllCardParts) {
            // need all card parts
            commanderMainCards.stream()
                    .map(this::getCard)
                    .filter(Objects::nonNull)
                    .forEach(card -> {
                        filteredCards.addAll(CardUtil.getObjectParts(card));
                    });
        } else {
            filteredCards.addAll(commanderMainCards);
        }

        return filteredCards;
    }

    @Override
    public void setGameStopped(boolean gameStopped) {
        this.gameStopped = gameStopped;
    }

    @Override
    public boolean isGameStopped() {
        return gameStopped;
    }

    @Override
    public boolean isTurnOrderReversed() {
        return state.getReverseTurnOrder();
    }

    @Override
    public String toString() {
        Player activePayer = this.getPlayer(this.getActivePlayerId());
        StringBuilder sb = new StringBuilder()
                .append(this.getGameType().toString())
                .append("; ").append(CardUtil.getTurnInfo(this))
                .append("; active: ").append((activePayer == null ? "none" : activePayer.getName()))
                .append("; stack: ").append(this.getStack().toString());
        return sb.toString();
    }
}
