package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author L_J
 */
public final class Desolation extends CardImpl {

    public Desolation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");

        // At the beginning of each end step, each player who tapped a land for mana this turn sacrifices a land. Desolation deals 2 damage to each player who sacrificed a Plains this way.
        BeginningOfEndStepTriggeredAbility ability = new BeginningOfEndStepTriggeredAbility(new DesolationEffect(), TargetController.ANY, false);
        this.addAbility(ability, new DesolationWatcher());
    }

    public Desolation(final Desolation card) {
        super(card);
    }

    @Override
    public Desolation copy() {
        return new Desolation(this);
    }
}

class DesolationEffect extends OneShotEffect {

    private static final FilterPermanent filterPlains = new FilterPermanent();

    static {
        filterPlains.add(SubType.PLAINS.getPredicate());
    }

    public DesolationEffect() {
        super(Outcome.Damage);
        this.staticText = "each player who tapped a land for mana this turn sacrifices a land. {this} deals 2 damage to each player who sacrificed a Plains this way";
    }

    public DesolationEffect(DesolationEffect copy) {
        super(copy);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DesolationWatcher watcher = game.getState().getWatcher(DesolationWatcher.class);
        if (watcher != null) {
            for (UUID playerId : watcher.getPlayersTappedForMana()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    FilterControlledPermanent filter = new FilterControlledPermanent("land");
                    filter.add(CardType.LAND.getPredicate());
                    filter.add(new ControllerIdPredicate(playerId));
                    TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, true);
                    if (target.canChoose(player.getId(), game)) {
                        player.choose(Outcome.Sacrifice, target, source.getSourceId(), game);
                        Permanent permanent = game.getPermanent(target.getFirstTarget());
                        if (permanent != null) {
                            permanent.sacrifice(source.getSourceId(), game);
                            if (filterPlains.match(permanent, game)) {
                                player.damage(2, source.getSourceId(), game);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public DesolationEffect copy() {
        return new DesolationEffect(this);
    }
}

class DesolationWatcher extends Watcher {

    private final Set<UUID> tappedForManaThisTurnPlayers = new HashSet<>();

    public DesolationWatcher() {
        super(WatcherScope.GAME);
    }


    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UNTAP_STEP_PRE) {
            reset();
        }
        if (event.getType() == GameEvent.EventType.TAPPED_FOR_MANA 
                && !game.inCheckPlayableState()) { // Ignored - see GameEvent.TAPPED_FOR_MANA
            UUID playerId = event.getPlayerId();
            if (playerId != null) {
                Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
                if (permanent != null && permanent.isLand()) {
                    tappedForManaThisTurnPlayers.add(playerId);
                }
            }
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
