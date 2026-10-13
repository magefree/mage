package mage.client.util.gui;

import java.awt.*;
import java.awt.geom.AffineTransform;

/** Convert logical drawing sizes to raster pixels, including rotated card transforms. */
public final class HiDpiRenderUtil {
    private HiDpiRenderUtil() {
    }

    public static Dimension getRasterSize(Graphics2D graphics, int width, int height) {
        return getRasterSize(graphics.getTransform(), width, height);
    }

    public static Dimension getRasterSize(Component component, int width, int height) {
        GraphicsConfiguration display = component.getGraphicsConfiguration();
        return getRasterSize(display == null ? new AffineTransform() : display.getDefaultTransform(), width, height);
    }

    private static Dimension getRasterSize(AffineTransform transform, int width, int height) {
        double scaleX = Math.hypot(transform.getScaleX(), transform.getShearY());
        double scaleY = Math.hypot(transform.getShearX(), transform.getScaleY());
        // Retain normal-DPI quality when animating down, and bound temporary animation rasters.
        scaleX = Math.max(1, Math.min(4, scaleX));
        scaleY = Math.max(1, Math.min(4, scaleY));
        return new Dimension(Math.max(1, (int) Math.ceil(width * scaleX)),
                Math.max(1, (int) Math.ceil(height * scaleY)));
    }

    /** Cache a few resolution tiers rather than a new art image on every animation frame. */
    public static Dimension getSourceSize(Dimension raster, double oversampling) {
        int width = Math.max(64, (int) Math.ceil(raster.width * oversampling / 64) * 64);
        return new Dimension(width, Math.max(1, (int) Math.ceil(raster.height * (width / (double) raster.width))));
    }
}
