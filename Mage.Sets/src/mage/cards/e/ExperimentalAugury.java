package mage.cards.e;

import java.util.UUID;

import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;

/**
 * @author TheElk801
 */
public final class ExperimentalAugury extends CardImpl {

    public ExperimentalAugury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Look at the top three cards of your library. Put one of them into your hand and rest on the bottom of your library in any order. Proliferate.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                3, 1, PutCards.HAND, PutCards.BOTTOM_ANY
        ));
        this.getSpellAbility().addEffect(new ProliferateEffect());
    }

    private ExperimentalAugury(final ExperimentalAugury card) {
        super(card);
    }

    @Override
    public ExperimentalAugury copy() {
        return new ExperimentalAugury(this);
    }
}
