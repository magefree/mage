
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 * @author Loki
 */
public final class OblivionStone extends CardImpl {

    public OblivionStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {4}, {tap}: Put a fate counter on target permanent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.FATE.createInstance()), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
        // {5}, {tap}, Sacrifice Oblivion Stone: Destroy each nonland permanent without a fate counter on it, then remove all fate counters from all permanents.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new OblivionStoneEffect(), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private OblivionStone(final OblivionStone card) {
        super(card);
    }

    @Override
    public OblivionStone copy() {
        return new OblivionStone(this);
    }
}

class OblivionStoneEffect extends OneShotEffect {

    OblivionStoneEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy each nonland permanent without a fate counter on it, then remove all fate counters from all permanents";
    }

    private OblivionStoneEffect(final OblivionStoneEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent p : game.getBattlefield().getAllActivePermanents()) {
            if (!(p.isLand(game) || p.getCounters(game).containsKey(CounterType.FATE))) {
                p.destroy(source, game, false);
            }
        }
        for (Permanent p : game.getBattlefield().getAllActivePermanents()) {
            if (p.getCounters(game).containsKey(CounterType.FATE)) {
                p.removeCounters(CounterType.FATE.getName(), p.getCounters(game).getCount(CounterType.FATE), source, game);
            }
        }
        return true;
    }

    @Override
    public OblivionStoneEffect copy() {
        return new OblivionStoneEffect(this);
    }
}
