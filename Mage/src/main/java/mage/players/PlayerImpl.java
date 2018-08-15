package mage.players;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import mage.ConditionalMana;
import mage.MageObject;
import mage.MageObjectReference;
import mage.Mana;
import mage.abilities.*;
import mage.abilities.ActivatedAbility.ActivationStatus;
import mage.abilities.common.PassAbility;
import mage.abilities.common.WhileSearchingPlayFromLibraryAbility;
import mage.abilities.common.delayed.AtTheEndOfTurnStepPostDelayedTriggeredAbility;
import mage.abilities.costs.*;
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
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.SplitCard;
import mage.cards.decks.Deck;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import static mage.constants.Zone.BATTLEFIELD;
import static mage.constants.Zone.EXILED;
import static mage.constants.Zone.GRAVEYARD;
import static mage.constants.Zone.HAND;
import static mage.constants.Zone.LIBRARY;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.designations.Designation;
import mage.designations.DesignationType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureForCombat;
import mage.filter.common.FilterCreatureForCombatBlock;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.*;
import mage.game.combat.CombatGroup;
import mage.game.command.CommandObject;
import mage.game.events.DamagePlayerEvent;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
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

public abstract class PlayerImpl implements Player, Serializable {

    private static final Logger logger = Logger.getLogger(PlayerImpl.class);

    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

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
    protected boolean passedTurnSkipStack; // F6
    protected boolean passedUntilEndOfTurn; // F5
    protected boolean passedUntilNextMain; // F7
    protected boolean passedUntilStackResolved; // F10
    protected Date dateLastAddedToStack;
    protected boolean passedUntilEndStepBeforeMyTurn; // F11
    protected boolean skippedAtLeastOnce; // used to track if passed started in specific phase
    /**
     * This indicates that player passed all turns until his own turn starts
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
    protected Set<UUID> inRange = new HashSet<>();

    protected boolean isTestMode = false;
    protected boolean canGainLife = true;
    protected boolean canLoseLife = true;
    protected boolean canPayLifeCost = true;
    protected boolean loseByZeroOrLessLife = true;
    protected boolean canPlayCardsFromGraveyard = true;

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
    protected UUID castSourceIdWithAlternateMana;
    protected ManaCosts<ManaCost> castSourceIdManaCosts;
    protected Costs<Cost> castSourceIdCosts;

    // indicates that the player is in mana payment phase
    protected boolean payManaMode = false;

    protected UserData userData;
    protected MatchPlayer matchPlayer;

    protected List<Designation> designations = new ArrayList<>();

    /**
     * During some steps we can't play anything
     */
    protected final Map<PhaseStep, Step.StepPart> silentPhaseSteps = new HashMap<PhaseStep, Step.StepPart>() {
        {
            put(PhaseStep.DECLARE_ATTACKERS, Step.StepPart.PRE);
        }
    };

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

        this.attachments.addAll(player.attachments);

        this.inRange.addAll(player.inRange);
        this.userData = player.userData;
        this.canPayLifeCost = player.canPayLifeCost;
        this.sacrificeCostFilter = player.sacrificeCostFilter;
        this.alternativeSourceCosts.addAll(player.alternativeSourceCosts);
        this.storedBookmark = player.storedBookmark;

        this.topCardRevealed = player.topCardRevealed;
        this.playersUnderYourControl.clear();
        this.playersUnderYourControl.addAll(player.playersUnderYourControl);
        this.usersAllowedToSeeHandCards.addAll(player.usersAllowedToSeeHandCards);

        this.isTestMode = player.isTestMode;
        this.isGameUnderControl = player.isGameUnderControl;

        this.turnController = player.turnController;
        this.turnControllers.clear();
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

        this.castSourceIdWithAlternateMana = player.castSourceIdWithAlternateMana;
        this.castSourceIdManaCosts = player.castSourceIdManaCosts;
        this.castSourceIdCosts = player.castSourceIdCosts;
        this.payManaMode = player.payManaMode;

        this.designations.addAll(player.designations);
    }

    @Override
    public void restore(Player player) {
        this.name = player.getName();
        this.human = player.isHuman();
        this.life = player.getLife();

        // Don't restore more global states. If restored they are probably cause for unintended draws (https://github.com/magefree/mage/issues/1205).
//        this.wins = player.hasWon();
//        this.loses = player.hasLost();
//        this.left = player.hasLeft();
//        this.quit = player.hasQuit();
        // Makes no sense to restore
//        this.passed = player.isPassed();
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
        this.commandersIds = player.getCommandersIds();
        this.abilities = player.getAbilities().copy();
        this.counters = player.getCounters().copy();

        this.landsPlayed = player.getLandsPlayed();
        this.landsPerTurn = player.getLandsPerTurn();
        this.loyaltyUsePerTurn = player.getLoyaltyUsePerTurn();
        this.maxHandSize = player.getMaxHandSize();
        this.maxAttackedBy = player.getMaxAttackedBy();
        this.manaPool = player.getManaPool().copy();
        this.turns = player.getTurns();

        this.range = player.getRange();
        this.canGainLife = player.isCanGainLife();
        this.canLoseLife = player.isCanLoseLife();
        this.attachments.clear();
        this.attachments.addAll(player.getAttachments());

        this.inRange.clear();
        this.inRange.addAll(player.getInRange());
        this.canPayLifeCost = player.canPayLifeCost();
        this.sacrificeCostFilter = player.getSacrificeCostFilter() != null ? player.getSacrificeCostFilter().copy() : null;
        this.loseByZeroOrLessLife = player.canLoseByZeroOrLessLife();
        this.canPlayCardsFromGraveyard = player.canPlayCardsFromGraveyard();
        this.alternativeSourceCosts.addAll(player.getAlternativeSourceCosts());

        this.topCardRevealed = player.isTopCardRevealed();
        this.playersUnderYourControl.clear();
        this.playersUnderYourControl.addAll(player.getPlayersUnderYourControl());
        this.isGameUnderControl = player.isGameUnderControl();

        this.turnController = player.getTurnControlledBy();
        this.turnControllers.clear();
        this.turnControllers.addAll(player.getTurnControllers());
        this.reachedNextTurnAfterLeaving = player.hasReachedNextTurnAfterLeaving();
        this.castSourceIdWithAlternateMana = player.getCastSourceIdWithAlternateMana();
        this.castSourceIdManaCosts = player.getCastSourceIdManaCosts();
        this.castSourceIdCosts = player.getCastSourceIdCosts();

        this.designations.clear();
        this.designations.addAll(player.getDesignations());

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
        this.setLife(game.getLife(), game, (UUID) null);
        this.setReachedNextTurnAfterLeaving(false);

        this.castSourceIdWithAlternateMana = null;
        this.castSourceIdManaCosts = null;
        this.castSourceIdCosts = null;
        this.getManaPool().init(); // needed to remove mana that not empties on step change from previous game if left

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
        this.topCardRevealed = false;
        this.alternativeSourceCosts.clear();
        this.castSourceIdWithAlternateMana = null;
        this.castSourceIdManaCosts = null;
        this.castSourceIdCosts = null;
        this.getManaPool().clearEmptyManaPoolRules();
    }

    @Override
    public Counters getCounters() {
        return counters;
    }

    @Override
    public void otherPlayerLeftGame(Game game) {
        findRange(game);
    }

    @Override
    public void beginTurn(Game game) {
        this.landsPlayed = 0;
        findRange(game);
    }

    @Override
    public RangeOfInfluence getRange() {
        return range;
    }

    protected void findRange(Game game) {
        //20100423 - 801.2c
        inRange.clear();
        if (range == RangeOfInfluence.ALL) {
            for (Player player : game.getPlayers().values()) {
                if (!player.hasLeft()) {
                    inRange.add(player.getId());
                }
            }
        } else if ((range.getRange() * 2) + 1 >= game.getPlayers().size()) {
            for (Player player : game.getPlayers().values()) {
                if (!player.hasLeft()) {
                    inRange.add(player.getId());
                }
            }
        } else {
            inRange.add(playerId);
            PlayerList players = game.getState().getPlayerList(playerId);
            for (int i = 0; i < range.getRange(); i++) {
                Player player = players.getNext(game);
                if (player != null) {
                    while (player.hasLeft()) {
                        player = players.getNext(game);
                    }
                    inRange.add(player.getId());
                }
            }
            players = game.getState().getPlayerList(playerId);
            for (int i = 0; i < range.getRange(); i++) {
                Player player = players.getPrevious(game);
                if (player != null) {
                    while (player.hasLeft()) {
                        player = players.getPrevious(game);
                    }
                    inRange.add(player.getId());
                }
            }
        }
    }

    @Override
    public Set<UUID> getInRange() {
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
            DelayedTriggeredAbility ability = new AtTheEndOfTurnStepPostDelayedTriggeredAbility(new LoseControlOnOtherPlayersControllerEffect(this.getLogName(), player.getLogName()));
            ability.setSourceId(getId());
            ability.setControllerId(getId());
            game.addDelayedTriggeredAbility(ability);
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
            if (abilities.containsKey(ShroudAbility.getInstance().getId())) {
                return false;
            }
            if (abilities.containsKey(HexproofAbility.getInstance().getId())) {
                if (sourceControllerId != null && this.hasOpponent(sourceControllerId, game)
                        && null == game.getContinuousEffects().asThough(this.getId(), AsThoughEffectType.HEXPROOF, null, this.getId(), game)) {
                    return false;
                }
            }
            if (hasProtectionFrom(source, game)) {
                return false;
            }
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
    public int drawCards(int num, Game game) {
        if (num > 0) {
            return game.doAction(new MageDrawAction(this, num, null));
        }
        return 0;
    }

    @Override
    public int drawCards(int num, Game game, List<UUID> appliedEffects) {
        return game.doAction(new MageDrawAction(this, num, appliedEffects));
    }

    @Override
    public void discardToMax(Game game) {
        if (hand.size() > this.maxHandSize) {
            if (!game.isSimulation()) {
                game.informPlayers(getLogName() + " discards down to " + this.maxHandSize + (this.maxHandSize == 1 ? " hand card" : " hand cards"));
            }
            discard(hand.size() - this.maxHandSize, null, game);
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
        return true;
    }

    /**
     * @param amount
     * @param source
     * @param game
     */
    @Override
    public void discard(int amount, Ability source, Game game) {
        discard(amount, false, source, game);
    }

    @Override
    public Card discardOne(boolean random, Ability source, Game game) {
        Cards cards = discard(1, random, source, game);
        if (cards.isEmpty()) {
            return null;
        }
        return cards.getRandom(game);
    }

    @Override
    public Cards discard(int amount, boolean random, Ability source, Game game) {
        Cards discardedCards = new CardsImpl();
        if (amount <= 0) {
            return discardedCards;
        }
        if (this.getHand().size() == 1 || this.getHand().size() == amount) {
            discardedCards.addAll(this.getHand());
            while (!this.getHand().isEmpty()) {
                discard(this.getHand().get(this.getHand().iterator().next(), game), source, game);
            }
            return discardedCards;
        }
        if (random) {
            for (int i = 0; i < amount; i++) {
                Card card = this.getHand().getRandom(game);
                if (card != null) {
                    discardedCards.add(card);
                    discard(card, source, game);
                }
            }
        } else {
            int possibleAmount = Math.min(getHand().size(), amount);
            TargetDiscard target = new TargetDiscard(possibleAmount, possibleAmount, new FilterCard(CardUtil.numberToText(possibleAmount, "a") + " card" + (possibleAmount > 1 ? "s" : "")), playerId);
            choose(Outcome.Discard, target, source == null ? null : source.getSourceId(), game);
            for (UUID cardId : target.getTargets()) {
                Card card = this.getHand().get(cardId, game);
                if (card != null) { // can happen if user is removed (session expires)
                    discardedCards.add(card);
                    discard(card, source, game);
                }
            }
        }
        return discardedCards;
    }

    @Override
    public boolean discard(Card card, Ability source, Game game) {
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
        if (card != null
                && !game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DISCARD_CARD, card.getId(), source == null ? null : source.getSourceId(), playerId), source)) {
            // write info to game log first so game log infos from triggered or replacement effects follow in the game log
            if (!game.isSimulation()) {
                game.informPlayers(getLogName() + " discards " + card.getLogName());
            }
            /* If a card is discarded while Rest in Peace is on the battlefield, abilities that function
             * when a card is discarded (such as madness) still work, even though that card never reaches
             * a graveyard. In addition, spells or abilities that check the characteristics of a discarded
             * card (such as Chandra Ablaze's first ability) can find that card in exile. */
            card.moveToZone(Zone.GRAVEYARD, source == null ? null : source.getSourceId(), game, false);
            // So discard is also successful if card is moved to another zone by replacement effect!
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.DISCARDED_CARD, card.getId(), source == null ? null : source.getSourceId(), playerId));
            return true;
        }
        return false;
    }

    @Override
    public List<UUID> getAttachments() {
        return attachments;
    }

    @Override
    public boolean addAttachment(UUID permanentId, Game game) {
        if (!this.attachments.contains(permanentId)) {
            Permanent aura = game.getPermanent(permanentId);
            if (aura == null) {
                aura = game.getPermanentEntering(permanentId);
            }
            if (aura != null) {
                if (!game.replaceEvent(new GameEvent(GameEvent.EventType.ENCHANT_PLAYER, playerId, permanentId, aura.getControllerId()))) {
                    this.attachments.add(permanentId);
                    aura.attachTo(playerId, game);
                    game.fireEvent(new GameEvent(GameEvent.EventType.ENCHANTED_PLAYER, playerId, permanentId, aura.getControllerId()));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean removeAttachment(Permanent attachment, Game game) {
        if (this.attachments.contains(attachment.getId())) {
            if (!game.replaceEvent(new GameEvent(GameEvent.EventType.UNATTACH, playerId, attachment.getId(), attachment.getControllerId()))) {
                this.attachments.remove(attachment.getId());
                attachment.attachTo(null, game);
                game.fireEvent(new GameEvent(GameEvent.EventType.UNATTACHED, playerId, attachment.getId(), attachment.getControllerId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeFromBattlefield(Permanent permanent, Game game) {
        permanent.removeFromCombat(game, false);
        game.getBattlefield().removePermanent(permanent.getId());
        if (permanent.getAttachedTo() != null) {
            Permanent attachedTo = game.getPermanent(permanent.getAttachedTo());
            if (attachedTo != null) {
                attachedTo.removeAttachment(permanent.getId(), game);
            } else {
                Player attachedToPlayer = game.getPlayer(permanent.getAttachedTo());
                if (attachedToPlayer != null) {
                    attachedToPlayer.removeAttachment(permanent, game);
                } else {
                    Card attachedToCard = game.getCard(permanent.getAttachedTo());
                    if (attachedToCard != null) {
                        attachedToCard.removeAttachment(permanent.getId(), game);
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
        this.graveyard.remove(card);
        return true;
    }

    @Override
    public boolean putCardsOnBottomOfLibrary(Cards cardsToLibrary, Game game, Ability source, boolean anyOrder) {
        if (!cardsToLibrary.isEmpty()) {
            Cards cards = new CardsImpl(cardsToLibrary); // prevent possible ConcurrentModificationException
            if (!anyOrder) {
                while (!cards.isEmpty()) {
                    Card card = cards.getRandom(game);
                    if (card != null) {
                        cards.remove(card);
                        moveObjectToLibrary(card.getId(), source == null ? null : source.getSourceId(), game, false, false);
                    } else {
                        return false;// probably cards were removed because player left the game
                    }
                }
            } else {
                TargetCard target = new TargetCard(Zone.ALL, new FilterCard("card to put on the bottom of your library (last one chosen will be bottommost)"));
                target.setRequired(true);
                while (cards.size() > 1) {
                    this.choose(Outcome.Neutral, cards, target, game);
                    if (!canRespond()) {
                        return false;
                    }
                    UUID targetObjectId = target.getFirstTarget();
                    cards.remove(targetObjectId);
                    moveObjectToLibrary(targetObjectId, source == null ? null : source.getSourceId(), game, false, false);
                    target.clearChosen();
                }
                if (cards.size() == 1) {
                    moveObjectToLibrary(cards.iterator().next(), source == null ? null : source.getSourceId(), game, false, false);
                }
            }
        }
        return true;
    }

    @Override
    public boolean putCardOnTopXOfLibrary(Card card, Game game, Ability source, int xFromTheTop) {
        if (card.isOwnedBy(getId())) {
            if (library.size() + 1 < xFromTheTop) {
                putCardsOnBottomOfLibrary(new CardsImpl(card), game, source, true);
            } else {
                if (card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true) && !(card instanceof PermanentToken) && !card.isCopy()) {
                    card = getLibrary().removeFromTop(game);
                    getLibrary().putCardToTopXPos(card, xFromTheTop, game);
                    game.informPlayers(card.getLogName() + " is put into " + getLogName() + "'s library " + CardUtil.numberToOrdinalText(xFromTheTop) + " from the top");
                } else {
                    return false;
                }
            }
        } else {
            return game.getPlayer(card.getOwnerId()).putCardOnTopXOfLibrary(card, game, source, xFromTheTop);
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
            UUID sourceId = (source == null ? null : source.getSourceId());
            if (!anyOrder) {
                while (!cards.isEmpty()) {
                    Card card = cards.getRandom(game);
                    if (card != null) {
                        cards.remove(card.getId());
                        moveObjectToLibrary(card.getId(), source == null ? null : source.getSourceId(), game, true, false);
                    } else {
                        return false; // probably cards were removed because player left the game
                    }
                }
            } else {
                TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to put on the top of your library (last one chosen will be topmost)"));
                target.setRequired(true);
                while (cards.size() > 1) {
                    this.choose(Outcome.Neutral, cards, target, game);
                    if (!canRespond()) {
                        return false;
                    }
                    UUID targetObjectId = target.getFirstTarget();
                    cards.remove(targetObjectId);
                    moveObjectToLibrary(targetObjectId, sourceId, game, true, false);
                    target.clearChosen();
                }
                if (cards.size() == 1) {
                    moveObjectToLibrary(cards.iterator().next(), sourceId, game, true, false);
                }
            }
        }
        return true;
    }

    private boolean moveObjectToLibrary(UUID objectId, UUID sourceId, Game game, boolean toTop, boolean withName) {
        MageObject mageObject = game.getObject(objectId);
        if (mageObject != null) {
            Zone fromZone = game.getState().getZone(objectId);
            if ((mageObject instanceof Permanent)) {
                return this.moveCardToLibraryWithInfo((Permanent) mageObject, sourceId, game, fromZone, toTop, withName);
            } else if (mageObject instanceof Card) {
                return this.moveCardToLibraryWithInfo((Card) mageObject, sourceId, game, fromZone, toTop, withName);
            }
        }
        return false;
    }

    @Override
    public void setCastSourceIdWithAlternateMana(UUID sourceId, ManaCosts<ManaCost> manaCosts, Costs<Cost> costs) {
        castSourceIdWithAlternateMana = sourceId;
        castSourceIdManaCosts = manaCosts;
        castSourceIdCosts = costs;
    }

    @Override
    public UUID getCastSourceIdWithAlternateMana() {
        return castSourceIdWithAlternateMana;
    }

    @Override
    public Costs<Cost> getCastSourceIdCosts() {
        return castSourceIdCosts;
    }

    @Override
    public ManaCosts getCastSourceIdManaCosts() {
        return castSourceIdManaCosts;
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
    public boolean playCard(Card card, Game game, boolean noMana, boolean ignoreTiming, MageObjectReference reference) {
        if (card == null) {
            return false;
        }
        boolean result;
        if (card.isLand()) {
            result = playLand(card, game, ignoreTiming);
        } else {
            result = cast(card.getSpellAbility(), game, noMana, reference);
        }
        if (!result) {
            game.informPlayer(this, "You can't play " + card.getIdName() + '.');
        }
        return result;
    }

    @Override
    public boolean cast(SpellAbility ability, Game game, boolean noMana, MageObjectReference permittingObject) {
        if (game == null || ability == null) {
            return false;
        }

        // Use ability copy to avoid problems with targets and costs on recast (issue https://github.com/magefree/mage/issues/5189).
        ability = ability.copy();

        ability.setControllerId(getId());
        if (ability.getSpellAbilityType() != SpellAbilityType.BASE) {
            ability = chooseSpellAbilityForCast(ability, game, noMana);
            if (ability == null) {
                // No ability could be cast (selected), probably because of no valid targets (happens often if a card can be cast by an effect).
                return false;
            }
        }
        //20091005 - 601.2a
        if (ability.getSourceId() == null) {
            logger.error("Ability without sourceId turn " + game.getTurnNum() + ". Ability: " + ability.getRule());
            return false;
        }
        Card card = game.getCard(ability.getSourceId());
        if (card != null) {
            if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.CAST_SPELL, ability.getId(), ability.getSourceId(), playerId, permittingObject), ability)) {
                int bookmark = game.bookmarkState();
                Zone fromZone = game.getState().getZone(card.getMainCard().getId());
                card.cast(game, fromZone, ability, playerId);
                Spell spell = game.getStack().getSpell(ability.getId());
                if (spell == null) {
                    logger.error("Got no spell from stack. ability: " + ability.getRule());
                    return false;
                }
                // some effects set sourceId to cast without paying mana costs or other costs
                if (ability.getSourceId().equals(getCastSourceIdWithAlternateMana())) {
                    Ability spellAbility = spell.getSpellAbility();
                    ManaCosts alternateCosts = getCastSourceIdManaCosts();
                    Costs<Cost> costs = getCastSourceIdCosts();
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
                setCastSourceIdWithAlternateMana(null, null, null);
                GameEvent event = GameEvent.getEvent(GameEvent.EventType.CAST_SPELL, spell.getSpellAbility().getId(), spell.getSpellAbility().getSourceId(), playerId, permittingObject);
                game.fireEvent(event);
                if (spell.activate(game, noMana)) {
                    event = GameEvent.getEvent(GameEvent.EventType.SPELL_CAST, spell.getSpellAbility().getId(), spell.getSpellAbility().getSourceId(), playerId, permittingObject);
                    event.setZone(fromZone);
                    game.fireEvent(event);
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
    public SpellAbility chooseSpellAbilityForCast(SpellAbility ability, Game game, boolean noMana) {
        return ability;
    }

    @Override
    public boolean playLand(Card card, Game game, boolean ignoreTiming) {
        // Check for alternate casting possibilities: e.g. land with Morph
        ActivatedAbility playLandAbility = null;
        boolean found = false;
        for (Ability ability : card.getAbilities()) {
            // if cast for noMana no Alternative costs are allowed
            if ((ability instanceof AlternativeSourceCosts) || (ability instanceof OptionalAdditionalSourceCosts)) {
                found = true;
            }
            if (ability instanceof PlayLandAbility) {
                playLandAbility = (ActivatedAbility) ability;
            }
        }
        if (found) {
            SpellAbility spellAbility = new SpellAbility(null, "", game.getState().getZone(card.getId()), SpellAbilityType.FACE_DOWN_CREATURE);
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
        if (!ignoreTiming && !activationStatus.canActivate()) {
            return false;
        }

        //20091005 - 305.1
        if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.PLAY_LAND, card.getId(), card.getId(), playerId, activationStatus.getPermittingObject()))) {
            // int bookmark = game.bookmarkState();
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PLAY_LAND, card.getId(), card.getId(), playerId, activationStatus.getPermittingObject()));
            if (moveCards(card, Zone.BATTLEFIELD, playLandAbility, game, false, false, false, null)) {
                landsPlayed++;
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.LAND_PLAYED, card.getId(), card.getId(), playerId, activationStatus.getPermittingObject()));
                game.fireInformEvent(getLogName() + " plays " + card.getLogName());
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
        if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.ACTIVATE_ABILITY, ability.getId(), ability.getSourceId(), playerId))) {
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
            if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.ACTIVATE_ABILITY, ability.getId(), ability.getSourceId(), playerId))) {
                int bookmark = game.bookmarkState();
                ability.newId();
                ability.setControllerId(playerId);
                game.getStack().push(new StackAbility(ability, playerId));
                if (ability.activate(game, false)) {
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.ACTIVATED_ABILITY, ability.getId(), ability.getSourceId(), playerId));
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
        if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.ACTIVATE_ABILITY, action.getSourceId(), action.getId(), playerId))) {
            int bookmark = game.bookmarkState();
            if (action.activate(game, false)) {
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.ACTIVATED_ABILITY, action.getSourceId(), action.getId(), playerId));
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

    protected void restoreState(int bookmark, String text, Game game) {
        game.restoreState(bookmark, text);
        if (storedBookmark >= bookmark) {
            resetStoredBookmark(game);
        }
    }

    @Override
    public boolean activateAbility(ActivatedAbility ability, Game game) {
        boolean result;
        if (ability instanceof PassAbility) {
            pass(game);
            return true;
        }
        if (ability instanceof PlayLandAbility) {
            Card card = game.getCard(ability.getSourceId());
            result = playLand(card, game, false);
        } else {
            ActivationStatus activationStatus = ability.canActivate(this.playerId, game);
            if (!activationStatus.canActivate()) {
                return false;
            }

            switch (ability.getAbilityType()) {
                case SPECIAL_ACTION:
                    result = specialAction((SpecialAction) ability.copy(), game);
                    break;
                case MANA:
                    result = playManaAbility((ActivatedManaAbilityImpl) ability.copy(), game);
                    break;
                case SPELL:
                    result = cast((SpellAbility) ability, game, false, activationStatus.getPermittingObject());
                    break;
                default:
                    result = playAbility(ability.copy(), game);
                    break;
            }
        }

        //if player has taken an action then reset all player passed flags
        justActivatedType = null;
        if (result) {
            if (isHuman() && (ability.getAbilityType() == AbilityType.SPELL || ability.getAbilityType() == AbilityType.ACTIVATED)) {
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
        if (ability.canChooseTarget(game)) {
            if (ability.isUsesStack()) {
                game.getStack().push(new StackAbility(ability, playerId));
            }
            if (ability.activate(game, false)) {
                if ((ability.isUsesStack() || ability.getRuleVisible()) && !game.isSimulation()) {
                    game.informPlayers(ability.getGameLogMessage(game));
                }
                if (!ability.isUsesStack()) {
                    ability.resolve(game);
                } else {
                    game.fireEvent(new GameEvent(EventType.TRIGGERED_ABILITY, ability.getId(), ability.getSourceId(), ability.getControllerId()));
                }
                game.removeBookmark(bookmark);
                return true;
            }
        }
        restoreState(bookmark, triggeredAbility.getRule(), game); // why restore is needed here? (to remove the triggered ability from the stack)
        return false;
    }

    public LinkedHashMap<UUID, ActivatedAbility> getSpellAbilities(MageObject object, Zone zone, Game game) {
        LinkedHashMap<UUID, ActivatedAbility> useable = new LinkedHashMap<>();
        for (Ability ability : object.getAbilities()) {
            if (ability instanceof SpellAbility) {
                switch (((SpellAbility) ability).getSpellAbilityType()) {
                    case SPLIT_FUSED:
                        if (zone == Zone.HAND) {
                            if (((SpellAbility) ability).canChooseTarget(game)) {
                                useable.put(ability.getId(), (SpellAbility) ability);
                            }
                        }
                    case SPLIT:
                        if (((SplitCard) object).getLeftHalfCard().getSpellAbility().canChooseTarget(game)) {
                            useable.put(((SplitCard) object).getLeftHalfCard().getSpellAbility().getId(), ((SplitCard) object).getLeftHalfCard().getSpellAbility());
                        }
                        if (((SplitCard) object).getRightHalfCard().getSpellAbility().canChooseTarget(game)) {
                            useable.put(((SplitCard) object).getRightHalfCard().getSpellAbility().getId(), ((SplitCard) object).getRightHalfCard().getSpellAbility());
                        }
                        return useable;
                    case SPLIT_AFTERMATH:
                        if (zone == Zone.GRAVEYARD) {
                            if (((SplitCard) object).getRightHalfCard().getSpellAbility().canChooseTarget(game)) {
                                useable.put(((SplitCard) object).getRightHalfCard().getSpellAbility().getId(), ((SplitCard) object).getRightHalfCard().getSpellAbility());
                            }
                        } else {
                            if (((SplitCard) object).getLeftHalfCard().getSpellAbility().canChooseTarget(game)) {
                                useable.put(((SplitCard) object).getLeftHalfCard().getSpellAbility().getId(), ((SplitCard) object).getLeftHalfCard().getSpellAbility());
                            }
                        }
                        return useable;
                    default:
                        useable.put(ability.getId(), (SpellAbility) ability);
                }
            }
        }
        return useable;
    }

    // Get the usable activated abilities for a *single card object*, that is, either a card or half of a split card.
    // Also called on the whole split card but only passing the fuse ability and other whole-split-card shared abilities
    // as candidates.
    private void getUseableActivatedAbilitiesHalfImpl(MageObject object, Zone zone, Game game, Abilities<Ability> candidateAbilites, LinkedHashMap<UUID, ActivatedAbility> output) {
        boolean canUse = !(object instanceof Permanent) || ((Permanent) object).canUseActivatedAbilities(game);
        ManaOptions availableMana = null;
        //        ManaOptions availableMana = getManaAvailable(game); // can only be activated if mana calculation works flawless otherwise player can't play spells they could play if calculation would work correctly
        //        availableMana.addMana(manaPool.getMana());
        for (Ability ability : candidateAbilites) {
            if (canUse || ability.getAbilityType() == AbilityType.SPECIAL_ACTION) {
                if (ability.getZone().match(zone)) {
                    if (ability instanceof ActivatedAbility) {
                        if (ability instanceof ActivatedManaAbilityImpl) {
                            if (((ActivatedAbility) ability).canActivate(playerId, game).canActivate()) {
                                output.put(ability.getId(), (ActivatedAbility) ability);
                            }
                        } else if (canPlay(((ActivatedAbility) ability), availableMana, object, game)) {
                            output.put(ability.getId(), (ActivatedAbility) ability);
                        }
                    } else if (ability instanceof AlternativeSourceCosts) {
                        if (object.isLand()) {
                            for (Ability ability2 : object.getAbilities().copy()) {
                                if (ability2 instanceof PlayLandAbility) {
                                    output.put(ability2.getId(), (ActivatedAbility) ability2);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (zone != Zone.HAND) {
            if (Zone.GRAVEYARD == zone && canPlayCardsFromGraveyard()) {
                for (ActivatedAbility ability : candidateAbilites.getPlayableAbilities(Zone.HAND)) {
                    if (canUse || ability.getAbilityType() == AbilityType.SPECIAL_ACTION) {
                        if (ability.canActivate(playerId, game).canActivate()) {
                            output.put(ability.getId(), ability);
                        }
                    }
                }
            }
            if (zone != Zone.BATTLEFIELD
                    && null != game.getContinuousEffects().asThough(object.getId(), AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, null, this.getId(), game)) {
                for (Ability ability : candidateAbilites) {
                    if (canUse || ability.getAbilityType() == AbilityType.SPECIAL_ACTION) {
                        ability.setControllerId(this.getId());
                        if (ability instanceof ActivatedAbility && ability.getZone().match(Zone.HAND)
                                && ((ActivatedAbility) ability).canActivate(playerId, game).canActivate()) {
                            output.put(ability.getId(), (ActivatedAbility) ability);
                        }
                    }
                }
            }
        }
    }

    @Override
    public LinkedHashMap<UUID, ActivatedAbility> getUseableActivatedAbilities(MageObject object, Zone zone, Game game) {
        LinkedHashMap<UUID, ActivatedAbility> useable = new LinkedHashMap<>();
        if (object instanceof StackAbility) { // It may not be possible to activate abilities of stack abilities
            return useable;
        }
        if (object instanceof SplitCard) {
            SplitCard splitCard = (SplitCard) object;
            getUseableActivatedAbilitiesHalfImpl(splitCard.getLeftHalfCard(), zone, game, splitCard.getLeftHalfCard().getAbilities(), useable);
            getUseableActivatedAbilitiesHalfImpl(splitCard.getRightHalfCard(), zone, game, splitCard.getRightHalfCard().getAbilities(), useable);
            getUseableActivatedAbilitiesHalfImpl(splitCard, zone, game, splitCard.getSharedAbilities(), useable);
        } else {
            getUseableActivatedAbilitiesHalfImpl(object, zone, game, object.getAbilities(), useable);
        }
        getOtherUseableActivatedAbilities(object, zone, game, useable);

        return useable;
    }

    // Adds special abilities that are given to non permanents by continuous effects
    private void getOtherUseableActivatedAbilities(MageObject object, Zone zone, Game game, Map<UUID, ActivatedAbility> useable) {
        Abilities<ActivatedAbility> otherAbilities = game.getState().getActivatedOtherAbilities(object.getId(), zone);
        if (otherAbilities != null) {
            boolean canUse = !(object instanceof Permanent) || ((Permanent) object).canUseActivatedAbilities(game);
            for (ActivatedAbility ability : otherAbilities) {
                if (canUse || ability.getAbilityType() == AbilityType.SPECIAL_ACTION) {
                    Card card = game.getCard(ability.getSourceId());
                    if (card.isSplitCard() && ability instanceof FlashbackAbility) {
                        FlashbackAbility flashbackAbility;
                        // Left Half
                        if (card.isInstant()) {
                            flashbackAbility = new FlashbackAbility(((SplitCard) card).getLeftHalfCard().getManaCost(), TimingRule.INSTANT);
                        } else {
                            flashbackAbility = new FlashbackAbility(((SplitCard) card).getLeftHalfCard().getManaCost(), TimingRule.SORCERY);
                        }
                        flashbackAbility.setSourceId(card.getId());
                        flashbackAbility.setControllerId(card.getOwnerId());
                        flashbackAbility.setSpellAbilityType(SpellAbilityType.SPLIT_LEFT);
                        flashbackAbility.setAbilityName(((SplitCard) card).getLeftHalfCard().getName());
                        if (flashbackAbility.canActivate(playerId, game).canActivate()) {
                            useable.put(flashbackAbility.getId(), flashbackAbility);
                        }
                        // Right Half
                        if (card.isInstant()) {
                            flashbackAbility = new FlashbackAbility(((SplitCard) card).getRightHalfCard().getManaCost(), TimingRule.INSTANT);
                        } else {
                            flashbackAbility = new FlashbackAbility(((SplitCard) card).getRightHalfCard().getManaCost(), TimingRule.SORCERY);
                        }
                        flashbackAbility.setSourceId(card.getId());
                        flashbackAbility.setControllerId(card.getOwnerId());
                        flashbackAbility.setSpellAbilityType(SpellAbilityType.SPLIT_RIGHT);
                        flashbackAbility.setAbilityName(((SplitCard) card).getRightHalfCard().getName());
                        if (flashbackAbility.canActivate(playerId, game).canActivate()) {
                            useable.put(flashbackAbility.getId(), flashbackAbility);
                        }

                    } else {
                        useable.put(ability.getId(), ability);
                    }
                }
            }
        }
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
        if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.SHUFFLE_LIBRARY, playerId, playerId))) {
            this.library.shuffle();
            if (!game.isSimulation()) {
                game.informPlayers(getLogName() + "'s library is shuffled");
            }
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.LIBRARY_SHUFFLED, playerId, (source == null ? null : source.getSourceId()), playerId));
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
        List<Permanent> phasedOut = game.getBattlefield().getPhasedOut(playerId);
        for (Permanent permanent : game.getBattlefield().getPhasedIn(playerId)) {
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
        HashMap<Entry<RestrictionUntapNotMoreThanEffect, Set<Ability>>, Integer> notMoreThanEffectsUsage = new HashMap<>();
        for (Entry<RestrictionUntapNotMoreThanEffect, Set<Ability>> restrictionEffect : game.getContinuousEffects().getApplicableRestrictionUntapNotMoreThanEffects(this, game).entrySet()) {
            notMoreThanEffectsUsage.put(restrictionEffect, restrictionEffect.getKey().getNumber());
        }

        if (!notMoreThanEffectsUsage.isEmpty()) {
            // create list of all permanents that can be untapped generally
            List<Permanent> canBeUntapped = new ArrayList<>();
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
                boolean untap = true;
                for (RestrictionEffect effect : game.getContinuousEffects().getApplicableRestrictionEffects(permanent, game).keySet()) {
                    untap &= effect.canBeUntapped(permanent, null, game);
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

                        List<Permanent> leftForUntap = getPermanentsThatCanBeUntapped(game, canBeUntapped, handledEntry.getKey().getKey(), notMoreThanEffectsUsage);

                        FilterControlledPermanent filter = handledEntry.getKey().getKey().getFilter().copy();
                        String message = filter.getMessage();
                        // omit already from other untap effects selected permanents
                        for (Permanent permanent : selectedToUntap) {
                            filter.add(Predicates.not(new PermanentIdPredicate(permanent.getId())));
                        }
                        // while targets left and there is still allowed to untap
                        while (canRespond() && !leftForUntap.isEmpty() && numberToUntap > 0) {
                            // player has to select the permanent he wants to untap for this restriction
                            Ability ability = handledEntry.getKey().getValue().iterator().next();
                            if (ability != null) {
                                StringBuilder sb = new StringBuilder(message).append(" to untap").append(" (").append(Math.min(leftForUntap.size(), numberToUntap)).append(" in total");
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
                                        if (notMoreThanEffect.getValue() > 0 && notMoreThanEffect.getKey().getKey().getFilter().match(selectedPermanent, game)) {
                                            notMoreThanEffect.setValue(notMoreThanEffect.getValue() - 1);
                                        }
                                    }
                                    // update the left for untap list
                                    leftForUntap = getPermanentsThatCanBeUntapped(game, canBeUntapped, handledEntry.getKey().getKey(), notMoreThanEffectsUsage);
                                    // remove already selected permanents
                                    for (Permanent permanent : selectedToUntap) {
                                        if (leftForUntap.contains(permanent)) {
                                            leftForUntap.remove(permanent);
                                        }
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
                    untap &= effect.canBeUntapped(permanent, null, game);
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
                    if (notMoreThanEffect.getKey().getKey().getFilter().match(permanent, game) && notMoreThanEffect.getValue() == 0) {
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
        setLife(life, game, source.getSourceId());
    }

    @Override
    public void setLife(int life, Game game, UUID sourceId) {
        // rule 118.5
        if (life > this.life) {
            gainLife(life - this.life, game, sourceId);
        } else if (life < this.life) {
            loseLife(this.life - life, game, false);
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
    public int loseLife(int amount, Game game, boolean atCombat) {
        if (!canLoseLife || !this.isInGame()) {
            return 0;
        }
        GameEvent event = new GameEvent(GameEvent.EventType.LOSE_LIFE, playerId, playerId, playerId, amount, atCombat);
        if (!game.replaceEvent(event)) {
            // this.life -= event.getAmount();
            this.life = CardUtil.subtractWithOverflowCheck(this.life, event.getAmount());
            if (!game.isSimulation()) {
                game.informPlayers(this.getLogName() + " loses " + event.getAmount() + " life");
            }
            if (amount > 0) {
                game.fireEvent(new GameEvent(GameEvent.EventType.LOST_LIFE, playerId, playerId, playerId, amount, atCombat));
            }
            return amount;
        }
        return 0;
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
        return gainLife(amount, game, source.getSourceId());
    }

    @Override
    public int gainLife(int amount, Game game, UUID sourceId) {
        if (!canGainLife || amount <= 0) {
            return 0;
        }
        GameEvent event = new GameEvent(GameEvent.EventType.GAIN_LIFE, playerId, playerId, playerId, amount, false);
        if (!game.replaceEvent(event)) {
            // TODO: lock life at Integer.MAX_VALUE if reached, until it's set to a different amount
            // (https://magic.wizards.com/en/articles/archive/news/unstable-faqawaslfaqpaftidawabiajtbt-2017-12-06 - "infinite" life total stays infinite no matter how much is gained or lost)
            // this.life += event.getAmount();
            this.life = CardUtil.addWithOverflowCheck(this.life, event.getAmount());
            if (!game.isSimulation()) {
                game.informPlayers(this.getLogName() + " gains " + event.getAmount() + " life");
            }
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.GAINED_LIFE, playerId, sourceId, playerId, event.getAmount()));
            return event.getAmount();
        }
        return 0;
    }

    @Override
    public int damage(int damage, UUID sourceId, Game game, boolean combatDamage, boolean preventable) {
        return doDamage(damage, sourceId, game, combatDamage, preventable, null);
    }

    @Override
    public int damage(int damage, UUID sourceId, Game game, boolean combatDamage, boolean preventable, List<UUID> appliedEffects) {
        return doDamage(damage, sourceId, game, combatDamage, preventable, appliedEffects);
    }

    @SuppressWarnings({"null", "ConstantConditions"})
    private int doDamage(int damage, UUID sourceId, Game game, boolean combatDamage, boolean preventable, List<UUID> appliedEffects) {
        if (damage > 0) {
            if (canDamage(game.getObject(sourceId), game)) {
                GameEvent event = new DamagePlayerEvent(playerId, sourceId, playerId, damage, preventable, combatDamage);
                event.setAppliedEffects(appliedEffects);
                if (!game.replaceEvent(event)) {
                    int actualDamage = event.getAmount();
                    if (actualDamage > 0) {
                        UUID sourceControllerId = null;
                        Abilities sourceAbilities = null;
                        MageObject source = game.getPermanentOrLKIBattlefield(sourceId);
                        if (source == null) {
                            StackObject stackObject = game.getStack().getStackObject(sourceId);
                            if (stackObject != null) {
                                source = stackObject.getStackAbility().getSourceObject(game);
                            } else {
                                source = game.getObject(sourceId);
                            }
                            if (source instanceof Spell) {
                                sourceAbilities = ((Spell) source).getAbilities(game);
                                sourceControllerId = ((Spell) source).getControllerId();
                            } else if (source instanceof Card) {
                                sourceAbilities = ((Card) source).getAbilities(game);
                                sourceControllerId = ((Card) source).getOwnerId();
                            } else if (source instanceof CommandObject) {
                                sourceControllerId = ((CommandObject) source).getControllerId();
                                sourceAbilities = ((CommandObject) source).getAbilities();
                            }
                        } else {
                            sourceAbilities = ((Permanent) source).getAbilities(game);
                            sourceControllerId = ((Permanent) source).getControllerId();
                        }
                        if (sourceAbilities != null && sourceAbilities.containsKey(InfectAbility.getInstance().getId())) {
                            addCounters(CounterType.POISON.createInstance(actualDamage), game);
                        } else {
                            GameEvent damageToLifeLossEvent = new GameEvent(EventType.DAMAGE_CAUSES_LIFE_LOSS, playerId, sourceId, playerId, actualDamage, combatDamage);
                            if (!game.replaceEvent(damageToLifeLossEvent)) {
                                this.loseLife(damageToLifeLossEvent.getAmount(), game, combatDamage);
                            }
                        }
                        if (sourceAbilities != null && sourceAbilities.containsKey(LifelinkAbility.getInstance().getId())) {
                            Player player = game.getPlayer(sourceControllerId);
                            player.gainLife(actualDamage, game, sourceId);
                        }
                        // Unstable ability - Earl of Squirrel
                        if (sourceAbilities != null && sourceAbilities.containsKey(SquirrellinkAbility.getInstance().getId())) {
                            Player player = game.getPlayer(sourceControllerId);
                            new SquirrelToken().putOntoBattlefield(actualDamage, game, sourceId, player.getId());
                        }
                        game.fireEvent(new DamagedPlayerEvent(playerId, sourceId, playerId, actualDamage, combatDamage));
                        return actualDamage;
                    }
                }
            } else {
                MageObject sourceObject = game.getObject(sourceId);
                game.informPlayers(damage + " damage "
                        + (sourceObject == null ? "" : "from " + sourceObject.getLogName())
                        + " to " + getLogName()
                        + (damage > 1 ? " were" : "was") + " prevented because of protection.");
            }
        }
        return 0;
    }

    @Override
    public boolean addCounters(Counter counter, Game game) {
        boolean returnCode = true;
        GameEvent countersEvent = GameEvent.getEvent(EventType.ADD_COUNTERS, playerId, null, playerId, counter.getName(), counter.getCount());
        if (!game.replaceEvent(countersEvent)) {
            int amount = countersEvent.getAmount();
            int finalAmount = amount;
            for (int i = 0; i < amount; i++) {
                Counter eventCounter = counter.copy();
                eventCounter.remove(eventCounter.getCount() - 1);
                GameEvent event = GameEvent.getEvent(EventType.ADD_COUNTER, playerId, null, playerId, counter.getName(), 1);
                if (!game.replaceEvent(event)) {
                    getCounters().addCounter(eventCounter);
                    game.fireEvent(GameEvent.getEvent(EventType.COUNTER_ADDED, playerId, null, playerId, counter.getName(), 1));
                } else {
                    finalAmount--;
                    returnCode = false;
                }
            }
            if (finalAmount > 0) {
                game.fireEvent(GameEvent.getEvent(EventType.COUNTERS_ADDED, playerId, null, playerId, counter.getName(), amount));
            }
        } else {
            returnCode = false;
        }
        return returnCode;
    }

    @Override
    public void removeCounters(String name, int amount, Ability source, Game game) {
        for (int i = 0; i < amount; i++) {
            if (!counters.removeCounter(name, 1)) {
                break;
            }
            GameEvent event = GameEvent.getEvent(GameEvent.EventType.COUNTER_REMOVED,
                    getId(), (source == null ? null : source.getSourceId()), (source == null ? null : source.getControllerId()));
            event.setData(name);
            event.setAmount(1);
            game.fireEvent(event);
        }
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
    public boolean isEmptyDraw() {
        return library.isEmptyDraw();
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
                skippedAtLeastOnce = !(game.getTurn().getStepType() == PhaseStep.POSTCOMBAT_MAIN || game.getTurn().getStepType() == PhaseStep.PRECOMBAT_MAIN);
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
                break;
        }
        logger.trace("PASS Priority: " + playerAction.toString());
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
        return hasLeft() // If a player concedes or has left the match he loses also if effect would say otherwise
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
                        logger.debug("player won -> calling opponent lost: " + this.getName() + "  opponent: " + opponent.getName());
                        opponent.lostForced(game);
                    }
                }
                // if no more opponents alive, you win and the game ends
                int opponentsAlive = 0;
                for (UUID opponentId : game.getOpponents(playerId)) {
                    Player opponent = game.getPlayer(opponentId);
                    if (opponent != null && !opponent.hasLost()) {
                        opponentsAlive++;
                    }
                }
                if (opponentsAlive == 0 && !hasWon()) {
                    logger.debug("player won -> No more opponents alive game won: " + this.getName());
                    game.informPlayers(this.getLogName() + " has won the game");
                    this.wins = true;
                    game.end();
                }
            } else {
                logger.debug("player won -> but already lost before: " + this.getName());
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
    public boolean canRespond() { // abort is checked here to get out of player requests
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
        if (attacker != null && attacker.canAttack(defenderId, game) && attacker.isControlledBy(playerId)) {
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
    public boolean searchLibrary(TargetCardInLibrary target, Game game) {
        return searchLibrary(target, game, playerId, true);
    }

    @Override
    public boolean searchLibrary(TargetCardInLibrary target, Game game, boolean triggerEvents) {
        return searchLibrary(target, game, playerId, triggerEvents);
    }

    @Override
    public boolean searchLibrary(TargetCardInLibrary target, Game game, UUID targetPlayerId) {
        return searchLibrary(target, game, targetPlayerId, true);
    }

    @Override
    public boolean searchLibrary(TargetCardInLibrary target, Game game, UUID targetPlayerId, boolean triggerEvents) {
        //20091005 - 701.14c
        Library searchedLibrary = null;
        String searchInfo = null;
        if (targetPlayerId.equals(playerId)) {
            searchInfo = getLogName() + " searches their library";
            searchedLibrary = library;
        } else {
            Player targetPlayer = game.getPlayer(targetPlayerId);
            if (targetPlayer != null) {
                searchInfo = getLogName() + " searches the library of " + targetPlayer.getLogName();
                searchedLibrary = targetPlayer.getLibrary();
            }
        }
        if (searchedLibrary == null) {
            return false;
        }
        GameEvent event = GameEvent.getEvent(GameEvent.EventType.SEARCH_LIBRARY, targetPlayerId, playerId, playerId, Integer.MAX_VALUE);
        if (!game.replaceEvent(event)) {
            if (!game.isSimulation()) {
                game.informPlayers(searchInfo);
            }
            TargetCardInLibrary newTarget = target.copy();
            int count;
            int librarySearchLimit = event.getAmount();
            List<Card> cardsFromTop = null;
            do {
                // TODO: prevent shuffling from moving the visualized cards
                if (librarySearchLimit == Integer.MAX_VALUE) {
                    count = searchedLibrary.count(target.getFilter(), game);
                } else {
                    Player targetPlayer = game.getPlayer(targetPlayerId);
                    if (targetPlayer == null) {
                        return false;
                    }
                    if (cardsFromTop == null) {
                        cardsFromTop = new ArrayList<>(targetPlayer.getLibrary().getTopCards(game, librarySearchLimit));
                    } else {
                        cardsFromTop.retainAll(targetPlayer.getLibrary().getCards(game));
                    }
                    newTarget.setCardLimit(Math.min(librarySearchLimit, cardsFromTop.size()));
                    count = Math.min(searchedLibrary.count(target.getFilter(), game), librarySearchLimit);
                }

                if (count < target.getNumberOfTargets()) {
                    newTarget.setMinNumberOfTargets(count);
                }
                if (newTarget.choose(Outcome.Neutral, playerId, targetPlayerId, game)) {
                    if (targetPlayerId.equals(playerId) && handleLibraryCastableCards(library, game, targetPlayerId)) { // for handling Panglacial Wurm
                        newTarget.clearChosen();
                        continue;
                    }
                    target.getTargets().clear();
                    for (UUID targetId : newTarget.getTargets()) {
                        target.add(targetId, game);
                    }
                    if (triggerEvents) {
                        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.LIBRARY_SEARCHED, targetPlayerId, playerId));
                    }
                } else if (targetPlayerId.equals(playerId) && handleLibraryCastableCards(library, game, targetPlayerId)) { // for handling Panglacial Wurm
                    newTarget.clearChosen();
                    continue;
                }
                break;
            } while (true);
            return true;
        }
        return false;
    }

    private boolean handleLibraryCastableCards(Library library, Game game, UUID targetPlayerId) {
        // for handling Panglacial Wurm
        boolean alreadyChosenUse = false;
        Map<UUID, String> libraryCastableCardTracker = new HashMap<>();
        searchForCards:
        do {
            for (Card card : library.getCards(game)) {
                for (Ability ability : card.getAbilities()) {
                    if (ability.getClass() == WhileSearchingPlayFromLibraryAbility.class) {
                        libraryCastableCardTracker.put(card.getId(), card.getName() + " [" + card.getId().toString().substring(0, 3) + "]");
                    }
                }
            }
            if (!libraryCastableCardTracker.isEmpty()) {
                Player player = game.getPlayer(targetPlayerId);
                if (player != null) {
                    if (player.isHuman() && (alreadyChosenUse || player.chooseUse(Outcome.AIDontUseIt, "Cast a creature card from your library? (choose \"No\" to finish search)", null, game))) {
                        ChoiceImpl chooseCard = new ChoiceImpl();
                        chooseCard.setMessage("Which creature do you wish to cast from your library?");
                        Set<String> choice = new LinkedHashSet<>();
                        for (Entry<UUID, String> entry : libraryCastableCardTracker.entrySet()) {
                            choice.add(new AbstractMap.SimpleEntry<>(entry).getValue());
                        }
                        chooseCard.setChoices(choice);
                        while (!choice.isEmpty()) {
                            if (player.choose(Outcome.AIDontUseIt, chooseCard, game)) {
                                String chosenCard = chooseCard.getChoice();
                                for (Entry<UUID, String> entry : libraryCastableCardTracker.entrySet()) {
                                    if (chosenCard.equals(entry.getValue())) {
                                        Card card = game.getCard(entry.getKey());
                                        if (card != null) {
                                            // TODO: fix costs (why is Panglacial Wurm automatically accepting payment?)
                                            player.cast(card.getSpellAbility(), game, false, null);
                                        }
                                        chooseCard.clearChoice();
                                        libraryCastableCardTracker.clear();
                                        alreadyChosenUse = true;
                                        continue searchForCards;
                                    }
                                }
                                continue;
                            }
                            break;
                        }
                        return true;
                    }
                }
            }
            break;
        } while (alreadyChosenUse);
        return alreadyChosenUse;
    }

    @Override
    public boolean flipCoin(Game game) {
        return this.flipCoin(game, null);
    }

    /**
     * @param game
     * @param appliedEffects
     * @return true if player won the toss
     */
    @Override
    public boolean flipCoin(Game game, ArrayList<UUID> appliedEffects) {
        boolean result = RandomUtil.nextBoolean();
        if (!game.isSimulation()) {
            game.informPlayers("[Flip a coin] " + getLogName() + (result ? " won (head)." : " lost (tail)."));
        }
        GameEvent event = new GameEvent(GameEvent.EventType.FLIP_COIN, playerId, null, playerId, 0, result);
        event.setAppliedEffects(appliedEffects);
        if (!game.replaceEvent(event)) {
            game.fireEvent(new GameEvent(GameEvent.EventType.COIN_FLIPPED, playerId, null, playerId, 0, event.getFlag()));
        }
        return event.getFlag();
    }

    @Override
    public int rollDice(Game game, int numSides) {
        return this.rollDice(game, null, numSides);
    }

    /**
     * @param game
     * @param appliedEffects
     * @param numSides Number of sides the dice has
     * @return the number that the player rolled
     */
    @Override
    public int rollDice(Game game, ArrayList<UUID> appliedEffects, int numSides) {
        int result = RandomUtil.nextInt(numSides) + 1;
        if (!game.isSimulation()) {
            game.informPlayers("[Roll a die] " + getLogName() + " rolled a " + result + " on a " + numSides + " sided dice");
        }
        GameEvent event = new GameEvent(GameEvent.EventType.ROLL_DICE, playerId, null, playerId, result, true);
        event.setAppliedEffects(appliedEffects);
        event.setAmount(result);
        event.setData(numSides + "");
        if (!game.replaceEvent(event)) {
            GameEvent ge = new GameEvent(GameEvent.EventType.DICE_ROLLED, playerId, null, playerId, event.getAmount(), event.getFlag());
            ge.setData(numSides + "");
            game.fireEvent(ge);
        }
        return event.getAmount();
    }

    @Override
    public PlanarDieRoll rollPlanarDie(Game game) {
        return this.rollPlanarDie(game, null);
    }

    @Override
    public PlanarDieRoll rollPlanarDie(Game game, ArrayList<UUID> appliedEffects) {
        return rollPlanarDie(game, appliedEffects, 2, 2);
    }

    /**
     * @param game
     * @param appliedEffects
     * @param numberChaosSides The number of chaos sides the planar die
     * currently has (normally 1 but can be 5)
     * @param numberPlanarSides The number of chaos sides the planar die
     * currently has (normally 1)
     * @return the outcome that the player rolled. Either ChaosRoll, PlanarRoll
     * or NilRoll
     */
    @Override
    public PlanarDieRoll rollPlanarDie(Game game, ArrayList<UUID> appliedEffects, int numberChaosSides, int numberPlanarSides) {
        int result = RandomUtil.nextInt(9) + 1;
        PlanarDieRoll roll = PlanarDieRoll.NIL_ROLL;
        if (numberChaosSides + numberPlanarSides > 9) {
            numberChaosSides = 2;
            numberPlanarSides = 2;
        }
        if (result <= numberChaosSides) {
            roll = PlanarDieRoll.CHAOS_ROLL;
        } else if (result > 9 - numberPlanarSides) {
            roll = PlanarDieRoll.PLANAR_ROLL;
        }
        if (!game.isSimulation()) {
            game.informPlayers("[Roll the planar die] " + getLogName() + " rolled a " + roll + " on the planar die");
        }
        GameEvent event = new GameEvent(GameEvent.EventType.ROLL_PLANAR_DIE, playerId, null, playerId, result, true);
        event.setAppliedEffects(appliedEffects);
        event.setData(roll + "");
        if (!game.replaceEvent(event)) {
            GameEvent ge = new GameEvent(GameEvent.EventType.PLANAR_DIE_ROLLED, playerId, null, playerId, event.getAmount(), event.getFlag());
            ge.setData(roll + "");
            game.fireEvent(ge);
        }
        return roll;
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

    @Override
    public ManaOptions getManaAvailable(Game game) {
        ManaOptions available = new ManaOptions();

        List<Abilities<ActivatedManaAbilityImpl>> sourceWithoutManaCosts = new ArrayList<>();
        List<Abilities<ActivatedManaAbilityImpl>> sourceWithCosts = new ArrayList<>();
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            Boolean canUse = null;
            boolean canAdd = false;
            boolean withCost = false;
            Abilities<ActivatedManaAbilityImpl> manaAbilities = permanent.getAbilities().getAvailableActivatedManaAbilities(Zone.BATTLEFIELD, game);
            for (ActivatedManaAbilityImpl ability : manaAbilities) {
                if (canUse == null) {
                    canUse = permanent.canUseActivatedAbilities(game);
                }
                if (canUse && ability.canActivate(playerId, game).canActivate()) {
                    canAdd = true;
                    if (!ability.getManaCosts().isEmpty()) {
                        withCost = true;
                        break;
                    }
                }
            }
            if (canAdd) {
                if (withCost) {
                    sourceWithCosts.add(manaAbilities);
                } else {
                    sourceWithoutManaCosts.add(manaAbilities);
                }
            }
        }

        for (Abilities<ActivatedManaAbilityImpl> manaAbilities : sourceWithoutManaCosts) {
            available.addMana(manaAbilities, game);
        }
        for (Abilities<ActivatedManaAbilityImpl> manaAbilities : sourceWithCosts) {
            available.removeDuplicated();
            available.addManaWithCost(manaAbilities, game);
        }

        // remove duplicated variants (see ManaOptionsTest for info - when that rises)
        available.removeDuplicated();

        return available;
    }

    // returns only mana producers that don't require mana payment
    protected List<MageObject> getAvailableManaProducers(Game game) {
        List<MageObject> result = new ArrayList<>();
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
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
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            Boolean canUse = null;
            for (ActivatedManaAbilityImpl ability : permanent.getAbilities().getActivatedManaAbilities(Zone.BATTLEFIELD)) {
                if (canUse == null) {
                    canUse = permanent.canUseActivatedAbilities(game);
                }
                if (canUse && ability.canActivate(playerId, game).canActivate() && !ability.getManaCosts().isEmpty()) {
                    result.add(permanent);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * @param ability
     * @param available if null, it won't be checked if enough mana is available
     * @param sourceObject
     * @param game
     * @return
     */
    protected boolean canPlay(ActivatedAbility ability, ManaOptions available, MageObject sourceObject, Game game) {
        if (!(ability instanceof ActivatedManaAbilityImpl)) {
            ActivatedAbility copy = ability.copy();
            copy.setCheckPlayableMode(); // prevents from endless loops for asking player to use effects by checking this mode
            if (!copy.canActivate(playerId, game).canActivate()) {
                return false;
            }
            if (available != null) {
                game.getContinuousEffects().costModification(copy, game);
            }

            Card card = game.getCard(ability.getSourceId());
            if (card != null) {
                for (Ability ability0 : card.getAbilities()) {
                    if (ability0 instanceof AdjustingSourceCosts) {
                        // A workaround for Issue#457
                        if (!(ability0 instanceof ConvokeAbility)) {
                            ((AdjustingSourceCosts) ability0).adjustCosts(copy, game);
                        }
                    }
                }
            }
            boolean canBeCastRegularly = true;
            if (copy instanceof SpellAbility && copy.getManaCosts().isEmpty() && copy.getCosts().isEmpty()) {
                // 117.6. Some mana costs contain no mana symbols. This represents an unpayable cost...
                // 117.6a (...) If an alternative cost is applied to an unpayable cost,
                // including an effect that allows a player to cast a spell without paying its mana cost, the alternative cost may be paid.
                canBeCastRegularly = false;
            }
            if (canBeCastRegularly) {
                ManaOptions abilityOptions = copy.getMinimumCostToActivate(playerId, game);
                if (abilityOptions.isEmpty()) {
                    return true;
                } else {
                    if (available == null) {
                        return true;
                    }
                    MageObjectReference permittingObject = game.getContinuousEffects().asThough(ability.getSourceId(), AsThoughEffectType.SPEND_OTHER_MANA, ability, ability.getControllerId(), game);
                    for (Mana mana : abilityOptions) {
                        for (Mana avail : available) {
                            if (permittingObject != null && mana.count() <= avail.count()) {
                                return true;
                            }
                            if (mana.enough(avail)) { // here we need to check if spend mana as though allow to pay the mana cost
                                return true;
                            }
                        }
                    }
                }
            }

            for (Ability objectAbility : sourceObject.getAbilities()) {
                if (objectAbility instanceof AlternativeCostSourceAbility) {
                    if (objectAbility.getCosts().canPay(ability, ability.getSourceId(), playerId, game)) {
                        return true;
                    }
                }
            }
            if (canPlayCardByAlternateCost(card, available, ability, game)) {
                return true;
            }
        }
        return false;
    }

    protected boolean canPlayCardByAlternateCost(Card sourceObject, ManaOptions available, Ability ability, Game game) {
        if (sourceObject != null && !(sourceObject instanceof Permanent)) {
            for (Ability alternateSourceCostsAbility : sourceObject.getAbilities()) {
                // if cast for noMana no Alternative costs are allowed
                if (alternateSourceCostsAbility instanceof AlternativeSourceCosts) {
                    if (((AlternativeSourceCosts) alternateSourceCostsAbility).isAvailable(ability, game)) {
                        if (alternateSourceCostsAbility.getCosts().canPay(ability, playerId, playerId, game)) {
                            ManaCostsImpl manaCosts = new ManaCostsImpl();
                            for (Cost cost : alternateSourceCostsAbility.getCosts()) {
                                if (cost instanceof ManaCost) {
                                    manaCosts.add((ManaCost) cost);
                                }
                            }

                            if (manaCosts.isEmpty()) {
                                return true;
                            } else {
                                if (available == null) {
                                    return true;
                                }
                                for (Mana mana : manaCosts.getOptions()) {
                                    for (Mana avail : available) {
                                        if (mana.enough(avail)) {
                                            return true;
                                        }
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
                        if (((Ability) alternateSourceCosts).getCosts().canPay(ability, playerId, playerId, game)) {
                            ManaCostsImpl manaCosts = new ManaCostsImpl();
                            for (Cost cost : ((Ability) alternateSourceCosts).getCosts()) {
                                if (cost instanceof ManaCost) {
                                    manaCosts.add((ManaCost) cost);
                                }
                            }

                            if (manaCosts.isEmpty()) {
                                return true;
                            } else {
                                for (Mana mana : manaCosts.getOptions()) {
                                    for (Mana avail : available) {
                                        if (mana.enough(avail)) {
                                            return true;
                                        }
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

    protected boolean canLandPlayAlternateSourceCostsAbility(Card sourceObject, ManaOptions available, Ability ability, Game game) {
        if (!(sourceObject instanceof Permanent)) {
            Ability sourceAbility = null;
            for (Ability landAbility : sourceObject.getAbilities()) {
                if (landAbility.getAbilityType() == AbilityType.PLAY_LAND) {
                    sourceAbility = landAbility;
                    break;
                }
            }
            if (sourceAbility != null && ((AlternativeSourceCosts) ability).isAvailable(sourceAbility, game)) {
                if (ability.getCosts().canPay(ability, sourceObject.getId(), this.getId(), game)) {
                    ManaCostsImpl manaCosts = new ManaCostsImpl();
                    for (Cost cost : ability.getCosts()) {
                        if (cost instanceof ManaCost) {
                            manaCosts.add((ManaCost) cost);
                        }
                    }

                    if (manaCosts.isEmpty()) {
                        return true;
                    } else {
                        for (Mana mana : manaCosts.getOptions()) {
                            for (Mana avail : available) {
                                if (mana.enough(avail)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private void getPlayableFromGraveyardCard(Game game, Card card, Abilities<Ability> candidateAbilities, ManaOptions availableMana, List<Ability> output) {
        MageObjectReference permittingObject = game.getContinuousEffects().asThough(card.getId(), AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, null, this.getId(), game);
        for (ActivatedAbility ability : candidateAbilities.getActivatedAbilities(Zone.ALL)) {
            boolean possible = false;
            if (ability.getZone().match(Zone.GRAVEYARD)) {
                possible = true;
            } else if (ability.getZone().match(Zone.HAND) && (ability instanceof SpellAbility || ability instanceof PlayLandAbility)) {
                if (permittingObject != null || canPlayCardsFromGraveyard()) {
                    possible = true;
                }
            }
            if (possible && canPlay(ability, availableMana, card, game)) {
                output.add(ability);
            }
        }
    }

    @Override
    public List<Ability> getPlayable(Game game, boolean hidden) {
        List<Ability> playable = new ArrayList<>();

        if (!shouldSkipGettingPlayable(game)) {

            ManaOptions availableMana = getManaAvailable(game);
            availableMana.addMana(manaPool.getMana());
            for (ConditionalMana conditionalMana : manaPool.getConditionalMana()) {
                availableMana.addMana(conditionalMana);
            }
            if (hidden) {
                for (Card card : hand.getUniqueCards(game)) {
                    for (Ability ability : card.getAbilities(game)) { // gets this activated ability from hand? (Morph?)
                        if (ability.getZone().match(Zone.HAND)) {
                            if (ability instanceof ActivatedAbility) {
                                if (!(ability instanceof PlayLandAbility)
                                        || !game.getContinuousEffects().preventedByRuleModification(GameEvent.getEvent(GameEvent.EventType.PLAY_LAND, ability.getSourceId(), ability.getSourceId(), playerId), ability, game, true)) {
                                    if (canPlay((ActivatedAbility) ability, availableMana, card, game)) {
                                        playable.add(ability);
                                    }
                                }
                            } else if (ability instanceof AlternativeSourceCosts) {
                                if (card.isLand()) {
                                    if (canLandPlayAlternateSourceCostsAbility(card, availableMana, ability, game)) { // e.g. Land with Morph
                                        playable.add(ability);
                                    }
                                } else if (card.isCreature()) { // e.g. makes a card available for play by Morph if the card may not be cast normally
                                    if (!playable.contains(card.getSpellAbility())) {
                                        if (((AlternativeSourceCosts) ability).isAvailable(card.getSpellAbility(), game)) {
                                            playable.add(card.getSpellAbility());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            for (Card card : graveyard.getUniqueCards(game)) {
                // Handle split cards in graveyard to support Aftermath
                if (card instanceof SplitCard) {
                    SplitCard splitCard = (SplitCard) card;
                    getPlayableFromGraveyardCard(game, splitCard.getLeftHalfCard(), splitCard.getLeftHalfCard().getAbilities(), availableMana, playable);
                    getPlayableFromGraveyardCard(game, splitCard.getRightHalfCard(), splitCard.getRightHalfCard().getAbilities(), availableMana, playable);
                    getPlayableFromGraveyardCard(game, splitCard, splitCard.getSharedAbilities(), availableMana, playable);
                } else {
                    getPlayableFromGraveyardCard(game, card, card.getAbilities(), availableMana, playable);
                }

                // Other activated abilities
                LinkedHashMap<UUID, ActivatedAbility> useable = new LinkedHashMap<>();
                getOtherUseableActivatedAbilities(card, Zone.GRAVEYARD, game, useable);
                playable.addAll(useable.values());
            }
            for (ExileZone exile : game.getExile().getExileZones()) {
                for (Card card : exile.getCards(game)) {
                    if (null != game.getContinuousEffects().asThough(card.getId(), AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, null, this.getId(), game)) {
                        for (Ability ability : card.getAbilities()) {
                            if (ability.getZone().match(Zone.HAND)) {
                                ability.setControllerId(this.getId()); // controller must be set for case owner != caster
                                if (ability instanceof ActivatedAbility) {
                                    if (((ActivatedAbility) ability).canActivate(playerId, game).canActivate()) {
                                        playable.add(ability);
                                    }
                                }
                                ability.setControllerId(card.getOwnerId());
                            }
                        }
                    }
                }
            }
            // Check to play revealed cards
            for (Cards cards : game.getState().getRevealed().values()) {
                for (Card card : cards.getCards(game)) {
                    if (null != game.getContinuousEffects().asThough(card.getId(), AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, null, this.getId(), game)) {
                        for (ActivatedAbility ability : card.getAbilities().getActivatedAbilities(Zone.HAND)) {
                            if (ability instanceof SpellAbility || ability instanceof PlayLandAbility) {
                                playable.add(ability);
                            }
                        }
                    }
                }
            }
            // check if it's possible to play the top card of a library
            for (UUID playerInRangeId : game.getState().getPlayersInRange(getId(), game)) {
                Player player = game.getPlayer(playerInRangeId);
                if (player != null) {
                    if (/*player.isTopCardRevealed() &&*/player.getLibrary().hasCards()) {
                        Card card = player.getLibrary().getFromTop(game);
                        if (null != game.getContinuousEffects().asThough(card.getId(), AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, null, getId(), game)) {
                            for (ActivatedAbility ability : card.getAbilities().getActivatedAbilities(Zone.HAND)) {
                                if (ability instanceof SpellAbility || ability instanceof PlayLandAbility) {
                                    playable.add(ability);
                                }
                            }
                        }
                    }
                }
            }
            // eliminate duplicate activated abilities
            Map<String, Ability> playableActivated = new HashMap<>();
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
                LinkedHashMap<UUID, ActivatedAbility> useableAbilities = getUseableActivatedAbilities(permanent, Zone.BATTLEFIELD, game);
                for (ActivatedAbility ability : useableAbilities.values()) {
                    playableActivated.putIfAbsent(ability.toString(), ability);
                }
            }
            // activated abilities from stack objects
            for (StackObject stackObject : game.getState().getStack()) {
                for (ActivatedAbility ability : stackObject.getAbilities().getActivatedAbilities(Zone.STACK)) {
                    if (ability != null && canPlay(ability, availableMana, game.getObject(ability.getSourceId()), game)) {
                        playableActivated.put(ability.toString(), ability);
                    }

                }
            }
            // activated abilities from objects in the command zone (emblems or commanders)
            for (CommandObject commandObject : game.getState().getCommand()) {
                for (ActivatedAbility ability : commandObject.getAbilities().getActivatedAbilities(Zone.COMMAND)) {
                    if (ability.isControlledBy(getId()) && canPlay(ability, availableMana, game.getObject(ability.getSourceId()), game)) {
                        playableActivated.put(ability.toString(), ability);
                    }

                }
            }
            playable.addAll(playableActivated.values());
        }

        return playable;
    }

    /**
     * Creates a list of card ids that are currently playable.<br>
     * Used to mark the playable cards in GameView
     *
     * @param game
     * @return A Set of cardIds that are playable
     */
    @Override
    public Set<UUID> getPlayableInHand(Game game
    ) {
        Set<UUID> playable = new HashSet<>();
        if (!shouldSkipGettingPlayable(game)) {
            ManaOptions available = getManaAvailable(game);
            available.addMana(manaPool.getMana());

            for (Card card : hand.getCards(game)) {
                Abilities:
                for (Ability ability : card.getAbilities()) {
                    if (ability.getZone().match(Zone.HAND)) {
                        switch (ability.getAbilityType()) {
                            case PLAY_LAND:
                                if (game.getContinuousEffects().preventedByRuleModification(GameEvent.getEvent(GameEvent.EventType.PLAY_LAND, ability.getSourceId(), ability.getSourceId(), playerId), ability, game, true)) {
                                    break;
                                }
                                if (canPlay((ActivatedAbility) ability, available, card, game)) {
                                    playable.add(card.getId());
                                    break Abilities;
                                }
                                break;
                            case ACTIVATED:
                            case SPELL:
                                if (canPlay((ActivatedAbility) ability, available, card, game)) {
                                    playable.add(card.getId());
                                    break Abilities;
                                }
                                break;
                            case STATIC:
                                if (card.isLand() && ability instanceof AlternativeSourceCosts) {
                                    if (canLandPlayAlternateSourceCostsAbility(card, available, ability, game)) { // e.g. Land with Morph
                                        if (game.canPlaySorcery(getId())) {
                                            playable.add(card.getId());
                                        }
                                        break Abilities;
                                    }
                                }
                        }
                    }
                }
            }
        }

        return playable;
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
            if (game.getPhase() != null && game.getPhase().getStep() != null && phaseStep.getKey() == game.getPhase().getStep().getType()) {
                if (phaseStep.getValue() == null || phaseStep.getValue() == game.getPhase().getStep().getStepPart()) {
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
            newOption.getModes().getSelectedModes().clear();
            newOption.getModes().getSelectedModes().add(mode.getId());
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
    public boolean isTestMode() {
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
    public void setAllowBadMoves(boolean allowBadMoves
    ) {
        // do nothing
    }

    @Override
    public boolean canPayLifeCost() {
        return isLifeTotalCanChange() && canPayLifeCost;
    }

    @Override
    public void setCanPayLifeCost(boolean canPayLifeCost
    ) {
        this.canPayLifeCost = canPayLifeCost;
    }

    @Override
    public boolean canPaySacrificeCost(Permanent permanent, UUID sourceId,
            UUID controllerId, Game game
    ) {
        return sacrificeCostFilter == null || !sacrificeCostFilter.match(permanent, sourceId, controllerId, game);
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
    public void setLoseByZeroOrLessLife(boolean loseByZeroOrLessLife
    ) {
        this.loseByZeroOrLessLife = loseByZeroOrLessLife;
    }

    @Override
    public boolean canPlayCardsFromGraveyard() {
        return canPlayCardsFromGraveyard;
    }

    @Override
    public void setPlayCardsFromGraveyard(boolean playCardsFromGraveyard
    ) {
        this.canPlayCardsFromGraveyard = playCardsFromGraveyard;
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
    public synchronized void resetStoredBookmark(Game game
    ) {
        if (this.storedBookmark != -1) {
            game.removeBookmark(this.storedBookmark);
        }
        setStoredBookmark(-1);
    }

    @Override
    public boolean lookAtFaceDownCard(Card card, Game game
    ) {
        if (null != game.getContinuousEffects().asThough(card.getId(), AsThoughEffectType.LOOK_AT_FACE_DOWN, card.getSpellAbility(), this.getId(), game)) {
            if (chooseUse(Outcome.Benefit, "Look at that card?", null, game)) {
                Cards cards = new CardsImpl(card);
                this.lookAtCards(getName() + " - " + sdf.format(System.currentTimeMillis()), cards, game);
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
    public void setReachedNextTurnAfterLeaving(boolean reachedNextTurnAfterLeaving
    ) {
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
    public boolean moveCards(Card card, Zone toZone,
            Ability source, Game game,
            boolean tapped, boolean faceDown, boolean byOwner, List<UUID> appliedEffects
    ) {
        Set<Card> cardList = new HashSet<>();
        if (card != null) {
            cardList.add(card);
        }
        return moveCards(cardList, toZone, source, game, tapped, faceDown, byOwner, appliedEffects);
    }

    @Override
    public boolean moveCards(Cards cards, Zone toZone,
            Ability source, Game game
    ) {
        return moveCards(cards.getCards(game), toZone, source, game);
    }

    @Override
    public boolean moveCards(Set<Card> cards, Zone toZone,
            Ability source, Game game
    ) {
        return moveCards(cards, toZone, source, game, false, false, false, null);
    }

    @Override
    public boolean moveCards(Set<Card> cards, Zone toZone,
            Ability source, Game game,
            boolean tapped, boolean faceDown, boolean byOwner, List<UUID> appliedEffects
    ) {
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
                    ZoneChangeEvent event = new ZoneChangeEvent(card.getId(), source.getSourceId(), byOwner ? card.getOwnerId() : getId(), fromZone, Zone.BATTLEFIELD, appliedEffects);
                    infoList.add(new ZoneChangeInfo.Battlefield(event, faceDown, tapped));
                }
                infoList = ZonesHandler.moveCards(infoList, game);
                for (ZoneChangeInfo info : infoList) {
                    Permanent permanent = game.getPermanent(info.event.getTargetId());
                    if (permanent != null) {
                        successfulMovedCards.add(permanent);
                        if (!game.isSimulation()) {
                            Player eventPlayer = game.getPlayer(info.event.getPlayerId());
                            if (eventPlayer != null && fromZone != null) {
                                game.informPlayers(eventPlayer.getLogName() + " puts "
                                        + (info.faceDown ? "a card face down " : permanent.getLogName()) + " from "
                                        + fromZone.toString().toLowerCase(Locale.ENGLISH) + " onto the Battlefield");
                            }
                        }
                    }
                }
                game.applyEffects();
                break;
            case HAND:
                for (Card card : cards) {
                    fromZone = game.getState().getZone(card.getId());
                    boolean hideCard = fromZone == Zone.LIBRARY
                            || (card.isFaceDown(game) && fromZone != Zone.STACK && fromZone != Zone.BATTLEFIELD);
                    if (moveCardToHandWithInfo(card, source == null ? null : source.getSourceId(), game, !hideCard)) {
                        successfulMovedCards.add(card);
                    }
                }
                break;
            case EXILED:
                for (Card card : cards) {
                    fromZone = game.getState().getZone(card.getId());
                    boolean withName = (fromZone == Zone.BATTLEFIELD || fromZone == Zone.STACK) || !card.isFaceDown(game);
                    if (moveCardToExileWithInfo(card, null, "", source == null ? null : source.getSourceId(), game, fromZone, withName)) {
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
                    if (moveCardToLibraryWithInfo(card, source == null ? null : source.getSourceId(), game, fromZone, true, !hideCard)) {
                        successfulMovedCards.add(card);
                    }
                }
                break;
            case OUTSIDE:
                for (Card card : cards) {
                    if (card instanceof Permanent) {
                        game.getBattlefield().removePermanent(((Permanent) card).getId());
                        ZoneChangeEvent event = new ZoneChangeEvent(card.getId(), (source == null ? null : source.getSourceId()),
                                byOwner ? card.getOwnerId() : getId(), Zone.BATTLEFIELD, Zone.OUTSIDE, appliedEffects);
                        game.fireEvent(event);
                    }
                }
                break;
            default:
                throw new UnsupportedOperationException("to Zone" + toZone.toString() + " not supported yet");
        }
        return !successfulMovedCards.isEmpty();
    }

    @Override
    public boolean moveCardsToExile(Card card, Ability source,
            Game game, boolean withName, UUID exileId,
            String exileZoneName
    ) {
        Set<Card> cards = new HashSet<>();
        cards.add(card);
        return moveCardsToExile(cards, source, game, withName, exileId, exileZoneName);
    }

    @Override
    public boolean moveCardsToExile(Set<Card> cards, Ability source,
            Game game, boolean withName, UUID exileId,
            String exileZoneName
    ) {
        if (cards.isEmpty()) {
            return true;
        }
        boolean result = false;
        for (Card card : cards) {
            Zone fromZone = game.getState().getZone(card.getId());
            result |= moveCardToExileWithInfo(card, exileId, exileZoneName, source == null ? null : source.getSourceId(), game, fromZone, withName);
        }
        return result;
    }

    @Override
    public boolean moveCardToHandWithInfo(Card card, UUID sourceId,
            Game game
    ) {
        return this.moveCardToHandWithInfo(card, sourceId, game, true);
    }

    @Override
    public boolean moveCardToHandWithInfo(Card card, UUID sourceId,
            Game game, boolean withName
    ) {
        boolean result = false;
        Zone fromZone = game.getState().getZone(card.getId());
        if (fromZone == Zone.BATTLEFIELD && !(card instanceof Permanent)) {
            card = game.getPermanent(card.getId());
        }
        if (card.moveToZone(Zone.HAND, sourceId, game, false)) {
            if (card instanceof PermanentCard && game.getCard(card.getId()) != null) {
                card = game.getCard(card.getId());
            }
            if (!game.isSimulation()) {
                game.informPlayers(getLogName() + " puts "
                        + (withName ? card.getLogName() : (card.isFaceDown(game) ? "a face down card" : "a card"))
                        + " from " + fromZone.toString().toLowerCase(Locale.ENGLISH) + ' '
                        + (card.isOwnedBy(this.getId()) ? "into their hand" : "into its owner's hand")
                );
            }
            result = true;
        }
        return result;
    }

    @Override
    public Set<Card> moveCardsToGraveyardWithInfo(Set<Card> allCards, Ability source,
            Game game, Zone fromZone
    ) {
        UUID sourceId = source == null ? null : source.getSourceId();
        Set<Card> movedCards = new LinkedHashSet<>();
        while (!allCards.isEmpty()) {
            // identify cards from one owner
            Cards cards = new CardsImpl();
            UUID ownerId = null;
            for (Iterator<Card> it = allCards.iterator(); it.hasNext();) {
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
                    if (cards.size() > 3) {
                        chooseOrder = choosingPlayer.chooseUse(Outcome.Neutral, "Would you like to choose the order the cards go to graveyard?", source, game);
                    }
                }
                if (chooseOrder) {
                    TargetCard target = new TargetCard(fromZone, new FilterCard("card to put on the top of your graveyard (last one chosen will be topmost)"));
                    target.setRequired(true);
                    while (choosingPlayer.canRespond() && cards.size() > 1) {
                        choosingPlayer.chooseTarget(Outcome.Neutral, cards, target, source, game);
                        UUID targetObjectId = target.getFirstTarget();
                        Card card = cards.get(targetObjectId, game);
                        cards.remove(targetObjectId);
                        if (card != null) {
                            fromZone = game.getState().getZone(card.getId());
                            if (choosingPlayer.moveCardToGraveyardWithInfo(card, sourceId, game, fromZone)) {
                                movedCards.add(card);
                            }
                        }
                        target.clearChosen();
                    }
                    if (cards.size() == 1) {
                        Card card = cards.getCards(game).iterator().next();
                        if (card != null && choosingPlayer.moveCardToGraveyardWithInfo(card, sourceId, game, fromZone)) {
                            movedCards.add(card);
                        }
                    }
                } else {
                    for (Card card : cards.getCards(game)) {
                        if (choosingPlayer.moveCardToGraveyardWithInfo(card, sourceId, game, fromZone)) {
                            movedCards.add(card);
                        }
                    }
                }
            }
        }
        return movedCards;
    }

    @Override
    public boolean moveCardToGraveyardWithInfo(Card card, UUID sourceId,
            Game game, Zone fromZone
    ) {
        if (card == null) {
            return false;
        }
        boolean result = false;
        //    Zone fromZone = game.getState().getZone(card.getId());
        if (card.moveToZone(Zone.GRAVEYARD, sourceId, game, fromZone != null ? fromZone == Zone.BATTLEFIELD : false)) {
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
                game.informPlayers(sb.toString());
            }
            result = true;
        }
        return result;
    }

    @Override
    public boolean moveCardToLibraryWithInfo(Card card, UUID sourceId,
            Game game, Zone fromZone,
            boolean toTop, boolean withName
    ) {
        if (card == null) {
            return false;
        }
        boolean result = false;
        if (card.moveToZone(Zone.LIBRARY, sourceId, game, toTop)) {
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
                game.informPlayers(sb.toString());
            }
            result = true;
        }
        return result;
    }

    @Override
    public boolean moveCardToExileWithInfo(Card card, UUID exileId, String exileName, UUID sourceId,
            Game game, Zone fromZone, boolean withName) {
        if (card == null) {
            return false;
        }
        boolean result = false;
        if (card.moveToExile(exileId, exileName, sourceId, game)) {
            if (!game.isSimulation()) {
                if (card instanceof PermanentCard) {
                    // in case it's face down or name was changed by copying from other permanent
                    Card basicCard = game.getCard(card.getId());
                    if (basicCard != null) {
                        card = basicCard;
                    }
                } else if (card instanceof Spell) {
                    final Spell spell = (Spell) card;
                    if (spell.isCopiedSpell()) {
                        // Copied spell, only remove from stack
                        game.getStack().remove(spell, game);
                    }
                }
                game.informPlayers(this.getLogName() + " moves " + (withName ? card.getLogName() + (card.isCopy() ? " (Copy)" : "") : "a card face down") + ' '
                        + (fromZone != null ? "from " + fromZone.toString().toLowerCase(Locale.ENGLISH) + ' ' : "") + "to the exile zone");

            }
            result = true;
        }
        return result;
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
    public boolean isRequestToShowHandCardsAllowed() {
        return userData.isAllowRequestShowHandCards();
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
        game.informPlayers(getLogName() + " scries " + value);
        Cards cards = new CardsImpl();
        cards.addAll(getLibrary().getTopCards(game, value));
        if (!cards.isEmpty()) {
            String text;
            if (cards.size() == 1) {
                text = "card if you want to put it to the bottom of your library (Scry)";
            } else {
                text = "cards you want to put on the bottom of your library (Scry)";
            }
            TargetCard target = new TargetCard(0, cards.size(), Zone.LIBRARY, new FilterCard(text));
            chooseTarget(Outcome.Benefit, cards, target, source, game);
            putCardsOnBottomOfLibrary(new CardsImpl(target.getTargets()), game, source, true);
            cards.removeAll(target.getTargets());
            putCardsOnTopOfLibrary(cards, game, source, true);
        }
        game.fireEvent(new GameEvent(GameEvent.EventType.SCRY, getId(), source == null ? null : source.getSourceId(), getId(), value, true));
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
}
