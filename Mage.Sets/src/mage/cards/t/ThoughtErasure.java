package mage.cards.t;

import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThoughtErasure extends CardImpl {

    public ThoughtErasure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}{B}");

        // Target opponent reveals their hand. You choose a nonland card from it. That player discards that card.
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(StaticFilters.FILTER_CARD_NON_LAND));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Surveil 1.
        this.getSpellAbility().addEffect(new SurveilEffect(1).concatBy("<br>"));
    }

    private ThoughtErasure(final ThoughtErasure card) {
        super(card);
    }

    @Override
    public ThoughtErasure copy() {
        return new ThoughtErasure(this);
    }
}
