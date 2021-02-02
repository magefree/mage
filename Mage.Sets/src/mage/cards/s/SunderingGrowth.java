
package mage.cards.s;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.PopulateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author LevleX2
 */
public final class SunderingGrowth extends CardImpl {

    public SunderingGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G/W}{G/W}");


        // Destroy target artifact or enchantment, then populate.
        // (Create a token that's a copy of a creature token you control.)
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new PopulateEffect("then"));
    }

    private SunderingGrowth(final SunderingGrowth card) {
        super(card);
    }

    @Override
    public SunderingGrowth copy() {
        return new SunderingGrowth(this);
    }
}
