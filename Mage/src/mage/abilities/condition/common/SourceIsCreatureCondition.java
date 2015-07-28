package mage.abilities.condition.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.CardType;
import mage.game.Game;

/**
 * @author klayhamn
 */
public class SourceIsCreatureCondition implements Condition {

    private static final SourceIsCreatureCondition fInstance = new SourceIsCreatureCondition();

    public static Condition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source.getSourceId());
        return object != null && object.getCardType().contains(CardType.CREATURE);
    }
}
