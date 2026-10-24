package mage.client.util.gui;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/** Convert logical drawing sizes to raster pixels, including rotated card transforms. */
public final class HiDpiRenderUtil {
    private static final double MAX_RASTER_SCALE = 4;
    private static final int SOURCE_SIZE_STEP = 64;

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
        scaleX = Math.max(1, Math.min(MAX_RASTER_SCALE, scaleX));
        scaleY = Math.max(1, Math.min(MAX_RASTER_SCALE, scaleY));
        return new Dimension(Math.max(1, (int) Math.ceil(width * scaleX)),
                Math.max(1, (int) Math.ceil(height * scaleY)));
    }

    /** Cache a few resolution tiers rather than a new art image on every animation frame. */
    public static Dimension getSourceSize(Dimension raster, double oversampling) {
        int width = Math.max(SOURCE_SIZE_STEP,
                (int) Math.ceil(raster.width * oversampling / SOURCE_SIZE_STEP) * SOURCE_SIZE_STEP);
        return new Dimension(width, Math.max(1, (int) Math.ceil(raster.height * (width / (double) raster.width))));
    }

    /** Device-sized caches need a pixel copy; resample only for rotation or animation. */
    public static void configureRasterDraw(Graphics2D graphics, BufferedImage image, int width, int height) {
        AffineTransform transform = graphics.getTransform();
        boolean deviceSized = transform.getShearX() == 0 && transform.getShearY() == 0
                && Math.abs(Math.abs(transform.getScaleX()) * width - image.getWidth()) < 1
                && Math.abs(Math.abs(transform.getScaleY()) * height - image.getHeight()) < 1;
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, deviceSized
                ? RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
                : RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    }
}
