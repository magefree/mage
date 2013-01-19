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

import mage.Constants.MultiplayerAttackOption;
import mage.Constants.RangeOfInfluence;
import mage.Constants.Zone;
import mage.MageItem;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffects;
import mage.actions.impl.MageAction;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.decks.Deck;
import mage.choices.Choice;
import mage.game.combat.Combat;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.events.Listener;
import mage.game.events.PlayerQueryEvent;
import mage.game.events.TableEvent;
import mage.game.match.MatchType;
import mage.game.permanent.Battlefield;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.stack.SpellStack;
import mage.game.turn.Phase;
import mage.game.turn.Step;
import mage.game.turn.Turn;
import mage.players.Player;
import mage.players.PlayerList;
import mage.players.Players;
import mage.util.functions.ApplyToPermanent;

import java.io.Serializable;
import java.util.*;

public interface Game extends MageItem, Serializable {

    MatchType getGameType();
    int getNumPlayers();
    int getLife();
    RangeOfInfluence getRangeOfInfluence();
    MultiplayerAttackOption getAttackOption();

    //game data methods
    void loadCards(Set<Card> cards, UUID ownerId);
    Collection<Card> getCards();
    Object getCustomData();
    void setCustomData(Object data);
    GameOptions getOptions();
    MageObject getObject(UUID objectId);
    MageObject getEmblem(UUID objectId);
    UUID getControllerId(UUID objectId);
    Permanent getPermanent(UUID permanentId);
    Card getCard(UUID cardId);
    Ability getAbility(UUID abilityId, UUID sourceId);
    void setZone(UUID objectId, Zone zone);
    void addPlayer(Player player, Deck deck) throws GameException;
    Player getPlayer(UUID playerId);
    Players getPlayers();
    PlayerList getPlayerList();
    Set<UUID> getOpponents(UUID playerId);
    Turn getTurn();
    Phase getPhase();
    Step getStep();
    int getTurnNum();
    boolean isMainPhase();
    boolean canPlaySorcery(UUID playerId);
    UUID getActivePlayerId();
    UUID getPriorityPlayerId();
    void leave(UUID playerId);
    boolean isGameOver();
    Battlefield getBattlefield();
    SpellStack getStack();
    Exile getExile();
    Combat getCombat();
    GameState getState();
    String getWinner();
    ContinuousEffects getContinuousEffects();
    GameStates getGameStates();
    void loadGameStates(GameStates states);
    Game copy();
    boolean isSimulation();
    void setSimulation(boolean simulation);
    MageObject getLastKnownInformation(UUID objectId, Zone zone);
    MageObject getShortLivingLKI(UUID objectId, Zone zone);
    void rememberLKI(UUID objectId, Zone zone, MageObject object);
    void resetLKI();
    void resetShortLivingLKI();
    void setLosingPlayer(Player player);
    Player getLosingPlayer();
    void setStateCheckRequired();
    boolean getStateCheckRequired();
    void resetForSourceId(UUID sourceId);

    //client event methods
    void addTableEventListener(Listener<TableEvent> listener);
    void addPlayerQueryEventListener(Listener<PlayerQueryEvent> listener);
    void fireAskPlayerEvent(UUID playerId, String message);
    void fireChooseEvent(UUID playerId, Choice choice);
    void fireSelectTargetEvent(UUID playerId, String message, Set<UUID> targets, boolean required, Map<String, Serializable> options);
    void fireSelectTargetEvent(UUID playerId, String message, Cards cards, boolean required, Map<String, Serializable> options);
    void fireSelectTargetEvent(UUID playerId, String message, List<TriggeredAbility> abilities);
    void fireSelectTargetEvent(UUID playerId, String message, List<Permanent> perms, boolean required);
    void fireSelectEvent(UUID playerId, String message);
    void fireLookAtCardsEvent(UUID playerId, String message, Cards cards);
    void firePriorityEvent(UUID playerId);
    void firePlayManaEvent(UUID playerId, String message);
    void firePlayXManaEvent(UUID playerId, String message);
    void fireGetChoiceEvent(UUID playerId, String message, List<? extends ActivatedAbility> choices);
    void fireGetModeEvent(UUID playerId, String message, Map<UUID, String> modes);
    void fireGetAmountEvent(UUID playerId, String message, int min, int max);
    void fireChoosePileEvent(UUID playerId, String message, List<? extends Card> pile1, List<? extends Card> pile2);
    void fireInformEvent(String message);
    void fireUpdatePlayersEvent();
    void informPlayers(String message);
    void informPlayer(Player player, String message);
    void debugMessage(String message);
    void fireErrorEvent(String message, Exception ex);

    //game event methods
    void fireEvent(GameEvent event);
    boolean replaceEvent(GameEvent event);

    //game play methods
    void start(UUID choosingPlayerId);
    void start(UUID choosingPlayerId, GameOptions options);
    void resume();
    void pause();
    boolean isPaused();
    void end();
    void mulligan(UUID playerId);
    void quit(UUID playerId);
    void concede(UUID playerId);
    void emptyManaPools();
    void addEffect(ContinuousEffect continuousEffect, Ability source);
    void addEmblem(Emblem emblem, Ability source);
    void addPermanent(Permanent permanent);

    /**
     * This version supports copying of copies of any depth.
     *
     * @param copyFromPermanent
     * @param copyToPermanent
     * @param source
     * @param applier
     */
    Permanent copyPermanent(Permanent copyFromPermanent, Permanent copyToPermanent, Ability source, ApplyToPermanent applier);
    
    Card copyCard(Card cardToCopy, Ability source, UUID newController);

    void addTriggeredAbility(TriggeredAbility ability);
    void addDelayedTriggeredAbility(DelayedTriggeredAbility delayedAbility);
    void applyEffects();
    boolean checkStateAndTriggered();
    void playPriority(UUID activePlayerId, boolean resuming);
    boolean endTurn(UUID playerId);

    int doAction(MageAction action);

    //game transaction methods
    void saveState();
    int bookmarkState();
    void restoreState(int bookmark);
    void removeBookmark(int bookmark);

    // game options
    void setGameOptions(GameOptions options);

    // game times
    Date getStartTime();
    Date getEndTime();

    // game cheats (for tests only)
    void cheat(UUID ownerId, Map<Zone, String> commands);
    void cheat(UUID ownerId, List<Card> library, List<Card> hand, List<PermanentCard> battlefield, List<Card> graveyard);
}
