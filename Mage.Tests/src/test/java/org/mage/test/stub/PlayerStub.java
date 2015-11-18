/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package org.mage.test.stub;

import mage.MageObject;
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
import mage.constants.PlayerAction;
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
import mage.players.Library;
import mage.players.ManaPool;
import mage.players.Player;
import mage.players.net.UserData;
import mage.target.Target;
import mage.target.TargetAmount;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.util.MessageToClient;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author Quercitron
 */
public class PlayerStub implements Player {

    private final UUID id = UUID.randomUUID();

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean isHuman() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getLogName() {
        return null;
    }

    @Override
    public RangeOfInfluence getRange() {
        return null;
    }

    @Override
    public Library getLibrary() {
        return null;
    }

    @Override
    public Cards getSideboard() {
        return null;
    }

    @Override
    public Graveyard getGraveyard() {
        return null;
    }

    @Override
    public Abilities<Ability> getAbilities() {
        return null;
    }

    @Override
    public void addAbility(Ability ability) {

    }

    @Override
    public Counters getCounters() {
        return null;
    }

    @Override
    public int getLife() {
        return 0;
    }

    @Override
    public void initLife(int life) {

    }

    @Override
    public void setLife(int life, Game game) {

    }

    @Override
    public int loseLife(int amount, Game game) {
        return 0;
    }

    @Override
    public int gainLife(int amount, Game game) {
        return 0;
    }

    @Override
    public int damage(int damage, UUID sourceId, Game game, boolean combatDamage, boolean preventable) {
        return 0;
    }

    @Override
    public int damage(int damage, UUID sourceId, Game game, boolean combatDamage, boolean preventable, ArrayList<UUID> appliedEffects) {
        return 0;
    }

    @Override
    public boolean isCanLoseLife() {
        return false;
    }

    @Override
    public void setCanLoseLife(boolean canLoseLife) {

    }

    @Override
    public void setCanGainLife(boolean canGainLife) {

    }

    @Override
    public boolean isCanGainLife() {
        return false;
    }

    @Override
    public void setCanPayLifeCost(boolean canPayLifeCost) {

    }

    @Override
    public boolean canPayLifeCost() {
        return false;
    }

    @Override
    public void setCanPaySacrificeCost(boolean canPaySacrificeCost) {

    }

    @Override
    public boolean canPaySacrificeCost() {
        return false;
    }

    @Override
    public void setLifeTotalCanChange(boolean lifeTotalCanChange) {

    }

    @Override
    public boolean isLifeTotalCanChange() {
        return false;
    }

    @Override
    public void setLoseByZeroOrLessLife(boolean loseByZeroOrLessLife) {

    }

    @Override
    public boolean canLoseByZeroOrLessLife() {
        return false;
    }

    @Override
    public void setPlayCardsFromGraveyard(boolean playCardsFromGraveyard) {

    }

    @Override
    public boolean canPlayCardsFromGraveyard() {
        return false;
    }

    @Override
    public List<AlternativeSourceCosts> getAlternativeSourceCosts() {
        return null;
    }

    @Override
    public Cards getHand() {
        return null;
    }

    @Override
    public int getLandsPlayed() {
        return 0;
    }

    @Override
    public int getLandsPerTurn() {
        return 0;
    }

    @Override
    public void setLandsPerTurn(int landsPerTurn) {

    }

    @Override
    public int getLoyaltyUsePerTurn() {
        return 0;
    }

    @Override
    public void setLoyaltyUsePerTurn(int loyaltyUsePerTurn) {

    }

    @Override
    public int getMaxHandSize() {
        return 0;
    }

    @Override
    public void setMaxHandSize(int maxHandSize) {

    }

    @Override
    public int getMaxAttackedBy() {
        return 0;
    }

    @Override
    public void setMaxAttackedBy(int maxAttackedBy) {

    }

    @Override
    public boolean isPassed() {
        return false;
    }

    @Override
    public boolean isEmptyDraw() {
        return false;
    }

    @Override
    public void pass(Game game) {

    }

    @Override
    public void resetPassed() {

    }

    @Override
    public void resetPlayerPassedActions() {

    }

    @Override
    public boolean getPassedTurn() {
        return false;
    }

    @Override
    public boolean getPassedUntilEndOfTurn() {
        return false;
    }

    @Override
    public boolean getPassedUntilNextMain() {
        return false;
    }

    @Override
    public boolean getPassedUntilStackResolved() {
        return false;
    }

    @Override
    public boolean getPassedAllTurns() {
        return false;
    }

    @Override
    public AbilityType getJustActivatedType() {
        return null;
    }

    @Override
    public void setJustActivatedType(AbilityType abilityType) {

    }

    @Override
    public boolean hasLost() {
        return false;
    }

    @Override
    public boolean hasWon() {
        return false;
    }

    @Override
    public boolean hasQuit() {
        return false;
    }

    @Override
    public void quit(Game game) {

    }

    @Override
    public boolean hasTimerTimeout() {
        return false;
    }

    @Override
    public void timerTimeout(Game game) {

    }

    @Override
    public boolean hasIdleTimeout() {
        return false;
    }

    @Override
    public void idleTimeout(Game game) {

    }

    @Override
    public boolean hasLeft() {
        return false;
    }

    @Override
    public boolean isInGame() {
        return false;
    }

    @Override
    public boolean canRespond() {
        return false;
    }

    @Override
    public void otherPlayerLeftGame(Game game) {

    }

    @Override
    public ManaPool getManaPool() {
        return null;
    }

    @Override
    public Set<UUID> getInRange() {
        return null;
    }

    @Override
    public boolean isTopCardRevealed() {
        return false;
    }

    @Override
    public void setTopCardRevealed(boolean topCardRevealed) {

    }

    @Override
    public UserData getUserData() {
        return null;
    }

    @Override
    public void setUserData(UserData userData) {

    }

    @Override
    public boolean canLose(Game game) {
        return false;
    }

    @Override
    public boolean autoLoseGame() {
        return false;
    }

    @Override
    public Set<UUID> getPlayersUnderYourControl() {
        return null;
    }

    @Override
    public void controlPlayersTurn(Game game, UUID playerId) {

    }

    @Override
    public void setTurnControlledBy(UUID playerId) {

    }

    @Override
    public UUID getTurnControlledBy() {
        return null;
    }

    @Override
    public void resetOtherTurnsControlled() {

    }

    @Override
    public boolean isGameUnderControl() {
        return false;
    }

    @Override
    public void setGameUnderYourControl(boolean value) {

    }

    @Override
    public boolean isTestMode() {
        return false;
    }

    @Override
    public void setTestMode(boolean value) {

    }

    @Override
    public void addAction(String action) {

    }

    @Override
    public int getActionCount() {
        return 0;
    }

    @Override
    public void setAllowBadMoves(boolean allowBadMoves) {

    }

    @Override
    public void init(Game game) {

    }

    @Override
    public void init(Game game, boolean testMode) {

    }

    @Override
    public void useDeck(Deck deck, Game game) {

    }

    @Override
    public void reset() {

    }

    @Override
    public void shuffleLibrary(Game game) {

    }

    @Override
    public int drawCards(int num, Game game) {
        return 0;
    }

    @Override
    public int drawCards(int num, Game game, ArrayList<UUID> appliedEffects) {
        return 0;
    }

    @Override
    public boolean cast(SpellAbility ability, Game game, boolean noMana) {
        return false;
    }

    @Override
    public SpellAbility chooseSpellAbilityForCast(SpellAbility ability, Game game, boolean noMana) {
        return null;
    }

    @Override
    public boolean putInHand(Card card, Game game) {
        return false;
    }

    @Override
    public boolean removeFromHand(Card card, Game game) {
        return false;
    }

    @Override
    public boolean removeFromBattlefield(Permanent permanent, Game game) {
        return false;
    }

    @Override
    public boolean putInGraveyard(Card card, Game game, boolean fromBattlefield) {
        return false;
    }

    @Override
    public boolean removeFromGraveyard(Card card, Game game) {
        return false;
    }

    @Override
    public boolean removeFromLibrary(Card card, Game game) {
        return false;
    }

    @Override
    public boolean searchLibrary(TargetCardInLibrary target, Game game) {
        return false;
    }

    @Override
    public boolean searchLibrary(TargetCardInLibrary target, Game game, UUID targetPlayerId) {
        return false;
    }

    @Override
    public boolean canPlayLand() {
        return false;
    }

    @Override
    public boolean playLand(Card card, Game game) {
        return false;
    }

    @Override
    public boolean activateAbility(ActivatedAbility ability, Game game) {
        return false;
    }

    @Override
    public boolean triggerAbility(TriggeredAbility ability, Game game) {
        return false;
    }

    @Override
    public boolean canBeTargetedBy(MageObject source, UUID sourceControllerId, Game game) {
        return false;
    }

    @Override
    public boolean hasProtectionFrom(MageObject source, Game game) {
        return false;
    }

    @Override
    public boolean flipCoin(Game game) {
        return false;
    }

    @Override
    public boolean flipCoin(Game game, ArrayList<UUID> appliedEffects) {
        return false;
    }

    @Override
    public void discard(int amount, Ability source, Game game) {

    }

    @Override
    public Card discardOne(boolean random, Ability source, Game game) {
        return null;
    }

    @Override
    public Cards discard(int amount, boolean random, Ability source, Game game) {
        return null;
    }

    @Override
    public void discardToMax(Game game) {

    }

    @Override
    public boolean discard(Card card, Ability source, Game game) {
        return false;
    }

    @Override
    public void lost(Game game) {

    }

    @Override
    public void lostForced(Game game) {

    }

    @Override
    public void won(Game game) {

    }

    @Override
    public void leave() {

    }

    @Override
    public void concede(Game game) {

    }

    @Override
    public void abort() {

    }

    @Override
    public void abortReset() {

    }

    @Override
    public void skip() {

    }

    @Override
    public void sendPlayerAction(PlayerAction passPriorityAction, Game game, Object data) {

    }

    @Override
    public int getStoredBookmark() {
        return 0;
    }

    @Override
    public void setStoredBookmark(int bookmark) {

    }

    @Override
    public void resetStoredBookmark(Game game) {

    }

    @Override
    public void revealCards(String name, Cards cards, Game game) {

    }

    @Override
    public void revealCards(String name, Cards cards, Game game, boolean postToLog) {

    }

    @Override
    public void lookAtCards(String name, Card card, Game game) {

    }

    @Override
    public void lookAtCards(String name, Cards cards, Game game) {

    }

    @Override
    public Player copy() {
        return null;
    }

    @Override
    public void restore(Player player) {

    }

    @Override
    public void setResponseString(String responseString) {

    }

    @Override
    public void setResponseUUID(UUID responseUUID) {

    }

    @Override
    public void setResponseBoolean(Boolean responseBoolean) {

    }

    @Override
    public void setResponseInteger(Integer data) {

    }

    @Override
    public void setResponseManaType(UUID manaTypePlayerId, ManaType responseManaType) {

    }

    @Override
    public boolean priority(Game game) {
        return false;
    }

    @Override
    public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game) {
        return false;
    }

    @Override
    public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game, Map<String, Serializable> options) {
        return false;
    }

    @Override
    public boolean choose(Outcome outcome, Cards cards, TargetCard target, Game game) {
        return false;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean chooseTargetAmount(Outcome outcome, TargetAmount target, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean chooseMulligan(Game game) {
        return false;
    }

    @Override
    public boolean chooseUse(Outcome outcome, String message, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean chooseUse(Outcome outcome, MessageToClient message, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean choose(Outcome outcome, Choice choice, Game game) {
        return false;
    }

    @Override
    public boolean choosePile(Outcome outcome, String message, List<? extends Card> pile1, List<? extends Card> pile2, Game game) {
        return false;
    }

    @Override
    public boolean playMana(Ability ability, ManaCost unpaid, String promptText, Game game) {
        return false;
    }

    @Override
    public boolean putCardsOnBottomOfLibrary(Cards cards, Game game, Ability source, boolean anyOrder) {
        return false;
    }

    @Override
    public boolean putCardsOnTopOfLibrary(Cards cards, Game game, Ability source, boolean anyOrder) {
        return false;
    }

    @Override
    public int announceXMana(int min, int max, String message, Game game, Ability ability) {
        return 0;
    }

    @Override
    public int announceXCost(int min, int max, String message, Game game, Ability ability, VariableCost variableCost) {
        return 0;
    }

    @Override
    public int chooseReplacementEffect(Map<String, String> abilityMap, Game game) {
        return 0;
    }

    @Override
    public TriggeredAbility chooseTriggeredAbility(List<TriggeredAbility> abilities, Game game) {
        return null;
    }

    @Override
    public Mode chooseMode(Modes modes, Ability source, Game game) {
        return null;
    }

    @Override
    public void selectAttackers(Game game, UUID attackingPlayerId) {

    }

    @Override
    public void selectBlockers(Game game, UUID defendingPlayerId) {

    }

    @Override
    public UUID chooseAttackerOrder(List<Permanent> attacker, Game game) {
        return null;
    }

    @Override
    public UUID chooseBlockerOrder(List<Permanent> blockers, CombatGroup combatGroup, List<UUID> blockerOrder, Game game) {
        return null;
    }

    @Override
    public void assignDamage(int damage, List<UUID> targets, String singleTargetName, UUID sourceId, Game game) {

    }

    @Override
    public int getAmount(int min, int max, String message, Game game) {
        return 0;
    }

    @Override
    public void sideboard(Match match, Deck deck) {

    }

    @Override
    public void construct(Tournament tournament, Deck deck) {

    }

    @Override
    public void pickCard(List<Card> cards, Deck deck, Draft draft) {

    }

    @Override
    public void declareAttacker(UUID attackerId, UUID defenderId, Game game, boolean allowUndo) {

    }

    @Override
    public void declareBlocker(UUID defenderId, UUID blockerId, UUID attackerId, Game game) {

    }

    @Override
    public List<Permanent> getAvailableAttackers(Game game) {
        return null;
    }

    @Override
    public List<Permanent> getAvailableAttackers(UUID defenderId, Game game) {
        return null;
    }

    @Override
    public List<Permanent> getAvailableBlockers(Game game) {
        return null;
    }

    @Override
    public void beginTurn(Game game) {

    }

    @Override
    public void endOfTurn(Game game) {

    }

    @Override
    public void phasing(Game game) {

    }

    @Override
    public void untap(Game game) {

    }

    @Override
    public ManaOptions getManaAvailable(Game game) {
        return null;
    }

    @Override
    public List<Ability> getPlayable(Game game, boolean hidden) {
        return null;
    }

    @Override
    public List<Ability> getPlayableOptions(Ability ability, Game game) {
        return null;
    }

    @Override
    public Set<UUID> getPlayableInHand(Game game) {
        return null;
    }

    @Override
    public LinkedHashMap<UUID, ActivatedAbility> getUseableActivatedAbilities(MageObject object, Zone zone, Game game) {
        return null;
    }

    @Override
    public void addCounters(Counter counter, Game game) {

    }

    @Override
    public List<UUID> getAttachments() {
        return null;
    }

    @Override
    public boolean addAttachment(UUID permanentId, Game game) {
        return false;
    }

    @Override
    public boolean removeAttachment(Permanent permanent, Game game) {
        return false;
    }

    @Override
    public void becomesActivePlayer() {

    }

    @Override
    public int getTurns() {
        return 0;
    }

    @Override
    public boolean lookAtFaceDownCard(Card card, Game game) {
        return false;
    }

    @Override
    public void setPriorityTimeLeft(int timeLeft) {

    }

    @Override
    public int getPriorityTimeLeft() {
        return 0;
    }

    @Override
    public void setReachedNextTurnAfterLeaving(boolean reachedNextTurnAfterLeaving) {

    }

    @Override
    public boolean hasReachedNextTurnAfterLeaving() {
        return false;
    }

    @Override
    public boolean canJoinTable(Table table) {
        return false;
    }

    @Override
    public void setCommanderId(UUID commanderId) {

    }

    @Override
    public UUID getCommanderId() {
        return null;
    }

    @Override
    public boolean moveCards(Cards cards, Zone fromZone, Zone toZone, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean moveCards(Card card, Zone fromZone, Zone toZone, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean moveCards(Set<Card> cards, Zone fromZone, Zone toZone, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean moveCards(Card card, Zone toZone, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean moveCards(Card card, Zone toZone, Ability source, Game game, boolean tapped, boolean faceDown, boolean byOwner, ArrayList<UUID> appliedEffects) {
        return false;
    }

    @Override
    public boolean moveCards(Cards cards, Zone toZone, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean moveCards(Set<Card> cards, Zone toZone, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean moveCards(Set<Card> cards, Zone toZone, Ability source, Game game, boolean tapped, boolean faceDown, boolean byOwner, ArrayList<UUID> appliedEffects) {
        return false;
    }

    @Override
    public boolean moveCardsToExile(Card card, Ability source, Game game, boolean withName, UUID exileId, String exileZoneName) {
        return false;
    }

    @Override
    public boolean moveCardsToExile(Set<Card> cards, Ability source, Game game, boolean withName, UUID exileId, String exileZoneName) {
        return false;
    }

    @Override
    public boolean moveCardToHandWithInfo(Card card, UUID sourceId, Game game) {
        return false;
    }

    @Override
    public boolean moveCardToHandWithInfo(Card card, UUID sourceId, Game game, boolean withName) {
        return false;
    }

    @Override
    public boolean moveCardToExileWithInfo(Card card, UUID exileId, String exileName, UUID sourceId, Game game, Zone fromZone, boolean withName) {
        return false;
    }

    @Override
    public boolean moveCardToGraveyardWithInfo(Card card, UUID sourceId, Game game, Zone fromZone) {
        return false;
    }

    @Override
    public Set<Card> moveCardsToGraveyardWithInfo(Set<Card> cards, Ability source, Game game, Zone fromZone) {
        return null;
    }

    @Override
    public boolean moveCardToLibraryWithInfo(Card card, UUID sourceId, Game game, Zone fromZone, boolean toTop, boolean withName) {
        return false;
    }

    @Override
    public boolean hasOpponent(UUID playerToCheckId, Game game) {
        return false;
    }

    @Override
    public void cleanUpOnMatchEnd() {

    }

    @Override
    public void setCastSourceIdWithAlternateMana(UUID sourceId, ManaCosts<ManaCost> manaCosts, Costs costs) {

    }

    @Override
    public UUID getCastSourceIdWithAlternateMana() {
        return null;
    }

    @Override
    public ManaCosts getCastSourceIdManaCosts() {
        return null;
    }

    @Override
    public Costs<Cost> getCastSourceIdCosts() {
        return null;
    }

    @Override
    public void addPermissionToShowHandCards(UUID watcherUserId) {

    }

    @Override
    public boolean hasUserPermissionToSeeHand(UUID userId) {
        return false;
    }

    @Override
    public void revokePermissionToSeeHandCards() {

    }

    @Override
    public boolean isRequestToShowHandCardsAllowed() {
        return false;
    }

    @Override
    public Set<UUID> getUsersAllowedToSeeHandCards() {
        return null;
    }

    @Override
    public boolean isInPayManaMode() {
        return false;
    }

    @Override
    public void setMatchPlayer(MatchPlayer matchPlayer) {

    }

    @Override
    public MatchPlayer getMatchPlayer() {
        return null;
    }

    @Override
    public boolean scry(int value, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean addTargets(Ability ability, Game game) {
        return false;
    }
}
