
package mage.cards.d;

import java.util.UUID;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class DownhillCharge extends CardImpl {

     private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Mountain");

     static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public DownhillCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        // You may sacrifice a Mountain rather than pay Downhill Charge's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new SacrificeTargetCost(new TargetControlledPermanent(filter))));
        // Target creature gets +X/+0 until end of turn, where X is the number of Mountains you control.
        Effect effect = new BoostTargetEffect(new PermanentsOnBattlefieldCount(filter), StaticValue.get(0), Duration.EndOfTurn, true);
        effect.setText("Target creature gets +X/+0 until end of turn, where X is the number of Mountains you control.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private DownhillCharge(final DownhillCharge card) {
        super(card);
    }

    @Override
    public DownhillCharge copy() {
        return new DownhillCharge(this);
    }
}
