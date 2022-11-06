package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.ValueHint;
import mage.game.Game;
import mage.watchers.common.CardsDrawnThisTurnWatcher;

/**
 * Don't forget to add CardsDrawnThisTurnWatcher in card's definition
 *
 * @author TheElk801
 */
public enum CardsDrawnThisTurnDynamicValue implements DynamicValue {
    instance;
    private static final ValueHint hint = new ValueHint("Cards you've drawn this turn", instance);

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        CardsDrawnThisTurnWatcher watcher = game.getState().getWatcher(CardsDrawnThisTurnWatcher.class);
        if (watcher != null) {
            return watcher.getCardsDrawnThisTurn(sourceAbility.getControllerId());
        }
        return 0;
    }

    @Override
    public CardsDrawnThisTurnDynamicValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {

        return "card you've drawn this turn";
    }

    public static ValueHint getHint() {
        return hint;
    }
}

