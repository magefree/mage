package mage.util.functions;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class AddSubtypeCopyApplier extends CopyApplier {

    private final SubType subtype;

    public AddSubtypeCopyApplier(SubType subtype) {
        this.subtype = subtype;
    }

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        if (!blueprint.getSubtype().contains(subtype)) {
            blueprint.getSubtype().add(subtype);
        }
        return true;
    }

}
