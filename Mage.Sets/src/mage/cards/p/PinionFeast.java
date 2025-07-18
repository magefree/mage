
package mage.cards.p;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PinionFeast extends CardImpl {

    public PinionFeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{G}");

        // Destroy target creature with flying. Bolster 2.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
        this.getSpellAbility().addEffect(new BolsterEffect(2));

    }

    private PinionFeast(final PinionFeast card) {
        super(card);
    }

    @Override
    public PinionFeast copy() {
        return new PinionFeast(this);
    }
}
