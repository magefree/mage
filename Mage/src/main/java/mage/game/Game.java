
package mage.game;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

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
import mage.cards.MeldCard;
import mage.cards.decks.Deck;
import mage.choices.Choice;
import mage.constants.*;
import mage.counters.Counters;
import mage.game.combat.Combat;
import mage.game.command.Commander;
import mage.game.command.Emblem;
import mage.game.command.Plane;
import mage.game.events.GameEvent;
import mage.game.events.Listener;
import mage.game.events.PlayerQueryEvent;
import mage.game.events.TableEvent;
import mage.game.match.MatchType;
import mage.game.permanent.Battlefield;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.stack.Spell;
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

    MeldCard getMeldCard(UUID meldId);

    void addMeldCard(UUID meldId, MeldCard meldCard);

    Object getCustomData();

    void setCustomData(Object data);

    GameOptions getOptions();

    MageObject getObject(UUID objectId);

    MageObject getBaseObject(UUID objectId);

    MageObject getEmblem(UUID objectId);

    UUID getControllerId(UUID objectId);

    UUID getOwnerId(UUID objectId);

    UUID getOwnerId(MageObject object);

    Spell getSpell(UUID spellId);

    Spell getSpellOrLKIStack(UUID spellId);

    Permanent getPermanent(UUID permanentId);

    Permanent getPermanentOrLKIBattlefield(UUID permanentId);

    Permanent getPermanentEntering(UUID permanentId);

    Map<UUID, Permanent> getPermanentsEntering();

    Map<Zone, HashMap<UUID, MageObject>> getLKI();

    Card getCard(UUID cardId);

    Optional<Ability> getAbility(UUID abilityId, UUID sourceId);

    void setZone(UUID objectId, Zone zone);

    void addPlayer(Player player, Deck deck);

    Player getPlayer(UUID playerId);

    Player getPlayerOrPlaneswalkerController(UUID playerId);

    Players getPlayers();

    PlayerList getPlayerList();

    /**
     * Returns a Set of opponents in range for the given playerId This return
     * also a player, that has dies this turn.
     *
     * @param playerId
     * @return
     */
    default Set<UUID> getOpponents(UUID playerId) {
        Player player = getPlayer(playerId);
        return player.getInRange().stream()
                .filter(opponentId -> !opponentId.equals(playerId))
                .collect(Collectors.toSet());

    }


    default boolean isActivePlayer(UUID playerId){
        return getActivePlayerId().equals(playerId);
    }

    /**
     * Checks if the given playerToCheckId is an opponent of player As long as
     * no team formats are implemented, this method returns always true for each
     * playerId not equal to the player it is checked for. Also if this player
     * is out of range. This method can't handle that only players in range are
     * processed because it can only return TRUE or FALSE.
     *
     * @param player
     * @param playerToCheckId
     * @return
     */
    default boolean isOpponent(Player player, UUID playerToCheckId) {
        return !player.getId().equals(playerToCheckId);
    }

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

    boolean checkIfGameIsOver();

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

    void fireAskPlayerEvent(UUID playerId, MessageToClient message, Ability source, Map<String, Serializable> options);

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

    void setConcedingPlayer(UUID playerId);

    void setManaPaymentMode(UUID playerId, boolean autoPayment);

    void setManaPaymentModeRestricted(UUID playerId, boolean autoPaymentRestricted);

    void setUseFirstManaAbility(UUID playerId, boolean useFirstManaAbility);

    void undo(UUID playerId);

    void emptyManaPools();

    void addEffect(ContinuousEffect continuousEffect, Ability source);

    void addEmblem(Emblem emblem, MageObject sourceObject, Ability source);

    void addEmblem(Emblem emblem, MageObject sourceObject, UUID toPlayerId);

    boolean addPlane(Plane plane, MageObject sourceObject, UUID toPlayerId);

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

    void resetControlAfterSpellResolve(UUID topId);

    boolean endTurn(Ability source);

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

    boolean getScopeRelevant();

    // players' timers
    void initTimer(UUID playerId);

    void resumeTimer(UUID playerId);

    void pauseTimer(UUID playerId);

    int getPriorityTime();

    void setPriorityTime(int priorityTime);

    UUID getStartingPlayerId();

    void setStartingPlayerId(UUID startingPlayerId);

    void saveRollBackGameState();

    boolean canRollbackTurns(int turnsToRollback);

    void rollbackTurns(int turnsToRollback);

    boolean executingRollback();

    void setEnterWithCounters(UUID sourceId, Counters counters);

    Counters getEnterWithCounters(UUID sourceId);

    UUID getMonarchId();

    void setMonarchId(Ability source, UUID monarchId);

    int damagePlayerOrPlaneswalker(UUID playerOrWalker, int damage, UUID sourceId, Game game, boolean combatDamage, boolean preventable);

    int damagePlayerOrPlaneswalker(UUID playerOrWalker, int damage, UUID sourceId, Game game, boolean combatDamage, boolean preventable, List<UUID> appliedEffects);
}
