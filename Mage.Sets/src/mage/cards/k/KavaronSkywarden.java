package mage.cards.k;

import mage.MageInt;
import mage.abilities.condition.common.VoidCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.watchers.common.VoidWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class KavaronSkywarden extends CardImpl {

    public KavaronSkywarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.KAVU);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Void -- At the beginning of your end step, if a nonland permanent left the battlefield this turn or a spell was warped this turn, put a +1/+1 counter on this creature.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()))
                .withInterveningIf(VoidCondition.instance)
                .setAbilityWord(AbilityWord.VOID)
                .addHint(VoidCondition.getHint()), new VoidWatcher());
    }

    private KavaronSkywarden(final KavaronSkywarden card) {
        super(card);
    }

    @Override
    public KavaronSkywarden copy() {
        return new KavaronSkywarden(this);
    }
}
