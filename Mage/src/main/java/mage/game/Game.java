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

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageItem;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffects;
import mage.abilities.effects.PreventionEffectData;
import mage.actions.impl.MageAction;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.decks.Deck;
import mage.choices.Choice;
import mage.constants.Duration;
import mage.constants.MultiplayerAttackOption;
import mage.constants.PlayerAction;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.counters.Counters;
import mage.game.combat.Combat;
import mage.game.command.Commander;
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
import mage.util.MessageToClient;
import mage.util.functions.ApplyToPermanent;

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

    MageObject getBaseObject(UUID objectId);

    MageObject getEmblem(UUID objectId);

    UUID getControllerId(UUID objectId);

    UUID getOwnerId(UUID objectId);

    UUID getOwnerId(MageObject object);

    Permanent getPermanent(UUID permanentId);

    Permanent getPermanentOrLKIBattlefield(UUID permanentId);

    Permanent getPermanentEntering(UUID permanentId);

    Map<UUID, Permanent> getPermanentsEntering();

    Map<Zone, HashMap<UUID, MageObject>> getLKI();

    Card getCard(UUID cardId);

    Ability getAbility(UUID abilityId, UUID sourceId);

    void setZone(UUID objectId, Zone zone);

    void addPlayer(Player player, Deck deck) throws GameException;

    Player getPlayer(UUID playerId);

    Players getPlayers();

    PlayerList getPlayerList();

    /**
     * Returns a Set of opponents in range for the given playerId
     *
     * @param playerId
     * @return
     */
    Set<UUID> getOpponents(UUID playerId);

    /**
     * Checks if the given playerToCheckId is an opponent of player
     *
     * @param player
     * @param playerToCheckId
     * @return
     */
    boolean isOpponent(Player player, UUID playerToCheckId);

    Turn getTurn();

    Phase getPhase();

    Step getStep();

    int getTurnNum();

    boolean isMainPhase();

    boolean canPlaySorcery(UUID playerId);

    /**
     * Id of the player the current turn it is.
     *
     * @return
     */
    UUID getActivePlayerId();

    UUID getPriorityPlayerId();

    boolean gameOver(UUID playerId);

    boolean hasEnded();

    Battlefield getBattlefield();

    SpellStack getStack();

    Exile getExile();

    Combat getCombat();

    GameState getState();

    String getWinner();

    void setDraw(UUID playerId);

    boolean isADraw();

    ContinuousEffects getContinuousEffects();

    GameStates getGameStates();

    void loadGameStates(GameStates states);

    Game copy();

    boolean isSimulation();

    void setSimulation(boolean simulation);

    MageObject getLastKnownInformation(UUID objectId, Zone zone);

    MageObject getLastKnownInformation(UUID objectId, Zone zone, int zoneChangeCounter);

    boolean getShortLivingLKI(UUID objectId, Zone zone);

    void rememberLKI(UUID objectId, Zone zone, MageObject object);

    void resetLKI();

    void resetShortLivingLKI();

    void setLosingPlayer(Player player);

    Player getLosingPlayer();

    void setStateCheckRequired();

    boolean getStateCheckRequired();

    //client event methods
    void addTableEventListener(Listener<TableEvent> listener);

    void addPlayerQueryEventListener(Listener<PlayerQueryEvent> listener);

    void fireAskPlayerEvent(UUID playerId, MessageToClient message, Ability source);

    void fireChooseChoiceEvent(UUID playerId, Choice choice);

    void fireSelectTargetEvent(UUID playerId, MessageToClient message, Set<UUID> targets, boolean required, Map<String, Serializable> options);

    void fireSelectTargetEvent(UUID playerId, MessageToClient message, Cards cards, boolean required, Map<String, Serializable> options);

    void fireSelectTargetTriggeredAbilityEvent(UUID playerId, String message, List<TriggeredAbility> abilities);

    void fireSelectTargetEvent(UUID playerId, String message, List<Permanent> perms, boolean required);

    void fireSelectEvent(UUID playerId, String message);

    void fireSelectEvent(UUID playerId, String message, Map<String, Serializable> options);

    void firePriorityEvent(UUID playerId);

    void firePlayManaEvent(UUID playerId, String message, Map<String, Serializable> options);

    void firePlayXManaEvent(UUID playerId, String message);

    void fireGetChoiceEvent(UUID playerId, String message, MageObject object, List<? extends ActivatedAbility> choices);

    void fireGetModeEvent(UUID playerId, String message, Map<UUID, String> modes);

    void fireGetAmountEvent(UUID playerId, String message, int min, int max);

    void fireChoosePileEvent(UUID playerId, String message, List<? extends Card> pile1, List<? extends Card> pile2);

    void fireInformEvent(String message);

    void fireStatusEvent(String message, boolean withTime);

    void fireUpdatePlayersEvent();

    void informPlayers(String message);

    void informPlayer(Player player, String message);

    void debugMessage(String message);

    void fireErrorEvent(String message, Exception ex);

    void fireGameEndInfo();

    //game event methods
    void fireEvent(GameEvent event);

    /**
     * The events are stored until the resolution of the current effect ends and
     * fired then all together (e.g. X lands enter the battlefield from
     * Scapeshift)
     *
     * @param event
     */
    void addSimultaneousEvent(GameEvent event);

    boolean replaceEvent(GameEvent event);

    boolean replaceEvent(GameEvent event, Ability targetAbility);

    /**
     * Creates and fires an damage prevention event
     *
     * @param damageEvent damage event that will be replaced (instanceof check
     * will be done)
     * @param source ability that's the source of the prevention effect
     * @param game
     * @param amountToPrevent max preventable amount
     * @return true prevention was successfull / false prevention was replaced
     */
    PreventionEffectData preventDamage(GameEvent damageEvent, Ability source, Game game, int amountToPrevent);

    /**
     * Creates and fires an damage prevention event
     *
     * @param event damage event that will be replaced (instanceof check will be
     * done)
     * @param source ability that's the source of the prevention effect
     * @param game
     * @param preventAllDamage true if there is no limit to the damage that can
     * be prevented
     * @return true prevention was successfull / false prevention was replaced
     */
    PreventionEffectData preventDamage(GameEvent event, Ability source, Game game, boolean preventAllDamage);

    void start(UUID choosingPlayerId);

    void resume();

    void pause();

    boolean isPaused();

    void end();

    void cleanUp();
    /*
     * Gives back the number of cards the player has after the next mulligan
     */

    int mulliganDownTo(UUID playerId);

    void mulligan(UUID playerId);

    void endMulligan(UUID playerId);

    // void quit(UUID playerId);
    void timerTimeout(UUID playerId);

    void idleTimeout(UUID playerId);

    void concede(UUID playerId);

    void setManaPaymentMode(UUID playerId, boolean autoPayment);

    void setManaPaymentModeRestricted(UUID playerId, boolean autoPaymentRestricted);

    void undo(UUID playerId);

    void emptyManaPools();

    void addEffect(ContinuousEffect continuousEffect, Ability source);

    void addEmblem(Emblem emblem, Ability source);

    void addEmblem(Emblem emblem, Ability source, UUID toPlayerId);

    void addCommander(Commander commander);

    void addPermanent(Permanent permanent);

    // priority method
    void sendPlayerAction(PlayerAction playerAction, UUID playerId, Object data);

    /**
     * This version supports copying of copies of any depth.
     *
     * @param copyFromPermanent
     * @param copyToPermanentId
     * @param source
     * @param applier
     * @return
     */
    Permanent copyPermanent(Permanent copyFromPermanent, UUID copyToPermanentId, Ability source, ApplyToPermanent applier);

    Permanent copyPermanent(Duration duration, Permanent copyFromPermanent, UUID copyToPermanentId, Ability source, ApplyToPermanent applier);

    Card copyCard(Card cardToCopy, Ability source, UUID newController);

    void addTriggeredAbility(TriggeredAbility ability);

    UUID addDelayedTriggeredAbility(DelayedTriggeredAbility delayedAbility);

    UUID addDelayedTriggeredAbility(DelayedTriggeredAbility delayedAbility, Ability source);

    void applyEffects();

    boolean checkStateAndTriggered();

    void playPriority(UUID activePlayerId, boolean resuming);

    boolean endTurn();

    int doAction(MageAction action);

    //game transaction methods
    void saveState(boolean bookmark);

    int bookmarkState();

    void restoreState(int bookmark, String context);

    void removeBookmark(int bookmark);

    int getSavedStateSize();

    boolean isSaveGame();

    void setSaveGame(boolean saveGame);

    // game options
    void setGameOptions(GameOptions options);

    // game times
    Date getStartTime();

    Date getEndTime();

    // game cheats (for tests only)
    void cheat(UUID ownerId, Map<Zone, String> commands);

    void cheat(UUID ownerId, List<Card> library, List<Card> hand, List<PermanentCard> battlefield, List<Card> graveyard);

    // controlling the behaviour of replacement effects while permanents entering the battlefield
    void setScopeRelevant(boolean scopeRelevant);

    public boolean getScopeRelevant();

    // players' timers
    void initTimer(UUID playerId);

    void resumeTimer(UUID playerId);

    void pauseTimer(UUID playerId);

    int getPriorityTime();

    void setPriorityTime(int priorityTime);

    UUID getStartingPlayerId();

    void saveRollBackGameState();

    boolean canRollbackTurns(int turnsToRollback);

    void rollbackTurns(int turnsToRollback);

    boolean executingRollback();

    void setEnterWithCounters(UUID sourceId, Counters counters);

    Counters getEnterWithCounters(UUID sourceId);
}
