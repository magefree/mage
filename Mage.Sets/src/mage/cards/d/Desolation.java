package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author L_J
 */
public final class Desolation extends CardImpl {

    public Desolation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");

        // At the beginning of each end step, each player who tapped a land for mana this
        // turn sacrifices a land. Desolation deals 2 damage to each player who sacrificed a Plains this way.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new DesolationEffect(), TargetController.ANY, false
        ), new DesolationWatcher());
    }

    private Desolation(final Desolation card) {
        super(card);
    }

    @Override
    public Desolation copy() {
        return new Desolation(this);
    }
}

class DesolationEffect extends OneShotEffect {

    DesolationEffect() {
        super(Outcome.Damage);
        this.staticText = "each player who tapped a land for mana this turn sacrifices a land. " +
                "{this} deals 2 damage to each player who sacrificed a Plains this way";
    }

    private DesolationEffect(DesolationEffect copy) {
        super(copy);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DesolationWatcher watcher = game.getState().getWatcher(DesolationWatcher.class);
        if (watcher == null) {
            return false;
        }
        List<Permanent> permanents = new ArrayList<>();
        for (UUID playerId : watcher.getPlayersTappedForMana()) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            TargetPermanent target = new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND);
            target.setNotTarget(true);
            if (!target.canChoose(player.getId(), source, game)) {
                continue;
            }
            player.choose(Outcome.Sacrifice, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                permanents.add(permanent);
            }
        }
        for (Permanent permanent : permanents) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (permanent != null
                    && permanent.sacrifice(source, game)
                    && permanent.hasSubtype(SubType.PLAINS, game)
                    && player != null) {
                player.damage(2, source.getSourceId(), source, game);
            }
        }
        return true;
    }

    @Override
    public DesolationEffect copy() {
        return new DesolationEffect(this);
    }
}

class DesolationWatcher extends Watcher {

    private final Set<UUID> tappedForManaThisTurnPlayers = new HashSet<>();

    DesolationWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (game.inCheckPlayableState() || event.getType() != GameEvent.EventType.TAPPED_FOR_MANA) {
            return;
        }
        Permanent permanent = ((TappedForManaEvent) event).getPermanent();
        if (permanent != null) {
            tappedForManaThisTurnPlayers.add(permanent.getControllerId());
        }
    }

    public Set<UUID> getPlayersTappedForMana() {
        return tappedForManaThisTurnPlayers;
    }

    @Override
    public void reset() {
        super.reset();
        tappedForManaThisTurnPlayers.clear();
    }
}
