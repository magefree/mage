package mage.cards.r;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RomanticRendezvous extends CardImpl {

    public RomanticRendezvous(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Discard a card, then draw two cards.
        this.getSpellAbility().addEffect(new DiscardControllerEffect(1));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2).concatBy(", then"));
    }

    private RomanticRendezvous(final RomanticRendezvous card) {
        super(card);
    }

    @Override
    public RomanticRendezvous copy() {
        return new RomanticRendezvous(this);
    }
}
