package mage.players;

import mage.MageItem;
import mage.MageObject;
import mage.MageObjectReference;
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
import mage.counters.Counters;
import mage.designations.Designation;
import mage.designations.DesignationType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.Graveyard;
import mage.game.Table;
import mage.game.combat.CombatGroup;
import mage.game.draft.Draft;
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

import java.io.Serializable;
import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public interface Player extends MageItem, Copyable<Player> {

    boolean isHuman();

    String getName();

    String getLogName();

    RangeOfInfluence getRange();

    Library getLibrary();

    Cards getSideboard();

    Graveyard getGraveyard();

    Abilities<Ability> getAbilities();

    void addAbility(Ability ability);

    Counters getCounters();

    int getLife();

    void initLife(int life);

    void setLife(int life, Game game, Ability source);

    void setLife(int life, Game game, UUID sourceId);

    /**
     * @param amount   amount of life loss
     * @param game
     * @param atCombat was the source combat damage
     * @return
     */
    int loseLife(int amount, Game game, boolean atCombat);

    int gainLife(int amount, Game game, Ability source);

    int gainLife(int amount, Game game, UUID sourceId);

    int damage(int damage, UUID sourceId, Game game);

    int damage(int damage, UUID sourceId, Game game, boolean combatDamage, boolean preventable);

    int damage(int damage, UUID sourceId, Game game, boolean combatDamage, boolean preventable, List<UUID> appliedEffects);

    // to handle rule changing effects (613.10)
    boolean isCanLoseLife();

    void setCanLoseLife(boolean canLoseLife);

    void setCanGainLife(boolean canGainLife);

    boolean isCanGainLife();

    void setCanPayLifeCost(boolean canPayLifeCost);

    boolean canPayLifeCost();

    void setCanPaySacrificeCostFilter(FilterPermanent filter);

    FilterPermanent getSacrificeCostFilter();

    boolean canPaySacrificeCost(Permanent permanent, UUID sourceId, UUID controllerId, Game game);

    void setLifeTotalCanChange(boolean lifeTotalCanChange);

    boolean isLifeTotalCanChange();

    void setLoseByZeroOrLessLife(boolean loseByZeroOrLessLife);

    boolean canLoseByZeroOrLessLife();

    void setPlayCardsFromGraveyard(boolean playCardsFromGraveyard);

    boolean canPlayCardsFromGraveyard();

    /**
     * Returns alternative casting costs a player can cast spells for
     *
     * @return
     */
    List<AlternativeSourceCosts> getAlternativeSourceCosts();

    Cards getHand();

    int getLandsPlayed();

    int getLandsPerTurn();

    void setLandsPerTurn(int landsPerTurn);

    int getLoyaltyUsePerTurn();

    void setLoyaltyUsePerTurn(int loyaltyUsePerTurn);

    int getMaxHandSize();

    void setMaxHandSize(int maxHandSize);

    int getMaxAttackedBy();

    void setMaxAttackedBy(int maxAttackedBy);

    boolean isPassed();

    boolean isEmptyDraw();

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

    /**
     * Called if other player left the game
     *
     * @param game
     */
    void otherPlayerLeftGame(Game game);

    ManaPool getManaPool();

    Set<UUID> getInRange();

    boolean isTopCardRevealed();

    void setTopCardRevealed(boolean topCardRevealed);

    /**
     * Get data from the client Preferences (e.g. avatarId or
     * showAbilityPickerForce)
     *
     * @return
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
     * @param playerId
     */
    void controlPlayersTurn(Game game, UUID playerId);

    /**
     * Sets player {@link UUID} who controls this player's turn.
     *
     * @param playerId
     */
    void setTurnControlledBy(UUID playerId);

    List<UUID> getTurnControllers();

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

    void setGameUnderYourControl(boolean value, boolean fullRestore);

    boolean isTestMode();

    void setTestMode(boolean value);

    void addAction(String action);

    int getActionCount();

    void setAllowBadMoves(boolean allowBadMoves);

    void init(Game game);

    void init(Game game, boolean testMode);

    void useDeck(Deck deck, Game game);

    /**
     * Called before each applyEffects, to rest all what can be applyed by
     * continuous effects
     */
    void reset();

    void shuffleLibrary(Ability source, Game game);

    int drawCards(int num, Game game);

    int drawCards(int num, Game game, List<UUID> appliedEffects);

    boolean cast(SpellAbility ability, Game game, boolean noMana, MageObjectReference reference);

    SpellAbility chooseSpellAbilityForCast(SpellAbility ability, Game game, boolean noMana);

    boolean putInHand(Card card, Game game);

    boolean removeFromHand(Card card, Game game);

    boolean removeFromBattlefield(Permanent permanent, Game game);

    boolean putInGraveyard(Card card, Game game);

    boolean removeFromGraveyard(Card card, Game game);

    boolean removeFromLibrary(Card card, Game game);

    boolean searchLibrary(TargetCardInLibrary target, Ability source, Game game);

    boolean searchLibrary(TargetCardInLibrary target, Ability source, Game game, boolean triggerEvents);

    boolean searchLibrary(TargetCardInLibrary target, Ability source, Game game, UUID targetPlayerId);

    /**
     * @param target
     * @param source
     * @param game
     * @param targetPlayerId player whose library will be searched
     * @param triggerEvents  whether searching will trigger any game events
     * @return true if search was successful
     */
    boolean searchLibrary(TargetCardInLibrary target, Ability source, Game game, UUID targetPlayerId, boolean triggerEvents);

    /**
     * Reveals all players' libraries. Useful for abilities like Jace, Architect of Thought's -8
     * that have effects that require information from all libraries.
     *
     * @param source
     * @param game
     * @return
     */
    void lookAtAllLibraries(Ability source, Game game);

    boolean canPlayLand();

    /**
     * Plays a card if possible
     *
     * @param card         the card that can be cast
     * @param game
     * @param noMana       if it's a spell i can be cast without paying mana
     * @param ignoreTiming if it's cast during the resolution of another spell
     *                     no sorcery or play land timing restriction are checked. For a land it has
     *                     to be the turn of the player playing that card.
     * @param reference    mage object that allows to play the card
     * @return
     */
    boolean playCard(Card card, Game game, boolean noMana, boolean ignoreTiming, MageObjectReference reference);

    /**
     * @param card         the land card to play
     * @param game
     * @param ignoreTiming false - it won't be checked if the stack is empty and
     *                     you are able to play a Sorcery. It's still checked, if you are able to
     *                     play a land concerning the number of lands you already played.
     * @return
     */
    boolean playLand(Card card, Game game, boolean ignoreTiming);

    boolean activateAbility(ActivatedAbility ability, Game game);

    boolean triggerAbility(TriggeredAbility ability, Game game);

    boolean canBeTargetedBy(MageObject source, UUID sourceControllerId, Game game);

    boolean hasProtectionFrom(MageObject source, Game game);

    boolean flipCoin(Ability source, Game game, boolean winnable);

    boolean flipCoin(Ability source, Game game, boolean winnable, ArrayList<UUID> appliedEffects);

    int rollDice(Game game, int numSides);

    int rollDice(Game game, ArrayList<UUID> appliedEffects, int numSides);

    PlanarDieRoll rollPlanarDie(Game game);

    PlanarDieRoll rollPlanarDie(Game game, ArrayList<UUID> appliedEffects);

    PlanarDieRoll rollPlanarDie(Game game, ArrayList<UUID> appliedEffects, int numberChaosSides, int numberPlanarSides);

    @Deprecated
    void discard(int amount, Ability source, Game game);

    Card discardOne(boolean random, Ability source, Game game);

    Cards discard(int amount, boolean random, Ability source, Game game);

    void discardToMax(Game game);

    boolean discard(Card card, Ability source, Game game);

    void lost(Game game);

    void lostForced(Game game);

    void drew(Game game);

    void won(Game game);

    void leave();

    void concede(Game game);

    void abort();

    void abortReset();

    void signalPlayerConcede();

    void skip();

    // priority, undo, ...
    void sendPlayerAction(PlayerAction passPriorityAction, Game game, Object data);

    int getStoredBookmark();

    void setStoredBookmark(int bookmark);

    void resetStoredBookmark(Game game);

    void revealCards(Ability source, Cards cards, Game game);

    void revealCards(String name, Cards cards, Game game);

    void revealCards(Ability source, String name, Cards cards, Game game);

    void revealCards(String name, Cards cards, Game game, boolean postToLog);

    /**
     * Adds the cards to the reveal window and adds the source object's id name
     * to the title bar of the revealed cards window
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

    boolean choose(Outcome outcome, Target target, UUID sourceId, Game game);

    boolean choose(Outcome outcome, Target target, UUID sourceId, Game game, Map<String, Serializable> options);

    boolean choose(Outcome outcome, Cards cards, TargetCard target, Game game);

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
     * @param anyOrder - true if player can determine the order of the cards
     *                 else random order
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
     * @return
     */
    boolean putCardOnTopXOfLibrary(Card card, Game game, Ability source, int xFromTheTop);

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

    void selectBlockers(Game game, UUID defendingPlayerId);

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

    void assignDamage(int damage, List<UUID> targets, String singleTargetName, UUID sourceId, Game game);

    int getAmount(int min, int max, String message, Game game);

    void sideboard(Match match, Deck deck);

    void construct(Tournament tournament, Deck deck);

    void pickCard(List<Card> cards, Deck deck, Draft draft);

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

    ManaOptions getManaAvailable(Game game);

    List<Ability> getPlayable(Game game, boolean hidden);

    List<Ability> getPlayableOptions(Ability ability, Game game);

    Set<UUID> getPlayableObjects(Game game, Zone zone);

    LinkedHashMap<UUID, ActivatedAbility> getUseableActivatedAbilities(MageObject object, Zone zone, Game game);

    boolean addCounters(Counter counter, Game game);

    void removeCounters(String name, int amount, Ability source, Game game);

    List<UUID> getAttachments();

    boolean addAttachment(UUID permanentId, Game game);

    boolean removeAttachment(Permanent permanent, Game game);

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
     * @param abilitiesToActivate extra info about abilities that can be activated on NO option
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
     * Get the commanderIds of the player
     * Deprecated, use game.getCommandersIds(xxx) instead
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

    boolean moveCards(Set<Card> cards, Zone toZone, Ability source, Game game);

    /**
     * Universal method to move cards from one zone to another. Do not mix
     * objects from different from zones to move.
     *
     * @param cards
     * @param toZone
     * @param source
     * @param game
     * @param tapped         the cards are tapped on the battlefield
     * @param faceDown       the cards are face down in the to zone
     * @param byOwner        the card is moved (or put onto battlefield) by the owner
     *                       of the card and if target zone is battlefield controls the permanent
     *                       (instead of the controller of the source)
     * @param appliedEffects
     * @return
     */
    boolean moveCards(Set<Card> cards, Zone toZone, Ability source, Game game, boolean tapped, boolean faceDown, boolean byOwner, List<UUID> appliedEffects);

    boolean moveCardsToExile(Card card, Ability source, Game game, boolean withName, UUID exileId, String exileZoneName);

    boolean moveCardsToExile(Set<Card> cards, Ability source, Game game, boolean withName, UUID exileId, String exileZoneName);

    /**
     * Uses card.moveToZone and posts a inform message about moving the card
     * into the game log
     *
     * @param card
     * @param sourceId
     * @param game
     * @return
     */
    boolean moveCardToHandWithInfo(Card card, UUID sourceId, Game game);

    /**
     * @param card
     * @param sourceId
     * @param game
     * @param withName show the card name in the log
     * @return
     */
    boolean moveCardToHandWithInfo(Card card, UUID sourceId, Game game, boolean withName);

    /**
     * Uses card.moveToExile and posts a inform message about moving the card to
     * exile into the game log. Don't use this in replacement effects, because
     * list of applied effects is not saved
     *
     * @param card
     * @param exileId   exile zone id (optional)
     * @param exileName name of exile zone (optional)
     * @param sourceId
     * @param game
     * @param fromZone
     * @param withName
     * @return
     */
    @Deprecated
    boolean moveCardToExileWithInfo(Card card, UUID exileId, String exileName, UUID sourceId, Game game, Zone fromZone, boolean withName);

    /**
     * Uses card.moveToZone and posts a inform message about moving the card to
     * graveyard into the game log
     *
     * @param card
     * @param sourceId
     * @param game
     * @param fromZone if null, this info isn't postet
     * @return
     */
    boolean moveCardToGraveyardWithInfo(Card card, UUID sourceId, Game game, Zone fromZone);

    /**
     * Internal used to move cards Use commonly player.moveCards()
     *
     * @param cards
     * @param source
     * @param game
     * @param fromZone if null, this info isn't postet
     * @return Set<Cards> that were successful moved to graveyard
     */
    Set<Card> moveCardsToGraveyardWithInfo(Set<Card> cards, Ability source, Game game, Zone fromZone);

    /**
     * Uses card.moveToZone and posts a inform message about moving the card to
     * library into the game log
     *
     * @param card
     * @param sourceId
     * @param game
     * @param fromZone if null, this info isn't postet
     * @param toTop    to the top of the library else to the bottom
     * @param withName show the card name in the log
     * @return
     */
    boolean moveCardToLibraryWithInfo(Card card, UUID sourceId, Game game, Zone fromZone, boolean toTop, boolean withName);

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
    void setCastSourceIdWithAlternateMana(UUID sourceId, ManaCosts<ManaCost> manaCosts, Costs<Cost> costs);

    UUID getCastSourceIdWithAlternateMana();

    ManaCosts<ManaCost> getCastSourceIdManaCosts();

    Costs<Cost> getCastSourceIdCosts();

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

    boolean surveil(int value, Ability source, Game game);

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

}
