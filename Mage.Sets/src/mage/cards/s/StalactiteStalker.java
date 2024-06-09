package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.DescendedThisTurnCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.DescendedThisTurnCount;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.DescendedWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class StalactiteStalker extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(new SourcePermanentPowerCount(), -1);

    public StalactiteStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // At the beginning of your end step, if you descended this turn, put a +1/+1 counter on Stalactite Stalker.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                TargetController.YOU, DescendedThisTurnCondition.instance, false
        ).addHint(DescendedThisTurnCount.getHint()), new DescendedWatcher());

        // {2}{B}, Sacrifice Stalactite Stalker: Target creature gets -X/-X until end of turn, where X is Stalactite Stalker's power.
        Ability ability = new SimpleActivatedAbility(
                new BoostTargetEffect(xValue, xValue)
                        .setText("target creature gets -X/-X until end of turn, where X is {this}'s power"),
                new ManaCostsImpl<>("{2}{B}")
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private StalactiteStalker(final StalactiteStalker card) {
        super(card);
    }

    @Override
    public StalactiteStalker copy() {
        return new StalactiteStalker(this);
    }
}
