package org.mage.card.arcane;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
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
    private static final Map<String, BufferedImage> MANA_IMAGES = new HashMap<>();
    private static final Map<String, Image> SET_IMAGES = new HashMap<>();
    private static final Map<String, Dimension> SET_IMAGES_EXIST = new HashMap<>();
    private static final Pattern REPLACE_SYMBOLS_PATTERN = Pattern.compile("\\{([^}/]*)/?([^}]*)\\}");
    private static String cachedPath;

    public static void loadImages() {
        String[] symbols = new String[]{"0", "1", "10", "11", "12", "15", "16", "2", "3", "4", "5", "6", "7", "8", "9", "B", "BG",
            "BR", "G", "GU", "GW", "R", "RG", "RW", "S", "T", "U", "UB", "UR", "W", "WB", "WU",
            "WP", "UP", "BP", "RP", "GP", "X", "C" /*, "Y", "Z", "slash"*/};

        MANA_IMAGES.clear();
        SET_IMAGES.clear();
        SET_IMAGES_EXIST.clear();

        for (String symbol : symbols) {
            String resourcePath = Constants.RESOURCE_PATH_MANA_SMALL;
            switch (GUISizeHelper.basicSymbolSize) {
                case "medium":
                    resourcePath = Constants.RESOURCE_PATH_MANA_SMALL;
                    break;
                case "large":
                    resourcePath = Constants.RESOURCE_PATH_MANA_LARGE;
                    break;
            }
            File file = new File(getSymbolsPath() + resourcePath + "/" + symbol + ".jpg");
            try {
                if (GUISizeHelper.symbolPaySize != 15) {
                    BufferedImage notResized = ImageIO.read(file);
                    MANA_IMAGES.put(symbol, notResized);
                } else {
                    Rectangle r = new Rectangle(GUISizeHelper.symbolPaySize, GUISizeHelper.symbolPaySize);
                    Image image = UI.getImageIcon(file.getAbsolutePath()).getImage();
                    BufferedImage resized = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
                    MANA_IMAGES.put(symbol, resized);
                }
            } catch (Exception e) {
                LOGGER.error("Error for symbol:" + symbol);
            }
        }

        List<String> setCodes = ExpansionRepository.instance.getSetCodes();
        if (setCodes == null) {
            // the cards db file is probaly not included in the client. It will be created after the first connect to a server.
            LOGGER.warn("No db information for sets found. Connect to a server to create database file on client side. Then try to restart the client.");
            return;
        }
        for (String set : setCodes) {
            File file = new File(getSymbolsPath() + Constants.RESOURCE_PATH_SET + set + "-C.jpg");
            try {
                Image image = UI.getImageIcon(file.getAbsolutePath()).getImage();
                int width = image.getWidth(null);
                if (width > 21) {
                    int h = image.getHeight(null);
                    if (h > 0) {
                        Rectangle r = new Rectangle(21, (int) (h * 21.0f / width));
                        BufferedImage resized = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
                        SET_IMAGES.put(set, resized);
                    }
                } else {
                    SET_IMAGES.put(set, image);
                }
            } catch (Exception e) {
            }
            String[] codes = new String[]{"C", "U", "R", "M"};
            try {
                file = new File(getSymbolsPath() + Constants.RESOURCE_PATH_SET_SMALL);
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
                SET_IMAGES_EXIST.put(set, new Dimension(width, height));
            } catch (Exception e) {
            }
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

    public static void draw(Graphics g, String manaCost, int x, int y) {
        if (manaCost.length() == 0) {
            return;
        }
        manaCost = manaCost.replace("\\", "");
        manaCost = UI.getDisplayManaCost(manaCost);
        StringTokenizer tok = new StringTokenizer(manaCost, " ");
        while (tok.hasMoreTokens()) {
            String symbol = tok.nextToken().substring(0);
            Image image = MANA_IMAGES.get(symbol);
            if (image == null) {
                //log.error("Symbol not recognized \"" + symbol + "\" in mana cost: " + manaCost);
                continue;
            }
            g.drawImage(image, x, y, null);
            x += symbol.length() > 2 ? 10 : 12; // slash.png is only 10 pixels wide.
        }
    }

    public static String getStringManaCost(List<String> manaCost) {
        StringBuilder sb = new StringBuilder();
        for (String s : manaCost) {
            sb.append(s);
        }
        return sb.toString().replace("{", "").replace("}", " ").trim();
    }

    public static int getWidth(String manaCost) {
        int width = 0;
        manaCost = manaCost.replace("\\", "");
        StringTokenizer tok = new StringTokenizer(manaCost, " ");
        while (tok.hasMoreTokens()) {
            String symbol = tok.nextToken().substring(0);
            width += symbol.length() > 2 ? 10 : 12; // slash.png is only 10 pixels wide.
        }
        return width;
    }

    public enum Type {
        CARD,
        TOOLTIP,
        EDITOR,
        PAY
    }

    public static synchronized String replaceSymbolsWithHTML(String value, Type type) {
        value = value.replace("{source}", "|source|");
        value = value.replace("{this}", "|this|");
        String replaced = value;

        if (!MANA_IMAGES.isEmpty()) {
            int symbolSize;
            switch (type) {
                case TOOLTIP:
                    symbolSize = GUISizeHelper.symbolTooltipSize;
                    break;
                case CARD:
                    symbolSize = GUISizeHelper.symbolCardSize;
                    break;
                case PAY:
                    symbolSize = GUISizeHelper.symbolPaySize;
                    break;
                case EDITOR:
                    symbolSize = GUISizeHelper.symbolEditorSize;
                    break;
                default:
                    symbolSize = 11;
                    break;
            }
            replaced = REPLACE_SYMBOLS_PATTERN.matcher(value).replaceAll("<img src='file:" + getSymbolsPath(true)
                    + "/symbols/" + GUISizeHelper.basicSymbolSize + "/$1$2.jpg' alt='$1$2' width="
                    + symbolSize + " height=" + symbolSize + ">");

        }
        replaced = replaced.replace("|source|", "{source}");
        replaced = replaced.replace("|this|", "{this}");
        return replaced;
    }

    public static String replaceSetCodeWithHTML(String set, String rarity) {
        String _set = set;
        if (SET_IMAGES_EXIST.containsKey(_set)) {
            Integer width = SET_IMAGES_EXIST.get(_set).width;
            Integer height = SET_IMAGES_EXIST.get(_set).height;
            return "<img src='file:" + getSymbolsPath() + "/sets/small/" + _set + "-" + rarity + ".png' alt='" + rarity + " ' width=" + width + " height=" + height + ">";
        } else {
            return set;
        }
    }

    public static Image getSetSymbolImage(String set) {
        return SET_IMAGES.get(set);
    }

    public static BufferedImage getSizedManaSymbol(String symbol) {
        return MANA_IMAGES.get(symbol);
    }
}
