
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.BearToken;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class WordsOfWilding extends CardImpl {

    public WordsOfWilding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // {1}: The next time you would draw a card this turn, create a 2/2 green Bear creature token instead.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new WordsOfWildingEffect(), new ManaCostsImpl<>("{1}")));
    }

    private WordsOfWilding(final WordsOfWilding card) {
        super(card);
    }

    @Override
    public WordsOfWilding copy() {
        return new WordsOfWilding(this);
    }
}

class WordsOfWildingEffect extends ReplacementEffectImpl {

    public WordsOfWildingEffect() {
        super(Duration.EndOfTurn, Outcome.PutCreatureInPlay);
        staticText = "The next time you would draw a card this turn, create a 2/2 green Bear creature token instead";
    }

    public WordsOfWildingEffect(final WordsOfWildingEffect effect) {
        super(effect);
    }

    @Override
    public WordsOfWildingEffect copy() {
        return new WordsOfWildingEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            new CreateTokenEffect(new BearToken()).apply(game, source);
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
        return source.isControlledBy(event.getPlayerId());
    }
}
