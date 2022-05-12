package mage.cards.s;

import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShimmerOfPossibility extends CardImpl {

    public ShimmerOfPossibility(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Look at the top four cards of your library. Put one of them into your hand and the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(4, 1, PutCards.HAND, PutCards.BOTTOM_RANDOM));
    }

    private ShimmerOfPossibility(final ShimmerOfPossibility card) {
        super(card);
    }

    @Override
    public ShimmerOfPossibility copy() {
        return new ShimmerOfPossibility(this);
    }
}
