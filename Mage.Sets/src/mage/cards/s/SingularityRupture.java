package mage.cards.s;

import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.MillHalfLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SingularityRupture extends CardImpl {

    public SingularityRupture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{B}{B}");

        // Destroy all creatures, then any number of target players each mill half their library, rounded down.
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));
        this.getSpellAbility().addEffect(new MillHalfLibraryTargetEffect(false)
                .setText(", then any number of target players each mill half their library, rounded down"));
        this.getSpellAbility().addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false));
    }

    private SingularityRupture(final SingularityRupture card) {
        super(card);
    }

    @Override
    public SingularityRupture copy() {
        return new SingularityRupture(this);
    }
}
