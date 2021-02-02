
package mage.cards.k;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author jonubuu
 */
public final class KrosanGrip extends CardImpl {

    public KrosanGrip(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");

        // Split second
        this.addAbility(new SplitSecondAbility());
        // Destroy target artifact or enchantment.
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
    }

    private KrosanGrip(final KrosanGrip card) {
        super(card);
    }

    @Override
    public KrosanGrip copy() {
        return new KrosanGrip(this);
    }
}
