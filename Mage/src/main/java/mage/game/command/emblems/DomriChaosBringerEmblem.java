package mage.game.command.emblems;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.TargetController;
import mage.game.command.Emblem;
import mage.game.permanent.token.SoldierToken;

/**
 * @author TheElk801
 */
public final class DomriChaosBringerEmblem extends Emblem {

    // -8: You get an emblem with "At the beginning of each end step, create a 4/4 red and green Beast creature token with trample."
    public DomriChaosBringerEmblem() {
        this.setName("Emblem Domri");
        this.setExpansionSetCodeForImage("RNA");
        this.getAbilities().add(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new SoldierToken()),
                TargetController.ANY, false
        ));
    }
}
