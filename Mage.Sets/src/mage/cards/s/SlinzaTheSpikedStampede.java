package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.EntersWithCountersControlledEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SlinzaTheSpikedStampede extends CardImpl {

    private static final FilterCard filter = new FilterCard("Beast spells");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent(SubType.BEAST, "Beast creature");
    private static final FilterPermanent filter3 = new FilterCreaturePermanent("creature with power 4 or greater");

    static {
        filter.add(SubType.BEAST.getPredicate());
        filter3.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public SlinzaTheSpikedStampede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Beast spells you cast cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 2)));

        // Each other Beast creature you control enters with an additional +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(new EntersWithCountersControlledEffect(
                filter2, CounterType.P1P1.createInstance(), true
        )));

        // Whenever Slinza or another creature with power 4 or greater enters, you may pay {1}{R/G}. When you do, Slinza fights target creature you don't control.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new FightTargetSourceEffect(), false);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(new DoWhenCostPaid(
                ability, new ManaCostsImpl<>("{1}{R/G}"), "Pay {1}{R/G}?"
        ), filter3, false, false));
    }

    private SlinzaTheSpikedStampede(final SlinzaTheSpikedStampede card) {
        super(card);
    }

    @Override
    public SlinzaTheSpikedStampede copy() {
        return new SlinzaTheSpikedStampede(this);
    }
}
