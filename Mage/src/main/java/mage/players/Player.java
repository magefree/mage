package mage.players;

import mage.*;
import mage.abilities.*;
import mage.abilities.costs.AlternativeSourceCosts;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.mana.ManaOptions;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.decks.Deck;
import mage.choices.Choice;
import mage.constants.*;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.designations.Designation;
import mage.designations.DesignationType;
import mage.filter.FilterCard;
import mage.filter.FilterMana;
import mage.filter.FilterPermanent;
import mage.game.*;
import mage.game.combat.CombatGroup;
import mage.game.draft.Draft;
import mage.game.events.GameEvent;
import mage.game.match.Match;
import mage.game.match.MatchPlayer;
import mage.game.permanent.Permanent;
import mage.game.tournament.Tournament;
import mage.players.net.UserData;
import mage.target.Target;
import mage.target.TargetAmount;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.util.Copyable;
import mage.util.MultiAmountMessage;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public interface Player extends MageItem, Copyable<Player> {

    /**
     * Enum used to indicate what each player is allowed to spend life on.
     * By default it is set to `allAbilities`, but can be changed by effects.
     * E.g. Angel of Jubilation sets it to `nonSpellnonActivatedAbilities`,
     * and Karn's Sylex sets it to `onlyManaAbilities`.
     * <p>
     * <p>
     * Default is PayLifeCostLevel.allAbilities.
     */
    enum PayLifeCostLevel {
        allAbilities, nonSpellnonActivatedAbilities, onlyManaAbilities, none
    }

    /**
     * Current player is real life player (human). Try to use in GUI and network engine only.
     * <p>
     * WARNING, you must use isComputer instead isHuman in card's code (for good Human/AI logic testing in unit tests)
     * TODO: check combat code and other and replace isHuman to isComputer usage if possible (if AI support that actions)
     *
     * @return
     */
    boolean isHuman();

    boolean isTestsMode();

    /**
     * Current player is AI. Use it in card's code and all other places.
     * <p>
     * It help to split Human/AI logic and test both by unit tests.
     * <p>
     * Usage example: AI hint to skip or auto-calculate choices instead call of real choose dialogs
     * - unit tests for Human logic: call normal commands
     * - unit tests for AI logic: call aiXXX commands
     *
     * @return
     */
    default boolean isComputer() {
        return !isHuman();
    }

    String getName();

    String getLogName();

    RangeOfInfluence getRange();

    Library getLibrary();

    Cards getSideboard();

    Graveyard getGraveyard();

    Abilities<Ability> getAbilities();

    void addAbility(Ability ability);

    /**
     * The counters should be manipulated (adding or removing counters) with the appropriate addCounters/removeCounters/getCounterCount methods.
     * This returns a copy for specific usage, to make sure the Player's counters are not altered from there.
     */
    Counters getCountersAsCopy();

    int getLife();

    void initLife(int life);

    /**
     * Set player's life
     *
     * @param life
     * @param game
     * @param source can be null for cheats or game startup setup
     */
    void setLife(int life, Game game, Ability source);

    /**
     * @param amount     amount of life loss
     * @param game
     * @param source     can be null for default game events like mana burn
     * @param atCombat   was the source combat damage
     * @param attackerId id of the attacker for combat events (can be null)
     * @return
     */
    int loseLife(int amount, Game game, Ability source, boolean atCombat, UUID attackerId);

    int loseLife(int amount, Game game, Ability source, boolean atCombat);

    /**
     * @param amount
     * @param game
     * @param source can be null for default game events life lifelink damage
     * @return
     */
    int gainLife(int amount, Game game, Ability source);

    void exchangeLife(Player player, Ability source, Game game);

    int damage(int damage, Ability source, Game game);

    int damage(int damage, UUID attackerId, Ability source, Game game);

    int damage(int damage, UUID attackerId, Ability source, Game game, boolean combatDamage, boolean preventable);

    int damage(int damage, UUID attackerId, Ability source, Game game, boolean combatDamage, boolean preventable, List<UUID> appliedEffects);

    // to handle rule changing effects (613.10)
    boolean isCanLoseLife();

    void setCanLoseLife(boolean canLoseLife);

    void setCanGainLife(boolean canGainLife);

    boolean isCanGainLife();

    /**
     * Is the player allowed to pay life for casting spells or activate activated abilities
     *
     * @param payLifeCostLevel
     */

    void setPayLifeCostLevel(PayLifeCostLevel payLifeCostLevel);

    PayLifeCostLevel getPayLifeCostLevel();

    /**
     * Can the player pay life to cast or activate the given ability
     *
     * @param Ability
     * @return
     */
    boolean canPayLifeCost(Ability Ability);

    void setCanPaySacrificeCostFilter(FilterPermanent filter);

    FilterPermanent getSacrificeCostFilter();

    boolean canPaySacrificeCost(Permanent permanent, Ability source, UUID controllerId, Game game);

    void setLifeTotalCanChange(boolean lifeTotalCanChange);

    boolean isLifeTotalCanChange();

    void setLoseByZeroOrLessLife(boolean loseByZeroOrLessLife);

    boolean canLoseByZeroOrLessLife();

    void setPlayCardsFromGraveyard(boolean playCardsFromGraveyard);

    boolean canPlayCardsFromGraveyard();

    void setPlotFromTopOfLibrary(boolean canPlotFromTopOfLibrary);

    boolean canPlotFromTopOfLibrary();

    void setDrawsOnOpponentsTurn(boolean drawsOnOpponentsTurn);

    boolean isDrawsOnOpponentsTurn();

    /**
     * Returns alternative casting costs a player can cast spells for
     *
     * @return
     */
    List<AlternativeSourceCosts> getAlternativeSourceCosts();

    Cards getHand();

    void incrementLandsPlayed();

    void resetLandsPlayed();

    int getLandsPlayed();

    int getLandsPerTurn();

    void setLandsPerTurn(int landsPerTurn);

    int getMaxHandSize();

    void setMaxHandSize(int maxHandSize);

    int getMaxAttackedBy();

    void setMaxAttackedBy(int maxAttackedBy);

    boolean isPassed();

    void pass(Game game);

    void resetPassed();

    void resetPlayerPassedActions();

    boolean getPassedTurn();

    boolean getPassedUntilEndOfTurn();

    boolean getPassedUntilNextMain();

    boolean getPassedUntilStackResolved();

    boolean getPassedUntilEndStepBeforeMyTurn();

    boolean getPassedAllTurns();

    AbilityType getJustActivatedType();

    void setJustActivatedType(AbilityType abilityType);

    boolean hasLost();

    boolean hasDrew();

    boolean hasWon();

    boolean hasQuit();

    void quit(Game game);

    boolean hasTimerTimeout();

    void timerTimeout(Game game);

    boolean hasIdleTimeout();

    void idleTimeout(Game game);

    boolean hasLeft();

    /**
     * Player is still active in game (has not left, lost or won the game).
     *
     * @return
     */
    boolean isInGame();

    /**
     * Player is still active in game (has not left, lost or won the game) and
     * no abort state is given.
     *
     * @return
     */
    boolean canRespond();

    ManaPool getManaPool();

    Set<UUID> getInRange();

    boolean isTopCardRevealed();

    void setTopCardRevealed(boolean topCardRevealed);

    /**
     * User's settings like avatar or skip buttons.
     * WARNING, game related code must use controlling player settings only, e.g. getControllingPlayersUserData
     */
    UserData getUserData();

    void setUserData(UserData userData);

    boolean canLose(Game game);

    boolean autoLoseGame();

    /**
     * Returns a set of players which turns under you control. Doesn't include
     * yourself.
     *
     * @return
     */
    Set<UUID> getPlayersUnderYourControl();

    /**
     * Defines player whose turn this player controls at the moment.
     *
     * @param game
     * @param playerUnderControlId
     * @param info                 additional info to show in game logs like source
     */
    void controlPlayersTurn(Game game, UUID playerUnderControlId, String info);

    /**
     * Sets player {@link UUID} who controls this player's turn.
     *
     * @param playerId
     */
    void setTurnControlledBy(UUID playerId);

    List<UUID> getTurnControllers();

    /**
     * Current turn controller for a player (return own id for own control)
     */
    UUID getTurnControlledBy();

    /**
     * Resets players whose turns you control at the moment.
     */
    void resetOtherTurnsControlled();

    /**
     * Returns false in case player don't control the game.
     * <p>
     * Note: For effects like "You control target player during that player's
     * next turn".
     *
     * @return
     */
    boolean isGameUnderControl();

    /**
     * Returns false in case you don't control the game.
     * <p>
     * Note: For effects like "You control target player during that player's
     * next turn".
     *
     * @param value
     */
    void setGameUnderYourControl(boolean value);

    /**
     * Return player's turn control to prev player
     *
     * @param value
     * @param fullRestore return turn control to own
     */
    void setGameUnderYourControl(boolean value, boolean fullRestore);

    void setTestMode(boolean value);

    void setAllowBadMoves(boolean allowBadMoves);

    /**
     * Reset values before new game, e.g. for next game
     */
    void init(Game game);

    void useDeck(Deck deck, Game game);

    /**
     * Called before each applyEffects, to rest all what can be applied by
     * continuous effects
     */
    void reset();

    /**
     * @param source can be null for game default shuffle (non effects, example: mulligans)
     * @param game
     */
    void shuffleLibrary(Ability source, Game game);

    /**
     * Draw cards. If you call it in replace events then use method with event.appliedEffects param instead.
     * Returns 0 if replacement effect triggers on card draw.
     *
     * @param num
     * @param source can be null for game default draws (non effects, example: start of the turn)
     * @param game
     * @return
     */
    int drawCards(int num, Ability source, Game game);

    /**
     * Draw cards with applied effects, for replaceEvent
     * Returns 0 if replacement effect triggers on card draw.
     *
     * @param num
     * @param source can be null for game default draws (non effects, example: start of the turn)
     * @param game
     * @param event  original draw event in replacement code
     * @return
     */
    int drawCards(int num, Ability source, Game game, GameEvent event);

    boolean cast(SpellAbility ability, Game game, boolean noMana, ApprovingObject approvingObject);

    /**
     * Force player to choose spell ability to cast. Use it in effects while casting cards.
     * <p>
     * Commands order in all use cases:
     * - PlayFromNotOwnHandZone - true (if you put main id then all parts allows, if you put part id then only part allows)
     * - chooseAbilityForCast
     * - cast
     * - PlayFromNotOwnHandZone - false
     *
     * @param card
     * @param game
     * @param noMana
     * @return
     */
    SpellAbility chooseAbilityForCast(Card card, Game game, boolean noMana);

    ActivatedAbility chooseLandOrSpellAbility(Card card, Game game, boolean noMana);

    boolean removeFromHand(Card card, Game game);

    boolean removeFromBattlefield(Permanent permanent, Ability source, Game game);

    boolean putInGraveyard(Card card, Game game);

    boolean removeFromGraveyard(Card card, Game game);

    boolean removeFromLibrary(Card card, Game game);

    boolean searchLibrary(TargetCardInLibrary target, Ability source, Game game);

    /**
     * @param target
     * @param source
     * @param game
     * @param targetPlayerId player whose library will be searched
     * @return true if search was successful
     */

    boolean searchLibrary(TargetCardInLibrary target, Ability source, Game game, UUID targetPlayerId);

    /**
     * Gets a random card which matches the given filter and puts it into its owner's hand
     * Doesn't reveal the card
     */
    boolean seekCard(FilterCard filter, Ability source, Game game);

    /**
     * Reveals all players' libraries. Useful for abilities like Jace, Architect
     * of Thought's -8 that have effects that require information from all
     * libraries.
     *
     * @param source
     * @param game
     * @return
     */
    void lookAtAllLibraries(Ability source, Game game);

    boolean canPlayLand();

    /**
     * Plays a card (play land or cast spell). Works from any zones without timing restriction
     *
     * @param card            the card that can be cast
     * @param game
     * @param noMana          if it's a spell it can be cast without paying mana
     * @param approvingObject reference to the ability that allows to play the card
     * @return
     */
    // TODO: should have a version taking a PlayLandAbility or SpellAbility to handle MDFC/Zoetic Cavern/Adventure/etc...
    boolean playCard(Card card, Game game, boolean noMana, ApprovingObject approvingObject);

    /**
     * @param card         the land card to play
     * @param game
     * @param ignoreTiming false - it won't be checked if the stack is empty and
     *                     you are able to play a Sorcery. It's still checked,
     *                     if you are able to play a land concerning the number
     *                     of lands you already played.
     * @return
     */
    // TODO: should have a version taking a PlayLandAbility to handle MDFC/Zoetic Cavern/etc...
    boolean playLand(Card card, Game game, boolean ignoreTiming);

    boolean activateAbility(ActivatedAbility ability, Game game);

    boolean triggerAbility(TriggeredAbility ability, Game game);

    boolean canBeTargetedBy(MageObject sourceObject, UUID sourceControllerId, Ability source, Game game);

    boolean hasProtectionFrom(MageObject source, Game game);

    boolean flipCoin(Ability source, Game game, boolean winnable);

    boolean flipCoinResult(Game game);

    default int rollDice(Outcome outcome, Ability source, Game game, int numSides) {
        return rollDice(outcome, source, game, numSides, 1, 0).stream().findFirst().orElse(0);
    }

    List<Integer> rollDice(Outcome outcome, Ability source, Game game, int numSides, int numDice, int ignoreLowestAmount);

    int rollDieResult(int sides, Game game);

    default PlanarDieRollResult rollPlanarDie(Outcome outcome, Ability source, Game game) {
        return rollPlanarDie(outcome, source, game, GameOptions.PLANECHASE_PLANAR_DIE_CHAOS_SIDES, GameOptions.PLANECHASE_PLANAR_DIE_PLANAR_SIDES);
    }

    PlanarDieRollResult rollPlanarDie(Outcome outcome, Ability source, Game game, int numberChaosSides, int numberPlanarSides);

    Card discardOne(boolean random, boolean payForCost, Ability source, Game game);

    Cards discard(int amount, boolean random, boolean payForCost, Ability source, Game game);

    Cards discard(int minAmount, int maxAmount, boolean payForCost, Ability source, Game game);

    Cards discard(Cards cards, boolean payForCost, Ability source, Game game);

    void discardToMax(Game game);

    boolean discard(Card card, boolean payForCost, Ability source, Game game);

    void lost(Game game);

    void lostForced(Game game);

    void drew(Game game);

    void won(Game game);

    void leave();

    void concede(Game game);

    void abort();

    void abortReset();

    void signalPlayerConcede(boolean stopCurrentChooseDialog);

    void signalPlayerCheat();

    void skip();

    // priority, undo, ...
    void sendPlayerAction(PlayerAction passPriorityAction, Game game, Object data);

    int getStoredBookmark();

    /**
     * Save player's bookmark for undo, e.g. enable undo button on mana payment
     *
     * @param bookmark
     */
    void setStoredBookmark(int bookmark);

    /**
     * Reset player's bookmark, e.g. disable undo button
     *
     * @param game
     */
    void resetStoredBookmark(Game game);

    default GameState restoreState(int bookmark, String text, Game game) {
        GameState state = game.restoreState(bookmark, text);
        if (getStoredBookmark() >= bookmark) {
            resetStoredBookmark(game);
        }
        return state;
    }

    void revealCards(Ability source, Cards cards, Game game);

    void revealCards(String titleSuffix, Cards cards, Game game);

    void revealCards(Ability source, String titleSuffix, Cards cards, Game game);

    void revealCards(String titleSuffix, Cards cards, Game game, boolean postToLog);

    /**
     * Adds the cards to the reveal window and adds the source object's id name
     * to the title bar of the revealed cards window
     * <p>
     * Warning, if you use it from continuous effect, then check with extra call
     * isCanLookAtNextTopLibraryCard
     *
     * @param source
     * @param name
     * @param cards
     * @param game
     * @param postToLog
     */
    void revealCards(Ability source, String name, Cards cards, Game game, boolean postToLog);

    void lookAtCards(String name, Card card, Game game);

    void lookAtCards(String name, Cards cards, Game game);

    /**
     * Adds the cards to the look window and adds the source object's id name to
     * the title bar of the lookedAt window
     * <p>
     * Warning, if you use it from continuous effect, then check with extra call
     * isCanLookAtNextTopLibraryCard
     *
     * @param source
     * @param name
     * @param cards
     * @param game
     */
    void lookAtCards(Ability source, String name, Cards cards, Game game);

    @Override
    Player copy();

    void restore(Player player);

    void setResponseString(String responseString);

    void setResponseUUID(UUID responseUUID);

    void setResponseBoolean(Boolean responseBoolean);

    void setResponseInteger(Integer data);

    void setResponseManaType(UUID manaTypePlayerId, ManaType responseManaType);

    boolean priority(Game game);

    boolean choose(Outcome outcome, Target target, Ability source, Game game);

    boolean choose(Outcome outcome, Target target, Ability source, Game game, Map<String, Serializable> options);

    boolean choose(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game);

    boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game);

    boolean chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game);

    boolean chooseTargetAmount(Outcome outcome, TargetAmount target, Ability source, Game game);

    boolean chooseMulligan(Game game);

    boolean chooseUse(Outcome outcome, String message, Ability source, Game game);

    boolean chooseUse(Outcome outcome, String message, String secondMessage, String trueText, String falseText, Ability source, Game game);

    boolean choose(Outcome outcome, Choice choice, Game game);

    boolean choosePile(Outcome outcome, String message, List<? extends Card> pile1, List<? extends Card> pile2, Game game);

    boolean playMana(Ability ability, ManaCost unpaid, String promptText, Game game);

    /**
     * Moves the cards from cards to the bottom of the players library.
     *
     * @param cards    - list of cards that have to be moved
     * @param game     - game
     * @param anyOrder - true = if player can determine the order of the cards
     *                 else false = random order 401.4. If an effect puts two or
     *                 more cards in a specific position in a library at the
     *                 same time, the owner of those cards may arrange them in
     *                 any order. That library’s owner doesn’t reveal the order
     *                 in which the cards go into the library.
     * @param source   - source ability
     * @return
     */
    boolean putCardsOnBottomOfLibrary(Cards cards, Game game, Ability source, boolean anyOrder);

    boolean putCardsOnBottomOfLibrary(Card card, Game game, Ability source, boolean anyOrder);

    /**
     * Moves the card to the top x position of the library
     *
     * @param card
     * @param game
     * @param source
     * @param xFromTheTop
     * @param withName    - show card name in game logs for all players
     * @return
     */
    boolean putCardOnTopXOfLibrary(Card card, Game game, Ability source, int xFromTheTop, boolean withName);

    /**
     * Moves the cards from cards to the top of players library.
     *
     * @param cards    - list of cards that have to be moved
     * @param game     - game
     * @param anyOrder - true if player can determine the order of the cards
     * @param source   - source ability
     * @return
     */
    boolean putCardsOnTopOfLibrary(Cards cards, Game game, Ability source, boolean anyOrder);

    boolean putCardsOnTopOfLibrary(Card card, Game game, Ability source, boolean anyOrder);

    boolean shuffleCardsToLibrary(Cards cards, Game game, Ability source);

    boolean shuffleCardsToLibrary(Card card, Game game, Ability source);

    // set the value for X mana spells and abilities
    default int announceXMana(int min, int max, String message, Game game, Ability ability) {
        return announceXMana(min, max, 1, message, game, ability);
    }

    int announceXMana(int min, int max, int multiplier, String message, Game game, Ability ability);

    // set the value for non mana X costs
    int announceXCost(int min, int max, String message, Game game, Ability ability, VariableCost variableCost);

    // TODO: rework choose replacement effects to use array, not map (it'a random order now)
    int chooseReplacementEffect(Map<String, String> abilityMap, Game game);

    TriggeredAbility chooseTriggeredAbility(List<TriggeredAbility> abilities, Game game);

    Mode chooseMode(Modes modes, Ability source, Game game);

    void selectAttackers(Game game, UUID attackingPlayerId);

    void selectBlockers(Ability source, Game game, UUID defendingPlayerId);

    UUID chooseAttackerOrder(List<Permanent> attacker, Game game);

    /**
     * Choose the order in which blockers get damage assigned to
     *
     * @param blockers     list of blockers where to choose the next one from
     * @param combatGroup  the concerning combat group
     * @param blockerOrder the already set order of blockers
     * @param game
     * @return blocker next to add to the blocker order
     */
    UUID chooseBlockerOrder(List<Permanent> blockers, CombatGroup combatGroup, List<UUID> blockerOrder, Game game);

    void assignDamage(int damage, List<UUID> targets, String singleTargetName, UUID attackerId, Ability source, Game game);

    int getAmount(int min, int max, String message, Game game);

    /**
     * Player distributes amount among multiple options
     *
     * @param outcome  AI hint
     * @param messages List of options to distribute amount among
     * @param min      Minimum value per option
     * @param max      Total amount to be distributed
     * @param type     MultiAmountType enum to set dialog options such as title and header
     * @param game     Game
     * @return List of integers with size equal to messages.size().  The sum of the integers is equal to max.
     */
    default List<Integer> getMultiAmount(Outcome outcome, List<String> messages, int min, int max, MultiAmountType type, Game game) {
        List<MultiAmountMessage> constraints = messages.stream()
                .map(s -> new MultiAmountMessage(s, min, max))
                .collect(Collectors.toList());
        return getMultiAmountWithIndividualConstraints(outcome, constraints, min, max, type, game);
    }

    /**
     * Player distributes amount among multiple options
     *
     * @param outcome  AI hint
     * @param messages List of options to distribute amount among. Each option has a constraint on the min, max chosen for it
     * @param min      Total minimum amount to be distributed
     * @param max      Total amount to be distributed
     * @param type     MultiAmountType enum to set dialog options such as title and header
     * @param game     Game
     * @return List of integers with size equal to messages.size().  The sum of the integers is equal to max.
     */
    List<Integer> getMultiAmountWithIndividualConstraints(Outcome outcome, List<MultiAmountMessage> messages, int min, int max, MultiAmountType type, Game game);

    void sideboard(Match match, Deck deck);

    void construct(Tournament tournament, Deck deck);

    void pickCard(List<Card> cards, Deck deck, Draft draft);

    // TODO: add result, process it in AI code (if something put creature to attack then it can broke current AI logic)
    void declareAttacker(UUID attackerId, UUID defenderId, Game game, boolean allowUndo);

    void declareBlocker(UUID defenderId, UUID blockerId, UUID attackerId, Game game);

    void declareBlocker(UUID defenderId, UUID blockerId, UUID attackerId, Game game, boolean allowUndo);

    List<Permanent> getAvailableAttackers(Game game);

    List<Permanent> getAvailableAttackers(UUID defenderId, Game game);

    List<Permanent> getAvailableBlockers(Game game);

    void beginTurn(Game game);

    void endOfTurn(Game game);

    void phasing(Game game);

    void untap(Game game);

    void updateRange(Game game);

    ManaOptions getManaAvailable(Game originalGame);

    void addAvailableTriggeredMana(List<Mana> netManaAvailable);

    List<List<Mana>> getAvailableTriggeredMana();

    List<ActivatedAbility> getPlayable(Game game, boolean hidden);

    List<Ability> getPlayableOptions(Ability ability, Game game);

    PlayableObjectsList getPlayableObjects(Game game, Zone zone);

    Map<UUID, ActivatedAbility> getPlayableActivatedAbilities(MageObject object, Zone zone, Game originalGame);

    /**
     * add counter to the player (action verb is `get`, but not using that one to avoid ambiguity with getters)
     */
    boolean addCounters(Counter counter, UUID playerAddingCounters, Ability source, Game game);

    /**
     * lose {@param amount} counters of the specified kind.
     */
    void loseCounters(String counterName, int amount, Ability source, Game game);

    /**
     * lose all counters of any kind.
     *
     * @return the amount of counters removed this way.
     */
    int loseAllCounters(Ability source, Game game);

    /**
     * lose all counters of a specific kind.
     *
     * @return the amount of counters removed this way.
     */
    int loseAllCounters(String counterName, Ability source, Game game);

    /**
     * @return the amount of counters of the specified kind the player has
     */
    int getCountersCount(CounterType counterType);

    /**
     * @return the amount of counters of the specified kind the player has
     */
    int getCountersCount(String counterName);

    /**
     * @return the amount of counters in total of any kind the player has
     */
    int getCountersTotalCount();

    List<UUID> getAttachments();

    boolean addAttachment(UUID permanentId, Ability source, Game game);

    boolean removeAttachment(Permanent permanent, Ability source, Game game);

    /**
     * Signals that the player becomes active player in this turn.
     */
    void becomesActivePlayer();

    int getTurns();

    /**
     * asThough effect to reveal faceDown cards
     *
     * @param card
     * @param game
     * @param abilitiesToActivate extra info about abilities that can be
     *                            activated on NO option
     * @return player looked at the card
     */
    boolean lookAtFaceDownCard(Card card, Game game, int abilitiesToActivate);

    /**
     * Set seconds left to play the game.
     *
     * @param timeLeft
     */
    void setPriorityTimeLeft(int timeLeft);

    /**
     * Returns seconds left to play the game.
     *
     * @return
     */
    int getPriorityTimeLeft();

    /**
     * Set seconds left before priority time starts ticking down.
     *
     * @param timeLeft
     */
    void setBufferTimeLeft(int timeLeft);

    /**
     * Returns seconds left before priority time starts ticking down.
     *
     * @return
     */
    int getBufferTimeLeft();

    void setReachedNextTurnAfterLeaving(boolean reachedNextTurnAfterLeaving);

    boolean hasReachedNextTurnAfterLeaving();

    /**
     * Checks if a AI player is able to join a table i.e. Draft - bot can not
     * enter a table with constructed format
     *
     * @param table
     * @return
     */
    boolean canJoinTable(Table table);

    /**
     * Set the commanderId of the player
     *
     * @param commanderId
     */
    void addCommanderId(UUID commanderId);

    /**
     * Get the commanderIds of the player Deprecated, use
     * game.getCommandersIds(xxx) instead
     *
     * @return
     */
    @Deprecated
    Set<UUID> getCommandersIds();

    /**
     * Moves cards from one zone to another
     *
     * @param cards
     * @param toZone
     * @param source
     * @param game
     * @return
     */
    boolean moveCards(Cards cards, Zone toZone, Ability source, Game game);

    boolean moveCards(Card card, Zone toZone, Ability source, Game game);

    boolean moveCards(Card card, Zone toZone, Ability source, Game game, boolean tapped, boolean faceDown, boolean byOwner, List<UUID> appliedEffects);

    boolean moveCards(Set<? extends Card> cards, Zone toZone, Ability source, Game game);

    /**
     * Universal method to move cards from one zone to another. Do not mix
     * objects from different zones to move.
     *
     * @param cards
     * @param toZone
     * @param source
     * @param game
     * @param tapped         the cards are tapped on the battlefield
     * @param faceDown       the cards are face down in the to zone
     * @param byOwner        the card is moved (or put onto battlefield) by the
     *                       owner of the card and if target zone is battlefield
     *                       controls the permanent (instead of the controller
     *                       of the source)
     * @param appliedEffects
     * @return
     */
    boolean moveCards(Set<? extends Card> cards, Zone toZone, Ability source, Game game, boolean tapped, boolean faceDown, boolean byOwner, List<UUID> appliedEffects);

    boolean moveCardsToExile(Card card, Ability source, Game game, boolean withName, UUID exileId, String exileZoneName);

    boolean moveCardsToExile(Set<Card> cards, Ability source, Game game, boolean withName, UUID exileId, String exileZoneName);

    /**
     * Uses card.moveToZone and posts a inform message about moving the card
     * into the game log
     *
     * @param card
     * @param source
     * @param game
     * @param withName show the card name in the log
     * @return
     */
    boolean moveCardToHandWithInfo(Card card, Ability source, Game game, boolean withName);

    /**
     * Iterates through a set of cards and runs moveCardToHandWithInfo on each item
     *
     * @param cards
     * @param source
     * @param game
     * @param withName show the card names in the log
     * @return
     */
    boolean moveCardsToHandWithInfo(Cards cards, Ability source, Game game, boolean withName);

    /**
     * Uses card.moveToExile and posts a inform message about moving the card to
     * exile into the game log. Don't use this in replacement effects, because
     * list of applied effects is not saved
     *
     * @param card
     * @param exileId   exile zone id (optional)
     * @param exileName name of exile zone (optional)
     * @param source
     * @param game
     * @param fromZone
     * @param withName  for face down: used to hide card name in game logs before real face down status apply
     * @return
     */
    @Deprecated
    // if you want to use it in replaceEvent, then use ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
    boolean moveCardToExileWithInfo(Card card, UUID exileId, String exileName, Ability source, Game game, Zone fromZone, boolean withName);

    /**
     * Uses card.moveToZone and posts a inform message about moving the card to
     * graveyard into the game log
     *
     * @param card
     * @param source
     * @param game
     * @param fromZone if null, this info isn't postet
     * @return
     */
    boolean moveCardToGraveyardWithInfo(Card card, Ability source, Game game, Zone fromZone);

    /**
     * Internal used to move cards Use commonly player.moveCards()
     *
     * @param cards
     * @param source
     * @param game
     * @param fromZone if null, this info isn't postet
     * @return Set<Cards> that were successful moved to graveyard
     */
    Set<Card> moveCardsToGraveyardWithInfo(Set<? extends Card> cards, Ability source, Game game, Zone fromZone);

    /**
     * Uses card.moveToZone and posts a inform message about moving the card to
     * library into the game log
     *
     * @param card
     * @param source
     * @param game
     * @param fromZone if null, this info isn't postet
     * @param toTop    to the top of the library else to the bottom
     * @param withName show the card name in the log
     * @return
     */
    boolean moveCardToLibraryWithInfo(Card card, Ability source, Game game, Zone fromZone, boolean toTop, boolean withName);

    /**
     * Uses card.moveToZone and posts a inform message about moving the card to
     * library into the game log
     *
     * @param card
     * @param source
     * @param game
     * @param fromZone if null, this info isn't postet
     * @return
     */
    boolean moveCardToCommandWithInfo(Card card, Ability source, Game game, Zone fromZone);

    Cards millCards(int toMill, Ability source, Game game);

    /**
     * Checks if the playerToCheckId is from an opponent in range
     *
     * @param playerToCheckId
     * @param game
     * @return true if playerToCheckId belongs to an opponent
     */
    boolean hasOpponent(UUID playerToCheckId, Game game);

    /**
     * Free resources on match end
     */
    void cleanUpOnMatchEnd();

    /**
     * If the next spell cast has the set sourceId, the spell will be cast
     * without mana (null) or the mana set to manaCosts instead of its normal
     * mana costs.
     *
     * @param sourceId  the source that can be cast without mana
     * @param manaCosts alternate ManaCost, null if it can be cast without mana
     *                  cost
     * @param costs     alternate other costs you need to pay
     */
    default void setCastSourceIdWithAlternateMana(UUID sourceId, ManaCosts<ManaCost> manaCosts, Costs<Cost> costs) {
        setCastSourceIdWithAlternateMana(sourceId, manaCosts, costs, MageIdentifier.Default);
    }

    /**
     * If the next spell cast has the set sourceId, the spell will be cast
     * without mana (null) or the mana set to manaCosts instead of its normal
     * mana costs.
     *
     * @param sourceId   the source that can be cast without mana
     * @param manaCosts  alternate ManaCost, null if it can be cast without mana
     *                   cost
     * @param costs      alternate other costs you need to pay
     * @param identifier if not using the MageIdentifier.Default, only apply the alternate mana when ApprovingSource if of that kind.
     */
    void setCastSourceIdWithAlternateMana(UUID sourceId, ManaCosts<ManaCost> manaCosts, Costs<Cost> costs, MageIdentifier identifier);

    Map<UUID, Set<MageIdentifier>> getCastSourceIdWithAlternateMana();

    Map<UUID, Map<MageIdentifier, ManaCosts<ManaCost>>> getCastSourceIdManaCosts();

    Map<UUID, Map<MageIdentifier, Costs<Cost>>> getCastSourceIdCosts();

    void clearCastSourceIdManaCosts();

    // permission handling to show hand cards
    void addPermissionToShowHandCards(UUID watcherUserId);

    boolean hasUserPermissionToSeeHand(UUID userId);

    void revokePermissionToSeeHandCards();

    boolean isPlayerAllowedToRequestHand(UUID gameId, UUID requesterPlayerId);

    void addPlayerToRequestedHandList(UUID gameId, UUID requesterPlayerId);

    Set<UUID> getUsersAllowedToSeeHandCards();

    void setPayManaMode(boolean payManaMode);

    boolean isInPayManaMode();

    void setMatchPlayer(MatchPlayer matchPlayer);

    MatchPlayer getMatchPlayer();

    boolean scry(int value, Ability source, Game game);

    /**
     * result of the doSurveil action.
     * Sometimes more info is needed for the caller after the surveil is done.
     */
    class SurveilResult {
        private final boolean surveilled;
        private final int numberInGraveyard; // how many cards were put into the graveyard
        private final int numberOnTop; // how many cards were put into the graveyard

        private SurveilResult(boolean surveilled, int inGrave, int onTop) {
            this.surveilled = surveilled;
            this.numberInGraveyard = inGrave;
            this.numberOnTop = onTop;
        }

        public static SurveilResult noSurveil() {
            return new SurveilResult(false, 0, 0);
        }

        public static SurveilResult surveil(int inGrave, int onTop) {
            return new SurveilResult(true, inGrave, onTop);
        }

        public boolean hasSurveilled() {
            return this.surveilled;
        }

        public int getNumberPutInGraveyard() {
            return this.numberInGraveyard;
        }

        public int getNumberPutOnTop() {
            return this.numberOnTop;
        }
    }

    SurveilResult doSurveil(int value, Ability source, Game game);

    default boolean surveil(int value, Ability source, Game game) {
        SurveilResult result = doSurveil(value, source, game);
        return result.hasSurveilled();
    }

    /**
     * Only used for test player for pre-setting targets
     *
     * @param ability
     * @param game
     * @return
     */
    boolean addTargets(Ability ability, Game game);

    String getHistory();

    boolean hasDesignation(DesignationType designationName);

    void addDesignation(Designation designation);

    List<Designation> getDesignations();

    /**
     * Set the mana colors the user can pay with 2 life instead
     *
     * @param colors
     */
    void addPhyrexianToColors(FilterMana colors);

    /**
     * Mana colors the player can pay instead with 2 life
     *
     * @return
     */
    FilterMana getPhyrexianColors();

    Permanent getRingBearer(Game game);

    void chooseRingBearer(Game game);

    /**
     * Function to query if the player has strictChooseMode enabled. Only the test player can have it.
     * Function is added here so that the test suite project does not have to be imported into the client/server project.
     *
     * @return whether the player has strictChooseMode enabled
     */
    public default boolean getStrictChooseMode() {
        return false;
    }

    public UserData getControllingPlayersUserData(Game game);
}
