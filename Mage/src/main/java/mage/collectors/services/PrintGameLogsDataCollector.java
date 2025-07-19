package mage.collectors.services;

import mage.game.Game;
import mage.util.CardUtil;
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
        writeLog("GAME", "LOG", String.format("%s: %s",
                CardUtil.getTurnInfo(game),
                needMessage
        ));
    }
}
