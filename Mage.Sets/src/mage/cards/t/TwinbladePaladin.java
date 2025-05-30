package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.LifeCompareCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TwinbladePaladin extends CardImpl {

    public TwinbladePaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you gain life, put a +1/+1 counter on Twinblade Paladin.
        this.addAbility(new GainLifeControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false
        ));

        // As long as you have 25 or more life, Twinblade Paladin has double strike.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield),
                new LifeCompareCondition(TargetController.YOU, ComparisonType.OR_GREATER, 25),
                "As long as you have 25 or more life, {this} has double strike."
        )));
    }

    private TwinbladePaladin(final TwinbladePaladin card) {
        super(card);
    }

    @Override
    public TwinbladePaladin copy() {
        return new TwinbladePaladin(this);
    }
}
