
package mage.cards.s;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ConvokeAbility;
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
public final class SunderingVitae extends CardImpl {

    public SunderingVitae(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");

        // Convoke
        this.addAbility(new ConvokeAbility());
        // Destroy target artifact or enchantment.
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
    }

    private SunderingVitae(final SunderingVitae card) {
        super(card);
    }

    @Override
    public SunderingVitae copy() {
        return new SunderingVitae(this);
    }
}
