package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FaithboundJudge extends CardImpl {

    private static final Condition condition1 = new SourceHasCounterCondition(CounterType.JUDGMENT, 0, 2);
    private static final Condition condition2 = new SourceHasCounterCondition(CounterType.JUDGMENT, 3);

    public FaithboundJudge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.secondSideCardClazz = mage.cards.s.SinnersJudgment.class;

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // At the beginning of your upkeep, if Faithbound Judge has two or fewer judgment counters on it, put a judgment counter on it.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        new AddCountersSourceEffect(CounterType.JUDGMENT.createInstance()),
                        TargetController.YOU, false
                ), condition1, "At the beginning of your upkeep, if {this} has " +
                "two or fewer judgment counters on it, put a judgment counter on it."
        ));

        // As long as Faithbound Judge has three or more judgment counters on it, it can attack as though it didn't have defender.
        this.addAbility(new SimpleStaticAbility(new ConditionalAsThoughEffect(
                new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.WhileOnBattlefield), condition2
        ).setText("as long as {this} has three or more judgment counters on it," +
                " it can attack as though it didn't have defender")));

        // Disturb {5}{W}{W}
        this.addAbility(new TransformAbility());
        this.addAbility(new DisturbAbility(new ManaCostsImpl<>("{5}{W}{W}")));
    }

    private FaithboundJudge(final FaithboundJudge card) {
        super(card);
    }

    @Override
    public FaithboundJudge copy() {
        return new FaithboundJudge(this);
    }
}
