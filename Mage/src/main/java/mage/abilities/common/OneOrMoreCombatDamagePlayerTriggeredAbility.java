package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;

/**
 * @author Xanderhall, xenohedron
 */
public class OneOrMoreCombatDamagePlayerTriggeredAbility extends OneOrMoreDamagePlayerTriggeredAbility {

    public OneOrMoreCombatDamagePlayerTriggeredAbility(Effect effect) {
        this(effect, SetTargetPointer.NONE);
    }

    public OneOrMoreCombatDamagePlayerTriggeredAbility(Effect effect, FilterPermanent filter) {
        this(Zone.BATTLEFIELD, effect, filter, SetTargetPointer.NONE, false);
    }

    public OneOrMoreCombatDamagePlayerTriggeredAbility(Effect effect, SetTargetPointer setTargetPointer) {
        this(effect, setTargetPointer, StaticFilters.FILTER_PERMANENT_CREATURES, false);
    }

    public OneOrMoreCombatDamagePlayerTriggeredAbility(Effect effect, SetTargetPointer setTargetPointer, FilterPermanent filter, boolean optional) {
        this(Zone.BATTLEFIELD, effect, filter, setTargetPointer, optional);
    }

    public OneOrMoreCombatDamagePlayerTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter,
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
