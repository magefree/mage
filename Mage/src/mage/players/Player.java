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

import mage.Constants.Outcome;
import mage.Constants.RangeOfInfluence;
import mage.MageItem;
import mage.MageObject;
import mage.abilities.*;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.ReplacementEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.decks.Deck;
import mage.choices.Choice;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.draft.Draft;
import mage.game.match.Match;
import mage.game.permanent.Permanent;
import mage.game.tournament.Tournament;
import mage.players.net.UserData;
import mage.target.Target;
import mage.target.TargetAmount;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.util.Copyable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public interface Player extends MageItem, Copyable<Player> {

    boolean isHuman();
    String getName();
    RangeOfInfluence getRange();
    Library getLibrary();
    Cards getSideboard();
    Cards getGraveyard();
    Abilities<Ability> getAbilities();
    void addAbility(Ability ability);
    Counters getCounters();
    int getLife();
    void setLife(int life, Game game);
    int loseLife(int amount, Game game);
    boolean isCanLoseLife();
    void setCanLoseLife(boolean canLoseLife);
    int gainLife(int amount, Game game);
    boolean isCanGainLife();
    void setCanGainLife(boolean canGainLife);
    boolean isLifeTotalCanChange();
    void setCanPayLifeCost(boolean canPayLifeCost);
    boolean canPayLifeCost();
    void setCanPaySacrificeCost(boolean canPaySacrificeCost);
    boolean canPaySacrificeCost();
    void setLifeTotalCanChange(boolean lifeTotalCanChange);
    int damage(int damage, UUID sourceId, Game game, boolean combatDamage, boolean preventable);
    int damage(int damage, UUID sourceId, Game game, boolean combatDamage, boolean preventable, ArrayList<UUID> appliedEffects);
    Cards getHand();
    int getLandsPlayed();
    int getLandsPerTurn();
    void setLandsPerTurn(int landsPerTurn);
    int getMaxHandSize();
    void setMaxHandSize(int maxHandSize);
    boolean isPassed();
    boolean isEmptyDraw();
    void pass();
    void resetPassed();
    boolean hasLost();
    boolean hasWon();
    boolean hasLeft();
    ManaPool getManaPool();
    Set<UUID> getInRange();
    boolean isTopCardRevealed();
    void setTopCardRevealed(boolean topCardRevealed);
    UserData getUserData();
    void setUserData(UserData userData);
    boolean canLose(Game game);
    boolean autoLoseGame();

    /**
     * Returns a set of players which turns under you control.
     * Doesn't include yourself.
     *
     * @return
     */
    Set<UUID> getPlayersUnderYourControl();

    /**
     * Defines player whose turn this player controls at the moment.
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
     * Note: For effects like "You control target player during that player's next turn".
     *
     * @return
     */
    boolean isGameUnderControl();

    /**
     * Returns false in case you don't control the game.
     *
     * Note: For effects like "You control target player during that player's next turn".
     *
     * @param value
     */
    void setGameUnderYourControl(boolean value);

    boolean isTestMode();
    void setTestMode(boolean value);
    void addAction(String action);
    void setAllowBadMoves(boolean allowBadMoves);

    void init(Game game);
    void init(Game game, boolean testMode);
    void useDeck(Deck deck, Game game);
    void reset();
    void shuffleLibrary(Game game);
    int drawCards(int num, Game game);
    boolean cast(SpellAbility ability, Game game, boolean noMana);
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
    boolean canBeTargetedBy(MageObject source, Game game);
    boolean hasProtectionFrom(MageObject source, Game game);
    boolean flipCoin(Game game);
    void discard(int amount, Ability source, Game game);
    void discardToMax(Game game);
    boolean discard(Card card, Ability source, Game game);
    void lost(Game game);
    void won(Game game);
    void leave();
    void concede(Game game);
    void abort();

    void revealCards(String name, Cards cards, Game game);
    void lookAtCards(String name, Cards cards, Game game);

    @Override
    Player copy();
    void restore(Player player);

    void setResponseString(String responseString);
    void setResponseUUID(UUID responseUUID);
    void setResponseBoolean(Boolean responseBoolean);
    void setResponseInteger(Integer data);

    boolean priority(Game game);
    boolean choose(Outcome outcome, Target target, UUID sourceId, Game game);
    boolean choose(Outcome outcome, Target target, UUID sourceId, Game game, Map<String, Serializable> options);
    boolean choose(Outcome outcome, Cards cards, TargetCard target, Game game);
    boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game);
    boolean chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game);
    boolean chooseTargetAmount(Outcome outcome, TargetAmount target, Ability source, Game game);
    boolean chooseMulligan(Game game);
    boolean chooseUse(Outcome outcome, String message, Game game);
    boolean choose(Outcome outcome, Choice choice, Game game);
    boolean choosePile(Outcome outcome, String message, List<? extends Card> pile1, List<? extends Card> pile2, Game game);
    boolean playMana(ManaCost unpaid, Game game);
    boolean playXMana(VariableManaCost cost, ManaCosts<ManaCost> costs, Game game);
    int chooseEffect(List<ReplacementEffect> rEffects, Game game);
    TriggeredAbility chooseTriggeredAbility(List<TriggeredAbility> abilities, Game game);
    Mode chooseMode(Modes modes, Ability source, Game game);
    void selectAttackers(Game game, UUID attackingPlayerId);
    void selectBlockers(Game game, UUID defendingPlayerId);
    UUID chooseAttackerOrder(List<Permanent> attacker, Game game);
    UUID chooseBlockerOrder(List<Permanent> blockers, Game game);
    void assignDamage(int damage, List<UUID> targets, String singleTargetName, UUID sourceId, Game game);
    int getAmount(int min, int max, String message, Game game);
    void sideboard(Match match, Deck deck);
    void construct(Tournament tournament, Deck deck);
    void pickCard(List<Card> cards, Deck deck, Draft draft);

    void declareAttacker(UUID attackerId, UUID defenderId, Game game);
    void declareBlocker(UUID blockerId, UUID attackerId, Game game);
    List<Permanent> getAvailableAttackers(Game game);
    List<Permanent> getAvailableBlockers(Game game);

    void beginTurn(Game game);
    void endOfTurn(Game game);
    void phasing(Game game);
    void untap(Game game);

    List<Ability> getPlayable(Game game, boolean hidden);
    List<Ability> getPlayableOptions(Ability ability, Game game);

    void addCounters(Counter counter, Game game);
    List<UUID> getAttachments();
    boolean addAttachment(UUID permanentId, Game game);
    boolean removeAttachment(UUID permanentId, Game game);

    /**
     * Signals that the player becomes active player in this turn.
     */
    void becomesActivePlayer();

    int getTurns();
}
