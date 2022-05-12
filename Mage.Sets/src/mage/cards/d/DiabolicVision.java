package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class DiabolicVision extends CardImpl {

    public DiabolicVision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}{B}");

        // Look at the top five cards of your library. Put one of them into your hand and the rest on top of your library in any order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(5, 1, PutCards.HAND, PutCards.TOP_ANY));
    }

    private DiabolicVision(final DiabolicVision card) {
        super(card);
    }

    @Override
    public DiabolicVision copy() {
        return new DiabolicVision(this);
    }
}
