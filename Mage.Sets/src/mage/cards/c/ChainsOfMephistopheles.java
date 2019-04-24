
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveTargetEffect;
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

        // If a player would draw a card except the first one he or she draws in their draw step each turn, that player discards a card instead. If the player discards a card this way, he or she draws a card. If the player doesn't discard a card this way, he or she puts the top card of their library into their graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ChainsOfMephistophelesReplacementEffect()), new CardsDrawnDuringDrawStepWatcher());
    }

    public ChainsOfMephistopheles(final ChainsOfMephistopheles card) {
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
        staticText = "If a player would draw a card except the first one he or she draws in their draw step each turn, that player discards a card instead. If the player discards a card this way, he or she draws a card. If the player doesn't discard a card this way, he or she puts the top card of their library into their graveyard";
    }

    public ChainsOfMephistophelesReplacementEffect(final ChainsOfMephistophelesReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ChainsOfMephistophelesReplacementEffect copy() {
        return new ChainsOfMephistophelesReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            if (player.getHand().isEmpty()) {
                // he or she puts the top card of their library into their graveyard
                Effect effect = new PutTopCardOfLibraryIntoGraveTargetEffect(1);
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                effect.apply(game, source);
                return true;
            } else  {
                // discards a card instead. If the player discards a card this way, he or she draws a card.
                player.discard(1, false, source, game);
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
            CardsDrawnDuringDrawStepWatcher watcher = (CardsDrawnDuringDrawStepWatcher) game.getState().getWatchers().get(CardsDrawnDuringDrawStepWatcher.class.getSimpleName());
            if (watcher != null && watcher.getAmountCardsDrawn(event.getPlayerId()) > 0) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }
}
