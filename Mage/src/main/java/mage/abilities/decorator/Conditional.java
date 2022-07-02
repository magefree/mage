package mage.abilities.decorator;

import mage.abilities.condition.Condition;

/**
 * Used to create conditional versions of effects in a way that allows for them to be differentiated from
 * the nonconditional version without needed any Java reflection hacks.
 *
 * @author Alex-Vasile
 */
public interface Conditional {
    public Condition getCondition();
}
