package mage.cards.m;

import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MidnightTilling extends CardImpl {

    public MidnightTilling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Mill four cards, then you may return a permanent card from among them to your hand.
        this.getSpellAbility().addEffect(new MillThenPutInHandEffect(4, StaticFilters.FILTER_CARD_PERMANENT));
    }

    private MidnightTilling(final MidnightTilling card) {
        super(card);
    }

    @Override
    public MidnightTilling copy() {
        return new MidnightTilling(this);
    }
}
