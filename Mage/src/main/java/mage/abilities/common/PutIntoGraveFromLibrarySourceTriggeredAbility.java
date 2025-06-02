package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.constants.Zone;

/**
 * @author xenohedron
 */
public class PutIntoGraveFromLibrarySourceTriggeredAbility extends ZoneChangeTriggeredAbility {

    public PutIntoGraveFromLibrarySourceTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public PutIntoGraveFromLibrarySourceTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.LIBRARY, Zone.GRAVEYARD, effect, "When this card is put into your graveyard from your library, ",  optional);
    }

    protected PutIntoGraveFromLibrarySourceTriggeredAbility(final PutIntoGraveFromLibrarySourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PutIntoGraveFromLibrarySourceTriggeredAbility copy() {
        return new PutIntoGraveFromLibrarySourceTriggeredAbility(this);
    }
}
