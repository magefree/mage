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

import mage.Constants.*;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.ChancellorAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffects;
import mage.abilities.keyword.LeylineAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.actions.impl.MageAction;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.decks.Deck;
import mage.choices.Choice;
import mage.counters.CounterType;
import mage.filter.Filter;
import mage.filter.Filter.ComparisonScope;
import mage.filter.common.*;
import mage.game.combat.Combat;
import mage.game.events.*;
import mage.game.events.TableEvent.EventType;
import mage.game.permanent.Battlefield;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.PermanentImpl;
import mage.game.stack.SpellStack;
import mage.game.stack.StackObject;
import mage.game.turn.Phase;
import mage.game.turn.Step;
import mage.game.turn.Turn;
import mage.players.Player;
import mage.players.PlayerList;
import mage.players.Players;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.watchers.common.CastSpellLastTurnWatcher;
import mage.watchers.common.MiracleWatcher;
import mage.watchers.common.MorbidWatcher;
import mage.watchers.common.PlayerDamagedBySourceWatcher;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public abstract class GameImpl<T extends GameImpl<T>> implements Game, Serializable {

	private final static transient Logger logger = Logger.getLogger(GameImpl.class);

	private static FilterAura filterAura = new FilterAura();
    private static FilterLegendaryPermanent filterLegendary = new FilterLegendaryPermanent();
	private static FilterEquipment filterEquipment = new FilterEquipment();
	private static FilterFortification filterFortification = new FilterFortification();
	private static Random rnd = new Random();

	private transient Stack<Integer> savedStates = new Stack<Integer>();
	private transient Object customData;
	protected boolean simulation = false;

	protected final UUID id;
	protected boolean ready;
	protected transient TableEventSource tableEventSource = new TableEventSource();
	protected transient PlayerQueryEventSource playerQueryEventSource = new PlayerQueryEventSource();

	protected Map<UUID, Card> gameCards = new HashMap<UUID, Card>();
	protected Map<UUID, MageObject> lki = new HashMap<UUID, MageObject>();
	protected GameState state;
    
    protected Date startTime;
    protected Date endTime;
	protected UUID startingPlayerId;
	protected UUID winnerId;

	protected transient GameStates gameStates = new GameStates();
	protected RangeOfInfluence range;
	protected MultiplayerAttackOption attackOption;
	protected GameOptions gameOptions;

    public static volatile int copyCount = 0;
    public static volatile long copyTime = 0;

    private transient LinkedList<MageAction> actions;
    private Player scorePlayer;
    private int score = 0;
    private Player losingPlayer;
    private boolean stateCheckRequired = false;

	@Override
	public abstract T copy();

	public GameImpl(MultiplayerAttackOption attackOption, RangeOfInfluence range) {
		this.id = UUID.randomUUID();
		this.range = range;
		this.attackOption = attackOption;
		this.state = new GameState();
        this.actions = new LinkedList<MageAction>();
	}

	public GameImpl(final GameImpl<T> game) {
        long t1 = 0;
        if (logger.isDebugEnabled()) {
            t1 = System.currentTimeMillis();
        }
		this.id = game.id;
		this.ready = game.ready;
		this.startingPlayerId = game.startingPlayerId;
		this.winnerId = game.winnerId;
		this.range = game.range;
		this.attackOption = game.attackOption;
		this.state = game.state.copy();
		// Issue 350
		this.gameCards = game.gameCards;
//        for (Map.Entry<UUID, Card> entry: game.gameCards.entrySet()) {
//            this.gameCards.put(entry.getKey(), entry.getValue().copy());
//		}
		this.simulation = game.simulation;
        this.gameOptions = game.gameOptions;
        this.lki.putAll(game.lki);
        if (logger.isDebugEnabled()) {
            copyCount++;
            copyTime += (System.currentTimeMillis() - t1);
        }
        this.actions = new LinkedList<MageAction>();
        this.stateCheckRequired = game.stateCheckRequired;
        this.scorePlayer = game.scorePlayer;
	}

	@Override
	public boolean isSimulation() {
		return simulation;
	}
	
	@Override
	public void setSimulation(boolean simulation) {
		this.simulation = simulation;
	}

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public Object getCustomData() {
		return customData;
	}

	@Override
	public void setCustomData(Object data) {
		this.customData = data;
	}
    
    @Override
    public GameOptions getOptions() {
        return gameOptions;
    }

	@Override
	public void loadCards(Set<Card> cards, UUID ownerId) {
		for (Card card: cards) {
            if (card instanceof PermanentCard) {
                card = ((PermanentCard)card).getCard();
            }
			card.setOwnerId(ownerId);
			gameCards.put(card.getId(), card);
            state.addCard(card);
		}
	}

	@Override
	public Collection<Card> getCards() {
		return gameCards.values();
	}

	@Override
	public void addPlayer(Player player, Deck deck) throws GameException {
		player.useDeck(deck, this);
		state.addPlayer(player);
	}

	@Override
	public RangeOfInfluence getRangeOfInfluence() {
		return range;
	}

	@Override
	public MultiplayerAttackOption getAttackOption() {
		return attackOption;
	}

	@Override
	public Player getPlayer(UUID playerId) {
		if (playerId == null)
			return null;
		return state.getPlayer(playerId);
	}

	@Override
	public MageObject getObject(UUID objectId) {
		if (objectId == null)
			return null;
		MageObject object;
		if (state.getBattlefield().containsPermanent(objectId)) {
			object = state.getBattlefield().getPermanent(objectId);
			state.setZone(objectId, Zone.BATTLEFIELD);
			return object;
		}
		for (StackObject item: state.getStack()) {
			if (item.getId().equals(objectId)) {
				state.setZone(objectId, Zone.STACK);
				return item;
			}
		}
		object = getCard(objectId);
		if (object != null)
			return object;

		return null;
	}

	@Override
	public UUID getControllerId(UUID objectId) {
		if (objectId == null) {
			return null;
		}
		MageObject object = getObject(objectId);
		if (object != null) {
			if (object instanceof Permanent) {
				return ((Permanent)object).getControllerId();
			}
			if (object instanceof Card) {
				return ((Card)object).getOwnerId();
			}
		}
		return null;
	}

	@Override
	public Permanent getPermanent(UUID permanentId) {
		return state.getPermanent(permanentId);
	}

	@Override
	public Card getCard(UUID cardId) {
		if (cardId == null)
			return null;
		return gameCards.get(cardId);
	}

//	@Override
//	public Zone getZone(UUID objectId) {
//		return state.getZone(objectId);
//	}

	@Override
	public void setZone(UUID objectId, Zone zone) {
		state.setZone(objectId, zone);
	}

	@Override
	public GameStates getGameStates() {
		return gameStates;
	}
	
	@Override
	public void loadGameStates(GameStates states) {
		this.gameStates = states;
	}

	@Override
	public void saveState() {
		if (!simulation && gameStates != null)
			gameStates.save(state);
	}

	@Override
	public boolean isGameOver() {
		if (state.isGameOver())
			return true;
		int remainingPlayers = 0;
		int numLosers = 0;
		for (Player player: state.getPlayers().values()) {
			if (!player.hasLeft())
				remainingPlayers++;
			if (player.hasLost())
				numLosers++;
		}
		if (remainingPlayers <= 1 || numLosers >= state.getPlayers().size() - 1) {
			state.endGame();
            endTime = new Date();
			return true;
		}
		return false;
	}

	@Override
	public String getWinner() {
		if (winnerId == null)
			return "Game is a draw";
		return "Player " + state.getPlayer(winnerId).getName() + " is the winner";
	}

	@Override
	public GameState getState() {
		return state;
	}

	@Override
	public int bookmarkState() {
		if (!simulation) {
			saveState();
			if (logger.isDebugEnabled())
				logger.debug("Bookmarking state: " + gameStates.getSize());
			savedStates.push(gameStates.getSize() - 1);
            return savedStates.size();
		}
        return 0;
	}

	@Override
	public void restoreState(int bookmark) {
		if (!simulation) {
            if (bookmark != 0) {
                int stateNum = savedStates.get(bookmark - 1);
                removeBookmark(bookmark);
                GameState restore = gameStates.rollback(stateNum);
                if (restore != null)
                    state.restore(restore);
            }
		}
	}

	@Override
	public void removeBookmark(int bookmark) {
		if (!simulation) {
            if (bookmark != 0) {
                while (savedStates.size() > bookmark)
                    savedStates.pop();
            }
		}
	}

	@Override
	public void start(UUID choosingPlayerId) {
		start(choosingPlayerId, this.gameOptions != null ? gameOptions : GameOptions.getDefault());
	}

	@Override
	public void start(UUID choosingPlayerId, GameOptions options) {
        startTime = new Date();
        this.gameOptions = options;
        scorePlayer = state.getPlayers().values().iterator().next();
		init(choosingPlayerId, options);
        play(startingPlayerId);
		//saveState();
	}

    @Override
    public void resume() {
		PlayerList players = state.getPlayerList(state.getActivePlayerId());
		Player player = getPlayer(players.get());
        boolean wasPaused = state.isPaused();
        state.resume();
		if (!isGameOver()) {
//            if (simulation)
//                logger.info("Turn " + Integer.toString(state.getTurnNum()));
			fireInformEvent("Turn " + Integer.toString(state.getTurnNum()));
			if (checkStopOnTurnOption()) return;
			state.getTurn().resumePlay(this, wasPaused);
			if (!isPaused() && !isGameOver()) {
                endOfTurn();
                player = players.getNext(this);
                state.setTurnNum(state.getTurnNum() + 1);
            }
		}
        play(player.getId());
    }
    
    protected void play(UUID nextPlayerId) {
        if (!isPaused() && !isGameOver()) {
            PlayerList players = state.getPlayerList(nextPlayerId);
            Player player = getPlayer(players.get());
            while (!isPaused() && !isGameOver()) {
//                if (simulation)
//                    logger.info("Turn " + Integer.toString(state.getTurnNum()));
                fireInformEvent("Turn " + Integer.toString(state.getTurnNum()));
                if (checkStopOnTurnOption()) return;
                state.setActivePlayerId(player.getId());
                state.getTurn().play(this, player.getId());
                if (isPaused() || isGameOver())
                    break;
                endOfTurn();
                player = players.getNext(this);
                state.setTurnNum(state.getTurnNum() + 1);
            }
        }
        if (isGameOver())
            winnerId = findWinnersAndLosers();
    }
    
	private boolean checkStopOnTurnOption() {
		if (gameOptions.stopOnTurn != null && gameOptions.stopAtStep == PhaseStep.UNTAP) {
			if (gameOptions.stopOnTurn.equals(state.getTurnNum())) {
				winnerId = null; //DRAW
				saveState();
				return true;
			}
		}
		return false;
	}
    
	protected void init(UUID choosingPlayerId, GameOptions gameOptions) {
		for (Player player: state.getPlayers().values()) {
			player.beginTurn(this);
		}
		fireInformEvent("game has started");
		//saveState();

		//20091005 - 103.1
        if (!gameOptions.skipInitShuffling) { //don't shuffle in test mode for card injection on top of player's libraries
            for (Player player: state.getPlayers().values()) {
                player.shuffleLibrary(this);
            }
        }

		//20091005 - 103.2
		TargetPlayer targetPlayer = new TargetPlayer();
		targetPlayer.setRequired(true);
		targetPlayer.setTargetName("starting player");
		Player choosingPlayer;
		if (choosingPlayerId == null) {
			choosingPlayer = getPlayer(pickChoosingPlayer());
		}
		else {
			choosingPlayer = this.getPlayer(choosingPlayerId);
		}
		if (choosingPlayer.choose(Outcome.Benefit, targetPlayer, null, this)) {
			startingPlayerId = ((List<UUID>)targetPlayer.getTargets()).get(0);
			fireInformEvent(state.getPlayer(startingPlayerId).getName() + " will start");
		}
		else {
			return;
		}

		//saveState();

		//20091005 - 103.3
		for (UUID playerId: state.getPlayerList(startingPlayerId)) {
			Player player = getPlayer(playerId);
			if (!gameOptions.testMode || player.getLife() == 0) {
				player.setLife(this.getLife(), this);
			}
			if (!gameOptions.testMode) {
				player.drawCards(7, this);
			}
		}

		//20091005 - 103.4
		for (UUID playerId: state.getPlayerList(startingPlayerId)) {
			Player player = getPlayer(playerId);
			while (player.getHand().size() > 0 && player.chooseMulligan(this)) {
				mulligan(player.getId());
			}
			fireInformEvent(player.getName() + " keeps hand");
			//saveState();
		}

        for (UUID playerId : state.getPlayerList(startingPlayerId)) {
            state.getWatchers().add(new PlayerDamagedBySourceWatcher(playerId));
        }
        state.getWatchers().add(new MorbidWatcher());
        state.getWatchers().add(new CastSpellLastTurnWatcher());
        state.getWatchers().add(new MiracleWatcher());
        
		//20100716 - 103.5
		for (UUID playerId: state.getPlayerList(startingPlayerId)) {
			Player player = getPlayer(playerId);
			for (Card card: player.getHand().getCards(this)) {
				if (card.getAbilities().containsKey(LeylineAbility.getInstance().getId())) {
					if (player.chooseUse(Outcome.PutCardInPlay, "Do you wish to put " + card.getName() + " on the battlefield?", this)) {
						card.putOntoBattlefield(this, Zone.HAND, null, player.getId());
					}
				}
                for (Ability ability: card.getAbilities()) {
                    if (ability instanceof ChancellorAbility) {
                        if (player.chooseUse(Outcome.PutCardInPlay, "Do you wish to reveal " + card.getName() + "?", this)) {
                            Cards cards = new CardsImpl();
                            cards.add(card);
                            player.revealCards("Revealed", cards, this);
                            ability.resolve(this);
                        }
                    }
                }
			}
		}
	}

	protected UUID findWinnersAndLosers() {
		UUID winner = null;
		for (Player player: state.getPlayers().values()) {
			if (player.hasWon() || (!player.hasLost() && !player.hasLeft())) {
				player.won(this);
				winner = player.getId();
				break;
			}
		}
		for (Player player: state.getPlayers().values()) {
			if (winner != null && !player.getId().equals(winner)) {
				player.lost(this);
			}
		}
		return winner;
	}

	protected void endOfTurn() {
		for (Player player: getPlayers().values()) {
			player.endOfTurn(this);
		}
		state.getWatchers().reset();
	}

	protected UUID pickChoosingPlayer() {
		UUID[] players = getPlayers().keySet().toArray(new UUID[0]);
		UUID playerId = players[rnd.nextInt(players.length)];
		fireInformEvent(state.getPlayer(playerId).getName() + " won the toss");
		return playerId;
	}

    @Override
    public void pause() {
        state.pause();
    }
    
    @Override
    public boolean isPaused() {
        return state.isPaused();
    }
    
	@Override
	public void end() {
		state.endGame();
		for (Player player: state.getPlayers().values()) {
			player.abort();
		}
	}

	@Override
	public void addTableEventListener(Listener<TableEvent> listener) {
		tableEventSource.addListener(listener);
	}

	@Override
	public void mulligan(UUID playerId) {
		Player player = getPlayer(playerId);
		int numCards = player.getHand().size();
		player.getLibrary().addAll(player.getHand().getCards(this), this);
		player.getHand().clear();
		player.shuffleLibrary(this);
		player.drawCards(numCards - 1, this);
		fireInformEvent(player.getName() + " mulligans down to " + Integer.toString(numCards - 1) + " cards");
	}

	@Override
	public synchronized void quit(UUID playerId) {
		Player player = state.getPlayer(playerId);
		if (player != null) {
			leave(playerId);
			fireInformEvent(player.getName() + " has left the game.");
		}
	}

	@Override
	public synchronized void concede(UUID playerId) {
		Player player = state.getPlayer(playerId);
		if (player != null) {
			player.concede(this);
			fireInformEvent(player.getName() + " has conceded.");
		}
	}

	@Override
	public void playPriority(UUID activePlayerId, boolean resuming) {
        int bookmark = 0;
		try {
			while (!isPaused() && !isGameOver()) {
                if (!resuming) {
                    state.getPlayers().resetPassed();
                    state.getPlayerList().setCurrent(activePlayerId);
                }
                else {
                    state.getPlayerList().setCurrent(this.getPriorityPlayerId());
                }
				Player player;
				while (!isPaused() && !isGameOver()) {
                    try {
                        //if (bookmark == 0)
                            //bookmark = bookmarkState();
                        player = getPlayer(state.getPlayerList().get());
                        state.setPriorityPlayerId(player.getId());
                        while (!player.isPassed() && !player.hasLost() && !player.hasLeft() && !isPaused() && !isGameOver()) {
                            if (!resuming) {
                                checkStateAndTriggered();
                                if (isPaused() || isGameOver()) return;
                                // resetPassed should be called if player performs any action
                                if (player.priority(this))
                                    applyEffects();
                                if (isPaused()) return;
                            }
                            resuming = false;
                        }
                        resuming = false;
                        if (isPaused() || isGameOver()) return;
                        if (allPassed()) {
                            if (!state.getStack().isEmpty()) {
                               //20091005 - 115.4
                                resolve();
                                applyEffects();
                                state.getPlayers().resetPassed();
                                fireUpdatePlayersEvent();
                                state.getRevealed().reset();
                                break;
                            } else {
                                //removeBookmark(bookmark);
                                return;
                            }
                        }
                    }
                    catch (Exception ex) {
                        logger.fatal("Game exception ", ex);
                        this.fireErrorEvent("Game exception occurred: ", ex);
                        //restoreState(bookmark);
                        bookmark = 0;
                        continue;
                    }
					state.getPlayerList().getNext();
				}
                //removeBookmark(bookmark);
                bookmark = 0;
			}
		} catch (Exception ex) {
			logger.fatal("Game exception ", ex);
			this.fireErrorEvent("Game exception occurred: ", ex);
		} finally {
			resetLKI();
		}
	}

	//resolve top StackObject
	protected void resolve() {
		StackObject top = null;
		try {
			top = state.getStack().peek();
			top.resolve(this);
		} finally {
			if (top != null)
				state.getStack().remove(top);
		}
	}

    protected boolean allPassed() {
		for (Player player: state.getPlayers().values()) {
			if (!player.isPassed() && !player.hasLost() && !player.hasLeft())
				return false;
		}
		return true;
	}

	@Override
	public void emptyManaPools() {
		if (!replaceEvent(new GameEvent(GameEvent.EventType.EMPTY_MANA_POOLS, null, null, null))) {
			for (Player player: getPlayers().values()) {
				player.getManaPool().emptyPool();
			}
		}
	}

	@Override
	public synchronized void applyEffects() {
		state.applyEffects(this);
	}

	@Override
	public void addEffect(ContinuousEffect continuousEffect, Ability source) {
		ContinuousEffect newEffect = (ContinuousEffect)continuousEffect.copy();
		Ability newAbility = source.copy();
		newEffect.newId();
		newEffect.setTimestamp();
		newEffect.init(newAbility, this);
		state.addEffect(newEffect, newAbility);
	}

	@Override
	public void addTriggeredAbility(TriggeredAbility ability) {
        if (ability instanceof TriggeredManaAbility) {
            // 20110715 - 605.4
            ability.resolve(this);
        }
        else {
            TriggeredAbility newAbility = (TriggeredAbility) ability.copy();
            newAbility.newId();
            state.addTriggeredAbility(newAbility);
        }
	}
	
	@Override
	public void addDelayedTriggeredAbility(DelayedTriggeredAbility delayedAbility) {
		DelayedTriggeredAbility newAbility = (DelayedTriggeredAbility) delayedAbility.copy();
		newAbility.newId();
		state.addDelayedTriggeredAbility(newAbility);
	}

	@Override
	public boolean checkStateAndTriggered() {
		boolean somethingHappened = false;
		//20091005 - 115.5
		while (!isPaused() && !this.isGameOver()) {
			if (!checkStateBasedActions() ) {
				if (isPaused() || this.isGameOver() || !checkTriggered()) {
					break;
				}
			}
			somethingHappened = true;
		}
		return somethingHappened;
	}

	public boolean checkTriggered() {
		boolean played = false;
		for (UUID playerId: state.getPlayerList(state.getActivePlayerId())) {
			Player player = getPlayer(playerId);
			while (true) {
				List<TriggeredAbility> abilities = state.getTriggered(player.getId());
				if (abilities.isEmpty())
					break;
				if (abilities.size() == 1) {
					state.removeTriggeredAbility(abilities.get(0));
					played |= player.triggerAbility(abilities.get(0), this);
				}
				else {
					TriggeredAbility ability = player.chooseTriggeredAbility(abilities, this);
					state.removeTriggeredAbility(ability);
					played |= player.triggerAbility(ability, this);
				}
			}
		}
		return played;
	}

	protected boolean checkStateBasedActions() {
		boolean somethingHappened = false;

		//20091005 - 704.5a/704.5b/704.5c
		for (Player player: state.getPlayers().values()) {
			if (!player.hasLost() && (player.getLife() <= 0 || player.isEmptyDraw() || player.getCounters().getCount(CounterType.POISON) >= 10)) {
				player.lost(this);
			}
		}
        
        List<Permanent> planeswalkers = new ArrayList<Permanent>();
        List<Permanent> legendary = new ArrayList<Permanent>();
        for (Permanent perm: getBattlefield().getAllActivePermanents()) {
            if (perm.getCardType().contains(CardType.CREATURE)) {
                //20091005 - 704.5f
                if (perm.getToughness().getValue() <= 0) {
                    if (perm.moveToZone(Zone.GRAVEYARD, null, this, false)) {
                        somethingHappened = true;
                        continue;
                    }
                }
                //20091005 - 704.5g/704.5h
                else if (perm.getToughness().getValue() <= perm.getDamage() || perm.isDeathtouched()) {
                    if (perm.destroy(null, this, false)) {
                        somethingHappened = true;
                        continue;
                    }
                }
            }
            if (perm.getCardType().contains(CardType.PLANESWALKER)) {
                //20091005 - 704.5i
                if (perm.getCounters().getCount(CounterType.LOYALTY) == 0) {
                    if (perm.moveToZone(Zone.GRAVEYARD, null, this, false)) {
                        somethingHappened = true;
                        continue;
                    }
                }
                planeswalkers.add(perm);
            }
            if (filterAura.match(perm)) {
                //20091005 - 704.5n, 702.14c
                if (perm.getAttachedTo() == null) {
                    if (perm.moveToZone(Zone.GRAVEYARD, null, this, false))
                        somethingHappened = true;
                }
                else {
                    Target target = perm.getSpellAbility().getTargets().get(0);
                    if (target instanceof TargetPermanent) {
                        Permanent attachedTo = getPermanent(perm.getAttachedTo());
                        if (attachedTo == null) {
                            if (perm.moveToZone(Zone.GRAVEYARD, null, this, false))
                                somethingHappened = true;
                        }
                        else {
                            Filter auraFilter = perm.getSpellAbility().getTargets().get(0).getFilter();
                            if (!auraFilter.match(attachedTo) || attachedTo.hasProtectionFrom(perm)) {
                                if (perm.moveToZone(Zone.GRAVEYARD, null, this, false))
                                    somethingHappened = true;
                            }
                        }
                    }
                    else if (target instanceof TargetPlayer) {
                        Player attachedTo = getPlayer(perm.getAttachedTo());
                        if (attachedTo == null) {
                            if (perm.moveToZone(Zone.GRAVEYARD, null, this, false))
                                somethingHappened = true;
                        }
                        else {
                            Filter auraFilter = perm.getSpellAbility().getTargets().get(0).getFilter();
                            if (!auraFilter.match(attachedTo) || attachedTo.hasProtectionFrom(perm)) {
                                if (perm.moveToZone(Zone.GRAVEYARD, null, this, false))
                                    somethingHappened = true;
                            }
                        }
                    }
                }
            }
            if (filterLegendary.match(perm))
                legendary.add(perm);
            if (filterEquipment.match(perm)) {
                //20091005 - 704.5p, 702.14d
                if (perm.getAttachedTo() != null) {
                    Permanent creature = getPermanent(perm.getAttachedTo());
                    if (creature == null) {
                        perm.attachTo(null, this);
                    }
                    else if (!creature.getCardType().contains(CardType.CREATURE) || creature.hasProtectionFrom(perm)) {
                        if (creature.removeAttachment(perm.getId(), this))
                            somethingHappened = true;
                    }
                }
            }
            if (filterFortification.match(perm)) {
                if (perm.getAttachedTo() != null) {
                    Permanent land = getPermanent(perm.getAttachedTo());
                    if (land == null) {
                        perm.attachTo(null, this);
                    }
                    else if (!land.getCardType().contains(CardType.LAND) || land.hasProtectionFrom(perm)) {
                        if (land.removeAttachment(perm.getId(), this))
                            somethingHappened = true;
                    }
                }
            }
            //20091005 - 704.5q
   			if (perm.getAttachments().size() > 0) {
				for (UUID attachmentId: perm.getAttachments()) {
					Permanent attachment = getPermanent(attachmentId);
					if (attachment != null && !(attachment.getSubtype().contains("Aura") ||
							attachment.getSubtype().contains("Equipment") ||
							attachment.getSubtype().contains("Fortification"))) {
						if (perm.removeAttachment(attachment.getId(), this)) {
							somethingHappened = true;
                            break;
                        }
					}
				}
			}

            //20110501 - 704.5r
            if (perm.getCounters().containsKey(CounterType.P1P1) && perm.getCounters().containsKey(CounterType.M1M1)) {
				int p1p1 = perm.getCounters().getCount(CounterType.P1P1);
				int m1m1 = perm.getCounters().getCount(CounterType.M1M1);
				int min = Math.min(p1p1, m1m1);
				perm.getCounters().removeCounter(CounterType.P1P1, min);
				perm.getCounters().removeCounter(CounterType.M1M1, min);
			}

        }
		//20091005 - 704.5j, 801.14
		if (planeswalkers.size() > 1) {  //don't bother checking if less than 2 planeswalkers in play
			for (Permanent planeswalker: planeswalkers) {
				for (String planeswalkertype: planeswalker.getSubtype()) {
					FilterPlaneswalkerPermanent filterPlaneswalker = new FilterPlaneswalkerPermanent();
					filterPlaneswalker.getSubtype().add(planeswalkertype);
					filterPlaneswalker.setScopeSubtype(ComparisonScope.Any);
					if (getBattlefield().contains(filterPlaneswalker, planeswalker.getControllerId(), this, 2)) {
						for (Permanent perm: getBattlefield().getActivePermanents(filterPlaneswalker, planeswalker.getControllerId(), this)) {
							perm.moveToZone(Zone.GRAVEYARD, null, this, false);
						}
						return true;
					}
				}
			}
		}
		//20091005 - 704.5k, 801.12
		if (legendary.size() > 1) {  //don't bother checking if less than 2 legends in play
			for (Permanent legend: legendary) {
				FilterLegendaryPermanent filterLegendName = new FilterLegendaryPermanent();
				filterLegendName.getName().add(legend.getName());
				if (getBattlefield().contains(filterLegendName, legend.getControllerId(), this, 2)) {
					for (Permanent dupLegend: getBattlefield().getActivePermanents(filterLegendName, legend.getControllerId(), this)) {
						dupLegend.moveToZone(Zone.GRAVEYARD, null, this, false);
					}
					return true;
				}
			}
		}

		//TODO: implement the rest

		return somethingHappened;
	}

	@Override
	public void addPlayerQueryEventListener(Listener<PlayerQueryEvent> listener) {
		playerQueryEventSource.addListener(listener);
	}

	@Override
	public synchronized void firePriorityEvent(UUID playerId) {
        if (simulation) return;
		String message = this.state.getTurn().getStepType().toString();
		if (this.canPlaySorcery(playerId))
			message += " - play spells and abilities.";
		else
			message +=  " - play instants and activated abilities.";

		playerQueryEventSource.select(playerId, message);
	}

	@Override
	public synchronized void fireSelectEvent(UUID playerId, String message) {
        if (simulation) return;
		playerQueryEventSource.select(playerId, message);
	}

	@Override
	public void firePlayManaEvent(UUID playerId, String message) {
        if (simulation) return;
		playerQueryEventSource.playMana(playerId, message);
	}

	@Override
	public void firePlayXManaEvent(UUID playerId, String message) {
        if (simulation) return;
		playerQueryEventSource.playXMana(playerId, message);
	}

	@Override
	public void fireAskPlayerEvent(UUID playerId, String message) {
        if (simulation) return;
		playerQueryEventSource.ask(playerId, message);
	}

	@Override
	public void fireGetChoiceEvent(UUID playerId, String message, Collection<? extends ActivatedAbility> choices) {
        if (simulation) return;
		playerQueryEventSource.chooseAbility(playerId, message, choices);
	}

	@Override
	public void fireGetModeEvent(UUID playerId, String message, Map<UUID, String> modes) {
        if (simulation) return;
		playerQueryEventSource.chooseMode(playerId, message, modes);
	}

	@Override
	public void fireSelectTargetEvent(UUID playerId, String message, Set<UUID> targets, boolean required, Map<String, Serializable> options) {
        if (simulation) return;
		playerQueryEventSource.target(playerId, message, targets, required, options);
	}

	@Override
	public void fireSelectTargetEvent(UUID playerId, String message, Cards cards, boolean required, Map<String, Serializable> options) {
        if (simulation) return;
		playerQueryEventSource.target(playerId, message, cards, required, options);
	}

	@Override
	public void fireSelectTargetEvent(UUID playerId, String message, List<TriggeredAbility> abilities) {
        if (simulation) return;
		playerQueryEventSource.target(playerId, message, abilities);
	}

	@Override
	public void fireSelectTargetEvent(UUID playerId, String message, List<Permanent> perms, boolean required) {
        if (simulation) return;
		playerQueryEventSource.target(playerId, message, perms, required);
	}

	@Override
	public void fireLookAtCardsEvent(UUID playerId, String message, Cards cards) {
        if (simulation) return;
		playerQueryEventSource.target(playerId, message, cards);
	}

	@Override
	public void fireGetAmountEvent(UUID playerId, String message, int min, int max) {
        if (simulation) return;
		playerQueryEventSource.amount(playerId, message, min, max);
	}

	@Override
	public void fireChooseEvent(UUID playerId, Choice choice) {
        if (simulation) return;
		playerQueryEventSource.choose(playerId, choice.getMessage(), choice.getChoices());
	}
    
    @Override
    public void fireChoosePileEvent(UUID playerId, String message, List<? extends Card> pile1, List<? extends Card> pile2) {
        if (simulation) return;
        playerQueryEventSource.choosePile(playerId, message, pile1, pile2);
    }
    
	@Override
	public void informPlayers(String message) {
        if (simulation) return;
//		state.addMessage(message);
		fireInformEvent(message);
	}

    @Override
	public void debugMessage(String message) {
        logger.warn(message);
	}

	@Override
	public void fireInformEvent(String message) {
        if (simulation) return;
		tableEventSource.fireTableEvent(EventType.INFO, message, this);
	}

	@Override
	public void fireUpdatePlayersEvent() {
        if (simulation) return;
		tableEventSource.fireTableEvent(EventType.UPDATE, null, this);
	}
	
	@Override
	public void fireErrorEvent(String message, Exception ex) {
		tableEventSource.fireTableEvent(EventType.ERROR, message, ex, this);
	}

	@Override
	public Players getPlayers() {
		return state.getPlayers();
	}

	@Override
	public PlayerList getPlayerList() {
		return state.getPlayerList();
	}

	@Override
	public Turn getTurn() {
		return state.getTurn();
	}

	@Override
	public Phase getPhase() {
		return state.getTurn().getPhase();
	}

	@Override
	public Step getStep() {
		return state.getTurn().getStep();
	}

	@Override
	public Battlefield getBattlefield() {
		return state.getBattlefield();
	}

	@Override
	public SpellStack getStack() {
		return state.getStack();
	}

	@Override
	public Exile getExile() {
		return state.getExile();
	}

	@Override
	public Combat getCombat() {
		return state.getCombat();
	}

	@Override
	public int getTurnNum() {
		return state.getTurnNum();
	}

	@Override
	public boolean isMainPhase() {
		return state.getTurn().getStepType() == PhaseStep.PRECOMBAT_MAIN || state.getTurn().getStepType() == PhaseStep.POSTCOMBAT_MAIN;
	}

	@Override
	public boolean canPlaySorcery(UUID playerId) {
		return getActivePlayerId().equals(playerId) && getStack().isEmpty() && isMainPhase();
	}

	@Override
	public void leave(UUID playerId) {
		Player player = getPlayer(playerId);
		player.leave();
		//20100423 - 800.4a
		for (Iterator<Permanent> it = getBattlefield().getAllPermanents().iterator(); it.hasNext();) {
			Permanent perm = it.next();
			if (perm.getOwnerId().equals(playerId)) {
				if (perm.getAttachedTo() != null) {
					Permanent attachedTo = getPermanent(perm.getAttachedTo());
					if (attachedTo != null)
						attachedTo.removeAttachment(perm.getId(), this);
				}
				it.remove();
			}
		}
		for (Iterator<StackObject> it = getStack().iterator(); it.hasNext();) {
			StackObject object = it.next();
			if (object.getControllerId().equals(playerId)) {
				it.remove();
			}
		}
		for (Iterator<Permanent> it = getBattlefield().getAllPermanents().iterator(); it.hasNext();) {
			Permanent perm = it.next();
			if (perm.getControllerId().equals(playerId)) {
				perm.moveToExile(null, "", null, this);
			}
		}
	}

	@Override
	public UUID getActivePlayerId() {
		return state.getActivePlayerId();
	}

	@Override
	public UUID getPriorityPlayerId() {
		return state.getPriorityPlayerId();
	}

	@Override
	public void fireEvent(GameEvent event) {
		state.handleEvent(event, this);
	}

	@Override
	public boolean replaceEvent(GameEvent event) {
		return state.replaceEvent(event, this);
	}

	protected void removeCreaturesFromCombat() {
		//20091005 - 511.3
		getCombat().endCombat(this);
	}

	@Override
	public ContinuousEffects getContinuousEffects() {
		return state.getContinuousEffects();
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		//initialize transient objects during deserialization
		in.defaultReadObject();
		savedStates = new Stack<Integer>();
		tableEventSource = new TableEventSource();
		playerQueryEventSource = new PlayerQueryEventSource();
		gameStates = new GameStates();
	}

	/**
	 * Gets last known information about object in the zone.
	 * At the moment doesn't take into account zone (it is expected that it doesn't really matter, if not, then Map<UUID, Map<Zone, Card>> should be used instead).
	 *
	 * Can return null.
	 *
	 * @param objectId
	 * @param zone
	 * @return
	 */
	@Override
	public MageObject getLastKnownInformation(UUID objectId, Zone zone) {
		/*if (!lki.containsKey(objectId)) {
			return getCard(objectId);
		}*/
        MageObject object = lki.get(objectId);
        if (object != null) {
            return object.copy();
        }
        return null;
	}

	/**
	 * Remembers object state to be used as Last Known Information.
	 *
	 * @param objectId
	 * @param zone
	 * @param object
	 */
	@Override
	public void rememberLKI(UUID objectId, Zone zone, MageObject object) {
        if (object instanceof Permanent || object instanceof StackObject) {
            MageObject copy = object.copy();
            lki.put(objectId, copy);
        }
	}

	/**
	 * Reset objects stored for Last Known Information.
	 */
	@Override
	public void resetLKI() {
		lki.clear();
	}

	@Override
	public void cheat(UUID ownerId, Map<Zone, String> commands) {
		if (commands != null) {
			Player player = getPlayer(ownerId);
			if (player != null) {
				for (Map.Entry<Zone, String> command : commands.entrySet()) {
					switch (command.getKey()) {
						case HAND:
							if (command.getValue().equals("clear")) {
								removeCards(player.getHand());
							}
							break;
						case LIBRARY:
							if (command.getValue().equals("clear")) {
								for (UUID card : player.getLibrary().getCardList()) {
									gameCards.remove(card);
								}
								player.getLibrary().clear();
							}
							break;
						case OUTSIDE:
							if (command.getValue().contains("life:")) {
								String[] s = command.getValue().split(":");
								if (s.length == 2) {
									try {
										Integer amount = Integer.parseInt(s[1]);
										player.setLife(amount, this);
										logger.info("Setting player's life: ");
									} catch (NumberFormatException e) {
										logger.fatal("error setting life", e);
									}
								}


							}
							break;
					}
				}
			}
		}
	}

	private void removeCards(Cards cards) {
		for (UUID card : cards) {
			gameCards.remove(card);
		}
		cards.clear();
	}

	@Override
	public void cheat(UUID ownerId, List<Card> library, List<Card> hand, List<PermanentCard> battlefield, List<Card> graveyard) {
		Player player = getPlayer(ownerId);
		if (player != null) {
			loadCards(ownerId, library);
			loadCards(ownerId, hand);
			loadCards(ownerId, battlefield);
			loadCards(ownerId, graveyard);

			for (Card card : library) {
				setZone(card.getId(), Zone.LIBRARY);
				player.getLibrary().putOnTop(card, this);
			}
            for (Card card : hand) {
				setZone(card.getId(), Zone.HAND);
				player.getHand().add(card);
			}
			for (Card card : graveyard) {
				setZone(card.getId(), Zone.GRAVEYARD);
				player.getGraveyard().add(card);
			}
			for (PermanentCard card : battlefield) {
                setZone(card.getId(), Zone.BATTLEFIELD);
                card.setOwnerId(ownerId);
                PermanentCard permanent = new PermanentCard(card.getCard(), ownerId);
                getBattlefield().addPermanent(permanent);
                permanent.entersBattlefield(permanent.getId(), this);                    
                ((PermanentImpl)permanent).removeSummoningSickness();
                if (card.isTapped())
                    permanent.setTapped(true);
			}
			applyEffects();
		}
	}

	private void loadCards(UUID ownerId, List<? extends Card> cards) {
		if (cards == null) {
			return;
		}
		Set<Card> set = new HashSet<Card>(cards);
		loadCards(set, ownerId);
	}

	public void replaceLibrary(List<Card> cardsDownToTop, UUID ownerId) {
		Player player = getPlayer(ownerId);
		if (player != null) {
			for (UUID card : player.getLibrary().getCardList()) {
				gameCards.remove(card);
			}
			player.getLibrary().clear();
			Set<Card> cards = new HashSet<Card>();
			for (Card card : cardsDownToTop) {
				cards.add(card);
			}
			loadCards(cards, ownerId);

			for (Card card : cards) {
				player.getLibrary().putOnTop(card, this);
			}
		}
	}

	@Override
	public boolean endTurn(UUID playerId) {
		if (!getActivePlayerId().equals(playerId)) {
			return false;
		}
		getTurn().endTurn(this, getActivePlayerId());
		return true;
	}

    @Override
    public int doAction(MageAction action) {
        //actions.add(action);
		int value = action.doAction(this);
		score += action.getScore(scorePlayer);
        return value;
    }

    @Override
    public Date getStartTime() {
        return startTime;
    }
    
    @Override
    public Date getEndTime() {
        return endTime;
    }
    
    @Override
	public void setGameOptions(GameOptions options) {
		this.gameOptions = options;
	}

    @Override
    public void setLosingPlayer(Player player) {
        this.losingPlayer = player;
    }

    @Override
    public Player getLosingPlayer() {
        return this.losingPlayer;
    }

    @Override
    public void informPlayer(Player player, String message) {
        //TODO: implement personal messages
    }

    @Override
    public boolean getStateCheckRequired() {
        return stateCheckRequired;
    }

    @Override
    public void setStateCheckRequired() {
        stateCheckRequired = true;
    }
}
