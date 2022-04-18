package mage.cards.w;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.CitizenGreenWhiteToken;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class WarmWelcome extends CardImpl {

    public WarmWelcome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Look at the top five cards of your library.
        // You may reveal a creature card from among them and put it into your hand.
        // Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                5, 1, StaticFilters.FILTER_CARD_CREATURE_A, PutCards.HAND, PutCards.BOTTOM_RANDOM));

        // Create a 1/1 green and white Citizen creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new CitizenGreenWhiteToken()));
    }

    private WarmWelcome(final WarmWelcome card) {
        super(card);
    }

    @Override
    public WarmWelcome copy() {
        return new WarmWelcome(this);
    }
}
