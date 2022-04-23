package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class ShuffleLibrarySourceEffect extends OneShotEffect {

    private boolean optional;

    public ShuffleLibrarySourceEffect() {
        this(false);
    }

    public ShuffleLibrarySourceEffect(boolean optional) {
        super(Outcome.Neutral);
        this.optional = optional;
        this.staticText = optional ? "you may shuffle" : "shuffle your library";
    }

    public ShuffleLibrarySourceEffect(final ShuffleLibrarySourceEffect effect) {
        super(effect);
        this.optional = effect.optional;
    }

    @Override
    public ShuffleLibrarySourceEffect copy() {
        return new ShuffleLibrarySourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (!optional || player.chooseUse(Outcome.Benefit, "Shuffle your library?", source, game)) {
                player.shuffleLibrary(source, game);
            }
            return true;
        }
        return false;
    }
}
