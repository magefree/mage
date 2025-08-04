package mage.collectors;

import mage.collectors.services.PrintGameLogsDataCollector;
import mage.collectors.services.SaveGameHistoryDataCollector;
import mage.game.Game;
import mage.game.Table;
import mage.players.Player;
import org.apache.log4j.Logger;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Not a real data collector. It's a global service to inject and collect data all around the code.
 *
 * @author JayDi85
 */
final public class DataCollectorServices implements DataCollector {

    // usage example: -Dxmage.dataCollectors.saveGameHistory=true
    private static final String COMMAND_LINE_DATA_COLLECTORS_PREFIX = "xmage.dataCollectors.";

    private static final Logger logger = Logger.getLogger(DataCollectorServices.class);

    private static DataCollectorServices instance = null;

    // fill on server startup, so it's thread safe
    Set<DataCollector> allServices = new LinkedHashSet<>();
    Set<DataCollector> activeServices = new LinkedHashSet<>();

    public static DataCollectorServices getInstance() {
        if (instance == null) {
            instance = new DataCollectorServices();
        }
        return instance;
    }

    /**
     * Init data service on server's startup
     *
     * @param enablePrintGameLogs   use for unit tests to enable additional logs for better debugging
     * @param enableSaveGameHistory use to save full game history with logs, decks, etc
     */
    public static void init(boolean enablePrintGameLogs, boolean enableSaveGameHistory) {
        if (instance != null) {
            // unit tests: init on first test run, all other will use same process
            // real server: init on server startup
            return;
        }

        // fill all possible services
        getInstance().allServices.add(new PrintGameLogsDataCollector());
        getInstance().allServices.add(new SaveGameHistoryDataCollector());
        logger.info(String.format("Data collectors: found %d services", getInstance().allServices.size()));

        // enable only needed
        getInstance().allServices.forEach(service -> {
            boolean isDefault = false;
            isDefault |= enablePrintGameLogs && service.getServiceCode().equals(PrintGameLogsDataCollector.SERVICE_CODE);
            isDefault |= enableSaveGameHistory && service.getServiceCode().equals(SaveGameHistoryDataCollector.SERVICE_CODE);
            boolean isEnable = isServiceEnable(service.getServiceCode(), isDefault);
            if (isEnable) {
                getInstance().activeServices.add(service);
            }
            String info = isEnable ? String.format(" (%s)", service.getInitInfo()) : "";
            logger.info(String.format("Data collectors: %s - %s%s", service.getServiceCode(), isEnable ? "enabled" : "disabled", info));
        });
    }

    private static boolean isServiceEnable(String dataCollectorCode, boolean isEnableByDefault) {
        String needCommand = COMMAND_LINE_DATA_COLLECTORS_PREFIX + dataCollectorCode;
        boolean isEnable;
        if (System.getProperty(needCommand) != null) {
            isEnable = System.getProperty(needCommand, "false").equals("true");
        } else {
            isEnable = isEnableByDefault;
        }
        return isEnable;
    }

    @Override
    public String getServiceCode() {
        throw new IllegalStateException("Wrong code usage. Use it by static methods only");
    }

    @Override
    public String getInitInfo() {
        throw new IllegalStateException("Wrong code usage. Use it by static methods only");
    }

    @Override
    public void onServerStart() {
        activeServices.forEach(DataCollector::onServerStart);
    }

    @Override
    public void onTableStart(Table table) {
        activeServices.forEach(c -> c.onTableStart(table));
    }

    @Override
    public void onTableEnd(Table table) {
        activeServices.forEach(c -> c.onTableEnd(table));
    }

    @Override
    public void onGameStart(Game game) {
        if (game.isSimulation()) return;
        activeServices.forEach(c -> c.onGameStart(game));
    }

    @Override
    public void onGameLog(Game game, String message) {
        if (game.isSimulation()) return;
        activeServices.forEach(c -> c.onGameLog(game, message));
    }

    @Override
    public void onGameEnd(Game game) {
        if (game.isSimulation()) return;
        activeServices.forEach(c -> c.onGameEnd(game));
    }

    @Override
    public void onChatRoom(UUID roomId, String userName, String message) {
        activeServices.forEach(c -> c.onChatRoom(roomId, userName, message));
    }

    @Override
    public void onChatTourney(UUID tourneyId, String userName, String message) {
        activeServices.forEach(c -> c.onChatTourney(tourneyId, userName, message));
    }

    @Override
    public void onChatTable(UUID tableId, String userName, String message) {
        activeServices.forEach(c -> c.onChatTable(tableId, userName, message));
    }

    @Override
    public void onChatGame(UUID gameId, String userName, String message) {
        activeServices.forEach(c -> c.onChatGame(gameId, userName, message));
    }

    @Override
    public void onTestsChoiceUse(Game game, Player player, String usingChoice, String reason) {
        if (game.isSimulation()) return;
        activeServices.forEach(c -> c.onTestsChoiceUse(game, player, usingChoice, reason));
    }

    @Override
    public void onTestsTargetUse(Game game, Player player, String usingTarget, String reason) {
        if (game.isSimulation()) return;
        activeServices.forEach(c -> c.onTestsTargetUse(game, player, usingTarget, reason));
    }

    @Override
    public void onTestsStackPush(Game game) {
        if (game.isSimulation()) return;
        activeServices.forEach(c -> c.onTestsStackPush(game));
    }

    @Override
    public void onTestsStackResolve(Game game) {
        if (game.isSimulation()) return;
        activeServices.forEach(c -> c.onTestsStackResolve(game));
    }
}
