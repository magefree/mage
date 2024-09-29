package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SourcePhaseInTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoUnlessControllerPaysEffect;
import mage.abilities.effects.common.PhaseOutSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PhasingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class WarpingWurm extends CardImpl {

    public WarpingWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.subtype.add(SubType.WURM);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Phasing
        this.addAbility(PhasingAbility.getInstance());

        // At the beginning of your upkeep, Warping Wurm phases out unless you pay {2}{G}{U}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DoUnlessControllerPaysEffect(
                        new PhaseOutSourceEffect(),
                        new ManaCostsImpl<>("{2}{G}{U}")
                ).setText("{this} phases out unless you pay {2}{G}{U}"),
                TargetController.YOU, false
        ));

        // When Warping Wurm phases in, put a +1/+1 counter on it.
        this.addAbility(new SourcePhaseInTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false));
    }

    private WarpingWurm(final WarpingWurm card) {
        super(card);
    }

    @Override
    public WarpingWurm copy() {
        return new WarpingWurm(this);
    }
}
