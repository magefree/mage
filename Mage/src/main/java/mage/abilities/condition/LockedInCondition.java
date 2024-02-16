
package mage.abilities.condition;

import mage.abilities.Ability;
import mage.game.Game;

/**
 * The use of this class must be handled carefully because conditions don't
 * have a copy method, the condition state is kept when the effect or ability
 * is copied that uses the condition.
 * So if you use this class, you have to do it like in ConditionalContinuousEffect,
 * where always a new FixedCondition(condition.apply(...)) is used if a
 * LockedInCondition is given.
 *
 * Needs probably some redesign, don't like it the way it's done now.
 *
 * @author LevelX2
 */
public class LockedInCondition implements Condition {

    private boolean conditionChecked = false;
    private boolean result;
    private final Condition condition;

    public LockedInCondition ( Condition condition ) {
        this.condition = condition;
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public boolean apply(Game game, Ability source) {
        if(!conditionChecked) {
            result = condition.apply(game, source);
            conditionChecked = true;
        }
        return result;
    }

    public Condition getBaseCondition() {
        return condition;
    }

    @Override
    public boolean caresAboutManaColor() {
        return condition.caresAboutManaColor();
    }

}
