package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OmnathLocusOfTheRoil extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.ELEMENTAL, "Elemental you control");
    private static final DynamicValue xValue
            = new PermanentsOnBattlefieldCount(filter);
    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_LAND, ComparisonType.MORE_THAN, 7);

    public OmnathLocusOfTheRoil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Omnath, Locus of the Roil enters the battlefield, it deals damage to any target equal to the number of Elementals you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DamageTargetEffect(xValue)
                        .setText("it deals damage to any target equal to the number of Elementals you control")
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Whenever a land enters the battlefield under your control, put a +1/+1 counter on target Elemental you control. If you control eight or more lands, draw a card.
        ability = new LandfallAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), condition,
                "If you control eight or more lands, draw a card."
        ));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private OmnathLocusOfTheRoil(final OmnathLocusOfTheRoil card) {
        super(card);
    }

    @Override
    public OmnathLocusOfTheRoil copy() {
        return new OmnathLocusOfTheRoil(this);
    }
}
