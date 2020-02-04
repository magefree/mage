package mage.cards;

import mage.ObjectColor;

import java.io.Serializable;

public final class CardGraphicInfo implements Serializable {

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

    private CardGraphicInfo(final CardGraphicInfo info) {
        this.frameColor = info.frameColor != null ? info.frameColor.copy() : null;
        this.frameStyle = info.frameStyle;
        this.useVariousArt = info.useVariousArt;
    }

    public CardGraphicInfo copy() {
        return new CardGraphicInfo(this);
    }
}
