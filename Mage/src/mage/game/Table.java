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
import java.util.List;
import java.util.UUID;
import mage.Constants.TableState;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidator;
import mage.game.events.Listener;
import mage.game.events.TableEvent;
import mage.game.events.TableEvent.EventType;
import mage.game.events.TableEventSource;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Table implements Serializable {

	private UUID tableId;
	private String name;
	private String gameType;
	private Seat[] seats;
	private int numSeats;
	private boolean isTournament;
	private DeckValidator validator;
	private TableState state = TableState.WAITING;

	protected TableEventSource tableEventSource = new TableEventSource();

	public Table(String gameType, String name, DeckValidator validator, List<String> playerTypes, boolean isTournament) {
		tableId = UUID.randomUUID();
		this.numSeats = playerTypes.size();
		this.gameType = gameType;
		this.name = name;
		this.isTournament = isTournament;
		createSeats(playerTypes);
		this.validator = validator;
	}

	private void createSeats(List<String> playerTypes) {
		int i = 0;
		seats = new Seat[numSeats];
		for(String playerType: playerTypes) {
			seats[i] = new Seat(playerType);
			i++;
		}
	}

	public UUID getId() {
		return tableId;
	}

	public void initGame() {
		state = TableState.DUELING;
	}

	public void initDraft() {
		state = TableState.DRAFTING;
	}

	public void endGame() {
		state = TableState.FINISHED;
	}

	public String getGameType() {
		return gameType;
	}

	public String getDeckType() {
		return validator.getName();
	}

	public boolean isTournament() {
		return this.isTournament;
	}

	public UUID joinTable(Player player, Seat seat) throws GameException {
		if (seat.getPlayer() != null) {
			throw new GameException("Seat is occupied.");
		}
		seat.setPlayer(player);
		if (isReady())
			state = TableState.STARTING;
		return seat.getPlayer().getId();
	}

	private boolean isReady() {
		for (int i = 0; i < numSeats; i++ ) {
			if (seats[i].getPlayer() == null)
				return false;
		}
		return true;
	}

	public Seat[] getSeats() {
		return seats;
	}

	public Seat getNextAvailableSeat(String playerType) {
		for (int i = 0; i < numSeats; i++ ) {
			if (seats[i].getPlayer() == null && seats[i].getPlayerType().equals(playerType))
				return seats[i];
		}
		return null;
	}

	public void leaveTable(UUID playerId) {
		for (int i = 0; i < numSeats; i++ ) {
			if (seats[i].getPlayer().getId().equals(playerId))
				seats[i].setPlayer(null);
		}
	}

	public TableState getState() {
		return state;
	}

	public DeckValidator getValidator() {
		return this.validator;
	}

	public void sideboard() {
		state = TableState.SIDEBOARDING;
	}

	public void construct() {
		state = TableState.CONSTRUCTING;
	}

	public String getName() {
		return this.name;
	}

	public void fireSideboardEvent(UUID playerId, Deck deck) {
		tableEventSource.fireTableEvent(EventType.SIDEBOARD, playerId, deck);
	}

	public void fireConstructEvent(UUID playerId, Deck deck) {
		tableEventSource.fireTableEvent(EventType.CONSTRUCT, playerId, deck);
	}

	public void fireSubmitDeckEvent(UUID playerId, Deck deck) {
		tableEventSource.fireTableEvent(EventType.SUBMIT_DECK, playerId, deck);
	}

	public void addTableEventListener(Listener<TableEvent> listener) {
		tableEventSource.addListener(listener);
	}

}
