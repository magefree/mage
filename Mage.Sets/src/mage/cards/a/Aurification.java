package mage.cards.a;

import mage.abilities.common.DealsDamageToYouAllTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.RemoveAllCountersAllEffect;
import mage.abilities.effects.common.continuous.BecomesSubtypeAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author andyfries
 */

public final class Aurification extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Each creature with a gold counter on it");

    static {
        filter.add(CounterType.GOLD.getPredicate());
    }

    static final String rule = "Each creature with a gold counter on it is a Wall in addition to its other creature types and has defender.";

    public Aurification(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // Whenever a creature deals damage to you, put a gold counter on it.
        this.addAbility(new DealsDamageToYouAllTriggeredAbility(StaticFilters.FILTER_PERMANENT_CREATURE,
                new AddCountersTargetEffect(CounterType.GOLD.createInstance()).setText("put a gold counter on it")));

        // Each creature with a gold counter on it is a Wall in addition to its other creature types and has defender.
        BecomesSubtypeAllEffect becomesSubtypeAllEffect = new BecomesSubtypeAllEffect(Duration.WhileOnBattlefield, Arrays.asList(SubType.WALL), filter, false);
        becomesSubtypeAllEffect.setText("");

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, becomesSubtypeAllEffect));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(DefenderAbility.getInstance(), Duration.WhileOnBattlefield, filter, rule)));

        // When Aurification leaves the battlefield, remove all gold counters from all creatures.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new RemoveAllCountersAllEffect(
                CounterType.GOLD, StaticFilters.FILTER_PERMANENT_CREATURES), false));
    }

    private Aurification(final Aurification card) {
        super(card);
    }

    @Override
    public Aurification copy() {
        return new Aurification(this);
    }
}
