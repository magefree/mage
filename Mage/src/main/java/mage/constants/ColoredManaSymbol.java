package mage.constants;

/**
 *
 * @author North, JayDi85
 */
public enum ColoredManaSymbol {
    W("W", "white", "<font color='gray'>white</font>"), // white can't be white on white background, need gray
    U("U", "blue", "<font color='blue'>blue</font>"),
    B("B", "black", "<font color='black'>black</font>"),
    R("R", "red", "<font color='red'>red</font>"),
    G("G", "green", "<font color='green'>green</font>"),
    O("O", "gold", "<font color='gold'>gold</font>");

    private final String text;
    private final String colorName;
    private final String colorHtmlName;

    ColoredManaSymbol(String text, String colorName, String colorHtmlName) {
        this.text = text;
        this.colorName = colorName;
        this.colorHtmlName = colorHtmlName;
    }

    @Override
    public String toString() {
        return text;
    }

    public String getColorName() {
        return colorName;
    }

    public String getColorHtmlName() {
        return colorHtmlName;
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
                
            case 'O':
                return O;
        }
        return null;
    }

}
