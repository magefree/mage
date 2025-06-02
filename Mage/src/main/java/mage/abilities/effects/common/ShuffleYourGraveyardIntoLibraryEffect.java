package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author xenohedron
 */
public class ShuffleYourGraveyardIntoLibraryEffect extends OneShotEffect {

    public ShuffleYourGraveyardIntoLibraryEffect() {
        super(Outcome.Neutral);
        this.staticText = "shuffle your graveyard into your library";
    }

    protected ShuffleYourGraveyardIntoLibraryEffect(final ShuffleYourGraveyardIntoLibraryEffect effect) {
        super(effect);
    }

    @Override
    public ShuffleYourGraveyardIntoLibraryEffect copy() {
        return new ShuffleYourGraveyardIntoLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        return controller != null && controller.shuffleCardsToLibrary(controller.getGraveyard(), game, source);
    }

}
