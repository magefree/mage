package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;

/**
 *
 * @author tcontis
 */
public final class AdventurousImpulse extends CardImpl {

    public AdventurousImpulse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{G}");

        // Look at the top three cards of your library.
        // You may reveal a creature or land card from among them and put it into your hand.
        // Put the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                3, 1, StaticFilters.FILTER_CARD_CREATURE_OR_LAND, PutCards.HAND, PutCards.BOTTOM_ANY));
    }

    private AdventurousImpulse(final AdventurousImpulse card) {
        super(card);
    }

    @Override
    public AdventurousImpulse copy() {
        return new AdventurousImpulse(this);
    }
}
