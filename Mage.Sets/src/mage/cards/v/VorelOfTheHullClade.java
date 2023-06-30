
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class VorelOfTheHullClade extends CardImpl {
    
    static final private FilterPermanent filter = new FilterPermanent("artifact, creature, or land");
    
    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()));
    }

    public VorelOfTheHullClade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERFOLK);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // {G}{U}, {tap}: For each counter on target artifact, creature, or land, put another of those counters on that permanent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new VorelOfTheHullCladeEffect(), new ManaCostsImpl<>("{G}{U}"));
        ability.addTarget(new TargetPermanent(filter));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        
    }

    private VorelOfTheHullClade(final VorelOfTheHullClade card) {
        super(card);
    }

    @Override
    public VorelOfTheHullClade copy() {
        return new VorelOfTheHullClade(this);
    }
}

class VorelOfTheHullCladeEffect extends OneShotEffect {

    public VorelOfTheHullCladeEffect() {
        super(Outcome.Benefit);
        staticText = "double the number of each kind of counter on target artifact, creature, or land";
    }

    public VorelOfTheHullCladeEffect(VorelOfTheHullCladeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target == null) {
            return false;
        }
        for (Counter counter : target.getCounters(game).values()) {
            Counter newCounter = new Counter(counter.getName(), counter.getCount());
            target.addCounters(newCounter, source.getControllerId(), source, game);
        }
        return true;
    }

    @Override
    public VorelOfTheHullCladeEffect copy() {
        return new VorelOfTheHullCladeEffect(this);
    }

}
