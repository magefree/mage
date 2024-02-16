package mage.cards.k;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.GetKickerXValue;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
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
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(CounterType.FEATHER.createInstance(), GetKickerXValue.instance, true));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, KickedCondition.ONCE, "When {this} enters the battlefield, if it was kicked, put X feather counters on it."));

        // Other Bird creatures get +1/+1 for each feather counter on Kangee, Aerie Keeper.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(new CountersSourceCount(CounterType.FEATHER), new CountersSourceCount(CounterType.FEATHER), Duration.WhileOnBattlefield, filter, true, "Other Bird creatures get +1/+1 for each feather counter on {this}.")));
    }

    private KangeeAerieKeeper(final KangeeAerieKeeper card) {
        super(card);
    }

    @Override
    public KangeeAerieKeeper copy() {
        return new KangeeAerieKeeper(this);
    }
}
