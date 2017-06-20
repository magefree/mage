package mage.cards;

import java.awt.geom.Rectangle2D;

/**
 * Created by stravant@gmail.com on 2017-04-04.
 */
public enum ArtRect {
    NORMAL(new Rectangle2D.Double(.079f, .11f, .84f, .42f)),
    AFTERMATH_TOP(new Rectangle2D.Double(0.075, 0.113, 0.832, 0.227)),
    AFTERMATH_BOTTOM(new Rectangle2D.Double(0.546, 0.562, 0.272, 0.346)),
    SPLIT_LEFT(new Rectangle2D.Double(0.152, 0.539, 0.386, 0.400)),
    SPLIT_RIGHT(new Rectangle2D.Double(0.152, 0.058, 0.386, 0.400)),
    SPLIT_FUSED(null);

    public final Rectangle2D rect;

    ArtRect(Rectangle2D.Double rect) {
        this.rect = rect;
    }
}
