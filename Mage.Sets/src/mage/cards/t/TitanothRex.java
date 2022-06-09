package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TitanothRex extends CardImpl {

    public TitanothRex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(11);
        this.toughness = new MageInt(11);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Cycling {1}{G}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}{G}")));

        // When you cycle Titanoth Rex, put a trample counter on target creature you control.
        Ability ability = new CycleTriggeredAbility(
                new AddCountersTargetEffect(CounterType.TRAMPLE.createInstance())
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private TitanothRex(final TitanothRex card) {
        super(card);
    }

    @Override
    public TitanothRex copy() {
        return new TitanothRex(this);
    }
}
