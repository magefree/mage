package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.hint.Hint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.QuandrixToken;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmergentSequence extends CardImpl {

    public EmergentSequence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Search your library for a basic land card, put it onto the battlefield tapped, then shuffle. That land becomes a 0/0 green and blue Fractal creature that's still a land. Put a +1/+1 counter on it for each land you had enter the battlefield under your control this turn.
        this.getSpellAbility().addEffect(new EmergentSequenceEffect());
        this.getSpellAbility().addWatcher(new EmergentSequenceWatcher());
        this.getSpellAbility().addHint(EmergentSequenceHint.instance);
    }

    private EmergentSequence(final EmergentSequence card) {
        super(card);
    }

    @Override
    public EmergentSequence copy() {
        return new EmergentSequence(this);
    }
}

class EmergentSequenceEffect extends OneShotEffect {

    EmergentSequenceEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for a basic land card, put it onto the battlefield tapped, then shuffle. " +
                "That land becomes a 0/0 green and blue Fractal creature that's still a land. " +
                "Put a +1/+1 counter on it for each land you had enter the battlefield under your control this turn";
    }

    private EmergentSequenceEffect(final EmergentSequenceEffect effect) {
        super(effect);
    }

    @Override
    public EmergentSequenceEffect copy() {
        return new EmergentSequenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND_A);
        player.searchLibrary(target, source, game);
        Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
        Permanent permanent = null;
        if (card != null) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
            permanent = game.getPermanent(target.getFirstTarget());
        }
        player.shuffleLibrary(source, game);
        if (permanent == null) {
            return true;
        }
        game.addEffect(new BecomesCreatureTargetEffect(
                new QuandrixToken(), true, true, Duration.Custom
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        permanent.addCounters(CounterType.P1P1.createInstance(
                EmergentSequenceWatcher.getAmount(source.getControllerId(), game)
        ), source.getControllerId(), source, game);
        return true;
    }
}

class EmergentSequenceWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    EmergentSequenceWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                && ((EntersTheBattlefieldEvent) event).getTarget().isLand()) {
            playerMap.compute(event.getPlayerId(), (u, i) -> i == null ? 1 : Integer.sum(i, 1));
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerMap.clear();
    }

    static int getAmount(UUID playerId, Game game) {
        EmergentSequenceWatcher watcher = game.getState().getWatcher(EmergentSequenceWatcher.class);
        return watcher != null ? watcher.playerMap.getOrDefault(playerId, 0) : 0;
    }
}

enum EmergentSequenceHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        return "Lands entered under your control this turn: "
                + EmergentSequenceWatcher.getAmount(ability.getControllerId(), game);
    }

    @Override
    public EmergentSequenceHint copy() {
        return instance;
    }
}
