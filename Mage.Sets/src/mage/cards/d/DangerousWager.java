
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class DangerousWager extends CardImpl {

    public DangerousWager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");


        // Discard your hand, then draw two cards.
        this.getSpellAbility().addEffect(new DangerousWagerEffect());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    public DangerousWager(final DangerousWager card) {
        super(card);
    }

    @Override
    public DangerousWager copy() {
        return new DangerousWager(this);
    }
}

class DangerousWagerEffect extends OneShotEffect {

    public DangerousWagerEffect() {
        super(Outcome.Discard);
        this.staticText = "Discard your hand";
    }

    public DangerousWagerEffect(final DangerousWagerEffect effect) {
        super(effect);
    }

    @Override
    public DangerousWagerEffect copy() {
        return new DangerousWagerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (Card card : player.getHand().getCards(game)) {
                player.discard(card, source, game);
            }
            return true;
        }
        return false;
    }
}
