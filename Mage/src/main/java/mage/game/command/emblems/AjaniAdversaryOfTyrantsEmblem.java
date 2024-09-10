package mage.game.command.emblems;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.command.Emblem;
import mage.game.permanent.token.CatToken2;

/**
 * @author TheElk801
 */
public final class AjaniAdversaryOfTyrantsEmblem extends Emblem {

    // −7: You get an emblem with "At the beginning of your end step, create three 1/1 white Cat creature tokens with lifelink."
    public AjaniAdversaryOfTyrantsEmblem() {
        super("Emblem Ajani");
        this.getAbilities().add(new BeginningOfEndStepTriggeredAbility(
                Zone.COMMAND, new CreateTokenEffect(new CatToken2(), 3),
                TargetController.YOU, null, false
        ));
    }

    private AjaniAdversaryOfTyrantsEmblem(final AjaniAdversaryOfTyrantsEmblem card) {
        super(card);
    }

    @Override
    public AjaniAdversaryOfTyrantsEmblem copy() {
        return new AjaniAdversaryOfTyrantsEmblem(this);
    }
}
