
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class CullingDais extends CardImpl {

    public CullingDais(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        this.addAbility(ability);
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CullingDaisEffect(), new GenericManaCost(1));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private CullingDais(final CullingDais card) {
        super(card);
    }

    @Override
    public CullingDais copy() {
        return new CullingDais(this);
    }

}

class CullingDaisEffect extends OneShotEffect {

    CullingDaisEffect() {
        super(Outcome.DrawCard);
        staticText = "Draw a card for each charge counter on {this}";
    }

    CullingDaisEffect(final CullingDaisEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        Player player = game.getPlayer(source.getControllerId());
        if (p != null && player != null) {
            int count = p.getCounters(game).getCount(CounterType.CHARGE);
            player.drawCards(count, source, game);
            return true;
        }
        return false;
    }

    @Override
    public CullingDaisEffect copy() {
        return new CullingDaisEffect(this);
    }

}
