package mage.cards.q;

import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class QuicksilverGeyser extends CardImpl {

    public QuicksilverGeyser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}");

        // Return up to two target nonland permanents to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(0, 2, StaticFilters.FILTER_PERMANENTS_NON_LAND));
    }

    private QuicksilverGeyser(final QuicksilverGeyser card) {
        super(card);
    }

    @Override
    public QuicksilverGeyser copy() {
        return new QuicksilverGeyser(this);
    }
}
