
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class DarkInquiry extends CardImpl {

    public DarkInquiry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target opponent reveals their hand. You choose a non land card from it. That player discards that card.
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(StaticFilters.FILTER_CARD_A_NON_LAND));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private DarkInquiry(final DarkInquiry card) {
        super(card);
    }

    @Override
    public DarkInquiry copy() {
        return new DarkInquiry(this);
    }
}
