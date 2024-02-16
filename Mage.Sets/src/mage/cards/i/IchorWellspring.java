

package mage.cards.i;

import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author ayratn
 */
public final class IchorWellspring extends CardImpl {

    public IchorWellspring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // When Ichor Wellspring enters the battlefield or is put into a graveyard from the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldOrDiesSourceTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false, false
        ));
    }

    private IchorWellspring(final IchorWellspring card) {
        super(card);
    }

    @Override
    public IchorWellspring copy() {
        return new IchorWellspring(this);
    }

}
