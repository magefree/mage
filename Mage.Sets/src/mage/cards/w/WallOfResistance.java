package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceDealtDamageCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.watchers.common.DamageDoneWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WallOfResistance extends CardImpl {

    private static final Condition condition = new SourceDealtDamageCondition(1);

    public WallOfResistance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each end step, if Wall of Resistance was dealt damage this turn, put a +0/+1 counter on it.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new AddCountersSourceEffect(CounterType.P0P1.createInstance()),
                        TargetController.ANY, false
                ), condition, "At the beginning of each end step, " +
                "if {this} was dealt damage this turn, put a +0/+1 counter on it."
        ), new DamageDoneWatcher());
    }

    private WallOfResistance(final WallOfResistance card) {
        super(card);
    }

    @Override
    public WallOfResistance copy() {
        return new WallOfResistance(this);
    }
}
