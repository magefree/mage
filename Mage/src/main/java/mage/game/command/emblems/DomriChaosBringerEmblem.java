package mage.game.command.emblems;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.command.Emblem;
import mage.game.permanent.token.RedGreenBeastToken;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class DomriChaosBringerEmblem extends Emblem {

    // -8: You get an emblem with "At the beginning of each end step, create a 4/4 red and green Beast creature token with trample."
    public DomriChaosBringerEmblem() {
        this.setName("Emblem Domri");
        availableImageSetCodes = Arrays.asList("MED", "RNA");
        this.getAbilities().add(new BeginningOfEndStepTriggeredAbility(
                Zone.COMMAND, new CreateTokenEffect(new RedGreenBeastToken()),
                TargetController.ANY, null, false
        ));
    }
}
