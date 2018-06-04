
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class MakeAWish extends CardImpl {

    public MakeAWish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Return two cards at random from your graveyard to your hand.
        this.getSpellAbility().addEffect(new MakeAWishEffect());
    }

    public MakeAWish(final MakeAWish card) {
        super(card);
    }

    @Override
    public MakeAWish copy() {
        return new MakeAWish(this);
    }
}

class MakeAWishEffect extends OneShotEffect {

    public MakeAWishEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return two cards at random from your graveyard to your hand";
    }

    public MakeAWishEffect(final MakeAWishEffect effect) {
        super(effect);
    }

    @Override
    public MakeAWishEffect copy() {
        return new MakeAWishEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Cards cards = player.getGraveyard();
            for (int i = 0; i < 2 && !cards.isEmpty(); i++) {
                Card card = cards.getRandom(game);
                if (card != null) {
                    card.moveToZone(Zone.HAND, source.getSourceId(), game, true);
                    cards.remove(card);
                    game.informPlayers(card.getName() + " returned to the hand of " + player.getLogName());
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
