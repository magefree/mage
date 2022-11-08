package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.EachTwoManaSpentToCastValue;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClayChampion extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(EachTwoManaSpentToCastValue.GREEN, 3);

    public ClayChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{X}{4}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Clay Champion enters the battlefield with three +1/+1 counters on it for each {G}{G} spent to cast it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(), xValue, true),
                null, null, "with three +1/+1 counters on it for each {G}{G} spent to cast it"
        ));

        // When Clay Champion enters the battlefield, choose up to two other target creatures you control. For each {W}{W} spent to cast Clay Champion, put a +1/+1 counter on each of them.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(
                        CounterType.P1P1.createInstance(0),
                        EachTwoManaSpentToCastValue.WHITE
                ).setText("choose up to two other target creatures you control. " +
                        "For each {W}{W} spent to cast {this}, put a +1/+1 counter on each of them")
        );
        ability.addTarget(new TargetPermanent(
                0, 2, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        ));
        this.addAbility(ability);
    }

    private ClayChampion(final ClayChampion card) {
        super(card);
    }

    @Override
    public ClayChampion copy() {
        return new ClayChampion(this);
    }
}
