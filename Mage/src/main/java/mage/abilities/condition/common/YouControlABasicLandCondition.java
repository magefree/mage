package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum YouControlABasicLandCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance);

    public static Hint getHint() {
        return hint;
    }

    private static final FilterPermanent filter = new FilterControlledLandPermanent();

    static {
        filter.add(SuperType.BASIC.getPredicate());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getBattlefield().contains(filter, source.getControllerId(), source, game, 1);
    }

    @Override
    public String toString() {
        return "you control a basic land";
    }
}
