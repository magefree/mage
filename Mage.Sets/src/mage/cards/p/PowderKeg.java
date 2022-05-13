
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author fireshoes
 */
public final class PowderKeg extends CardImpl {

    public PowderKeg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // At the beginning of your upkeep, you may put a fuse counter on Powder Keg.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.FUSE.createInstance(), true), TargetController.YOU, true));

        // {T}, Sacrifice Powder Keg: Destroy each artifact and creature with converted mana cost equal to the number of fuse counters on Powder Keg.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PowderKegEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private PowderKeg(final PowderKeg card) {
        super(card);
    }

    @Override
    public PowderKeg copy() {
        return new PowderKeg(this);
    }
}

class PowderKegEffect extends OneShotEffect {

    public PowderKegEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy each artifact and creature with mana value equal to the number of fuse counters on {this}";
    }

    public PowderKegEffect(final PowderKegEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent == null) {
            return false;
        }
        int count = sourcePermanent.getCounters(game).getCount(CounterType.FUSE);
        FilterPermanent filter = new FilterPermanent();
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.CREATURE.getPredicate()));
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, count));
        for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            perm.destroy(source, game, false);
        }
        return true;
    }

    @Override
    public PowderKegEffect copy() {
        return new PowderKegEffect(this);
    }

}
