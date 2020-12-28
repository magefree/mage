
package mage.abilities.common;

import mage.abilities.condition.Condition;
import mage.constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author nantuko
 */
public class BecomesTappedSourceTriggeredAbility extends TriggeredAbilityImpl {

    private final Condition interveningIfClauseCondition;

    public BecomesTappedSourceTriggeredAbility(Effect effect, boolean isOptional) {
        this(effect, isOptional, null);
    }

    public BecomesTappedSourceTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public BecomesTappedSourceTriggeredAbility(Effect effect, boolean isOptional, Condition interveningIfClauseCondition) {
        super(Zone.BATTLEFIELD, effect, isOptional);
        this.interveningIfClauseCondition = interveningIfClauseCondition;
    }

    public BecomesTappedSourceTriggeredAbility(final BecomesTappedSourceTriggeredAbility ability) {
        super(ability);
        this.interveningIfClauseCondition = ability.interveningIfClauseCondition;
    }

    @Override
    public BecomesTappedSourceTriggeredAbility copy() {
        return new BecomesTappedSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(sourceId);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        if (interveningIfClauseCondition != null) {
            return interveningIfClauseCondition.apply(game, this);
        }
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} becomes tapped, " + generateConditionString() + super.getRule();
    }

    private String generateConditionString () {
        if (interveningIfClauseCondition != null) {
            if (interveningIfClauseCondition.toString().startsWith("if")) {

                //Fixes punctuation on multiple sentence if-then construction
                // see -- Colfenor's Urn
                if (interveningIfClauseCondition.toString().endsWith(".")) {
                    return interveningIfClauseCondition.toString() + " ";
                }

                return interveningIfClauseCondition.toString() + ", ";
            } else {
                return "if {this} is " + interveningIfClauseCondition.toString() + ", ";
            }
        }
        return "";
    }
}
