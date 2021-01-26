package mage.util.functions;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class AbilityCopyApplier extends CopyApplier {

    private final Ability ability;

    public AbilityCopyApplier(Ability ability) {
        this.ability = ability;
    }

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        blueprint.getAbilities().add(ability);
        return true;
    }

}
