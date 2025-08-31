package mage.collectors;

import mage.game.Game;
import mage.game.Table;
import mage.players.Player;

import java.util.UUID;

/**
 * Data collection for better debugging. Can collect server/table/game events and process related data.
 * <p>
 * Supported features:
 * - [x] collect and print game logs in server output, including unit tests
 * - [x] collect and save full games history and decks
 * - [ ] TODO: collect and print performance metrics like ApplyEffects calc time or inform players time (pings)
 * - [ ] TODO: collect and send metrics to third party tools like prometheus + grafana
 * - [x] tests: print used selections (choices, targets, modes, skips) TODO: add yes/no, replacement effect, coins, other choices
 * - [ ] TODO: tests: print additional info like current resolve ability?
 * - [ ] TODO: prepare "attachable" game data for bug reports
 * - [ ] TODO: record game replays data (GameView history)
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
     * @param gameId chat session don't have full game access, so use onGameStart event to find game's ID before chat
     */
    void onChatGame(UUID gameId, String userName, String message);

    /**
     * Tests only: on any non-target choice like yes/no, mode, etc
     */
    void onTestsChoiceUse(Game game, Player player, String usingChoice, String reason);

    /**
     * Tests only: on any target choice
     */
    void onTestsTargetUse(Game game, Player player, String usingTarget, String reason);

    /**
     * Tests only: on push object to stack (calls before activate and make any choice/announce)
     */
    void onTestsStackPush(Game game);

    /**
     * Tests only: on stack object resolve (calls before starting resolve)
     */
    void onTestsStackResolve(Game game);
}
