package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.DoubleCounterOnEachPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BristlyBillSpineSower extends CardImpl {

    public BristlyBillSpineSower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Landfall -- Whenever a land enters the battlefield under your control, put a +1/+1 counter on target creature.
        Ability ability = new LandfallAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {3}{G}{G}: Double the number of +1/+1 counters on each creature you control.
        this.addAbility(new SimpleActivatedAbility(new DoubleCounterOnEachPermanentEffect(
                CounterType.P1P1, StaticFilters.FILTER_CONTROLLED_CREATURE
        ), new ManaCostsImpl<>("{3}{G}{G}")));
    }

    private BristlyBillSpineSower(final BristlyBillSpineSower card) {
        super(card);
    }

    @Override
    public BristlyBillSpineSower copy() {
        return new BristlyBillSpineSower(this);
    }
}
