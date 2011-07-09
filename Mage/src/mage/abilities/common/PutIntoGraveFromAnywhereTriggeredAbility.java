package mage.abilities.common;

import mage.Constants;
import mage.abilities.effects.Effect;

/**
 * @author Loki
 */
public class PutIntoGraveFromAnywhereTriggeredAbility extends ZoneChangeTriggeredAbility<PutIntoGraveFromAnywhereTriggeredAbility> {
    public PutIntoGraveFromAnywhereTriggeredAbility(Effect effect, boolean optional) {
        super(Constants.Zone.GRAVEYARD, effect, "When {this} is put into a graveyard from anywhere, ", optional);
    }

    public PutIntoGraveFromAnywhereTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public PutIntoGraveFromAnywhereTriggeredAbility(final PutIntoGraveFromAnywhereTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PutIntoGraveFromAnywhereTriggeredAbility copy() {
        return new PutIntoGraveFromAnywhereTriggeredAbility(this);
    }
}
