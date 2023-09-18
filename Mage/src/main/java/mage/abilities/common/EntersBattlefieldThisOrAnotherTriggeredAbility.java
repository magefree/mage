package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.FilterPermanentThisOrAnother;

/**
 * @author TheElk801
 */
public class EntersBattlefieldThisOrAnotherTriggeredAbility extends EntersBattlefieldAllTriggeredAbility {

    public EntersBattlefieldThisOrAnotherTriggeredAbility(Effect effect, FilterPermanent filter) {
        this(effect, filter, false, false);
    }

    public EntersBattlefieldThisOrAnotherTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional, boolean onlyControlled) {
        this(effect, filter, optional, SetTargetPointer.NONE, onlyControlled);
    }

    public EntersBattlefieldThisOrAnotherTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer, boolean onlyControlled) {
        this(Zone.BATTLEFIELD, effect, filter, optional, setTargetPointer, onlyControlled);
    }

    public EntersBattlefieldThisOrAnotherTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer, boolean onlyControlled) {
        super(zone, effect,
                new FilterPermanentThisOrAnother(filter, onlyControlled),
                optional, setTargetPointer, null, onlyControlled);
    }

    private EntersBattlefieldThisOrAnotherTriggeredAbility(final EntersBattlefieldThisOrAnotherTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EntersBattlefieldThisOrAnotherTriggeredAbility copy() {
        return new EntersBattlefieldThisOrAnotherTriggeredAbility(this);
    }
}