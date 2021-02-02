
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class BazaarOfBaghdad extends CardImpl {

    public BazaarOfBaghdad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Draw two cards, then discard three cards.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawDiscardControllerEffect(2, 3), new TapSourceCost()));
    }

    private BazaarOfBaghdad(final BazaarOfBaghdad card) {
        super(card);
    }

    @Override
    public BazaarOfBaghdad copy() {
        return new BazaarOfBaghdad(this);
    }
}
