package mage.game;

import mage.MageItem;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
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
import mage.game.command.*;
import mage.game.events.GameEvent;
import mage.game.events.Listener;
import mage.game.events.PlayerQueryEvent;
import mage.game.events.TableEvent;
import mage.game.match.MatchType;
import mage.game.mulligan.Mulligan;
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
import mage.util.Copyable;
import mage.util.MessageToClient;
import mage.util.functions.CopyApplier;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public interface Game extends MageItem, Serializable, Copyable<Game> {

    MatchType getGameType();

    int getNumPlayers();

    int getStartingLife();

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

    /**
     * Return object or LKI from battlefield
     *
     * @param objectId
     * @return
     */
    MageObject getObject(UUID objectId);

    MageObject getBaseObject(UUID objectId);

    MageObject getEmblem(UUID objectId);

    Dungeon getDungeon(UUID objectId);

    Dungeon getPlayerDungeon(UUID objectId);

    UUID getControllerId(UUID objectId);

    UUID getOwnerId(UUID objectId);

    UUID getOwnerId(MageObject object);

    Spell getSpell(UUID spellId);

    Spell getSpellOrLKIStack(UUID spellId);

    /**
     * Find permanent on the battlefield by id. If you works with cards and want to check it on battlefield then
     * use game.getState().getZone() instead. Card's id and permanent's id can be different (example: mdf card
     * puts half card to battlefield, not the main card).
     *
     * @param permanentId
     * @return
     */
    Permanent getPermanent(UUID permanentId);

    Permanent getPermanentOrLKIBattlefield(UUID permanentId);

    Permanent getPermanentEntering(UUID permanentId);

    Map<UUID, Permanent> getPermanentsEntering();

    Map<Zone, Map<UUID, MageObject>> getLKI();

    // Result must be checked for null. Possible errors search pattern: (\S*) = game.getCard.+\n(?!.+\1 != null)
    Card getCard(UUID cardId);

    Optional<Ability> getAbility(UUID abilityId, UUID sourceId);

    void setZone(UUID objectId, Zone zone);

    void addPlayer(Player player, Deck deck);

    // Result must be checked for null. Possible errors search pattern: (\S*) = game.getPlayer.+\n(?!.+\1 != null)
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
        if (player == null) {
            return new HashSet<>();
        }

        return player.getInRange().stream()
                .filter(opponentId -> !opponentId.equals(playerId))
                .collect(Collectors.toSet());
    }

    default boolean isActivePlayer(UUID playerId) {
        return getActivePlayerId() != null && getActivePlayerId().equals(playerId);
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

    boolean isSimulation();

    void setSimulation(boolean checkPlayableState);

    boolean inCheckPlayableState();

    void setCheckPlayableState(boolean checkPlayableState);

    MageObject getLastKnownInformation(UUID objectId, Zone zone);

    CardState getLastKnownInformationCard(UUID objectId, Zone zone);

    MageObject getLastKnownInformation(UUID objectId, Zone zone, int zoneChangeCounter);

    boolean getShortLivingLKI(UUID objectId, Zone zone);

    void rememberLKI(UUID objectId, Zone zone, MageObject object);

    void resetLKI();

    void resetShortLivingLKI();

    void setLosingPlayer(Player player);

    Player getLosingPlayer();

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

    void fireGetMultiAmountEvent(UUID playerId, List<String> messages, int min, int max, Map<String, Serializable> options);

    void fireChoosePileEvent(UUID playerId, String message, List<? extends Card> pile1, List<? extends Card> pile2);

    void fireInformEvent(String message);

    void fireStatusEvent(String message, boolean withTime, boolean withTurnInfo);

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
     * Creates and fires a damage prevention event
     *
     * @param damageEvent     damage event that will be replaced (instanceof
     *                        check will be done)
     * @param source          ability that's the source of the prevention effect
     * @param game
     * @param amountToPrevent max preventable amount
     * @return true prevention was successful / false prevention was replaced
     */
    PreventionEffectData preventDamage(GameEvent damageEvent, Ability source, Game game, int amountToPrevent);

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

    /**
     * Empty mana pool with mana burn and life lose checks
     *
     * @param source must be null for default game events
     */
    void emptyManaPools(Ability source);

    void addEffect(ContinuousEffect continuousEffect, Ability source);

    void addEmblem(Emblem emblem, MageObject sourceObject, Ability source);

    void addEmblem(Emblem emblem, MageObject sourceObject, UUID toPlayerId);

    boolean addPlane(Plane plane, MageObject sourceObject, UUID toPlayerId);

    void addCommander(Commander commander);

    Dungeon addDungeon(Dungeon dungeon, UUID playerId);

    void ventureIntoDungeon(UUID playerId);

    /**
     * Tells whether the current game has day or night, defaults to false
     */
    boolean hasDayNight();

    /**
     * Sets game to day or night, sets hasDayNight to true
     *
     * @param daytime day is true, night is false
     */
    void setDaytime(boolean daytime);

    /**
     * Returns true if hasDayNight is true and parameter matches current day/night value
     * Returns false if hasDayNight is false
     *
     * @param daytime day is true, night is false
     */
    boolean checkDayNight(boolean daytime);

    /**
     * Adds a permanent to the battlefield
     *
     * @param permanent
     * @param createOrder upcounting number from state about the create order of
     *                    all permanents. Can equal for multiple permanents, if
     *                    they go to battlefield at the same time. If the value
     *                    is set to 0, a next number will be set automatically.
     */
    void addPermanent(Permanent permanent, int createOrder);

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
    Permanent copyPermanent(Permanent copyFromPermanent, UUID copyToPermanentId, Ability source, CopyApplier applier);

    Permanent copyPermanent(Duration duration, Permanent copyFromPermanent, UUID copyToPermanentId, Ability source, CopyApplier applier);

    Card copyCard(Card cardToCopy, Ability source, UUID newController);

    void addTriggeredAbility(TriggeredAbility ability, GameEvent triggeringEvent);

    UUID addDelayedTriggeredAbility(DelayedTriggeredAbility delayedAbility, Ability source);

    UUID fireReflexiveTriggeredAbility(ReflexiveTriggeredAbility reflexiveAbility, Ability source);

    void applyEffects();

    boolean checkStateAndTriggered();

    void playPriority(UUID activePlayerId, boolean resuming);

    void resetControlAfterSpellResolve(UUID topId);

    boolean endTurn(Ability source);

    int doAction(Ability source, MageAction action);

    //game transaction methods
    void saveState(boolean bookmark);

    int bookmarkState();

    GameState restoreState(int bookmark, String context);

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

    void cheat(UUID ownerId, List<Card> library, List<Card> hand, List<PermanentCard> battlefield, List<Card> graveyard, List<Card> command);

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

    int damagePlayerOrPlaneswalker(UUID playerOrWalker, int damage, UUID attackerId, Ability source, Game game, boolean combatDamage, boolean preventable);

    int damagePlayerOrPlaneswalker(UUID playerOrWalker, int damage, UUID attackerId, Ability source, Game game, boolean combatDamage, boolean preventable, List<UUID> appliedEffects);

    Mulligan getMulligan();

    Set<UUID> getCommandersIds(Player player, CommanderCardType commanderCardType, boolean returnAllCardParts);

    /**
     * Return not played commander cards from command zone
     * Read comments for CommanderCardType for more info on commanderCardType usage
     *
     * @param player
     * @return
     */
    default Set<Card> getCommanderCardsFromCommandZone(Player player, CommanderCardType commanderCardType) {
        // commanders in command zone aren't cards so you must call getCard instead getObject
        return getCommandersIds(player, commanderCardType, false).stream()
                .map(this::getCard)
                .filter(Objects::nonNull)
                .filter(card -> Zone.COMMAND.equals(this.getState().getZone(card.getId())))
                .collect(Collectors.toSet());
    }

    /**
     * Return commander cards from any zones (main card from command and permanent card from battlefield)
     * Read comments for CommanderCardType for more info on commanderCardType usage
     *
     * @param player
     * @param commanderCardType commander or signature spell
     * @return
     */
    default Set<Card> getCommanderCardsFromAnyZones(Player player, CommanderCardType commanderCardType, Zone... searchZones) {
        Set<Zone> needZones = Arrays.stream(searchZones).collect(Collectors.toSet());
        if (needZones.isEmpty()) {
            throw new IllegalArgumentException("Empty zones list in searching commanders");
        }
        Set<UUID> needCommandersIds = this.getCommandersIds(player, commanderCardType, true);
        Set<Card> needCommandersCards = needCommandersIds.stream()
                .map(this::getCard)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Set<Card> res = new HashSet<>();

        // hand
        if (needZones.contains(Zone.ALL) || needZones.contains(Zone.HAND)) {
            needCommandersCards.stream()
                    .filter(card -> Zone.HAND.equals(this.getState().getZone(card.getId())))
                    .forEach(res::add);
        }

        // graveyard
        if (needZones.contains(Zone.ALL) || needZones.contains(Zone.GRAVEYARD)) {
            needCommandersCards.stream()
                    .filter(card -> Zone.GRAVEYARD.equals(this.getState().getZone(card.getId())))
                    .forEach(res::add);
        }

        // library
        if (needZones.contains(Zone.ALL) || needZones.contains(Zone.LIBRARY)) {
            needCommandersCards.stream()
                    .filter(card -> Zone.LIBRARY.equals(this.getState().getZone(card.getId())))
                    .forEach(res::add);
        }

        // battlefield (need permanent card)
        if (needZones.contains(Zone.ALL) || needZones.contains(Zone.BATTLEFIELD)) {
            needCommandersIds.stream()
                    .map(this::getPermanent)
                    .filter(Objects::nonNull)
                    .forEach(res::add);
        }

        // stack
        if (needZones.contains(Zone.ALL) || needZones.contains(Zone.STACK)) {
            needCommandersCards.stream()
                    .filter(card -> Zone.STACK.equals(this.getState().getZone(card.getId())))
                    .forEach(res::add);
        }

        // exiled
        if (needZones.contains(Zone.ALL) || needZones.contains(Zone.EXILED)) {
            needCommandersCards.stream()
                    .filter(card -> Zone.EXILED.equals(this.getState().getZone(card.getId())))
                    .forEach(res::add);
        }

        // command
        if (needZones.contains(Zone.ALL) || needZones.contains(Zone.COMMAND)) {
            res.addAll(getCommanderCardsFromCommandZone(player, commanderCardType));
        }

        // outside must be ignored (example: second side of MDFC commander after cast)
        if (needZones.contains(Zone.OUTSIDE)) {
            throw new IllegalArgumentException("Outside zone doesn't supported in searching commanders");
        }

        return res;
    }

    /**
     * Finds is it a commander card/object (use it in conditional and other things)
     *
     * @param player
     * @param object
     * @return
     */
    default boolean isCommanderObject(Player player, MageObject object) {
        UUID idToCheck = null;
        if (object instanceof Spell) {
            idToCheck = ((Spell) object).getCard().getId();
        }
        if (object instanceof CommandObject) {
            idToCheck = object.getId();
        }
        if (object instanceof Card) {
            idToCheck = ((Card) object).getMainCard().getId();
        }
        return idToCheck != null && this.getCommandersIds(player, CommanderCardType.COMMANDER_OR_OATHBREAKER, false).contains(idToCheck);
    }

    void setGameStopped(boolean gameStopped);

    boolean isGameStopped();
}
