package org.mage.card.arcane;

import java.awt.*;
import java.awt.image.BufferedImage;
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
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

import mage.cards.repository.ExpansionRepository;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.GUISizeHelper;
import mage.client.util.ImageHelper;
import mage.client.util.gui.BufferedImageBuilder;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.TranscodingHints;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.util.SVGConstants;
import org.apache.log4j.Logger;
import org.mage.plugins.card.constants.Constants;

public final class ManaSymbols {

    private static final Logger LOGGER = Logger.getLogger(ManaSymbols.class);
    private static final Map<Integer, Map<String, BufferedImage>> manaImages = new HashMap<>();
    private static boolean smallSymbolsFound = false;
    private static boolean mediumSymbolsFound = false;

    private static final Map<String, Map<String, Image>> setImages = new HashMap<>();

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
    private static String cachedPath;
    private static final String[] symbols = new String[]{"0", "1", "10", "11", "12", "15", "16", "2", "3", "4", "5", "6", "7", "8", "9", "B", "BG",
        "BR", "G", "GU", "GW", "R", "RG", "RW", "S", "T", "U", "UB", "UR", "W", "WB", "WU",
        "WP", "UP", "BP", "RP", "GP", "X", "C", "E"};

    public static void loadImages() {
        renameSymbols(getSymbolsPath() + File.separator + "symbols");
        smallSymbolsFound = loadSymbolsImages(15);
        mediumSymbolsFound = loadSymbolsImages(25);

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

            for (String rarityCode : codes) {
                File file = new File(getSymbolsPath() + Constants.RESOURCE_PATH_SET + File.separator + set + '-' + rarityCode + ".jpg");
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

            try {
                File file = new File(getSymbolsPath() + Constants.RESOURCE_PATH_SET_SMALL);
                if (!file.exists()) {
                    file.mkdirs();
                }

                for (String code : codes) {
                    file = new File(getSymbolsPath() + Constants.RESOURCE_PATH_SET_SMALL + File.separator + set + '-' + code + ".png");
                    if (file.exists()) {
                        continue;
                    }
                    file = new File(getSymbolsPath() + Constants.RESOURCE_PATH_SET + File.separator + set + '-' + code + ".jpg");
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
                            File newFile = new File(getSymbolsPath() + Constants.RESOURCE_PATH_SET_SMALL + File.separator + set + '-' + code + ".png");
                            ImageIO.write(resized, "png", newFile);
                        }
                    } catch (Exception e) {
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                }

            } catch (Exception e) {
            }
        }

        File file;
        for (String set : ExpansionRepository.instance.getSetCodes()) {
            file = new File(getSymbolsPath() + Constants.RESOURCE_PATH_SET_SMALL);
            if (!file.exists()) {
                break;
            }
            file = new File(getSymbolsPath() + Constants.RESOURCE_PATH_SET_SMALL + File.separator + set + "-C.png");
            try {
                Image image = UI.getImageIcon(file.getAbsolutePath()).getImage();
                int width = image.getWidth(null);
                int height = image.getHeight(null);
                setImagesExist.put(set, new Dimension(width, height));
            } catch (Exception e) {
            }
        }
    }

    public static BufferedImage loadSVG(File svgFile, int resizeToWidth, int resizeToHeight) throws IOException {

        // load SVG image
        // base loader code: https://stackoverflow.com/questions/11435671/how-to-get-a-buffererimage-from-a-svg
        // resize code: https://vibranttechie.wordpress.com/2015/05/15/svg-loading-to-javafx-stage-and-auto-scaling-when-stage-resize/

        final BufferedImage[] imagePointer = new BufferedImage[1];

        // Rendering hints can't be set programatically, so
        // we override defaults with a temporary stylesheet.
        // These defaults emphasize quality and precision, and
        // are more similar to the defaults of other SVG viewers.
        // SVG documents can still override these defaults.
        String css = "svg {" +
                "shape-rendering: geometricPrecision;" +
                "text-rendering:  geometricPrecision;" +
                "color-rendering: optimizeQuality;" +
                "image-rendering: optimizeQuality;" +
                "}";
        File cssFile = File.createTempFile("batik-default-override-", ".css");
        FileWriter w = new FileWriter(cssFile);
        w.write(css);
        w.close();

        TranscodingHints transcoderHints = new TranscodingHints();

        // resize
        if(resizeToWidth > 0){
            transcoderHints.put(ImageTranscoder.KEY_WIDTH, (float)resizeToWidth); //your image width
        }
        if(resizeToHeight > 0){
            transcoderHints.put(ImageTranscoder.KEY_HEIGHT, (float)resizeToHeight); //your image height
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
        }
        catch (TranscoderException ex) {
            // Requires Java 6
            ex.printStackTrace();
            throw new IOException("Couldn't convert " + svgFile);
        }
        finally {
            cssFile.delete();
        }

        return imagePointer[0];
    }

    private static File getSymbolFileNameAsSVG(String symbol){
        return new File(getSymbolsPath() + Constants.RESOURCE_PATH_MANA_SVG + '/' + symbol + ".svg");
    }

    private static BufferedImage loadSymbolAsSVG(String symbol, int resizeToWidth, int resizeToHeight){

        File sourceFile = getSymbolFileNameAsSVG(symbol);
        return loadSymbolAsSVG(sourceFile, resizeToWidth, resizeToHeight);
    }

    private static BufferedImage loadSymbolAsSVG(File sourceFile, int resizeToWidth, int resizeToHeight){
        try{
            // no need to resize svg (lib already do it on load)
            return loadSVG(sourceFile, resizeToWidth, resizeToHeight);

        } catch (Exception e) {
            LOGGER.error("Can't load svg symbol: " + sourceFile.getPath() + File.separator + sourceFile.getName());
            return null;
        }
    }

    private static File getSymbolFileNameAsGIF(String symbol, int size){
        String resourcePath = Constants.RESOURCE_PATH_MANA_SMALL;
        if (size > 25) {
            resourcePath = Constants.RESOURCE_PATH_MANA_LARGE;
        } else if (size > 15) {
            resourcePath = Constants.RESOURCE_PATH_MANA_MEDIUM;
        }

        return new File(getSymbolsPath() + resourcePath + '/' + symbol + ".gif");
    }

    private static BufferedImage loadSymbolAsGIF(String symbol, int resizeToWidth, int resizeToHeight){
        File file = getSymbolFileNameAsGIF(symbol, resizeToWidth);
        return loadSymbolAsGIF(file, resizeToWidth, resizeToHeight);
    }

    private static BufferedImage loadSymbolAsGIF(File sourceFile, int resizeToWidth, int resizeToHeight){

        BufferedImage image = null;

        try {
            if ((resizeToWidth == 15) || (resizeToWidth == 25)){
                // normal size
                image = ImageIO.read(sourceFile);
            }else{
                // resize size
                image = ImageIO.read(sourceFile);

                if (image != null) {
                    Rectangle r = new Rectangle(resizeToWidth, resizeToHeight);
                    image = ImageHelper.getResizedImage(image, r);
                }
            }
        } catch (IOException e) {
            LOGGER.error("Can't load gif symbol: " + sourceFile.getPath() + File.separator + sourceFile.getName());
            return null;
        }

        return  image;
    }

    private static boolean loadSymbolsImages(int size) {
        // load all symbols to cash
        // priority: SVG -> GIF
        // gif remain for backward compatibility

        boolean fileErrors = false;

        HashMap<String, BufferedImage> sizedSymbols = new HashMap<>();
        for (String symbol : symbols) {

            BufferedImage image = null;
            File file = null;

            // svg
            file = getSymbolFileNameAsSVG(symbol);
            if (file.exists()) {
                image = loadSymbolAsSVG(file, size, size);
            }

            // gif
            if (image == null) {
                LOGGER.warn("SVG symbol can't be load: " + file.getPath() + File.separator + file.getName());

                file = getSymbolFileNameAsGIF(symbol, size);
                if (file.exists()) {
                    image = loadSymbolAsGIF(file, size, size);
                }
            }

            // save
            if (image != null) {
                sizedSymbols.put(symbol, image);
            } else {
                fileErrors = true;
                LOGGER.warn("SVG or GIF symbol can''t be load: " + symbol);
            }
        }

        manaImages.put(size, sizedSymbols);
        return !fileErrors;
    }

    private static void renameSymbols(String path) {
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
            LOGGER.error("Couldn't rename mana symbols!");
        }
    }

    private static String getSymbolsPath() {
        return getSymbolsPath(false);
    }

    private static String getSymbolsPath(boolean forHtmlCode) {
        String useDefault = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_CARD_IMAGES_USE_DEFAULT, "true");
        String path = useDefault.equals("true") ? null : PreferencesDialog.getCachedValue(PreferencesDialog.KEY_CARD_IMAGES_PATH, null);
        if (path == null) {
            if (forHtmlCode) {
                // for html code we need to use double '//' symbols
                // and seems it should be hard coded - as it is not the same as using File.separator
                return "plugins/images/";
            } else {
                return mage.client.constants.Constants.IO.imageBaseDir;
            }
        }
        if (forHtmlCode) {
            if (cachedPath != null) {
                return cachedPath;
            }
            if (path.contains("\\")) {
                cachedPath = path.replaceAll("[\\\\]", "/");
                return cachedPath;
            }
        }
        return path;
    }

    public static void draw(Graphics g, String manaCost, int x, int y, int symbolWidth) {
        if (!manaImages.containsKey(symbolWidth)) {
            loadSymbolsImages(symbolWidth);
        }
        Map<String, BufferedImage> sizedSymbols = manaImages.get(symbolWidth);
        if (manaCost.isEmpty()) {
            return;
        }
        manaCost = manaCost.replace("\\", "");
        manaCost = UI.getDisplayManaCost(manaCost);
        StringTokenizer tok = new StringTokenizer(manaCost, " ");
        while (tok.hasMoreTokens()) {
            String symbol = tok.nextToken();
            // Check and load symbol in the width
            Image image = sizedSymbols.get(symbol);
            if (image == null) {
                //log.error("Symbol not recognized \"" + symbol + "\" in mana cost: " + manaCost);
                continue;
            }
            g.drawImage(image, x, y, null);
            x += symbolWidth;
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

    public static synchronized String replaceSymbolsWithHTML(String value, Type type) {
        value = value.replace("{source}", "|source|");
        value = value.replace("{this}", "|this|");
        String replaced = value;
        boolean symbolFilesFound;
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
        String resourcePath = "small";
        symbolFilesFound = smallSymbolsFound;
        if (symbolSize > 25) {
            resourcePath = "large";
        } else if (symbolSize > 15) {
            resourcePath = "medium";
            symbolFilesFound = mediumSymbolsFound;
        }
        if (symbolFilesFound) {
            replaced = REPLACE_SYMBOLS_PATTERN.matcher(value).replaceAll(
                    "<img src='file:" + getSymbolsPath(true) + "/symbols/" + resourcePath + "/$1$2.gif' alt='$1$2' width=" + symbolSize
                    + " height=" + symbolSize + '>');
        }
        replaced = replaced.replace("|source|", "{source}");
        replaced = replaced.replace("|this|", "{this}");
        return replaced;
    }

    public static String replaceSetCodeWithHTML(String set, String rarity, int size) {
        String _set = set;
        if (setImagesExist.containsKey(_set)) {
            int factor = size / 15 + 1;
            Integer width = setImagesExist.get(_set).width * factor;
            Integer height = setImagesExist.get(_set).height * factor;
            return "<img src='file:" + getSymbolsPath() + "/sets/small/" + _set + '-' + rarity + ".png' alt='" + rarity + "' height='" + height + "' width='" + width + "' >";
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
            loadSymbolsImages(size);
        }
        Map<String, BufferedImage> sizedSymbols = manaImages.get(size);
        return sizedSymbols.get(symbol);
    }
}

