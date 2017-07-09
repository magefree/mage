/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.game.match;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import mage.cards.decks.Deck;
import mage.game.Game;
import mage.game.GameException;
import mage.game.GameInfo;
import mage.game.events.Listener;
import mage.game.events.TableEvent;
import mage.game.result.ResultProtos.MatchProto;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public interface Match {

    int SIDEBOARD_TIME = 180;

    UUID getId();

    String getName();

    boolean hasEnded();

    boolean hasStarted();

    boolean checkIfMatchEnds();

    List<MatchPlayer> getPlayers();

    MatchPlayer getPlayer(UUID playerId);

    void addPlayer(Player player, Deck deck);

    boolean quitMatch(UUID playerId);

    void submitDeck(UUID playerId, Deck deck);

    boolean updateDeck(UUID playerId, Deck deck);

    void startMatch();

    void startGame() throws GameException;

    void sideboard();

    void endGame();

    Game getGame();

    List<Game> getGames();

    int getWinsNeeded();

    int getFreeMulligans();

    void addDraw();

    int getDraws();

    int getNumGames();

    void addGame();

    boolean isDoneSideboarding();

    UUID getChooser();

    MatchOptions getOptions();

    void addTableEventListener(Listener<TableEvent> listener);

    void fireSideboardEvent(UUID playerId, Deck deck);

    // match times
    Date getStartTime();

    Date getEndTime();

    /**
     * Can the games of the match be replayed
     *
     * @return
     */
    boolean isReplayAvailable();

    void setReplayAvailable(boolean replayAvailable);

    /**
     * Free resources no longer needed if match ended and only exists for
     * information purpose.
     *
     * @param isSaveGameActivated
     * @param isTournament
     */
    void cleanUpOnMatchEnd(boolean isSaveGameActivated, boolean isTournament);

    /**
     * Free all possible resources
     */
    void cleanUp();

    GameInfo createGameInfo(Game game);

    List<GameInfo> getGamesInfo();

    void setTableId(UUID tableId);

    void setTournamentRound(int round);

    MatchProto toProto();
}
