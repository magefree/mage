package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldOneOrMoreTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.BasePowerPredicate;
import mage.filter.predicate.mageobject.BaseToughnessPredicate;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class BessSoulNourisher extends CardImpl {

    static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other creatures with base power and toughness 1/1");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new BasePowerPredicate(ComparisonType.EQUAL_TO, 1));
        filter.add(new BaseToughnessPredicate(ComparisonType.EQUAL_TO, 1));
    }

    public BessSoulNourisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.addSubType(SubType.HUMAN, SubType.CITIZEN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever one or more other creatures with base power and toughness 1/1 enter the battlefield under your control,
        // put a +1/+1 counter on Bess, Soul Nourisher.
        this.addAbility(new EntersBattlefieldOneOrMoreTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                filter,
                TargetController.YOU)
        );

        // Whenever Bess attacks, each other creature you control with base power and toughness 1/1 gets
        // +X/+X until end of turn, where X is the number of +1/+1 counters on Bess.
        DynamicValue xValue = new CountersSourceCount(CounterType.P1P1);
        this.addAbility(new AttacksTriggeredAbility(new BoostControlledEffect(
                xValue, xValue, Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE, true
        ).setText("each other creature you control with base power and toughness 1/1 " +
                "gets +X/+X until end of turn, where X is the number of +1/+1 counters on {this}"),
                false));
    }

    private BessSoulNourisher(final BessSoulNourisher card) {
        super(card);
    }

    @Override
    public BessSoulNourisher copy() {
        return new BessSoulNourisher(this);
    }
}
