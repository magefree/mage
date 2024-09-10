package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.HaventCastSpellFromHandThisTurnCondition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class InventiveWingsmith extends CardImpl {

    private static final Condition condition = new CompoundCondition(
            "if you haven't cast a spell from your hand this turn and {this} doesn't have a flying counter on it",
            HaventCastSpellFromHandThisTurnCondition.instance,
            new SourceHasCounterCondition(CounterType.FLYING, 0, 0)
    );

    public InventiveWingsmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // At the beginning of your end step, if you haven't cast a spell from your hand this turn and Inventive Wingsmith doesn't have a flying counter on it, put a flying counter on it.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.FLYING.createInstance()),
                TargetController.YOU, condition, false
        ).withRuleTextReplacement(true).addHint(HaventCastSpellFromHandThisTurnCondition.hint));
    }

    private InventiveWingsmith(final InventiveWingsmith card) {
        super(card);
    }

    @Override
    public InventiveWingsmith copy() {
        return new InventiveWingsmith(this);
    }
}
