package mage.cards.z;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.abilities.effects.common.continuous.NonbasicLandsAreMountainsEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZhaoTheMoonSlayer extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.CONQUEROR);

    public ZhaoTheMoonSlayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // Nonbasic lands enter tapped.
        this.addAbility(new SimpleStaticAbility(new PermanentsEnterBattlefieldTappedEffect(StaticFilters.FILTER_LANDS_NONBASIC)));

        // {7}: Put a conqueror counter on Zhao.
        this.addAbility(new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.CONQUEROR.createInstance()), new GenericManaCost(7)
        ));

        // As long as Zhao has a conqueror counter on him, nonbasic lands are Mountains.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new NonbasicLandsAreMountainsEffect(), condition,
                "as long as {this} has a conqueror counter on him, nonbasic lands are Mountains"
        )));
    }

    private ZhaoTheMoonSlayer(final ZhaoTheMoonSlayer card) {
        super(card);
    }

    @Override
    public ZhaoTheMoonSlayer copy() {
        return new ZhaoTheMoonSlayer(this);
    }
}
