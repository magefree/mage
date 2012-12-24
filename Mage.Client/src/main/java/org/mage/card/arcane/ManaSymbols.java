package org.mage.card.arcane;

import mage.cards.repository.CardRepository;
import mage.client.util.ImageHelper;
import mage.client.util.gui.BufferedImageBuilder;
import org.apache.log4j.Logger;
import org.mage.plugins.card.constants.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class ManaSymbols {

    private static final Logger log = Logger.getLogger(ManaSymbols.class);
    private static final Map<String, BufferedImage> manaImages = new HashMap<String, BufferedImage>();
    private static final Map<String, Image> manaImagesOriginal = new HashMap<String, Image>();
    private static final Map<String, Image> setImages = new HashMap<String, Image>();
    private static final Map<String, Dimension> setImagesExist = new HashMap<String, Dimension>();
    private static Pattern replaceSymbolsPattern = Pattern.compile("\\{([^}/]*)/?([^}]*)\\}");

    public static void loadImages() {
        String[] symbols = new String[]{"0", "1", "10", "11", "12", "15", "16", "2", "3", "4", "5", "6", "7", "8", "9", "B", "BG",
            "BR", "G", "GU", "GW", "R", "RG", "RW", "S", "T", "U", "UB", "UR", "W", "WB", "WU",
            "WP", "UP", "BP", "RP", "GP", "X" /*, "Y", "Z", "slash"*/};
        for (String symbol : symbols) {
            File file = new File(Constants.RESOURCE_PATH_MANA_MEDIUM + "/" + symbol + ".jpg");
            Rectangle r = new Rectangle(11, 11);
            try {
                Image image = UI.getImageIcon(file.getAbsolutePath()).getImage();
                BufferedImage resized = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
                manaImages.put(symbol, resized);
            } catch (Exception e) {
                log.error("Error for symbol:" + symbol);
            }
            file = new File(Constants.RESOURCE_PATH_MANA_MEDIUM + "/" + symbol + ".jpg");
            try {
                Image image = UI.getImageIcon(file.getAbsolutePath()).getImage();
                manaImagesOriginal.put(symbol, image);
            } catch (Exception e) {
            }
        }
        List<String> setCodes = CardRepository.instance.getSetCodes();
        for (String set : setCodes) {
            String _set = set.equals("CON") ? "CFX" : set;
            File file = new File(Constants.RESOURCE_PATH_SET + _set + "-C.jpg");
            try {
                Image image = UI.getImageIcon(file.getAbsolutePath()).getImage();
                int width = image.getWidth(null);
                if (width > 21) {
                    int h = image.getHeight(null);
                    if (h > 0) {
                        Rectangle r = new Rectangle(21, (int) (h * 21.0f / width));
                        BufferedImage resized = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB), r);
                        setImages.put(set, resized);
                    }
                } else {
                    setImages.put(set, image);
                }
            } catch (Exception e) {
            }
            String[] codes = new String[]{"C", "U", "R", "M"};
            try {
                file = new File(Constants.RESOURCE_PATH_SET_SMALL);
                if (!file.exists()) {
                    file.mkdirs();
                }

                for (String code : codes) {
                    file = new File(Constants.RESOURCE_PATH_SET_SMALL + set + "-" + code + ".png");
                    if (file.exists()) {
                        continue;
                    }
                    file = new File(Constants.RESOURCE_PATH_SET + _set + "-" + code + ".jpg");
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
                            File newFile = new File(Constants.RESOURCE_PATH_SET_SMALL + File.separator + _set + "-" + code + ".png");
                            ImageIO.write(resized, "png", newFile);
                        }
                    } catch (Exception e) {
                        if (file != null && file.exists()) {
                            file.delete();
                        }
                    }
                }

            } catch (Exception e) {
            }
        }

        File file;
        for (String set : CardRepository.instance.getSetCodes()) {
            file = new File(Constants.RESOURCE_PATH_SET_SMALL);
            if (!file.exists()) {
                break;
            }
            file = new File(Constants.RESOURCE_PATH_SET_SMALL + set + "-C.png");
            try {
                Image image = UI.getImageIcon(file.getAbsolutePath()).getImage();
                int width = image.getWidth(null);
                int height = image.getHeight(null);
                setImagesExist.put(set, new Dimension(width, height));
            } catch (Exception e) {
            }
        }
    }

    public static Image getManaSymbolImage(String symbol) {
        return manaImagesOriginal.get(symbol);
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
            Image image = manaImages.get(symbol);
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
        PAY
    }

    public static synchronized String replaceSymbolsWithHTML(String value, Type type) {
        value = value.replace("{source}", "|source|");
        value = value.replace("{this}", "|this|");
        String replaced = value;
        if (!manaImages.isEmpty()) {
            if (type.equals(Type.TOOLTIP)) {
                replaced = replaceSymbolsPattern.matcher(value).replaceAll("<img src='file:plugins/images/symbols/small/$1$2.jpg' alt='$1$2' width=11 height=11>");
            } else if (type.equals(Type.CARD)) {
                value = value.replace("{slash}", "<img src='file:plugins/images/symbols/medium/slash.jpg' alt='slash' width=10 height=13>");
                replaced = replaceSymbolsPattern.matcher(value).replaceAll("<img src='file:plugins/images/symbols/medium/$1$2.jpg' alt='$1$2' width=12 height=12>");
            } else if (type.equals(Type.PAY)) {
                value = value.replace("{slash}", "<img src='file:plugins/images/symbols/medium/slash.jpg' alt='slash' width=10 height=13>");
                replaced = replaceSymbolsPattern.matcher(value).replaceAll("<img src='file:plugins/images/symbols/medium/$1$2.jpg' alt='$1$2' width=15 height=15>");
            }
        }
        replaced = replaced.replace("|source|", "{source}");
        replaced = replaced.replace("|this|", "{this}");
        return replaced;
    }

    public static String replaceSetCodeWithHTML(String set, String rarity) {
        String _set = set;
        if (_set.equals("CON")) {
            _set = "CFX";
        }
        if (setImagesExist.containsKey(_set)) {
            Integer width = setImagesExist.get(_set).width;
            Integer height = setImagesExist.get(_set).height;
            return "<img src='file:plugins/images/sets/small/" + _set + "-" + rarity + ".png' alt='" + rarity + " ' width=" + width + " height=" + height + ">";
        } else {
            return set;
        }
    }

    public static Image getSetSymbolImage(String set) {
        return setImages.get(set);
    }

    public static BufferedImage getManaSymbolImageSmall(String symbol) {
        return manaImages.get(symbol);
    }
}
