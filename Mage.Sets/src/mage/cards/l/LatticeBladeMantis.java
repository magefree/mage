package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LatticeBladeMantis extends CardImpl {

    public LatticeBladeMantis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Lattice-Blade Mantis enters the battlefield with two oil counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance(2)),
                "with two oil counters on it"
        ));

        // Whenever Lattice-Blade Mantis attacks, you may remove an oil counter from it. If you do, untap it and it gets +1/+1 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new UntapSourceEffect().setText("untap it"),
                new RemoveCountersSourceCost(CounterType.OIL.createInstance())
        ).addEffect(new BoostSourceEffect(1, 1, Duration.EndOfTurn, "and it"))));
    }

    private LatticeBladeMantis(final LatticeBladeMantis card) {
        super(card);
    }

    @Override
    public LatticeBladeMantis copy() {
        return new LatticeBladeMantis(this);
    }
}
