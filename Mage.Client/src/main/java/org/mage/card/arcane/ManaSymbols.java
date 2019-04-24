package org.mage.card.arcane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;
import javax.swing.*;

import mage.cards.repository.ExpansionRepository;
import mage.client.MageFrame;
import mage.client.constants.Constants;
import mage.client.constants.Constants.ResourceSetSize;
import mage.client.constants.Constants.ResourceSymbolSize;
import mage.client.util.GUISizeHelper;
import mage.client.util.ImageHelper;
import mage.client.util.gui.BufferedImageBuilder;
import mage.utils.StreamUtils;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.TranscodingHints;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.util.SVGConstants;
import org.apache.log4j.Logger;
import org.mage.plugins.card.utils.CardImageUtils;

import static org.mage.plugins.card.utils.CardImageUtils.getImagesDir;

public final class ManaSymbols {

    private static final Logger LOGGER = Logger.getLogger(ManaSymbols.class);
    private static final Map<Integer, Map<String, BufferedImage>> manaImages = new HashMap<>();

    private static final Map<String, Map<String, Image>> setImages = new ConcurrentHashMap<>();

    private static final HashSet<String> onlyMythics = new HashSet<>();
    private static final HashSet<String> withoutSymbols = new HashSet<>();

    static {
        onlyMythics.add("DRB");
        onlyMythics.add("V09");
        onlyMythics.add("V12");
        onlyMythics.add("V13");
        onlyMythics.add("V14");
        onlyMythics.add("V15");
        onlyMythics.add("V16");
        onlyMythics.add("EXP");
        onlyMythics.add("MPS");

        withoutSymbols.add("MPRP");
    }

    private static final Map<String, Dimension> setImagesExist = new HashMap<>();
    private static final Pattern REPLACE_SYMBOLS_PATTERN = Pattern.compile("\\{([^}/]*)/?([^}]*)\\}");

    private static final String[] symbols = new String[]{"0", "1", "10", "11", "12", "15", "16", "2", "3", "4", "5", "6", "7", "8", "9",
            "B", "BG", "BR", "BP", "2B",
            "G", "GU", "GW", "GP", "2G",
            "R", "RG", "RW", "RP", "2R",
            "S", "T",
            "U", "UB", "UR", "UP", "2U",
            "W", "WB", "WU", "WP", "2W",
            "X", "C", "E"};

    private static final JLabel labelRender = new JLabel(); // render mana text

    private static String getSvgPathToCss() {
        return getImagesDir() + File.separator + "temp" + File.separator + "batic-svg-settings.css";
    }

    private static void prepareSvg(Boolean forceToCreateCss) {
        File f = new File(getSvgPathToCss());

        if (forceToCreateCss || !f.exists()) {

            // Rendering hints can't be set programatically, so
            // we override defaults with a temporary stylesheet.
            // These defaults emphasize quality and precision, and
            // are more similar to the defaults of other SVG viewers.
            // SVG documents can still override these defaults.
            String css = "svg {"
                    + "shape-rendering: geometricPrecision;"
                    + "text-rendering:  geometricPrecision;"
                    + "color-rendering: optimizeQuality;"
                    + "image-rendering: optimizeQuality;"
                    + "}";

            FileWriter w = null;
            try {
                f.getParentFile().mkdirs();
                f.createNewFile();
                w = new FileWriter(f);
                w.write(css);
            } catch (Throwable e) {
                LOGGER.error("Can't create css file for svg", e);
            } finally {
                StreamUtils.closeQuietly(w);
            }
        }
    }

    public static void loadImages() {
        LOGGER.info("Loading symbols...");

        // TODO: delete files rename jpg->gif (it was for backward compatibility for one of the old version?)
        renameSymbols(getResourceSymbolsPath(ResourceSymbolSize.SMALL));
        renameSymbols(getResourceSymbolsPath(ResourceSymbolSize.MEDIUM));
        renameSymbols(getResourceSymbolsPath(ResourceSymbolSize.LARGE));
        //renameSymbols(getSymbolsPath(ResourceSymbolSize.SVG)); // not need
        // TODO: remove medium sets files to "medium" folder like symbols above?

        // prepare svg settings
        prepareSvg(true);

        // preload symbol images
        loadSymbolImages(15);
        loadSymbolImages(25);
        loadSymbolImages(50);

        // save symbol images in png for html replacement in texts
        // you can add bigger size for better quality
        Map<String, BufferedImage> pngImages = manaImages.get(50);
        if (pngImages != null) {

            File pngPath = new File(getResourceSymbolsPath(ResourceSymbolSize.PNG));
            if (!pngPath.exists()) {
                pngPath.mkdirs();
            }

            for (String symbol : symbols) {
                try {
                    BufferedImage image = pngImages.get(symbol);
                    if (image != null) {
                        File newFile = new File(pngPath.getPath() + File.separator + symbol + ".png");
                        ImageIO.write(image, "png", newFile);
                    }
                } catch (Exception e) {
                    LOGGER.warn("Can't generate png image for symbol:" + symbol);
                }
            }
        }

        // preload set images
        List<String> setCodes = ExpansionRepository.instance.getSetCodes();
        if (setCodes == null) {
            // the cards db file is probaly not included in the client. It will be created after the first connect to a server.
            LOGGER.warn("No db information for sets found. Connect to a server to create database file on client side. Then try to restart the client.");
            return;
        }
        for (String set : setCodes) {

            if (withoutSymbols.contains(set)) {
                continue;
            }

            String[] codes;
            if (onlyMythics.contains(set)) {
                codes = new String[]{"M"};
            } else {
                codes = new String[]{"C", "U", "R", "M"};
            }

            Map<String, Image> rarityImages = new HashMap<>();
            setImages.put(set, rarityImages);

            // load medium size
            for (String rarityCode : codes) {
                File file = new File(getResourceSetsPath(ResourceSetSize.MEDIUM) + set + '-' + rarityCode + ".jpg");
                try {
                    Image image = UI.getImageIcon(file.getAbsolutePath()).getImage();
                    int width = image.getWidth(null);
                    if (width > 21) {
                        int h = image.getHeight(null);
                        if (h > 0) {
                            Rectangle r = new Rectangle(21, (int) (h * 21.0f / width));
                            BufferedImage resized = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
                            rarityImages.put(rarityCode, resized);
                        }
                    } else {
                        rarityImages.put(rarityCode, image);
                    }
                } catch (Exception e) {
                }
            }

            // generate small size
            try {
                File file = new File(getResourceSetsPath(ResourceSetSize.MEDIUM));
                if (!file.exists()) {
                    file.mkdirs();
                }
                String pathRoot = getResourceSetsPath(ResourceSetSize.SMALL) + set;
                for (String code : codes) {
                    File newFile = new File(pathRoot + '-' + code + ".png");
                    if (!(MageFrame.isSkipSmallSymbolGenerationForExisting() && newFile.exists())) {// skip if option enabled and file already exists
                        file = new File(getResourceSetsPath(ResourceSetSize.MEDIUM) + set + '-' + code + ".png");
                        if (file.exists()) {
                            continue;
                        }
                        file = new File(getResourceSetsPath(ResourceSetSize.MEDIUM) + set + '-' + code + ".jpg");
                        Image image = UI.getImageIcon(file.getAbsolutePath()).getImage();
                        try {
                            int width = image.getWidth(null);
                            int height = image.getHeight(null);
                            if (height > 0) {
                                int dx = 0;
                                if (set.equals("M10") || set.equals("M11") || set.equals("M12")) {
                                    dx = 6;
                                }
                                Rectangle r = new Rectangle(15 + dx, (int) (height * (15.0f + dx) / width));
                                BufferedImage resized = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
                                ImageIO.write(resized, "png", newFile);
                            }
                        } catch (Exception e) {
                            if (file.exists()) {
                                file.delete();
                            }
                        }
                    }
                }

            } catch (Exception e) {
            }
        }
        // mark loaded images
        // TODO: delete that code, images draw-show must dynamicly
        File file;
        for (String set : ExpansionRepository.instance.getSetCodes()) {
            file = new File(getResourceSetsPath(ResourceSetSize.SMALL));
            if (!file.exists()) {
                break;
            }
            file = new File(getResourceSetsPath(ResourceSetSize.SMALL) + set + "-C.png");
            try {
                Image image = UI.getImageIcon(file.getAbsolutePath()).getImage();
                int width = image.getWidth(null);
                int height = image.getHeight(null);
                setImagesExist.put(set, new Dimension(width, height));
            } catch (Exception e) {
            }
        }
    }

    public static BufferedImage loadSVG(File svgFile, int resizeToWidth, int resizeToHeight, boolean useShadow) throws IOException {
        // debug: disable shadow gen, need to test it
        useShadow = false;

        // load SVG image
        // base loader code: https://stackoverflow.com/questions/11435671/how-to-get-a-buffererimage-from-a-svg
        // resize code: https://vibranttechie.wordpress.com/2015/05/15/svg-loading-to-javafx-stage-and-auto-scaling-when-stage-resize/
        if (useShadow && ((resizeToWidth <= 0) || (resizeToHeight <= 0))) {
            throw new IllegalArgumentException("Must use non zero sizes for shadow.");
        }

        final BufferedImage[] imagePointer = new BufferedImage[1];

        // css settings for svg
        prepareSvg(false);
        File cssFile = new File(getSvgPathToCss());

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
            TranscoderInput input = new TranscoderInput(new FileInputStream(svgFile));
            ImageTranscoder t = new ImageTranscoder() {

                @Override
                public BufferedImage createImage(int w, int h) {
                    return new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                }

                @Override
                public void writeImage(BufferedImage image, TranscoderOutput out)
                        throws TranscoderException {
                    imagePointer[0] = image;
                }
            };
            t.setTranscodingHints(transcoderHints);
            t.transcode(input, null);
        } catch (Exception e) {
            throw new IOException("Couldn't convert svg file: " + svgFile + " , reason: " + e.getMessage());
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

    public static File getSymbolFileNameAsSVG(String symbol) {
        return new File(getResourceSymbolsPath(ResourceSymbolSize.SVG) + symbol + ".svg");
    }

    private static BufferedImage loadSymbolAsSVG(String symbol, int resizeToWidth, int resizeToHeight) {

        File sourceFile = getSymbolFileNameAsSVG(symbol);
        return loadSymbolAsSVG(sourceFile, resizeToWidth, resizeToHeight);
    }

    private static BufferedImage loadSymbolAsSVG(File sourceFile, int resizeToWidth, int resizeToHeight) {
        try {
            // no need to resize svg (lib already do it on load)
            return loadSVG(sourceFile, resizeToWidth, resizeToHeight, true);

        } catch (Exception e) {
            LOGGER.error("Can't load svg symbol: " + sourceFile.getPath() + " , reason: " + e.getMessage());
            return null;
        }
    }

    private static File getSymbolFileNameAsGIF(String symbol, int size) {

        ResourceSymbolSize needSize = null;
        if (size <= 15) {
            needSize = ResourceSymbolSize.SMALL;
        } else if (size <= 25) {
            needSize = ResourceSymbolSize.MEDIUM;
        } else {
            needSize = ResourceSymbolSize.LARGE;
        }

        return new File(getResourceSymbolsPath(needSize) + symbol + ".gif");
    }

    private static BufferedImage loadSymbolAsGIF(String symbol, int resizeToWidth, int resizeToHeight) {
        File file = getSymbolFileNameAsGIF(symbol, resizeToWidth);
        return loadSymbolAsGIF(file, resizeToWidth, resizeToHeight);
    }

    private static BufferedImage loadSymbolAsGIF(File sourceFile, int resizeToWidth, int resizeToHeight) {

        BufferedImage image = null;

        try {
            if ((resizeToWidth == 15) || (resizeToWidth == 25)) {
                // normal size
                image = ImageIO.read(sourceFile);
            } else {
                // resize size
                image = ImageIO.read(sourceFile);

                if (image != null) {
                    Rectangle r = new Rectangle(resizeToWidth, resizeToHeight);
                    image = ImageHelper.getResizedImage(image, r);
                }
            }
        } catch (IOException e) {
            LOGGER.error("Can't load gif symbol: " + sourceFile.getPath());
            return null;
        }

        return image;
    }

    private static boolean loadSymbolImages(int size) {
        // load all symbols to cache
        // priority: SVG -> GIF
        // gif remain for backward compatibility

        int[] iconErrors = new int[2]; // 0 - svg, 1 - gif

        AtomicBoolean fileErrors = new AtomicBoolean(false);
        Map<String, BufferedImage> sizedSymbols = new ConcurrentHashMap<>();
        IntStream.range(0, symbols.length).parallel().forEach(i -> {
            String symbol = symbols[i];
            BufferedImage image = null;
            File file;

            // svg
            file = getSymbolFileNameAsSVG(symbol);
            if (file.exists()) {
                image = loadSymbolAsSVG(file, size, size);
            }

            // gif
            if (image == null) {

                iconErrors[0] += 1; // svg fail

                file = getSymbolFileNameAsGIF(symbol, size);
                if (file.exists()) {
                    image = loadSymbolAsGIF(file, size, size);
                }
            }

            // save
            if (image != null) {
                sizedSymbols.put(symbol, image);
            } else {
                iconErrors[1] += 1; // gif fail
                fileErrors.set(true);
            }
        });

        // total errors
        String errorInfo = "";
        if (iconErrors[0] > 0) {
            errorInfo += "SVG fails - " + iconErrors[0];
        }
        if (iconErrors[1] > 0) {
            if (!errorInfo.isEmpty()) {
                errorInfo += ", ";
            }
            errorInfo += "GIF fails - " + iconErrors[1];
        }

        if (!errorInfo.isEmpty()) {
            LOGGER.warn("Symbols can't be load for size " + size + ": " + errorInfo);
        }

        manaImages.put(size, sizedSymbols);
        return !fileErrors.get();
    }

    private static void renameSymbols(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }

        final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.jpg");
        try {
            Files.walkFileTree(Paths.get(path), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (matcher.matches(file)) {
                        Path gifPath = file.resolveSibling(file.getFileName().toString().replaceAll("\\.jpg$", ".gif"));
                        Files.move(file, gifPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            LOGGER.error("Couldn't rename mana symbols on " + path, e);
        }
    }

    private static String getResourceSymbolsPath(ResourceSymbolSize needSize) {
        // return real path to symbols (default or user defined)

        String path = CardImageUtils.getImagesDir() + Constants.RESOURCE_PATH_SYMBOLS + File.separator;

        // folder by sizes
        switch (needSize) {
            case SMALL:
                path = path + Constants.RESOURCE_SYMBOL_FOLDER_SMALL;
                break;
            case MEDIUM:
                path = path + Constants.RESOURCE_SYMBOL_FOLDER_MEDIUM;
                break;
            case LARGE:
                path = path + Constants.RESOURCE_SYMBOL_FOLDER_LARGE;
                break;
            case SVG:
                path = path + Constants.RESOURCE_SYMBOL_FOLDER_SVG;
                break;
            case PNG:
                path = path + Constants.RESOURCE_SYMBOL_FOLDER_PNG;
                break;
            default:
                throw new java.lang.IllegalArgumentException(
                        "ResourceSymbolSize value is unknown");
        }

        // fix double separator if size folder is not set
        while (path.endsWith(File.separator)) {
            path = path.substring(0, path.length() - 1);
        }

        return path + File.separator;
    }

    private static String getResourceSetsPath(ResourceSetSize needSize) {
        // return real path to sets icons (default or user defined)

        String path = CardImageUtils.getImagesDir() + Constants.RESOURCE_PATH_SYMBOLS + File.separator;

        // folder by sizes
        switch (needSize) {
            case SMALL:
                path = path + Constants.RESOURCE_SET_FOLDER_SMALL;
                break;
            case MEDIUM:
                path = path + Constants.RESOURCE_SET_FOLDER_MEDIUM;
                break;
            case SVG:
                path = path + Constants.RESOURCE_SET_FOLDER_SVG;
                break;
            default:
                throw new java.lang.IllegalArgumentException(
                        "ResourceSetSize value is unknown");
        }

        // fix double separator if size folder is not set
        while (path.endsWith(File.separator)) {
            path = path.substring(0, path.length() - 1);
        }

        return path + File.separator;
    }

    public static void draw(Graphics g, String manaCost, int x, int y, int symbolWidth) {
        draw(g, manaCost, x, y, symbolWidth, Color.white, 0);
    }

    public static void draw(Graphics g, String manaCost, int x, int y, int symbolWidth, Color symbolsTextColor, int symbolMarginX) {
        if (!manaImages.containsKey(symbolWidth)) {
            loadSymbolImages(symbolWidth);
        }

        // TODO: replace with jlabel render (look at table rendere)?

        /*
        // NEW version with component draw
        JPanel manaPanel = new JPanel();

        // icons size with margin
        int symbolHorizontalMargin = 2;

        // create each mana symbol as child label
        manaPanel.removeAll();
        manaPanel.setLayout(new BoxLayout(manaPanel, BoxLayout.X_AXIS));
        StringTokenizer tok = new StringTokenizer(manaCost, " ");
        while (tok.hasMoreTokens()) {
            String symbol = tok.nextToken();

            JLabel symbolLabel = new JLabel();
            //symbolLabel.setBorder(new LineBorder(new Color(150, 150, 150))); // debug
            symbolLabel.setBorder(new EmptyBorder(0, symbolHorizontalMargin,0, 0));

            BufferedImage image = ManaSymbols.getSizedManaSymbol(symbol, symbolWidth);
            if (image != null){
                // icon
                symbolLabel.setIcon(new ImageIcon(image));
            }else
            {
                // text
                symbolLabel.setText("{" + symbol + "}");
                //symbolLabel.setOpaque(baseLabel.isOpaque());
                //symbolLabel.setForeground(baseLabel.getForeground());
                //symbolLabel.setBackground(baseLabel.getBackground());
            }

            manaPanel.add(symbolLabel);
        }

        // draw result
        Dimension d = manaPanel.getPreferredSize();
        BufferedImage image = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gg = image.createGraphics();
        manaPanel.paint(gg);
        g.drawImage(image, x, y, null);
         */
        // OLD version with custom draw
        Map<String, BufferedImage> sizedSymbols = manaImages.get(symbolWidth);
        if (manaCost.isEmpty()) {
            return;
        }

        manaCost = manaCost.replace("\\", "");
        manaCost = UI.getDisplayManaCost(manaCost);
        StringTokenizer tok = new StringTokenizer(manaCost, " ");
        while (tok.hasMoreTokens()) {
            String symbol = tok.nextToken();
            Image image = sizedSymbols.get(symbol);
            if (image == null && symbol != null && symbol.length() == 2) {
                String symbol2 = "" + symbol.charAt(1) + symbol.charAt(0);
                image = sizedSymbols.get(symbol2);
            }

            if (image == null) {
                // TEXT draw

                labelRender.setText("{" + symbol + "}");
                labelRender.setForeground(symbolsTextColor);
                labelRender.setSize(symbolWidth, symbolWidth);
                labelRender.setVerticalAlignment(SwingConstants.CENTER);
                labelRender.setHorizontalAlignment(SwingConstants.CENTER);
                //labelRender.setBorder(new LineBorder(new Color(125, 250, 250), 1));

                // fix font size for mana text
                // work for labels WITHOUT borders
                // https://stackoverflow.com/questions/2715118/how-to-change-the-size-of-the-font-of-a-jlabel-to-take-the-maximum-size
                Font labelFont = labelRender.getFont();
                String labelText = "{W}"; //labelRender.getText(); // need same font size for all -- use max symbol ever, not current text
                int stringWidth = labelRender.getFontMetrics(labelFont).stringWidth(labelText);
                int componentWidth = labelRender.getWidth();
                // Find out how much the font can grow in width.
                double widthRatio = (double) componentWidth / (double) stringWidth;
                int newFontSize = (int) (labelFont.getSize() * widthRatio);
                int componentHeight = labelRender.getHeight();
                // Pick a new font size so it will not be larger than the height of label.
                int fontSizeToUse = Math.min(newFontSize, componentHeight);
                // Set the label's font size to the newly determined size.
                labelRender.setFont(new Font(labelFont.getName(), Font.PLAIN + Font.BOLD, fontSizeToUse - 1)); // - for "..." fix in text

                // render component to new position
                // need to copy graphics, overvise it draw at top left corner
                // https://stackoverflow.com/questions/4974268/java-paint-problem
                Graphics2D labelG = (Graphics2D) g.create(x, y, symbolWidth, symbolWidth);
                labelG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                labelG.fillOval(x + 1, y + 1, symbolWidth - 2, symbolWidth - 2);
                labelRender.paint(labelG);
            } else {
                // ICON draw
                g.drawImage(image, x, y, null);
            }
            x += symbolWidth + symbolMarginX;
        }

    }

    public static String getStringManaCost(List<String> manaCost) {
        StringBuilder sb = new StringBuilder();
        for (String s : manaCost) {
            sb.append(s);
        }
        return sb.toString().replace("/", "").replace("{", "").replace("}", " ").trim();
    }

    public enum Type {
        TABLE,
        CHAT,
        DIALOG,
        TOOLTIP,
    }

    private static String filePathToUrl(String path) {
        // convert file path to uri path (for html docs)
        if ((path != null) && (!path.equals(""))) {
            File file = new File(path);
            return file.toURI().toString();
        } else {
            return null;
        }
    }

    public static synchronized String replaceSymbolsWithHTML(String value, Type type) {

        // mana cost to HTML images (urls to files)
        // do not use it for new code - try to suppotr svg render
        int symbolSize;
        switch (type) {
            case TABLE:
                symbolSize = GUISizeHelper.symbolTableSize;
                break;
            case CHAT:
                symbolSize = GUISizeHelper.symbolChatSize;
                break;
            case DIALOG:
                symbolSize = GUISizeHelper.symbolDialogSize;
                break;
            case TOOLTIP:
                symbolSize = GUISizeHelper.symbolTooltipSize;
                break;
            default:
                symbolSize = 11;
                break;
        }

        // auto size
        ResourceSymbolSize needSize = null;
        if (symbolSize <= 15) {
            needSize = ResourceSymbolSize.SMALL;
        } else if (symbolSize <= 25) {
            needSize = ResourceSymbolSize.MEDIUM;
        } else {
            needSize = ResourceSymbolSize.LARGE;
        }

        // replace every {symbol} to <img> link
        // ignore data backup
        String replaced = value
                .replace("{source}", "|source|")
                .replace("{this}", "|this|");

        // not need to add different images (width and height do the work)
        // use best png size (generated on startup) TODO: add reload images after update
        String htmlImagesPath = getResourceSymbolsPath(ResourceSymbolSize.PNG);
        htmlImagesPath = htmlImagesPath
                .replace("$", "@S@"); // paths with $ will rise error, need escape that

        replaced = REPLACE_SYMBOLS_PATTERN.matcher(replaced).replaceAll(
                "<img src='" + filePathToUrl(htmlImagesPath) + "$1$2" + ".png' alt='$1$2' width="
                        + symbolSize + " height=" + symbolSize + '>');

        // ignore data restore
        replaced = replaced
                .replace("|source|", "{source}")
                .replace("|this|", "{this}")
                .replace("@S@", "$");

        return replaced;
    }

    public static String replaceSetCodeWithHTML(String set, String rarity, int size) {
        String _set = set;
        if (setImagesExist.containsKey(_set)) {
            int factor = size / 15 + 1;
            Integer width = setImagesExist.get(_set).width * factor;
            Integer height = setImagesExist.get(_set).height * factor;
            return "<img src='" + filePathToUrl(getResourceSetsPath(ResourceSetSize.SMALL)) + _set + '-' + rarity + ".png' alt='" + rarity + "' height='" + height + "' width='" + width + "' >";
        } else {
            return set;
        }
    }

    public static Image getSetSymbolImage(String set) {
        return getSetSymbolImage(set, "C");
    }

    public static Image getSetSymbolImage(String set, String rarity) {
        Map<String, Image> rarityImages = setImages.get(set);
        if (rarityImages != null) {
            return rarityImages.get(rarity);
        } else {
            return null;
        }
    }

    public static BufferedImage getSizedManaSymbol(String symbol) {
        return getSizedManaSymbol(symbol, GUISizeHelper.symbolDialogSize);
    }

    public static BufferedImage getSizedManaSymbol(String symbol, int size) {
        if (!manaImages.containsKey(size)) {
            loadSymbolImages(size);
        }
        Map<String, BufferedImage> sizedSymbols = manaImages.get(size);
        return sizedSymbols.get(symbol);
    }
}
