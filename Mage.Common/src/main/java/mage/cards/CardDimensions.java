

package mage.cards;

import static mage.constants.Constants.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CardDimensions {

    private final int frameHeight;
    private final int frameWidth;
    private final int symbolHeight;
    private final int symbolWidth;
    private final int contentXOffset;
    private final int nameYOffset;
    private final int typeYOffset;
    private final int textYOffset;
    private final int textWidth;
    private final int textHeight;
    private final int powBoxTextTop;
    private final int powBoxTextLeft;
    private final int nameFontSize;

    public CardDimensions(double scaleFactor) {
        frameHeight = (int) (FRAME_MAX_HEIGHT * scaleFactor);
        frameWidth = (int) (FRAME_MAX_WIDTH * scaleFactor);
        symbolHeight = (int) (SYMBOL_MAX_HEIGHT * scaleFactor);
        symbolWidth = (int) (SYMBOL_MAX_WIDTH * scaleFactor);
        contentXOffset = (int) (CONTENT_MAX_XOFFSET * scaleFactor);
        nameYOffset = (int) (NAME_MAX_YOFFSET * scaleFactor);
        typeYOffset = (int) (TYPE_MAX_YOFFSET * scaleFactor);
        textYOffset = (int) (TEXT_MAX_YOFFSET * scaleFactor);
        textWidth = (int) (TEXT_MAX_WIDTH * scaleFactor);
        textHeight = (int) (TEXT_MAX_HEIGHT * scaleFactor);
        powBoxTextTop = (int) (POWBOX_TEXT_MAX_TOP * scaleFactor);
        powBoxTextLeft = (int) (POWBOX_TEXT_MAX_LEFT * scaleFactor);
        nameFontSize = Math.max(9, (int) (NAME_FONT_MAX_SIZE * scaleFactor));
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public int getSymbolHeight() {
        return symbolHeight;
    }

    public int getSymbolWidth() {
        return symbolWidth;
    }

    public int getContentXOffset() {
        return contentXOffset;
    }

    public int getNameYOffset() {
        return nameYOffset;
    }

    public int getTypeYOffset() {
        return typeYOffset;
    }

    public int getTextYOffset() {
        return textYOffset;
    }

    public int getTextWidth() {
        return textWidth;
    }

    public int getTextHeight() {
        return textHeight;
    }

    public int getPowBoxTextTop() {
        return powBoxTextTop;
    }

    public int getPowBoxTextLeft() {
        return powBoxTextLeft;
    }

    public int getNameFontSize() {
        return nameFontSize;
    }
}
