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

    public EntersBattlefieldThisOrAnotherTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional, boolean onlyControlled) {
        this(effect, filter, optional, SetTargetPointer.NONE, onlyControlled);
    }

    public EntersBattlefieldThisOrAnotherTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer, boolean onlyControlled) {
        super(Zone.BATTLEFIELD, effect, new FilterPermanentThisOrAnother(filter, onlyControlled), optional, setTargetPointer);
        setTriggerPhrase("Whenever " + this.filter.getMessage() + " enters the battlefield" + (onlyControlled ? " under your control, " : ", "));
    }

    private EntersBattlefieldThisOrAnotherTriggeredAbility(final EntersBattlefieldThisOrAnotherTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EntersBattlefieldThisOrAnotherTriggeredAbility copy() {
        return new EntersBattlefieldThisOrAnotherTriggeredAbility(this);
    }
}
