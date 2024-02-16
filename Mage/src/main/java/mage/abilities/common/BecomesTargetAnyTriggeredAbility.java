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
import mage.util.CardUtil;

/**
 * @author xenohedron
 */
public class BecomesTargetAnyTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterPermanent filterTarget;
    private final FilterStackObject filterStack;
    private final SetTargetPointer setTargetPointer;

    public BecomesTargetAnyTriggeredAbility(Effect effect, FilterPermanent filterTarget) {
        this(effect, filterTarget, StaticFilters.FILTER_SPELL_OR_ABILITY_A);
    }

    /**
     * "Whenever [a filterTarget] becomes the target of [a filterStack], [effect]"
     * @param effect defaults to SetTargetPointer.PERMANENT
     * @param filterTarget permanents to check being targetted
     * @param filterStack spells/abilities to check targeting
     */
    public BecomesTargetAnyTriggeredAbility(Effect effect, FilterPermanent filterTarget, FilterStackObject filterStack) {
        this(effect, filterTarget, filterStack, SetTargetPointer.PERMANENT, false);
    }

    public BecomesTargetAnyTriggeredAbility(Effect effect, FilterPermanent filterTarget, FilterStackObject filterStack,
                                            SetTargetPointer setTargetPointer, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filterTarget = filterTarget;
        this.filterStack = filterStack;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever " + filterTarget.getMessage() + " becomes the target of "
                + filterStack.getMessage() + ", ");
    }

    protected BecomesTargetAnyTriggeredAbility(final BecomesTargetAnyTriggeredAbility ability) {
        super(ability);
        this.filterTarget = ability.filterTarget;
        this.filterStack = ability.filterStack;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public BecomesTargetAnyTriggeredAbility copy() {
        return new BecomesTargetAnyTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null || !filterTarget.match(permanent, getControllerId(), this, game)) {
            return false;
        }
        StackObject targetingObject = CardUtil.getTargetingStackObject(event, game);
        if (targetingObject == null || !filterStack.match(targetingObject, getControllerId(), this, game)) {
            return false;
        }
        if (CardUtil.checkTargetedEventAlreadyUsed(this.id.toString(), targetingObject, event, game)) {
            return false;
        }
        switch (setTargetPointer) {
            case PERMANENT:
                this.getAllEffects().setTargetPointer(new FixedTarget(permanent.getId(), game));
                break;
            case PLAYER:
                this.getAllEffects().setTargetPointer(new FixedTarget(targetingObject.getControllerId(), game));
                break;
            case SPELL:
                this.getAllEffects().setTargetPointer(new FixedTarget(targetingObject.getId()));
                break;
            case NONE:
                break;
            default:
                throw new IllegalArgumentException("Unsupported SetTargetPointer in BecomesTargetAnyTriggeredAbility");
        }
        return true;
    }
}
