package mage.client.constants;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class Constants {

    private Constants() {
        throw new AssertionError();
    }

    public static final int FRAME_MAX_HEIGHT = 367;
    public static final int FRAME_MAX_WIDTH = 256;
    public static final int ART_MAX_HEIGHT = 168;
    public static final int ART_MAX_WIDTH = 227;
    public static final int SYMBOL_MAX_HEIGHT = 14;
    public static final int SYMBOL_MAX_WIDTH = 14;
    public static final int SYMBOL_MAX_XOFFSET = 27;
    public static final int SYMBOL_MAX_YOFFSET = 15;
    public static final int SYMBOL_MAX_SPACE = 14;
    public static final int CONTENT_MAX_XOFFSET = 15;
    public static final int ART_MAX_YOFFSET = 37;
    public static final int NAME_MAX_YOFFSET = 28;
    public static final int TYPE_MAX_YOFFSET = 223;
    public static final int ICON_MAX_HEIGHT = 16;
    public static final int ICON_MAX_WIDTH = 16;
    public static final int ICON_MAX_XOFFSET = 238;
    public static final int ICON_MAX_YOFFSET = 210;
    public static final int TEXT_MAX_YOFFSET = 232;
    public static final int TEXT_MAX_WIDTH = 227;
    public static final int TEXT_MAX_HEIGHT = 105;
    public static final int NAME_FONT_MAX_SIZE = 13;
    public static final int TEXT_FONT_MAX_SIZE = 11;
    public static final int POWBOX_MAX_TOP = 336;
    public static final int POWBOX_MAX_LEFT = 202;
    public static final int POWBOX_TEXT_MAX_TOP = 352;
    public static final int POWBOX_TEXT_MAX_LEFT = 212;
    public static final int DAMAGE_MAX_LEFT = 180;

    // tooltip hints delay in ms (need more time to display long hints withour hiding)
    public static final int TOOLTIPS_DELAY_MS = 60 * 1000;

    public static final Border EMPTY_BORDER = BorderFactory.createEmptyBorder(2, 2, 2, 2);

    public static final double SCALE_FACTOR = 0.5;

    // cards render
    public static final Rectangle CARD_SIZE_FULL = new Rectangle(101, 149);
    public static final Rectangle THUMBNAIL_SIZE_FULL = new Rectangle(102, 146);

    // resources - default images
    public static final String RESOURCE_PATH_DEFAULT_IMAGES = File.separator + "default";

    // resources - symbols
    public static final String RESOURCE_PATH_SYMBOLS = File.separator + "symbols";
    public static final String RESOURCE_SYMBOL_FOLDER_SMALL = "small";
    public static final String RESOURCE_SYMBOL_FOLDER_MEDIUM = "medium";
    public static final String RESOURCE_SYMBOL_FOLDER_LARGE = "large";
    public static final String RESOURCE_SYMBOL_FOLDER_SVG = "svg";
    public static final String RESOURCE_SYMBOL_FOLDER_PNG = "png";

    public enum ResourceSymbolSize {
        SMALL,
        MEDIUM,
        LARGE,
        SVG,
        PNG
    }

    // resources - sets
    public static final String RESOURCE_PATH_SETS = File.separator + "sets";
    public static final String RESOURCE_SET_FOLDER_SMALL = "small";
    public static final String RESOURCE_SET_FOLDER_MEDIUM = ""; // empty, medium images laydown in "sets" folder, TODO: delete that and auto gen, use png for html, not gif
    public static final String RESOURCE_SET_FOLDER_SVG = "svg";

    public enum ResourceSetSize {
        SMALL,
        MEDIUM,
        SVG
    }

    // sound
    public static final String BASE_SOUND_PATH = "sounds" + File.separator; // TODO: check path with File.separator
    public static final String BASE_MUSICS_PATH = "music" + File.separator;

    // battlefield feedback panel colors (used in preferences dialogs too)
    public static final int BATTLEFIELD_FEEDBACK_COLORIZING_MODE_DISABLE = 0;
    public static final int BATTLEFIELD_FEEDBACK_COLORIZING_MODE_ENABLE_BY_ONE_COLOR = 1;
    public static final int BATTLEFIELD_FEEDBACK_COLORIZING_MODE_ENABLE_BY_MULTICOLOR = 2;

    public static final int AUTO_TARGET_DISABLE      = 0;
    public static final int AUTO_TARGET_NON_FEEL_BAD = 1;
    public static final int AUTO_TARGET_ALL          = 2;

    public interface IO {
        String DEFAULT_IMAGES_DIR = "plugins" + File.separator + "images" + File.separator;
    }

    public enum DeckEditorMode {

        FREE_BUILDING,
        LIMITED_BUILDING,
        SIDEBOARDING,
        VIEW_LIMITED_DECK
    }

    public enum SortBy {
        CARD_TYPE("Card Type"),
        CASTING_COST("Casting Cost"),
        COLOR("Color"),
        COLOR_IDENTITY("Color Identity"),
        NAME("Name"),
        RARITY("Rarity"),
        UNSORTED("Unsorted"),
        EDH_POWER_LEVEL("EDH Power Level");

        private final String text;

        SortBy(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }

        public static SortBy getByString(String text) {
            for (SortBy sortBy : values()) {
                if (sortBy.text.equals(text)) {
                    return sortBy;
                }
            }
            return UNSORTED;
        }

    }

}
