/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.client.constants;

import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 *
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

    public static final Border EMPTY_BORDER = BorderFactory.createEmptyBorder(2, 2, 2, 2);

    public static final double SCALE_FACTOR = 0.5;

    public static final String PLUGINS_DIRECTORY = "plugins/";

    public static final String RESOURCE_PATH_MANA_LARGE = IO.imageBaseDir + "symbols" + File.separator + "large";
    public static final String RESOURCE_PATH_MANA_MEDIUM = IO.imageBaseDir + "symbols" + File.separator + "medium";
    public static final String RESOURCE_PATH_SET = IO.imageBaseDir + "sets" + File.separator;
    public static final String RESOURCE_PATH_SET_SMALL = RESOURCE_PATH_SET + File.separator + "small" + File.separator;
    public static final String BASE_SOUND_PATH = "sounds" + File.separator;
    public static final String BASE_MUSICS_PATH = "music" + File.separator;

    public interface IO {

        String imageBaseDir = "plugins" + File.separator + "images" + File.separator;
        String IMAGE_PROPERTIES_FILE = "image.url.properties";
    }

    public enum DeckEditorMode {

        FREE_BUILDING,
        LIMITED_BUILDING,
        SIDEBOARDING
    }

    public enum SortBy {

        CASTING_COST("Casting Cost"),
        RARITY("Rarity"),
        COLOR("Color"),
        COLOR_IDENTITY("Color Identity"),
        NAME("Name"),
        UNSORTED("Unsorted");

        private final String text;

        SortBy(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }

        public static SortBy getByString(String text) {
            switch (text) {
                case "Casting Cost":
                    return CASTING_COST;
                case "Rarity":
                    return RARITY;
                case "Color":
                    return COLOR;
                case "Color Identity":
                    return COLOR_IDENTITY;
                case "Name":
                    return NAME;
                default:
                    return UNSORTED;
            }
        }

    }

}
