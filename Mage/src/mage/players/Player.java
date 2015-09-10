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
package mage.players;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageItem;
import mage.MageObject;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.Mode;
import mage.abilities.Modes;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbility;
import mage.abilities.costs.AlternativeSourceCosts;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.mana.ManaOptions;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.decks.Deck;
import mage.choices.Choice;
import mage.constants.AbilityType;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.PlayerAction;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.Counters;
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
import mage.util.MessageToClient;

/**
 *
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

    void setLife(int life, Game game);

    int loseLife(int amount, Game game);

    int gainLife(int amount, Game game);

    int damage(int damage, UUID sourceId, Game game, boolean combatDamage, boolean preventable);

    int damage(int damage, UUID sourceId, Game game, boolean combatDamage, boolean preventable, ArrayList<UUID> appliedEffects);

    // to handle rule changing effects (613.10)
    boolean isCanLoseLife();

    void setCanLoseLife(boolean canLoseLife);

    void setCanGainLife(boolean canGainLife);

    boolean isCanGainLife();

    void setCanPayLifeCost(boolean canPayLifeCost);

    boolean canPayLifeCost();

    void setCanPaySacrificeCost(boolean canPaySacrificeCost);

    boolean canPaySacrificeCost();

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

    boolean getPassedAllTurns();

    AbilityType getJustActivatedType();

    void setJustActivatedType(AbilityType abilityType);

    boolean hasLost();

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

    UUID getTurnControlledBy();

    /**
     * Resets players whose turns you control at the moment.
     */
    void resetOtherTurnsControlled();

    /**
     * Returns false in case player don't control the game.
     *
     * Note: For effects like "You control target player during that player's
     * next turn".
     *
     * @return
     */
    boolean isGameUnderControl();

    /**
     * Returns false in case you don't control the game.
     *
     * Note: For effects like "You control target player during that player's
     * next turn".
     *
     * @param value
     */
    void setGameUnderYourControl(boolean value);

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

    void shuffleLibrary(Game game);

    int drawCards(int num, Game game);

    int drawCards(int num, Game game, ArrayList<UUID> appliedEffects);

    boolean cast(SpellAbility ability, Game game, boolean noMana);

    SpellAbility chooseSpellAbilityForCast(SpellAbility ability, Game game, boolean noMana);

    boolean putInHand(Card card, Game game);

    boolean removeFromHand(Card card, Game game);

    boolean removeFromBattlefield(Permanent permanent, Game game);

    boolean putInGraveyard(Card card, Game game, boolean fromBattlefield);

    boolean removeFromGraveyard(Card card, Game game);

    boolean removeFromLibrary(Card card, Game game);

    boolean searchLibrary(TargetCardInLibrary target, Game game);

    /**
     *
     * @param target
     * @param game
     * @param targetPlayerId player whose library will be searched
     * @return true if search was successful
     */
    boolean searchLibrary(TargetCardInLibrary target, Game game, UUID targetPlayerId);

    boolean canPlayLand();

    boolean playLand(Card card, Game game);

    boolean activateAbility(ActivatedAbility ability, Game game);

    boolean triggerAbility(TriggeredAbility ability, Game game);

    boolean canBeTargetedBy(MageObject source, UUID sourceControllerId, Game game);

    boolean hasProtectionFrom(MageObject source, Game game);

    boolean flipCoin(Game game);

    boolean flipCoin(Game game, ArrayList<UUID> appliedEffects);

    @Deprecated
    void discard(int amount, Ability source, Game game);

    Card discardOne(boolean random, Ability source, Game game);

    Cards discard(int amount, boolean random, Ability source, Game game);

    void discardToMax(Game game);

    boolean discard(Card card, Ability source, Game game);

    void lost(Game game);

    void lostForced(Game game);

    void won(Game game);

    void leave();

    void concede(Game game);

    void abort();

    void abortReset();

    void skip();

    // priority, undo, ...
    void sendPlayerAction(PlayerAction passPriorityAction, Game game, Object data);

    int getStoredBookmark();

    void setStoredBookmark(int bookmark);

    void resetStoredBookmark(Game game);

    void revealCards(String name, Cards cards, Game game);

    void revealCards(String name, Cards cards, Game game, boolean postToLog);

    void lookAtCards(String name, Card card, Game game);

    void lookAtCards(String name, Cards cards, Game game);

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

    boolean chooseUse(Outcome outcome, MessageToClient message, Ability source, Game game);

    boolean choose(Outcome outcome, Choice choice, Game game);

    boolean choosePile(Outcome outcome, String message, List<? extends Card> pile1, List<? extends Card> pile2, Game game);

    boolean playMana(Ability ability, ManaCost unpaid, String promptText, Game game);

    /**
     * Moves the cards from cards to the bottom of the players library.
     *
     * @param cards - list of cards that have to be moved
     * @param game - game
     * @param anyOrder - true if player can determine the order of the cards
     * @param source - source ability
     * @return
     */
    boolean putCardsOnBottomOfLibrary(Cards cards, Game game, Ability source, boolean anyOrder);

    /**
     * Moves the cards from cards to the top of players library.
     *
     * @param cards - list of cards that have to be moved
     * @param game - game
     * @param anyOrder - true if player can determine the order of the cards
     * @param source - source ability
     * @return
     */
    boolean putCardsOnTopOfLibrary(Cards cards, Game game, Ability source, boolean anyOrder);

    // set the value for X mana spells and abilities
    int announceXMana(int min, int max, String message, Game game, Ability ability);

    // set the value for non mana X costs
    int announceXCost(int min, int max, String message, Game game, Ability ability, VariableCost variableCost);

    int chooseReplacementEffect(Map<String, String> abilityMap, Game game);

    TriggeredAbility chooseTriggeredAbility(List<TriggeredAbility> abilities, Game game);

    Mode chooseMode(Modes modes, Ability source, Game game);

    void selectAttackers(Game game, UUID attackingPlayerId);

    void selectBlockers(Game game, UUID defendingPlayerId);

    UUID chooseAttackerOrder(List<Permanent> attacker, Game game);

    /**
     * Choose the order in which blockers get damage assigned to
     *
     * @param blockers list of blockers where to choose the next one from
     * @param combatGroup the concerning combat group
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

    Set<UUID> getPlayableInHand(Game game);

    LinkedHashMap<UUID, ActivatedAbility> getUseableActivatedAbilities(MageObject object, Zone zone, Game game);

    void addCounters(Counter counter, Game game);

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
     * @return player looked at the card
     */
    boolean lookAtFaceDownCard(Card card, Game game);

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
    void setCommanderId(UUID commanderId);

    /**
     * Get the commanderId of the player
     *
     * @return
     */
    UUID getCommanderId();

    /**
     * Moves cards from one zone to another
     *
     * @param cards
     * @param fromZone
     * @param toZone
     * @param source
     * @param game
     * @return
     */
    boolean moveCards(Cards cards, Zone fromZone, Zone toZone, Ability source, Game game);

    boolean moveCards(Card card, Zone fromZone, Zone toZone, Ability source, Game game);

    boolean moveCards(Set<Card> cards, Zone fromZone, Zone toZone, Ability source, Game game);

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
     *
     */
    boolean moveCardToHandWithInfo(Card card, UUID sourceId, Game game, boolean withName);

    /**
     * Uses card.moveToExile and posts a inform message about moving the card to
     * exile into the game log
     *
     * @param card
     * @param exileId exile zone id (optional)
     * @param exileName name of exile zone (optional)
     * @param sourceId
     * @param game
     * @param fromZone
     * @param withName
     * @return
     */
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
     * graveyard into the game log
     *
     * @param card
     * @param sourceId
     * @param game
     * @param fromZone if null, this info isn't postet
     * @param toTop to the top of the library else to the bottom
     * @param withName show the card name in the log
     * @return
     */
    boolean moveCardToLibraryWithInfo(Card card, UUID sourceId, Game game, Zone fromZone, boolean toTop, boolean withName);

    /**
     * Uses putOntoBattlefield and posts also a info message about in the game
     * log
     *
     * @param card
     * @param game
     * @param fromZone
     * @param sourceId
     * @return
     */
    boolean putOntoBattlefieldWithInfo(Card card, Game game, Zone fromZone, UUID sourceId);

    /**
     * Uses putOntoBattlefield and posts also a info message about in the game
     * log
     *
     * @param card
     * @param game
     * @param fromZone
     * @param sourceId
     * @param tapped the card enters the battlefield tapped
     * @return
     */
    boolean putOntoBattlefieldWithInfo(Card card, Game game, Zone fromZone, UUID sourceId, boolean tapped);

    /**
     * Uses putOntoBattlefield and posts also a info message about in the game
     * log
     *
     * @param card
     * @param game
     * @param fromZone
     * @param sourceId
     * @param tapped the card enters the battlefield tapped
     * @param facedown the card enters the battlefield facedown
     * @return
     */
    boolean putOntoBattlefieldWithInfo(Card card, Game game, Zone fromZone, UUID sourceId, boolean tapped, boolean facedown);

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
     * If the next cast spell has the set sourceId, the spell will be cast
     * without mana.
     *
     * @param sourceId the source that can be cast without mana
     * @param manaCosts alternate ManaCost, null if it can be cast without mana
     * cost
     */
    void setCastSourceIdWithAlternateMana(UUID sourceId, ManaCosts manaCosts);

    UUID getCastSourceIdWithAlternateMana();

    ManaCosts getCastSourceIdManaCosts();

    // permission handling to show hand cards
    void addPermissionToShowHandCards(UUID watcherUserId);

    boolean hasUserPermissionToSeeHand(UUID userId);

    void revokePermissionToSeeHandCards();

    boolean isRequestToShowHandCardsAllowed();

    Set<UUID> getUsersAllowedToSeeHandCards();

    boolean isInPayManaMode();

    void setMatchPlayer(MatchPlayer matchPlayer);

    MatchPlayer getMatchPlayer();

    boolean scry(int value, Ability source, Game game);
}
