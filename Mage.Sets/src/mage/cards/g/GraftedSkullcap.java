
package mage.cards.g;

import java.util.UUID;
import mage.abilities.triggers.BeginningOfDrawTriggeredAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LoneFox

 */
public final class GraftedSkullcap extends CardImpl {

    public GraftedSkullcap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // At the beginning of your draw step, draw an additional card.
        this.addAbility(new BeginningOfDrawTriggeredAbility(new DrawCardSourceControllerEffect(1).setText("draw an additional card"),
                false));
        // At the beginning of your end step, discard your hand.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DiscardHandControllerEffect()
        ));
    }

    private GraftedSkullcap(final GraftedSkullcap card) {
        super(card);
    }

    @Override
    public GraftedSkullcap copy() {
        return new GraftedSkullcap(this);
    }
}
