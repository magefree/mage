
package mage.cards.t;

import java.util.*;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 *
 * @author L_J
 */
public final class TheFallen extends CardImpl {

    public TheFallen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, The Fallen deals 1 damage to each opponent it has dealt damage to this game.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new TheFallenEffect(), TargetController.YOU, false), new TheFallenWatcher());
    }

    public TheFallen(final TheFallen card) {
        super(card);
    }

    @Override
    public TheFallen copy() {
        return new TheFallen(this);
    }
}

class TheFallenEffect extends OneShotEffect {

    public TheFallenEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 1 damage to each opponent or planeswalker it has dealt damage to this game";
    }

    public TheFallenEffect(final TheFallenEffect effect) {
        super(effect);
    }

    @Override
    public TheFallenEffect copy() {
        return new TheFallenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TheFallenWatcher watcher = game.getState().getWatcher(TheFallenWatcher.class);
        if (watcher != null && watcher.getPlayersAndWalkersDealtDamageThisGame(source.getSourceId()) != null) {
            for (UUID playerId : watcher.getPlayersAndWalkersDealtDamageThisGame(source.getSourceId())) {
                if (!source.isControlledBy(playerId)) {
                    game.damagePlayerOrPlaneswalker(playerId, 1, source.getSourceId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }
}

class TheFallenWatcher extends Watcher {

    private Map<UUID, Set<UUID>> playersAndWalkersDealtDamageThisGame = new HashMap<>(); // Map<creatureId, Set<playerId>>

    public TheFallenWatcher() {
        super(TheFallenWatcher.class, WatcherScope.GAME);
    }

    public TheFallenWatcher(final TheFallenWatcher watcher) {
        super(watcher);
        playersAndWalkersDealtDamageThisGame = new HashMap<>(watcher.playersAndWalkersDealtDamageThisGame);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.DAMAGED_PLANESWALKER) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (permanent != null) {
                Set<UUID> toAdd;
                if (playersAndWalkersDealtDamageThisGame.get(event.getSourceId()) == null) {
                    toAdd = new HashSet<>();
                } else {
                    toAdd = playersAndWalkersDealtDamageThisGame.get(event.getSourceId());
                }
                toAdd.add(event.getPlayerId());
                playersAndWalkersDealtDamageThisGame.put(event.getSourceId(), toAdd);
            }
        }
    }

    public Set<UUID> getPlayersAndWalkersDealtDamageThisGame(UUID creatureId) {
        return playersAndWalkersDealtDamageThisGame.get(creatureId);
    }

    @Override
    public TheFallenWatcher copy() {
        return new TheFallenWatcher(this);
    }
}
