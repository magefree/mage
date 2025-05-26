package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author Xanderhall, xenohedron
 */
public class OneOrMoreCombatDamagePlayerTriggeredAbility extends OneOrMoreDamagePlayerTriggeredAbility {

    public OneOrMoreCombatDamagePlayerTriggeredAbility(Effect effect) {
        this(effect, SetTargetPointer.NONE);
    }

    public OneOrMoreCombatDamagePlayerTriggeredAbility(Effect effect, FilterCreaturePermanent filter) {
        this(Zone.BATTLEFIELD, effect, filter, SetTargetPointer.NONE, false);
    }

    public OneOrMoreCombatDamagePlayerTriggeredAbility(Effect effect, SetTargetPointer setTargetPointer) {
        this(Zone.BATTLEFIELD, effect, StaticFilters.FILTER_PERMANENT_CREATURES, setTargetPointer, false);
    }

    public OneOrMoreCombatDamagePlayerTriggeredAbility(Effect effect, FilterCreaturePermanent filter, SetTargetPointer setTargetPointer) {
        this(Zone.BATTLEFIELD, effect, filter, setTargetPointer, false);
    }
    public OneOrMoreCombatDamagePlayerTriggeredAbility(Zone zone, Effect effect, FilterCreaturePermanent filter,
                                                       SetTargetPointer setTargetPointer, boolean optional) {
        super(zone, effect, filter, true, true, setTargetPointer, optional);
    }

    protected OneOrMoreCombatDamagePlayerTriggeredAbility(final OneOrMoreCombatDamagePlayerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OneOrMoreCombatDamagePlayerTriggeredAbility copy() {
        return new OneOrMoreCombatDamagePlayerTriggeredAbility(this);
    }
}
