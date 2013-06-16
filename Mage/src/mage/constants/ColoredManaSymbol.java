package mage.constants;

/**
 *
 * @author North
 */
public enum ColoredManaSymbol {
    W("W"), U("U"), B("B"), R("R"), G("G");

    private String text;

    ColoredManaSymbol(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
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
