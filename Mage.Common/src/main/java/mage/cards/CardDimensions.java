

package mage.cards;

import static mage.constants.Constants.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CardDimensions {

    public int frameHeight;
    public int frameWidth;
    public int symbolHeight;
    public int symbolWidth;
    public int contentXOffset;
    public int nameYOffset;
    public int typeYOffset;
    public int textYOffset;
    public int textWidth;
    public int textHeight;
    public int powBoxTextTop;
    public int powBoxTextLeft;
    public int nameFontSize;

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

}
