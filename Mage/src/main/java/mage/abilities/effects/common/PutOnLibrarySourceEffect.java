package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LevelX2
 */

public class PutOnLibrarySourceEffect extends OneShotEffect {

    private final boolean onTop;

    public PutOnLibrarySourceEffect(boolean onTop) {
        this(onTop, "put {this} on " + (onTop ? "top" : "the bottom") + " of its owner's library");
    }

    public PutOnLibrarySourceEffect(boolean onTop, String rule) {
        super(Outcome.ReturnToHand);
        this.onTop = onTop;
        this.staticText = rule;
    }

    protected PutOnLibrarySourceEffect(final PutOnLibrarySourceEffect effect) {
        super(effect);
        this.onTop = effect.onTop;
    }

    @Override
    public PutOnLibrarySourceEffect copy() {
        return new PutOnLibrarySourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = source.getSourceCardIfItStillExists(game);
        if (player == null || card == null) {
            return false;
        }
        if (onTop) {
            return player.putCardsOnTopOfLibrary(card, game, source, false);
        }
        return player.putCardsOnBottomOfLibrary(card, game, source, false);
    }
}
