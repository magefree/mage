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
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLists;
import mage.game.GameException;
import mage.game.draft.Draft;
import mage.game.match.Match;
import mage.game.match.MatchOptions;
import mage.game.tournament.Tournament;
import mage.game.tournament.TournamentOptions;
import mage.MageException;
import mage.players.Player;
import mage.server.game.GamesRoomManager;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TableManager {

	private final static TableManager INSTANCE = new TableManager();
	//private final static Logger logger = Logger.getLogger(TableManager.class);

	private ConcurrentHashMap<UUID, TableController> controllers = new ConcurrentHashMap<UUID, TableController>();
	private ConcurrentHashMap<UUID, Table> tables = new ConcurrentHashMap<UUID, Table>();

	public static TableManager getInstance() {
		return INSTANCE;
	}

	public Table createTable(UUID roomId, UUID userId, MatchOptions options) {
		TableController tableController = new TableController(roomId, userId, options);
		controllers.put(tableController.getTable().getId(), tableController);
		tables.put(tableController.getTable().getId(), tableController.getTable());
		return tableController.getTable();
	}

	public Table createTable(UUID roomId, MatchOptions options) {
		TableController tableController = new TableController(roomId, null, options);
		controllers.put(tableController.getTable().getId(), tableController);
		tables.put(tableController.getTable().getId(), tableController.getTable());
		return tableController.getTable();
	}

	public Table createTournamentTable(UUID roomId, UUID userId, TournamentOptions options) {
		TableController tableController = new TableController(roomId, userId, options);
		controllers.put(tableController.getTable().getId(), tableController);
		tables.put(tableController.getTable().getId(), tableController.getTable());
		return tableController.getTable();
	}

	public Table getTable(UUID tableId) {
		return tables.get(tableId);
	}

	public Match getMatch(UUID tableId) {
		if (controllers.containsKey(tableId))
			return controllers.get(tableId).getMatch();
		return null;
	}

	public Collection<Table> getTables() {
		return tables.values();
	}

    public TableController getController(UUID tableId) {
        return controllers.get(tableId);
    }
    
	public boolean joinTable(UUID userId, UUID tableId, String name, String playerType, int skill, DeckCardLists deckList) throws MageException {
		if (controllers.containsKey(tableId))
			return controllers.get(tableId).joinTable(userId, name, playerType, skill, deckList);
		return false;
	}

	public boolean joinTournament(UUID userId, UUID tableId, String name, String playerType, int skill) throws GameException {
		if (controllers.containsKey(tableId))
			return controllers.get(tableId).joinTournament(userId, name, playerType, skill);
		return false;
	}

	public boolean submitDeck(UUID userId, UUID tableId, DeckCardLists deckList) throws MageException {
		if (controllers.containsKey(tableId))
			return controllers.get(tableId).submitDeck(userId, deckList);
		return false;
	}

	public void updateDeck(UUID userId, UUID tableId, DeckCardLists deckList) throws MageException {
		if (controllers.containsKey(tableId))
			controllers.get(tableId).updateDeck(userId, deckList);
	}

    public void removeSession(UUID userId) {
		for (TableController controller: controllers.values()) {
			controller.kill(userId);
		}
	}

	public boolean isTableOwner(UUID tableId, UUID userId) {
		if (controllers.containsKey(tableId))
			return controllers.get(tableId).isOwner(userId);
		return false;
	}

	public boolean removeTable(UUID userId, UUID tableId) {
		if (isTableOwner(tableId, userId) || UserManager.getInstance().isAdmin(userId)) {
			removeTable(tableId);
			return true;
		}
		return false;
	}

	public void leaveTable(UUID userId, UUID tableId) {
		if (controllers.containsKey(tableId))
			controllers.get(tableId).leaveTable(userId);
	}

	public UUID getChatId(UUID tableId) {
		if (controllers.containsKey(tableId))
			return controllers.get(tableId).getChatId();
		return null;
	}

	public void startMatch(UUID userId, UUID roomId, UUID tableId) {
		if (controllers.containsKey(tableId))
			controllers.get(tableId).startMatch(userId);
	}

	public void startMatch(UUID roomId, UUID tableId) {
		if (controllers.containsKey(tableId))
			controllers.get(tableId).startMatch();
	}

	public void startChallenge(UUID userId, UUID roomId, UUID tableId, UUID challengeId) {
		if (controllers.containsKey(tableId))
			controllers.get(tableId).startChallenge(userId, challengeId);
	}

	public void startTournament(UUID userId, UUID roomId, UUID tableId) {
		if (controllers.containsKey(tableId))
			controllers.get(tableId).startTournament(userId);
	}

	public void startDraft(UUID tableId, Draft draft) {
		if (controllers.containsKey(tableId))
			controllers.get(tableId).startDraft(draft);
	}

	public boolean watchTable(UUID userId, UUID tableId) {
		if (controllers.containsKey(tableId))
			return controllers.get(tableId).watchTable(userId);
		return false;
	}

	public boolean replayTable(UUID userId, UUID tableId) {
		if (controllers.containsKey(tableId))
			return controllers.get(tableId).replayTable(userId);
		return false;
	}

	public void endGame(UUID tableId) {
		if (controllers.containsKey(tableId))
			controllers.get(tableId).endGame();
	}

	public void endDraft(UUID tableId, Draft draft) {
		if (controllers.containsKey(tableId))
			controllers.get(tableId).endDraft(draft);
	}

	public void endTournament(UUID tableId, Tournament tournament) {
		if (controllers.containsKey(tableId))
			controllers.get(tableId).endTournament(tournament);
	}

	public void swapSeats(UUID tableId, UUID userId, int seatNum1, int seatNum2) {
		if (controllers.containsKey(tableId) && isTableOwner(tableId, userId)) {
			controllers.get(tableId).swapSeats(seatNum1, seatNum2);
		}
	}

	public void construct(UUID tableId) {
		if (controllers.containsKey(tableId))
			controllers.get(tableId).construct();
	}

	public void addPlayer(UUID userId, UUID tableId, Player player, String playerType, Deck deck) throws GameException {
		if (controllers.containsKey(tableId))
			controllers.get(tableId).addPlayer(userId, player, playerType, deck);
	}

	public void removeTable(UUID tableId) {
		if (tables.containsKey(tableId)) {
			Table table = tables.get(tableId);
			controllers.remove(tableId);
			tables.remove(tableId);
			GamesRoomManager.getInstance().removeTable(tableId);
			if (table.getMatch() != null && table.getMatch().getGame() != null)
				table.getMatch().getGame().end();
		}
	}

}
