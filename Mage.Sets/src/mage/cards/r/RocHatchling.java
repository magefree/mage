package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class RocHatchling extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.SHELL, ComparisonType.EQUAL_TO, 0);

    public RocHatchling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Roc Hatchling enters the battlefield with four shell counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.SHELL.createInstance(4)),
                "with four shell counters on it"
        ));

        // At the beginning of your upkeep, remove a shell counter from Roc Hatchling.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new RemoveCounterSourceEffect(CounterType.SHELL.createInstance())
        ));

        // As long as Roc Hatchling has no shell counters on it, it gets +3/+2 and has flying.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(3, 2, Duration.WhileOnBattlefield),
                condition, "as long as {this} has no shell counters on it, it gets +3/+2"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FlyingAbility.getInstance()),
                condition, "and has flying"
        ));
        this.addAbility(ability);
    }

    private RocHatchling(final RocHatchling card) {
        super(card);
    }

    @Override
    public RocHatchling copy() {
        return new RocHatchling(this);
    }
}
