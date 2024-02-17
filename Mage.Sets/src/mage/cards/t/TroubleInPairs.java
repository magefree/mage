package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.Watcher;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.*;

/**
 * @author PurpleCrowbar
 */
public final class TroubleInPairs extends CardImpl {

    public TroubleInPairs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // If an opponent would begin an extra turn, that player skips that turn instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TroubleInPairsSkipExtraTurnsEffect()));

        // Whenever an opponent attacks you with two or more creatures, draws their second card each turn, or casts their second spell each turn, you draw a card.
        this.addAbility(new TroubleInPairsTriggeredAbility());
    }

    private TroubleInPairs(final TroubleInPairs card) {
        super(card);
    }

    @Override
    public TroubleInPairs copy() {
        return new TroubleInPairs(this);
    }
}

class TroubleInPairsSkipExtraTurnsEffect extends ReplacementEffectImpl {

    TroubleInPairsSkipExtraTurnsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "If an opponent would begin an extra turn, that player skips that turn instead";
    }

    private TroubleInPairsSkipExtraTurnsEffect(final TroubleInPairsSkipExtraTurnsEffect effect) {
        super(effect);
    }

    @Override
    public TroubleInPairsSkipExtraTurnsEffect copy() {
        return new TroubleInPairsSkipExtraTurnsEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        MageObject sourceObject = game.getObject(source);
        if (player != null && sourceObject != null) {
            game.informPlayers(sourceObject.getLogName() + ": Extra turn of " + player.getLogName() + " skipped");
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.EXTRA_TURN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        return controller != null && controller.hasOpponent(event.getPlayerId(), game);
    }
}

class TroubleInPairsTriggeredAbility extends TriggeredAbilityImpl {

    TroubleInPairsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        this.addWatcher(new TroubleInPairsDrawCardWatcher());
    }

    private TroubleInPairsTriggeredAbility(final TroubleInPairsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TroubleInPairsTriggeredAbility copy() {
        return new TroubleInPairsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS
                || event.getType() == GameEvent.EventType.DREW_CARD
                || event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(getControllerId());
        if (controller == null || !game.getOpponents(getControllerId()).contains(event.getPlayerId())) {
            return false;
        }
        GameEvent.EventType eventType = event.getType();
        switch (eventType) {
            // Whenever an opponent attacks you with two or more creatures
            case DECLARED_ATTACKERS:
                return game
                        .getCombat()
                        .getAttackers()
                        .stream()
                        .map(uuid -> game.getCombat().getDefendingPlayerId(uuid, game))
                        .filter(getControllerId()::equals)
                        .count() >= 2;
            // Whenever an opponent draws their second card each turn
            case DREW_CARD:
                return TroubleInPairsDrawCardWatcher.checkEvent(event.getPlayerId(), event, game);
            // Whenever an opponent casts their second spell each turn
            case SPELL_CAST:
                CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
                return watcher != null && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(event.getPlayerId()) == 2;
        }

        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent attacks you with two or more creatures, draws their second " +
                "card each turn, or casts their second spell each turn, you draw a card.";
    }
}

class TroubleInPairsDrawCardWatcher extends Watcher {

    private final Map<UUID, List<UUID>> drawMap = new HashMap<>();

    TroubleInPairsDrawCardWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DREW_CARD) {
            return;
        }
        if (!drawMap.containsKey(event.getPlayerId())) {
            drawMap.putIfAbsent(event.getPlayerId(), new ArrayList<>());
        }
        drawMap.get(event.getPlayerId()).add(event.getId());
    }

    @Override
    public void reset() {
        super.reset();
        drawMap.clear();
    }

    static boolean checkEvent(UUID playerId, GameEvent event, Game game) {
        Map<UUID, List<UUID>> drawMap = game.getState().getWatcher(TroubleInPairsDrawCardWatcher.class).drawMap;
        return drawMap.containsKey(playerId) && Objects.equals(drawMap.get(playerId).size(), 2) && event.getId().equals(drawMap.get(playerId).get(1));
    }
}
