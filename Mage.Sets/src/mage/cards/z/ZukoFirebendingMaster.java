package mage.cards.z;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.IsPhaseCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersControllerCount;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.keyword.FirebendingAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class ZukoFirebendingMaster extends CardImpl {

    private static final DynamicValue xValue = new CountersControllerCount(CounterType.EXPERIENCE);
    private static final Condition condition = new IsPhaseCondition(TurnPhase.COMBAT);

    public ZukoFirebendingMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Firebending X where X is the number of experience counters you have.
        this.addAbility(new FirebendingAbility(xValue));

        // Whenever you cast a spell during combat, you get an experience counter.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersPlayersEffect(CounterType.EXPERIENCE.createInstance(), TargetController.YOU),
                StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.SPELL
        ).withTriggerCondition(condition));
    }

    private ZukoFirebendingMaster(final ZukoFirebendingMaster card) {
        super(card);
    }

    @Override
    public ZukoFirebendingMaster copy() {
        return new ZukoFirebendingMaster(this);
    }
}
