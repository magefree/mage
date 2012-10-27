package mage.abilities.decorator;

import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * Adds condition to {@link mage.abilities.effects.ContinuousEffect}. Acts as decorator.
 *
 * @author nantuko
 */
public class ConditionalTriggeredAbility extends TriggeredAbilityImpl<ConditionalTriggeredAbility> {

    protected TriggeredAbility ability;
    protected Condition condition;
    protected String text;

    public ConditionalTriggeredAbility(TriggeredAbility ability,  Condition condition, String text) {
        this(ability, condition, text, false);
    }

    public ConditionalTriggeredAbility(TriggeredAbility ability, Condition condition, String text, Boolean optional) {
        super(ability.getZone(), null);
        this.ability = ability;
        this.modes = ability.getModes();
        this.condition = condition;
        this.optional = optional;
        this.text = text;
    }

    public ConditionalTriggeredAbility(ConditionalTriggeredAbility triggered) {
        super(triggered);
        this.ability = triggered.ability;
        this.condition = triggered.condition;
        this.text = triggered.text;
    }

    @Override
    public ConditionalTriggeredAbility copy() {
        return new ConditionalTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ability.setSourceId(this.getSourceId());
        ability.setControllerId(this.getControllerId());
        if (ability.checkTrigger(event, game)) {
            if (condition.apply(game, this)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        if (text != null && text.isEmpty()) {
            return ability.getRule();
        }
        return text;
    }
}
