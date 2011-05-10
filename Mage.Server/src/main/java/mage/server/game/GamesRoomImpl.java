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

package mage.server.game;

import mage.server.TableManager;
import mage.server.RoomImpl;
import mage.game.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import mage.cards.decks.DeckCardLists;
import mage.game.GameException;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.view.TableView;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GamesRoomImpl extends RoomImpl implements GamesRoom, Serializable {

//	private final static Logger logger = Logger.getLogger(GamesRoomImpl.class);

	private ConcurrentHashMap<UUID, Table> tables = new ConcurrentHashMap<UUID, Table>();

	@Override
	public List<TableView> getTables() {
		ArrayList<TableView> tableList = new ArrayList<TableView>();
		for (Table table: tables.values()) {
			tableList.add(new TableView(table));
		}
		return tableList;
	}

	@Override
	public boolean joinTable(UUID sessionId, UUID tableId, String name, String playerType, int skill, DeckCardLists deckList) throws GameException {
		if (tables.containsKey(tableId)) {
			return TableManager.getInstance().joinTable(sessionId, tableId, name, playerType, skill, deckList);
		} else {
			return false;
		}
	}

	@Override
	public TableView createTable(UUID sessionId, MatchOptions options) {
		Table table = TableManager.getInstance().createTable(this.getRoomId(), sessionId, options);
		tables.put(table.getId(), table);
		return new TableView(table);
	}

	@Override
	public boolean joinTournamentTable(UUID sessionId, UUID tableId, String name, String playerType, int skill) throws GameException {
		if (tables.containsKey(tableId)) {
			return TableManager.getInstance().joinTournament(sessionId, tableId, name, playerType, skill);
		} else {
			return false;
		}
	}

	@Override
	public TableView createTournamentTable(UUID sessionId, TournamentOptions options) {
		Table table = TableManager.getInstance().createTournamentTable(this.getRoomId(), sessionId, options);
		tables.put(table.getId(), table);
		return new TableView(table);
	}

	@Override
	public TableView getTable(UUID tableId) {
		if (tables.containsKey(tableId))
			return new TableView(tables.get(tableId));
		return null;
	}

	@Override
	public void removeTable(UUID sessionId, UUID tableId) {
		if (TableManager.getInstance().removeTable(sessionId, tableId)) {
			tables.remove(tableId);
		}
	}

	@Override
	public void leaveTable(UUID sessionId, UUID tableId) {
		TableManager.getInstance().leaveTable(sessionId, tableId);
	}

	@Override
	public boolean watchTable(UUID sessionId, UUID tableId) {
		return TableManager.getInstance().watchTable(sessionId, tableId);
	}

}
