package mage.cards.s;

import mage.MageInt;
import mage.abilities.condition.common.OpponentsLostLifeCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.OpponentsLostLifeHint;
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
public final class SavageGorger extends CardImpl {

    public SavageGorger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, if an opponent lost life this turn, put a +1/+1 counter on Savage Gorger.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()))
                .withInterveningIf(OpponentsLostLifeCondition.instance).addHint(OpponentsLostLifeHint.instance));
    }

    private SavageGorger(final SavageGorger card) {
        super(card);
    }

    @Override
    public SavageGorger copy() {
        return new SavageGorger(this);
    }
}
