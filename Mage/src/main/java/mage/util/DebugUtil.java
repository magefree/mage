package mage.util;

/**
 * Devs only: enable or disable debug features
 * <p>
 * WARNING, don't forget to disable it before release/commit
 *
 * @author JayDi85
 */
public class DebugUtil {

    public static boolean NETWORK_SHOW_CLIENT_CALLBACK_MESSAGES_LOG = false; // show all callback messages (server commands)

    // AI
    // game simulations runs in multiple threads, if you stop code to debug then it will be terminated by timeout
    // so AI debug mode will make single simulation thread without any timeouts
    public static boolean AI_ENABLE_DEBUG_MODE = false;
    public static boolean AI_SHOW_TARGET_OPTIMIZATION_LOGS = false; // works with target and target amount calculations

    // SERVER
    // data collectors - enable additional logs and data collection for better AI and human games debugging
    public static boolean TESTS_DATA_COLLECTORS_ENABLE_SAVE_GAME_HISTORY = false; // WARNING, for debug only, can generate too much files
    public static boolean SERVER_DATA_COLLECTORS_ENABLE_PRINT_GAME_LOGS = false;
    public static boolean SERVER_DATA_COLLECTORS_ENABLE_SAVE_GAME_HISTORY = false;

    // GAME
    // print detail target info for activate/cast/trigger only, not a single choose dialog
    // can be useful to debug unit tests, auto-choose or AI
    public static boolean GAME_SHOW_CHOOSE_TARGET_LOGS = false;

    // cards basic (card panels)
    public static boolean GUI_CARD_DRAW_OUTER_BORDER = false;
    public static boolean GUI_CARD_DRAW_INNER_BORDER = false;
    public static boolean GUI_CARD_ICONS_DRAW_PANEL_BORDER = false;
    public static boolean GUI_CARD_ICONS_DRAW_ICON_BORDER = false;
    public static boolean GUI_CARD_DRAW_MOUSE_CONTAINS_BOUNDS = false;

    // cards renders (inner card panel)
    public static boolean GUI_RENDER_IMAGE_DRAW_IMAGE_BORDER = false;
    public static boolean GUI_RENDER_CENTERED_TEXT_DRAW_DEBUG_LINES = false;

    // popup container (example: popup with card info)
    public static boolean GUI_POPUP_CONTAINER_DRAW_DEBUG_BORDER = false;

    // deck editor
    public static boolean GUI_DECK_EDITOR_DRAW_DRAGGING_PANE_BORDER = false;
    public static boolean GUI_DECK_EDITOR_DRAW_COUNT_LABEL_BORDER = false;

    // game
    public static boolean GUI_GAME_DRAW_BATTLEFIELD_BORDER = false;
    public static boolean GUI_GAME_DRAW_HAND_AND_STACK_BORDER = false;
    public static boolean GUI_GAME_DRAW_PLAYER_PANEL_BORDER = false;
    public static boolean GUI_GAME_DRAW_SKIP_BUTTONS_PANEL_BORDER = false;
    public static boolean GUI_GAME_DRAW_PHASE_BUTTONS_PANEL_BORDER = false;
    public static boolean GUI_GAME_DRAW_COMMANDS_PANEL_BORDER = false;

    // game dialogs
    public static boolean GUI_GAME_DIALOGS_DRAW_CARDS_AREA_BORDER = false;

    // database - show additional info about cache and memory settings
    public static boolean DATABASE_SHOW_CACHE_AND_MEMORY_STATS_ON_STARTUP = false;

    // database - collect sql queries and stats
    // how-to use:
    // - clean db folders or delete all *.trace.db files
    // - run tests or real server to collect some stats
    // - download h2 files for ver 1.4.197 from https://h2database.com/h2-2018-03-18.zip and open tools folder like xxx\H2\bin
    // - execute command: java -cp "h2-1.4.196.jar;%H2DRIVERS%;%CLASSPATH%" org.h2.tools.ConvertTraceFile -traceFile "xxx\Mage.Tests\db\cards.h2.trace.db" -script "xxx\Mage.Tests\db\cards.h2.trace.sql"
    // - open *.sql file for all sql-queries and exec stats
    public static boolean DATABASE_PROFILE_SQL_QUERIES_TO_FILE = false;

    // network
    public static boolean NETWORK_PROFILE_REQUESTS = false; // collect diff time between requests, http status and url into special log file
    public static String NETWORK_PROFILE_REQUESTS_DUMP_FILE_NAME = "httpRequests.log";

    /**
     * Return source line number for better debugging like "secondMethod - DebugUtilTest.java:21"
     *
     * @param skipMethodsAmount use 0 to return current method info, use 1 for prev method, use 2 for prev-prev method, etc
     * @param infoType          use "class" for full class name and "method" for short method name
     */
    public static String getMethodNameWithSource(int skipMethodsAmount, String infoType) {
        // 3 is default methods amount to skip:
        // - getMethodNameWithSource
        // - TraceHelper.getMethodNameWithSource
        // - Thread.currentThread().getStackTrace()
        switch (infoType) {
            case "class":
                return TraceHelper.getClassNameWithSource(3 + skipMethodsAmount);
            case "method":
                return TraceHelper.getMethodNameWithSource(3 + skipMethodsAmount);
            default:
                throw new IllegalArgumentException("Unknown info type: " + infoType);
        }
    }

}

/**
 * Debug: allows to find a caller's method name, compatible with java 8 and 9+
 * <a href="https://stackoverflow.com/a/10992439">Original code</a>
 */
class TraceHelper {

    /**
     * Example: showDialog - ChooseTargetTestableDialog.java:72
     */
    public static String getMethodNameWithSource(final int depth) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length == 0) {
            return "[no access to stack]";
        } else {
            return String.format("%s - %s:%d", stackTrace[depth].getMethodName(), stackTrace[depth].getFileName(), stackTrace[depth].getLineNumber());
        }
    }

    /**
     * Compatible with IntelliJ IDEA's logs navigation (will be clickable)
     * Example: mage.utils.testers.ChooseTargetTestableDialog(ChooseTargetTestableDialog.java:62)
     */
    public static String getClassNameWithSource(final int depth) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length == 0) {
            return "[no access to stack]";
        } else {
            return String.format("%s(%s:%d)", stackTrace[depth].getClassName(), stackTrace[depth].getFileName(), stackTrace[depth].getLineNumber());
        }
    }
}