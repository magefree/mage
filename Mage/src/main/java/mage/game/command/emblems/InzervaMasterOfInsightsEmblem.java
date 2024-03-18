package mage.game.command.emblems;

import mage.abilities.common.DrawCardOpponentTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.PlayWithHandRevealedEffect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.command.Emblem;

/**
 * @author PurpleCrowbar
 */
public final class InzervaMasterOfInsightsEmblem extends Emblem {

    // You get an emblem with "Your opponents play with their hands revealed" and "Whenever an opponent draws a card, this emblem deals 1 damage to them."
    public InzervaMasterOfInsightsEmblem() {
        super("Emblem Inzerva");
        // Your opponents play with their hands revealed
        this.getAbilities().add(new SimpleStaticAbility(
                Zone.COMMAND,
                new PlayWithHandRevealedEffect(TargetController.OPPONENT)
        ));
        // Whenever an opponent draws a card, this emblem deals 1 damage to them
        this.getAbilities().add(new DrawCardOpponentTriggeredAbility(
                Zone.COMMAND, new DamageTargetEffect(1, true, "them")
                        .setText("this emblem deals 1 damage to them"), false, true
        ));
    }

    private InzervaMasterOfInsightsEmblem(final InzervaMasterOfInsightsEmblem card) {
        super(card);
    }

    @Override
    public InzervaMasterOfInsightsEmblem copy() {
        return new InzervaMasterOfInsightsEmblem(this);
    }
}
