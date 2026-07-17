package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum OpponentsControlEightLandsCondition implements Condition {
    instance;
    private static final FilterPermanent filter = new FilterLandPermanent();

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    private static final Hint hint = new ValueHint(
            "Lands your opponents control", new PermanentsOnBattlefieldCount(filter)
    );

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getBattlefield().contains(filter, source.getControllerId(), source, game, 8);
    }

    @Override
    public String toString() {
        return "your opponents control eight or more lands";
    }
}
