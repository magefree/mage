package mage.cards.s;

import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author North
 */
public final class SleightOfHand extends CardImpl {

    public SleightOfHand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Look at the top two cards of your library. Put one of them into your hand and the other on the bottom of your library.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(2, 1, PutCards.HAND, PutCards.BOTTOM_ANY));
    }

    private SleightOfHand(final SleightOfHand card) {
        super(card);
    }

    @Override
    public SleightOfHand copy() {
        return new SleightOfHand(this);
    }
}
