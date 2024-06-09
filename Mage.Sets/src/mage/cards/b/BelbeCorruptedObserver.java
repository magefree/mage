package mage.cards.b;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPostCombatMainTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
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

    private static final Hint hint = new ValueHint("Opponents who lost life that turn", BelbeCorruptedObserverDynamicValue.instance);

    public BelbeCorruptedObserver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.ELF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of each player's postcombat main phase, that player adds {C}{C} for each of your opponents who lost life this turn.
        this.addAbility(new BeginningOfPostCombatMainTriggeredAbility(
                new BelbeCorruptedObserverEffect(), TargetController.ANY, false
        ).addHint(hint), new BelbeCorruptedObserverWatcher());
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
        if (player == null) {
            return false;
        }

        int playerCount = BelbeCorruptedObserverDynamicValue.instance.calculate(game, source, this);
        if (playerCount > 0) {
            player.getManaPool().addMana(Mana.ColorlessMana(2 * playerCount), game, source);
            return true;
        }
        return false;
    }
}

class BelbeCorruptedObserverWatcher extends Watcher {

    private final Map<UUID, Set<UUID>> opponentsWhoLostLife = new HashMap<>();

    BelbeCorruptedObserverWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.LOST_LIFE) {
            return;
        }
        game.getOpponents(event.getPlayerId()).forEach(uuid -> {
            opponentsWhoLostLife
                    .computeIfAbsent(uuid, x -> new HashSet<>())
                    .add(event.getPlayerId());
        });
    }

    @Override
    public void reset() {
        opponentsWhoLostLife.clear();
        super.reset();
    }

    int getOpponentCount(UUID controllerId) {
        return opponentsWhoLostLife.computeIfAbsent(controllerId, x -> new HashSet<>()).size();
    }
}

enum BelbeCorruptedObserverDynamicValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        BelbeCorruptedObserverWatcher watcher = game.getState().getWatcher(BelbeCorruptedObserverWatcher.class);
        if (watcher != null) {
            return watcher.getOpponentCount(sourceAbility.getControllerId());
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
