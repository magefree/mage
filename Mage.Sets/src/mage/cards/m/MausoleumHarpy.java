package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.CitysBlessingHint;
import mage.abilities.keyword.AscendAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MausoleumHarpy extends CardImpl {

    public MausoleumHarpy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.HARPY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ascend (If you control ten or more permanents, you get the city's blessing for the rest of the game.)
        this.addAbility(new AscendAbility());

        // Whenever another creature you control dies, if you have the city's blessing, put a +1/+1 counter on Mausoleum Harpy.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL),
                CitysBlessingCondition.instance,
                "Whenever another creature you control dies, if you have the city's blessing, put a +1/+1 counter on {this}. ")
                .addHint(CitysBlessingHint.instance));

    }

    private MausoleumHarpy(final MausoleumHarpy card) {
        super(card);
    }

    @Override
    public MausoleumHarpy copy() {
        return new MausoleumHarpy(this);
    }
}
