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
import mage.cards.decks.DeckValidator;
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
	private DeckValidator validator;
	private TableState state = TableState.WAITING;

	public Table(String gameType, DeckValidator validator, List<String> playerTypes) {
		tableId = UUID.randomUUID();
		this.numSeats = playerTypes.size();
		this.gameType = gameType;
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

//	public void setGame(Game game) {
//		this.game = game;
//	}

	public void initGame(Game game) throws GameException {
		for (int i = 0; i < numSeats; i++ ) {
			game.addPlayer(seats[i].getPlayer());
		}
		state = TableState.DUELING;
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

	public UUID joinTable(Player player, int seatNum) throws GameException {
		if (seatNum >= numSeats || seatNum < 0) {
			throw new GameException("Invalid seat number");
		}
		if (seats[seatNum].getPlayer() != null) {
			throw new GameException("Seat is occupied.");
		}
		this.seats[seatNum].setPlayer(player);
		if (isReady())
			state = TableState.STARTING;
		return this.seats[seatNum].getPlayer().getId();
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

}
