package mage.cards;

import mage.ObjectColor;

public final class CardGraphicInfo {

    private final ObjectColor frameColor;
    private final FrameStyle frameStyle;
    private final boolean useVariousArt; // card in set have multiple images (use to store images files)

    public CardGraphicInfo(FrameStyle frameStyle, boolean useVariousArt) {
        this(null, frameStyle, useVariousArt);
    }

    public CardGraphicInfo(ObjectColor frameColor, FrameStyle frameStyle, boolean useVariousArt) {
        this.frameColor = frameColor;
        this.frameStyle = frameStyle;
        this.useVariousArt = useVariousArt;
    }

    public ObjectColor getFrameColor() {
        return this.frameColor != null ? this.frameColor.copy() : null;
    }

    public FrameStyle getFrameStyle() {
        return this.frameStyle;
    }

    public boolean getUsesVariousArt() {
        return this.useVariousArt;
    }
}
