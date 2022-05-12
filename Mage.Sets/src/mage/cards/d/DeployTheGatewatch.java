
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPlaneswalkerCard;

/**
 *
 * @author awjackson
 */
public final class DeployTheGatewatch extends CardImpl {

    private static final FilterCard filter = new FilterPlaneswalkerCard("planeswalker cards");

    public DeployTheGatewatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");

        // Look at the top seven cards of your library. Put up to two planeswalker cards from among them onto the battlefield.
        // Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                7, 2, filter, PutCards.BATTLEFIELD, PutCards.BOTTOM_RANDOM, false));
    }

    private DeployTheGatewatch(final DeployTheGatewatch card) {
        super(card);
    }

    @Override
    public DeployTheGatewatch copy() {
        return new DeployTheGatewatch(this);
    }
}
