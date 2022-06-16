
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.LandfallAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author jeffwadsworth
 */
public final class SeersSundial extends CardImpl {

    public SeersSundial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // Landfall - Whenever a land enters the battlefield under your control, you may pay {2}. If you do, draw a card.
        this.addAbility(new LandfallAbility(new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{2}")), false)); // optional = false because DoIfCost is already optonal
    }

    private SeersSundial(final SeersSundial card) {
        super(card);
    }

    @Override
    public SeersSundial copy() {
        return new SeersSundial(this);
    }
}
