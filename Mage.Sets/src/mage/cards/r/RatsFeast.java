package mage.cards.r;

import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInASingleGraveyard;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RatsFeast extends CardImpl {

    public RatsFeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}");

        // Exile X target cards from a single graveyard.
        this.getSpellAbility().addEffect(new ExileTargetEffect("Exile X target cards from a single graveyard"));
        this.getSpellAbility().addTarget(new TargetCardInASingleGraveyard(1, 1, StaticFilters.FILTER_CARD_CARDS));
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
    }

    private RatsFeast(final RatsFeast card) {
        super(card);
    }

    @Override
    public RatsFeast copy() {
        return new RatsFeast(this);
    }
}
