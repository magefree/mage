package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.UnearthAbility;
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
public final class SimianSimulacrum extends CardImpl {

    public SimianSimulacrum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.APE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Simian Simulacrum enters the battlefield, put two +1/+1 counters on target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(2))
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Unearth {2}{G}{G}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{2}{G}{G}")));
    }

    private SimianSimulacrum(final SimianSimulacrum card) {
        super(card);
    }

    @Override
    public SimianSimulacrum copy() {
        return new SimianSimulacrum(this);
    }
}
