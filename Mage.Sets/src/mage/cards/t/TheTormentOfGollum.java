package mage.cards.t;

import java.util.UUID;

import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

/**
 *
 * @author TheElk801
 */
public final class TheTormentOfGollum extends CardImpl {

    public TheTormentOfGollum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Target opponent reveals their hand. You choose a nonland card from it. That player discards that card.
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(StaticFilters.FILTER_CARD_A_NON_LAND));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Amass Orcs 2.
        this.getSpellAbility().addEffect(new AmassEffect(2, SubType.ORC).concatBy("<br>"));
    }

    private TheTormentOfGollum(final TheTormentOfGollum card) {
        super(card);
    }

    @Override
    public TheTormentOfGollum copy() {
        return new TheTormentOfGollum(this);
    }
}
