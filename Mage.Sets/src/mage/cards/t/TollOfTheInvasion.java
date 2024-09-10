package mage.cards.t;

import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TollOfTheInvasion extends CardImpl {

    public TollOfTheInvasion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target opponent reveals their hand. You choose a nonland card from it. That player discards that card.
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(StaticFilters.FILTER_CARD_NON_LAND));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Amass 1.
        this.getSpellAbility().addEffect(new AmassEffect(1, SubType.ZOMBIE).concatBy("<br>"));
    }

    private TollOfTheInvasion(final TollOfTheInvasion card) {
        super(card);
    }

    @Override
    public TollOfTheInvasion copy() {
        return new TollOfTheInvasion(this);
    }
}
