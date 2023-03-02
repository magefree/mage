package org.mage.card.arcane;

import mage.abilities.hint.HintUtils;
import mage.cards.repository.CardInfo;
import mage.cards.repository.ExpansionRepository;
import mage.client.MageFrame;
import mage.client.constants.Constants;
import mage.client.constants.Constants.ResourceSetSize;
import mage.client.constants.Constants.ResourceSymbolSize;
import mage.client.util.GUISizeHelper;
import mage.client.util.ImageHelper;
import mage.client.util.gui.BufferedImageBuilder;
import mage.client.util.gui.GuiDisplayUtil;
import mage.constants.Rarity;
import org.apache.log4j.Logger;
import org.mage.plugins.card.utils.CardImageUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public final class ManaSymbols {

    private static final Logger logger = Logger.getLogger(ManaSymbols.class);

    private static final String CSS_FILE_NAME = "mana-svg-settings.css";
    private static final String CSS_ADDITIONAL_SETTINGS = "";

    private static final Map<Integer, Map<String, BufferedImage>> manaImages = new HashMap<>();
    private static final Map<String, Map<Rarity, Image>> setImages = new ConcurrentHashMap<>();

    private static final Set<String> onlyMythics = new HashSet<>();
    private static final Set<String> withoutSymbols = new HashSet<>();

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

        // Magic Player Reward sets
        withoutSymbols.add("MPR");
        withoutSymbols.add("P03");
        withoutSymbols.add("P04");
        withoutSymbols.add("P05");
        withoutSymbols.add("P06");
        withoutSymbols.add("P07");
        withoutSymbols.add("P08");
        withoutSymbols.add("P09");
        withoutSymbols.add("P10");
        withoutSymbols.add("P11");

    }

    private static final Map<String, Dimension> setImagesExist = new HashMap<>();
    private static final Pattern REPLACE_SYMBOLS_PATTERN = Pattern.compile("\\{([^}/]*)/?([^}/]*)/?([^}/]*)\\}");

    private static final String[] symbols = new String[]{
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "B", "BG", "BR", "BP", "2B",
            "G", "GU", "GW", "GP", "2G",
            "R", "RG", "RW", "RP", "2R",
            "S", "T", "Q",
            "U", "UB", "UR", "UP", "2U",
            "W", "WB", "WU", "WP", "2W",
            "X", "C", "E",
            "BGP", "BRP", "GUP", "GWP", "RGP", "RWP", "UBP", "URP", "WBP", "WUP"};

    private static final JLabel labelRender = new JLabel(); // render mana text

    public static void loadImages() {
        logger.info("Loading symbols...");

        // TODO: delete files rename jpg->gif (it was for backward compatibility for one of the old version?)
        renameSymbols(getResourceSymbolsPath(ResourceSymbolSize.SMALL));
        renameSymbols(getResourceSymbolsPath(ResourceSymbolSize.MEDIUM));
        renameSymbols(getResourceSymbolsPath(ResourceSymbolSize.LARGE));
        //renameSymbols(getSymbolsPath(ResourceSymbolSize.SVG)); // not need
        // TODO: remove medium sets files to "medium" folder like symbols above?

        // prepare svg's css settings
        SvgUtils.prepareCss(CSS_FILE_NAME, CSS_ADDITIONAL_SETTINGS, true);

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
                    logger.warn("Can't generate png image for symbol:" + symbol);
                }
            }
        }

        // preload set images
        java.util.List<String> setCodes = ExpansionRepository.instance.getSetCodes();
        if (setCodes == null) {
            // the cards db file is probaly not included in the client. It will be created after the first connect to a server.
            logger.warn("No db information for sets found. Connect to a server to create database file on client side. Then try to restart the client.");
            return;
        }
        for (String set : setCodes) {

            if (withoutSymbols.contains(set)) {
                continue;
            }

            Set<Rarity> codes;
            if (onlyMythics.contains(set)) {
                codes = EnumSet.of(Rarity.MYTHIC);
            } else {
                codes = EnumSet.of(Rarity.COMMON, Rarity.UNCOMMON, Rarity.RARE, Rarity.MYTHIC);
            }

            Map<Rarity, Image> rarityImages = new EnumMap<>(Rarity.class);
            setImages.put(set, rarityImages);

            // load medium size
            for (Rarity rarityCode : codes) {
                File file = new File(getResourceSetsPath(ResourceSetSize.MEDIUM) + set + '-' + rarityCode.getCode() + ".jpg");
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
                for (Rarity code : codes) {
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

    public static File getSymbolFileNameAsSVG(String symbol) {
        return new File(getResourceSymbolsPath(ResourceSymbolSize.SVG) + symbol + ".svg");
    }

    private static BufferedImage loadSymbolAsSVG(InputStream svgFile, String svgInfo, int resizeToWidth, int resizeToHeight) {
        try {
            // no need to resize svg (lib already do it on load)
            return SvgUtils.loadSVG(svgFile, svgInfo, CSS_FILE_NAME, CSS_ADDITIONAL_SETTINGS, resizeToWidth, resizeToHeight, true);
        } catch (Exception e) {
            logger.error("Can't load svg symbol: " + svgInfo + " , reason: " + e.getMessage());
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
            logger.error("Can't load gif symbol: " + sourceFile.getPath());
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
            if (SvgUtils.haveSvgSupport()) {
                file = getSymbolFileNameAsSVG(symbol);
                if (file.exists()) {
                    try {
                        InputStream fileStream = new FileInputStream(file);
                        image = loadSymbolAsSVG(fileStream, file.getPath(), size, size);
                    } catch (FileNotFoundException e) {
                        // it's ok to hide error
                    }
                }
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
            logger.warn("Symbols can't be load for size " + size + ": " + errorInfo);
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
            logger.error("Couldn't rename mana symbols on " + path, e);
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
        draw(g, manaCost, x, y, symbolWidth, ModernCardRenderer.MANA_ICONS_TEXT_COLOR, 0);
    }

    public static void draw(Graphics g, String manaCost, int x, int y, int symbolWidth, Color symbolsTextColor, int symbolMarginX) {
        if (!manaImages.containsKey(symbolWidth)) {
            loadSymbolImages(symbolWidth);
        }

        // TODO: replace with jlabel render (look at table renderer)?

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
                String sampleAutoFontText = "{W}"; // need same font size for all -- use max symbol ever, not current text
                if (symbol.equals(CardInfo.SPLIT_MANA_SEPARATOR_SHORT)) {
                    labelRender.setText(CardInfo.SPLIT_MANA_SEPARATOR_RENDER);
                    sampleAutoFontText = CardInfo.SPLIT_MANA_SEPARATOR_RENDER; // separator must be big
                } else {
                    labelRender.setText("{" + symbol + "}");
                }
                labelRender.setSize(symbolWidth, symbolWidth);
                labelRender.setVerticalAlignment(SwingConstants.CENTER);
                labelRender.setForeground(symbolsTextColor);
                labelRender.setHorizontalAlignment(SwingConstants.CENTER);

                // fix font size for mana text
                // work for labels WITHOUT borders
                // https://stackoverflow.com/questions/2715118/how-to-change-the-size-of-the-font-of-a-jlabel-to-take-the-maximum-size
                Font labelFont = labelRender.getFont();
                int stringWidth = labelRender.getFontMetrics(labelFont).stringWidth(sampleAutoFontText);
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

    public static String getClearManaCost(java.util.List<String> manaCost) {
        StringBuilder sb = new StringBuilder();
        for (String s : manaCost) {
            sb.append(s);
        }
        return getClearManaCost(sb.toString());
    }

    public static String getClearManaCost(String manaCost) {
        return manaCost
                .replace("/", "")
                .replace("{", "")
                .replace("}", " ") // each mana symbol splits by space
                .trim();
    }

    public static int getClearManaSymbolsCount(String manaCost) {
        // find mana symbols amount in the cost
        if (manaCost.isEmpty()) {
            return 0;
        } else {
            String clearManaCost = getClearManaCost(manaCost);
            String checkManaCost = clearManaCost.replace(" ", "");
            return clearManaCost.length() - checkManaCost.length() + 1;
        }
    }



    public enum Type {
        TABLE,
        CHAT,
        DIALOG,
        TOOLTIP,
        CARD_ICON_HINT
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

    /**
     * Replace images/icons code by real html links. Uses in many places.
     *
     * @param value
     * @param type
     * @return
     */
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
            case CARD_ICON_HINT:
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
                .replace("{this}", "|this|");

        // not need to add different images (width and height do the work)
        // use best png size (generated on startup) TODO: add reload images after update
        String htmlImagesPath = getResourceSymbolsPath(ResourceSymbolSize.PNG);
        htmlImagesPath = htmlImagesPath
                .replace("$", "@S@"); // paths with $ will rise error, need escape that

        replaced = replaced.replace(CardInfo.SPLIT_MANA_SEPARATOR_FULL, CardInfo.SPLIT_MANA_SEPARATOR_RENDER);
        replaced = REPLACE_SYMBOLS_PATTERN.matcher(replaced).replaceAll(
                "<img src='" + filePathToUrl(htmlImagesPath) + "$1$2$3" + ".png' alt='$1$2$3' width="
                        + symbolSize + " height=" + symbolSize + '>');

        // replace hint icons
        if (replaced.contains(HintUtils.HINT_ICON_GOOD)) {
            replaced = replaced.replace(HintUtils.HINT_ICON_GOOD, GuiDisplayUtil.getHintIconHtml("good", symbolSize) + "&nbsp;");
        }
        if (replaced.contains(HintUtils.HINT_ICON_BAD)) {
            replaced = replaced.replace(HintUtils.HINT_ICON_BAD, GuiDisplayUtil.getHintIconHtml("bad", symbolSize) + "&nbsp;");
        }
        if (replaced.contains(HintUtils.HINT_ICON_RESTRICT)) {
            replaced = replaced.replace(HintUtils.HINT_ICON_RESTRICT, GuiDisplayUtil.getHintIconHtml("restrict", symbolSize) + "&nbsp;");
        }
        if (replaced.contains(HintUtils.HINT_ICON_REQUIRE)) {
            replaced = replaced.replace(HintUtils.HINT_ICON_REQUIRE, GuiDisplayUtil.getHintIconHtml("require", symbolSize) + "&nbsp;");
        }
        if (replaced.contains(HintUtils.HINT_ICON_DUNGEON_ROOM_CURRENT)) {
            replaced = replaced.replace(HintUtils.HINT_ICON_DUNGEON_ROOM_CURRENT, GuiDisplayUtil.getHintIconHtml("arrow-right-square-fill-green", symbolSize) + "&nbsp;");
        }
        if (replaced.contains(HintUtils.HINT_ICON_DUNGEON_ROOM_NEXT)) {
            replaced = replaced.replace(HintUtils.HINT_ICON_DUNGEON_ROOM_NEXT, GuiDisplayUtil.getHintIconHtml("arrow-down-right-square fill-yellow", symbolSize) + "&nbsp;");
        }

        // ignored data restore
        replaced = replaced
                .replace("|this|", "{this}")
                .replace("@S@", "$");

        return replaced;
    }

    public static String replaceSetCodeWithHTML(String set, String rarity, int size) {
        if (setImagesExist.containsKey(set)) {
            int factor = size / 15 + 1;
            Integer width = setImagesExist.get(set).width * factor;
            Integer height = setImagesExist.get(set).height * factor;
            return "<img src='" + filePathToUrl(getResourceSetsPath(ResourceSetSize.SMALL)) + set + '-' + rarity + ".png' alt='" + rarity + "' height='" + height + "' width='" + width + "' >";
        } else {
            return set;
        }
    }

    public static Image getSetSymbolImage(String set) {
        return getSetSymbolImage(set, Rarity.COMMON);
    }

    public static Image getSetSymbolImage(String set, Rarity rarity) {
        Map<Rarity, Image> rarityImages = setImages.get(set);
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
