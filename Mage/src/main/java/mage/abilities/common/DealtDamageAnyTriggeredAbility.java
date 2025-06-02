package mage.abilities.common;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.DamagedPermanentEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author xenohedron
 */
public class DealtDamageAnyTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPermanentEvent> {

    private final FilterPermanent filter;
    private final SetTargetPointer setTargetPointer;

    public DealtDamageAnyTriggeredAbility(Effect effect, FilterPermanent filter, SetTargetPointer setTargetPointer, boolean optional) {
        this(Zone.BATTLEFIELD, effect, filter, setTargetPointer, optional);
    }

    public DealtDamageAnyTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, SetTargetPointer setTargetPointer, boolean optional) {
        super(zone, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase(getWhen() + CardUtil.addArticle(filter.getMessage()) + " is dealt damage, ");
    }

    protected DealtDamageAnyTriggeredAbility(final DealtDamageAnyTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public DealtDamageAnyTriggeredAbility copy() {
        return new DealtDamageAnyTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // all events in the batch are always relevant if triggers at all
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null || !filter.match(permanent, getControllerId(), this, game)) {
            return false;
        }
        getEffects().setValue("damage", event.getAmount());
        switch (setTargetPointer) {
            case PERMANENT:
                getEffects().setTargetPointer(new FixedTarget(permanent, game));
                return true;
            case PLAYER:
                getEffects().setTargetPointer(new FixedTarget(permanent.getControllerId()));
                return true;
            case NONE:
                return true;
            default:
                throw new IllegalArgumentException("Unsupported SetTargetPointer in DealtDamageAttachedTriggeredAbility");
        }
    }
}
