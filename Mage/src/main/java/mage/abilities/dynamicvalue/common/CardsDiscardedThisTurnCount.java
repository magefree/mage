package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.game.Game;
import mage.watchers.common.DiscardedCardWatcher;

/**
 * Number of cards you've discarded this turn, needs DiscardedCardWatcher
 *
 * @author JayDi85
 */
public enum CardsDiscardedThisTurnCount implements DynamicValue {

    PLURAL(true),
    SINGULAR(false);

    private static final Hint hint = new ValueHint("Cards you've discarded this turn", SINGULAR);

    private final boolean plural;

    CardsDiscardedThisTurnCount(boolean plural) {
        this.plural = plural;
    }

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return DiscardedCardWatcher.getDiscarded(sourceAbility.getControllerId(), game);
    }

    @Override
    public CardsDiscardedThisTurnCount copy() {
        return this;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "card" + (plural ? "s" : "") + " you've discarded this turn";
    }
}
