package mage.abilities.common;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.DamagedBatchBySourceEvent;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * Whenever [[filtered permanent]] deals (combat)? damage, [[effect]]
 *
 * @author xenohedron
 */
public class DealsDamageToAnyTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedEvent> {

    private final FilterPermanent filter;
    private final boolean onlyCombat;
    private final SetTargetPointer setTargetPointer;

    public DealsDamageToAnyTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter,
                                            SetTargetPointer setTargetPointer, boolean onlyCombat, boolean optional) {
        super(zone, effect, optional);
        this.filter = filter;
        this.onlyCombat = onlyCombat;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase(getWhen() + CardUtil.addArticle(filter.getMessage()) + " deals "
                + (onlyCombat ? "combat " : "") + "damage, ");
    }

    protected DealsDamageToAnyTriggeredAbility(final DealsDamageToAnyTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.onlyCombat = ability.onlyCombat;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public DealsDamageToAnyTriggeredAbility copy() {
        return new DealsDamageToAnyTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_BY_SOURCE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // all events in the batch are always relevant if triggers at all
        if (onlyCombat && !((DamagedBatchBySourceEvent) event).isCombatDamage()) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
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
