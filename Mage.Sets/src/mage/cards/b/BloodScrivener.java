

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */


public final class BloodScrivener  extends CardImpl {

    public BloodScrivener (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ZOMBIE, SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // If you would draw a card while you have no cards in hand, instead draw two cards and lose 1 life.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BloodScrivenerReplacementEffect()));

    }

    private BloodScrivener(final BloodScrivener card) {
        super(card);
    }

    @Override
    public BloodScrivener copy() {
        return new BloodScrivener(this);
    }

}

class BloodScrivenerReplacementEffect extends ReplacementEffectImpl {

    public BloodScrivenerReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would draw a card while you have no cards in hand, instead you draw two cards and you lose 1 life";
    }

    private BloodScrivenerReplacementEffect(final BloodScrivenerReplacementEffect effect) {
        super(effect);
    }

    @Override
    public BloodScrivenerReplacementEffect copy() {
        return new BloodScrivenerReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            player.drawCards(2, source, game, event);
            player.loseLife(1, game, source, false);
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
            Player player = game.getPlayer(event.getPlayerId());
            if(player != null) {
                if (player.getHand().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }
}
