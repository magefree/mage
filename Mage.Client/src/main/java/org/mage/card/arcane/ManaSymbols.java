package org.mage.card.arcane;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
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
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import mage.cards.repository.ExpansionRepository;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.GUISizeHelper;
import mage.client.util.ImageHelper;
import mage.client.util.gui.BufferedImageBuilder;
import org.apache.log4j.Logger;
import org.mage.plugins.card.constants.Constants;

public class ManaSymbols {

    private static final Logger LOGGER = Logger.getLogger(ManaSymbols.class);
    private static final Map<Integer, Map<String, BufferedImage>> manaImages = new HashMap<>();
    private static boolean smallSymbolsFound = false;
    private static boolean mediumSymbolsFound = false;

    private static final Map<String, Map<String, Image>> setImages = new HashMap<>();
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
            String[] codes = new String[]{"C", "U", "R", "M"};

            Map<String, Image> rarityImages = new HashMap<>();
            setImages.put(set, rarityImages);

            for (String rarityCode : codes) {
                File file = new File(getSymbolsPath() + Constants.RESOURCE_PATH_SET + set + "-" + rarityCode + ".jpg");
                try {
                    Image image = UI.getImageIcon(file.getAbsolutePath()).getImage();
                    int width = image.getWidth(null);
                    if (width > 21) {
                        int h = image.getHeight(null);
                        if (h > 0) {
                            Rectangle r = new Rectangle(21, (int) (h * 21.0f / width));
                            BufferedImage resized = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
                            rarityImages.put(set, resized);
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
                    file = new File(getSymbolsPath() + Constants.RESOURCE_PATH_SET_SMALL + set + "-" + code + ".png");
                    if (file.exists()) {
                        continue;
                    }
                    file = new File(getSymbolsPath() + Constants.RESOURCE_PATH_SET + set + "-" + code + ".jpg");
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
                            File newFile = new File(getSymbolsPath() + Constants.RESOURCE_PATH_SET_SMALL + File.separator + set + "-" + code + ".png");
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
            file = new File(getSymbolsPath() + Constants.RESOURCE_PATH_SET_SMALL + set + "-C.png");
            try {
                Image image = UI.getImageIcon(file.getAbsolutePath()).getImage();
                int width = image.getWidth(null);
                int height = image.getHeight(null);
                setImagesExist.put(set, new Dimension(width, height));
            } catch (Exception e) {
            }
        }
    }

    private static boolean loadSymbolsImages(int size) {
        boolean fileErrors = false;
        HashMap<String, BufferedImage> sizedSymbols = new HashMap<>();
        String resourcePath = Constants.RESOURCE_PATH_MANA_SMALL;
        if (size > 25) {
            resourcePath = Constants.RESOURCE_PATH_MANA_LARGE;
        } else if (size > 15) {
            resourcePath = Constants.RESOURCE_PATH_MANA_MEDIUM;
        }
        for (String symbol : symbols) {
            File file = new File(getSymbolsPath() + resourcePath + "/" + symbol + ".gif");
            try {

                if (size == 15 || size == 25) {
                    BufferedImage notResized = ImageIO.read(file);
                    sizedSymbols.put(symbol, notResized);
                } else {
                    Rectangle r = new Rectangle(size, size);
                    //Image image = UI.getImageIcon(file.getAbsolutePath()).getImage();
                    BufferedImage image = ImageIO.read(file);
                    //BufferedImage resized = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
                    if (image != null) {
                        BufferedImage resized = ImageHelper.getResizedImage(image, r);
                        sizedSymbols.put(symbol, resized);
                    }
                }
            } catch (IOException e) {
                LOGGER.error("Error for symbol:" + symbol);
                fileErrors = true;
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
        return sb.toString().replace("{", "").replace("}", " ").trim();
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
                    + " height=" + symbolSize + ">");
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
            return "<img src='file:" + getSymbolsPath() + "/sets/small/" + _set + "-" + rarity + ".png' alt='" + rarity + "' height='" + height + "' width='" + width + "' >";
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
