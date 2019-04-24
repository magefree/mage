
package mage.cards.c;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.watchers.Watcher;

/**
 *
 * @author emerald000
 */
public final class CerebralVortex extends CardImpl {

    public CerebralVortex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{R}");

        // Target player draws two cards, then Cerebral Vortex deals damage to that player equal to the number of cards he or she has drawn this turn.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(2));
        this.getSpellAbility().addEffect(new CerebralVortexEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addWatcher(new CerebralVortexWatcher());
    }

    public CerebralVortex(final CerebralVortex card) {
        super(card);
    }

    @Override
    public CerebralVortex copy() {
        return new CerebralVortex(this);
    }
}

class CerebralVortexEffect extends OneShotEffect {
    
    CerebralVortexEffect() {
        super(Outcome.Damage);
        this.staticText = ", then Cerebral Vortex deals damage to that player equal to the number of cards he or she has drawn this turn";
    }
    
    CerebralVortexEffect(final CerebralVortexEffect effect) {
        super(effect);
    }
    
    @Override
    public CerebralVortexEffect copy() {
        return new CerebralVortexEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            CerebralVortexWatcher watcher = (CerebralVortexWatcher) game.getState().getWatchers().get(CerebralVortexWatcher.class.getSimpleName());
            if (watcher != null) {
                targetPlayer.damage(watcher.getDraws(targetPlayer.getId()), source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }
}

class CerebralVortexWatcher extends Watcher {

    private final Map<UUID, Integer> draws = new HashMap<>();

    CerebralVortexWatcher() {
        super(CerebralVortexWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    CerebralVortexWatcher(final CerebralVortexWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Integer> entry: watcher.draws.entrySet()) {
            draws.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.DREW_CARD) {
            int count = 1;
            if (draws.containsKey(event.getPlayerId())) {
                count += draws.get(event.getPlayerId());
            }
            draws.put(event.getPlayerId(), count);
        }
    }

    @Override
    public void reset() {
        super.reset();
        draws.clear();
    }

    public int getDraws(UUID playerId) {
        return draws.getOrDefault(playerId, 0);
    }

    @Override
    public CerebralVortexWatcher copy() {
        return new CerebralVortexWatcher(this);
    }
}
