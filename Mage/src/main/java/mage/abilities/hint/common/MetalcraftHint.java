package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.game.Game;

/**
 * @author JayDi85
 */
public enum MetalcraftHint implements Hint {

    instance;
    private static final ConditionHint hint = new ConditionHint(MetalcraftCondition.instance, "You control three or more artifacts");
    private static final FilterPermanent filter = new FilterPermanent("artifact");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    @Override
    public String getText(Game game, Ability ability) {
        int amount = game.getBattlefield().countAll(filter, ability.getControllerId(), game);
        return hint.getText(game, ability) + " (current: " + amount + ")";
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
