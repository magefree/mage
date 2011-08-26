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

import mage.server.Room;
import java.util.List;
import java.util.UUID;
import mage.cards.decks.DeckCardLists;
import mage.game.GameException;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.MageException;
import mage.view.MatchView;
import mage.view.TableView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public interface GamesRoom extends Room {

	public List<TableView> getTables();
    public List<MatchView> getFinished();
	public boolean joinTable(UUID userId, UUID tableId, String name, String playerType, int skill, DeckCardLists deckList) throws MageException;
	public boolean joinTournamentTable(UUID userId, UUID tableId, String name, String playerType, int skill) throws GameException;
	public TableView createTable(UUID userId, MatchOptions options);
	public TableView createTournamentTable(UUID userId, TournamentOptions options);
	public void removeTable(UUID userId, UUID tableId);
	public void removeTable(UUID tableId);
	public TableView getTable(UUID tableId);
	public void leaveTable(UUID userId, UUID tableId);
	public boolean watchTable(UUID userId, UUID tableId) throws MageException;

}
