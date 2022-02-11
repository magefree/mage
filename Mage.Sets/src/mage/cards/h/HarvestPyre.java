
package mage.cards.h;

import java.util.UUID;
import mage.abilities.costs.common.ExileXFromYourGraveCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward
 */
public final class HarvestPyre extends CardImpl {

    public HarvestPyre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");


        // As an additional cost to cast Harvest Pyre, exile X cards from your graveyard.
        this.getSpellAbility().addCost(new ExileXFromYourGraveCost(StaticFilters.FILTER_CARDS_FROM_YOUR_GRAVEYARD));

        // Harvest Pyre deals X damage to target creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DamageTargetEffect(GetXValue.instance));
    }

    private HarvestPyre(final HarvestPyre card) {
        super(card);
    }

    @Override
    public HarvestPyre copy() {
        return new HarvestPyre(this);
    }
}
