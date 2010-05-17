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

import mage.game.Table;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import mage.Constants.MultiplayerAttackOption;
import mage.Constants.RangeOfInfluence;
import mage.cards.decks.DeckCardLists;
import mage.game.GameException;
import mage.util.Logging;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TableManager {

	private final static TableManager INSTANCE = new TableManager();
	private final static Logger logger = Logging.getLogger(TableManager.class.getName());

	private ConcurrentHashMap<UUID, TableController> controllers = new ConcurrentHashMap<UUID, TableController>();
	private ConcurrentHashMap<UUID, Table> tables = new ConcurrentHashMap<UUID, Table>();

	public static TableManager getInstance() {
		return INSTANCE;
	}

	public Table createTable(UUID sessionId, String gameType, String deckType, List<String> playerTypes, MultiplayerAttackOption attackOption, RangeOfInfluence range) {
		TableController tableController = new TableController(sessionId, gameType, deckType, playerTypes, attackOption, range);
		controllers.put(tableController.getTable().getId(), tableController);
		tables.put(tableController.getTable().getId(), tableController.getTable());
		return tableController.getTable();
	}

	public Table getTable(UUID tableId) {
		return tables.get(tableId);
	}

	public Collection<Table> getTables() {
		return tables.values();
	}

	public boolean joinTable(UUID sessionId, UUID tableId, int seatNum, String name, DeckCardLists deckList) throws GameException {
		return controllers.get(tableId).joinTable(sessionId, seatNum, name, deckList);
	}

	public void removeSession(UUID sessionId) {
		// TODO: search through tables and remove session
	}

	public boolean isTableOwner(UUID tableId, UUID sessionId) {
		return controllers.get(tableId).isOwner(sessionId);
	}

	public boolean removeTable(UUID sessionId, UUID tableId) {
		if (isTableOwner(tableId, sessionId)) {
			controllers.remove(tableId);
			tables.remove(tableId);
			return true;
		}
		return false;
	}

	public void leaveTable(UUID sessionId, UUID tableId) {
		controllers.get(tableId).leaveTable(sessionId);
	}

	public UUID getChatId(UUID tableId) {
		return controllers.get(tableId).getChatId();
	}

	public void startGame(UUID sessionId, UUID roomId, UUID tableId) {
		controllers.get(tableId).startGame(sessionId);
	}

	public boolean watchTable(UUID sessionId, UUID tableId) {
		return controllers.get(tableId).watchTable(sessionId);
	}

	public boolean replayTable(UUID sessionId, UUID tableId) {
		return controllers.get(tableId).replayTable(sessionId);
	}
	
	public void endGame(UUID tableId) {
		controllers.get(tableId).endGame();
	}

	public GameReplay createReplay(UUID tableId) {
		return controllers.get(tableId).createReplay();
	}

	public void swapSeats(UUID tableId, UUID sessionId, int seatNum1, int seatNum2) {
		if (isTableOwner(tableId, sessionId)) {
			controllers.get(tableId).swapSeats(seatNum1, seatNum2);
		}
	}
}
