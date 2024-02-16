package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.filter.FilterPermanent;
import mage.filter.FilterStackObject;
import mage.game.events.GameEvent;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author xenohedron
 */
public class BecomesTargetControllerTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterPermanent filterTarget;
    private final FilterStackObject filterStack;
    private final SetTargetPointer setTargetPointer;

    /**
     * Note: filterTarget can be null for "whenever you become the target of...";
     * if set, then "whenever you or a [filterTarget] becomes the target of..."
     */
    public BecomesTargetControllerTriggeredAbility(Effect effect, FilterPermanent filterTarget, FilterStackObject filterStack,
                                                   SetTargetPointer setTargetPointer, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filterTarget = filterTarget;
        this.filterStack = filterStack;
        this.setTargetPointer = setTargetPointer;
        String filterMessage = (filterTarget == null)
                ? "you become"
                : "you or " + filterTarget.getMessage() + " becomes";
        setTriggerPhrase("Whenever " + filterMessage + " the target of " + filterStack.getMessage() + ", ");
    }

    protected BecomesTargetControllerTriggeredAbility(final BecomesTargetControllerTriggeredAbility ability) {
        super(ability);
        this.filterTarget = ability.filterTarget;
        this.filterStack = ability.filterStack;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public BecomesTargetControllerTriggeredAbility copy() {
        return new BecomesTargetControllerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(getControllerId())) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent == null || filterTarget == null || !filterTarget.match(permanent, getControllerId(), this, game)) {
                return false;
            }
        }
        StackObject targetingObject = CardUtil.getTargetingStackObject(event, game);
        if (targetingObject == null || !filterStack.match(targetingObject, getControllerId(), this, game)) {
            return false;
        }
        if (CardUtil.checkTargetedEventAlreadyUsed(this.id.toString(), targetingObject, event, game)) {
            return false;
        }
        switch (setTargetPointer) {
            case SPELL:
                this.getAllEffects().setTargetPointer(new FixedTarget(targetingObject.getId()));
                break;
            case NONE:
                break;
            default:
                throw new IllegalArgumentException("Unsupported SetTargetPointer in BecomesTargetControllerTriggeredAbility");
        }
        return true;
    }
}
