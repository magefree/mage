
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.DiscardsACardOpponentTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Mitchel Sein
 */
public final class GethsGrimoire extends CardImpl {

    public GethsGrimoire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        Effect drawTrigger = new DrawCardSourceControllerEffect(1);
        drawTrigger.setText("you may draw a card.");
        // Whenever an opponent discards a card, you may draw a card.
        this.addAbility(new DiscardsACardOpponentTriggeredAbility(drawTrigger, true));
    }

    private GethsGrimoire(final GethsGrimoire card) {
        super(card);
    }

    @Override
    public GethsGrimoire copy() {
        return new GethsGrimoire(this);
    }
}
