package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.IntCompareCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.IntMinusDynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SlumberingTrudge extends CardImpl {

    private static final DynamicValue xValue = new IntMinusDynamicValue(3, GetXValue.instance);
    private static final Condition condition = new SlumberingTrudgeCondition();

    public SlumberingTrudge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // This creature enters with a number of stun counters on it equal to three minus X. If X is 2 or less, it enters tapped.
        Ability ability = new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.STUN.createInstance(), xValue, false),
                "with a number of stun counters on it equal to three minus X.");
        ability.addEffect(new ConditionalOneShotEffect(new TapSourceEffect(true), condition, null));
        this.addAbility(ability);
    }

    private SlumberingTrudge(final SlumberingTrudge card) {
        super(card);
    }

    @Override
    public SlumberingTrudge copy() {
        return new SlumberingTrudge(this);
    }
}

class SlumberingTrudgeCondition extends IntCompareCondition {

    SlumberingTrudgeCondition() {
        super(ComparisonType.OR_LESS, 2);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        return GetXValue.instance.calculate(game, source, null);
    }

    @Override
    public String toString() {
        return "If X is 2 or less";
    }
}