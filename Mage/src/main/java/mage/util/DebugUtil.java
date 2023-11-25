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
    public static boolean GUI_GAME_DIALOGS_DRAW_CARDS_AREA_BORDER = false;

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