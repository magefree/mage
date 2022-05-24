
package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author L_J
 */
public final class WordsOfWaste extends CardImpl {

    public WordsOfWaste(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // {1}: The next time you would draw a card this turn, each opponent discards a card instead.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new WordsOfWasteEffect(), new ManaCostsImpl<>("{1}")));
    }

    private WordsOfWaste(final WordsOfWaste card) {
        super(card);
    }

    @Override
    public WordsOfWaste copy() {
        return new WordsOfWaste(this);
    }
}

class WordsOfWasteEffect extends ReplacementEffectImpl {

    public WordsOfWasteEffect() {
        super(Duration.EndOfTurn, Outcome.Discard);
        staticText = "The next time you would draw a card this turn, each opponent discards a card instead";
    }

    public WordsOfWasteEffect(final WordsOfWasteEffect effect) {
        super(effect);
    }

    @Override
    public WordsOfWasteEffect copy() {
        return new WordsOfWasteEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            new DiscardEachPlayerEffect(TargetController.OPPONENT).apply(game, source);
            this.discard();
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
        return source.isControlledBy(event.getPlayerId());
    }
}
