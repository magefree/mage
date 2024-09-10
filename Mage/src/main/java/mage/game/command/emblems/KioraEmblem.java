package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.command.Emblem;
import mage.game.permanent.token.Kraken99Token;

/**
 * @author spjspj
 */
public final class KioraEmblem extends Emblem {

    /**
     * Emblem: "At the beginning of your end step, create a 9/9 blue Kraken
     * creature token."
     */

    public KioraEmblem() {
        super("Emblem Kiora");
        Ability ability = new BeginningOfEndStepTriggeredAbility(Zone.COMMAND, new CreateTokenEffect(new Kraken99Token()), TargetController.YOU, null, false);
        this.getAbilities().add(ability);
    }

    private KioraEmblem(final KioraEmblem card) {
        super(card);
    }

    @Override
    public KioraEmblem copy() {
        return new KioraEmblem(this);
    }
}
