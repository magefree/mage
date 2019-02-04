package mage.abilities.hint;

import mage.game.Game;

import java.awt.*;
import java.util.UUID;

/**
 * @author JayDi85
 */
public class StaticHint implements Hint {

    private String text;

    public StaticHint(String text) {
        this(text, null);
    }

    public StaticHint(String text, Color color) {
        if (color != null) {
            String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getGreen());
            this.text = String.format("<font color=%s>%s</font>", hex, text);
        } else {
            this.text = text;
        }
    }

    public String getText(Game game, UUID sourceId) {
        return text;
    }

    private StaticHint(final StaticHint hint) {
        this.text = hint.text;
    }

    @Override
    public Hint copy() {
        return new StaticHint(this);
    }
}
