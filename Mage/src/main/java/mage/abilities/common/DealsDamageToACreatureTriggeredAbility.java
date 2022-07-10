
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX
 */
public class DealsDamageToACreatureTriggeredAbility extends TriggeredAbilityImpl {

    protected boolean combatOnly;
    private final boolean setTargetPointer;
    private FilterCreaturePermanent filter;

    public DealsDamageToACreatureTriggeredAbility(Effect effect, boolean combatOnly, boolean optional, boolean setTargetPointer) {
        this(effect, combatOnly, optional, setTargetPointer, StaticFilters.FILTER_PERMANENT_A_CREATURE);
    }

    public DealsDamageToACreatureTriggeredAbility(Effect effect, boolean combatOnly, boolean optional, boolean setTargetPointer, FilterCreaturePermanent filter) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.combatOnly = combatOnly;
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
    }

    public DealsDamageToACreatureTriggeredAbility(final DealsDamageToACreatureTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
        this.combatOnly = ability.combatOnly;
        this.filter = ability.filter;
    }

    @Override
    public DealsDamageToACreatureTriggeredAbility copy() {
        return new DealsDamageToACreatureTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.sourceId)
                && (!combatOnly || ((DamagedEvent) event).isCombatDamage())) {

            Permanent creature = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (!filter.match(creature, getControllerId(), this, game)) {
                return false;
            }

            if (setTargetPointer) {
                this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
                this.getEffects().setValue("damage", event.getAmount());
            }
            return true;
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever {this} deals " + (combatOnly ? "combat " : "") + "damage to " + filter.getMessage() + ", ";
    }
}
