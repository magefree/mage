
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author fireshoes
 */
public final class Resupply extends CardImpl {

    public Resupply(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{5}{W}");

        // You gain 6 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(6));
        
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Resupply(final Resupply card) {
        super(card);
    }

    @Override
    public Resupply copy() {
        return new Resupply(this);
    }
}
