
package mage.cards.g;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 * @author LevelX2
 */
public final class GontisMachinations extends CardImpl {

    public GontisMachinations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        // Whenever you lose life for the first time each turn, you get {E}.
        this.addAbility(new GontisMachinationsTriggeredAbility(), new GontisMachinationsFirstLostLifeThisTurnWatcher());

        // Pay {E}{E}, Sacrifice Gonti's Machinations: Each opponent loses 3 life. You gain life equal to the life lost this way.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GontisMachinationsEffect(), new PayEnergyCost(2));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

    }

    public GontisMachinations(final GontisMachinations card) {
        super(card);
    }

    @Override
    public GontisMachinations copy() {
        return new GontisMachinations(this);
    }
}

class GontisMachinationsTriggeredAbility extends TriggeredAbilityImpl {

    public GontisMachinationsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GetEnergyCountersControllerEffect(1), false);
    }

    public GontisMachinationsTriggeredAbility(final GontisMachinationsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(getControllerId())) {
            GontisMachinationsFirstLostLifeThisTurnWatcher watcher
                    = (GontisMachinationsFirstLostLifeThisTurnWatcher) game.getState().getWatchers().get(GontisMachinationsFirstLostLifeThisTurnWatcher.class.getSimpleName());
            if (watcher != null && watcher.timesLostLifeThisTurn(event.getTargetId()) < 2) {
                return true;
            }
        }
        return false;
    }

    @Override
    public GontisMachinationsTriggeredAbility copy() {
        return new GontisMachinationsTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you lose life for the first time each turn, " + super.getRule();
    }
}

class GontisMachinationsFirstLostLifeThisTurnWatcher extends Watcher {

    private final Map<UUID, Integer> playersLostLife = new HashMap<>();

    public GontisMachinationsFirstLostLifeThisTurnWatcher() {
        super(GontisMachinationsFirstLostLifeThisTurnWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public GontisMachinationsFirstLostLifeThisTurnWatcher(final GontisMachinationsFirstLostLifeThisTurnWatcher watcher) {
        super(watcher);
        this.playersLostLife.putAll(watcher.playersLostLife);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case LOST_LIFE:
                int timesLifeLost = playersLostLife.getOrDefault(event.getTargetId(), 0);
                timesLifeLost++;
                playersLostLife.put(event.getTargetId(), timesLifeLost);
        }
    }

    @Override
    public GontisMachinationsFirstLostLifeThisTurnWatcher copy() {
        return new GontisMachinationsFirstLostLifeThisTurnWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        playersLostLife.clear();
    }

    public int timesLostLifeThisTurn(UUID playerId) {
        return playersLostLife.getOrDefault(playerId, 0);
    }
}

class GontisMachinationsEffect extends OneShotEffect {

    public GontisMachinationsEffect() {
        super(Outcome.Damage);
        staticText = "Each opponent loses 3 life. You gain life equal to the life lost this way";
    }

    public GontisMachinationsEffect(final GontisMachinationsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = 0;
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            damage += game.getPlayer(opponentId).loseLife(3, game, false);
        }
        game.getPlayer(source.getControllerId()).gainLife(damage, game, source);
        return true;
    }

    @Override
    public GontisMachinationsEffect copy() {
        return new GontisMachinationsEffect(this);
    }

}
