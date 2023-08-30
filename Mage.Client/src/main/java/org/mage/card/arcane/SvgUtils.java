package org.mage.card.arcane;

import mage.abilities.icon.CardIconColor;
import mage.abilities.icon.CardIconImpl;
import mage.utils.StreamUtils;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.TranscodingHints;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.util.SVGConstants;
import org.apache.log4j.Logger;
import org.mage.plugins.card.utils.impl.ImageManagerImpl;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import static org.mage.plugins.card.utils.CardImageUtils.getImagesDir;

/**
 * @author JayDi85
 */
public class SvgUtils {

    private static final Logger logger = Logger.getLogger(SvgUtils.class);

    private static boolean haveSvgSupport = false;

    // basic css settings for good svg rendering quality, other default settings can be defined by additional param
    private static final String CSS_BASE_SETTINGS = ""
            + "shape-rendering: geometricPrecision;"
            + "text-rendering:  geometricPrecision;"
            + "color-rendering: optimizeQuality;"
            + "image-rendering: optimizeQuality;";

    private static String getSvgTempFolder() {
        return getImagesDir() + File.separator + "temp";
    }

    public static String getSvgTempFile(String fileName) {
        return getSvgTempFolder() + File.separator + fileName;
    }

    public static void prepareCss(String cssFileName, String cssAdditionalSettings, Boolean forceToCreateCss) {
        // css must be created all the time, so ignore svg check here
        //if (!SvgUtils.haveSvgSupport())

        File cssFile = new File(SvgUtils.getSvgTempFile(cssFileName));
        if (forceToCreateCss || !cssFile.exists()) {

            // Rendering hints can't be set programatically, so
            // we override defaults with a temporary stylesheet.
            // These defaults emphasize quality and precision, and
            // are more similar to the defaults of other SVG viewers.
            // SVG documents can still override these defaults.
            String css = "svg {"
                    + CSS_BASE_SETTINGS
                    + cssAdditionalSettings
                    + "}";
            FileWriter w = null;
            try {
                cssFile.getParentFile().mkdirs();
                cssFile.createNewFile();
                w = new FileWriter(cssFile);
                w.write(css);
            } catch (Throwable e) {
                logger.error("Can't create css file for svg: " + cssFile.toPath().toAbsolutePath().toString(), e);
            } finally {
                StreamUtils.closeQuietly(w);
            }
        }
    }

    /**
     * Load svg file content as image
     *
     * @param svgFile               content (can be resource or file)
     * @param svgInfo               info to show in logs or errors
     * @param cssFileName           css settings
     * @param cssAdditionalSettings additional css settings (warning, if you change additional settings then css file must be re-created)
     * @param resizeToWidth         image size
     * @param resizeToHeight        image size
     * @param useShadow             draw image with shadow (not implemented)
     * @return can return null on error (some linux systems can have compatibility problem with different java/svg libs)
     * @throws IOException
     */
    public static BufferedImage loadSVG(InputStream svgFile, String svgInfo,
                                        String cssFileName, String cssAdditionalSettings,
                                        int resizeToWidth, int resizeToHeight, boolean useShadow) throws IOException {
        if (svgFile == null) {
            throw new IllegalArgumentException("Empty svg data or unknown file");
        }

        // load SVG image
        // base loader code: https://stackoverflow.com/questions/11435671/how-to-get-a-buffererimage-from-a-svg
        // resize code: https://vibranttechie.wordpress.com/2015/05/15/svg-loading-to-javafx-stage-and-auto-scaling-when-stage-resize/
        useShadow = false; // TODO: implement shadow drawing
        if (useShadow && ((resizeToWidth <= 0) || (resizeToHeight <= 0))) {
            throw new IllegalArgumentException("Must use non zero sizes for shadow");
        }

        final BufferedImage[] imagePointer = new BufferedImage[1];

        // css settings for svg
        SvgUtils.prepareCss(cssFileName, cssAdditionalSettings, false);
        File cssFile = new File(SvgUtils.getSvgTempFile(cssFileName));

        TranscodingHints transcoderHints = new TranscodingHints();

        // resize
        int shadowX = 0;
        int shadowY = 0;
        if (useShadow) {
            // shadow size (16px image: 1px left, 2px bottom)
            shadowX = 1 * Math.round(1f / 16f * resizeToWidth);
            shadowY = 2 * Math.round(1f / 16f * resizeToHeight);
            resizeToWidth = resizeToWidth - shadowX;
            resizeToHeight = resizeToHeight - shadowY;
        }

        if (resizeToWidth > 0) {
            transcoderHints.put(ImageTranscoder.KEY_WIDTH, (float) resizeToWidth); //your image width
        }
        if (resizeToHeight > 0) {
            transcoderHints.put(ImageTranscoder.KEY_HEIGHT, (float) resizeToHeight); //your image height
        }

        transcoderHints.put(ImageTranscoder.KEY_XML_PARSER_VALIDATING, Boolean.FALSE);
        transcoderHints.put(ImageTranscoder.KEY_DOM_IMPLEMENTATION,
                SVGDOMImplementation.getDOMImplementation());
        transcoderHints.put(ImageTranscoder.KEY_DOCUMENT_ELEMENT_NAMESPACE_URI,
                SVGConstants.SVG_NAMESPACE_URI);
        transcoderHints.put(ImageTranscoder.KEY_DOCUMENT_ELEMENT, "svg");
        transcoderHints.put(ImageTranscoder.KEY_USER_STYLESHEET_URI, cssFile.toURI().toString());

        try {
            TranscoderInput input = new TranscoderInput(svgFile);
            ImageTranscoder t = new ImageTranscoder() {

                @Override
                public BufferedImage createImage(int w, int h) {
                    return new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                }

                @Override
                public void writeImage(BufferedImage image, TranscoderOutput out) {
                    imagePointer[0] = image;
                }
            };
            t.setTranscodingHints(transcoderHints);
            t.transcode(input, null);
        } catch (Exception e) {
            throw new IOException("Can't load svg file: " + svgInfo + " , reason: " + e.getMessage());
        }

        BufferedImage originImage = imagePointer[0];

        if (useShadow && (originImage.getWidth() > 0)) {
            // draw shadow
            // origin image was reduces in sizes to fit shadow
            // see https://stackoverflow.com/a/40833715/1276632

            // a filter which converts all colors except 0 to black
            ImageProducer prod = new FilteredImageSource(originImage.getSource(), new RGBImageFilter() {
                @Override
                public int filterRGB(int x, int y, int rgb) {
                    if (rgb == 0) {
                        return 0;
                    } else {
                        return 0xff000000;
                    }
                }
            });
            // create whe black image
            Image shadow = Toolkit.getDefaultToolkit().createImage(prod);
            // result
            BufferedImage result = new BufferedImage(originImage.getWidth() + shadowX, originImage.getHeight() + shadowY, originImage.getType());
            Graphics2D g = (Graphics2D) result.getGraphics();
            // draw shadow with offset (left bottom)
            g.drawImage(shadow, -1 * shadowX, shadowY, null);
            // draw original image
            g.drawImage(originImage, 0, 0, null);
            return result;
        } else {
            // return origin image without shadow
            return originImage;
        }

        /*
        BufferedImage base = GraphicsUtilities.createCompatibleTranslucentImage(w, h);
        Graphics2D g2 = base.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, image.getWidth(), image.getHeight(), 10, 10);
        g2.dispose();

        ShadowRenderer renderer = new ShadowRenderer(shadowSize, 0.5f,
                Color.GRAY);
        return renderer.createShadow(base);
         */
        //imagePointer[0];
    }

    /**
     * Check if the current system support svg (some linux systems can have compatibility problems due to different java/svg libs)
     * <p>
     * Call it on app's start
     *
     * @return true on support, also save result for haveSvgSupport
     */
    public static boolean checkSvgSupport() {
        // usa sample icon for svg support testing
        // direct call, no needs in cache
        BufferedImage sampleImage = ImageManagerImpl.instance
                .getCardIcon(CardIconImpl.ABILITY_FLYING.getIconType().getResourceName(), 32, CardIconColor.DEFAULT);
        haveSvgSupport = (sampleImage != null && sampleImage.getWidth() > 0);
        if (!haveSvgSupport) {
            logger.warn("WARNING, your system doesn't support svg images, so card icons will be disabled. Please, make a bug report in the github.");
        }
        return haveSvgSupport;
    }

    public static boolean haveSvgSupport() {
        return haveSvgSupport;
    }
}
