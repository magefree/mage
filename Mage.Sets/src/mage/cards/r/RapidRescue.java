package mage.cards.r;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class RapidRescue extends CardImpl {

    public RapidRescue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Mill two cards. You may put a permanent card from among the milled cards into your hand. You gain 2 life.
        this.getSpellAbility().addEffect(new MillThenPutInHandEffect(2, StaticFilters.FILTER_CARD_A_PERMANENT));
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
    }

    private RapidRescue(final RapidRescue card) {
        super(card);
    }

    @Override
    public RapidRescue copy() {
        return new RapidRescue(this);
    }
}
