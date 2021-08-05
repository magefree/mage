package mage.util;

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

    // deck editor
    public static boolean GUI_DECK_EDITOR_DRAW_DRAGGING_PANE_BORDER = false;
    public static boolean GUI_DECK_EDITOR_DRAW_COUNT_LABEL_BORDER = false;

    // game
    public static boolean GUI_GAME_DRAW_BATTLEFIELD_BORDER = false;
    public static boolean GUI_GAME_DRAW_HAND_AND_STACK_BORDER = false;
    public static boolean GUI_GAME_DIALOGS_DRAW_CARDS_AREA_BORDER = false;
}
