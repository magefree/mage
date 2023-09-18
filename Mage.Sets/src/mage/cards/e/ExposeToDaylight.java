package mage.cards.e;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExposeToDaylight extends CardImpl {

    public ExposeToDaylight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Destroy target artifact or enchantment. Scry 1.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new ScryEffect(1, false));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
    }

    private ExposeToDaylight(final ExposeToDaylight card) {
        super(card);
    }

    @Override
    public ExposeToDaylight copy() {
        return new ExposeToDaylight(this);
    }
}
