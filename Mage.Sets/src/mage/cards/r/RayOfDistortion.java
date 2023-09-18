
package mage.cards.r;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author cbt33
 */
public final class RayOfDistortion extends CardImpl {
    
    public RayOfDistortion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}");

        // Destroy target artifact or enchantment.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        // Flashback {4}{W}{W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{4}{W}{W}")));
    }

    private RayOfDistortion(final RayOfDistortion card) {
        super(card);
    }

    @Override
    public RayOfDistortion copy() {
        return new RayOfDistortion(this);
    }
}
