package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.TrampleAbility;
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
public final class ShatterskullCharger extends CardImpl {

    private static final Condition condition = new InvertCondition(
            new SourceHasCounterCondition(CounterType.P1P1),
            "{this} doesn't have a +1/+1 counter on it"
    );

    public ShatterskullCharger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Kicker {2}
        this.addAbility(new KickerAbility("{2}"));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // If Shatterskull Charger was kicked, it enters with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), KickedCondition.ONCE,
                "If {this} was kicked, it enters with a +1/+1 counter on it.", ""
        ));

        // At the beginning of your end step, if Shatterskull Charger doesn't have a +1/+1 counter on it, return it to its owner's hand.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new ReturnToHandSourceEffect(true)
                .setText("return it to its owner's hand")).withInterveningIf(condition));
    }

    private ShatterskullCharger(final ShatterskullCharger card) {
        super(card);
    }

    @Override
    public ShatterskullCharger copy() {
        return new ShatterskullCharger(this);
    }
}
