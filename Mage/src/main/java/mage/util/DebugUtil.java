package mage.util;

import java.lang.reflect.Method;

/**
 * Devs only: enable or disable debug features
 * <p>
 * WARNING, don't forget to disable it before release/commit
 *
 * @author JayDi85
 */
public class DebugUtil {

    public static boolean NETWORK_SHOW_CLIENT_CALLBACK_MESSAGES_LOG = false; // show all callback messages (server commands)

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

    public static String getMethodNameWithSource(final int depth) {
        return TraceHelper.getMethodNameWithSource(depth);
    }

}

/**
 * Debug: allows to find a caller's method name
 * <a href="https://stackoverflow.com/a/11726687/1276632">Original code</a>
 */
class TraceHelper {

    private static Method m;

    static {
        try {
            m = Throwable.class.getDeclaredMethod("getStackTraceElement", int.class);
            m.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getMethodName(final int depth) {
        try {
            StackTraceElement element = (StackTraceElement) m.invoke(new Throwable(), depth + 1);
            return element.getMethodName();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getMethodNameWithSource(final int depth) {
        try {
            StackTraceElement element = (StackTraceElement) m.invoke(new Throwable(), depth + 1);
            return String.format("%s - %s:%d", element.getMethodName(), element.getFileName(), element.getLineNumber());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}