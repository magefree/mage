package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.FilterStackObject;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.targetpointer.FixedTarget;

/**
 * @author Susucr
 */
public class BecomesTargetTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterPermanent filterTarget;
    private final FilterStackObject filterStack;
    private final SetTargetPointer setTargetPointer;

    public BecomesTargetTriggeredAbility(Effect effect, FilterPermanent filterTarget) {
        this(effect, filterTarget, StaticFilters.FILTER_SPELL_OR_ABILITY_A);
    }

    public BecomesTargetTriggeredAbility(Effect effect, FilterPermanent filterTarget, FilterStackObject filterStack) {
        this(effect, filterTarget, filterStack, SetTargetPointer.NONE);
    }

    public BecomesTargetTriggeredAbility(Effect effect, FilterPermanent filterTarget, FilterStackObject filterStack,
                                         SetTargetPointer setTargetPointer) {
        this(effect, filterTarget, filterStack, setTargetPointer, false);
    }

    public BecomesTargetTriggeredAbility(Effect effect, FilterPermanent filterTarget, FilterStackObject filterStack,
                                         SetTargetPointer setTargetPointer, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filterTarget = filterTarget;
        this.filterStack = filterStack;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever " + filterTarget.getMessage() + " becomes the target of "
                                     + filterStack.getMessage() + ", ");
    }

    public BecomesTargetTriggeredAbility(final BecomesTargetTriggeredAbility ability) {
        super(ability);
        this.filterTarget = ability.filterTarget;
        this.filterStack = ability.filterStack;
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
        if (sourceObject == null
            || !filterStack.match(sourceObject, getControllerId(), this, game)) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null
            || !filterTarget.match(permanent, getControllerId(), this, game)) {
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
