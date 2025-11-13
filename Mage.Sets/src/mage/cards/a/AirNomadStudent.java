package mage.cards.a;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.AttackedThisTurnSourceCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AirNomadStudent extends CardImpl {

    private static final Condition condition = new InvertCondition(
            AttackedThisTurnSourceCondition.instance, "{this} didn't attack this turn"
    );

    public AirNomadStudent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your end step, if this creature didn't attack this turn, put a +1/+1 counter on it.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                .setText("put a +1/+1 counter on it")).withInterveningIf(condition));
    }

    private AirNomadStudent(final AirNomadStudent card) {
        super(card);
    }

    @Override
    public AirNomadStudent copy() {
        return new AirNomadStudent(this);
    }
}
