package mage.game.command.emblems;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.command.Emblem;
import mage.game.permanent.token.RedGreenBeastToken;

/**
 * @author TheElk801
 */
public final class DomriChaosBringerEmblem extends Emblem {

    // -8: You get an emblem with "At the beginning of each end step, create a 4/4 red and green Beast creature token with trample."
    public DomriChaosBringerEmblem() {
        super("Emblem Domri");
        this.getAbilities().add(new BeginningOfEndStepTriggeredAbility(
                Zone.COMMAND, new CreateTokenEffect(new RedGreenBeastToken()),
                TargetController.ANY, null, false
        ));
    }

    private DomriChaosBringerEmblem(final DomriChaosBringerEmblem card) {
        super(card);
    }

    @Override
    public DomriChaosBringerEmblem copy() {
        return new DomriChaosBringerEmblem(this);
    }
}
