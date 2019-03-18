
package mage.view;

import java.io.Serializable;
import mage.ConditionalMana;
import mage.players.ManaPool;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ManaPoolView implements Serializable {

    private static final long serialVersionUID = 1L;

    private int red;
    private int green;
    private int blue;
    private int white;
    private int black;
    private int colorless;

    public ManaPoolView(ManaPool pool) {
        this.red = pool.getRed();
        this.green = pool.getGreen();
        this.blue = pool.getBlue();
        this.white = pool.getWhite();
        this.black = pool.getBlack();
        this.colorless = pool.getColorless();
        for (ConditionalMana mana : pool.getConditionalMana()) {
            this.red += mana.getRed();
            this.green += mana.getGreen();
            this.blue += mana.getBlue();
            this.white += mana.getWhite();
            this.black += mana.getBlack();
            this.colorless += mana.getColorless();
        }
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public int getWhite() {
        return white;
    }

    public int getBlack() {
        return black;
    }

    public int getColorless() {
        return colorless;
    }

}
