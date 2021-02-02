
package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.DrawCardOpponentTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class MindsEye extends CardImpl {

    public MindsEye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // Whenever an opponent draws a card, you may pay {1}. If you do, draw a card.
        this.addAbility(new DrawCardOpponentTriggeredAbility(new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new GenericManaCost(1)), false, false));
    }

    private MindsEye(final MindsEye card) {
        super(card);
    }

    @Override
    public MindsEye copy() {
        return new MindsEye(this);
    }
}
