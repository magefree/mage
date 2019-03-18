
package mage.cards.a;

import java.util.Set;
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
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class Amnesia extends CardImpl {

    public Amnesia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}{U}{U}");

        // Target player reveals their hand and discards all nonland cards.
        this.getSpellAbility().addEffect(new AmnesiaEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public Amnesia(final Amnesia card) {
        super(card);
    }

    @Override
    public Amnesia copy() {
        return new Amnesia(this);
    }
}

class AmnesiaEffect extends OneShotEffect {

    public AmnesiaEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player reveals their hand and discards all nonland cards";
    }

    public AmnesiaEffect(final AmnesiaEffect effect) {
        super(effect);
    }

    @Override
    public AmnesiaEffect copy() {
        return new AmnesiaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            Cards hand = player.getHand();
            player.revealCards("Amnesia", hand, game);
            Set<Card> cards = hand.getCards(game);
            for (Card card : cards) {
                if (card != null && !card.isLand()) {
                    player.discard(card, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
