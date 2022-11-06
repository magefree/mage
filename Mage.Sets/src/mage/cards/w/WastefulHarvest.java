package mage.cards.w;

import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WastefulHarvest extends CardImpl {

    public WastefulHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Mill five cards. You may put a permanent card from among the cards milled this way into your hand.
        this.getSpellAbility().addEffect(new MillThenPutInHandEffect(5, StaticFilters.FILTER_CARD_A_PERMANENT));
    }

    private WastefulHarvest(final WastefulHarvest card) {
        super(card);
    }

    @Override
    public WastefulHarvest copy() {
        return new WastefulHarvest(this);
    }
}
