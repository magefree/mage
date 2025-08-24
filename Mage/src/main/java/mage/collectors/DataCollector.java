package mage.collectors;

import mage.game.Game;
import mage.game.Table;

import java.util.UUID;

/**
 * Data collection for better debugging. Can collect server/table/game events and process related data.
 * <p>
 * Supported features:
 * - [x] collect and print game logs in server output, including unit tests
 * - [x] collect and save full games history and decks
 * - [ ] collect and print performance metrics like ApplyEffects calc time or inform players time (pings)
 * - [ ] collect and send metrics to third party tools like prometheus + grafana
 * - [ ] prepare "attachable" game data for bug reports
 * - [ ] record game replays data (GameView history)
 * <p>
 * How-to enable or disable:
 * - use java params like -Dxmage.dataCollectors.saveGameHistory=true
 * <p>
 * How-to add new service:
 * - create new class and extends EmptyDataCollector
 * - each service must use unique service code
 * - override only needed events
 * - modify DataCollectorServices.init with new class
 * - make sure it's fast and never raise errors
 *
 * @author JayDi85
 */
public interface DataCollector {

    /**
     * Return unique service code to enable by command line
     */
    String getServiceCode();

    /**
     * Show some hints on service enabled, e.g. root folder path
     */
    String getInitInfo();

    void onServerStart();

    void onTableStart(Table table);

    void onTableEnd(Table table);

    void onGameStart(Game game);

    void onGameLog(Game game, String message);

    void onGameEnd(Game game);

    /**
     * @param userName can be null for system messages
     */
    void onChatRoom(UUID roomId, String userName, String message);

    void onChatTourney(UUID tourneyId, String userName, String message);

    void onChatTable(UUID tableId, String userName, String message);

    /**
     * @param gameId chat sessings don't have full game access, so use onGameStart event to find game's ID before chat
     */
    void onChatGame(UUID gameId, String userName, String message);
}
