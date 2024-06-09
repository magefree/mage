package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.PowerTargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class LegacysAllure extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power less than or equal to the number of treasure counters on {this}");

    public LegacysAllure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{U}");

        // At the beginning of your upkeep, you may put a treasure counter on Legacy's Allure.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.TREASURE.createInstance(), true), TargetController.YOU, true));

        // Sacrifice Legacy's Allure: Gain control of target creature with power less than or equal to the number of treasure counters on Legacy's Allure.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainControlTargetEffect(Duration.EndOfGame, true), new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        ability.setTargetAdjuster(new PowerTargetAdjuster(new CountersSourceCount(CounterType.TREASURE), ComparisonType.OR_LESS));
        this.addAbility(ability);
    }

    private LegacysAllure(final LegacysAllure card) {
        super(card);
    }

    @Override
    public LegacysAllure copy() {
        return new LegacysAllure(this);
    }
}
