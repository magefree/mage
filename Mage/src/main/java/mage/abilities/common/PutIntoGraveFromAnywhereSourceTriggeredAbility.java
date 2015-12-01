package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.constants.Zone;

/**
 * @author Loki
 */
public class PutIntoGraveFromAnywhereSourceTriggeredAbility extends ZoneChangeTriggeredAbility {

    public PutIntoGraveFromAnywhereSourceTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.GRAVEYARD, effect, "When {this} is put into a graveyard from anywhere, ", optional);
    }

    public PutIntoGraveFromAnywhereSourceTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public PutIntoGraveFromAnywhereSourceTriggeredAbility(final PutIntoGraveFromAnywhereSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PutIntoGraveFromAnywhereSourceTriggeredAbility copy() {
        return new PutIntoGraveFromAnywhereSourceTriggeredAbility(this);
    }
}
