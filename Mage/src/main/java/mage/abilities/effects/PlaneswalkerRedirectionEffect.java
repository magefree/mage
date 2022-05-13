
package mage.abilities.effects;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 * NOTE: This is no longer used, but I'm leaving it in because why not
 * -TheElk801
 *
 * @author BetaSteward_at_googlemail.com
 */
@Deprecated
public class PlaneswalkerRedirectionEffect extends RedirectionEffect {

    public PlaneswalkerRedirectionEffect() {
        super(Duration.EndOfGame);
    }

    public PlaneswalkerRedirectionEffect(final PlaneswalkerRedirectionEffect effect) {
        super(effect);
    }

    @Override
    public PlaneswalkerRedirectionEffect copy() {
        return new PlaneswalkerRedirectionEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent) event;
        UUID playerId = getSourceControllerId(event.getSourceId(), game);
        if (!damageEvent.isCombatDamage() && game.getOpponents(event.getTargetId()).contains(playerId)) {
            Player target = game.getPlayer(event.getTargetId());
            Player player = game.getPlayer(playerId);
            if (target != null && player != null) {
                int numPlaneswalkers = game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_PLANESWALKER, target.getId(), game);
                if (numPlaneswalkers > 0 && player.chooseUse(outcome, "Redirect damage to planeswalker?", source, game)) {
                    redirectTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_PLANESWALKER);
                    if (numPlaneswalkers == 1) {
                        List<Permanent> planeswalker = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_PLANESWALKER, target.getId(), game);
                        if (!planeswalker.isEmpty()) {
                            redirectTarget.add(planeswalker.get(0).getId(), game);
                        }
                    } else {
                        player.choose(Outcome.Damage, redirectTarget, source, game);
                    }
                    if (!game.isSimulation()) {
                        Permanent redirectTo = game.getPermanent(redirectTarget.getFirstTarget());
                        if (redirectTo != null) {
                            game.informPlayers(player.getLogName() + " redirects " + event.getAmount() + " damage to " + redirectTo.getLogName());
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private UUID getSourceControllerId(UUID sourceId, Game game) {
        StackObject source = game.getStack().getStackObject(sourceId);
        if (source != null) {
            return source.getControllerId();
        }
        Permanent permanent = game.getBattlefield().getPermanent(sourceId);
        if (permanent != null) {
            return permanent.getControllerId();
        }
        // for effects like Deflecting Palm (could be wrong if card was played multiple times by different players)
        return game.getContinuousEffects().getControllerOfSourceId(sourceId);
    }
}
