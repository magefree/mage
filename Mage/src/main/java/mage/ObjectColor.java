
package mage;

import mage.constants.ColoredManaSymbol;
import mage.util.Copyable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ObjectColor implements Serializable, Copyable<ObjectColor>, Comparable<ObjectColor> {

    public static final ObjectColor WHITE = new ObjectColor("W");
    public static final ObjectColor BLUE = new ObjectColor("U");
    public static final ObjectColor BLACK = new ObjectColor("B");
    public static final ObjectColor RED = new ObjectColor("R");
    public static final ObjectColor GREEN = new ObjectColor("G");

    public static final ObjectColor COLORLESS = new ObjectColor();

    public static final ObjectColor GOLD = new ObjectColor("O"); // Not multicolored - Sword of Dungeons & Dragons

    private boolean white;
    private boolean blue;
    private boolean black;
    private boolean red;
    private boolean green;

    private boolean gold;

    public ObjectColor() {
    }

    public ObjectColor(String color) {
        for (int i = 0; i < color.length(); i++) {
            switch (color.charAt(i)) {
                case 'W':
                    white = true;
                    break;
                case 'U':
                    blue = true;
                    break;
                case 'B':
                    black = true;
                    break;
                case 'R':
                    red = true;
                    break;
                case 'G':
                    green = true;
                    break;

                case 'O':
                    gold = true;
                    break;
            }
        }
    }

    public ObjectColor(ObjectColor color) {
        white = color.white;
        blue = color.blue;
        black = color.black;
        red = color.red;
        green = color.green;

        gold = color.gold;
    }

    /**
     * Returns a new color which contains all of the colors of this ObjectColor
     * in addition to all of the colors of the other ObjectColor.
     *
     * @param other The other ObjectColor to union with
     * @return A new color which is the union of this and other
     */
    public ObjectColor union(ObjectColor other) {
        ObjectColor newColor = new ObjectColor();
        newColor.white = white || other.white;
        newColor.blue = blue || other.blue;
        newColor.black = black || other.black;
        newColor.red = red || other.red;
        newColor.green = green || other.green;

        newColor.gold = gold || other.gold;
        return newColor;
    }

    /**
     * Returns a new color which contains the intersection of the colors of this
     * ObjectColor and the other ObjectColor.
     *
     * @param other The other ObjectColor to intersect with
     * @return A new color which is the intersection of this and other
     */
    public ObjectColor intersection(ObjectColor other) {
        ObjectColor newColor = new ObjectColor();
        newColor.white = white && other.white;
        newColor.blue = blue && other.blue;
        newColor.black = black && other.black;
        newColor.red = red && other.red;
        newColor.green = green && other.green;

        newColor.gold = gold && other.gold;
        return newColor;
    }

    public int getColorCount() {
        int count = 0;
        if (white) {
            count++;
        }
        if (blue) {
            count++;
        }
        if (black) {
            count++;
        }
        if (green) {
            count++;
        }
        if (red) {
            count++;
        }

        if (gold) {
            count++;
        }
        return count;
    }

    public List<ObjectColor> getColors() {
        List<ObjectColor> colors = new ArrayList<>();
        int firstColor = 5000;
        int secondColor = -1;

        if (this.isWhite()) {
            firstColor = 1;
            secondColor = 1;
        }
        if (this.isBlue()) {
            firstColor = Math.min(firstColor, 2);
            secondColor = Math.max(secondColor, 2);
        }
        if (this.isBlack()) {
            firstColor = Math.min(firstColor, 3);
            secondColor = Math.max(secondColor, 3);
        }
        if (this.isRed()) {
            firstColor = Math.min(firstColor, 4);
            secondColor = Math.max(secondColor, 4);
        }
        if (this.isGreen()) {
            firstColor = Math.min(firstColor, 5);
            secondColor = Math.max(secondColor, 5);
        }

        if (this.isWhite()) {
            colors.add(ObjectColor.WHITE);
        }
        if (this.isBlue()) {
            colors.add(ObjectColor.BLUE);
        }
        if (this.isBlack()) {
            colors.add(ObjectColor.BLACK);
        }
        if (this.isRed()) {
            colors.add(ObjectColor.RED);
        }
        if (this.isGreen()) {
            colors.add(ObjectColor.GREEN);
        }

        if (colors.size() >= 2 && secondColor - firstColor >= 3) {
            Collections.swap(colors, 0, 1);
        }

        if (this.isGold()) {
            colors.add(ObjectColor.GOLD);
        }
        return colors;
    }

    public void setColor(ObjectColor color) {
        this.setBlack(color != null && color.isBlack());
        this.setBlue(color != null && color.isBlue());
        this.setGreen(color != null && color.isGreen());
        this.setRed(color != null && color.isRed());
        this.setWhite(color != null && color.isWhite());

        this.setGold(color != null && color.isGold());
    }

    public void addColor(ObjectColor color) {
        if (color.isWhite()) {
            setWhite(true);
        }
        if (color.isBlue()) {
            setBlue(true);
        }
        if (color.isBlack()) {
            setBlack(true);
        }
        if (color.isRed()) {
            setRed(true);
        }
        if (color.isGreen()) {
            setGreen(true);
        }

        if (color.isGold()) {
            setGold(true);
        }
    }

    public boolean isColorless() {
        return !(hasColor());
    }

    public boolean hasColor() {
        return white || blue || black || red || green
                || gold;
    }

    public boolean isMulticolored() {
        if (isColorless()) {
            return false;
        }
        if (white && (blue || black || red || green
                || gold)) {
            return true;
        }
        if (blue && (black || red || green
                || gold)) {
            return true;
        }
        if (black && (red || green
                || gold)) {
            return true;
        }
        if (red && (green
                || gold)) {
            return true;
        }
        return green
                && gold;
    }

    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }

    public boolean isBlue() {
        return blue;
    }

    public void setBlue(boolean blue) {
        this.blue = blue;
    }

    public boolean isBlack() {
        return black;
    }

    public void setBlack(boolean black) {
        this.black = black;
    }

    public boolean isRed() {
        return red;
    }

    public void setRed(boolean red) {
        this.red = red;
    }

    public boolean isGreen() {
        return green;
    }

    public void setGreen(boolean green) {
        this.green = green;
    }

    public boolean isGold() {
        return gold;
    }

    public void setGold(boolean gold) {
        this.gold = gold;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(5);
        if (white) {
            sb.append('W');
        }
        if (blue) {
            sb.append('U');
        }
        if (black) {
            sb.append('B');
        }
        if (red) {
            sb.append('R');
        }
        if (green) {
            sb.append('G');
        }

        if (gold) {
            sb.append('O');
        }
        return sb.toString();
    }

    public String getDescription() {
        if (getColorCount() > 1) {
            return "multicolored";
        } else {
            if (white) {
                return "white";
            }
            if (blue) {
                return "blue";
            }
            if (black) {
                return "black";
            }
            if (red) {
                return "red";
            }
            if (green) {
                return "green";
            }

            if (gold) {
                return "gold";
            }
        }
        return "colorless";
    }

    @Override
    public boolean equals(Object color) {
        if (this == color) {
            return true;
        }
        if (!(color instanceof ObjectColor)) {
            return false;
        }
        ObjectColor test = (ObjectColor) color;
        if (test.white != this.white) {
            return false;
        }
        if (test.blue != this.blue) {
            return false;
        }
        if (test.black != this.black) {
            return false;
        }
        if (test.red != this.red) {
            return false;
        }
        if (test.green != this.green) {
            return false;
        }
        if (test.gold != this.gold) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (this.white ? 1 : 0);
        hash = 23 * hash + (this.blue ? 1 : 0);
        hash = 23 * hash + (this.black ? 1 : 0);
        hash = 23 * hash + (this.red ? 1 : 0);
        hash = 23 * hash + (this.green ? 1 : 0);

        hash = 23 * hash + (this.gold ? 1 : 0);
        return hash;
    }

    public boolean contains(ObjectColor color) {
        if (Objects.equals(this, color)) {
            return true;
        }
        if (color.white && this.white) {
            return true;
        }
        if (color.blue && this.blue) {
            return true;
        }
        if (color.black && this.black) {
            return true;
        }
        if (color.red && this.red) {
            return true;
        }
        if (color.green && this.green) {
            return true;
        }

        if (color.gold && this.gold) {
            return true;
        }
        return false;
    }

    public boolean shares(ObjectColor color) {
        // 105.4. [...] “Multicolored” is not a color. Neither is “colorless.”
        return !color.isColorless()
                && (color.white && white || color.blue && blue || color.black && black
                || color.red && red || color.green && green
                || color.gold && gold);
    }

    @Override
    public ObjectColor copy() {
        return new ObjectColor(this);
    }

    @Override
    public int compareTo(ObjectColor o) {
        int o1 = 0;
        int o2 = 0;

        if (this.isMulticolored()) {
            o1 = 7;
        } else if (this.isColorless()) {
            o1 = 0;
        } else if (this.isBlack()) {
            o1 = 1;
        } else if (this.isBlue()) {
            o1 = 2;
        } else if (this.isGreen()) {
            o1 = 3;
        } else if (this.isRed()) {
            o1 = 4;
        } else if (this.isWhite()) {
            o1 = 5;
        } else if (this.isGold()) {
            o1 = 6;
        }

        if (o.isMulticolored()) {
            o2 = 7;
        } else if (o.isColorless()) {
            o2 = 0;
        } else if (o.isBlack()) {
            o2 = 1;
        } else if (o.isBlue()) {
            o2 = 2;
        } else if (o.isGreen()) {
            o2 = 3;
        } else if (o.isRed()) {
            o2 = 4;
        } else if (o.isWhite()) {
            o2 = 5;
        } else if (o.isGold()) {
            o2 = 6;
        }

        return o1 - o2;
    }

    /**
     * Returns a ColoredManaSymbol of a color included If multicolor only one
     * symbol is returned
     *
     * @return null or
     */
    public ColoredManaSymbol getOneColoredManaSymbol() {

        if (isMulticolored()) {
            throw new IllegalStateException("Found multicolor object, but was waiting for simple color.");
        }

        if (isBlack()) {
            return ColoredManaSymbol.B;
        }
        if (isRed()) {
            return ColoredManaSymbol.R;
        }
        if (isBlue()) {
            return ColoredManaSymbol.U;
        }
        if (isGreen()) {
            return ColoredManaSymbol.G;
        }
        if (isWhite()) {
            return ColoredManaSymbol.W;
        }

        if (isGold()) {
            return ColoredManaSymbol.O;
        }
        return null;
    }

    public static List<ObjectColor> getAllColors() {
        List<ObjectColor> colors = new ArrayList<>();
        colors.add(ObjectColor.WHITE);
        colors.add(ObjectColor.BLUE);
        colors.add(ObjectColor.BLACK);
        colors.add(ObjectColor.RED);
        colors.add(ObjectColor.GREEN);

        colors.add(ObjectColor.GOLD);
        return colors;
    }
}
