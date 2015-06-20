package mage.abilities.decorator;

import mage.MageObject;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.effects.Effects;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * Adds condition to {@link mage.abilities.effects.ContinuousEffect}. Acts as decorator.
 *
 * @author nantuko
 */
public class ConditionalTriggeredAbility extends TriggeredAbilityImpl {

    protected TriggeredAbility ability;
    protected Condition condition;
    protected String text;

    public ConditionalTriggeredAbility(TriggeredAbility ability,  Condition condition, String text) {
        this(ability, condition, text, false);
    }

    public ConditionalTriggeredAbility(TriggeredAbility ability, Condition condition, String text, boolean optional) {
        super(ability.getZone(), null);
        this.ability = ability;
        this.modes = ability.getModes();
        this.condition = condition;
        this.optional = optional;
        this.text = text;
    }

    public ConditionalTriggeredAbility(final ConditionalTriggeredAbility triggered) {
        super(triggered);
        this.ability = triggered.ability;
        this.condition = triggered.condition;
        this.text = triggered.text;
    }


    @Override
    public boolean checkInterveningIfClause(Game game) {
        return condition.apply(game, this);
    }

    @Override
    public ConditionalTriggeredAbility copy() {
        return new ConditionalTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return ability.checkEventType(event, game);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ability.setSourceId(this.getSourceId());
        ability.setControllerId(this.getControllerId());
        return ability.checkTrigger(event, game);
    }

    @Override
    public String getRule() {
        if (text == null || text.isEmpty()) {            
            return ability.getRule();
        }
        return text;
    }

    @Override
    public Effects getEffects() {
        return ability.getEffects();
    }

    @Override
    public MageObject getSourceObjectIfItStillExists(Game game) {
        return ability.getSourceObjectIfItStillExists(game); 
    }

    @Override
    public MageObject getSourceObject(Game game) {
        return ability.getSourceObject(game);
    }

    
    
    @Override
    public int getSourceObjectZoneChangeCounter() {
        return ability.getSourceObjectZoneChangeCounter();
    }

}
