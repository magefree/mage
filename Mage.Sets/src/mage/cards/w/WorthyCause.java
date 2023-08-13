
package mage.cards.w;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesToughness;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class WorthyCause extends CardImpl {

    public WorthyCause(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Buyback {2}
        this.addAbility(new BuybackAbility("{2}"));

        // As an additional cost to cast Worthy Cause, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));

        // You gain life equal to the sacrificed creature's toughness.
        Effect effect = new GainLifeEffect(SacrificeCostCreaturesToughness.instance);
        effect.setText("You gain life equal to the sacrificed creature's toughness");
        this.getSpellAbility().addEffect(effect);
    }

    private WorthyCause(final WorthyCause card) {
        super(card);
    }

    @Override
    public WorthyCause copy() {
        return new WorthyCause(this);
    }
}
