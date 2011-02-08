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

package mage.server;

import mage.game.Table;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLists;
import mage.game.GameException;
import mage.game.draft.Draft;
import mage.game.match.Match;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.players.Player;
import mage.server.game.GameReplay;
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

	public Table createTable(UUID sessionId, MatchOptions options) {
		TableController tableController = new TableController(sessionId, options);
		controllers.put(tableController.getTable().getId(), tableController);
		tables.put(tableController.getTable().getId(), tableController.getTable());
		return tableController.getTable();
	}

	public Table createTournamentTable(UUID sessionId, TournamentOptions options) {
		TableController tableController = new TableController(sessionId, options);
		controllers.put(tableController.getTable().getId(), tableController);
		tables.put(tableController.getTable().getId(), tableController.getTable());
		return tableController.getTable();
	}

	public Table getTable(UUID tableId) {
		return tables.get(tableId);
	}

	public Match getMatch(UUID tableId) {
		return controllers.get(tableId).getMatch();
	}

	public Collection<Table> getTables() {
		return tables.values();
	}

	public boolean joinTable(UUID sessionId, UUID tableId, String name, String playerType, DeckCardLists deckList) throws GameException {
		return controllers.get(tableId).joinTable(sessionId, name, playerType, deckList);
	}

	public boolean joinTournament(UUID sessionId, UUID tableId, String name, String playerType) throws GameException {
		return controllers.get(tableId).joinTournament(sessionId, name, playerType);
	}

	public boolean submitDeck(UUID sessionId, UUID tableId, DeckCardLists deckList) throws GameException {
		return controllers.get(tableId).submitDeck(sessionId, deckList);
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

	public void startMatch(UUID sessionId, UUID roomId, UUID tableId) {
		controllers.get(tableId).startMatch(sessionId);
	}

	public void startTournament(UUID sessionId, UUID roomId, UUID tableId) {
		controllers.get(tableId).startTournament(sessionId);
	}

	public void startDraft(UUID tableId, Draft draft) {
		controllers.get(tableId).startDraft(draft);
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

	public void endDraft(UUID tableId, Draft draft) {
		controllers.get(tableId).endDraft(draft);
	}

	public GameReplay createReplay(UUID tableId) {
		return controllers.get(tableId).createReplay();
	}

	public void swapSeats(UUID tableId, UUID sessionId, int seatNum1, int seatNum2) {
		if (isTableOwner(tableId, sessionId)) {
			controllers.get(tableId).swapSeats(seatNum1, seatNum2);
		}
	}

	public void construct(UUID tableId) {
		controllers.get(tableId).construct();
	}

	public void addPlayer(UUID sessionId, UUID tableId, Player player, String playerType, Deck deck) throws GameException {
		controllers.get(tableId).addPlayer(sessionId, player, playerType, deck);
	}
}
