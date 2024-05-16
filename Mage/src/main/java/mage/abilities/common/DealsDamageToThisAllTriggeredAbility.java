package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author Susucr
 */
public class DealsDamageToThisAllTriggeredAbility extends TriggeredAbilityImpl {

    private final boolean combatOnly;
    private final FilterPermanent filterPermanent;
    private final SetTargetPointer setTargetPointer;

    public DealsDamageToThisAllTriggeredAbility(
            Effect effect, boolean optional, FilterPermanent filterPermanent,
            SetTargetPointer setTargetPointer, boolean combatOnly
    ) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setTargetPointer = setTargetPointer;
        this.filterPermanent = filterPermanent;
        this.combatOnly = combatOnly;
        setTriggerPhrase("Whenever " + filterPermanent.getMessage() + " deals "
                + (combatOnly ? "combat " : "") + "damage to a {this}, ");
    }

    protected DealsDamageToThisAllTriggeredAbility(final DealsDamageToThisAllTriggeredAbility ability) {
        super(ability);
        this.combatOnly = ability.combatOnly;
        this.filterPermanent = ability.filterPermanent;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public DealsDamageToThisAllTriggeredAbility copy() {
        return new DealsDamageToThisAllTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (combatOnly && !((DamagedEvent) event).isCombatDamage()) {
            return false;
        }
        if (!event.getTargetId().equals(this.sourceId)) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (!filterPermanent.match(permanent, getControllerId(), this, game)) {
            return false;
        }
        int damageAmount = event.getAmount();
        if (damageAmount < 1) {
            return false;
        }
        this.getEffects().setValue("damage", damageAmount);
        this.getEffects().setValue("sourceId", event.getSourceId());
        switch (setTargetPointer) {
            case PLAYER:
                this.getEffects().setTargetPointer(new FixedTarget(permanent.getControllerId()));
                break;
            case PERMANENT:
                this.getEffects().setTargetPointer(new FixedTarget(permanent, game));
                break;
        }
        return true;
    }
}
