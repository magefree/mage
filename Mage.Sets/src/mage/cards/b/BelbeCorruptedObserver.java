package mage.cards.b;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPostCombatMainTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class BelbeCorruptedObserver extends CardImpl {

    public BelbeCorruptedObserver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.ELF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of each player's postcombat main phase, that player adds {C}{C} for each of your opponents who lost life this turn.
        this.addAbility(new BeginningOfPostCombatMainTriggeredAbility(
                new BelbeCorruptedObserverEffect(), TargetController.ANY, false
        ), new BelbeCorruptedObserverWatcher());
    }

    private BelbeCorruptedObserver(final BelbeCorruptedObserver card) {
        super(card);
    }

    @Override
    public BelbeCorruptedObserver copy() {
        return new BelbeCorruptedObserver(this);
    }
}

class BelbeCorruptedObserverEffect extends OneShotEffect {

    BelbeCorruptedObserverEffect() {
        super(Outcome.Benefit);
        staticText = "that player adds {C}{C} for each of your opponents who lost life this turn";
    }

    private BelbeCorruptedObserverEffect(final BelbeCorruptedObserverEffect effect) {
        super(effect);
    }

    @Override
    public BelbeCorruptedObserverEffect copy() {
        return new BelbeCorruptedObserverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        BelbeCorruptedObserverWatcher watcher = game.getState().getWatcher(BelbeCorruptedObserverWatcher.class);
        if (player == null || watcher == null) {
            return false;
        }
        int playerCount = watcher.getOpponentCount(source.getControllerId());
        player.getManaPool().addMana(Mana.ColorlessMana(2 * playerCount), game, source);
        return true;
    }
}

class BelbeCorruptedObserverWatcher extends Watcher {

    private final Map<UUID, Set<UUID>> playerMap = new HashMap<>();

    BelbeCorruptedObserverWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.LOST_LIFE) {
            return;
        }
        game.getOpponents(event.getPlayerId())
                .stream()
                .map(uuid -> playerMap
                        .computeIfAbsent(uuid, x -> new HashSet<>())
                        .add(event.getPlayerId()));
    }

    @Override
    public void reset() {
        playerMap.clear();
        super.reset();
    }

    int getOpponentCount(UUID playerId) {
        return playerMap.computeIfAbsent(playerId, x -> new HashSet<>()).size();
    }
}
