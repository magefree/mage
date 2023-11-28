
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.CardsDrawnDuringDrawStepWatcher;

/**
 *
 * @author LevelX2
 */
public final class ChainsOfMephistopheles extends CardImpl {

    public ChainsOfMephistopheles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");

        // If a player would draw a card except the first one they draw in their draw step each turn, that player discards a card instead. If the player discards a card this way, they draw a card. If the player doesn't discard a card this way, they put the top card of their library into their graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ChainsOfMephistophelesReplacementEffect()), new CardsDrawnDuringDrawStepWatcher());
    }

    private ChainsOfMephistopheles(final ChainsOfMephistopheles card) {
        super(card);
    }

    @Override
    public ChainsOfMephistopheles copy() {
        return new ChainsOfMephistopheles(this);
    }
}

class ChainsOfMephistophelesReplacementEffect extends ReplacementEffectImpl {

    public ChainsOfMephistophelesReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a player would draw a card except the first one they draw in each of their draw steps, that player discards a card instead. If the player discards a card this way, they draw a card. If the player doesn't discard a card this way, they mill a card";
    }

    private ChainsOfMephistophelesReplacementEffect(final ChainsOfMephistophelesReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ChainsOfMephistophelesReplacementEffect copy() {
        return new ChainsOfMephistophelesReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            if (player.getHand().isEmpty()) {
                // they put the top card of their library into their graveyard
                Effect effect = new MillCardsTargetEffect(1);
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                effect.apply(game, source);
                return true;
            } else  {
                // discards a card instead. If the player discards a card this way, they draw a card.
                player.discard(1, false, false, source, game);
                return false; // because player draws a card, the draw event is kept
            }
        }
        return false;
    }
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    } 
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.isActivePlayer(event.getPlayerId()) && game.getPhase().getStep().getType() == PhaseStep.DRAW) {
            CardsDrawnDuringDrawStepWatcher watcher = game.getState().getWatcher(CardsDrawnDuringDrawStepWatcher.class);
            if (watcher != null && watcher.getAmountCardsDrawn(event.getPlayerId()) > 0) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }
}
