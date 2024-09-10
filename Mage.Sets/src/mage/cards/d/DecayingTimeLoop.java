package mage.cards.d;

import mage.abilities.effects.common.discard.DiscardHandDrawSameNumberSourceEffect;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DecayingTimeLoop extends CardImpl {

    public DecayingTimeLoop(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Discard all the cards in your hand, then draw that many cards.
        this.getSpellAbility().addEffect(new DiscardHandDrawSameNumberSourceEffect());

        // Retrace
        this.addAbility(new RetraceAbility(this));
    }

    private DecayingTimeLoop(final DecayingTimeLoop card) {
        super(card);
    }

    @Override
    public DecayingTimeLoop copy() {
        return new DecayingTimeLoop(this);
    }
}
