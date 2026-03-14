package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
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
public final class WarBalloon extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.FIRE, 3);

    public WarBalloon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}: Put a fire counter on this Vehicle.
        this.addAbility(new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.FIRE.createInstance()), new GenericManaCost(1)
        ));

        // As long as this Vehicle has three or more fire counters on it, it's an artifact creature.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new AddCardTypeSourceEffect(Duration.WhileOnBattlefield, CardType.ARTIFACT, CardType.CREATURE),
                condition, "as long as {this} has three or more fire counters on it, it's an artifact creature"
        )));

        // Crew 3
        this.addAbility(new CrewAbility(3));
    }

    private WarBalloon(final WarBalloon card) {
        super(card);
    }

    @Override
    public WarBalloon copy() {
        return new WarBalloon(this);
    }
}
