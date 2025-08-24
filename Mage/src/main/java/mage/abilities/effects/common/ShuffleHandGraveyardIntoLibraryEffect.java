package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class ShuffleHandGraveyardIntoLibraryEffect extends OneShotEffect {

    public ShuffleHandGraveyardIntoLibraryEffect() {
        super(Outcome.Discard);
        this.staticText = "shuffle your hand and graveyard into your library";
    }

    protected ShuffleHandGraveyardIntoLibraryEffect(final ShuffleHandGraveyardIntoLibraryEffect effect) {
        super(effect);
    }

    @Override
    public ShuffleHandGraveyardIntoLibraryEffect copy() {
        return new ShuffleHandGraveyardIntoLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getHand());
        cards.addAll(player.getGraveyard());
        return player.shuffleCardsToLibrary(cards, game, source);
    }
}
