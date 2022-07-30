package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterStackObject;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;
import mage.target.targetpointer.FixedTarget;

/**
 * @author North
 */
public class BecomesTargetTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterStackObject filter;
    private final SetTargetPointer setTargetPointer;

    public BecomesTargetTriggeredAbility(Effect effect) {
        this(effect, StaticFilters.FILTER_SPELL_OR_ABILITY_A);
    }

    public BecomesTargetTriggeredAbility(Effect effect, FilterStackObject filter) {
        this(effect, filter, SetTargetPointer.NONE);
        setTriggerPhrase("When {this} becomes the target of " + filter.getMessage() + ", ");
    }

    public BecomesTargetTriggeredAbility(Effect effect, FilterStackObject filter, SetTargetPointer setTargetPointer) {
        this(effect, filter, setTargetPointer, false);
    }

    public BecomesTargetTriggeredAbility(Effect effect, FilterStackObject filter, SetTargetPointer setTargetPointer, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter.copy();
        this.setTargetPointer = setTargetPointer;
    }

    public BecomesTargetTriggeredAbility(final BecomesTargetTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter.copy();
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public BecomesTargetTriggeredAbility copy() {
        return new BecomesTargetTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        StackObject sourceObject = game.getStack().getStackObject(event.getSourceId());
        if (!event.getTargetId().equals(getSourceId())
                || !filter.match(sourceObject, getControllerId(), this, game)) {
            return false;
        }
        switch (setTargetPointer) {
            case PLAYER:
                this.getEffects().stream()
                        .forEach(effect -> effect.setTargetPointer(
                                new FixedTarget(sourceObject.getControllerId(), game)
                        ));
                break;
            case SPELL:
                this.getEffects().stream()
                        .forEach(effect -> effect.setTargetPointer(
                                new FixedTarget(sourceObject.getId(), game)
                        ));
                break;
        }
        return true;
    }
}
