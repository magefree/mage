package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

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
        ability.addTarget(new TargetCreaturePermanent(0, 0, filter, false));
        ability.setTargetAdjuster(LegacysAllureAdjuster.instance);
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

enum LegacysAllureAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(ability.getSourceId());
        if (sourcePermanent != null) {
            int xValue = sourcePermanent.getCounters(game).getCount(CounterType.TREASURE);
            FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature with power less than or equal to " + xValue);
            filter2.add(new PowerPredicate(ComparisonType.FEWER_THAN, xValue + 1));
            ability.getTargets().clear();
            ability.getTargets().add(new TargetCreaturePermanent(filter2));
        }
    }
}
