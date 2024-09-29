
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class WordsOfWorship extends CardImpl {

    public WordsOfWorship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");

        // {1}: The next time you would draw a card this turn, you gain 5 life instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new WordsOfWorshipEffect(), new GenericManaCost(1));        
        this.addAbility(ability);
    }

    private WordsOfWorship(final WordsOfWorship card) {
        super(card);
    }

    @Override
    public WordsOfWorship copy() {
        return new WordsOfWorship(this);
    }
}

class WordsOfWorshipEffect extends ReplacementEffectImpl {
    
    WordsOfWorshipEffect() {
        super(Duration.EndOfTurn, Outcome.Damage);
        staticText = "The next time you would draw a card this turn, you gain 5 life instead.";
    }
    
    private WordsOfWorshipEffect(final WordsOfWorshipEffect effect) {
        super(effect);
    }
    
    @Override
    public WordsOfWorshipEffect copy() {
        return new WordsOfWorshipEffect(this);
    }
        
    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.gainLife(5, game, source);
			this.used = true;
            discard();
            return true;
        }
        return false;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used) {
			return source.isControlledBy(event.getPlayerId());
        }
        return false;
    }
}
