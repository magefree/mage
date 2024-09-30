package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class RevealPutInHandLoseLifeEffect extends OneShotEffect {

    public RevealPutInHandLoseLifeEffect() {
        this(false);
    }

    public RevealPutInHandLoseLifeEffect(boolean ifYouDo) {
        super(Outcome.Benefit);
        staticText = "reveal the top card of your library and put that card into your hand. " +
                (ifYouDo ? "If you do, y" : "Y") + "ou lose life equal to its mana value";
    }

    private RevealPutInHandLoseLifeEffect(final RevealPutInHandLoseLifeEffect effect) {
        super(effect);
    }

    @Override
    public RevealPutInHandLoseLifeEffect copy() {
        return new RevealPutInHandLoseLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.revealCards(source, new CardsImpl(card), game);
        player.moveCards(card, Zone.HAND, source, game);
        player.loseLife(card.getManaValue(), game, source, false);
        return true;
    }
}
