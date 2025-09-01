package mage.collectors.services;

import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;
import mage.util.ConsoleUtil;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;

/**
 * Data collector to print game logs in console output. Used for better unit tests debugging.
 *
 * @author JayDi85
 */
public class PrintGameLogsDataCollector extends EmptyDataCollector {

    private static final Logger logger = Logger.getLogger(PrintGameLogsDataCollector.class);
    public static final String SERVICE_CODE = "printGameLogs";

    private void writeLog(String message) {
        logger.info(message);
    }

    private void writeLog(String category, String event, String details) {
        writeLog(String.format("[%s][%s] %s",
                category,
                event,
                details
        ));
    }

    @Override
    public String getServiceCode() {
        return SERVICE_CODE;
    }

    @Override
    public String getInitInfo() {
        return "print game logs in server logs";
    }

    @Override
    public void onGameLog(Game game, String message) {
        String needMessage = Jsoup.parse(message).text();
        writeLog("LOG", "GAME", String.format("%s: %s",
                CardUtil.getTurnInfo(game),
                needMessage
        ));
    }

    @Override
    public void onTestsChoiceUse(Game game, Player player, String choice, String reason) {
        String needReason = Jsoup.parse(reason).text();
        writeLog("LOG", "GAME", ConsoleUtil.asYellow(String.format("%s: %s using choice: %s%s",
                CardUtil.getTurnInfo(game),
                player.getName(),
                choice,
                reason.isEmpty() ? "" : " (" + needReason + ")"
        )));
    }

    @Override
    public void onTestsTargetUse(Game game, Player player, String target, String reason) {
        String needReason = Jsoup.parse(reason).text();
        writeLog("LOG", "GAME", ConsoleUtil.asYellow(String.format("%s: %s using target: %s%s",
                CardUtil.getTurnInfo(game),
                player.getName(),
                target,
                reason.isEmpty() ? "" : " (" + needReason + ")"
        )));
    }

    @Override
    public void onTestsStackPush(Game game) {
        writeLog("LOG", "GAME", String.format("%s: Stack push: %s",
                CardUtil.getTurnInfo(game),
                game.getStack().toString()
        ));
    }

    @Override
    public void onTestsStackResolve(Game game) {
        writeLog("LOG", "GAME", String.format("%s: Stack resolve: %s",
                CardUtil.getTurnInfo(game),
                game.getStack().toString()
        ));
    }
}
