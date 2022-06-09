package mage.cards.s;

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
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.VerseCounterAdjuster;

/**
 *
 * @author TheElk801
 */
public final class SerrasLiturgy extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("up to X target artifacts and/or enchantments, where X is the number of verse counters on {this}");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public SerrasLiturgy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // At the beginning of your upkeep, you may put a verse counter on Serra's Liturgy.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.VERSE.createInstance(), true), TargetController.YOU, true));

        // {W}, Sacrifice Serra's Liturgy: Destroy up to X target artifacts and/or enchantments, where X is the number of verse counters on Serra's Liturgy.
        Effect effect = new DestroyTargetEffect(true);
        effect.setText("Destroy up to X target artifacts and/or enchantments, where X is the number of verse counters on {this}.");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{W}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(0, 0, filter, false));
        ability.setTargetAdjuster(VerseCounterAdjuster.instance);
        this.addAbility(ability);
    }

    private SerrasLiturgy(final SerrasLiturgy card) {
        super(card);
    }

    @Override
    public SerrasLiturgy copy() {
        return new SerrasLiturgy(this);
    }
}
