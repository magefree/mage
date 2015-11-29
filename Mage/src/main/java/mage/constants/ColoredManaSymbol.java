package mage.constants;

/**
 *
 * @author North
 */
public enum ColoredManaSymbol {
    W("W","white"), U("U","blue"), B("B","black"), R("R","red"), G("G","green");

    private final String text;
    private final String colorName;

    ColoredManaSymbol(String text, String colorName) {
        this.text = text;
        this.colorName = colorName;
    }


    @Override
    public String toString() {
        return text;
    }

    public String getColorName() {
        return colorName;
    }

    
    public static ColoredManaSymbol lookup(char c) {
        switch (c) {
            case 'W':
                return W;
            case 'R':
                return R;
            case 'G':
                return G;
            case 'B':
                return B;
            case 'U':
                return U;
        }
        return null;
    }

}
