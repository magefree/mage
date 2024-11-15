package mage.cards.d;

import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LoneFox
 */
public final class DownhillCharge extends CardImpl {

    private static final DynamicValue xValue2 = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.MOUNTAIN));
    private static final Hint hint = new ValueHint("Mountains you control", xValue2);

     private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.MOUNTAIN, "a Mountain");
     private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            new FilterControlledPermanent(SubType.MOUNTAIN, "Mountains you control"), null
    );

    public DownhillCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        // You may sacrifice a Mountain rather than pay Downhill Charge's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new SacrificeTargetCost(filter)));

        // Target creature gets +X/+0 until end of turn, where X is the number of Mountains you control.
        this.getSpellAbility().addEffect(new BoostTargetEffect(xValue, StaticValue.get(0), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(hint);
    }

    private DownhillCharge(final DownhillCharge card) {
        super(card);
    }

    @Override
    public DownhillCharge copy() {
        return new DownhillCharge(this);
    }
}
