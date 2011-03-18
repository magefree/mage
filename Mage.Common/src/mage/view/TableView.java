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

package mage.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants.TableState;
import mage.game.Game;
import mage.game.Seat;
import mage.game.Table;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TableView implements Serializable {
    private static final long serialVersionUID = 1L;
	
	private UUID tableId;
	private String gameType;
	private String deckType;
	private TableState tableState;
	private boolean isTournament;
	private List<SeatView> seats = new ArrayList<SeatView>();
	private List<UUID> games = new ArrayList<UUID>();

	public TableView(Table table) {
		this.tableId = table.getId();
		this.gameType = table.getGameType();
		this.deckType = table.getDeckType();
		this.tableState = table.getState();
		this.isTournament = table.isTournament();
		for (Seat seat: table.getSeats()) {
			seats.add(new SeatView(seat));
		}
		if (!table.isTournament()) {
			for (Game game: table.getMatch().getGames()) {
				games.add(game.getId());
			}
		}
	}

	public UUID getTableId() {
		return tableId;
	}

	public String getGameType() {
		return gameType;
	}

	public String getDeckType() {
		return deckType;
	}

	public TableState getTableState() {
		return tableState;
	}

	public List<SeatView> getSeats() {
		return seats;
	}

	public List<UUID> getGames() {
		return games;
	}

	public boolean isTournament() {
		return this.isTournament;
	}

}
