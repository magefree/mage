package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;

import mage.target.TargetPermanent;
import mage.target.targetadjustment.VerseCounterAdjuster;

/**
 *
 * @author TheElk801
 */
public final class RumblingCrescendo extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("up to X target lands, where X is the number of verse counters on {this}");

    static {
        filter.add(CardType.LAND.getPredicate());
    }

    public RumblingCrescendo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");

        // At the beginning of your upkeep, you may put a verse counter on Rumbling Crescendo.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.VERSE.createInstance(), true), TargetController.YOU, true));

        // {R}, Sacrifice Rumbling Crescendo: Destroy up to X target lands, where X is the number of verse counters on Rumbling Crescendo.        
        Effect effect = new DestroyTargetEffect(true);
        effect.setText("Destroy up to X target lands, where X is the number of verse counters on {this}.");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{R}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(0, 0, filter, false));
        ability.setTargetAdjuster(VerseCounterAdjuster.instance);
        this.addAbility(ability);
    }

    private RumblingCrescendo(final RumblingCrescendo card) {
        super(card);
    }

    @Override
    public RumblingCrescendo copy() {
        return new RumblingCrescendo(this);
    }
}
