package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.DescendedThisTurnCondition;
import mage.abilities.dynamicvalue.common.DescendedThisTurnCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.watchers.common.DescendedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeepGoblinSkulltaker extends CardImpl {

    public DeepGoblinSkulltaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // At the beginning of your end step, if you descended this turn, put a +1/+1 counter on Deep Goblin Skulltaker.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                TargetController.YOU, DescendedThisTurnCondition.instance, false
        ).addHint(DescendedThisTurnCount.getHint()), new DescendedWatcher());
    }

    private DeepGoblinSkulltaker(final DeepGoblinSkulltaker card) {
        super(card);
    }

    @Override
    public DeepGoblinSkulltaker copy() {
        return new DeepGoblinSkulltaker(this);
    }
}
