package mage.cards.d;

import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DrawnFromDreams extends CardImpl {

    public DrawnFromDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");

        // Look at the top seven cards of your library. Put two of them into your hand and the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(7, 2, PutCards.HAND, PutCards.BOTTOM_RANDOM));
    }

    private DrawnFromDreams(final DrawnFromDreams card) {
        super(card);
    }

    @Override
    public DrawnFromDreams copy() {
        return new DrawnFromDreams(this);
    }
}
