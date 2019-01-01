
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class DrasticRevelation extends CardImpl {

    public DrasticRevelation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}{B}{R}");



        

        // Discard your hand. Draw seven cards, then discard three cards at random.
        this.getSpellAbility().addEffect(new DrasticRevelationEffect());
    }

    public DrasticRevelation(final DrasticRevelation card) {
        super(card);
    }

    @Override
    public DrasticRevelation copy() {
        return new DrasticRevelation(this);
    }
}

class DrasticRevelationEffect extends OneShotEffect {

    DrasticRevelationEffect() {
        super(Outcome.DrawCard);
        staticText = "Discard your hand. Draw seven cards, then discard three cards at random";
    }

    DrasticRevelationEffect(final DrasticRevelationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if(you != null) {
            you.discardToMax(game);
            you.drawCards(7, game);
            Cards hand = you.getHand();
            for (int i = 0; i < 3; i++) {
                Card card = hand.getRandom(game);
                if (card != null) {
                    you.discard(card, source, game);
                }
            }
        }
        return false;
    }

    @Override
    public DrasticRevelationEffect copy() {
        return new DrasticRevelationEffect(this);
    }
}