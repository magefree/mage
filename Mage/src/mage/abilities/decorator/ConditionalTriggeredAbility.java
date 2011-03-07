package mage.abilities.decorator;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
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
	protected Effect effect;
    protected String text;

    public ConditionalTriggeredAbility(TriggeredAbility ability, Effect effect, Condition condition, String text) {
        super(ability.getZone(), effect);
        this.ability = ability;
        this.effect = effect;
		this.condition = condition;
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
		if (ability.checkTrigger(event, game)) {
			return condition.apply(game, this);
		}
		return false;
	}

	@Override
	public String getRule() {
		return text;
	}
}
