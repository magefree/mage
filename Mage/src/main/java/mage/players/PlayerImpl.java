package mage.players;

import com.google.common.collect.ImmutableMap;
import mage.*;
import mage.abilities.*;
import mage.abilities.ActivatedAbility.ActivationStatus;
import mage.abilities.common.PassAbility;
import mage.abilities.common.PlayLandAsCommanderAbility;
import mage.abilities.common.WhileSearchingPlayFromLibraryAbility;
import mage.abilities.common.delayed.AtTheEndOfTurnStepPostDelayedTriggeredAbility;
import mage.abilities.costs.*;
import mage.abilities.costs.mana.AlternateManaPaymentAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.RestrictionUntapNotMoreThanEffect;
import mage.abilities.effects.common.LoseControlOnOtherPlayersControllerEffect;
import mage.abilities.keyword.*;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.ManaOptions;
import mage.actions.MageDrawAction;
import mage.cards.*;
import mage.cards.decks.Deck;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.designations.Designation;
import mage.designations.DesignationType;
import mage.filter.FilterCard;
import mage.filter.FilterMana;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureForCombat;
import mage.filter.common.FilterCreatureForCombatBlock;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.*;
import mage.game.combat.CombatGroup;
import mage.game.command.CommandObject;
import mage.game.events.*;
import mage.game.match.MatchPlayer;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.SquirrelToken;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.game.turn.Step;
import mage.players.net.UserData;
import mage.target.Target;
import mage.target.TargetAmount;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetDiscard;
import mage.util.CardUtil;
import mage.util.GameLog;
import mage.util.RandomUtil;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public abstract class PlayerImpl implements Player, Serializable {

    private static final Logger logger = Logger.getLogger(PlayerImpl.class);

    /**
     * Used to cancel waiting requests send to the player
     */
    protected boolean abort;

    protected final UUID playerId;
    protected String name;
    protected boolean human;
    protected int life;
    protected boolean wins;
    protected boolean draws;
    protected boolean loses;
    protected Library library;
    protected Cards sideboard;
    protected Cards hand;
    protected Graveyard graveyard;
    protected Set<UUID> commandersIds = new HashSet<>(0);
    protected Abilities<Ability> abilities;
    protected Counters counters;
    protected int landsPlayed;
    protected int landsPerTurn = 1;
    protected int loyaltyUsePerTurn = 1;
    protected int maxHandSize = 7;
    protected int maxAttackedBy = Integer.MAX_VALUE;
    protected ManaPool manaPool;
    // priority control
    protected boolean passed; // player passed priority
    protected boolean passedTurn; // F4
    protected boolean passedTurnSkipStack; // F6 // TODO: research
    protected boolean passedUntilEndOfTurn; // F5
    protected boolean passedUntilNextMain; // F7
    protected boolean passedUntilStackResolved; // F10
    protected Date dateLastAddedToStack;
    protected boolean passedUntilEndStepBeforeMyTurn; // F11
    protected boolean skippedAtLeastOnce; // used to track if passed started in specific phase
    /**
     * This indicates that player passed all turns until their own turn starts
     * (F9). Note! This differs from passedTurn as it doesn't care about spells
     * and abilities in the stack and will pass them as well.
     */
    protected boolean passedAllTurns; // F9
    protected AbilityType justActivatedType; // used to check if priority can be passed automatically

    protected int turns;
    protected int storedBookmark = -1;
    protected int priorityTimeLeft = Integer.MAX_VALUE;

    // conceded or connection lost game
    protected boolean left;
    // set if the player quits the complete match
    protected boolean quit;
    // set if the player lost match because of priority timeout
    protected boolean timerTimeout;
    // set if the player lost match because of idle timeout
    protected boolean idleTimeout;

    protected RangeOfInfluence range;
    protected Set<UUID> inRange = new HashSet<>(); // players list in current range of influence (updates each turn)

    protected boolean isTestMode = false;
    protected boolean canGainLife = true;
    protected boolean canLoseLife = true;
    protected boolean canPayLifeCost = true;
    protected boolean loseByZeroOrLessLife = true;
    protected boolean canPlayCardsFromGraveyard = true;
    protected boolean drawsOnOpponentsTurn = false;

    protected FilterPermanent sacrificeCostFilter;

    protected final List<AlternativeSourceCosts> alternativeSourceCosts = new ArrayList<>();

    protected boolean isGameUnderControl = true;
    protected UUID turnController;
    protected List<UUID> turnControllers = new ArrayList<>();
    protected Set<UUID> playersUnderYourControl = new HashSet<>();

    protected Set<UUID> usersAllowedToSeeHandCards = new HashSet<>();

    protected List<UUID> attachments = new ArrayList<>();

    protected boolean topCardRevealed = false;

    // 800.4i When a player leaves the game, any continuous effects with durations that last until that player's next turn
    // or until a specific point in that turn will last until that turn would have begun.
    // They neither expire immediately nor last indefinitely.
    protected boolean reachedNextTurnAfterLeaving = false;

    // indicates that the spell with the set sourceId can be cast with an alternate mana costs (can also be no mana costs)
    // support multiple cards with alternative mana cost
    protected Set<UUID> castSourceIdWithAlternateMana = new HashSet<>();
    protected Map<UUID, ManaCosts<ManaCost>> castSourceIdManaCosts = new HashMap<>();
    protected Map<UUID, Costs<Cost>> castSourceIdCosts = new HashMap<>();

    // indicates that the player is in mana payment phase
    protected boolean payManaMode = false;

    protected UserData userData;
    protected MatchPlayer matchPlayer;

    protected List<Designation> designations = new ArrayList<>();

    // mana colors the player can handle like Phyrexian mana
    protected FilterMana phyrexianColors;

    // Used during available mana calculation to give back possible available net mana from triggered mana abilities (No need to copy)
    protected final List<List<Mana>> availableTriggeredManaList = new ArrayList<>();

    /**
     * During some steps we can't play anything
     */
    protected final Map<PhaseStep, Step.StepPart> silentPhaseSteps = ImmutableMap.<PhaseStep, Step.StepPart>builder().
            put(PhaseStep.DECLARE_ATTACKERS, Step.StepPart.PRE).build();

    public PlayerImpl(String name, RangeOfInfluence range) {
        this(UUID.randomUUID());
        this.name = name;
        this.range = range;
        hand = new CardsImpl();
        graveyard = new Graveyard();
        abilities = new AbilitiesImpl<>();
        counters = new Counters();
        manaPool = new ManaPool(playerId);
        library = new Library(playerId);
        sideboard = new CardsImpl();
        phyrexianColors = null;
    }

    protected PlayerImpl(UUID id) {
        this.playerId = id;
    }

    public PlayerImpl(final PlayerImpl player) {
        this.abort = player.abort;
        this.playerId = player.playerId;

        this.name = player.name;
        this.human = player.human;
        this.life = player.life;
        this.wins = player.wins;
        this.draws = player.draws;
        this.loses = player.loses;

        this.library = player.library.copy();
        this.sideboard = player.sideboard.copy();
        this.hand = player.hand.copy();
        this.graveyard = player.graveyard.copy();
        this.commandersIds = player.commandersIds;
        this.abilities = player.abilities.copy();
        this.counters = player.counters.copy();

        this.landsPlayed = player.landsPlayed;
        this.landsPerTurn = player.landsPerTurn;
        this.loyaltyUsePerTurn = player.loyaltyUsePerTurn;
        this.maxHandSize = player.maxHandSize;
        this.maxAttackedBy = player.maxAttackedBy;
        this.manaPool = player.manaPool.copy();
        this.turns = player.turns;

        this.left = player.left;
        this.quit = player.quit;
        this.timerTimeout = player.timerTimeout;
        this.idleTimeout = player.idleTimeout;
        this.range = player.range;
        this.canGainLife = player.canGainLife;
        this.canLoseLife = player.canLoseLife;
        this.loseByZeroOrLessLife = player.loseByZeroOrLessLife;
        this.canPlayCardsFromGraveyard = player.canPlayCardsFromGraveyard;
        this.drawsOnOpponentsTurn = player.drawsOnOpponentsTurn;

        this.attachments.addAll(player.attachments);

        this.inRange.addAll(player.inRange);
        this.userData = player.userData;
        this.matchPlayer = player.matchPlayer;

        this.canPayLifeCost = player.canPayLifeCost;
        this.sacrificeCostFilter = player.sacrificeCostFilter;
        this.alternativeSourceCosts.addAll(player.alternativeSourceCosts);
        this.storedBookmark = player.storedBookmark;

        this.topCardRevealed = player.topCardRevealed;
        this.playersUnderYourControl.addAll(player.playersUnderYourControl);
        this.usersAllowedToSeeHandCards.addAll(player.usersAllowedToSeeHandCards);

        this.isTestMode = player.isTestMode;
        this.isGameUnderControl = player.isGameUnderControl;

        this.turnController = player.turnController;
        this.turnControllers.addAll(player.turnControllers);

        this.passed = player.passed;
        this.passedTurn = player.passedTurn;
        this.passedTurnSkipStack = player.passedTurnSkipStack;
        this.passedUntilEndOfTurn = player.passedUntilEndOfTurn;
        this.passedUntilNextMain = player.passedUntilNextMain;
        this.passedUntilStackResolved = player.passedUntilStackResolved;
        this.dateLastAddedToStack = player.dateLastAddedToStack;
        this.passedUntilEndStepBeforeMyTurn = player.passedUntilEndStepBeforeMyTurn;
        this.skippedAtLeastOnce = player.skippedAtLeastOnce;
        this.passedAllTurns = player.passedAllTurns;
        this.justActivatedType = player.justActivatedType;

        this.priorityTimeLeft = player.getPriorityTimeLeft();
        this.reachedNextTurnAfterLeaving = player.reachedNextTurnAfterLeaving;

        this.castSourceIdWithAlternateMana.addAll(player.getCastSourceIdWithAlternateMana());
        for (Entry<UUID, ManaCosts<ManaCost>> entry : player.getCastSourceIdManaCosts().entrySet()) {
            this.castSourceIdManaCosts.put(entry.getKey(), (entry.getValue() == null ? null : entry.getValue().copy()));
        }
        for (Entry<UUID, Costs<Cost>> entry : player.getCastSourceIdCosts().entrySet()) {
            this.castSourceIdCosts.put(entry.getKey(), (entry.getValue() == null ? null : entry.getValue().copy()));
        }
        this.payManaMode = player.payManaMode;
        this.phyrexianColors = player.getPhyrexianColors() != null ? player.phyrexianColors.copy() : null;
        for (Designation object : player.designations) {
            this.designations.add(object.copy());
        }
    }

    @Override
    public void restore(Player player) {
        this.name = player.getName();
        this.human = player.isHuman();
        this.life = player.getLife();

        this.passed = player.isPassed();

        // Don't restore more global states. If restored they are probably cause for unintended draws (https://github.com/magefree/mage/issues/1205).
//        this.wins = player.hasWon();
//        this.loses = player.hasLost();
//        this.left = player.hasLeft();
//        this.quit = player.hasQuit();
        // Makes no sense to restore
//        this.priorityTimeLeft = player.getPriorityTimeLeft();
//        this.idleTimeout = player.hasIdleTimeout();
//        this.timerTimeout = player.hasTimerTimeout();
        // can't change so no need to restore
//        this.isTestMode = player.isTestMode();
        // This is meta data and should'nt be restored by rollback
//        this.userData = player.getUserData();
        this.library = player.getLibrary().copy();
        this.sideboard = player.getSideboard().copy();
        this.hand = player.getHand().copy();
        this.graveyard = player.getGraveyard().copy();

        //noinspection deprecation - it's ok to use it in inner methods
        this.commandersIds = new HashSet<>(player.getCommandersIds());

        this.abilities = player.getAbilities().copy();
        this.counters = player.getCounters().copy();

        this.landsPlayed = player.getLandsPlayed();
        this.landsPerTurn = player.getLandsPerTurn();
        this.loyaltyUsePerTurn = player.getLoyaltyUsePerTurn();
        this.maxHandSize = player.getMaxHandSize();
        this.maxAttackedBy = player.getMaxAttackedBy();
        this.manaPool = player.getManaPool().copy();
        // Restore user specific settings in case changed since state save
        this.manaPool.setAutoPayment(this.getUserData().isManaPoolAutomatic());
        this.manaPool.setAutoPaymentRestricted(this.getUserData().isManaPoolAutomaticRestricted());

        this.turns = player.getTurns();

        this.range = player.getRange();
        this.canGainLife = player.isCanGainLife();
        this.canLoseLife = player.isCanLoseLife();
        this.attachments.clear();
        this.attachments.addAll(player.getAttachments());

        this.inRange.clear();
        this.inRange.addAll(player.getInRange());
        this.canPayLifeCost = player.getCanPayLifeCost();
        this.sacrificeCostFilter = player.getSacrificeCostFilter() != null
                ? player.getSacrificeCostFilter().copy() : null;
        this.loseByZeroOrLessLife = player.canLoseByZeroOrLessLife();
        this.canPlayCardsFromGraveyard = player.canPlayCardsFromGraveyard();
        this.drawsOnOpponentsTurn = player.isDrawsOnOpponentsTurn();
        this.alternativeSourceCosts.clear();
        this.alternativeSourceCosts.addAll(player.getAlternativeSourceCosts());

        this.topCardRevealed = player.isTopCardRevealed();
        this.playersUnderYourControl.clear();
        this.playersUnderYourControl.addAll(player.getPlayersUnderYourControl());
        this.isGameUnderControl = player.isGameUnderControl();

        this.turnController = player.getTurnControlledBy();
        this.turnControllers.clear();
        this.turnControllers.addAll(player.getTurnControllers());
        this.reachedNextTurnAfterLeaving = player.hasReachedNextTurnAfterLeaving();

        this.clearCastSourceIdManaCosts();
        this.castSourceIdWithAlternateMana.clear();
        this.castSourceIdWithAlternateMana.addAll(player.getCastSourceIdWithAlternateMana());
        for (Entry<UUID, ManaCosts<ManaCost>> entry : player.getCastSourceIdManaCosts().entrySet()) {
            this.castSourceIdManaCosts.put(entry.getKey(), (entry.getValue() == null ? null : entry.getValue().copy()));
        }
        for (Entry<UUID, Costs<Cost>> entry : player.getCastSourceIdCosts().entrySet()) {
            this.castSourceIdCosts.put(entry.getKey(), (entry.getValue() == null ? null : entry.getValue().copy()));
        }

        this.phyrexianColors = player.getPhyrexianColors() != null ? player.getPhyrexianColors().copy() : null;

        this.designations.clear();
        for (Designation object : player.getDesignations()) {
            this.designations.add(object.copy());
        }

        // Don't restore!
        // this.storedBookmark
        // this.usersAllowedToSeeHandCards
    }

    @Override
    public void useDeck(Deck deck, Game game) {
        library.clear();
        library.addAll(deck.getCards(), game);
        sideboard.clear();
        for (Card card : deck.getSideboard()) {
            sideboard.add(card);
        }
    }

    /**
     * Cast e.g. from Karn Liberated to restart the current game
     *
     * @param game
     */
    @Override
    public void init(Game game) {
        init(game, false);
    }

    @Override
    public void init(Game game, boolean testMode) {
        this.abort = false;
        if (!testMode) {
            this.hand.clear();
            this.graveyard.clear();
        }
        this.library.reset();
        this.abilities.clear();
        this.counters.clear();
        this.wins = false;
        this.draws = false;
        this.loses = false;
        this.left = false;
        // reset is necessary because in tournament player will be used for each round
        this.quit = false;
        this.timerTimeout = false;
        this.idleTimeout = false;

        this.turns = 0;
        this.isGameUnderControl = true;
        this.turnController = this.getId();
        this.turnControllers.clear();
        this.playersUnderYourControl.clear();

        this.passed = false;
        this.passedTurn = false;
        this.passedTurnSkipStack = false;
        this.passedUntilEndOfTurn = false;
        this.passedUntilNextMain = false;
        this.passedUntilStackResolved = false;
        this.dateLastAddedToStack = null;
        this.passedUntilEndStepBeforeMyTurn = false;
        this.skippedAtLeastOnce = false;
        this.passedAllTurns = false;
        this.justActivatedType = null;

        this.canGainLife = true;
        this.canLoseLife = true;
        this.topCardRevealed = false;
        this.payManaMode = false;
        this.setLife(game.getStartingLife(), game, null);
        this.setReachedNextTurnAfterLeaving(false);

        this.clearCastSourceIdManaCosts();

        this.getManaPool().init(); // needed to remove mana that not empties on step change from previous game if left
        this.phyrexianColors = null;

        this.designations.clear();
    }

    /**
     * called before apply effects
     */
    @Override
    public void reset() {
        this.abilities.clear();
        this.landsPerTurn = 1;
        this.loyaltyUsePerTurn = 1;
        this.maxHandSize = 7;
        this.maxAttackedBy = Integer.MAX_VALUE;
        this.canGainLife = true;
        this.canLoseLife = true;
        this.canPayLifeCost = true;
        this.sacrificeCostFilter = null;
        this.loseByZeroOrLessLife = true;
        this.canPlayCardsFromGraveyard = false;
        this.drawsOnOpponentsTurn = false;
        this.topCardRevealed = false;
        this.alternativeSourceCosts.clear();
        this.clearCastSourceIdManaCosts();
        this.getManaPool().clearEmptyManaPoolRules();
        this.phyrexianColors = null;
    }

    @Override
    public Counters getCounters() {
        return counters;
    }

    @Override
    public void beginTurn(Game game) {
        this.landsPlayed = 0;
        updateRange(game);
    }

    @Override
    public RangeOfInfluence getRange() {
        return range;
    }

    @Override
    public void updateRange(Game game) {
        // 20100423 - 801.2c
        // 801.2c The particular players within each player’s range of influence are determined as each turn begins.
        // BUT it also uses before game start to fill game and card data in starting game events
        inRange.clear();
        inRange.add(this.playerId);
        inRange.addAll(getAllNearPlayers(game, true));
        inRange.addAll(getAllNearPlayers(game, false));
    }

    private Set<UUID> getAllNearPlayers(Game game, boolean needPrevious) {
        // find all near players (search from current player position)
        Set<UUID> foundedList = new HashSet<>();
        PlayerList players = game.getState().getPlayerList(this.playerId);
        int needAmount = this.getRange().getRange(); // distance to search (0 - ALL range)
        int foundedAmount = 0;
        while (needAmount == 0 || foundedAmount < needAmount) {
            Player foundedPlayer = needPrevious ? players.getPrevious(game) : players.getNext(game, false);

            // PlayerList is inifine, so stops on repeats
            if (foundedPlayer == null || foundedPlayer.getId().equals(this.playerId) || foundedList.contains(foundedPlayer.getId())) {
                break;
            }
            // skip leaved player (no needs cause next/previous code already checks it)

            foundedList.add(foundedPlayer.getId());
            foundedAmount++;
        }
        return foundedList;
    }

    @Override
    public Set<UUID> getInRange() {
        if (inRange.isEmpty()) {
            // runtime check: inRange filled on beginTurn, but unit tests adds cards by cheat engine before game starting,
            // so inRange will be empty and some ETB effects can be broken (example: Spark Double puts direct to battlefield).
            // Cheat engine already have a workaround, so that error must not be visible in normal situation.
            throw new IllegalStateException("Wrong code usage (game is not started, but you call getInRange in some effects).");
        }

        return inRange;
    }

    @Override
    public Set<UUID> getPlayersUnderYourControl() {
        return this.playersUnderYourControl;
    }

    @Override
    public void controlPlayersTurn(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        player.setTurnControlledBy(this.getId());
        game.informPlayers(getLogName() + " controls the turn of " + player.getLogName());
        if (!playerId.equals(this.getId())) {
            this.playersUnderYourControl.add(playerId);
            if (!player.hasLeft() && !player.hasLost()) {
                player.setGameUnderYourControl(false);
            }
            DelayedTriggeredAbility ability = new AtTheEndOfTurnStepPostDelayedTriggeredAbility(
                    new LoseControlOnOtherPlayersControllerEffect(this.getLogName(), player.getLogName()));
            ability.setSourceId(getId());
            ability.setControllerId(getId());
            game.addDelayedTriggeredAbility(ability, null);
        }
    }

    @Override
    public void setTurnControlledBy(UUID playerId) {
        this.turnController = playerId;
        this.turnControllers.add(playerId);
    }

    @Override
    public List<UUID> getTurnControllers() {
        return this.turnControllers;
    }

    @Override
    public UUID getTurnControlledBy() {
        return this.turnController;
    }

    @Override
    public void resetOtherTurnsControlled() {
        playersUnderYourControl.clear();
    }

    /**
     * returns true if the player has the control itself - false if the player
     * is controlled by another player
     *
     * @return
     */
    @Override
    public boolean isGameUnderControl() {
        return isGameUnderControl;
    }

    @Override
    public void setGameUnderYourControl(boolean value) {
        setGameUnderYourControl(value, true);
    }

    @Override
    public void setGameUnderYourControl(boolean value, boolean fullRestore) {
        this.isGameUnderControl = value;
        if (isGameUnderControl) {
            if (fullRestore) {
                this.turnControllers.clear();
                this.turnController = getId();
            } else {
                if (turnControllers.size() > 0) {
                    this.turnControllers.remove(turnControllers.size() - 1);
                }
                if (turnControllers.isEmpty()) {
                    this.turnController = getId();
                } else {
                    this.turnController = turnControllers.get(turnControllers.size() - 1);
                    isGameUnderControl = false;
                }
            }
        }
    }

    @Override
    public void endOfTurn(Game game) {
        this.passedTurn = false;
        this.passedTurnSkipStack = false;
    }

    @Override
    public boolean canBeTargetedBy(MageObject source, UUID sourceControllerId, Game game) {
        if (this.hasLost() || this.hasLeft()) {
            return false;
        }
        if (source != null) {
            // there is only variant of shroud, so check the instance and any asthougheffects that would ignore it
            if (abilities.containsKey(ShroudAbility.getInstance().getId())
                    && game.getContinuousEffects().asThough(this.getId(), AsThoughEffectType.SHROUD, null, sourceControllerId, game) == null) {
                return false;
            }
            // check for all variants of hexproof and any asthougheffects that would ignore it
            // TODO there may be "prevented by rule-modification" effects, so add them if known
            for (Ability a : abilities) {
                if (a instanceof HexproofBaseAbility
                        && ((HexproofBaseAbility) a).checkObject(source, game)
                        && game.getContinuousEffects().asThough(this.getId(), AsThoughEffectType.HEXPROOF, null, sourceControllerId, game) == null) {
                    return false;
                }
            }
            return !hasProtectionFrom(source, game);
        }
        return true;
    }

    @Override
    public boolean hasProtectionFrom(MageObject source, Game game) {
        for (ProtectionAbility ability : abilities.getProtectionAbilities()) {
            if (!ability.canTarget(source, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int drawCards(int num, Ability source, Game game) {
        if (num > 0) {
            return game.doAction(source, new MageDrawAction(this, num, null));
        }
        return 0;
    }

    @Override
    public int drawCards(int num, Ability source, Game game, GameEvent event) {
        return game.doAction(source, new MageDrawAction(this, num, event));
    }

    @Override
    public void discardToMax(Game game) {
        if (hand.size() > this.maxHandSize) {
            if (!game.isSimulation()) {
                game.informPlayers(getLogName() + " discards down to "
                        + this.maxHandSize
                        + (this.maxHandSize == 1
                                ? " hand card" : " hand cards"));
            }
            discard(hand.size() - this.maxHandSize, false, false, null, game);
        }
    }

    /**
     * Don't use this in normal card code, it's for more internal use. Always
     * use the [Player].moveCards methods if possible for card movement of card
     * code.
     *
     * @param card
     * @param game
     * @return
     */
    @Override
    public boolean putInHand(Card card, Game game) {
        if (card.isOwnedBy(playerId)) {
            card.setZone(Zone.HAND, game);
            this.hand.add(card);
        } else {
            return game.getPlayer(card.getOwnerId()).putInHand(card, game);
        }
        return true;
    }

    @Override
    public boolean removeFromHand(Card card, Game game) {
        return hand.remove(card.getId());
    }

    @Override
    public boolean removeFromLibrary(Card card, Game game) {
        if (card == null) {
            return false;
        }
        library.remove(card.getId(), game);
        // must return true all the time (some cards can be removed directly from library, see getLibrary().removeFromTop)
        // TODO: replace removeFromTop logic to normal with moveToZone
        return true;
    }

    @Override
    public Card discardOne(boolean random, boolean payForCost, Ability source, Game game) {
        return discard(1, random, payForCost, source, game).getRandom(game);
    }

    @Override
    public Cards discard(int amount, boolean random, boolean payForCost, Ability source, Game game) {
        if (random) {
            return discard(getRandomToDiscard(amount, source, game), payForCost, source, game);
        }
        return discard(amount, amount, payForCost, source, game);
    }

    @Override
    public Cards discard(int minAmount, int maxAmount, boolean payForCost, Ability source, Game game) {
        return discard(getToDiscard(minAmount, maxAmount, source, game), payForCost, source, game);
    }

    @Override
    public Cards discard(Cards cards, boolean payForCost, Ability source, Game game) {
        Cards discardedCards = new CardsImpl();
        if (cards == null) {
            return discardedCards;
        }
        for (Card card : cards.getCards(game)) {
            if (doDiscard(card, source, game, payForCost, false)) {
                discardedCards.add(card);
            }
        }
        if (!discardedCards.isEmpty()) {
            game.fireEvent(new DiscardedCardsEvent(source, playerId, discardedCards.size(), discardedCards));
        }
        return discardedCards;
    }

    @Override
    public boolean discard(Card card, boolean payForCost, Ability source, Game game) {
        return doDiscard(card, source, game, payForCost, true);
    }

    private Cards getToDiscard(int minAmount, int maxAmount, Ability source, Game game) {
        Cards toDiscard = new CardsImpl();
        if (minAmount > maxAmount) {
            return getToDiscard(maxAmount, minAmount, source, game);
        }
        if (maxAmount < 1) {
            return toDiscard;
        }
        if (getHand().size() <= minAmount) {
            toDiscard.addAll(getHand());
            return toDiscard;
        }
        TargetDiscard target = new TargetDiscard(minAmount, maxAmount, StaticFilters.FILTER_CARD, getId());
        choose(Outcome.Discard, target, source != null ? source.getSourceId() : null, game);
        toDiscard.addAll(target.getTargets());
        return toDiscard;
    }

    private Cards getRandomToDiscard(int amount, Ability source, Game game) {
        Cards toDiscard = new CardsImpl();
        Cards hand = getHand().copy();
        for (int i = 0; i < amount; i++) {
            if (hand.isEmpty()) {
                break;
            }
            Card card = hand.getRandom(game);
            hand.remove(card);
            toDiscard.add(card);
        }
        return toDiscard;
    }

    private boolean doDiscard(Card card, Ability source, Game game, boolean payForCost, boolean fireFinalEvent) {
        //20100716 - 701.7
        /* 701.7. Discard #
         701.7a To discard a card, move it from its owners hand to that players graveyard.
         701.7b By default, effects that cause a player to discard a card allow the affected
         player to choose which card to discard. Some effects, however, require a random
         discard or allow another player to choose which card is discarded.
         701.7c If a card is discarded, but an effect causes it to be put into a hidden zone
         instead of into its owners graveyard without being revealed, all values of that
         cards characteristics are considered to be undefined.
         TODO:
         If a card is discarded this way to pay a cost that specifies a characteristic
         about the discarded card, that cost payment is illegal; the game returns to
         the moment before the cost was paid (see rule 717, "Handling Illegal Actions").
         */
        if (card == null) {
            return false;
        }
        GameEvent gameEvent = GameEvent.getEvent(GameEvent.EventType.DISCARD_CARD, card.getId(), source, playerId);
        gameEvent.setFlag(!payForCost); // event from effect (1) or from cost (0)
        if (game.replaceEvent(gameEvent, source)) {
            return false;
        }
        // write info to game log first so game log infos from triggered or replacement effects follow in the game log
        if (!game.isSimulation()) {
            game.informPlayers(getLogName() + " discards " + card.getLogName() + CardUtil.getSourceLogName(game, source));
        }
        /* If a card is discarded while Rest in Peace is on the battlefield, abilities that function
         * when a card is discarded (such as madness) still work, even though that card never reaches
         * a graveyard. In addition, spells or abilities that check the characteristics of a discarded
         * card (such as Chandra Ablaze's first ability) can find that card in exile. */
        card.moveToZone(Zone.GRAVEYARD, source, game, false);
        // So discard is also successful if card is moved to another zone by replacement effect!
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.DISCARDED_CARD, card.getId(), source, playerId));

        if (fireFinalEvent) {
            game.fireEvent(new DiscardedCardsEvent(source, playerId, 1, new CardsImpl(card)));
        }
        return true;
    }

    @Override
    public List<UUID> getAttachments() {
        return attachments;
    }

    @Override
    public boolean addAttachment(UUID permanentId, Ability source, Game game) {
        if (!this.attachments.contains(permanentId)) {
            Permanent aura = game.getPermanent(permanentId);
            if (aura == null) {
                aura = game.getPermanentEntering(permanentId);
            }
            if (aura != null) {
                if (!game.replaceEvent(new EnchantPlayerEvent(playerId, aura, source))) {
                    this.attachments.add(permanentId);
                    aura.attachTo(playerId, source, game);
                    game.fireEvent(new EnchantedPlayerEvent(playerId, aura, source));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean removeAttachment(Permanent attachment, Ability source, Game game) {
        if (this.attachments.contains(attachment.getId())) {
            if (!game.replaceEvent(new UnattachEvent(playerId, attachment.getId(), attachment, source))) {
                this.attachments.remove(attachment.getId());
                attachment.attachTo(null, source, game);
                game.fireEvent(new UnattachedEvent(playerId, attachment.getId(), attachment, source));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeFromBattlefield(Permanent permanent, Ability source, Game game) {
        permanent.removeFromCombat(game, false);
        game.getBattlefield().removePermanent(permanent.getId());
        if (permanent.getAttachedTo() != null) {
            Permanent attachedTo = game.getPermanent(permanent.getAttachedTo());
            if (attachedTo != null) {
                attachedTo.removeAttachment(permanent.getId(), source, game);
            } else {
                Player attachedToPlayer = game.getPlayer(permanent.getAttachedTo());
                if (attachedToPlayer != null) {
                    attachedToPlayer.removeAttachment(permanent, source, game);
                } else {
                    Card attachedToCard = game.getCard(permanent.getAttachedTo());
                    if (attachedToCard != null) {
                        attachedToCard.removeAttachment(permanent.getId(), source, game);
                    }
                }
            }

        }
        if (permanent.getPairedCard() != null) {
            Permanent pairedCard = permanent.getPairedCard().getPermanent(game);
            if (pairedCard != null) {
                pairedCard.clearPairedCard();
            }
        }
        if (permanent.getBandedCards() != null && !permanent.getBandedCards().isEmpty()) {
            for (UUID bandedId : permanent.getBandedCards()) {
                Permanent banded = game.getPermanent(bandedId);
                if (banded != null) {
                    banded.removeBandedCard(permanent.getId());
                }
            }
        }
        return true;
    }

    @Override
    public boolean putInGraveyard(Card card, Game game) {
        if (card.isOwnedBy(playerId)) {
            this.graveyard.add(card);
        } else {
            return game.getPlayer(card.getOwnerId()).putInGraveyard(card, game);
        }
        return true;
    }

    @Override
    public boolean removeFromGraveyard(Card card, Game game) {
        return this.graveyard.remove(card);
    }

    @Override
    public boolean putCardsOnBottomOfLibrary(Card card, Game game, Ability source, boolean anyOrder) {
        return putCardsOnBottomOfLibrary(new CardsImpl(card), game, source, anyOrder);
    }

    @Override
    public boolean putCardsOnBottomOfLibrary(Cards cardsToLibrary, Game game, Ability source, boolean anyOrder) {
        if (!cardsToLibrary.isEmpty()) {
            Cards cards = new CardsImpl(cardsToLibrary); // prevent possible ConcurrentModificationException
            if (!anyOrder) {
                // random order
                List<UUID> ids = new ArrayList<>(cards);
                Collections.shuffle(ids);
                for (UUID id : ids) {
                    moveObjectToLibrary(id, source, game, false, false);
                }
            } else {
                // user defined order
                TargetCard target = new TargetCard(Zone.ALL,
                        new FilterCard("card ORDER to put on the BOTTOM of your library (last one chosen will be bottommost)"));
                target.setRequired(true);
                while (cards.size() > 1 && this.canRespond()
                        && this.choose(Outcome.Neutral, cards, target, game)) {
                    UUID targetObjectId = target.getFirstTarget();
                    if (targetObjectId == null) {
                        break;
                    }
                    cards.remove(targetObjectId);
                    moveObjectToLibrary(targetObjectId, source, game, false, false);
                    target.clearChosen();
                }
                for (UUID c : cards) {
                    moveObjectToLibrary(c, source, game, false, false);
                }
            }
        }
        return true;
    }

    @Override
    public boolean shuffleCardsToLibrary(Cards cards, Game game, Ability source) {
        if (cards.isEmpty()) {
            return true;
        }
        game.informPlayers(getLogName() + " shuffles " + CardUtil.numberToText(cards.size(), "a")
                + " card" + (cards.size() == 1 ? "" : "s")
                + " into their library" + CardUtil.getSourceLogName(game, source));
        boolean status = moveCards(cards, Zone.LIBRARY, source, game);
        shuffleLibrary(source, game);
        return status;
    }

    @Override
    public boolean shuffleCardsToLibrary(Card card, Game game, Ability source) {
        if (card == null) {
            return true;
        }
        return shuffleCardsToLibrary(new CardsImpl(card), game, source);
    }

    @Override
    public boolean putCardOnTopXOfLibrary(Card card, Game game, Ability source, int xFromTheTop, boolean withName) {
        if (card.isOwnedBy(getId())) {
            if (library.size() + 1 < xFromTheTop) {
                putCardsOnBottomOfLibrary(new CardsImpl(card), game, source, true);
            } else {
                if (card.moveToZone(Zone.LIBRARY, source, game, true)
                        && !(card instanceof PermanentToken) && !card.isCopy()) {
                    Card cardInLib = getLibrary().getFromTop(game);
                    if (cardInLib != null && cardInLib.getId().equals(card.getId())) { // check needed because e.g. commander can go to command zone
                        cardInLib = getLibrary().removeFromTop(game);
                        getLibrary().putCardToTopXPos(cardInLib, xFromTheTop, game);
                        game.informPlayers(withName ? cardInLib.getLogName() : "A card"
                                + " is put into "
                                + getLogName()
                                + "'s library "
                                + CardUtil.numberToOrdinalText(xFromTheTop)
                                + " from the top" + CardUtil.getSourceLogName(game, source, cardInLib.getId()));
                    }
                } else {
                    return false;
                }
            }
        } else {
            return game.getPlayer(card.getOwnerId()).putCardOnTopXOfLibrary(card, game, source, xFromTheTop, withName);
        }
        return true;
    }

    /**
     * Can be cards or permanents that go to library
     *
     * @param cardsToLibrary
     * @param game
     * @param source
     * @param anyOrder
     * @return
     */
    @Override
    public boolean putCardsOnTopOfLibrary(Cards cardsToLibrary, Game game, Ability source, boolean anyOrder) {
        if (cardsToLibrary != null && !cardsToLibrary.isEmpty()) {
            Cards cards = new CardsImpl(cardsToLibrary); // prevent possible ConcurrentModificationException
            if (!anyOrder) {
                // random order
                List<UUID> ids = new ArrayList<>(cards);
                Collections.shuffle(ids);
                for (UUID id : ids) {
                    moveObjectToLibrary(id, source, game, true, false);
                }
            } else {
                // user defined order
                TargetCard target = new TargetCard(Zone.ALL,
                        new FilterCard("card ORDER to put on the TOP of your library (last one chosen will be topmost)"));
                target.setRequired(true);
                while (cards.size() > 1
                        && this.canRespond()
                        && this.choose(Outcome.Neutral, cards, target, game)) {
                    UUID targetObjectId = target.getFirstTarget();
                    if (targetObjectId == null) {
                        break;
                    }
                    cards.remove(targetObjectId);
                    moveObjectToLibrary(targetObjectId, source, game, true, false);
                    target.clearChosen();
                }
                for (UUID c : cards) {
                    moveObjectToLibrary(c, source, game, true, false);
                }
            }
        }
        return true;
    }

    @Override
    public boolean putCardsOnTopOfLibrary(Card cardToLibrary, Game game, Ability source, boolean anyOrder) {
        if (cardToLibrary != null) {
            return putCardsOnTopOfLibrary(new CardsImpl(cardToLibrary), game, source, anyOrder);
        }
        return true;
    }

    private boolean moveObjectToLibrary(UUID objectId, Ability source, Game game, boolean toTop, boolean withName) {
        MageObject mageObject = game.getObject(objectId);
        if (mageObject instanceof Spell && mageObject.isCopy()) {
            // Spell copies are not moved as cards, so here the no copy spell has to be selected to move
            // (but because copy and original have the same objectId the wrong sepell can be selected from stack).
            // So let's check if the original spell is on the stack and has to be selected. // TODO: Better handling so each spell could be selected by a unique id
            Spell spellNoCopy = game.getStack().getSpell(source.getSourceId(), false);
            if (spellNoCopy != null) {
                mageObject = spellNoCopy;
            }
        }
        if (mageObject != null) {
            Zone fromZone = game.getState().getZone(objectId);
            if ((mageObject instanceof Permanent)) {
                return this.moveCardToLibraryWithInfo((Permanent) mageObject, source, game, fromZone, toTop, withName);
            } else if (mageObject instanceof Card) {
                return this.moveCardToLibraryWithInfo((Card) mageObject, source, game, fromZone, toTop, withName);
            }
        }
        return false;
    }

    @Override
    public void setCastSourceIdWithAlternateMana(UUID sourceId, ManaCosts<ManaCost> manaCosts, Costs<Cost> costs) {
        // cost must be copied for data consistence between game simulations
        castSourceIdWithAlternateMana.add(sourceId);
        castSourceIdManaCosts.put(sourceId, manaCosts != null ? manaCosts.copy() : null);
        castSourceIdCosts.put(sourceId, costs != null ? costs.copy() : null);
    }

    @Override
    public Set<UUID> getCastSourceIdWithAlternateMana() {
        return castSourceIdWithAlternateMana;
    }

    @Override
    public Map<UUID, Costs<Cost>> getCastSourceIdCosts() {
        return castSourceIdCosts;
    }

    @Override
    public Map<UUID, ManaCosts<ManaCost>> getCastSourceIdManaCosts() {
        return castSourceIdManaCosts;
    }

    @Override
    public void clearCastSourceIdManaCosts() {
        this.castSourceIdCosts.clear();
        this.castSourceIdManaCosts.clear();
        this.castSourceIdWithAlternateMana.clear();
    }

    @Override
    public void setPayManaMode(boolean payManaMode) {
        this.payManaMode = payManaMode;
    }

    @Override
    public boolean isInPayManaMode() {
        return payManaMode;
    }

    @Override
    public boolean playCard(Card card, Game game, boolean noMana, ApprovingObject approvingObject) {
        if (card == null) {
            return false;
        }

        // play without timing and from any zone
        boolean result;
        if (card.isLand(game)) {
            result = playLand(card, game, true);
        } else {
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
            result = cast(this.chooseAbilityForCast(card, game, noMana), game, noMana, approvingObject);
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
        }

        if (!result) {
            game.informPlayer(this, "You can't play " + card.getIdName() + '.');
        }
        return result;
    }

    /**
     * @param originalAbility
     * @param game
     * @param noMana cast it without paying mana costs
     * @param approvingObject which object approved the cast
     * @return
     */
    @Override
    public boolean cast(SpellAbility originalAbility, Game game, boolean noMana, ApprovingObject approvingObject) {
        if (game == null || originalAbility == null) {
            return false;
        }

        // Use ability copy to avoid problems with targets and costs on recast (issue https://github.com/magefree/mage/issues/5189).
        SpellAbility ability = originalAbility.copy();
        ability.setControllerId(getId());
        ability.setSourceObjectZoneChangeCounter(game.getState().getZoneChangeCounter(ability.getSourceId()));

        //20091005 - 601.2a
        if (ability.getSourceId() == null) {
            logger.error("Ability without sourceId turn " + game.getTurnNum() + ". Ability: " + ability.getRule());
            return false;
        }
        Card card = game.getCard(ability.getSourceId());
        if (card != null) {
            Zone fromZone = game.getState().getZone(card.getMainCard().getId());
            GameEvent castEvent = GameEvent.getEvent(GameEvent.EventType.CAST_SPELL,
                    ability.getId(), ability, playerId, approvingObject);
            castEvent.setZone(fromZone);
            if (!game.replaceEvent(castEvent, ability)) {
                int bookmark = game.bookmarkState();
                setStoredBookmark(bookmark); // move global bookmark to current state (if you activated mana before then you can't rollback it)
                card.cast(game, fromZone, ability, playerId);
                Spell spell = game.getStack().getSpell(ability.getId());
                if (spell == null) {
                    logger.error("Got no spell from stack. ability: " + ability.getRule());
                    return false;
                }
                if (card.isCopy()) {
                    spell.setCopy(true, null);
                }
                // Update the zcc to the stack
                ability.setSourceObjectZoneChangeCounter(game.getState().getZoneChangeCounter(ability.getSourceId()));

                // ALTERNATIVE COST from dynamic effects
                // some effects set sourceId to cast without paying mana costs or other costs
                if (getCastSourceIdWithAlternateMana().contains(ability.getSourceId())) {
                    Ability spellAbility = spell.getSpellAbility();
                    ManaCosts alternateCosts = getCastSourceIdManaCosts().get(ability.getSourceId());
                    Costs<Cost> costs = getCastSourceIdCosts().get(ability.getSourceId());
                    if (alternateCosts == null) {
                        noMana = true;
                    } else {
                        spellAbility.getManaCosts().clear();
                        spellAbility.getManaCostsToPay().clear();
                        spellAbility.getManaCosts().add(alternateCosts.copy());
                        spellAbility.getManaCostsToPay().add(alternateCosts.copy());
                    }
                    spellAbility.getCosts().clear();
                    if (costs != null) {
                        spellAbility.getCosts().addAll(costs);
                    }
                }
                clearCastSourceIdManaCosts(); // TODO: test multiple alternative cost for different cards as same time

                castEvent = GameEvent.getEvent(GameEvent.EventType.CAST_SPELL,
                        spell.getSpellAbility().getId(), spell.getSpellAbility(), playerId, approvingObject);
                castEvent.setZone(fromZone);
                game.fireEvent(castEvent);
                if (spell.activate(game, noMana)) {
                    GameEvent castedEvent = GameEvent.getEvent(GameEvent.EventType.SPELL_CAST,
                            spell.getSpellAbility().getId(), spell.getSpellAbility(), playerId, approvingObject);
                    castedEvent.setZone(fromZone);
                    game.fireEvent(castedEvent);
                    if (!game.isSimulation()) {
                        game.informPlayers(getLogName() + spell.getActivatedMessage(game));
                    }
                    game.removeBookmark(bookmark);
                    resetStoredBookmark(game);
                    return true;
                }
                restoreState(bookmark, ability.getRule(), game);
            }
        }
        return false;
    }

    @Override
    public boolean playLand(Card card, Game game, boolean ignoreTiming) {
        // Check for alternate casting possibilities: e.g. land with Morph
        if (card == null) {
            return false;
        }
        ActivatedAbility playLandAbility = null;
        boolean foundAlternative = false;
        for (Ability ability : card.getAbilities(game)) {
            // if cast for noMana no Alternative costs are allowed
            if ((ability instanceof AlternativeSourceCosts)
                    || (ability instanceof OptionalAdditionalSourceCosts)) {
                foundAlternative = true;
            }
            if (ability instanceof PlayLandAbility) {
                playLandAbility = (ActivatedAbility) ability;
            }
        }

        // try alternative cast (face down)
        if (foundAlternative) {
            SpellAbility spellAbility = new SpellAbility(null, "",
                    game.getState().getZone(card.getId()), SpellAbilityType.FACE_DOWN_CREATURE);
            spellAbility.setControllerId(this.getId());
            spellAbility.setSourceId(card.getId());
            if (cast(spellAbility, game, false, null)) {
                return true;
            }
        }

        if (playLandAbility == null) {
            return false;
        }

        //20091005 - 114.2a
        ActivationStatus activationStatus = playLandAbility.canActivate(this.playerId, game);
        if (ignoreTiming) {
            if (!canPlayLand()) {
                return false; // ignore timing does not mean that more lands than normal can be played
            }
        } else {
            if (!activationStatus.canActivate()) {
                return false;
            }
        }

        //20091005 - 305.1
        if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.PLAY_LAND,
                card.getId(), playLandAbility, playerId, activationStatus.getApprovingObject()))) {
            // int bookmark = game.bookmarkState();
            // land events must return original zone (uses for commander watcher)
            Zone cardZoneBefore = game.getState().getZone(card.getId());
            GameEvent landEventBefore = GameEvent.getEvent(GameEvent.EventType.PLAY_LAND,
                    card.getId(), playLandAbility, playerId, activationStatus.getApprovingObject());
            landEventBefore.setZone(cardZoneBefore);
            game.fireEvent(landEventBefore);

            if (moveCards(card, Zone.BATTLEFIELD, playLandAbility, game, false, false, false, null)) {
                landsPlayed++;
                GameEvent landEventAfter = GameEvent.getEvent(GameEvent.EventType.LAND_PLAYED,
                        card.getId(), playLandAbility, playerId, activationStatus.getApprovingObject());
                landEventAfter.setZone(cardZoneBefore);
                game.fireEvent(landEventAfter);

                String playText = getLogName() + " plays " + card.getLogName();
                if (card instanceof ModalDoubleFacesCardHalf) {
                    ModalDoubleFacesCard mdfCard = (ModalDoubleFacesCard) card.getMainCard();
                    playText = getLogName() + " plays " + GameLog.replaceNameByColoredName(card, card.getName(), mdfCard)
                            + " as MDF side of " + GameLog.getColoredObjectIdName(mdfCard);
                }
                game.fireInformEvent(playText);
                // game.removeBookmark(bookmark);
                resetStoredBookmark(game); // prevent undo after playing a land
                return true;
            }
            // putOntoBattlefield returned false if putOntoBattlefield was replaced by replacement effect (e.g. Kjeldoran Outpost).
            // But that would undo the effect completely,
            // what makes no real sense. So it makes no sense to generally do a restoreState here.
            // restoreState(bookmark, card.getName(), game);
        }
        // if the to play the land is replaced (e.g. Kjeldoran Outpost and don't sacrificing a Plains) it's a valid state so returning true here
        return true;
    }

    protected boolean playManaAbility(ActivatedManaAbilityImpl ability, Game game) {
        if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.ACTIVATE_ABILITY,
                ability.getId(), ability, playerId))) {
            int bookmark = game.bookmarkState();
            if (ability.activate(game, false)) {
                if (ability.resolve(game)) {
                    if (ability.isUndoPossible()) {
                        if (storedBookmark == -1 || storedBookmark > bookmark) { // e.g. useful for undo Nykthos, Shrine to Nyx
                            setStoredBookmark(bookmark);
                        }
                    } else {
                        resetStoredBookmark(game);
                    }
                    return true;
                }
            }
            restoreState(bookmark, ability.getRule(), game);
        }
        return false;
    }

    protected boolean playAbility(ActivatedAbility ability, Game game) {
        //20091005 - 602.2a
        if (ability.isUsesStack()) {
            if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.ACTIVATE_ABILITY,
                    ability.getId(), ability, playerId))) {
                int bookmark = game.bookmarkState();
                setStoredBookmark(bookmark); // move global bookmark to current state (if you activated mana before then you can't rollback it)
                ability.newId();
                ability.setControllerId(playerId);
                game.getStack().push(new StackAbility(ability, playerId));
                if (ability.activate(game, false)) {
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.ACTIVATED_ABILITY,
                            ability.getId(), ability, playerId));
                    if (!game.isSimulation()) {
                        game.informPlayers(getLogName() + ability.getGameLogMessage(game));
                    }
                    game.removeBookmark(bookmark);
                    resetStoredBookmark(game);
                    return true;
                }
                restoreState(bookmark, ability.getRule(), game);
            }
        } else {
            int bookmark = game.bookmarkState();
            if (ability.activate(game, false)) {
                ability.resolve(game);
                game.removeBookmark(bookmark);
                resetStoredBookmark(game);
                return true;
            }
            restoreState(bookmark, ability.getRule(), game);
        }
        return false;
    }

    protected boolean specialAction(SpecialAction action, Game game) {
        //20091005 - 114
        if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.TAKE_SPECIAL_ACTION,
                action.getId(), action, getId()))) {
            int bookmark = game.bookmarkState();
            if (action.activate(game, false)) {
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.TAKEN_SPECIAL_ACTION,
                        action.getId(), action, getId()));
                if (!game.isSimulation()) {
                    game.informPlayers(getLogName() + action.getGameLogMessage(game));
                }
                if (action.resolve(game)) {
                    game.removeBookmark(bookmark);
                    resetStoredBookmark(game);
                    return true;
                }
            }
            restoreState(bookmark, action.getRule(), game);
        }
        return false;
    }

    protected boolean specialManaPayment(SpecialAction action, Game game) {
        //20091005 - 114
        if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.TAKE_SPECIAL_MANA_PAYMENT,
                action.getId(), action, getId()))) {
            int bookmark = game.bookmarkState();
            if (action.activate(game, false)) {
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.TAKEN_SPECIAL_MANA_PAYMENT,
                        action.getId(), action, getId()));
                if (!game.isSimulation()) {
                    game.informPlayers(getLogName() + action.getGameLogMessage(game));
                }
                if (action.resolve(game)) {
                    game.removeBookmark(bookmark);
                    resetStoredBookmark(game);
                    return true;
                }
            }
            restoreState(bookmark, action.getRule(), game);
        }
        return false;
    }

    @Override
    public boolean activateAbility(ActivatedAbility ability, Game game) {
        if (ability == null) {
            return false;
        }
        boolean result;
        if (ability instanceof PassAbility) {
            pass(game);
            return true;
        }
        Card card = game.getCard(ability.getSourceId());
        if (ability instanceof PlayLandAsCommanderAbility) {

            // LAND as commander: play land with cost, but without stack
            ActivationStatus activationStatus = ability.canActivate(this.playerId, game);
            if (!activationStatus.canActivate() || !this.canPlayLand()) {
                return false;
            }
            if (card == null) {
                return false;
            }

            // as copy, tries to applie cost effects and pays
            Ability activatingAbility = ability.copy();
            if (activatingAbility.activate(game, false)) {
                result = playLand(card, game, false);
            } else {
                result = false;
            }

        } else if (ability instanceof PlayLandAbility) {

            // LAND as normal card: without cost and stack
            result = playLand(card, game, false);

        } else {

            // ABILITY
            ActivationStatus activationStatus = ability.canActivate(this.playerId, game);
            if (!activationStatus.canActivate()) {
                return false;
            }

            switch (ability.getAbilityType()) {
                case SPECIAL_ACTION:
                    result = specialAction((SpecialAction) ability.copy(), game);
                    break;
                case SPECIAL_MANA_PAYMENT:
                    result = specialManaPayment((SpecialAction) ability.copy(), game);
                    break;
                case MANA:
                    result = playManaAbility((ActivatedManaAbilityImpl) ability.copy(), game);
                    break;
                case SPELL:
                    result = cast((SpellAbility) ability, game, false, activationStatus.getApprovingObject());
                    break;
                default:
                    result = playAbility(ability.copy(), game);
                    break;
            }
        }

        //if player has taken an action then reset all player passed flags
        justActivatedType = null;
        if (result) {
            if (isHuman()
                    && (ability.getAbilityType() == AbilityType.SPELL
                    || ability.getAbilityType() == AbilityType.ACTIVATED)) {
                if (ability.isUsesStack()) { // if the ability does not use the stack (e.g. Suspend) auto pass would go to next phase unintended
                    setJustActivatedType(ability.getAbilityType());
                }
            }
            game.getPlayers().resetPassed();
        }
        return result;
    }

    @Override
    public boolean triggerAbility(TriggeredAbility triggeredAbility, Game game) {
        if (triggeredAbility == null) {
            logger.warn("Null source in triggerAbility method");
            throw new IllegalArgumentException("source TriggeredAbility  must not be null");
        }
        //20091005 - 603.3c, 603.3d
        int bookmark = game.bookmarkState();
        TriggeredAbility ability = triggeredAbility.copy();
        MageObject sourceObject = ability.getSourceObject(game);
        if (sourceObject != null) {
            sourceObject.adjustTargets(ability, game);
        }
        UUID triggerId = null;
        if (ability.canChooseTarget(game, playerId)) {
            if (ability.isUsesStack()) {
                game.getStack().push(new StackAbility(ability, playerId));
            }
            if (ability.activate(game, false)) {
                if ((ability.isUsesStack()
                        || ability.getRuleVisible())
                        && !game.isSimulation()) {
                    game.informPlayers(getLogName() + " - " + ability.getGameLogMessage(game));
                }
                if (!ability.isUsesStack()) {
                    ability.resolve(game);
                } else {
                    game.fireEvent(new GameEvent(
                            GameEvent.EventType.TRIGGERED_ABILITY,
                            ability.getId(), ability, ability.getControllerId()
                    ));
                    triggerId = ability.getId();
                }
                game.removeBookmark(bookmark);
                return true;
            }
        }
        restoreState(bookmark, triggeredAbility.getRule(), game); // why restore is needed here? (to remove the triggered ability from the stack because of no possible targets)
        GameEvent event = new GameEvent(
                GameEvent.EventType.ABILITY_TRIGGERED,
                triggerId, ability, ability.getControllerId()
        );
        game.getState().setValue(event.getId().toString(), ability.getTriggerEvent());
        game.fireEvent(event);
        return false;
    }

    /**
     * Return spells for possible cast Uses in GUI to show only playable spells
     * for choosing from the card (example: effect allow to cast card and player
     * must choose the spell ability)
     *
     * @param game
     * @param playerId
     * @param object
     * @param zone
     * @param noMana
     * @return
     */
    public static LinkedHashMap<UUID, ActivatedAbility> getCastableSpellAbilities(Game game, UUID playerId, MageObject object, Zone zone, boolean noMana) {
        // it uses simple check from spellCanBeActivatedRegularlyNow
        // reason: no approved info here (e.g. forced to choose spell ability from cast card)
        LinkedHashMap<UUID, ActivatedAbility> useable = new LinkedHashMap<>();
        Abilities<Ability> allAbilities;
        if (object instanceof Card) {
            allAbilities = ((Card) object).getAbilities(game);
        } else {
            allAbilities = object.getAbilities();
        }

        for (Ability ability : allAbilities) {
            if (ability instanceof SpellAbility) {
                SpellAbility spellAbility = (SpellAbility) ability;

                switch (spellAbility.getSpellAbilityType()) {
                    case BASE_ALTERNATE:
                        // rules:
                        // If you cast a spell “without paying its mana cost,” you can’t choose to cast it for
                        // any alternative costs. You can, however, pay additional costs, such as kicker costs.
                        // If the card has any mandatory additional costs, those must be paid to cast the spell.
                        // (2021-02-05)
                        if (!noMana) {
                            if (spellAbility.spellCanBeActivatedRegularlyNow(playerId, game)) {
                                useable.put(spellAbility.getId(), spellAbility);  // example: Chandra, Torch of Defiance +1 loyal ability
                            }
                            return useable;
                        }
                        break;
                    case SPLIT_FUSED:
                        // rules:
                        // If you cast a split card with fuse from your hand without paying its mana cost,
                        // you can choose to use its fuse ability and cast both halves without paying their mana costs.
                        if (zone == Zone.HAND) {
                            if (spellAbility.canChooseTarget(game, playerId)) {
                                useable.put(spellAbility.getId(), spellAbility);
                            }
                        }
                    case SPLIT:
                        if (((SplitCard) object).getLeftHalfCard().getSpellAbility().canChooseTarget(game, playerId)) {
                            useable.put(((SplitCard) object).getLeftHalfCard().getSpellAbility().getId(),
                                    ((SplitCard) object).getLeftHalfCard().getSpellAbility());
                        }
                        if (((SplitCard) object).getRightHalfCard().getSpellAbility().canChooseTarget(game, playerId)) {
                            useable.put(((SplitCard) object).getRightHalfCard().getSpellAbility().getId(),
                                    ((SplitCard) object).getRightHalfCard().getSpellAbility());
                        }
                        return useable;
                    case SPLIT_AFTERMATH:
                        if (zone == Zone.GRAVEYARD) {
                            if (((SplitCard) object).getRightHalfCard().getSpellAbility().canChooseTarget(game, playerId)) {
                                useable.put(((SplitCard) object).getRightHalfCard().getSpellAbility().getId(),
                                        ((SplitCard) object).getRightHalfCard().getSpellAbility());
                            }
                        } else {
                            if (((SplitCard) object).getLeftHalfCard().getSpellAbility().canChooseTarget(game, playerId)) {
                                useable.put(((SplitCard) object).getLeftHalfCard().getSpellAbility().getId(),
                                        ((SplitCard) object).getLeftHalfCard().getSpellAbility());
                            }
                        }
                        return useable;
                    default:
                        if (spellAbility.spellCanBeActivatedRegularlyNow(playerId, game)) {
                            useable.put(spellAbility.getId(), spellAbility);
                        }
                }
            }
        }
        return useable;
    }

    @Override
    public LinkedHashMap<UUID, ActivatedAbility> getPlayableActivatedAbilities(MageObject object, Zone zone, Game game) {
        LinkedHashMap<UUID, ActivatedAbility> useable = new LinkedHashMap<>();
        // stack abilities - can't activate anything
        // spell ability - can activate additional abilities (example: "Lightning Storm")
        if (object instanceof StackAbility || object == null) {
            return useable;
        }
        boolean previousState = game.inCheckPlayableState();
        game.setCheckPlayableState(true);
        try {
            // collect and filter playable activated abilities
            // GUI: user clicks on card, but it must activate ability from ANY card's parts (main, left, right)
            Set<UUID> needIds = CardUtil.getObjectParts(object);

            // workaround to find all abilities first and filter it for one object
            List<ActivatedAbility> allPlayable = getPlayable(game, true, zone, false);
            for (ActivatedAbility ability : allPlayable) {
                if (needIds.contains(ability.getSourceId())) {
                    useable.putIfAbsent(ability.getId(), ability);
                }
            }
        } finally {
            game.setCheckPlayableState(previousState);
        }
        return useable;
    }

    protected LinkedHashMap<UUID, ActivatedManaAbilityImpl> getUseableManaAbilities(MageObject object, Zone zone, Game game) {
        LinkedHashMap<UUID, ActivatedManaAbilityImpl> useable = new LinkedHashMap<>();
        boolean canUse = !(object instanceof Permanent) || ((Permanent) object).canUseActivatedAbilities(game);
        for (ActivatedManaAbilityImpl ability : object.getAbilities().getActivatedManaAbilities(zone)) {
            if (canUse || ability.getAbilityType() == AbilityType.SPECIAL_ACTION) {
                if (ability.canActivate(playerId, game).canActivate()) {
                    useable.put(ability.getId(), ability);
                }
            }
        }
        return useable;
    }

    @Override
    public int getLandsPlayed() {
        return landsPlayed;
    }

    @Override
    public boolean canPlayLand() {
        //20091005 - 114.2a
        return landsPlayed < landsPerTurn;
    }

    protected boolean isActivePlayer(Game game) {
        return game.isActivePlayer(this.playerId);
    }

    @Override
    public void shuffleLibrary(Ability source, Game game) {
        if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.SHUFFLE_LIBRARY, playerId, source, playerId))) {
            this.library.shuffle();
            if (!game.isSimulation()) {
                game.informPlayers(getLogName() + "'s library is shuffled" + CardUtil.getSourceLogName(game, source));
            }
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.LIBRARY_SHUFFLED, playerId, source, playerId));
        }
    }

    @Override
    public void revealCards(Ability source, Cards cards, Game game) {
        revealCards(source, null, cards, game, true);
    }

    @Override
    public void revealCards(String titleSuffix, Cards cards, Game game) {
        revealCards(titleSuffix, cards, game, true);
    }

    @Override
    public void revealCards(String titleSuffix, Cards cards, Game game, boolean postToLog) {
        revealCards(null, titleSuffix, cards, game, postToLog);
    }

    @Override
    public void revealCards(Ability source, String titleSuffix, Cards cards, Game game) {
        revealCards(source, titleSuffix, cards, game, true);
    }

    @Override
    public void revealCards(Ability source, String titleSuffix, Cards cards, Game game, boolean postToLog) {
        if (cards == null || cards.isEmpty()) {
            return;
        }
        if (postToLog) {
            game.getState().getRevealed().add(CardUtil.createObjectRealtedWindowTitle(source, game, titleSuffix), cards);
        } else {
            game.getState().getRevealed().update(CardUtil.createObjectRealtedWindowTitle(source, game, titleSuffix), cards);
        }
        if (postToLog && !game.isSimulation()) {
            StringBuilder sb = new StringBuilder(getLogName()).append(" reveals ");
            int current = 0, last = cards.size();
            for (Card card : cards.getCards(game)) {
                current++;
                sb.append(GameLog.getColoredObjectName(card));
                if (current < last) {
                    sb.append(", ");
                }
            }
            sb.append(CardUtil.getSourceLogName(game, source));
            game.informPlayers(sb.toString());
        }
    }

    @Override
    public void lookAtCards(String titleSuffix, Card card, Game game) {
        game.getState().getLookedAt(this.playerId).add(titleSuffix, card);
        game.fireUpdatePlayersEvent();
    }

    @Override
    public void lookAtCards(String titleSuffix, Cards cards, Game game) {
        this.lookAtCards(null, titleSuffix, cards, game);
    }

    @Override
    public void lookAtCards(Ability source, String titleSuffix, Cards cards, Game game) {
        game.getState().getLookedAt(this.playerId).add(CardUtil.createObjectRealtedWindowTitle(source, game, titleSuffix), cards);
        game.fireUpdatePlayersEvent();
    }

    @Override
    public void phasing(Game game) {
        //20091005 - 502.1
        List<Permanent> phasedOut = game.getBattlefield().getPhasedOut(game, playerId);
        for (Permanent permanent : game.getBattlefield().getPhasedIn(game, playerId)) {
            // 502.15i When a permanent phases out, any local enchantments or Equipment
            // attached to that permanent phase out at the same time. This alternate way of
            // phasing out is known as phasing out "indirectly." An enchantment or Equipment
            // that phased out indirectly won't phase in by itself, but instead phases in
            // along with the card it's attached to.
            Permanent attachedTo = game.getPermanent(permanent.getAttachedTo());
            if (!(attachedTo != null && attachedTo.isControlledBy(this.getId()))) {
                permanent.phaseOut(game, false);
            }
        }
        for (Permanent permanent : phasedOut) {
            if (!permanent.isPhasedOutIndirectly()) {
                permanent.phaseIn(game);
            }
        }
    }

    @Override
    public void untap(Game game) {
        // create list of all "notMoreThan" effects to track which one are consumed
        Map<Entry<RestrictionUntapNotMoreThanEffect, Set<Ability>>, Integer> notMoreThanEffectsUsage = new HashMap<>();
        for (Entry<RestrictionUntapNotMoreThanEffect, Set<Ability>> restrictionEffect
                : game.getContinuousEffects().getApplicableRestrictionUntapNotMoreThanEffects(this, game).entrySet()) {
            notMoreThanEffectsUsage.put(restrictionEffect, restrictionEffect.getKey().getNumber());
        }

        if (!notMoreThanEffectsUsage.isEmpty()) {
            // create list of all permanents that can be untapped generally
            List<Permanent> canBeUntapped = new ArrayList<>();
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
                boolean untap = true;
                for (RestrictionEffect effect : game.getContinuousEffects().getApplicableRestrictionEffects(permanent, game).keySet()) {
                    untap &= effect.canBeUntapped(permanent, null, game, true);
                }
                if (untap) {
                    canBeUntapped.add(permanent);
                }
            }
            // selected permanents to untap
            List<Permanent> selectedToUntap = new ArrayList<>();

            // player can cancel the selection of an effect to use a preferred order of restriction effects
            boolean playerCanceledSelection;
            do {
                playerCanceledSelection = false;
                // select permanents to untap to consume the "notMoreThan" effects
                for (Map.Entry<Entry<RestrictionUntapNotMoreThanEffect, Set<Ability>>, Integer> handledEntry : notMoreThanEffectsUsage.entrySet()) {
                    // select a permanent to untap for this entry
                    int numberToUntap = handledEntry.getValue();
                    if (numberToUntap > 0) {

                        List<Permanent> leftForUntap = getPermanentsThatCanBeUntapped(game,
                                canBeUntapped,
                                handledEntry.getKey().getKey(),
                                notMoreThanEffectsUsage);

                        FilterControlledPermanent filter = handledEntry.getKey().getKey().getFilter().copy();
                        String message = filter.getMessage();
                        // omit already from other untap effects selected permanents
                        for (Permanent permanent : selectedToUntap) {
                            filter.add(Predicates.not(new PermanentIdPredicate(permanent.getId())));
                        }
                        // while targets left and there is still allowed to untap
                        while (canRespond() && !leftForUntap.isEmpty() && numberToUntap > 0) {
                            // player has to select the permanent they want to untap for this restriction
                            Ability ability = handledEntry.getKey().getValue().iterator().next();
                            if (ability != null) {
                                StringBuilder sb = new StringBuilder(message).append(" to untap").append(" (").append(Math.min(leftForUntap.size(),
                                        numberToUntap)).append(" in total");
                                MageObject effectSource = game.getObject(ability.getSourceId());
                                if (effectSource != null) {
                                    sb.append(" from ").append(effectSource.getLogName());
                                }
                                sb.append(')');
                                filter.setMessage(sb.toString());
                                Target target = new TargetPermanent(1, 1, filter, true);
                                if (!this.chooseTarget(Outcome.Untap, target, ability, game)) {
                                    // player canceled, go on with the next effect (if no other effect available, this effect will be active again)
                                    playerCanceledSelection = true;
                                    break;
                                }
                                Permanent selectedPermanent = game.getPermanent(target.getFirstTarget());
                                if (leftForUntap.contains(selectedPermanent)) {
                                    selectedToUntap.add(selectedPermanent);
                                    numberToUntap--;
                                    // don't allow to select same permanent twice
                                    filter.add(Predicates.not(new PermanentIdPredicate(selectedPermanent.getId())));
                                    // reduce available untap numbers from other "UntapNotMoreThan" effects if selected permanent applies to their filter too
                                    for (Entry<Entry<RestrictionUntapNotMoreThanEffect, Set<Ability>>, Integer> notMoreThanEffect : notMoreThanEffectsUsage.entrySet()) {
                                        if (notMoreThanEffect.getValue() > 0
                                                && notMoreThanEffect.getKey().getKey().getFilter().match(selectedPermanent, game)) {
                                            notMoreThanEffect.setValue(notMoreThanEffect.getValue() - 1);
                                        }
                                    }
                                    // update the left for untap list
                                    leftForUntap = getPermanentsThatCanBeUntapped(game,
                                            canBeUntapped,
                                            handledEntry.getKey().getKey(),
                                            notMoreThanEffectsUsage);
                                    // remove already selected permanents
                                    for (Permanent permanent : selectedToUntap) {
                                        leftForUntap.remove(permanent);
                                    }

                                } else {
                                    // player selected an permanent that is restricted by another effect, disallow it (so AI can select another one)
                                    filter.add(Predicates.not(new PermanentIdPredicate(selectedPermanent.getId())));
                                    if (this.isHuman() && !game.isSimulation()) {
                                        game.informPlayer(this, "This permanent can't be untapped because of other restricting effect.");
                                    }
                                }
                            }
                        }
                    }
                }

            } while (canRespond() && playerCanceledSelection);

            if (!game.isSimulation()) {
                // show in log which permanents were selected to untap
                for (Permanent permanent : selectedToUntap) {
                    game.informPlayers(this.getLogName() + " untapped " + permanent.getLogName());
                }
            }
            // untap if permanent is not concerned by notMoreThan effects or is included in the selectedToUntapList
            for (Permanent permanent : canBeUntapped) {
                boolean doUntap = true;
                if (!selectedToUntap.contains(permanent)) {
                    // if the permanent is covered by one of the restriction effects, don't untap it
                    for (Entry<Entry<RestrictionUntapNotMoreThanEffect, Set<Ability>>, Integer> notMoreThanEffect : notMoreThanEffectsUsage.entrySet()) {
                        if (notMoreThanEffect.getKey().getKey().getFilter().match(permanent, game)) {
                            doUntap = false;
                            break;
                        }
                    }
                }
                if (permanent != null && doUntap) {
                    permanent.untap(game);
                }

            }

        } else {
            //20091005 - 502.2

            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
                boolean untap = true;
                for (RestrictionEffect effect : game.getContinuousEffects().getApplicableRestrictionEffects(permanent, game).keySet()) {
                    untap &= effect.canBeUntapped(permanent, null, game, true);
                }
                if (untap) {
                    permanent.untap(game);
                }
            }
        }
    }

    private List<Permanent> getPermanentsThatCanBeUntapped(Game game, List<Permanent> canBeUntapped, RestrictionUntapNotMoreThanEffect handledEffect, Map<Entry<RestrictionUntapNotMoreThanEffect, Set<Ability>>, Integer> notMoreThanEffectsUsage) {
        List<Permanent> leftForUntap = new ArrayList<>();
        // select permanents that can still be untapped
        for (Permanent permanent : canBeUntapped) {
            if (handledEffect.getFilter().match(permanent, game)) { // matches the restricted permanents of handled entry
                boolean canBeSelected = true;
                // check if the permanent is restricted by another restriction that has left no permanent
                for (Entry<Entry<RestrictionUntapNotMoreThanEffect, Set<Ability>>, Integer> notMoreThanEffect : notMoreThanEffectsUsage.entrySet()) {
                    if (notMoreThanEffect.getKey().getKey().getFilter().match(permanent, game)
                            && notMoreThanEffect.getValue() == 0) {
                        canBeSelected = false;
                        break;
                    }
                }
                if (canBeSelected) {
                    leftForUntap.add(permanent);
                }
            }
        }
        return leftForUntap;
    }

    @Override
    public UUID getId() {
        return playerId;
    }

    @Override
    public Cards getHand() {
        return hand;
    }

    @Override
    public Graveyard getGraveyard() {
        return graveyard;
    }

    @Override
    public ManaPool getManaPool() {
        return this.manaPool;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLogName() {
        return GameLog.getColoredPlayerName(name);
    }

    @Override
    public boolean isHuman() {
        return human;
    }

    @Override
    public Library getLibrary() {
        return library;
    }

    @Override
    public Cards getSideboard() {
        return sideboard;
    }

    @Override
    public int getLife() {
        return life;
    }

    @Override
    public void initLife(int life) {
        this.life = life;
    }

    @Override
    public void setLife(int life, Game game, Ability source) {
        // rule 118.5
        if (life > this.life) {
            gainLife(life - this.life, game, source);
        } else if (life < this.life) {
            loseLife(this.life - life, game, source, false);
        }
    }

    @Override
    public void setLifeTotalCanChange(boolean lifeTotalCanChange) {
        this.canGainLife = lifeTotalCanChange;
        this.canLoseLife = lifeTotalCanChange;
    }

    @Override
    public boolean isLifeTotalCanChange() {
        return canGainLife || canLoseLife;
    }

    @Override
    public List<AlternativeSourceCosts> getAlternativeSourceCosts() {
        return alternativeSourceCosts;
    }

    @Override
    public boolean isCanLoseLife() {
        return canLoseLife;
    }

    @Override
    public void setCanLoseLife(boolean canLoseLife) {
        this.canLoseLife = canLoseLife;
    }

    @Override
    public int loseLife(int amount, Game game, Ability source, boolean atCombat, UUID attackerId) {
        if (!canLoseLife || !this.isInGame()) {
            return 0;
        }
        GameEvent event = new GameEvent(GameEvent.EventType.LOSE_LIFE,
                playerId, source, playerId, amount, atCombat);
        if (!game.replaceEvent(event)) {
            this.life = CardUtil.overflowDec(this.life, event.getAmount());
            if (!game.isSimulation()) {
                UUID needId = attackerId;
                if (needId == null) {
                    needId = source == null ? null : source.getSourceId();
                }
                game.informPlayers(this.getLogName() + " loses " + event.getAmount() + " life"
                        + (atCombat ? " at combat" : "") + CardUtil.getSourceLogName(game, " from ", needId, "", ""));
            }
            if (amount > 0) {
                game.fireEvent(new GameEvent(GameEvent.EventType.LOST_LIFE,
                        playerId, source, playerId, amount, atCombat));
            }
            return amount;
        }
        return 0;
    }

    @Override
    public int loseLife(int amount, Game game, Ability source, boolean atCombat) {
        return loseLife(amount, game, source, atCombat, null);
    }

    @Override
    public boolean isCanGainLife() {
        return canGainLife;
    }

    @Override
    public void setCanGainLife(boolean canGainLife) {
        this.canGainLife = canGainLife;
    }

    @Override
    public int gainLife(int amount, Game game, Ability source) {
        if (!canGainLife || amount <= 0) {
            return 0;
        }
        GameEvent event = new GameEvent(GameEvent.EventType.GAIN_LIFE,
                playerId, source, playerId, amount, false);
        if (!game.replaceEvent(event)) {
            // TODO: lock life at Integer.MAX_VALUE if reached, until it's set to a different amount
            // (https://magic.wizards.com/en/articles/archive/news/unstable-faqawaslfaqpaftidawabiajtbt-2017-12-06 - "infinite" life total stays infinite no matter how much is gained or lost)
            // this.life += event.getAmount();
            this.life = CardUtil.overflowInc(this.life, event.getAmount());
            if (!game.isSimulation()) {
                game.informPlayers(this.getLogName() + " gains " + event.getAmount() + " life" + CardUtil.getSourceLogName(game, source));
            }
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.GAINED_LIFE,
                    playerId, source, playerId, event.getAmount()));
            return event.getAmount();
        }
        return 0;
    }

    @Override
    public void exchangeLife(Player player, Ability source, Game game) {
        int lifePlayer1 = getLife();
        int lifePlayer2 = player.getLife();
        if ((lifePlayer1 != lifePlayer2 && this.isLifeTotalCanChange() && player.isLifeTotalCanChange())
                && (lifePlayer1 >= lifePlayer2 || (this.isCanGainLife() && player.isCanLoseLife()))
                && (lifePlayer1 <= lifePlayer2 || (this.isCanLoseLife() && player.isCanGainLife()))) {
            this.setLife(lifePlayer2, game, source);
            player.setLife(lifePlayer1, game, source);
        }
    }

    @Override
    public int damage(int damage, UUID attackerId, Ability source, Game game) {
        return doDamage(damage, attackerId, source, game, false, true, null);
    }

    @Override
    public int damage(int damage, UUID attackerId, Ability source, Game game, boolean combatDamage, boolean preventable) {
        return doDamage(damage, attackerId, source, game, combatDamage, preventable, null);
    }

    @Override
    public int damage(int damage, UUID attackerId, Ability source, Game game, boolean combatDamage, boolean preventable, List<UUID> appliedEffects) {
        return doDamage(damage, attackerId, source, game, combatDamage, preventable, appliedEffects);
    }

    private int doDamage(int damage, UUID attackerId, Ability source, Game game, boolean combatDamage, boolean preventable, List<UUID> appliedEffects) {
        if (!this.isInGame()) {
            return 0;
        }

        if (damage < 1) {
            return 0;
        }
        if (!canDamage(game.getObject(attackerId), game)) {
            MageObject sourceObject = game.getObject(attackerId);
            game.informPlayers(damage + " damage "
                    + (sourceObject == null ? "" : "from " + sourceObject.getLogName())
                    + " to " + getLogName()
                    + (damage > 1 ? " were" : "was") + " prevented because of protection");
            return 0;
        }
        DamageEvent event = new DamagePlayerEvent(playerId, attackerId, playerId, damage, preventable, combatDamage);
        event.setAppliedEffects(appliedEffects);
        if (game.replaceEvent(event)) {
            return 0;
        }
        int actualDamage = event.getAmount();
        if (actualDamage < 1) {
            return 0;
        }
        UUID sourceControllerId = null;
        Abilities sourceAbilities = null;
        MageObject attacker = game.getPermanentOrLKIBattlefield(attackerId);
        if (attacker == null) {
            StackObject stackObject = game.getStack().getStackObject(attackerId);
            if (stackObject != null) {
                attacker = stackObject.getStackAbility().getSourceObject(game);
            } else {
                attacker = game.getObject(attackerId);
            }
            if (attacker instanceof Spell) {
                sourceAbilities = ((Spell) attacker).getAbilities(game);
                sourceControllerId = ((Spell) attacker).getControllerId();
            } else if (attacker instanceof Card) {
                sourceAbilities = ((Card) attacker).getAbilities(game);
                sourceControllerId = ((Card) attacker).getOwnerId();
            } else if (attacker instanceof CommandObject) {
                sourceControllerId = ((CommandObject) attacker).getControllerId();
                sourceAbilities = attacker.getAbilities();
            }
        } else {
            sourceAbilities = ((Permanent) attacker).getAbilities(game);
            sourceControllerId = ((Permanent) attacker).getControllerId();
        }
        if (event.isAsThoughInfect() || (sourceAbilities != null && sourceAbilities.containsKey(InfectAbility.getInstance().getId()))) {
            addCounters(CounterType.POISON.createInstance(actualDamage), sourceControllerId, source, game);
        } else {
            GameEvent damageToLifeLossEvent = new GameEvent(GameEvent.EventType.DAMAGE_CAUSES_LIFE_LOSS,
                    playerId, source, playerId, actualDamage, combatDamage);
            if (!game.replaceEvent(damageToLifeLossEvent)) {
                this.loseLife(damageToLifeLossEvent.getAmount(), game, source, combatDamage, attackerId);
            }
        }
        if (sourceAbilities != null && sourceAbilities.containsKey(LifelinkAbility.getInstance().getId())) {
            if (combatDamage) {
                game.getPermanent(attackerId).markLifelink(actualDamage);
            } else {
                Player player = game.getPlayer(sourceControllerId);
                player.gainLife(actualDamage, game, source);
            }
        }
        // Unstable ability - Earl of Squirrel
        if (sourceAbilities != null && sourceAbilities.containsKey(SquirrellinkAbility.getInstance().getId())) {
            Player player = game.getPlayer(sourceControllerId);
            new SquirrelToken().putOntoBattlefield(actualDamage, game, source, player.getId());
        }
        DamagedEvent damagedEvent = new DamagedPlayerEvent(playerId, attackerId, playerId, actualDamage, combatDamage);
        game.fireEvent(damagedEvent);
        game.getState().addSimultaneousDamage(damagedEvent, game);
        return actualDamage;
    }

    @Override
    public boolean addCounters(Counter counter, UUID playerAddingCounters, Ability source, Game game) {
        boolean returnCode = true;
        GameEvent addingAllEvent = GameEvent.getEvent(
                GameEvent.EventType.ADD_COUNTERS, playerId, source,
                playerAddingCounters, counter.getName(), counter.getCount()
        );
        if (!game.replaceEvent(addingAllEvent)) {
            int amount = addingAllEvent.getAmount();
            int finalAmount = amount;
            boolean isEffectFlag = addingAllEvent.getFlag();
            for (int i = 0; i < amount; i++) {
                Counter eventCounter = counter.copy();
                eventCounter.remove(eventCounter.getCount() - 1);
                GameEvent addingOneEvent = GameEvent.getEvent(
                        GameEvent.EventType.ADD_COUNTER, playerId, source,
                        playerAddingCounters, counter.getName(), 1
                );
                addingOneEvent.setFlag(isEffectFlag);
                if (!game.replaceEvent(addingOneEvent)) {
                    getCounters().addCounter(eventCounter);
                    GameEvent addedOneEvent = GameEvent.getEvent(
                            GameEvent.EventType.COUNTER_ADDED, playerId, source,
                            playerAddingCounters, counter.getName(), 1
                    );
                    addedOneEvent.setFlag(addingOneEvent.getFlag());
                    game.fireEvent(addedOneEvent);
                } else {
                    finalAmount--;
                    returnCode = false;
                }
            }
            if (finalAmount > 0) {
                GameEvent addedAllEvent = GameEvent.getEvent(
                        GameEvent.EventType.COUNTERS_ADDED, playerId, source,
                        playerAddingCounters, counter.getName(), amount
                );
                addedAllEvent.setFlag(addingAllEvent.getFlag());
                game.fireEvent(addedAllEvent);
            }
        } else {
            returnCode = false;
        }
        return returnCode;
    }

    @Override
    public void removeCounters(String name, int amount, Ability source, Game game) {
        int finalAmount = 0;
        for (int i = 0; i < amount; i++) {
            if (!counters.removeCounter(name, 1)) {
                break;
            }
            GameEvent event = GameEvent.getEvent(GameEvent.EventType.COUNTER_REMOVED,
                    getId(), source, (source == null ? null : source.getControllerId()));
            event.setData(name);
            event.setAmount(1);
            game.fireEvent(event);
            finalAmount++;
        }
        GameEvent event = GameEvent.getEvent(GameEvent.EventType.COUNTERS_REMOVED,
                getId(), source, (source == null ? null : source.getControllerId()));
        event.setData(name);
        event.setAmount(finalAmount);
        game.fireEvent(event);
    }

    protected boolean canDamage(MageObject source, Game game) {
        for (ProtectionAbility ability : abilities.getProtectionAbilities()) {
            if (!ability.canTarget(source, game)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Abilities<Ability> getAbilities() {
        return this.abilities;
    }

    @Override
    public void addAbility(Ability ability) {
        ability.setSourceId(playerId);
        this.abilities.add(ability);
    }

    @Override
    public int getLandsPerTurn() {
        return this.landsPerTurn;
    }

    @Override
    public void setLandsPerTurn(int landsPerTurn) {
        this.landsPerTurn = landsPerTurn;
    }

    @Override
    public int getLoyaltyUsePerTurn() {
        return this.loyaltyUsePerTurn;
    }

    @Override
    public void setLoyaltyUsePerTurn(int loyaltyUsePerTurn) {
        this.loyaltyUsePerTurn = loyaltyUsePerTurn;
    }

    @Override
    public int getMaxHandSize() {
        return maxHandSize;
    }

    @Override
    public void setMaxHandSize(int maxHandSize) {
        this.maxHandSize = maxHandSize;
    }

    @Override
    public void setMaxAttackedBy(int maxAttackedBy) {
        this.maxAttackedBy = maxAttackedBy;
    }

    @Override
    public int getMaxAttackedBy() {
        return maxAttackedBy;
    }

    @Override
    public void setResponseString(String responseString) {
    }

    @Override
    public void setResponseManaType(UUID manaTypePlayerId, ManaType responseManaType) {
    }

    @Override
    public void setResponseUUID(UUID responseUUID) {
    }

    @Override
    public void setResponseBoolean(Boolean responseBoolean) {
    }

    @Override
    public void setResponseInteger(Integer responseInteger) {
    }

    @Override
    public boolean isPassed() {
        return passed;
    }

    @Override
    public void pass(Game game) {
        this.passed = true;
        resetStoredBookmark(game);
    }

    @Override
    public void resetPassed() {
        this.passed = this.loses || this.hasLeft();
    }

    @Override
    public void resetPlayerPassedActions() {
        this.passed = false;
        this.passedTurn = false;
        this.passedTurnSkipStack = false;
        this.passedUntilEndOfTurn = false;
        this.passedUntilNextMain = false;
        this.passedUntilStackResolved = false;
        this.dateLastAddedToStack = null;
        this.passedUntilEndStepBeforeMyTurn = false;
        this.skippedAtLeastOnce = false;
        this.passedAllTurns = false;
        this.justActivatedType = null;
    }

    @Override
    public void quit(Game game) {
        quit = true;
        this.concede(game);
        logger.debug(getName() + " quits the match.");
        game.informPlayers(getLogName() + " quits the match.");
    }

    @Override
    public void timerTimeout(Game game) {
        quit = true;
        timerTimeout = true;
        this.concede(game);
        game.informPlayers(getLogName() + " has run out of time, losing the match.");
    }

    @Override
    public void idleTimeout(Game game) {
        quit = true;
        idleTimeout = true;
        this.concede(game);
        game.informPlayers(getLogName() + " was idle for too long, losing the Match.");
    }

    @Override
    public void concede(Game game) {
        game.setConcedingPlayer(playerId);
        lost(game);
//        this.left = true;
    }

    @Override
    public void sendPlayerAction(PlayerAction playerAction, Game game, Object data) {
        switch (playerAction) {
            case PASS_PRIORITY_UNTIL_MY_NEXT_TURN: // F9
                resetPlayerPassedActions();
                passedAllTurns = true;
                this.skip();
                break;
            case PASS_PRIORITY_UNTIL_TURN_END_STEP: // F5
                resetPlayerPassedActions();
                passedUntilEndOfTurn = true;
                skippedAtLeastOnce = PhaseStep.END_TURN != game.getTurn().getStepType();
                this.skip();
                break;
            case PASS_PRIORITY_UNTIL_NEXT_TURN: // F4
                resetPlayerPassedActions();
                passedTurn = true;
                this.skip();
                break;
            case PASS_PRIORITY_UNTIL_NEXT_TURN_SKIP_STACK: // F6
                resetPlayerPassedActions();
                passedTurnSkipStack = true;
                this.skip();
                break;
            case PASS_PRIORITY_UNTIL_NEXT_MAIN_PHASE: //F7
                resetPlayerPassedActions();
                passedUntilNextMain = true;
                skippedAtLeastOnce = !(game.getTurn().getStepType() == PhaseStep.POSTCOMBAT_MAIN
                        || game.getTurn().getStepType() == PhaseStep.PRECOMBAT_MAIN);
                this.skip();
                break;
            case PASS_PRIORITY_UNTIL_STACK_RESOLVED: // Default F10 - Skips until the current stack is resolved
                if (!game.getStack().isEmpty()) { // If stack is empty do nothing
                    resetPlayerPassedActions();
                    passedUntilStackResolved = true;
                    dateLastAddedToStack = game.getStack().getDateLastAdded();
                    this.skip();
                }
                break;
            case PASS_PRIORITY_UNTIL_END_STEP_BEFORE_MY_NEXT_TURN: //F11
                resetPlayerPassedActions();
                passedUntilEndStepBeforeMyTurn = true;
                this.skip();
                break;
            case PASS_PRIORITY_CANCEL_ALL_ACTIONS:
                resetPlayerPassedActions();
                break;
            case PERMISSION_REQUESTS_ALLOWED_OFF:
                userData.setAllowRequestShowHandCards(false);
                break;
            case PERMISSION_REQUESTS_ALLOWED_ON:
                userData.setAllowRequestShowHandCards(true);
                userData.resetRequestedHandPlayersList(game.getId()); // users can send request again
                break;
        }
        logger.trace("PASS Priority: " + playerAction);
    }

    @Override
    public void leave() {
        this.passed = true;
        this.loses = true;
        this.left = true;
        this.abort();
        //20100423 - 800.4a
        this.hand.clear();
        this.graveyard.clear();
        this.library.clear();
    }

    @Override
    public boolean hasLeft() {
        return this.left;
    }

    @Override
    public void lost(Game game) {
        if (canLose(game)) {
            lostForced(game);
        }
    }

    @Override
    public void lostForced(Game game) {
        logger.debug(this.getName() + " has lost gameId: " + game.getId());
        //20100423 - 603.9
        if (!this.wins) {
            this.loses = true;
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.LOST, null, null, playerId));
            game.informPlayers(this.getLogName() + " has lost the game.");
        } else {
            logger.debug(this.getName() + " has already won - stop lost");
        }
        // for draw - first all players that have lost have to be set to lost
        if (!hasLeft()) {
            logger.debug("Game over playerId: " + playerId);
            game.setConcedingPlayer(playerId);
        }
    }

    @Override
    public boolean canLose(Game game) {
        return hasLeft() // If a player concedes or has left the match they lose also if effect would say otherwise
                || !game.replaceEvent(new GameEvent(GameEvent.EventType.LOSES, null, null, playerId));
    }

    @Override
    public void won(Game game) {
        boolean opponentInGame = false;
        for (UUID opponentId : game.getOpponents(playerId)) {
            Player opponent = game.getPlayer(opponentId);

            if (opponent != null && opponent.isInGame()) {
                opponentInGame = true;
                break;
            }
        }
        if (!opponentInGame
                || // if no more opponent is in game the wins event may no longer be replaced
                !game.replaceEvent(new GameEvent(GameEvent.EventType.WINS, null, null, playerId))) {
            logger.debug("player won -> start: " + this.getName());
            if (!this.loses) {
                //20130501 - 800.7, 801.16
                // all opponents in range loose the game
                for (UUID opponentId : game.getOpponents(playerId)) {
                    Player opponent = game.getPlayer(opponentId);
                    if (opponent != null && !opponent.hasLost()) {
                        logger.debug("player won -> calling opponent lost: "
                                + this.getName() + "  opponent: " + opponent.getName());
                        opponent.lostForced(game);
                    }
                }
                // if no more opponents alive (independant from range), you win and the game ends
                int opponentsAlive = 0;
                for (UUID playerIdToCheck : game.getPlayerList()) {
                    if (game.isOpponent(this, playerIdToCheck)) { // Check without range
                        Player opponent = game.getPlayer(playerIdToCheck);
                        if (opponent != null && !opponent.hasLost()) {
                            opponentsAlive++;
                        }
                    }
                }
                if (opponentsAlive == 0 && !hasWon()) {
                    logger.debug("player won -> No more opponents alive game won: " + this.getName());
                    game.informPlayers(this.getLogName() + " has won the game");
                    this.wins = true;
                    game.end();
                }
            } else {
                logger.debug("player won -> but already lost before or other players still alive: " + this.getName());
            }
        }
    }

    @Override
    public void drew(Game game) {
        if (!hasLost()) {
            this.draws = true;
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.DRAW_PLAYER, null, null, playerId));
            game.informPlayers("For " + this.getLogName() + " the game is a draw.");
            game.setConcedingPlayer(playerId);
        }
    }

    @Override
    public boolean hasLost() {
        return this.loses;
    }

    @Override
    public boolean isInGame() {
        return !hasQuit() && !hasLost() && !hasWon() && !hasDrew() && !hasLeft();
    }

    @Override
    public boolean canRespond() { // abort is checked here to get out of player requests (as example: after disconnect)
        return isInGame() && !abort;
    }

    @Override
    public boolean hasWon() {
        return !this.loses && this.wins;
    }

    @Override
    public boolean hasDrew() {
        return this.draws;
    }

    @Override
    public void declareAttacker(UUID attackerId, UUID defenderId, Game game, boolean allowUndo) {
        if (allowUndo) {
            setStoredBookmark(game.bookmarkState()); // makes it possible to UNDO a declared attacker with costs from e.g. Propaganda
        }
        Permanent attacker = game.getPermanent(attackerId);
        if (attacker != null
                && attacker.canAttack(defenderId, game)
                && attacker.isControlledBy(playerId)) {
            if (!game.getCombat().declareAttacker(attackerId, defenderId, playerId, game)) {
                game.undo(playerId);
            }
        }
    }

    @Override
    public void declareBlocker(UUID defenderId, UUID blockerId, UUID attackerId, Game game) {
        declareBlocker(defenderId, blockerId, attackerId, game, true);
    }

    @Override
    public void declareBlocker(UUID defenderId, UUID blockerId, UUID attackerId, Game game, boolean allowUndo) {
        if (isHuman() && allowUndo) {
            setStoredBookmark(game.bookmarkState());
        }
        Permanent blocker = game.getPermanent(blockerId);
        CombatGroup group = game.getCombat().findGroup(attackerId);
        if (blocker != null && group != null && group.canBlock(blocker, game)) {
            group.addBlocker(blockerId, playerId, game);
            game.getCombat().addBlockingGroup(blockerId, attackerId, playerId, game);
        } else if (this.isHuman() && !game.isSimulation()) {
            game.informPlayer(this, "You can't block this creature.");
        }
    }

    @Override
    public boolean searchLibrary(TargetCardInLibrary target, Ability source, Game game) {
        return searchLibrary(target, source, game, playerId);
    }

    @Override
    public boolean searchLibrary(TargetCardInLibrary target, Ability source, Game game, UUID targetPlayerId) {
        //20091005 - 701.14c

        // searching control can be intercepted by another player, see Opposition Agent
        SearchLibraryEvent searchEvent = new SearchLibraryEvent(targetPlayerId, source, playerId, Integer.MAX_VALUE);
        if (game.replaceEvent(searchEvent)) {
            return false;
        }

        Player targetPlayer = game.getPlayer(targetPlayerId);
        Player searchingPlayer = this;
        Player searchingController = game.getPlayer(searchEvent.getSearchingControllerId());
        if (targetPlayer == null || searchingController == null) {
            return false;
        }

        String searchInfo = searchingPlayer.getLogName();
        if (!searchingPlayer.getId().equals(searchingController.getId())) {
            searchInfo = searchInfo + " under control of " + searchingPlayer.getLogName();
        }
        if (targetPlayer.getId().equals(searchingPlayer.getId())) {
            searchInfo = searchInfo + " searches their library";
        } else {
            searchInfo = searchInfo + " searches the library of " + targetPlayer.getLogName();
        }

        if (!game.isSimulation()) {
            game.informPlayers(searchInfo + CardUtil.getSourceLogName(game, source));
        }

        // https://www.reddit.com/r/magicTCG/comments/jj8gh9/opposition_agent_and_panglacial_wurm_interaction/
        // You must take full player control while searching, e.g. you can cast opponent's cards by Panglacial Wurm effect:
        // * While you’re searching your library, you may cast Panglacial Wurm from your library.
        // So use here same code as Word of Command
        // P.S. no needs in searchingController, but it helps with unit tests, see TakeControlWhileSearchingLibraryTest
        boolean takeControl = false;
        if (!searchingPlayer.getId().equals(searchingController.getId())) {
            CardUtil.takeControlUnderPlayerStart(game, searchingController, searchingPlayer, true);
            takeControl = true;
        }

        Library searchingLibrary = targetPlayer.getLibrary();
        TargetCardInLibrary newTarget = target.copy();
        int count;
        int librarySearchLimit = searchEvent.getAmount();
        List<Card> cardsFromTop = null;
        do {
            // TODO: prevent shuffling from moving the visualized cards
            if (librarySearchLimit == Integer.MAX_VALUE) {
                count = searchingLibrary.count(target.getFilter(), game);
            } else {
                if (cardsFromTop == null) {
                    cardsFromTop = new ArrayList<>(searchingLibrary.getTopCards(game, librarySearchLimit));
                } else {
                    cardsFromTop.retainAll(searchingLibrary.getCards(game));
                }
                newTarget.setCardLimit(Math.min(librarySearchLimit, cardsFromTop.size()));
                count = Math.min(searchingLibrary.count(target.getFilter(), game), librarySearchLimit);
            }

            if (count < target.getNumberOfTargets()) {
                newTarget.setMinNumberOfTargets(count);
            }

            // handling Panglacial Wurm - cast cards while searching from own library
            if (targetPlayer.getId().equals(searchingPlayer.getId())) {
                if (handleCastableCardsWhileLibrarySearching(library, game, targetPlayer)) {
                    // clear all choices to start from scratch (casted cards must be removed from library)
                    newTarget.clearChosen();
                    continue;
                }
            }

            if (newTarget.choose(Outcome.Neutral, searchingController.getId(), targetPlayer.getId(), game)) {
                target.getTargets().clear();
                for (UUID targetId : newTarget.getTargets()) {
                    target.add(targetId, game);
                }
            }

            // END SEARCH
            if (takeControl) {
                CardUtil.takeControlUnderPlayerEnd(game, searchingController, searchingPlayer);
                game.informPlayers("Control of " + searchingPlayer.getLogName() + " is back" + CardUtil.getSourceLogName(game, source));
            }

            LibrarySearchedEvent searchedEvent = new LibrarySearchedEvent(targetPlayer.getId(), source, searchingPlayer.getId(), target);
            if (!game.replaceEvent(searchedEvent)) {
                game.fireEvent(searchedEvent);
            }
            break;
        } while (true);

        return true;
    }

    @Override
    public boolean seekCard(FilterCard filter, Ability source, Game game) {
        Set<Card> cards = this.getLibrary()
                .getCards(game)
                .stream()
                .filter(card -> filter.match(card, source.getSourceId(), getId(), game))
                .collect(Collectors.toSet());
        Card card = RandomUtil.randomFromCollection(cards);
        if (card == null) {
            return false;
        }
        game.informPlayers(this.getLogName() + " seeks a card from their library");
        this.moveCards(card, Zone.HAND, source, game);
        return true;
    }

    @Override
    public void lookAtAllLibraries(Ability source, Game game) {
        for (UUID playerId : game.getState().getPlayersInRange(this.getId(), game)) {
            Player player = game.getPlayer(playerId);
            String playerName = this.getName().equals(player.getName()) ? "Your " : player.getName() + "'s ";
            playerName += "library";
            Cards cardsInLibrary = new CardsImpl(player.getLibrary().getTopCards(game, player.getLibrary().size()));
            lookAtCards(playerName, cardsInLibrary, game);
        }
    }

    private boolean handleCastableCardsWhileLibrarySearching(Library library, Game game, Player targetPlayer) {
        // must return true after cast try (to restart searching process without casted cards)
        // uses for handling Panglacial Wurm:
        // * While you're searching your library, you may cast Panglacial Wurm from your library.

        List<UUID> castableCards = library.getCards(game).stream()
                .filter(card -> card.getAbilities(game).containsClass(WhileSearchingPlayFromLibraryAbility.class))
                .map(MageItem::getId)
                .collect(Collectors.toList());
        if (castableCards.size() == 0) {
            return false;
        }

        // only humans can use it
        if (targetPlayer.isComputer()) {
            return false;
        }

        if (!targetPlayer.chooseUse(Outcome.AIDontUseIt, "There are " + castableCards.size() + " cards you can cast while searching your library. Cast any of them?", null, game)) {
            return false;
        }

        boolean casted = false;
        TargetCard targetCard = new TargetCard(0, 1, Zone.LIBRARY, StaticFilters.FILTER_CARD);
        targetCard.setTargetName("card to cast from library");
        targetCard.setNotTarget(true);
        while (castableCards.size() > 0) {
            targetCard.clearChosen();
            if (!targetPlayer.choose(Outcome.AIDontUseIt, new CardsImpl(castableCards), targetCard, game)) {
                break;
            }

            Card card = game.getCard(targetCard.getFirstTarget());
            if (card == null) {
                break;
            }

            // AI NOTICE: if you want AI implement here then remove selected card from castable after each
            // choice (otherwise you catch infinite freeze on uncastable use case)
            // casting selected card
            // TODO: fix costs (why is Panglacial Wurm automatically accepting payment?)
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
            targetPlayer.cast(targetPlayer.chooseAbilityForCast(card, game, false), game, false, null);
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
            castableCards.remove(card.getId());
            casted = true;
        }
        return casted;
    }

    /**
     * @param source
     * @param game
     * @param winnable
     * @return if winnable, true if player won the toss, if not winnable, true
     * for heads and false for tails
     */
    @Override
    public boolean flipCoin(Ability source, Game game, boolean winnable) {
        boolean chosen = false;
        if (winnable) {
            chosen = this.chooseUse(Outcome.Benefit, "Heads or tails?", "", "Heads", "Tails", source, game);
            game.informPlayers(getLogName() + " chose " + CardUtil.booleanToFlipName(chosen));
        }
        boolean result = this.flipCoinResult(game);
        FlipCoinEvent event = new FlipCoinEvent(playerId, source, result, chosen, winnable);
        game.replaceEvent(event);
        game.informPlayers(getLogName() + " flipped " + CardUtil.booleanToFlipName(event.getResult())
                + CardUtil.getSourceLogName(game, source));
        if (event.getFlipCount() > 1) {
            boolean canChooseHeads = event.getResult();
            boolean canChooseTails = !event.getResult();
            for (int i = 1; i < event.getFlipCount(); i++) {
                boolean tempFlip = this.flipCoinResult(game);
                canChooseHeads = canChooseHeads || tempFlip;
                canChooseTails = canChooseTails || !tempFlip;
                game.informPlayers(getLogName() + " flipped " + CardUtil.booleanToFlipName(tempFlip));
            }
            if (canChooseHeads && canChooseTails) {
                event.setResult(chooseUse(Outcome.Benefit, "Choose which flip to keep",
                        (event.isWinnable() ? "(You called " + event.getChosenName() + ")" : null),
                        "Heads", "Tails", source, game
                ));
            } else {
                event.setResult(canChooseHeads);
            }
            game.informPlayers(getLogName() + " chose to keep " + CardUtil.booleanToFlipName(event.getResult()));
        }
        if (event.isWinnable()) {
            game.informPlayers(getLogName() + " " + (event.getResult() == event.getChosen() ? "won" : "lost") + " the flip"
                    + CardUtil.getSourceLogName(game, source));
        }
        game.fireEvent(event.createFlippedEvent());
        if (event.isWinnable()) {
            return event.getResult() == event.getChosen();
        }
        return event.getResult();
    }

    /**
     * Return result for next flip coint try (can be contolled in tests)
     *
     * @return
     */
    @Override
    public boolean flipCoinResult(Game game) {
        return RandomUtil.nextBoolean();
    }

    private static final class RollDieResult {

        // 706.2.
        // After the roll, the number indicated on the top face of the die before any modifiers is
        // the natural result. The instruction may include modifiers to the roll which add to or
        // subtract from the natural result. Modifiers may also come from other sources. After
        // considering all applicable modifiers, the final number is the result of the die roll.
        private final int naturalResult;
        private final int modifier;
        private final PlanarDieRollResult planarResult;

        RollDieResult(int naturalResult, int modifier, PlanarDieRollResult planarResult) {
            this.naturalResult = naturalResult;
            this.modifier = modifier;
            this.planarResult = planarResult;
        }

        public int getResult() {
            return this.naturalResult + this.modifier;
        }

        public PlanarDieRollResult getPlanarResult() {
            return this.planarResult;
        }
    }

    @Override
    public int rollDieResult(int sides, Game game) {
        return RandomUtil.nextInt(sides) + 1;
    }

    /**
     * Roll single die. Support both die types: planar and numerical.
     *
     * @param outcome
     * @param game
     * @param source
     * @param rollDieType
     * @param sidesAmount
     * @param chaosSidesAmount
     * @param planarSidesAmount
     * @param rollsAmount
     * @return
     */
    private Object rollDieInner(Outcome outcome, Game game, Ability source, RollDieType rollDieType,
            int sidesAmount, int chaosSidesAmount, int planarSidesAmount, int rollsAmount) {
        if (rollsAmount == 1) {
            return rollDieInnerWithReplacement(game, source, rollDieType, sidesAmount, chaosSidesAmount, planarSidesAmount);
        }
        Set<Object> choices = new HashSet<>();
        for (int j = 0; j < rollsAmount; j++) {
            choices.add(rollDieInnerWithReplacement(game, source, rollDieType, sidesAmount, chaosSidesAmount, planarSidesAmount));
        }
        if (choices.size() == 1) {
            return choices.stream().findFirst().orElse(0);
        }

        // AI hint - use max/min values
        if (this.isComputer()) {
            if (rollDieType == RollDieType.NUMERICAL) {
                // numerical
                if (outcome.isGood()) {
                    return choices.stream()
                            .map(Integer.class::cast)
                            .max(Comparator.naturalOrder())
                            .orElse(null);
                } else {
                    return choices.stream()
                            .map(Integer.class::cast)
                            .min(Comparator.naturalOrder())
                            .orElse(null);
                }
            } else {
                // planar
                // priority: chaos -> planar -> blank
                return choices.stream()
                        .map(PlanarDieRollResult.class::cast)
                        .max(Comparator.comparingInt(PlanarDieRollResult::getAIPriority))
                        .orElse(null);
            }
        }

        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Choose which die roll result to keep (the rest will be ignored)");
        choice.setChoices(choices.stream().sorted().map(Object::toString).collect(Collectors.toSet()));

        this.choose(Outcome.Neutral, choice, game);
        Object defaultChoice = choices.iterator().next();
        return choices.stream()
                .filter(o -> o.toString().equals(choice.getChoice()))
                .findFirst()
                .orElse(defaultChoice);
    }

    private Object rollDieInnerWithReplacement(Game game, Ability source, RollDieType rollDieType, int numSides, int numChaosSides, int numPlanarSides) {
        switch (rollDieType) {

            case NUMERICAL: {
                int result = rollDieResult(numSides, game);
                // Clam-I-Am workaround:
                // If you roll a 3 on a six-sided die, you may reroll that die.
                if (numSides == 6
                        && result == 3
                        && game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.REPLACE_ROLLED_DIE, source.getControllerId(), source, source.getControllerId()))
                        && chooseUse(Outcome.Neutral, "Re-roll the 3?", source, game)) {
                    result = rollDieResult(numSides, game);
                }
                return result;
            }

            case PLANAR: {
                if (numChaosSides + numPlanarSides > numSides) {
                    numChaosSides = GameOptions.PLANECHASE_PLANAR_DIE_CHAOS_SIDES;
                    numPlanarSides = GameOptions.PLANECHASE_PLANAR_DIE_PLANAR_SIDES;
                }
                // for 9 sides:
                // 1..2 - chaos
                // 3..7 - blank
                // 8..9 - planar
                int result = this.rollDieResult(numSides, game);
                PlanarDieRollResult roll;
                if (result <= numChaosSides) {
                    roll = PlanarDieRollResult.CHAOS_ROLL;
                } else if (result > numSides - numPlanarSides) {
                    roll = PlanarDieRollResult.PLANAR_ROLL;
                } else {
                    roll = PlanarDieRollResult.BLANK_ROLL;
                }
                return roll;
            }

            default: {
                throw new IllegalArgumentException("Unknown roll die type " + rollDieType);
            }
        }
    }

    /**
     * @param outcome
     * @param source
     * @param game
     * @param sidesAmount number of sides the dice has
     * @param rollsAmount number of tries to roll the dice
     * @param ignoreLowestAmount remove the lowest rolls from the results
     * @return the number that the player rolled
     */
    @Override
    public List<Integer> rollDice(Outcome outcome, Ability source, Game game, int sidesAmount, int rollsAmount, int ignoreLowestAmount) {
        return rollDiceInner(outcome, source, game, RollDieType.NUMERICAL, sidesAmount, 0, 0, rollsAmount, ignoreLowestAmount)
                .stream()
                .map(Integer.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Inner code to roll a dice. Support normal and planar types.
     *
     * @param outcome
     * @param source
     * @param game
     * @param rollDieType die type to roll, e.g. planar or numerical
     * @param sidesAmount sides per die
     * @param chaosSidesAmount for planar die: chaos sides
     * @param planarSidesAmount for planar die: planar sides
     * @param rollsAmount rolls
     * @param ignoreLowestAmount for numerical die: ignore multiple rolls with
     * the lowest values
     * @return
     */
    private List<Object> rollDiceInner(Outcome outcome, Ability source, Game game, RollDieType rollDieType,
            int sidesAmount, int chaosSidesAmount, int planarSidesAmount,
            int rollsAmount, int ignoreLowestAmount) {
        RollDiceEvent rollDiceEvent = new RollDiceEvent(source, rollDieType, sidesAmount, rollsAmount);
        if (ignoreLowestAmount > 0) {
            rollDiceEvent.incIgnoreLowestAmount(ignoreLowestAmount);
        }
        game.replaceEvent(rollDiceEvent);

        // 706.6.
        // In a Planechase game, rolling the planar die will cause any ability that triggers whenever a
        // player rolls one or more dice to trigger. However, any effect that refers to a numerical
        // result of a die roll, including ones that compare the results of that roll to other rolls
        // or to a given number, ignores the rolling of the planar die. See rule 901, “Planechase.”
        // ROLL MULTIPLE dies
        // results amount can be less than a rolls amount (example: The Big Idea allows rolling 2x instead 1x)
        List<Object> dieResults = new ArrayList<>();
        List<RollDieResult> dieRolls = new ArrayList<>();
        for (int i = 0; i < rollDiceEvent.getAmount(); i++) {
            // ROLL SINGLE die
            RollDieEvent rollDieEvent = new RollDieEvent(source, rollDiceEvent.getRollDieType(), rollDiceEvent.getSides());
            game.replaceEvent(rollDieEvent);

            Object rollResult;
            // big idea logic for numerical rolls only
            if (rollDieEvent.getRollDieType() == RollDieType.NUMERICAL && rollDieEvent.getBigIdeaRollsAmount() > 0) {
                // rolls 2x + sum results
                // The Big Idea: roll two six-sided dice and use the total of those results
                int totalSum = 0;
                for (int j = 0; j < rollDieEvent.getBigIdeaRollsAmount() + 1; j++) {
                    int singleResult = (Integer) rollDieInner(
                            outcome,
                            game,
                            source,
                            rollDieEvent.getRollDieType(),
                            rollDieEvent.getSides(),
                            chaosSidesAmount,
                            planarSidesAmount,
                            rollDieEvent.getRollsAmount());
                    totalSum += singleResult;
                    dieRolls.add(new RollDieResult(singleResult, rollDieEvent.getResultModifier(), null));
                }
                rollResult = totalSum;
            } else {
                // rolls 1x
                switch (rollDieEvent.getRollDieType()) {
                    default:
                    case NUMERICAL: {
                        int naturalResult = (Integer) rollDieInner(
                                outcome,
                                game,
                                source,
                                rollDieEvent.getRollDieType(),
                                rollDieEvent.getSides(),
                                chaosSidesAmount,
                                planarSidesAmount,
                                rollDieEvent.getRollsAmount()
                        );
                        dieRolls.add(new RollDieResult(naturalResult, rollDieEvent.getResultModifier(), null));
                        rollResult = naturalResult;
                        break;
                    }

                    case PLANAR: {
                        PlanarDieRollResult planarResult = (PlanarDieRollResult) rollDieInner(
                                outcome,
                                game,
                                source,
                                rollDieEvent.getRollDieType(),
                                rollDieEvent.getSides(),
                                chaosSidesAmount,
                                planarSidesAmount,
                                rollDieEvent.getRollsAmount()
                        );
                        dieRolls.add(new RollDieResult(0, 0, planarResult));
                        rollResult = planarResult;
                        break;
                    }
                }
            }
            dieResults.add(rollResult);
        }

        // ignore the lowest results
        // planar dies: due to 706.6. planar die results must be fully ignored
        //
        // 706.5.
        // If a player is instructed to roll two or more dice and ignore the lowest roll, the roll
        // that yielded the lowest result is considered to have never happened. No abilities trigger
        // because of the ignored roll, and no effects apply to that roll. If multiple results are tied
        // for the lowest, the player chooses one of those rolls to be ignored.
        int diceRolledTotal = dieRolls.size();
        String ignoreMessage;
        if (rollDiceEvent.getRollDieType() == RollDieType.NUMERICAL && rollDiceEvent.getIgnoreLowestAmount() > 0) {
            // find ignored values
            List<Integer> ignoredResults = new ArrayList<>();
            for (int i = 0; i < rollDiceEvent.getIgnoreLowestAmount(); i++) {
                int min = dieResults.stream().map(Integer.class::cast).mapToInt(Integer::intValue).min().orElse(0);
                dieResults.remove(Integer.valueOf(min));
                ignoredResults.add(min);
            }
            ignoreMessage = String.format(
                    ignoredResults.size() > 1 ? ", ignoring [%s]" : ", ignoring %s",
                    ignoredResults
                            .stream()
                            .map(x -> "" + x)
                            .collect(Collectors.joining(", "))
            );
            // remove ignored rolls (they not exist anymore)
            List<RollDieResult> newRolls = new ArrayList<>();
            for (RollDieResult rollDieResult : dieRolls) {
                if (ignoredResults.contains(rollDieResult.getResult())) {
                    ignoredResults.remove((Integer) rollDieResult.getResult());
                } else {
                    newRolls.add(rollDieResult);
                }
            }
            dieRolls.clear();
            dieRolls.addAll(newRolls);
        } else {
            ignoreMessage = "";
        }

        // raise affected roll events
        for (RollDieResult result : dieRolls) {
            game.fireEvent(new DieRolledEvent(source, rollDiceEvent.getRollDieType(), rollDiceEvent.getSides(), result.naturalResult, result.modifier, result.planarResult));
        }
        game.fireEvent(new DiceRolledEvent(rollDiceEvent.getSides(), dieResults, source));

        String resultString = dieResults
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        String message;
        switch (rollDiceEvent.getRollDieType()) {
            default:
            case NUMERICAL:
                // [Roll a die] user rolled 4d6, results: [4, 6], ignoring [1, 3] (source: xxx)
                message = String.format("[Roll a die] %s rolled %sd%s, result%s: %s%s%s",
                        getLogName(),
                        diceRolledTotal > 1 ? diceRolledTotal : "a ",
                        rollDiceEvent.getSides(),
                        dieResults.size() > 1 ? 's' : "",
                        dieResults.size() > 1 ? '[' + resultString + ']' : resultString,
                        ignoreMessage,
                        CardUtil.getSourceLogName(game, source));
                break;
            case PLANAR:
                // [Roll a planar die] user rolled CHAOS (source: xxx)
                message = String.format("[Roll a planar die] %s rolled %s%s",
                        getLogName(),
                        dieResults.size() > 1 ? '[' + resultString + ']' : resultString,
                        CardUtil.getSourceLogName(game, source));
                break;
        }
        game.informPlayers(message);
        return dieResults;
    }

    /**
     * @param source
     * @param game
     * @param chaosSidesAmount The number of chaos sides the planar die
     * currently has (normally 1 but can be 5)
     * @param planarSidesAmount The number of chaos sides the planar die
     * currently has (normally 1)
     * @return the outcome that the player rolled. Either ChaosRoll, PlanarRoll
     * or BlankRoll
     */
    @Override
    public PlanarDieRollResult rollPlanarDie(Outcome outcome, Ability source, Game game, int chaosSidesAmount, int planarSidesAmount) {
        return rollDiceInner(outcome, source, game, RollDieType.PLANAR, GameOptions.PLANECHASE_PLANAR_DIE_TOTAL_SIDES, chaosSidesAmount, planarSidesAmount, 1, 0)
                .stream()
                .map(o -> (PlanarDieRollResult) o)
                .findFirst()
                .orElse(PlanarDieRollResult.BLANK_ROLL);
    }

    @Override
    public List<Permanent> getAvailableAttackers(Game game) {
        // TODO: get available opponents and their planeswalkers, check for each if permanent can attack one
        return getAvailableAttackers(null, game);
    }

    @Override
    public List<Permanent> getAvailableAttackers(UUID defenderId, Game game) {
        FilterCreatureForCombat filter = new FilterCreatureForCombat();
        List<Permanent> attackers = game.getBattlefield().getAllActivePermanents(filter, playerId, game);
        attackers.removeIf(entry -> !entry.canAttack(defenderId, game));
        return attackers;
    }

    @Override
    public List<Permanent> getAvailableBlockers(Game game) {
        FilterCreatureForCombatBlock blockFilter = new FilterCreatureForCombatBlock();
        return game.getBattlefield().getAllActivePermanents(blockFilter, playerId, game);
    }

    /**
     * Returns the mana options the player currently has. That means which
     * combinations of mana are available to cast spells or activate abilities
     * etc.
     *
     * @param game
     * @return
     */
    @Override
    public ManaOptions getManaAvailable(Game game) {
        boolean oldState = game.inCheckPlayableState();
        game.setCheckPlayableState(true);

        ManaOptions availableMana = new ManaOptions();
        availableMana.addMana(manaPool.getMana());
        // conditional mana
        for (ConditionalMana conditionalMana : manaPool.getConditionalMana()) {
            availableMana.addMana(conditionalMana);
        }

        List<Abilities<ActivatedManaAbilityImpl>> sourceWithoutManaCosts = new ArrayList<>();
        List<Abilities<ActivatedManaAbilityImpl>> sourceWithCosts = new ArrayList<>();
        for (Card card : getHand().getCards(game)) {
            Abilities<ActivatedManaAbilityImpl> manaAbilities
                    = card.getAbilities(game).getAvailableActivatedManaAbilities(Zone.HAND, playerId, game);
            for (Iterator<ActivatedManaAbilityImpl> it = manaAbilities.iterator(); it.hasNext();) {
                ActivatedManaAbilityImpl ability = it.next();
                Abilities<ActivatedManaAbilityImpl> noTapAbilities = new AbilitiesImpl<>(ability);
                if (ability.getManaCosts().isEmpty() && !ability.isPoolDependant()) {
                    sourceWithoutManaCosts.add(noTapAbilities);
                } else {
                    sourceWithCosts.add(noTapAbilities);
                }
            }
        }

        for (Permanent permanent : game.getBattlefield().getActivePermanents(playerId, game)) { // Some permanents allow use of abilities from non controlling players. so check all permanents in range
            Boolean canUse = null;
            boolean canAdd = false;
            boolean useLater = false; // sources with mana costs or mana pool dependency 
            Abilities<ActivatedManaAbilityImpl> manaAbilities
                    = permanent.getAbilities(game).getAvailableActivatedManaAbilities(Zone.BATTLEFIELD, playerId, game); // returns ability only if canActivate is true
            for (Iterator<ActivatedManaAbilityImpl> it = manaAbilities.iterator(); it.hasNext();) {
                ActivatedManaAbilityImpl ability = it.next();
                if (canUse == null) {
                    canUse = permanent.canUseActivatedAbilities(game);
                }
                if (canUse) {
                    // abilities without Tap costs have to be handled as separate sources, because they can be used also
                    if (!ability.hasTapCost()) {
                        it.remove();
                        Abilities<ActivatedManaAbilityImpl> noTapAbilities = new AbilitiesImpl<>(ability);
                        if (ability.getManaCosts().isEmpty() && !ability.isPoolDependant()) {
                            sourceWithoutManaCosts.add(noTapAbilities);
                        } else {
                            sourceWithCosts.add(noTapAbilities);
                        }
                        continue;
                    }

                    canAdd = true;
                    if (!ability.getManaCosts().isEmpty() || ability.isPoolDependant()) {
                        useLater = true;
                        break;
                    }
                }
            }
            if (canAdd) {
                if (useLater) {
                    sourceWithCosts.add(manaAbilities);
                } else {
                    sourceWithoutManaCosts.add(manaAbilities);
                }
            }
        }

        for (Abilities<ActivatedManaAbilityImpl> manaAbilities : sourceWithoutManaCosts) {
            availableMana.addMana(manaAbilities, game);
        }

        boolean anAbilityWasUsed = true;
        boolean usePoolDependantAbilities = false; // use such abilities later than other if possible because it can maximize mana production
        while (anAbilityWasUsed && !sourceWithCosts.isEmpty()) {
            anAbilityWasUsed = false;
            for (Iterator<Abilities<ActivatedManaAbilityImpl>> iterator = sourceWithCosts.iterator(); iterator.hasNext();) {
                Abilities<ActivatedManaAbilityImpl> manaAbilities = iterator.next();
                if (usePoolDependantAbilities || !manaAbilities.hasPoolDependantAbilities()) {
                    boolean used;
                    if (manaAbilities.hasPoolDependantAbilities()) {
                        used = availableMana.addManaPoolDependant(manaAbilities, game);
                    } else {
                        used = availableMana.addManaWithCost(manaAbilities, game);
                    }
                    if (used) {
                        iterator.remove();
                        availableMana.removeDuplicated();
                        anAbilityWasUsed = true;
                    }
                }
            }
            if (!anAbilityWasUsed && !usePoolDependantAbilities) {
                usePoolDependantAbilities = true;
                anAbilityWasUsed = true;
            }
        }

        // remove duplicated variants (see ManaOptionsTest for info - when that rises)
        availableMana.removeDuplicated();

        game.setCheckPlayableState(oldState);
        return availableMana;
    }

    /**
     * Used during calculation of available mana to gather the amount of
     * producable triggered mana caused by using mana sources. So the set value
     * is only used during the calculation of the mana produced by one source
     * and cleared thereafter
     *
     * @param netManaAvailable the net mana produced by the triggered mana
     * abaility
     */
    @Override
    public void addAvailableTriggeredMana(List<Mana> netManaAvailable
    ) {
        this.availableTriggeredManaList.add(netManaAvailable);
    }

    /**
     * Used during calculation of available mana to get the amount of producable
     * triggered mana caused by using mana sources. The list is cleared as soon
     * the value is retrieved during available mana calculation.
     *
     * @return
     */
    @Override
    public List<List<Mana>> getAvailableTriggeredMana() {
        return availableTriggeredManaList;
    }
    // returns only mana producers that don't require mana payment

    protected List<MageObject> getAvailableManaProducers(Game game) {
        List<MageObject> result = new ArrayList<>();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(playerId, game)) { // Some permanents allow use of abilities from non controlling players. so check all permanents in range
            Boolean canUse = null;
            boolean canAdd = false;
            for (ActivatedManaAbilityImpl ability : permanent.getAbilities().getActivatedManaAbilities(Zone.BATTLEFIELD)) {
                if (!ability.getManaCosts().isEmpty()) {
                    canAdd = false;
                    break;
                }
                if (canUse == null) {
                    canUse = permanent.canUseActivatedAbilities(game);
                }
                if (canUse && ability.canActivate(playerId, game).canActivate()) {
                    canAdd = true;
                }
            }
            if (canAdd) {
                result.add(permanent);
            }
        }
        for (Card card : getHand().getCards(game)) {
            boolean canAdd = false;
            for (ActivatedManaAbilityImpl ability : card.getAbilities(game).getActivatedManaAbilities(Zone.HAND)) {
                if (!ability.getManaCosts().isEmpty()) {
                    canAdd = false;
                    break;
                }
                if (ability.canActivate(playerId, game).canActivate()) {
                    canAdd = true;
                }
            }
            if (canAdd) {
                result.add(card);
            }
        }
        return result;
    }

    // returns only mana producers that require mana payment
    public List<Permanent> getAvailableManaProducersWithCost(Game game) {
        List<Permanent> result = new ArrayList<>();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(playerId, game)) {
            Boolean canUse = null;
            for (ActivatedManaAbilityImpl ability : permanent.getAbilities().getActivatedManaAbilities(Zone.BATTLEFIELD)) {
                if (canUse == null) {
                    canUse = permanent.canUseActivatedAbilities(game);
                }
                if (canUse && ability.canActivate(playerId, game).canActivate()
                        && !ability.getManaCosts().isEmpty()) {
                    result.add(permanent);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * @param ability
     * @param availableMana if null, it won't be checked if enough mana is
     * available
     * @param sourceObject
     * @param game
     * @return
     */
    protected boolean canPlay(ActivatedAbility ability, ManaOptions availableMana, MageObject sourceObject, Game game) {
        if (!(ability instanceof ActivatedManaAbilityImpl)) {
            ActivatedAbility copy = ability.copy(); // Copy is needed because cost reduction effects modify e.g. the mana to activate/cast the ability
            if (!copy.canActivate(playerId, game).canActivate()) {
                return false;
            }
            if (availableMana != null) {
                sourceObject.adjustCosts(copy, game);
                game.getContinuousEffects().costModification(copy, game);
            }
            boolean canBeCastRegularly = true;
            if (copy instanceof SpellAbility && copy.getManaCosts().isEmpty() && copy.getCosts().isEmpty()) {
                // 117.6. Some mana costs contain no mana symbols. This represents an unpayable cost...
                // 117.6a (...) If an alternative cost is applied to an unpayable cost,
                // including an effect that allows a player to cast a spell without paying its mana cost, the alternative cost may be paid.
                canBeCastRegularly = false;
            }
            if (canBeCastRegularly) {
                if (canPayMinimumManaCost(copy, availableMana, game)) {
                    return true;
                }
            }

            // ALTERNATIVE COST FROM dynamic effects
            if (getCastSourceIdWithAlternateMana().contains(copy.getSourceId())) {
                ManaCosts alternateCosts = getCastSourceIdManaCosts().get(copy.getSourceId());
                Costs<Cost> costs = getCastSourceIdCosts().get(copy.getSourceId());

                boolean canPutToPlay = true;
                if (alternateCosts != null && !alternateCosts.canPay(copy, copy, playerId, game)) {
                    canPutToPlay = false;
                }
                if (costs != null && !costs.canPay(copy, copy, playerId, game)) {
                    canPutToPlay = false;
                }

                if (canPutToPlay) {
                    return true;
                }
            }

            // ALTERNATIVE COST from source card (any AlternativeSourceCosts)
            if (AbilityType.SPELL.equals(ability.getAbilityType())) {
                return canPlayCardByAlternateCost(game.getCard(ability.getSourceId()), availableMana, copy, game);
            }
        }
        return false;
    }

    protected boolean canPayMinimumManaCost(ActivatedAbility ability, ManaOptions availableMana, Game game) {
        ManaOptions abilityOptions = ability.getMinimumCostToActivate(playerId, game);
        if (abilityOptions.isEmpty()) {
            return true;
        } else {
            if (availableMana == null) {
                return true;
            }
            // Check for pay option with like phyrexian mana
            if (getPhyrexianColors() != null) {
                addPhyrexianLikePayOptions(abilityOptions, availableMana, game);
            }

            ApprovingObject approvingObject = game.getContinuousEffects().asThough(ability.getSourceId(),
                    AsThoughEffectType.SPEND_OTHER_MANA, ability, ability.getControllerId(), game);
            for (Mana mana : abilityOptions) {
                if (mana.count() == 0) {
                    return true;
                }
                for (Mana avail : availableMana) {
                    // TODO: SPEND_OTHER_MANA effects with getAsThoughManaType can change mana type to pay,
                    //  but that code processing it as any color, need to test and fix another use cases
                    //  (example: Sunglasses of Urza - may spend white mana as though it were red mana)

                    //
                    //  add tests for non any color like Sunglasses of Urza
                    if (approvingObject != null && mana.count() <= avail.count()) {
                        return true;
                    }
                    if (avail instanceof ConditionalMana && !((ConditionalMana) avail).apply(ability, game, getId(), ability.getManaCosts())) {
                        continue;
                    }
                    if (mana.enough(avail)) { // here we need to check if spend mana as though allow to pay the mana cost
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void addPhyrexianLikePayOptions(ManaOptions abilityOptions, ManaOptions availableMana, Game game) {
        int maxLifeMana = getLife() / 2;
        if (maxLifeMana > 0) {
            Set<Mana> phyrexianOptions = new HashSet<>();
            for (Mana mana : abilityOptions) {
                int availableLifeMana = maxLifeMana;
                if (getPhyrexianColors().isBlack()) {
                    createReducedManaPayOption(availableLifeMana, mana, phyrexianOptions, ManaType.BLACK);
                }
                if (getPhyrexianColors().isBlue()) {
                    createReducedManaPayOption(availableLifeMana, mana, phyrexianOptions, ManaType.BLUE);
                }
                if (getPhyrexianColors().isRed()) {
                    createReducedManaPayOption(availableLifeMana, mana, phyrexianOptions, ManaType.RED);
                }
                if (getPhyrexianColors().isGreen()) {
                    createReducedManaPayOption(availableLifeMana, mana, phyrexianOptions, ManaType.GREEN);
                }
                if (getPhyrexianColors().isWhite()) {
                    createReducedManaPayOption(availableLifeMana, mana, phyrexianOptions, ManaType.WHITE);
                }
            }
            abilityOptions.addAll(phyrexianOptions);
        }
    }

    private int createReducedManaPayOption(int availableLifeMana, Mana oldPayOption, Set<Mana> phyrexianOptions, ManaType manaType) {
        if (oldPayOption.get(manaType) > 0) {
            Mana manaCopy = oldPayOption.copy();
            int restVal;
            if (availableLifeMana > oldPayOption.get(manaType)) {
                restVal = 0;
                availableLifeMana -= oldPayOption.get(manaType);
            } else {
                restVal = CardUtil.overflowDec(oldPayOption.get(manaType), availableLifeMana);
                availableLifeMana = 0;
            }
            manaCopy.set(manaType, restVal);
            phyrexianOptions.add(manaCopy);
        }
        return availableLifeMana;
    }

    protected boolean canPlayCardByAlternateCost(Card sourceObject, ManaOptions availableMana, Ability ability, Game game) {
        if (sourceObject != null && !(sourceObject instanceof Permanent)) {
            Ability copyAbility; // for alternative cost and reduce tries
            for (Ability alternateSourceCostsAbility : sourceObject.getAbilities()) {
                // if cast for noMana no Alternative costs are allowed
                if (alternateSourceCostsAbility instanceof AlternativeSourceCosts) {
                    if (((AlternativeSourceCosts) alternateSourceCostsAbility).isAvailable(ability, game)) {
                        if (alternateSourceCostsAbility.getCosts().canPay(ability, ability, playerId, game)) {
                            ManaCostsImpl manaCosts = new ManaCostsImpl();
                            for (Cost cost : alternateSourceCostsAbility.getCosts()) {
                                // AlternativeCost2 replaced by real cost on activate, so getPlayable need to extract that costs here
                                if (cost instanceof AlternativeCost2) {
                                    if (((AlternativeCost2) cost).getCost() instanceof ManaCost) {
                                        manaCosts.add((ManaCost) ((AlternativeCost2) cost).getCost());
                                    }
                                } else {
                                    if (cost instanceof ManaCost) {
                                        manaCosts.add((ManaCost) cost);
                                    }
                                }
                            }

                            if (manaCosts.isEmpty()) {
                                return true;
                            } else {
                                if (availableMana == null) {
                                    return true;
                                }

                                // alternative cost reduce
                                copyAbility = ability.copy();
                                copyAbility.getManaCostsToPay().clear();
                                copyAbility.getManaCostsToPay().addAll(manaCosts.copy());
                                sourceObject.adjustCosts(copyAbility, game);
                                game.getContinuousEffects().costModification(copyAbility, game);

                                // reduced all cost
                                if (copyAbility.getManaCostsToPay().isEmpty()) {
                                    return true;
                                }

                                for (Mana mana : copyAbility.getManaCostsToPay().getOptions()) {
                                    if (availableMana.enough(mana)) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // controller specific alternate spell costs
            for (AlternativeSourceCosts alternateSourceCosts : getAlternativeSourceCosts()) {
                if (alternateSourceCosts instanceof Ability) {
                    if (alternateSourceCosts.isAvailable(ability, game)) {
                        if (((Ability) alternateSourceCosts).getCosts().canPay(ability, ability, playerId, game)) {
                            ManaCostsImpl manaCosts = new ManaCostsImpl();
                            for (Cost cost : ((Ability) alternateSourceCosts).getCosts()) {
                                // AlternativeCost2 replaced by real cost on activate, so getPlayable need to extract that costs here
                                if (cost instanceof AlternativeCost2) {
                                    if (((AlternativeCost2) cost).getCost() instanceof ManaCost) {
                                        manaCosts.add((ManaCost) ((AlternativeCost2) cost).getCost());
                                    }
                                } else {
                                    if (cost instanceof ManaCost) {
                                        manaCosts.add((ManaCost) cost);
                                    }
                                }
                            }

                            if (manaCosts.isEmpty()) {
                                return true;
                            } else {
                                if (availableMana == null) {
                                    return true;
                                }

                                // alternative cost reduce
                                copyAbility = ability.copy();
                                copyAbility.getManaCostsToPay().clear();
                                copyAbility.getManaCostsToPay().addAll(manaCosts.copy());
                                sourceObject.adjustCosts(copyAbility, game);
                                game.getContinuousEffects().costModification(copyAbility, game);

                                // reduced all cost
                                if (copyAbility.getManaCostsToPay().isEmpty()) {
                                    return true;
                                }

                                for (Mana mana : copyAbility.getManaCostsToPay().getOptions()) {
                                    if (availableMana.enough(mana)) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    protected ActivatedAbility findActivatedAbilityFromPlayable(MageObject object, ManaOptions availableMana, Ability ability, Game game) {

        // special mana to pay spell cost
        ManaOptions manaFull = availableMana.copy();
        if (ability instanceof SpellAbility) {
            for (AlternateManaPaymentAbility altAbility : CardUtil.getAbilities(object, game).stream()
                    .filter(a -> a instanceof AlternateManaPaymentAbility)
                    .map(a -> (AlternateManaPaymentAbility) a)
                    .collect(Collectors.toList())) {
                ManaOptions manaSpecial = altAbility.getManaOptions(ability, game, ability.getManaCostsToPay());
                manaFull.addMana(manaSpecial);
            }
        }

        // replace alternative abilities by real play abilities (e.g. morph/facedown static ability by play land)
        if (ability instanceof ActivatedManaAbilityImpl) {
            // mana ability
            if (((ActivatedManaAbilityImpl) ability).canActivate(this.getId(), game).canActivate()) {
                return (ActivatedManaAbilityImpl) ability;
            }
        } else if (ability instanceof AlternativeSourceCosts) {
            // alternative cost must be replaced by real play ability
            return findActivatedAbilityFromAlternativeSourceCost(object, manaFull, ability, game);
        } else if (ability instanceof ActivatedAbility) {
            // all other activated ability
            if (canPlay((ActivatedAbility) ability, manaFull, object, game)) {
                return (ActivatedAbility) ability;
            }
        }

        // non playable abilities like static
        return null;
    }

    protected ActivatedAbility findActivatedAbilityFromAlternativeSourceCost(MageObject object, ManaOptions availableMana, Ability ability, Game game) {
        // return play ability that can activate AlternativeSourceCosts
        if (ability instanceof AlternativeSourceCosts && object != null && !(object instanceof Permanent)) {
            ActivatedAbility playAbility = null;
            if (object.isLand(game)) {
                playAbility = (PlayLandAbility) CardUtil.getAbilities(object, game).stream().filter(a -> a instanceof PlayLandAbility).findFirst().orElse(null);
            } else if (object instanceof Card) {
                playAbility = ((Card) object).getSpellAbility();
            }
            if (playAbility == null) {
                return null;
            }

            // 707.4.Objects that are cast face down are turned face down before they are put onto the stack
            // E.g. no lands per turn limit, no cast restrictions, cost reduce, etc
            // Even mana cost can't be checked here without lookahead
            // So make it available all the time
            boolean canUse;
            if (ability instanceof MorphAbility && object instanceof Card && (game.canPlaySorcery(getId())
                    || (null != game.getContinuousEffects().asThough(object.getId(), AsThoughEffectType.CAST_AS_INSTANT, playAbility, this.getId(), game)))) {
                canUse = canPlayCardByAlternateCost((Card) object, availableMana, playAbility, game);
            } else {
                canUse = canPlay(playAbility, availableMana, object, game); // canPlay already checks alternative source costs and all conditions
            }

            if (canUse) {
                return playAbility;
            }
        }
        return null;
    }

    private void getPlayableFromObjectAll(Game game, Zone fromZone, MageObject object, ManaOptions availableMana, List<ActivatedAbility> output) {
        if (fromZone == null || object == null) {
            return;
        }

        // BASIC abilities
        if (object instanceof SplitCard) {
            SplitCard mainCard = (SplitCard) object;
            getPlayableFromObjectSingle(game, fromZone, mainCard.getLeftHalfCard(), mainCard.getLeftHalfCard().getAbilities(game), availableMana, output);
            getPlayableFromObjectSingle(game, fromZone, mainCard.getRightHalfCard(), mainCard.getRightHalfCard().getAbilities(game), availableMana, output);
            getPlayableFromObjectSingle(game, fromZone, mainCard, mainCard.getSharedAbilities(game), availableMana, output);
        } else if (object instanceof ModalDoubleFacesCard) {
            ModalDoubleFacesCard mainCard = (ModalDoubleFacesCard) object;
            getPlayableFromObjectSingle(game, fromZone, mainCard.getLeftHalfCard(), mainCard.getLeftHalfCard().getAbilities(game), availableMana, output);
            getPlayableFromObjectSingle(game, fromZone, mainCard.getRightHalfCard(), mainCard.getRightHalfCard().getAbilities(game), availableMana, output);
            getPlayableFromObjectSingle(game, fromZone, mainCard, mainCard.getSharedAbilities(game), availableMana, output);
        } else if (object instanceof AdventureCard) {
            // adventure must use different card characteristics for different spells (main or adventure)
            AdventureCard adventureCard = (AdventureCard) object;
            getPlayableFromObjectSingle(game, fromZone, adventureCard.getSpellCard(), adventureCard.getSpellCard().getAbilities(game), availableMana, output);
            getPlayableFromObjectSingle(game, fromZone, adventureCard, adventureCard.getSharedAbilities(game), availableMana, output);
        } else if (object instanceof Card) {
            getPlayableFromObjectSingle(game, fromZone, object, ((Card) object).getAbilities(game), availableMana, output);
        } else if (object instanceof StackObject) {
            // spells on stack are processing by Card above, other stack objects must be ignored
        } else {
            // other things like CommandObject
            getPlayableFromObjectSingle(game, fromZone, object, object.getAbilities(), availableMana, output);
        }

        // DYNAMIC ADDED abilities are adds in getAbilities(game)
    }

    private void getPlayableFromObjectSingle(Game game, Zone fromZone, MageObject object, Abilities<Ability> candidateAbilities, ManaOptions availableMana, List<ActivatedAbility> output) {
        // check "can play" condition as affected controller (BUT play from not own hand zone must be checked as original controller)
        // must check all abilities, not activated only
        for (Ability ability : candidateAbilities) {
            if (!(ability instanceof ActivatedAbility)) {
                continue;
            }
            boolean isPlaySpell = (ability instanceof SpellAbility);
            boolean isPlayLand = (ability instanceof PlayLandAbility);

            // as original controller
            // play land restrictions
            if (isPlayLand && game.getContinuousEffects().preventedByRuleModification(
                    GameEvent.getEvent(GameEvent.EventType.PLAY_LAND, ability.getSourceId(),
                            ability, this.getId()), ability, game, true)) {
                continue;
            }
            // cast spell restrictions 1
            GameEvent castEvent = GameEvent.getEvent(GameEvent.EventType.CAST_SPELL, ability.getId(), ability, this.getId());
            castEvent.setZone(fromZone);
            if (isPlaySpell && game.getContinuousEffects().preventedByRuleModification(
                    castEvent, ability, game, true)) {
                continue;
            }
            // cast spell restrictions 2
            GameEvent castLateEvent = GameEvent.getEvent(GameEvent.EventType.CAST_SPELL_LATE,
                    ability.getId(), ability, this.getId());
            castLateEvent.setZone(fromZone);
            if (isPlaySpell && game.getContinuousEffects().preventedByRuleModification(
                    castLateEvent, ability, game, true)) {
                continue;
            }

            ApprovingObject approvingObject;
            if ((isPlaySpell || isPlayLand) && (fromZone != Zone.BATTLEFIELD)) {
                // play hand from non hand zone (except battlefield - you can't play already played permanents)
                approvingObject = game.getContinuousEffects().asThough(object.getId(),
                        AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, ability, this.getId(), game);
            } else {
                // other abilities from direct zones
                approvingObject = null;
            }

            boolean canActivateAsHandZone = approvingObject != null
                    || (fromZone == Zone.GRAVEYARD && canPlayCardsFromGraveyard());
            boolean possibleToPlay = canActivateAsHandZone
                    && ability.getZone().match(Zone.HAND)
                    && (isPlaySpell || isPlayLand);

            // spell/hand abilities (play from all zones)
            // need permitingObject or canPlayCardsFromGraveyard
            // zone's abilities (play from specific zone)
            // no need in permitingObject
            if (fromZone != Zone.ALL && ability.getZone().match(fromZone)) {
                possibleToPlay = true;
            }

            if (!possibleToPlay) {
                continue;
            }

            // direct mode (with original controller)
            ActivatedAbility playAbility = findActivatedAbilityFromPlayable(object, availableMana, ability, game);
            if (playAbility != null && !output.contains(playAbility)) {
                output.add(playAbility);
                continue;
            }

            // from non hand mode (with affected controller)
            if (canActivateAsHandZone && ability.getControllerId() != this.getId()) {
                UUID savedControllerId = ability.getControllerId();
                ability.setControllerId(this.getId());
                try {
                    playAbility = findActivatedAbilityFromPlayable(object, availableMana, ability, game);
                    if (playAbility != null && !output.contains(playAbility)) {
                        output.add(playAbility);
                    }
                } finally {
                    ability.setControllerId(savedControllerId);
                }
            }
        }
    }

    @Override
    public List<ActivatedAbility> getPlayable(Game game, boolean hidden) {
        return getPlayable(game, hidden, Zone.ALL, true);
    }

    /**
     * Returns a list of all available spells and abilities the player can
     * currently cast/activate with his available resources
     *
     * @param game
     * @param hidden also from hidden objects (e.g. turned face down cards ?)
     * @param fromZone of objects from which zone (ALL = from all zones)
     * @param hideDuplicatedAbilities if equal abilities exist return only the
     * first instance
     * @return
     */
    public List<ActivatedAbility> getPlayable(Game game, boolean hidden, Zone fromZone, boolean hideDuplicatedAbilities) {
        List<ActivatedAbility> playable = new ArrayList<>();
        if (shouldSkipGettingPlayable(game)) {
            return playable;
        }

        boolean previousState = game.inCheckPlayableState();
        game.setCheckPlayableState(true);
        try {
            ManaOptions availableMana = getManaAvailable(game); // get available mana options (mana pool and conditional mana added (but conditional still lose condition))
            boolean fromAll = fromZone.equals(Zone.ALL);
            if (hidden && (fromAll || fromZone == Zone.HAND)) {
                for (Card card : hand.getCards(game)) {
                    for (Ability ability : card.getAbilities(game)) { // gets this activated ability from hand? (Morph?)
                        if (ability.getZone().match(Zone.HAND)) {
                            boolean isPlaySpell = (ability instanceof SpellAbility);
                            boolean isPlayLand = (ability instanceof PlayLandAbility);

                            // play land restrictions
                            if (isPlayLand && game.getContinuousEffects().preventedByRuleModification(
                                    GameEvent.getEvent(GameEvent.EventType.PLAY_LAND, ability.getSourceId(),
                                            ability, this.getId()), ability, game, true)) {
                                continue;
                            }
                            // cast spell restrictions 1
                            GameEvent castEvent = GameEvent.getEvent(GameEvent.EventType.CAST_SPELL,
                                    ability.getId(), ability, this.getId());
                            castEvent.setZone(fromZone);
                            if (isPlaySpell && game.getContinuousEffects().preventedByRuleModification(
                                    castEvent, ability, game, true)) {
                                continue;
                            }
                            // cast spell restrictions 2
                            GameEvent castLateEvent = GameEvent.getEvent(GameEvent.EventType.CAST_SPELL_LATE,
                                    ability.getId(), ability, this.getId());
                            castLateEvent.setZone(fromZone);
                            if (isPlaySpell && game.getContinuousEffects().preventedByRuleModification(
                                    castLateEvent, ability, game, true)) {
                                continue;
                            }

                            ActivatedAbility playAbility = findActivatedAbilityFromPlayable(card, availableMana, ability, game);
                            if (playAbility != null && !playable.contains(playAbility)) {
                                playable.add(playAbility);
                            }
                        }
                    }
                }
            }

            if (fromAll || fromZone == Zone.GRAVEYARD) {
                for (UUID playerId : game.getState().getPlayersInRange(getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player == null) {
                        continue;
                    }
                    for (Card card : player.getGraveyard().getCards(game)) {
                        getPlayableFromObjectAll(game, Zone.GRAVEYARD, card, availableMana, playable);
                    }
                }
            }

            if (fromAll || fromZone == Zone.EXILED) {
                for (ExileZone exile : game.getExile().getExileZones()) {
                    for (Card card : exile.getCards(game)) {
                        getPlayableFromObjectAll(game, Zone.EXILED, card, availableMana, playable);
                    }
                }
            }

            // check to play revealed cards
            if (fromAll) {
                for (Cards revealedCards : game.getState().getRevealed().values()) {
                    for (Card card : revealedCards.getCards(game)) {
                        // revealed cards can be from any zones
                        getPlayableFromObjectAll(game, game.getState().getZone(card.getId()), card, availableMana, playable);
                    }
                }
            }

            // outside cards
            if (fromAll || fromZone == Zone.OUTSIDE) {
                // companion cards
                for (Cards companionCards : game.getState().getCompanion().values()) {
                    for (Card card : companionCards.getCards(game)) {
                        getPlayableFromObjectAll(game, Zone.OUTSIDE, card, availableMana, playable);
                    }
                }

                // sideboard cards (example: Wish)
                for (UUID sideboardCardId : this.getSideboard()) {
                    Card sideboardCard = game.getCard(sideboardCardId);
                    if (sideboardCard != null) {
                        getPlayableFromObjectAll(game, Zone.OUTSIDE, sideboardCard, availableMana, playable);
                    }
                }
            }

            // check if it's possible to play the top card of a library
            if (fromAll || fromZone == Zone.LIBRARY) {
                for (UUID playerInRangeId : game.getState().getPlayersInRange(getId(), game)) {
                    Player player = game.getPlayer(playerInRangeId);
                    if (player != null && player.getLibrary().hasCards()) {
                        Card card = player.getLibrary().getFromTop(game);
                        if (card != null) {
                            getPlayableFromObjectAll(game, Zone.LIBRARY, card, availableMana, playable);
                        }
                    }
                }
            }

            // check the hand zone (Sen Triplets)
            // TODO: remove direct hand check (reveal fix in Sen Triplets)?
            // human games: cards from opponent's hand must be revealed before play
            // AI games: computer can see and play cards from opponent's hand without reveal
            if (fromAll || fromZone == Zone.HAND) {
                for (UUID playerInRangeId : game.getState().getPlayersInRange(getId(), game)) {
                    Player player = game.getPlayer(playerInRangeId);
                    if (player != null && !player.getHand().isEmpty()) {
                        for (Card card : player.getHand().getCards(game)) {
                            if (card != null) {
                                getPlayableFromObjectAll(game, Zone.HAND, card, availableMana, playable);
                            }
                        }
                    }
                }
            }

            // eliminate duplicate activated abilities (uses for AI plays)
            Map<String, ActivatedAbility> activatedUnique = new HashMap<>();
            List<ActivatedAbility> activatedAll = new ArrayList<>();

            // activated abilities from battlefield objects
            if (fromAll || fromZone == Zone.BATTLEFIELD) {
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
                    boolean canUseActivated = permanent.canUseActivatedAbilities(game);
                    List<ActivatedAbility> currentPlayable = new ArrayList<>();
                    getPlayableFromObjectAll(game, Zone.BATTLEFIELD, permanent, availableMana, currentPlayable);
                    for (ActivatedAbility ability : currentPlayable) {
                        if (ability instanceof SpecialAction || canUseActivated) {
                            activatedUnique.putIfAbsent(ability.toString(), ability);
                            activatedAll.add(ability);
                        }
                    }
                }
            }

            // activated abilities from stack objects
            if (fromAll || fromZone == Zone.STACK) {
                for (StackObject stackObject : game.getState().getStack()) {
                    List<ActivatedAbility> currentPlayable = new ArrayList<>();
                    getPlayableFromObjectAll(game, Zone.STACK, stackObject, availableMana, currentPlayable);
                    for (ActivatedAbility ability : currentPlayable) {
                        activatedUnique.put(ability.toString(), ability);
                        activatedAll.add(ability);
                    }
                }
            }

            // activated abilities from objects in the command zone (emblems or commanders)
            if (fromAll || fromZone == Zone.COMMAND) {
                for (CommandObject commandObject : game.getState().getCommand()) {
                    List<ActivatedAbility> currentPlayable = new ArrayList<>();
                    getPlayableFromObjectAll(game, Zone.COMMAND, commandObject, availableMana, currentPlayable);
                    for (ActivatedAbility ability : currentPlayable) {
                        activatedUnique.put(ability.toString(), ability);
                        activatedAll.add(ability);
                    }
                }
            }

            if (hideDuplicatedAbilities) {
                playable.addAll(activatedUnique.values());
            } else {
                playable.addAll(activatedAll);
            }
        } finally {
            game.setCheckPlayableState(previousState);
        }

        return playable;
    }

    /**
     * Creates a list of card ids that are currently playable.<br>
     * Used to mark the playable cards in GameView Also contains number of
     * playable abilities for that object (it's just info, server decides to
     * show choose dialog or not)
     *
     * @param game
     * @return A Set of cardIds that are playable and amount of playable
     * abilities
     */
    @Override
    public PlayableObjectsList getPlayableObjects(Game game, Zone zone) {
        // collect abilities per object
        List<ActivatedAbility> playableAbilities = getPlayable(game, true, zone, false); // do not hide duplicated abilities/cards
        Map<UUID, List<ActivatedAbility>> playableObjects = new HashMap<>();
        for (ActivatedAbility ability : playableAbilities) {
            if (ability.getSourceId() != null) {

                // normal card
                putToPlayableObjects(playableObjects, ability.getSourceId(), ability);

                // main card - must be marked playable in GUI
                Card card = game.getCard(ability.getSourceId());
                if (card != null && card.getMainCard().getId() != card.getId()) {
                    putToPlayableObjects(playableObjects, card.getMainCard().getId(), ability);
                }

                // spell on stack - can have activated abilities,
                // so mark it as playable too (users must able to clicks on stack objects)
                // example: Lightning Storm
                Spell spell = game.getSpell(ability.getSourceId());
                if (spell != null) {
                    putToPlayableObjects(playableObjects, spell.getId(), ability);
                }
            }
        }

        // collect stats
        PlayableObjectsList playableObjectsList = new PlayableObjectsList(playableObjects);
        return playableObjectsList;
    }

    private void putToPlayableObjects(Map<UUID, List<ActivatedAbility>> playableObjects, UUID objectId, ActivatedAbility ability) {
        if (!playableObjects.containsKey(objectId)) {
            playableObjects.put(objectId, new ArrayList<>());
        }
        playableObjects.get(objectId).add(ability);
    }

    /**
     * Skip "silent" phase step when players are not allowed to cast anything.
     * E.g. players can't play or cast anything during declaring attackers.
     *
     * @param game
     * @return
     */
    private boolean shouldSkipGettingPlayable(Game game) {
        if (game.getStep() == null) { // happens at the start of the game
            return true;
        }
        for (Entry<PhaseStep, Step.StepPart> phaseStep : silentPhaseSteps.entrySet()) {
            if (game.getPhase() != null
                    && game.getPhase().getStep() != null
                    && phaseStep.getKey() == game.getPhase().getStep().getType()) {
                if (phaseStep.getValue() == null
                        || phaseStep.getValue() == game.getPhase().getStep().getStepPart()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Only used for AIs
     *
     * @param ability
     * @param game
     * @return
     */
    @Override
    public List<Ability> getPlayableOptions(Ability ability, Game game) {
        List<Ability> options = new ArrayList<>();
        if (ability.isModal()) {
            addModeOptions(options, ability, game);
        } else if (!ability.getTargets().getUnchosen().isEmpty()) {
            // TODO: Handle other variable costs than mana costs
            if (!ability.getManaCosts().getVariableCosts().isEmpty()) {
                addVariableXOptions(options, ability, 0, game);
            } else {
                addTargetOptions(options, ability, 0, game);
            }
        } else if (!ability.getCosts().getTargets().getUnchosen().isEmpty()) {
            addCostTargetOptions(options, ability, 0, game);
        }

        return options;
    }

    private void addModeOptions(List<Ability> options, Ability option, Game game) {
        // TODO: Support modal spells with more than one selectable mode
        for (Mode mode : option.getModes().values()) {
            Ability newOption = option.copy();
            newOption.getModes().clearSelectedModes();
            newOption.getModes().addSelectedMode(mode.getId());
            newOption.getModes().setActiveMode(mode);
            if (!newOption.getTargets().getUnchosen().isEmpty()) {
                if (!newOption.getManaCosts().getVariableCosts().isEmpty()) {
                    addVariableXOptions(options, newOption, 0, game);
                } else {
                    addTargetOptions(options, newOption, 0, game);
                }
            } else if (!newOption.getCosts().getTargets().getUnchosen().isEmpty()) {
                addCostTargetOptions(options, newOption, 0, game);
            } else {
                options.add(newOption);
            }
        }
    }

    protected void addVariableXOptions(List<Ability> options, Ability option, int targetNum, Game game) {
        addTargetOptions(options, option, targetNum, game);
    }

    protected void addTargetOptions(List<Ability> options, Ability option, int targetNum, Game game) {
        for (Target target : option.getTargets().getUnchosen().get(targetNum).getTargetOptions(option, game)) {
            Ability newOption = option.copy();
            if (target instanceof TargetAmount) {
                for (UUID targetId : target.getTargets()) {
                    int amount = target.getTargetAmount(targetId);
                    newOption.getTargets().get(targetNum).addTarget(targetId, amount, newOption, game, true);
                }
            } else {
                for (UUID targetId : target.getTargets()) {
                    newOption.getTargets().get(targetNum).addTarget(targetId, newOption, game, true);
                }
            }
            if (targetNum < option.getTargets().size() - 2) {
                addTargetOptions(options, newOption, targetNum + 1, game);
            } else if (!option.getCosts().getTargets().isEmpty()) {
                addCostTargetOptions(options, newOption, 0, game);
            } else {
                options.add(newOption);
            }
        }
    }

    private void addCostTargetOptions(List<Ability> options, Ability option, int targetNum, Game game) {
        for (UUID targetId : option.getCosts().getTargets().get(targetNum).possibleTargets(option.getSourceId(), playerId, game)) {
            Ability newOption = option.copy();
            newOption.getCosts().getTargets().get(targetNum).addTarget(targetId, option, game, true);
            if (targetNum < option.getCosts().getTargets().size() - 1) {
                addCostTargetOptions(options, newOption, targetNum + 1, game);
            } else {
                options.add(newOption);
            }
        }
    }

    @Override
    public boolean isTestsMode() {
        return isTestMode;
    }

    @Override
    public void setTestMode(boolean value) {
        this.isTestMode = value;
    }

    @Override
    public boolean isTopCardRevealed() {
        return topCardRevealed;
    }

    @Override
    public void setTopCardRevealed(boolean topCardRevealed) {
        this.topCardRevealed = topCardRevealed;
    }

    @Override
    public UserData getUserData() {
        return this.userData;
    }

    public UserData getControllingPlayersUserData(Game game) {
        if (!isGameUnderControl()) {
            Player player = game.getPlayer(getTurnControlledBy());
            if (player.isHuman()) {
                return player.getUserData();
            }
        }
        return this.userData;
    }

    @Override
    public void setUserData(UserData userData) {
        this.userData = userData;
        getManaPool().setAutoPayment(userData.isManaPoolAutomatic());
        getManaPool().setAutoPaymentRestricted(userData.isManaPoolAutomaticRestricted());
    }

    @Override
    public void addAction(String action
    ) {
        // do nothing
    }

    @Override
    public int getActionCount() {
        return 0;
    }

    @Override
    public void setAllowBadMoves(boolean allowBadMoves) {
        // do nothing
    }

    @Override
    public boolean canPayLifeCost(Ability ability) {
        if (!canPayLifeCost
                && (AbilityType.ACTIVATED.equals(ability.getAbilityType())
                || AbilityType.SPELL.equals(ability.getAbilityType()))) {
            return false;
        }
        return isLifeTotalCanChange();
    }

    @Override
    public boolean getCanPayLifeCost() {
        return canPayLifeCost;
    }

    @Override
    public void setCanPayLifeCost(boolean canPayLifeCost) {
        this.canPayLifeCost = canPayLifeCost;
    }

    @Override
    public boolean canPaySacrificeCost(Permanent permanent, Ability source, UUID controllerId, Game game) {
        return sacrificeCostFilter == null || !sacrificeCostFilter.match(permanent, source.getSourceId(), controllerId, game);
    }

    @Override
    public void setCanPaySacrificeCostFilter(FilterPermanent filter
    ) {
        this.sacrificeCostFilter = filter;
    }

    @Override
    public FilterPermanent getSacrificeCostFilter() {
        return sacrificeCostFilter;
    }

    @Override
    public boolean canLoseByZeroOrLessLife() {
        return loseByZeroOrLessLife;
    }

    @Override
    public void setLoseByZeroOrLessLife(boolean loseByZeroOrLessLife) {
        this.loseByZeroOrLessLife = loseByZeroOrLessLife;
    }

    @Override
    public boolean canPlayCardsFromGraveyard() {
        return canPlayCardsFromGraveyard;
    }

    @Override
    public void setPlayCardsFromGraveyard(boolean playCardsFromGraveyard) {
        this.canPlayCardsFromGraveyard = playCardsFromGraveyard;
    }

    @Override
    public void setDrawsOnOpponentsTurn(boolean drawsOnOpponentsTurn) {
        this.drawsOnOpponentsTurn = drawsOnOpponentsTurn;
    }

    @Override
    public boolean isDrawsOnOpponentsTurn() {
        return drawsOnOpponentsTurn;
    }

    @Override
    public boolean autoLoseGame() {
        return false;
    }

    @Override
    public void becomesActivePlayer() {
        this.passedAllTurns = false;
        this.passedUntilEndStepBeforeMyTurn = false;
        this.turns++;
    }

    @Override
    public int getTurns() {
        return turns;
    }

    @Override
    public int getStoredBookmark() {
        return storedBookmark;
    }

    @Override
    public void setStoredBookmark(int storedBookmark) {
        this.storedBookmark = storedBookmark;
    }

    @Override
    public synchronized void resetStoredBookmark(Game game) {
        if (this.storedBookmark != -1) {
            game.removeBookmark(this.storedBookmark);
        }
        setStoredBookmark(-1);
    }

    @Override
    public boolean lookAtFaceDownCard(Card card, Game game, int abilitiesToActivate) {
        if (null != game.getContinuousEffects().asThough(card.getId(),
                AsThoughEffectType.LOOK_AT_FACE_DOWN, null, this.getId(), game)) {
            // two modes: look at the card or do not look and activate other abilities
            String lookMessage = "Look at " + card.getIdName();
            String lookYes = "Yes, look at the card";
            String lookNo = "No, play/activate the card/ability";
            if (chooseUse(Outcome.Benefit, lookMessage, "", lookYes, lookNo, null, game)) {
                Cards cards = new CardsImpl(card);
                this.lookAtCards(getName() + " - " + card.getIdName() + " - "
                        + CardUtil.sdf.format(System.currentTimeMillis()), cards, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public void setPriorityTimeLeft(int timeLeft
    ) {
        priorityTimeLeft = timeLeft;
    }

    @Override
    public int getPriorityTimeLeft() {
        return priorityTimeLeft;
    }

    @Override
    public boolean hasQuit() {
        return quit;
    }

    @Override
    public boolean hasTimerTimeout() {
        return timerTimeout;
    }

    @Override
    public boolean hasIdleTimeout() {
        return idleTimeout;
    }

    @Override
    public void setReachedNextTurnAfterLeaving(boolean reachedNextTurnAfterLeaving) {
        this.reachedNextTurnAfterLeaving = reachedNextTurnAfterLeaving;
    }

    @Override
    public boolean hasReachedNextTurnAfterLeaving() {
        return reachedNextTurnAfterLeaving;
    }

    @Override
    public boolean canJoinTable(Table table
    ) {
        return !table.userIsBanned(name);
    }

    @Override
    public void addCommanderId(UUID commanderId
    ) {
        this.commandersIds.add(commanderId);
    }

    @Override
    public Set<UUID> getCommandersIds() {
        return this.commandersIds;
    }

    @Override
    public boolean moveCards(Card card, Zone toZone, Ability source, Game game) {
        return moveCards(card, toZone, source, game, false, false, false, null);
    }

    @Override
    public boolean moveCards(Card card, Zone toZone, Ability source, Game game, boolean tapped, boolean faceDown, boolean byOwner, List<UUID> appliedEffects) {
        Set<Card> cardList = new HashSet<>();
        if (card != null) {
            cardList.add(card);
        }
        return moveCards(cardList, toZone, source, game, tapped, faceDown, byOwner, appliedEffects);
    }

    @Override
    public boolean moveCards(Cards cards, Zone toZone, Ability source, Game game) {
        return moveCards(cards.getCards(game), toZone, source, game);
    }

    @Override
    public boolean moveCards(Set<? extends Card> cards, Zone toZone,
            Ability source, Game game
    ) {
        return moveCards(cards, toZone, source, game, false, false, false, null);
    }

    @Override
    public boolean moveCards(Set<? extends Card> cards, Zone toZone, Ability source, Game game, boolean tapped, boolean faceDown, boolean byOwner, List<UUID> appliedEffects) {
        if (cards.isEmpty()) {
            return true;
        }
        Set<Card> successfulMovedCards = new LinkedHashSet<>();
        Zone fromZone = null;
        switch (toZone) {
            case GRAVEYARD:
                fromZone = game.getState().getZone(cards.iterator().next().getId());
                successfulMovedCards = moveCardsToGraveyardWithInfo(cards, source, game, fromZone);
                return !successfulMovedCards.isEmpty();
            case BATTLEFIELD: // new logic that does not yet add the permanents to battlefield while replacement effects are handled
                List<ZoneChangeInfo> infoList = new ArrayList<>();
                for (Card card : cards) {
                    fromZone = game.getState().getZone(card.getId());
                    ZoneChangeEvent event = new ZoneChangeEvent(card.getId(), source,
                            byOwner ? card.getOwnerId() : getId(), fromZone, Zone.BATTLEFIELD, appliedEffects);
                    infoList.add(new ZoneChangeInfo.Battlefield(event, faceDown, tapped, source));
                }
                infoList = ZonesHandler.moveCards(infoList, game, source);
                for (ZoneChangeInfo info : infoList) {
                    Permanent permanent = game.getPermanent(info.event.getTargetId());
                    if (permanent != null) {
                        successfulMovedCards.add(permanent);
                        if (!game.isSimulation()) {
                            Player eventPlayer = game.getPlayer(info.event.getPlayerId());
                            if (eventPlayer != null && fromZone != null) {
                                game.informPlayers(eventPlayer.getLogName() + " puts "
                                        + (info.faceDown ? "a card face down " : permanent.getLogName()) + " from "
                                        + fromZone.toString().toLowerCase(Locale.ENGLISH) + " onto the Battlefield"
                                        + CardUtil.getSourceLogName(game, source, permanent.getId()));
                            }
                        }
                    }
                }
                // TODO: must be replaced by game.getState().processAction(game), see isInUseableZoneDiesTrigger comments
                //  about short living LKI problem
                //game.getState().processAction(game);
                game.applyEffects();
                break;
            case HAND:
                for (Card card : cards) {
                    fromZone = game.getState().getZone(card.getId());
                    boolean hideCard = fromZone == Zone.LIBRARY
                            || (card.isFaceDown(game)
                            && fromZone != Zone.STACK
                            && fromZone != Zone.BATTLEFIELD);
                    if (moveCardToHandWithInfo(card, source, game, !hideCard)) {
                        successfulMovedCards.add(card);
                    }
                }
                break;
            case EXILED:
                for (Card card : cards) {
                    fromZone = game.getState().getZone(card.getId());
                    boolean withName = (fromZone == Zone.BATTLEFIELD
                            || fromZone == Zone.STACK)
                            || !card.isFaceDown(game);
                    if (moveCardToExileWithInfo(card, null, "", source, game, fromZone, withName)) {
                        successfulMovedCards.add(card);
                    }
                }
                break;
            case LIBRARY:
                for (Card card : cards) {
                    if (card instanceof Spell) {
                        fromZone = game.getState().getZone(((Spell) card).getSourceId());
                    } else {
                        fromZone = game.getState().getZone(card.getId());
                    }
                    boolean hideCard = fromZone == Zone.HAND || fromZone == Zone.LIBRARY;
                    if (moveCardToLibraryWithInfo(card, source, game, fromZone, true, !hideCard)) {
                        successfulMovedCards.add(card);
                    }
                }
                break;
            case COMMAND:
                for (Card card : cards) {
                    fromZone = game.getState().getZone(card.getId());
                    if (moveCardToCommandWithInfo(card, source, game, fromZone)) {
                        successfulMovedCards.add(card);
                    }
                }
                break;
            case OUTSIDE:
                for (Card card : cards) {
                    if (card instanceof Permanent) {
                        game.getBattlefield().removePermanent(card.getId());
                        ZoneChangeEvent event = new ZoneChangeEvent((Permanent) card, source,
                                byOwner ? card.getOwnerId() : getId(), Zone.BATTLEFIELD, Zone.OUTSIDE, appliedEffects);
                        game.fireEvent(event);
                    }
                }
                break;
            default:
                throw new UnsupportedOperationException("to Zone" + toZone + " not supported yet");
        }
        return !successfulMovedCards.isEmpty();
    }

    @Override
    public boolean moveCardsToExile(Card card, Ability source, Game game, boolean withName, UUID exileId, String exileZoneName) {
        Set<Card> cards = new HashSet<>();
        if (card != null) {
            cards.add(card);
        }
        return moveCardsToExile(cards, source, game, withName, exileId, exileZoneName);
    }

    @Override
    public boolean moveCardsToExile(Set<Card> cards, Ability source, Game game, boolean withName, UUID exileId, String exileZoneName) {
        if (cards.isEmpty()) {
            return true;
        }
        boolean result = false;
        for (Card card : cards) {
            Zone fromZone = game.getState().getZone(card.getId());
            result |= moveCardToExileWithInfo(card, exileId, exileZoneName, source, game, fromZone, withName);
        }
        return result;
    }

    @Override
    public boolean moveCardToHandWithInfo(Card card, Ability source, Game game, boolean withName) {
        boolean result = false;
        Zone fromZone = game.getState().getZone(card.getId());
        if (fromZone == Zone.BATTLEFIELD && !(card instanceof Permanent)) {
            card = game.getPermanent(card.getId());
        }
        if (card.moveToZone(Zone.HAND, source, game, false)) {
            if (card instanceof PermanentCard && game.getCard(card.getId()) != null) {
                card = game.getCard(card.getId());
            }
            if (!game.isSimulation()) {
                game.informPlayers(getLogName() + " puts "
                        + (withName ? card.getLogName() : (card.isFaceDown(game) ? "a face down card" : "a card"))
                        + " from " + fromZone.toString().toLowerCase(Locale.ENGLISH) + ' '
                        + (card.isOwnedBy(this.getId()) ? "into their hand" : "into its owner's hand"
                        + CardUtil.getSourceLogName(game, source, card.getId()))
                );
            }
            result = true;
        }
        return result;
    }

    @Override
    public Set<Card> moveCardsToGraveyardWithInfo(Set<? extends Card> allCards, Ability source, Game game, Zone fromZone) {
        Set<Card> movedCards = new LinkedHashSet<>();
        while (!allCards.isEmpty()) {
            // identify cards from one owner
            Cards cards = new CardsImpl();
            UUID ownerId = null;
            for (Iterator<? extends Card> it = allCards.iterator(); it.hasNext();) {
                Card card = it.next();
                if (cards.isEmpty()) {
                    ownerId = card.getOwnerId();
                }
                if (card.isOwnedBy(ownerId)) {
                    it.remove();
                    cards.add(card);
                }
            }
            // move cards to graveyard in order the owner decides
            if (!cards.isEmpty()) {
                Player choosingPlayer = this;
                if (!Objects.equals(ownerId, this.getId())) {
                    choosingPlayer = game.getPlayer(ownerId);
                }
                if (choosingPlayer == null) {
                    continue;
                }
                boolean chooseOrder = false;
                if (userData.askMoveToGraveOrder()) {
                    if (cards.size() > 1) {
                        chooseOrder = choosingPlayer.chooseUse(Outcome.Neutral,
                                "Choose the order in which the cards go to the graveyard?", source, game);
                    }
                }
                if (chooseOrder) {
                    TargetCard target = new TargetCard(fromZone,
                            new FilterCard("card to put on the top of your graveyard (last one chosen will be topmost)"));
                    target.setRequired(true);
                    while (choosingPlayer.canRespond() && cards.size() > 1) {
                        choosingPlayer.chooseTarget(Outcome.Neutral, cards, target, source, game);
                        UUID targetObjectId = target.getFirstTarget();
                        Card card = cards.get(targetObjectId, game);
                        cards.remove(targetObjectId);
                        if (card != null) {
                            fromZone = game.getState().getZone(card.getId());
                            if (choosingPlayer.moveCardToGraveyardWithInfo(card, source, game, fromZone)) {
                                movedCards.add(card);
                            }
                        }
                        target.clearChosen();
                    }
                    if (cards.size() == 1) {
                        Card card = cards.getCards(game).iterator().next();
                        if (card != null && choosingPlayer.moveCardToGraveyardWithInfo(card, source, game, fromZone)) {
                            movedCards.add(card);
                        }
                    }
                } else {
                    for (Card card : cards.getCards(game)) {
                        if (choosingPlayer.moveCardToGraveyardWithInfo(card, source, game, fromZone)) {
                            movedCards.add(card);
                        }
                    }
                }
            }
        }
        return movedCards;
    }

    @Override
    public boolean moveCardToGraveyardWithInfo(Card card, Ability source, Game game, Zone fromZone) {
        if (card == null) {
            return false;
        }
        boolean result = false;
        if (card.moveToZone(Zone.GRAVEYARD, source, game, false)) {
            if (!game.isSimulation()) {
                if (card instanceof PermanentCard && game.getCard(card.getId()) != null) {
                    card = game.getCard(card.getId());
                }
                StringBuilder sb = new StringBuilder(this.getLogName())
                        .append(" puts ").append(card.getLogName()).append(' ').append(card.isCopy() ? "(Copy) " : "")
                        .append(fromZone != null ? "from " + fromZone.toString().toLowerCase(Locale.ENGLISH) + ' ' : "");
                if (card.isOwnedBy(getId())) {
                    sb.append("into their graveyard");
                } else {
                    sb.append("it into its owner's graveyard");
                }
                sb.append(CardUtil.getSourceLogName(game, source, card.getId()));
                game.informPlayers(sb.toString());
            }
            result = true;
        }
        return result;
    }

    @Override
    public boolean moveCardToLibraryWithInfo(Card card, Ability source, Game game, Zone fromZone, boolean toTop, boolean withName) {
        if (card == null) {
            return false;
        }
        boolean result = false;
        if (card.moveToZone(Zone.LIBRARY, source, game, toTop)) {
            if (!game.isSimulation()) {
                if (card instanceof PermanentCard && game.getCard(card.getId()) != null) {
                    card = game.getCard(card.getId());
                }
                StringBuilder sb = new StringBuilder(this.getLogName())
                        .append(" puts ").append(withName ? card.getLogName() : "a card").append(' ');
                if (fromZone != null) {
                    sb.append("from ").append(fromZone.toString().toLowerCase(Locale.ENGLISH)).append(' ');
                }
                sb.append("to the ").append(toTop ? "top" : "bottom");
                if (card.isOwnedBy(getId())) {
                    sb.append(" of their library");
                } else {
                    Player player = game.getPlayer(card.getOwnerId());
                    if (player != null) {
                        sb.append(" of ").append(player.getLogName()).append("'s library");
                    }
                }
                sb.append(CardUtil.getSourceLogName(game, source, card.getId()));
                game.informPlayers(sb.toString());
            }
            result = true;
        }
        return result;
    }

    @Override
    public boolean moveCardToCommandWithInfo(Card card, Ability source, Game game, Zone fromZone) {
        if (card == null) {
            return false;
        }
        boolean result = false;
        if (card.moveToZone(Zone.COMMAND, source, game, true)) {
            if (!game.isSimulation()) {
                if (card instanceof PermanentCard && game.getCard(card.getId()) != null) {
                    card = game.getCard(card.getId());
                }
                StringBuilder sb = new StringBuilder(this.getLogName())
                        .append(" puts ").append(card.getLogName()).append(' ');
                if (fromZone != null) {
                    sb.append("from ").append(fromZone.toString().toLowerCase(Locale.ENGLISH)).append(' ');
                }
                if (card.isOwnedBy(getId())) {
                    sb.append(" to their command zone");
                } else {
                    Player player = game.getPlayer(card.getOwnerId());
                    if (player != null) {
                        sb.append(" to ").append(player.getLogName()).append("'s command zone");
                    }
                }
                sb.append(CardUtil.getSourceLogName(game, source, card.getId()));
                game.informPlayers(sb.toString());
            }
            result = true;
        }
        return result;
    }

    @Override
    public boolean moveCardToExileWithInfo(Card card, UUID exileId, String exileName, Ability source, Game game, Zone fromZone, boolean withName) {
        if (card == null) {
            return false;
        }
        boolean result = false;
        if (card.moveToExile(exileId, exileName, source, game)) {
            if (!game.isSimulation()) {
                if (card instanceof PermanentCard) {
                    // in case it's face down or name was changed by copying from other permanent
                    Card basicCard = game.getCard(card.getId());
                    if (basicCard != null) {
                        card = basicCard;
                    }
                } else if (card instanceof Spell) {
                    final Spell spell = (Spell) card;
                    if (spell.isCopy()) {
                        // copied spell, only remove from stack
                        game.getStack().remove(spell, game);
                    }
                }
                if (Zone.EXILED.equals(game.getState().getZone(card.getId()))) { // only if target zone was not replaced
                    game.informPlayers(this.getLogName() + " moves " + (withName ? card.getLogName()
                            + (card.isCopy() ? " (Copy)" : "") : "a card face down") + ' '
                            + (fromZone != null ? "from " + fromZone.toString().toLowerCase(Locale.ENGLISH)
                                    + ' ' : "") + "to the exile zone" + CardUtil.getSourceLogName(game, source, card.getId()));
                }

            }
            result = true;
        }
        return result;
    }

    @Override
    public Cards millCards(int toMill, Ability source, Game game) {
        GameEvent event = GameEvent.getEvent(GameEvent.EventType.MILL_CARDS, getId(), source, getId(), toMill);
        if (game.replaceEvent(event)) {
            return new CardsImpl();
        }
        Cards cards = new CardsImpl(this.getLibrary().getTopCards(game, event.getAmount()));
        this.moveCards(cards, Zone.GRAVEYARD, source, game);
        for (Card card : cards.getCards(game)) {
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.MILLED_CARD, card.getId(), source, getId()));
        }
        return cards;
    }

    @Override
    public boolean hasOpponent(UUID playerToCheckId, Game game) {
        return !this.getId().equals(playerToCheckId)
                && game.isOpponent(this, playerToCheckId)
                && getInRange().contains(playerToCheckId);
    }

    @Override
    public void cleanUpOnMatchEnd() {

    }

    @Override
    public boolean getPassedAllTurns() {
        return passedAllTurns;
    }

    @Override
    public boolean getPassedUntilNextMain() {
        return passedUntilNextMain;
    }

    @Override
    public boolean getPassedUntilEndOfTurn() {
        return passedUntilEndOfTurn;
    }

    @Override
    public boolean getPassedTurn() {
        return passedTurn;
    }

    @Override
    public boolean getPassedUntilStackResolved() {
        return passedUntilStackResolved;
    }

    @Override
    public boolean getPassedUntilEndStepBeforeMyTurn() {
        return passedUntilEndStepBeforeMyTurn;
    }

    @Override
    public AbilityType getJustActivatedType() {
        return justActivatedType;
    }

    @Override
    public void setJustActivatedType(AbilityType justActivatedType
    ) {
        this.justActivatedType = justActivatedType;
    }

    @Override
    public void revokePermissionToSeeHandCards() {
        usersAllowedToSeeHandCards.clear();
    }

    @Override
    public void addPermissionToShowHandCards(UUID watcherUserId
    ) {
        usersAllowedToSeeHandCards.add(watcherUserId);
    }

    @Override
    public boolean isPlayerAllowedToRequestHand(UUID gameId, UUID requesterPlayerId) {
        return userData.isAllowRequestHandToPlayer(gameId, requesterPlayerId);
    }

    @Override
    public void addPlayerToRequestedHandList(UUID gameId, UUID requesterPlayerId) {
        userData.addPlayerToRequestedHandList(gameId, requesterPlayerId);
    }

    @Override
    public boolean hasUserPermissionToSeeHand(UUID userId
    ) {
        return usersAllowedToSeeHandCards.contains(userId);
    }

    @Override
    public Set<UUID> getUsersAllowedToSeeHandCards() {
        return usersAllowedToSeeHandCards;
    }

    @Override
    public void setMatchPlayer(MatchPlayer matchPlayer
    ) {
        this.matchPlayer = matchPlayer;
    }

    @Override
    public MatchPlayer getMatchPlayer() {
        return matchPlayer;
    }

    @Override
    public void abortReset() {
        abort = false;
    }

    @Override
    public void signalPlayerConcede() {

    }

    @Override
    public boolean scry(int value, Ability source, Game game) {
        GameEvent event = new GameEvent(GameEvent.EventType.SCRY, getId(), source, getId(), value, true);
        if (game.replaceEvent(event)) {
            return false;
        }
        game.informPlayers(getLogName() + " scries " + event.getAmount() + CardUtil.getSourceLogName(game, source));
        Cards cards = new CardsImpl();
        cards.addAll(getLibrary().getTopCards(game, event.getAmount()));
        if (!cards.isEmpty()) {
            TargetCard target = new TargetCard(0, cards.size(), Zone.LIBRARY,
                    new FilterCard("card" + (cards.size() == 1 ? "" : "s")
                            + " to PUT on the BOTTOM of your library (Scry)"));
            chooseTarget(Outcome.Benefit, cards, target, source, game);
            putCardsOnBottomOfLibrary(new CardsImpl(target.getTargets()), game, source, true);
            cards.removeAll(target.getTargets());
            putCardsOnTopOfLibrary(cards, game, source, true);
        }
        game.fireEvent(new GameEvent(GameEvent.EventType.SCRIED, getId(), source, getId(), event.getAmount(), true));
        return true;
    }

    @Override
    public boolean surveil(int value, Ability source, Game game) {
        GameEvent event = new GameEvent(GameEvent.EventType.SURVEIL, getId(), source, getId(), value, true);
        if (game.replaceEvent(event)) {
            return false;
        }
        game.informPlayers(getLogName() + " surveils " + event.getAmount() + CardUtil.getSourceLogName(game, source));
        Cards cards = new CardsImpl();
        cards.addAll(getLibrary().getTopCards(game, event.getAmount()));
        if (!cards.isEmpty()) {
            TargetCard target = new TargetCard(0, cards.size(), Zone.LIBRARY,
                    new FilterCard("cards to PUT into your GRAVEYARD (Surveil)"));
            chooseTarget(Outcome.Benefit, cards, target, source, game);
            moveCards(new CardsImpl(target.getTargets()), Zone.GRAVEYARD, source, game);
            cards.removeAll(target.getTargets());
            putCardsOnTopOfLibrary(cards, game, source, true);
        }
        game.fireEvent(new GameEvent(GameEvent.EventType.SURVEILED, getId(), source, getId(), event.getAmount(), true));
        return true;
    }

    @Override
    public boolean addTargets(Ability ability, Game game
    ) {
        // only used for TestPlayer to preSet Targets
        return true;
    }

    @Override
    public String getHistory() {
        return "no available";
    }

    @Override
    public boolean hasDesignation(DesignationType designationName) {
        for (Designation designation : designations) {
            if (designation.getDesignationType().equals(designationName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addDesignation(Designation designation) {
        if (!designation.isUnique() || !this.hasDesignation(designation.getDesignationType())) {
            designations.add(designation);
        }
    }

    @Override
    public List<Designation> getDesignations() {
        return designations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Player obj = (Player) o;
        if (this.getId() == null || obj.getId() == null) {
            return false;
        }

        return this.getId().equals(obj.getId());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.playerId);
        return hash;
    }

    @Override
    public void addPhyrexianToColors(FilterMana colors) {
        if (phyrexianColors == null) {
            phyrexianColors = colors.copy();
        } else {
            if (colors.isWhite()) {
                this.phyrexianColors.setWhite(true);
            }
            if (colors.isBlue()) {
                this.phyrexianColors.setBlue(true);
            }
            if (colors.isBlack()) {
                this.phyrexianColors.setBlack(true);
            }
            if (colors.isRed()) {
                this.phyrexianColors.setRed(true);
            }
            if (colors.isGreen()) {
                this.phyrexianColors.setGreen(true);
            }
        }
    }

    @Override
    public FilterMana getPhyrexianColors() {
        return this.phyrexianColors;
    }

    @Override
    public SpellAbility chooseAbilityForCast(Card card, Game game, boolean noMana) {
        return card.getSpellAbility();
    }

    @Override
    public String toString() {
        return getName() + " (" + super.getClass().getSimpleName() + ")";
    }
}
