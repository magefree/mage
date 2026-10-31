package mage.client.util.gui;

import org.junit.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

import static org.junit.Assert.*;

public class HiDpiRenderingTest extends GuiScalingTestSupport {
    @Test
    public void stretchIconsRetainDetailAndLoadOneRasterPerDisplaySize() {
        java.util.List<Integer> sizes = new ArrayList<>();
        mage.client.components.StretchIcon icon = mage.client.components.StretchIcon.fromRasterSource(pixels -> {
            sizes.add(pixels);
            BufferedImage source = new BufferedImage(pixels, pixels, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < pixels; x++) {
                for (int y = 0; y < pixels; y++) source.setRGB(x, y, (x % 2 == 0 ? Color.BLACK : Color.WHITE).getRGB());
            }
            return source;
        }, true);
        JLabel label = new JLabel(icon);
        label.setSize(32, 32);
        for (double scale : new double[]{1, 1.25, 1.5, 2, 1}) {
            BufferedImage output = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = output.createGraphics();
            try {
                graphics.scale(scale, scale);
                icon.paintIcon(label, graphics, 0, 0);
                icon.paintIcon(label, graphics, 0, 0);
            } finally { graphics.dispose(); }
            assertEquals(Color.BLACK.getRGB(), output.getRGB(10, 20));
            assertEquals(Color.WHITE.getRGB(), output.getRGB(11, 20));
        }
        assertEquals(Arrays.asList(32, 40, 48, 64, 32), sizes);
        assertEquals(0, icon.getIconWidth());
        assertEquals(0, icon.getIconHeight());
    }

    @Test
    public void iconsKeepLogicalSizeAndReuseDeviceRasterBetweenRepaints() {
        java.util.List<Integer> requests = new ArrayList<>();
        HiDpiIcon icon = new HiDpiIcon(32, pixels -> {
            requests.add(pixels);
            BufferedImage source = new BufferedImage(pixels, pixels, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < pixels; x++) {
                for (int y = 0; y < pixels; y++) {
                    source.setRGB(x, y, (x % 2 == 0 ? Color.BLACK : Color.WHITE).getRGB());
                }
            }
            return source;
        });
        for (double scale : new double[]{1, 1.25, 1.5, 2, 1}) {
            int pixels = (int) Math.ceil(32 * scale);
            BufferedImage output = new BufferedImage(pixels, pixels, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = output.createGraphics();
            try {
                graphics.scale(scale, scale);
                icon.paintIcon(null, graphics, 0, 0);
                icon.paintIcon(null, graphics, 0, 0);
            } finally {
                graphics.dispose();
            }
            assertEquals(32, icon.getIconWidth());
            assertEquals(32, icon.getIconHeight());
            assertEquals(Color.BLACK.getRGB(), output.getRGB(10, 20));
            assertEquals(Color.WHITE.getRGB(), output.getRGB(11, 20));
        }
        assertEquals(Arrays.asList(32, 40, 48, 64, 32), requests);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void manaDrawingUsesDevicePixelsWithoutChangingSymbolLayout() throws Exception {
        Map<Integer, Map<String, BufferedImage>> symbols = (Map<Integer, Map<String, BufferedImage>>) field(
                org.mage.card.arcane.ManaSymbols.class, "manaImages").get(null);
        Map<String, BufferedImage> oldLogical = symbols.get(32);
        Map<String, BufferedImage> oldRaster = symbols.get(64);
        BufferedImage logical = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        BufferedImage raster = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < 64; x++) {
            for (int y = 0; y < 64; y++) raster.setRGB(x, y, (x % 2 == 0 ? Color.BLACK : Color.WHITE).getRGB());
        }
        symbols.put(32, Collections.singletonMap("W", logical));
        symbols.put(64, Collections.singletonMap("W", raster));
        try {
            BufferedImage output = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = output.createGraphics();
            graphics.scale(2, 2);
            org.mage.card.arcane.ManaSymbols.draw(graphics, "W", 0, 0, 32);
            graphics.dispose();
            assertEquals(Color.BLACK.getRGB(), output.getRGB(10, 20));
            assertEquals(Color.WHITE.getRGB(), output.getRGB(11, 20));
            HiDpiIcon icon = new HiDpiIcon(32, size -> org.mage.card.arcane.ManaSymbols.getHiDpiManaSymbol("W", size));
            assertEquals(32, icon.getIconWidth());
            assertEquals(32, icon.getIconHeight());
        } finally {
            if (oldLogical == null) symbols.remove(32); else symbols.put(32, oldLogical);
            if (oldRaster == null) symbols.remove(64); else symbols.put(64, oldRaster);
        }
    }

    @Test
    public void renderedCardCacheChangesRasterWhenMovingBetweenDisplayScales() throws Exception {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        SwingUtilities.invokeAndWait(() -> {
            try {
                mage.view.CardView card = new mage.view.CardView(new RasterTestCard());
                org.mage.card.arcane.CardPanelRenderModeMTGO panel = new org.mage.card.arcane.CardPanelRenderModeMTGO(
                        card, null, false, null, false, new Dimension(100, 145), false, 0);
                panel.setSize(100, 145);
                for (double scale : new double[]{1, 1.25, 1.5, 2, 1}) {
                    BufferedImage output = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D graphics = output.createGraphics();
                    graphics.scale(scale, scale);
                    panel.paint(graphics);
                    graphics.dispose();
                    BufferedImage raster = (BufferedImage) field(org.mage.card.arcane.CardPanelRenderModeMTGO.class, "cardImage").get(panel);
                    assertEquals((int) Math.ceil(100 * scale), raster.getWidth());
                    assertEquals((int) Math.ceil(145 * scale), raster.getHeight());
                }
            } catch (Exception error) {
                throw new RuntimeException(error);
            }
        });
    }

    @Test
    public void imageAndPreviewRetainDevicePixelDetail() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            BufferedImage source = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < 200; x++) {
                for (int y = 0; y < 200; y++) {
                    source.setRGB(x, y, (x % 2 == 0 ? Color.BLACK : Color.WHITE).getRGB());
                }
            }
            org.mage.card.arcane.ScaledImagePanel image = new org.mage.card.arcane.ScaledImagePanel();
            image.setSize(100, 100);
            image.setImage(source);
            assertDevicePixelDetail(image);
            // Image mode fills its panel even when the source aspect ratio differs.
            // Avoid shrinking the width first and stretching that intermediate raster.
            image.setSize(100, 80);
            assertDevicePixelDetail(image);
            mage.client.cards.BigCard preview = new mage.client.cards.BigCard();
            preview.setSize(100, 100);
            preview.setCard(UUID.randomUUID(), mage.constants.EnlargeMode.NORMAL, source, Collections.emptyList(), false);
            assertDevicePixelDetail(preview);
        });
    }

    private static void assertDevicePixelDetail(JComponent component) {
        BufferedImage output = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = output.createGraphics();
        graphics.scale(2, 2);
        try {
            component.paint(graphics);
        } finally {
            graphics.dispose();
        }
        assertEquals(Color.BLACK.getRGB(), output.getRGB(10, 20));
        assertEquals(Color.WHITE.getRGB(), output.getRGB(11, 20));
    }

    @Test
    public void rasterDimensionsFollowScaleAndRotation() {
        Graphics2D graphics = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics();
        try {
            for (double scale : new double[]{1, 1.25, 1.5, 2}) {
                graphics.setTransform(java.awt.geom.AffineTransform.getScaleInstance(scale, scale));
                assertEquals(new Dimension((int) Math.ceil(101 * scale), (int) Math.ceil(145 * scale)), HiDpiRenderUtil.getRasterSize(graphics, 101, 145));
                graphics.rotate(Math.PI / 2);
                assertEquals(new Dimension((int) Math.ceil(101 * scale), (int) Math.ceil(145 * scale)), HiDpiRenderUtil.getRasterSize(graphics, 101, 145));
            }
        } finally {
            graphics.dispose();
        }
    }

    @Test
    public void mtgoFadeKeepsDeviceRasterAndBlendsAtEveryDisplayScale() throws Exception {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        SwingUtilities.invokeAndWait(() -> {
            try {
                RasterTestPanel panel = new RasterTestPanel();
                for (double scale : new double[]{1, 1.25, 1.5, 2}) {
                    int width = (int) Math.ceil(panel.getCardWidth() * scale);
                    int height = (int) Math.ceil(panel.getCardHeight() * scale);
                    BufferedImage raster = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D source = raster.createGraphics();
                    try {
                        source.setColor(Color.RED);
                        source.fillRect(0, 0, width, height);
                    } finally {
                        source.dispose();
                    }
                    field(org.mage.card.arcane.CardPanelRenderModeMTGO.class, "rasterWidth").setInt(panel, width);
                    field(org.mage.card.arcane.CardPanelRenderModeMTGO.class, "rasterHeight").setInt(panel, height);
                    field(org.mage.card.arcane.CardPanelRenderModeMTGO.class, "cardImage").set(panel, raster);
                    for (float alpha : new float[]{1, 0.5f, 0.25f, 1}) {
                        panel.setAlpha(alpha);
                        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D graphics = output.createGraphics();
                        try {
                            graphics.setColor(Color.BLUE);
                            graphics.fillRect(0, 0, width, height);
                            graphics.scale(scale, scale);
                            panel.paintRaster(graphics);
                        } finally {
                            graphics.dispose();
                        }
                        Color blended = new Color(output.getRGB(width / 2, height / 2), true);
                        assertEquals("red at scale " + scale + ", alpha " + alpha,
                                Math.round(255 * alpha), blended.getRed(), 1);
                        assertEquals("blue at scale " + scale + ", alpha " + alpha,
                                Math.round(255 * (1 - alpha)), blended.getBlue(), 1);
                        assertEquals(255, blended.getAlpha());
                        assertSame("fading must not rebuild the device raster", raster,
                                field(org.mage.card.arcane.CardPanelRenderModeMTGO.class, "cardImage").get(panel));
                    }
                }
            } catch (Exception error) {
                throw new RuntimeException(error);
            }
        });
    }

    private static class RasterTestPanel extends org.mage.card.arcane.CardPanelRenderModeMTGO {
        RasterTestPanel() {
            super(new mage.view.CardView(new RasterTestCard()), null, false, null, false,
                    new Dimension(100, 145), false, 0);
        }

        void paintRaster(Graphics2D graphics) {
            super.paintCard(graphics);
        }
    }

    private static class RasterTestCard extends mage.cards.CardImpl {
        RasterTestCard() {
            super(UUID.randomUUID(), new mage.cards.CardSetInfo("Raster test", "TST", "1", mage.constants.Rarity.COMMON),
                    new mage.constants.CardType[]{mage.constants.CardType.CREATURE}, "{1}");
            power = new mage.MageInt(2);
            toughness = new mage.MageInt(2);
        }
        RasterTestCard(RasterTestCard card) { super(card); }
        public RasterTestCard copy() { return new RasterTestCard(this); }
    }
}
