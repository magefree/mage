package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;

import java.util.*;
import java.util.stream.IntStream;

/**
 * @author TheElk801
 */
public final class GavelOfTheRighteous extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(null);

    public GavelOfTheRighteous(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // At the beginning of combat on your turn, put a charge counter on Gavel of the Righteous.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), TargetController.YOU, false
        ));

        // Equipped creature gets +1/+1 for each counter on Gavel of the Righteous.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(xValue, xValue)));

        // As long as Gavel of the Righteous has four or more counters on it, equipped creature has double strike.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(DoubleStrikeAbility.getInstance(), AttachmentType.EQUIPMENT),
                GavelOfTheRighteousCondition.instance, "as long as {this} has four " +
                "or more counters on it, equipped creature has double strike"
        )));

        // Equip—Pay {3} or remove a counter from Gavel of the Righteous.
        this.addAbility(new EquipAbility(
                Outcome.BoostCreature,
                new OrCost(
                        "Pay {3} or remove a counter from {this}",
                        new GenericManaCost(3), new RemoveCountersSourceCost()
                ),
                false
        ));
    }

    private GavelOfTheRighteous(final GavelOfTheRighteous card) {
        super(card);
    }

    @Override
    public GavelOfTheRighteous copy() {
        return new GavelOfTheRighteous(this);
    }
}

enum GavelOfTheRighteousCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional.of(source.getSourcePermanentIfItStillExists(game))
                .filter(Objects::nonNull)
                .map(permanent -> permanent.getCounters(game))
                .map(HashMap::values)
                .map(Collection::stream)
                .map(x -> x.mapToInt(Counter::getCount))
                .map(IntStream::sum)
                .orElse(0) >= 4;
    }
}
