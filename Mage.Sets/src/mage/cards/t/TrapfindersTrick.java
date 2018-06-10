
package mage.cards.t;

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
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class TrapfindersTrick extends CardImpl {

    public TrapfindersTrick(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}");


        // Target player reveals their hand and discards all Trap cards.
        this.getSpellAbility().addEffect(new TrapfindersTrickEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public TrapfindersTrick(final TrapfindersTrick card) {
        super(card);
    }

    @Override
    public TrapfindersTrick copy() {
        return new TrapfindersTrick(this);
    }
}

class TrapfindersTrickEffect extends OneShotEffect {

    public TrapfindersTrickEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player reveals their hand and discards all Trap cards";
    }

    public TrapfindersTrickEffect(final TrapfindersTrickEffect effect) {
        super(effect);
    }

    @Override
    public TrapfindersTrickEffect copy() {
        return new TrapfindersTrickEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            Cards hand = player.getHand();
            player.revealCards("Trapfinder's Trick", hand, game);
            Set<Card> cards = hand.getCards(game);
            for (Card card : cards) {
                if (card != null && card.hasSubtype(SubType.TRAP, game)) {
                    player.discard(card, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
