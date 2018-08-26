
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.AscendAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LevelX2
 */
public final class MausoleumHarpy extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature you control");

    static {
        filter.add(new AnotherPredicate());
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

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
                new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, filter),
                CitysBlessingCondition.instance,
                "Whenever another creature you control dies, if you have the city's blessing, put a +1/+1 counter on {this}. "));

    }

    public MausoleumHarpy(final MausoleumHarpy card) {
        super(card);
    }

    @Override
    public MausoleumHarpy copy() {
        return new MausoleumHarpy(this);
    }
}
