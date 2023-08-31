
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 *
 * @author LoneFox

 */
public final class GraftedSkullcap extends CardImpl {

    public GraftedSkullcap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // At the beginning of your draw step, draw an additional card.
        this.addAbility(new BeginningOfDrawTriggeredAbility(new DrawCardSourceControllerEffect(1).setText("draw an additional card"),
            TargetController.YOU, false));
        // At the beginning of your end step, discard your hand.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DiscardHandControllerEffect(),
            TargetController.YOU, false));
    }

    private GraftedSkullcap(final GraftedSkullcap card) {
        super(card);
    }

    @Override
    public GraftedSkullcap copy() {
        return new GraftedSkullcap(this);
    }
}
