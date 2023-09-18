package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;

/**
 *
 * @author LevelX2
 */
public final class Impulse extends CardImpl {

    public Impulse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Look at the top four cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(4, 1, PutCards.HAND, PutCards.BOTTOM_ANY));
    }

    private Impulse(final Impulse card) {
        super(card);
    }

    @Override
    public Impulse copy() {
        return new Impulse(this);
    }
}
