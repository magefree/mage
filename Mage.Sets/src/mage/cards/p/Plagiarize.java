
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Skyler Sell
 */
public final class Plagiarize extends CardImpl {

    public Plagiarize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");

        // Until end of turn, if target player would draw a card, instead that player skips that draw and you draw a card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new PlagiarizeEffect());
    }

    private Plagiarize(final Plagiarize card) {
        super(card);
    }

    @Override
    public Plagiarize copy() {
        return new Plagiarize(this);
    }
}

class PlagiarizeEffect extends ReplacementEffectImpl {
    
    
    public PlagiarizeEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        staticText = "Until end of turn, if target player would draw a card, instead that player skips that draw and you draw a card";
    }
    
    private PlagiarizeEffect(final PlagiarizeEffect effect) {
        super(effect);
    }
    
    @Override
    public PlagiarizeEffect copy() {
        return new PlagiarizeEffect(this);
    }
        
    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(1, source, game, event);
        }
        return true;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getFirstTarget())) {
            return true;
        }
        return false;
    }
}
