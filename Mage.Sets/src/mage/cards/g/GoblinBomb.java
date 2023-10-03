
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author fireshoes
 */
public final class GoblinBomb extends CardImpl {

    public GoblinBomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // At the beginning of your upkeep, you may flip a coin. If you win the flip, put a fuse counter on Goblin Bomb. If you lose the flip, remove a fuse counter from Goblin Bomb.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new GoblinBombEffect(), TargetController.YOU, true));

        // Remove five fuse counters from Goblin Bomb, Sacrifice Goblin Bomb: Goblin Bomb deals 20 damage to target player.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new DamageTargetEffect(20),
                new RemoveCountersSourceCost(CounterType.FUSE.createInstance(5))
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private GoblinBomb(final GoblinBomb card) {
        super(card);
    }

    @Override
    public GoblinBomb copy() {
        return new GoblinBomb(this);
    }
}

class GoblinBombEffect extends OneShotEffect {

    public GoblinBombEffect() {
        super(Outcome.Damage);
        staticText = "flip a coin. If you win the flip, put a fuse counter on {this}. If you lose the flip, remove a fuse counter from {this}";
    }

    private GoblinBombEffect(final GoblinBombEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            if (controller.flipCoin(source, game, true)) {
                game.informPlayers("Goblin Bomb: Won flip. Put a fuse counter on Goblin Bomb.");
                new AddCountersSourceEffect(CounterType.FUSE.createInstance(1)).apply(game, source);
                return true;
            } else {
                game.informPlayers("Goblin Bomb: Lost flip. Remove a fuse counter from Goblin Bomb.");
                new RemoveCounterSourceEffect(CounterType.FUSE.createInstance(1)).apply(game, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public GoblinBombEffect copy() {
        return new GoblinBombEffect(this);
    }
}
