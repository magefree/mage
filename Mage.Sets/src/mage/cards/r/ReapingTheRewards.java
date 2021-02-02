
package mage.cards.r;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class ReapingTheRewards extends CardImpl {

    public ReapingTheRewards(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Buyback-Sacrifice a land.
        this.addAbility(new BuybackAbility(new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent("a land")))));

        // You gain 2 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
    }

    private ReapingTheRewards(final ReapingTheRewards card) {
        super(card);
    }

    @Override
    public ReapingTheRewards copy() {
        return new ReapingTheRewards(this);
    }
}
