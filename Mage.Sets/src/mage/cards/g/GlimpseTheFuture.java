package mage.cards.g;

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
public final class GlimpseTheFuture extends CardImpl {

    public GlimpseTheFuture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Look at the top three cards of your library. Put one of them into your hand and the rest into your graveyard.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(3, 1, PutCards.HAND, PutCards.GRAVEYARD));
    }

    private GlimpseTheFuture(final GlimpseTheFuture card) {
        super(card);
    }

    @Override
    public GlimpseTheFuture copy() {
        return new GlimpseTheFuture(this);
    }
}
