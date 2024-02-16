
package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class SpontaneousCombustion extends CardImpl {

    public SpontaneousCombustion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}{R}");

        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        this.getSpellAbility().addEffect(new DamageAllEffect(3, new FilterCreaturePermanent()));
    }

    private SpontaneousCombustion(final SpontaneousCombustion card) {
        super(card);
    }

    @Override
    public SpontaneousCombustion copy() {
        return new SpontaneousCombustion(this);
    }
}
