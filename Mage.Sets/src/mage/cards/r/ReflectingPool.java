
package mage.cards.r;

import java.util.UUID;
import mage.abilities.mana.AnyColorLandsProduceManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 *
 * @author jeffwadsworth
 */
public final class ReflectingPool extends CardImpl {

    public ReflectingPool(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add one mana of any type that a land you control could produce.
        this.addAbility(new AnyColorLandsProduceManaAbility(TargetController.YOU, false));
    }

    private ReflectingPool(final ReflectingPool card) {
        super(card);
    }

    @Override
    public ReflectingPool copy() {
        return new ReflectingPool(this);
    }
}
