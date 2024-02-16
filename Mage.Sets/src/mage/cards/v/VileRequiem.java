package mage.cards.v;

import java.util.UUID;
import mage.ObjectColor;
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
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.VerseCounterAdjuster;

/**
 *
 * @author LevelX2
 */
public final class VileRequiem extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("up to X target nonblack creatures, where X is the number of verse counters on {this}");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
        filter.add(CardType.CREATURE.getPredicate());
    }

    public VileRequiem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // At the beginning of your upkeep, you may put a verse counter on Vile Requiem.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.VERSE.createInstance(), true), TargetController.YOU, true));

        // {1}{B}, Sacrifice Vile Requiem: Destroy up to X target nonblack creatures, where X is the number of verse counters on Vile Requiem. They can't be regenerated.
        Effect effect = new DestroyTargetEffect(true);
        effect.setText("Destroy up to X target nonblack creatures, where X is the number of verse counters on {this}. They can't be regenerated");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(0, 0, filter, false));
        ability.setTargetAdjuster(VerseCounterAdjuster.instance);
        this.addAbility(ability);

    }

    private VileRequiem(final VileRequiem card) {
        super(card);
    }

    @Override
    public VileRequiem copy() {
        return new VileRequiem(this);
    }
}
