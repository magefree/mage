package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldCastTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.*;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.BasePowerPredicate;

/**
 *
 * @author jimga150
 */
public final class RapidAugmenter extends CardImpl {

    private static final FilterPermanent filterBP1 = new FilterControlledCreaturePermanent("another creature you control with base power 1");

    static {
        filterBP1.add(AnotherPredicate.instance);
        filterBP1.add(new BasePowerPredicate(ComparisonType.EQUAL_TO, 1));
    }

    public RapidAugmenter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");
        
        this.subtype.add(SubType.OTTER);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever another creature you control with base power 1 enters, it gains haste until end of turn.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new GainAbilityTargetEffect(HasteAbility.getInstance()), filterBP1, false, SetTargetPointer.PERMANENT);
        this.addAbility(ability);

        // Whenever another creature you control enters, if it wasn't cast, put a +1/+1 counter on Rapid Augmenter
        // and Rapid Augmenter can't be blocked this turn.
        Ability ability2 = new EntersBattlefieldCastTriggeredAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(
                CounterType.P1P1.createInstance()), StaticFilters.FILTER_ANOTHER_CREATURE,
                false, SetTargetPointer.PERMANENT, false);
        ability2.addEffect(new CantBeBlockedSourceEffect(Duration.EndOfTurn).concatBy("and"));
        this.addAbility(ability2);
    }

    private RapidAugmenter(final RapidAugmenter card) {
        super(card);
    }

    @Override
    public RapidAugmenter copy() {
        return new RapidAugmenter(this);
    }
}
