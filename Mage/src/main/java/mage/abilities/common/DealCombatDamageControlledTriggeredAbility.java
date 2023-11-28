package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author Xanderhall, xenohedron
 */
public class DealCombatDamageControlledTriggeredAbility extends OneOrMoreDealDamageTriggeredAbility {

    public DealCombatDamageControlledTriggeredAbility(Effect effect) {
        this(effect, SetTargetPointer.NONE);
    }

    public DealCombatDamageControlledTriggeredAbility(Effect effect, FilterCreaturePermanent filter) {
        this(Zone.BATTLEFIELD, effect, filter, SetTargetPointer.NONE, false);
    }

    public DealCombatDamageControlledTriggeredAbility(Effect effect, SetTargetPointer setTargetPointer) {
        this(Zone.BATTLEFIELD, effect, StaticFilters.FILTER_PERMANENT_CREATURES, setTargetPointer, false);
    }

    public DealCombatDamageControlledTriggeredAbility(Zone zone, Effect effect, FilterCreaturePermanent filter,
                                                      SetTargetPointer setTargetPointer, boolean optional) {
        super(zone, effect, filter, true, true, setTargetPointer, optional);
    }

    protected DealCombatDamageControlledTriggeredAbility(final DealCombatDamageControlledTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DealCombatDamageControlledTriggeredAbility copy() {
        return new DealCombatDamageControlledTriggeredAbility(this);
    }
}
