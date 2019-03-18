
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.common.CardsDrawnDuringDrawStepWatcher;

/**
 *
 * @author fireshoes
 */
public final class AlhammarretsArchive extends CardImpl {

    public AlhammarretsArchive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");
        addSuperType(SuperType.LEGENDARY);

        // If you would gain life, you gain twice that much life instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AlhammarretsArchiveEffect()));

        // If you draw a card except the first one you draw in each of your draw steps, draw two cards instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AlhammarretsArchiveReplacementEffect()), new CardsDrawnDuringDrawStepWatcher());
    }

    public AlhammarretsArchive(final AlhammarretsArchive card) {
        super(card);
    }

    @Override
    public AlhammarretsArchive copy() {
        return new AlhammarretsArchive(this);
    }
}

class AlhammarretsArchiveEffect extends ReplacementEffectImpl {

    public AlhammarretsArchiveEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would gain life, you gain twice that much life instead";
    }

    public AlhammarretsArchiveEffect(final AlhammarretsArchiveEffect effect) {
        super(effect);
    }

    @Override
    public AlhammarretsArchiveEffect copy() {
        return new AlhammarretsArchiveEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() * 2);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAIN_LIFE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId()) && (source.getControllerId() != null);
    }
}

class AlhammarretsArchiveReplacementEffect extends ReplacementEffectImpl {

    public AlhammarretsArchiveReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "If you draw a card except the first one you draw in each of your draw steps, draw two cards instead";
    }

    public AlhammarretsArchiveReplacementEffect(final AlhammarretsArchiveReplacementEffect effect) {
        super(effect);
    }

    @Override
    public AlhammarretsArchiveReplacementEffect copy() {
        return new AlhammarretsArchiveReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.drawCards(2, game, event.getAppliedEffects());
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())) {
            if (game.isActivePlayer(event.getPlayerId())
                    && game.getPhase().getStep().getType() == PhaseStep.DRAW) {
                CardsDrawnDuringDrawStepWatcher watcher = game.getState().getWatcher(CardsDrawnDuringDrawStepWatcher.class);
                if (watcher != null && watcher.getAmountCardsDrawn(event.getPlayerId()) > 0) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }
}
