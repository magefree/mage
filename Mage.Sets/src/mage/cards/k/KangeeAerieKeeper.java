package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class KangeeAerieKeeper extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Bird creatures");

    static {
        filter.add(SubType.BIRD.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.FEATHER);

    public KangeeAerieKeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {X}{2}
        this.addAbility(new KickerAbility("{2}{X}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Kangee, Aerie Keeper enters the battlefield, if it was kicked, put X feather counters on it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(
                CounterType.FEATHER.createInstance(), GetXValue.instance, true
        )).withInterveningIf(KickedCondition.ONCE).withRuleTextReplacement(true));

        // Other Bird creatures get +1/+1 for each feather counter on Kangee, Aerie Keeper.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                xValue, xValue, Duration.WhileOnBattlefield, filter, true,
                "Other Bird creatures get +1/+1 for each feather counter on {this}."
        )));
    }

    private KangeeAerieKeeper(final KangeeAerieKeeper card) {
        super(card);
    }

    @Override
    public KangeeAerieKeeper copy() {
        return new KangeeAerieKeeper(this);
    }
}
