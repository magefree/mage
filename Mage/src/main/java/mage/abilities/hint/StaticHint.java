package mage.abilities.hint;

import mage.abilities.Ability;
import mage.game.Game;

import java.awt.*;

/**
 * @author JayDi85
 */
public class StaticHint implements Hint {

    private String text;

    public StaticHint(String text) {
        this(text, null);
    }

    public StaticHint(String text, Color color) {
        this.text = HintUtils.prepareText(text, color);
    }

    private StaticHint(final StaticHint hint) {
        this.text = hint.text;
    }

    @Override
    public String getText(Game game, Ability ability) {
        return text;
    }

    @Override
    public Hint copy() {
        return new StaticHint(this);
    }
}
