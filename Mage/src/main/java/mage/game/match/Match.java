
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
